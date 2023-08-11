package com.zetech.thingapp.thingapp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.constants.SpecialUsers;
import com.zetech.thingapp.thingapp.exceptions.FatalException;
import com.zetech.thingapp.thingapp.exceptions.NoDataFoundException;
import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.AuthResponseVO;
import com.zetech.thingapp.thingapp.model.UserAuthTokenVO;
import com.zetech.thingapp.thingapp.model.UserPasswordVO;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.SystemToken;
import com.zetech.thingapp.thingapp.security.UserToken;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Verifyer;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService implements AuthServiceInterface
{

  // timeout for tokens is 24 hours
  private static final int timeout_seconds = 86400;

  // inactive timout for tokens is 10 minutes
  private static final int inactive_timeout_seconds = 600;

  // used to identify the type of token
  private static final String token_type = "Bearer";

  @Autowired
  private UserPasswordServiceInterface _userPasswordService;

  @Autowired UserAuthTokenServiceInterface _userAuthTokenService;

  @Autowired
  private UserService _userService;

  public AuthResponseVO login(AuthRequestVO record,  UserToken token) throws ThingAppException 
  {
    if(!token.getUserId().contentEquals(SpecialUsers.UNAUTHENTICATED_USER.toString()))
    {
      throw new NotAuthorizedException("User already logged in.");
    }

    // get our private key to use for signing the JWT token
    PrivateKey privateKey = null;
    try 
    {
      privateKey = getPrivateKey();
    } 
    catch (Exception e) 
    {
      throw new FatalException("Error getting private key", e);
    }

    // create a token to act on behalf of the system
    SystemToken systemToken = new SystemToken();

    UserPasswordVO userPasswordVO = _userPasswordService.retrieveByEmail(record.getUserLoginId(), systemToken);

    if(null == userPasswordVO) 
    {
      throw new NotAuthorizedException("Login Failed: Username or password invalid.");
    }

    UserVO user = _userService.retrieveByEmail(record.getUserLoginId(), systemToken);

    if(null == user) 
    {
      throw new NotAuthorizedException("Error: User not found.");
    }

    // this is our logic to check the password with bcrypt
    Verifyer verifier = BCrypt.verifyer();

    Boolean passwordMatch = verifier.verify(record.getUserPassword().toCharArray(), userPasswordVO.getPasswordHash()).verified;

    if(passwordMatch) 
    {
      AuthResponseVO encryptedAuthTokenVO = new AuthResponseVO();

      // get the date and calculate the expiration
      Date now = new Date();

      // round "now" to the nearest second since JWT and Date methods don't treat milliseconds the same
      long timeInMillis = now.getTime();
      long roundedTimeInMillis = Math.round(timeInMillis / 1000.0) * 1000;  // Round to nearest second
      now = new Date(roundedTimeInMillis);

      Date expiration = Date.from(now.toInstant().plus(timeout_seconds, ChronoUnit.SECONDS));

      // build the jwt token with the user id as the subject
      // and a random UUID for the token id
      // signed with our private key
      String uuid = UUID.randomUUID().toString();
      
      // create the user auth token to write to the database
      UserAuthTokenVO userAuthTokenVO = new UserAuthTokenVO();
      userAuthTokenVO.setUuid(uuid);
      userAuthTokenVO.setUserId(user.getUserId());
      userAuthTokenVO.setClientIpAddress(token.getIpAddress());
      userAuthTokenVO.setExpirationDtg(expiration);
      userAuthTokenVO.setIssuedDtg(now);
      userAuthTokenVO.setLastActiveDtg(now);

      // write the token to the database
      _userAuthTokenService.create(userAuthTokenVO, systemToken);

      String jwtToken = Jwts.builder()
        .setSubject(userAuthTokenVO.getUserId())
        .setId(userAuthTokenVO.getUuid())
        .setIssuedAt(userAuthTokenVO.getIssuedDtg())
        .setExpiration(userAuthTokenVO.getExpirationDtg())
        .signWith(privateKey)
        .compact();

      // set the values to return the token to the client in a standard format
      encryptedAuthTokenVO.setAccessToken(jwtToken);
      encryptedAuthTokenVO.setTokenType(token_type);
      encryptedAuthTokenVO.setExpires(timeout_seconds);

      return encryptedAuthTokenVO;
    }
    else
    {
      throw new NotAuthorizedException("Login Failed: Username or password invalid.");
    }
  }

  public int logout(UserToken token) throws ThingAppException 
  {
    UserAuthTokenVO uat = null;

    try
    {
      uat = _userAuthTokenService.retrieveByUuid(token.getAuthTokenId(), token);
    }
    catch(ThingAppException e)
    {
      throw new NotAuthorizedException("Token no longer valid, please log in again");
    }

    return _userAuthTokenService.delete(uat, token);
  }

  public int logoutEverywhere(UserToken token) throws ThingAppException 
  {
    try
    {
      return _userAuthTokenService.deleteAllForUser(token.getUserId(), token);
    }
    catch(ThingAppException e) 
    {
      throw new NoDataFoundException("Token not found", e);
    }
  }

  // TODO: make this better
  private int invalidate(String uuid, UserToken token) throws ThingAppException 
  {
    // get the token from the database
    UserAuthTokenVO userAuthToken = null;

    try
    {
      userAuthToken = _userAuthTokenService.retrieveByUuid(uuid, token);
    }
    catch(ThingAppException e) 
    {
      return 0;
    }

    return _userAuthTokenService.delete(userAuthToken, token);
  }

  @Override
  public UserAuthTokenVO authenticate(String bearerToken, String ipAddress) throws ThingAppException 
  {   
    // create a token to act on behalf of the system
    SystemToken systemToken = new SystemToken();

    // get our private key to use for signing the JWT token
    PrivateKey privateKey = null;
    try 
    {
      privateKey = getPrivateKey();
    } 
    catch (Exception e) 
    {
      throw new FatalException("Error getting private key", e);
    }
    
    // if there is no token, throw not authorized
    if(null == bearerToken || bearerToken.isEmpty()) 
    {
      throw new NotAuthorizedException("User is not logged in.");
    }

    // if the token is not a bearer token, throw not authorized
    else if(!bearerToken.startsWith("Bearer ")) 
    {
      throw new NotAuthorizedException("User auth token is invalid");
    }

    // get the token without the bearer prefix
    String jwsTokenString = bearerToken.substring(7);

    // todo: maybe convert this into it's own JWT bean
    // build the parser with our private key
    JwtParserBuilder jwtPBuilder = Jwts.parserBuilder();
    jwtPBuilder.setSigningKey(privateKey);
    JwtParser jwtParser = jwtPBuilder.build();

    // parse the token to get the claims
    Jws<Claims> jwsTokenClaims = null;
    
    try 
    {
      jwsTokenClaims = jwtParser.parseClaimsJws(jwsTokenString);
    }
    catch(Exception e) 
    {
      throw new NotAuthorizedException("User auth token is invalid");
    }

    // validate that the token exists
    String uuid = jwsTokenClaims.getBody().getId();
    UserAuthTokenVO uat = null;

    try
    {
      uat = _userAuthTokenService.retrieveByUuid(uuid, systemToken);
    }
    catch(ThingAppException e)
    {
      throw new NotAuthorizedException("Token no longer valid, please log in again");
    }

    // validate the token fields with the database
    // notice how we void the token if any of these fields are different
    if (!uat.getUserId().contentEquals(jwsTokenClaims.getBody().getSubject()))
    {
      invalidate(uat.getUuid(), systemToken);
      throw new NotAuthorizedException("Token invalid user ID, please log in again");
    }
    if (0 != uat.getExpirationDtg().compareTo(jwsTokenClaims.getBody().getExpiration()))
    {
      invalidate(uat.getUuid(), systemToken);
      throw new NotAuthorizedException("Token invalid expiration date, please log in again");
    }
    if (0 != uat.getIssuedDtg().compareTo(jwsTokenClaims.getBody().getIssuedAt()))
    {
      invalidate(uat.getUuid(), systemToken);
      throw new NotAuthorizedException("Token invalid issued date, please log in again");
    }

    // validate the the ip address of the token matches the IP adress of the client
    if (!uat.getClientIpAddress().contentEquals(ipAddress))
    {
      throw new NotAuthorizedException("IP address change detected, please log in again");
    }

    // validate that the last active date isn't too old
    Date now = new Date();
    Date inactive_timout = Date.from(now.toInstant().minus(inactive_timeout_seconds, ChronoUnit.SECONDS));

    if(uat.getLastActiveDtg().before(inactive_timout))
    {
      invalidate(uat.getUuid(), systemToken);
      throw new NotAuthorizedException("User logged out due to inactivity. Please log in again");
    }

    // validate that the token isn't expired
    if (uat.getExpirationDtg().before(now))    
    {
      invalidate(uat.getUuid(), systemToken);
      throw new NotAuthorizedException("User login has expired. Please log in again");
    }

    // update the last active date
    _userAuthTokenService.updateLastActive(uat, systemToken);

    // return the user HttpServletRequest request of the authenticated user
    return uat;

  }

  // TODO: maybe wrap this with better error handling
  private PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, UnrecoverableKeyException, KeyStoreException, CertificateException, IOException 
  {
    // load dotenv to get the environment variables
    Dotenv dotenv = Dotenv.load();

    // get the required environment variables
    String keystore_path = dotenv.get("KEYSTORE_PATH");
    String keyAlias = dotenv.get("KEY_ALIAS");
    String keyPassword = dotenv.get("KEY_PASSWORD");
    String keystoreType = dotenv.get("KEYSTORE_TYPE");

    // load the keystore and get the private key
    KeyStore keyStore = KeyStore.getInstance(keystoreType);
    FileInputStream keyFile = new FileInputStream(keystore_path);
    keyStore.load(keyFile, keyPassword.toCharArray());
    PrivateKey privateKey = (PrivateKey)keyStore.getKey(keyAlias, keyPassword.toCharArray());

    return privateKey;
  }

}
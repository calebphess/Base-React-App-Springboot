package com.zetech.thingapp.thingapp.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Verifyer;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.zetech.thingapp.thingapp.exceptions.FatalException;
import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.AuthResponseVO;
import com.zetech.thingapp.thingapp.model.AuthTokenVO;
import com.zetech.thingapp.thingapp.model.UserPasswordVO;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.SystemToken;
import com.zetech.thingapp.thingapp.security.UserToken;


@Service
public class AuthService implements AuthServiceInterface
{

  private static final int timeout_seconds = 86400;
  private static final String token_type = "Bearer";

  @Autowired
  private UserPasswordServiceInterface _userPasswordService;

  @Autowired
  private UserService _userService;

  public AuthResponseVO login(AuthRequestVO record,  HttpServletRequest request) throws ThingAppException 
  {
    // TODO: implement code to create JWT token and UserAuthTokenVO to write to database

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

    // get the password hash on behalf of the system
    SystemToken token = new SystemToken();

    UserPasswordVO userPasswordVO = _userPasswordService.retrieveByEmail(record.getUserLoginId(), token);

    if(null == userPasswordVO) 
    {
      throw new NotAuthorizedException("Login Failed: Username or password invalid.");
    }

    UserVO user = _userService.retrieveByEmail(record.getUserLoginId(), token);

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
      Date expiration = Date.from(now.toInstant().plus(timeout_seconds, ChronoUnit.SECONDS));

      // build the jwt token with the user id as the subject
      // and a random UUID for the token id
      // signed with our private key
      String jwtToken = Jwts.builder()
        .setSubject(user.getUserId())
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(privateKey)
        .compact();

      // TODO: write the token to the database

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

  // TODO: implement this for token invalidation
  public int invalidate(UserToken token,  HttpServletRequest request) throws ThingAppException 
  {
    // TODO: Finish this code
    return 1;
  }

  @Override
  public UserVO authenticate(String token) throws ThingAppException 
  {
    // TODO properly validate the token
    // TODO maybe return the userVO instead of just the user ID

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
    if(null == token || token.isEmpty()) 
    {
      throw new NotAuthorizedException("User is not logged in.");
    }

    // if the token is not a bearer token, throw not authorized
    else if(!token.startsWith("Bearer ")) 
    {
      throw new NotAuthorizedException("User auth token is invalid");
    }

    // get the token without the bearer prefix
    String jwsTokenString = token.substring(7);

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

    String userId = jwsTokenClaims.getBody().getSubject();
    String uuid = jwsTokenClaims.getBody().getId();
    Date expiration = jwsTokenClaims.getBody().getExpiration();
    Date issued = jwsTokenClaims.getBody().getIssuedAt();

    System.out.println("User ID: " + userId);
    System.out.println("UUID: " + uuid);
    System.out.println("Expiration: " + expiration);
    System.out.println("Issued: " + issued);

    // TODO: check the database to see if the token is valid
    // TODO: return the proper user object

    return _userService.retrieveById("pennhess", new SystemToken());

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
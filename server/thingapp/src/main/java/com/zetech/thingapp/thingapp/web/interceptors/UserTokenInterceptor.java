package com.zetech.thingapp.thingapp.web.interceptors;

import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.lang.Nullable;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.constants.SpecialUsers;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.UserAuthTokenVO;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.SystemToken;
import com.zetech.thingapp.thingapp.security.UserToken;
import com.zetech.thingapp.thingapp.service.AuthServiceInterface;
import com.zetech.thingapp.thingapp.service.SecurityServiceInterface;

/*
 * This is the core piece to all of our applications security
 * This class intercepts every requests and ensure's that the user is properly authenticated
 * It returns the "User Token" which is used by the rest of the application to identify the user making the request
 */

// TODO: Think about just moving this to security for simplicity of folder structure

@Component
public class UserTokenInterceptor implements HandlerInterceptor
{
  @Autowired
  private SecurityServiceInterface _securityService;

  @Autowired
  private AuthServiceInterface _authService;

  //TODO: Source code I found this in had @Overrides statements above these methods
  // Figure out how required those are or why I don't seem to need them

  /*
   * Security Notes (for HIGH security):
   *  - Create a /auth/token endpoint that generates a token when given a email and password
   *    - Token has a userID, a tokenId, an ipAddress, an expirationDtg and a createdDtg
   *    - timeout of 5 minutes that is tracked via an auth_token database if unused
   *  - The user token interceptor will ALWAYS FULLY validate the token (as in all fields against the db)
   *    - This way someone can't re-generate tokens forever even if they get the token generation secret (AKA the server cert)
   *  - The difference between the auth_token and the auth_token database is that the database has a "last_used" flag with a DTG of when it was last used
   *    - This helps us track the 5 min inactive early timeout function
   *  - To "invalidate" an auth_token we just delete it from the database
   * **** TODO: When used on a profitable production system ****
   *    - We should probably shift our token strategy to fit openID connect at some point
   *    - This is not because it's more secure but instead because it makes us more compliant when integrating with other servers and api's
   *    - That said, I would avoid revamping this until that day comes because IMO OpenID connect is a poorly thought out standard
   * ***********************************************************
   *    - There's a good article on making JWTs more secure here https://pragmaticwebsecurity.com/articles/apisecurity/hard-parts-of-jwt.html
   *      - Notes from this article:
   *        - It's a good idea to be able to invalidate users regardless of their token, probably by deactivating their account in the event of "suspicious activity" thorough the "is_active" key
   *          - This actually lives in the user table as an "active" flag
   *        - Client side tokens should be stored as cookies
   *        - TLS is obviously a requirement
   *    - Another interesting article here https://www.jessym.com/articles/stateless-oauth2-social-logins-with-spring-boot#stateless-sessions-recurring
   *    - Another interesting read: https://blog.logrocket.com/jwt-authentication-best-practices/
   *  - Security is hard...
  */

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ThingAppException
  {
    // check if the user is trying to log in, if so allow them to hit the auth endpoint without generating a user token
    String uri = request.getRequestURI();
    String clientIp = request.getRemoteAddr();

    // Call the authentication service
    try
    {
      String bearerToken = request.getHeader("Authorization");
      UserAuthTokenVO userAuthToken = _authService.authenticate(bearerToken, clientIp);
      
      String userId = userAuthToken.getUserId();
      String tokenId = userAuthToken.getUuid();

      Set<ApplicationRoles> roles = _securityService.authorize(userId, new SystemToken());
      
      // NOTE for a business, email would actually be an employee ID
      // employee ID and roles would come from PKI
      // having PKI makes authentication WAAAAAAY easier, I truly don't know why the internet didn't ever enforce PKI as a standard
      UserToken token = new UserToken(userId, tokenId, clientIp, roles);

      request.getSession().setAttribute("TOKEN", token);
      return true;
    }
    // if something goes wrong check for a public URI before throwing the error
    catch (ThingAppException e)
    {
      if(isPublic(uri) || request.getMethod().contentEquals("OPTIONS")) 
      {
        UserToken token = new UserToken(SpecialUsers.UNAUTHENTICATED_USER.toString(), clientIp, null);
        request.getSession().setAttribute("TOKEN", token);
        return true;
      }
      else
      {
        throw e;
      }
    }
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws ThingAppException {}

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable Exception exception) throws ThingAppException {}

  private boolean isPublic(String uri)
  {

    // list of public URIs
    String[] publicUris = {
      "/thingapp/api/auth/token",
      "/thingapp/api-docs",
      "/thingapp/v3/api-docs",
      "/thingapp/v3/api-docs/.*",
      "/thingapp/swagger-ui/.*",
      "/thingapp/error"
    };

    // build the regex string to check for a matching URI
    String publicUriRegex = "(?:)";

    for (String publicUri : publicUris) 
    {
      publicUriRegex += "|(?:" + publicUri + ")";
    }

    // return whether the provided URI mataches a public URI or not
    return uri.matches(publicUriRegex);

  }
}

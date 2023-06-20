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
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
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
   *  - Create a /auth/token endpoint that generates a token when given a username and password
   *    - Token has a userID, a tokenId, an ipAddress and a createdDtg
   *    - timeout of 15 minutes that is tracked via an auth_token database
   *  - The user token interceptor should ALWAYS FULLY validate the token (as in all fields against the db)
   *    - This way someone can't re-generate tokens forever even if they get the token generation secret
   *  - The difference between the auth_token and the auth_token database is that the database has a "last_used" flag with a DTG of when it was last used
   *    - This helps us track the 15 min inactive early timeout function
   *  - To "invalidate" an auth_token we just delete it from the database
   * **** The following is too secure ****
   *    - If I wanted to be crazy I could also roll the token generation secret every 24 hours and have it live in the database
   *      - The downside to this is then it also has to be queried with the user every call
   *      - I could also do something where the token generation secret is it's own token that expires every 24 hours that is saved in the user table with each user
   *      - but then I would need a way to know which user was making the request so I'd have to add like a user-id to the header or something
   * *************************************
   *    - I should at least set up a schedule for rolling the oauth token secret
   * **** TODO: When making lots of money ****
   *    - We should probably shift our token strategy to fit openID connect at some point
   *    - This is not because it's more secure but instead because it makes us more compliant when integrating with other servers and api's
   *    - That said, I would avoid revamping this until that day comes because IMO OpenID connect is a poorly thought out standard
   * *****************************************
   *    - There's a good article on making JWTs more secure here https://pragmaticwebsecurity.com/articles/apisecurity/hard-parts-of-jwt.html
   *      - Notes from this article:
   *        - It's a good idea to be able to invalidate users regardless of their token, probably by deactivating their account in the event of "suspicious activity" thorugh an "is_active" key
   *          - This would actually live in the user table as an "active" flag
   *        - Client side tokens should be stored as cookies
   *        - TLS should obviously be a requirement
   *    - Another interesting article here https://www.jessym.com/articles/stateless-oauth2-social-logins-with-spring-boot#stateless-sessions-recurring
   *  - Security is hard...
  */

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ThingAppException
  {
    // check if the user is trying to log in, if so allow them to hit the auth endpoint without generating a user token
    String uri = request.getRequestURI();
    String clientIp = request.getRemoteAddr();
    if(isPublic(uri)) 
    {
      UserToken token = new UserToken("UNAUTHENTICATED_USER", clientIp, null);
      request.getSession().setAttribute("TOKEN", token);
      return true;
    }
    else
    {
      // TODO: investigate this to make sure we don't get 500s somewhere instead of 401s
      // Call the authentication service
      String userId = _authService.authenticate(request.getHeader("Authorization"));

      Set<ApplicationRoles> roles = _securityService.authorize("test-user@thingapp.com", new SystemToken());
      
      // NOTE for a business email would actually be an employee ID
      // employee ID and roles would com from PKI
      UserToken token = new UserToken(userId, clientIp, roles);

      request.getSession().setAttribute("TOKEN", token);
      return true;
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

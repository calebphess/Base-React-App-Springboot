package com.zetech.thingapp.thingapp.web.interceptors;

import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.lang.Nullable;

import com.zetech.thingapp.thingapp.biz.SecurityServiceInterface;
import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.security.SystemToken;
import com.zetech.thingapp.thingapp.security.UserToken;

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

  //TODO: Source code I found this in had @Overrides statements above these methods
  // Figure out how required those are or why I don't seem to need them

  /*
   * Security Notes:
   *  - Create a /token/get endpoint that generates a token when given a username and password
   *    - Token has a timeout of 15 minutes as well as a userID
   *  - Create a /token/refresh endpoint that takes a token and generates a new token IFF the last login date isn't older than 24 hours
   *  - The user token interceptor should ALWAYS validate the token AND should ALWAYS validate the last login date
   *    - This way someone can't re-generate tokens forever even if they get the token generation secret
   *    - If I wanted to be crazy I could also roll the token generation secret every 24 hours and have it live in the database
   *      - The downside to this is then it also has to be queried with the user every call
   *      - I could also do something where the token generation secret is it's own token that expires every 24 hours that is saved in the user table with each user
   *      - but then I would need a way to know which user was making the request so I'd have to add like a user-id to the header or something
   *    - I should at least set up a schedule for rolling the oauth token secret
   *    - There's a good article on making JWTs more secure here https://pragmaticwebsecurity.com/articles/apisecurity/hard-parts-of-jwt.html
   *      - Notes from this article:
   *        - It's a good idea to be able to invalidate users regardless of their token, probably by deactivating their account in the event of "suspicious activity" thorugh an "is_active" key
   *          - Not a flag so you can store the key in the token and invalidate it when the key is null or is different
   *        - Client side tokens should be stored as cookies
   *        - TLS should be a requirement
   *    - Another interesting article here https://www.jessym.com/articles/stateless-oauth2-social-logins-with-spring-boot#stateless-sessions-recurring
   *  - Security is hard...
  */

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  {
    Set<ApplicationRoles> roles = _securityService.authorize("test-user@thingapp.com", new SystemToken());
    
    // NOTE for a business email would actually be an employee ID
    // employee ID and roles would com from PKI
    UserToken token = new UserToken("test-user@thingapp.com", roles);

    request.getSession().setAttribute("TOKEN", token);
    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {}

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable Exception exception) throws Exception {}
}

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

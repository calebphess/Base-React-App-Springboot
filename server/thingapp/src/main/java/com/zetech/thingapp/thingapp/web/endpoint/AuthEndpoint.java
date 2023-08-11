package com.zetech.thingapp.thingapp.web.endpoint;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.AuthResponseVO;
import com.zetech.thingapp.thingapp.security.UserToken;
import com.zetech.thingapp.thingapp.service.AuthServiceInterface;

// This is the core authentication endpoint
// This endpoint is different from the others as it's the only service that you don't need a user token to call
// This is because this endpoint creates an auth token by "logging you in" so that future requests can create the user token
@RestController
public class AuthEndpoint extends BaseRESTEndpoint
{
  @Autowired
  private AuthServiceInterface _service;
  
  @PostMapping("/api/auth/token")
  public AuthResponseVO login(@RequestBody AuthRequestVO authRequestVO, HttpServletRequest request) throws ThingAppException
  {
    try
    {
      // This is a special service that doesn't create a real user token because the user shouldn't be logged in yet
      UserToken token = getToken(request);
      AuthResponseVO encryptedAuthToken = _service.login(authRequestVO, token);
      
      // audit success
      auditCreateSuccess("Created auth token (AKA logged in) for user: " + authRequestVO.getUserLoginId(), request);
      return encryptedAuthToken;
    }
    catch (ThingAppException e)
    {
      // audit fail
      auditCreateError("Error creating auth token for user: " + authRequestVO.getUserLoginId(), e, request);
      throw e;
    }
  }

  @DeleteMapping("/api/auth/logout")
  public void logout(HttpServletRequest request) throws ThingAppException
  {
    try
    {
      // delete the user token to log out the user
      UserToken token = getToken(request);
      _service.logout(token);
      
      // audit success
      auditDeleteSuccess("Deleted auth token (AKA logged out) for user: " + token.getUserId(), request);
    }
    catch (ThingAppException e)
    {
      // audit fail
      auditCreateError("Error deleting auth token from address: " + request.getRemoteAddr(), e, request);
      throw e;
    }
  }

  @DeleteMapping("/api/auth/logout/everywhere")
  public void logoutEverywhere(HttpServletRequest request) throws ThingAppException
  {
    try
    {
      // delete the user token to log out the user
      UserToken token = getToken(request);
      _service.logoutEverywhere(token);
      
      // audit success
      auditDeleteSuccess("Deleted all auth tokens (AKA logged out everywhere) for user: " + token.getUserId(), request);
    }
    catch (ThingAppException e)
    {
      // audit fail
      auditCreateError("Error deleting all auth tokens from address: " + request.getRemoteAddr(), e, request);
      throw e;
    }
  }
  
}

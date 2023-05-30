package com.zetech.thingapp.thingapp.web.endpoint;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zetech.thingapp.thingapp.biz.AuthServiceInterface;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.EncryptedAuthTokenVO;

// This is the core authentication endpoint
// This endpoint is different from the others as it's the only service that you don't need a user token to call
// This is because this endpoint creates an auth token by "logging you in" so that future requests can create the user token
@RestController
public class AuthTokenEndpoint extends BaseRESTEndpoint
{
  @Autowired
  private AuthServiceInterface _service;
  
  @PostMapping("/api/auth/token")
  public EncryptedAuthTokenVO create(@RequestBody AuthRequestVO authRequestVO, HttpServletRequest request) throws ThingAppException
  {
    try
    {
      // This is a special service that doesn't create a user token because we don't have a token to be handled by the UserTokenInterceptor yet
      EncryptedAuthTokenVO encryptedAuthToken = _service.create(authRequestVO, request);
      // audit success
      auditCreateSuccess("Created auth token for user: " + authRequestVO.getUserLoginId(), request);
      return encryptedAuthToken;
    }
    catch (ThingAppException e)
    {
      // audit fail
      auditCreateError("Error creating auth token for user: " + authRequestVO.getUserLoginId(), e, request);
      throw e;
    }
  }
  
}

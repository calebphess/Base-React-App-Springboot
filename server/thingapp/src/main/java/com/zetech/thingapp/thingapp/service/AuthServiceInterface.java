package com.zetech.thingapp.thingapp.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.AuthResponseVO;
import com.zetech.thingapp.thingapp.model.UserAuthTokenVO;
import com.zetech.thingapp.thingapp.security.UserToken;

/*
 * This is the important service that handles first time user authentication
 * This is for username/password applications and generates an auth token based on the given username and password
 */


public interface AuthServiceInterface
{
  @Transactional(rollbackFor = Throwable.class)
  AuthResponseVO login(AuthRequestVO record,  UserToken token) throws ThingAppException;

  // We don't want this as transactional as it should always invalidate the user when in doubt
  int logout(UserToken token) throws ThingAppException;

  // We don't want this as transactional as it should always invalidate the user when in doubt
  int logoutEverywhere(UserToken token) throws ThingAppException;

  // TODO: figure out how to make this transactional for all but unauthorized exceptions
  UserAuthTokenVO authenticate(String bearerToken, String ipAddress) throws ThingAppException;

}
package com.zetech.thingapp.thingapp.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.AuthResponseVO;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.UserToken;

/*
 * This is the important service that handles first time user authentication
 * This is for username/password applications and generates an auth token based on the given username and password
 */


public interface AuthServiceInterface
{
  @Transactional(rollbackFor = Throwable.class)
  AuthResponseVO login(AuthRequestVO record,  HttpServletRequest request) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int invalidate(UserToken token,  HttpServletRequest request) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserVO authenticate(String token) throws ThingAppException;

}
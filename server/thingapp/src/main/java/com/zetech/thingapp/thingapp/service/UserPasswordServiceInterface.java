package com.zetech.thingapp.thingapp.service;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.UserPasswordRequestVO;
import com.zetech.thingapp.thingapp.model.UserPasswordVO;
import com.zetech.thingapp.thingapp.security.UserToken;

public interface UserPasswordServiceInterface 
{
  @Transactional(readOnly = true)
  UserPasswordVO retrieveByEmail(String email, UserToken token) throws ThingAppException;

  // we only want to return the password if it was explicitly requested
  // if it fails it will throw an exception
  @Transactional(rollbackFor = Throwable.class)
  void create(UserPasswordRequestVO record, UserToken token) throws ThingAppException;

  // we only want to return the password if it was explicitly requested
  // if it fails it will throw an exception
  @Transactional(rollbackFor = Throwable.class)
  void update(UserPasswordRequestVO record, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int delete(UserPasswordVO record, UserToken token) throws ThingAppException;
}

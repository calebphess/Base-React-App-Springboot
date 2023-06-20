package com.zetech.thingapp.thingapp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.UserToken;

public interface UserServiceInterface 
{
  @Transactional(readOnly = true)
  List<UserVO> retrieveAll(UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  UserVO retrieveById(String userId, UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  UserVO retrieveByGoogleId(String googleId, UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  UserVO retrieveByEmail(String email, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserVO create(UserVO record, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserVO update(UserVO record, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserVO updateLastLogin(UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int delete(UserVO record, UserToken token) throws ThingAppException;
}

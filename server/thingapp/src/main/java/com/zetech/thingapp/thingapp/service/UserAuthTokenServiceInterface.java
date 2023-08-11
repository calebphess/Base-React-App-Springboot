package com.zetech.thingapp.thingapp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.UserAuthTokenVO;
import com.zetech.thingapp.thingapp.security.UserToken;

public interface UserAuthTokenServiceInterface 
{
  @Transactional(readOnly = true)
  List<UserAuthTokenVO> retrieveAll(UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  List<UserAuthTokenVO> retrieveAllForUserId(String userId, UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  UserAuthTokenVO retrieveByUuid(String uuid, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserAuthTokenVO create(UserAuthTokenVO record, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserAuthTokenVO updateLastActive(UserAuthTokenVO record, UserToken token) throws ThingAppException;

  // We don't want this as transactional as it should always delete the token when in doubt
  int delete(UserAuthTokenVO record, UserToken token) throws ThingAppException;

  // We don't want this as transactional as it should always delete the tokens when in doubt
  int deleteAllForUser(String userId, UserToken token) throws ThingAppException;
}

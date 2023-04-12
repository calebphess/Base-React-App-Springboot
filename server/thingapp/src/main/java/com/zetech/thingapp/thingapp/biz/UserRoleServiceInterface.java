package com.zetech.thingapp.thingapp.biz;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.UserRoleVO;
import com.zetech.thingapp.thingapp.security.UserToken;

public interface UserRoleServiceInterface 
{
  @Transactional(readOnly = true)
  List<UserRoleVO> retrieveFor(String ain, UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  UserRoleVO retrieveById(Long id, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  UserRoleVO create(UserRoleVO record, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int delete(UserRoleVO record, UserToken token) throws ThingAppException;
}

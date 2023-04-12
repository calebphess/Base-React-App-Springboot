package com.zetech.thingapp.thingapp.dal.interfaces;

import java.util.List;

import com.zetech.thingapp.thingapp.model.UserRoleVO;

public interface UserRoleDAOInterface
{
  List<UserRoleVO> retrieveFor(String email);

  UserRoleVO retrieveById(Long id);

  int create(UserRoleVO record);

  int delete(UserRoleVO record);
}

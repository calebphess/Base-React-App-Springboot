package com.zetech.thingapp.thingapp.dal.interfaces;

import java.util.List;

import com.zetech.thingapp.thingapp.model.UserAuthTokenVO;

public interface UserAuthTokenDAOInterface 
{
  List<UserAuthTokenVO> retrieveAll();

  List<UserAuthTokenVO> retrieveAllForUserId(String userId);

  UserAuthTokenVO retrieveByUuid(String id);

  int create(UserAuthTokenVO record);

  int update(UserAuthTokenVO record);

  int delete(UserAuthTokenVO record);

  int deleteAllForUser(String userId);
}

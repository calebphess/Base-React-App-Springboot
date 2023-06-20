package com.zetech.thingapp.thingapp.dal.interfaces;

import java.util.List;

import com.zetech.thingapp.thingapp.model.UserVO;

public interface UserDAOInterface 
{
  List<UserVO> retrieveAll();

  UserVO retrieveById(String id);

  UserVO retrieveByGoogleId(String googleId);

  UserVO retrieveByEmail(String email);

  int create(UserVO record);

  int update(UserVO record);

  int delete(UserVO record);
}

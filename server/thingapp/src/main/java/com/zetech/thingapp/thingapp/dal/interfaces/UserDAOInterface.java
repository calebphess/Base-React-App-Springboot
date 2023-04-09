package com.zetech.thingapp.thingapp.dal.interfaces;

import java.util.List;

import com.zetech.thingapp.thingapp.model.UserVO;

public interface UserDAOInterface {
  List<UserVO> retrieveAll();

  UserVO retrieveByGoogleId(String googleId);

  UserVO retrieveByEmail(String email);

  int create(UserVO record);

  int updateLastLogin(UserVO record);
}

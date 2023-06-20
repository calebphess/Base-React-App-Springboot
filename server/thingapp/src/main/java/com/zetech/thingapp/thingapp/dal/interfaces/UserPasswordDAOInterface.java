package com.zetech.thingapp.thingapp.dal.interfaces;

import com.zetech.thingapp.thingapp.model.UserPasswordVO;

public interface UserPasswordDAOInterface 
{
  UserPasswordVO retrieveByEmail(String email);

  int create(UserPasswordVO record);

  int update(UserPasswordVO record);

  int delete(UserPasswordVO record);
}

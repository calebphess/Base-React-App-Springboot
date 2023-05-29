package com.zetech.thingapp.thingapp.model;


public class AuthRequestVO
{
  private String _userLoginId;
  private String _userPassword;

  public String getUserLoginId()
  {
    return _userLoginId;
  }

  public void setUserLoginId(String userLoginId)
  {
    _userLoginId = userLoginId;
  }

  public String getUserPassword()
  {
    return _userPassword;
  }

  public void setUserPassword(String userPassword)
  {
    _userPassword = userPassword;
  }

}
package com.zetech.thingapp.thingapp.model;


public class AuthResponseVO
{
  private String _accessToken;
  private int _expires;
  private String _tokenType;

  public String getAccessToken()
  {
    return _accessToken;
  }

  public void setAccessToken(String accessToken)
  {
    _accessToken = accessToken;
  }

  public int getExpires()
  {
    return _expires;
  }

  public void setExpires(int expires)
  {
    _expires = expires;
  }

  public String getTokenType()
  {
    return _tokenType;
  }

  public void setTokenType(String tokenType)
  {
    _tokenType = tokenType;
  }

}
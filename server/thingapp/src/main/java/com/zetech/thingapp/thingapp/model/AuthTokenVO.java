package com.zetech.thingapp.thingapp.model;

import java.util.Date;

public class AuthTokenVO 
{
  private String _userId;
  private String _uuid;
  private String _ipAddress;
  private Date _issuedDtg;
  private Date _expirationDtg;

  public String getUuid()
  {
    return _uuid;
  }

  public void setUuid(String uuid)
  {
    _uuid = uuid;
  }

  public String getUserId()
  {
    return _userId;
  }

  public void setUserId(String userId)
  {
    _userId = userId;
  }

  public String getIpAddress()
  {
    return _ipAddress;
  }

  public void setIpAddress(String ipAddress)
  {
    _ipAddress = ipAddress;
  }

  public Date getIssuedDtg()
  {
    return _issuedDtg;
  }

  public void setIssuedDtg(Date issuedDtg)
  {
    _issuedDtg = issuedDtg;
  }

  public Date getExpirationDtg()
  {
    return _expirationDtg;
  }

  public void setExpirationDtg(Date expirationDtg)
  {
    _expirationDtg = expirationDtg;
  }
}

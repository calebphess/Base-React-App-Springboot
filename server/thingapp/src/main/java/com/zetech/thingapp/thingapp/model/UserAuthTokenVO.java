package com.zetech.thingapp.thingapp.model;

import java.util.Date;

public class UserAuthTokenVO 
{
  private String _uuid;
  private String _userId;
  private Date _issuedDtg;
  private Date _expirationDtg;
  private Date _lastActiveDtg;
  private String _clientIpAddress;
  private Long _version;

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

  public Date getLastActiveDtg()
  {
    return _lastActiveDtg;
  }

  public void setLastActiveDtg(Date lastActiveDtg)
  {
    _lastActiveDtg = lastActiveDtg;
  }

  public String getClientIpAddress()
  {
    return _clientIpAddress;
  }

  public void setClientIpAddress(String clientIpAddress)
  {
    _clientIpAddress = clientIpAddress;
  }

  public Long getVersion() 
  {
    return _version;
  }

  public void setVersion(Long version) 
  {
    _version = version;
  }

}

package com.zetech.thingapp.thingapp.model;

import java.util.Date;

public class UserVO 
{
  private String _userId;
  private String _email;
  private String _googleId;
  private Date _lastLoginDtg;
  private boolean _isActive;
  private Date _createdDtg;
  private Date _updatedDtg;
  private Long _version;

  public String getUserId()
  {
    return _userId;
  }

  public void setUserId(String userId)
  {
    _userId = userId;
  }

  public String getEmail()
  {
    return _email;
  }

  public void setEmail(String email)
  {
    _email = email;
  }

  public String getGoogleId()
  {
    return _googleId;
  }

  public void setGoogleId(String googleId)
  {
    _googleId = googleId;
  }

  public Date getLastLoginDtg()
  {
    return _lastLoginDtg;
  }

  public void setLastLoginDtg(Date lastLoginDtg)
  {
    _lastLoginDtg = lastLoginDtg;
  }

  public Date getCreatedDtg()
  {
    return _createdDtg;
  }

  public void setCreatedDtg(Date createdDtg)
  {
    _createdDtg = createdDtg;
  }

  public Date getUpdateDtg()
  {
    return _updatedDtg;
  }

  public void setUpdatedDtg(Date updatedDtg)
  {
    _updatedDtg = updatedDtg;
  }

  public Long getVersion()
  {
    return _version;
  }

  public void setVersion(Long version) 
  {
    _version = version;
  }

  public boolean isActive()
  {
    return _isActive;
  }

  public void setActive(boolean isActive)
  {
    _isActive = isActive;
  }
}

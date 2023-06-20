package com.zetech.thingapp.thingapp.model;

import java.util.Date;

public class UserPasswordVO 
{
  private String _email;
  private String _passwordHash;
  private int _loginAttempts;
  private boolean _resetRequired;
  private Date _createdDtg;
  private Date _updatedDtg;
  private Long _version;

  public String getEmail()
  {
    return _email;
  }

  public void setEmail(String email)
  {
    _email = email;
  }

  public String getPasswordHash()
  {
    return _passwordHash;
  }

  public void setPasswordHash(String passwordHash)
  {
    _passwordHash = passwordHash;
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

  public int getLoginAttempts()
  {
    return _loginAttempts;
  }

  public void setLoginAttempts(int loginAttempts)
  {
    _loginAttempts = loginAttempts;
  }

  public boolean isResetRequired()
  {
    return _resetRequired;
  }

  public void setResetRequired(boolean resetRequired)
  {
    _resetRequired = resetRequired;
  }
}

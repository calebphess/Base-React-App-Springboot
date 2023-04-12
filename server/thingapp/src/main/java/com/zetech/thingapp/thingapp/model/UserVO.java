package com.zetech.thingapp.thingapp.model;

import java.util.Date;

// TODO: convert passwordDate and DOB to just be DATE and not DATETIME

public class UserVO {
  private String _email;
  private String _googleId;
  private String _firstName;
  private String _lastName;
  private Date _dateOfBirth;
  private String _passwordHash;
  private Date _passwordDate;
  private String _profilePhoto;
  private Date _lastLoginDtg;
  private Date _createdDtg;
  private Date _updatedDtg;
  private String _updatedEmail;
  private Long _version;

  public String getPasswordHash()
  {
    return _passwordHash;
  }

  public void setPasswordHash(String passwordHash)
  {
    _passwordHash = passwordHash;
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

  public String getFirstName()
  {
    return _firstName;
  }

  public void setFirstName(String firstName)
  {
    _firstName = firstName;
  }

  public String getLastName()
  {
    return _lastName;
  }

  public void setLastName(String lastName)
  {
    _lastName = lastName;
  }

  public Date getDateOfBirth()
  {
    return _dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth)
  {
    _dateOfBirth = dateOfBirth;
  }

  public String getProfilePhoto()
  {
    return _profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto)
  {
    _profilePhoto = profilePhoto;
  }

  public Date getPasswordDate()
  {
    return _passwordDate;
  }

  public void setPasswordDate(Date passwordDate)
  {
    _passwordDate = passwordDate;
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

  public String getUpdatedEmail()
  {
    return _updatedEmail;
  }

  public void setUpdatedEmail(String updatedEmail)
  {
    _updatedEmail = updatedEmail;
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

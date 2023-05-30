package com.zetech.thingapp.thingapp.model;

import java.util.Date;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;

public class UserRoleVO
{
  private Long _id;
  private String _userId;
  private ApplicationRoles _role;
  private String _createdUserId;
  private Date _createdDtg;

  public Long getId()
  {
    return _id;
  }

  public void setId(Long id)
  {
    _id = id;
  }

  public String getUserId()
  {
    return _userId;
  }

  public void setUserId(String userId)
  {
    _userId = userId;
  }

  public ApplicationRoles getRole()
  {
    return _role;
  }

  public void setRole(String role)
  {
//    try
//    {
    _role = ApplicationRoles.valueOf(role);
//    }
//    catch (Throwable e)
//    {
//      e.printStackTrace();
//      _role = null;
//    }
// TODO: Ask about this

  }

  public void setRole(ApplicationRoles role)
  {
    _role = role;
  }

  public String getCreatedUserId()
  {
    return _createdUserId;
  }

  public void setCreatedUserId(String createdUserId)
  {
    _createdUserId = createdUserId;
  }

  public Date getCreatedDtg()
  {
    return _createdDtg;
  }

  public void setCreatedDtg(Date createdDtg)
  {
    _createdDtg = createdDtg;
  }

}


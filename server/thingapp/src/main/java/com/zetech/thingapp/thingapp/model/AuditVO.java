package com.zetech.thingapp.thingapp.model;

import java.util.Date;

import com.zetech.thingapp.thingapp.constants.CRUDOperation;

public class AuditVO
{
  private Long _id;
  private String _url;
  private String _email;
  private String _roles;
  private CRUDOperation _operation;
  private String _auditJson;
  private Date _eventDtg;
  private Date _reviewedDtg;
  private String _reviewerEmail;

  public Long getId()
  {
    return _id;
  }

  public void setId(Long id)
  {
    _id = id;
  }

  public String getUrl()
  {
    return _url;
  }

  public void setUrl(String url)
  {
    _url = url;
  }

  public String getEmail()
  {
    return _email;
  }

  public void setEmail(String email)
  {
    _email = email;
  }

  public String getRoles()
  {
    return _roles;
  }

  public void setRoles(String roles)
  {
    _roles = roles;
  }

  public CRUDOperation getOperation()
  {
    return _operation;
  }

  public void setOperation(CRUDOperation operation)
  {
    _operation = operation;
  }

  public String getAuditJson()
  {
    return _auditJson;
  }

  public void setAuditJson(String auditJson)
  {
    _auditJson = auditJson;
  }

  public Date getEventDtg()
  {
    return _eventDtg;
  }

  public void setEventDtg(Date eventDtg)
  {
    _eventDtg = eventDtg;
  }

  public Date getReviewedDtg()
  {
    return _reviewedDtg;
  }

  public void setReviewedDtg(Date reviewedDtg)
  {
    _reviewedDtg = reviewedDtg;
  }

  public String getReviewerEmail()
  {
    return _reviewerEmail;
  }

  public void setReviewerEmail(String reviewerEmail)
  {
    _reviewerEmail = reviewerEmail;
  }

}

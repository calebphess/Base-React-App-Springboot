package com.zetech.thingapp.thingapp.web.endpoint;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuditVO;
import com.zetech.thingapp.thingapp.service.AuditServiceInterface;

@RestController
public class AuditEndpoint extends BaseRESTEndpoint
{
  @Autowired
  private AuditServiceInterface _service;

  @GetMapping("/api/audit")
  public List<AuditVO> retrieveAll(HttpServletRequest request) throws ThingAppException
  {
    try
    {
      // call service
      List<AuditVO> data = _service.retrieveAll(getToken(request));
      // audit success
      // NOTE: if we return all audit and then audit it, we'll be exponential growth
//      auditRetrieveAllSuccess(new LinkedList<AuditVO>(), request);
      // return value
      return data;
    }
    catch (Throwable e)
    {
      // audit error
      auditRetrieveAllError(e, request);
      throw e;
    }
  }

  @GetMapping("/api/audit/{id}")
  public AuditVO retrieveById(@PathVariable("id") Long id, HttpServletRequest request) throws ThingAppException
  {
    try
    {
      AuditVO data = _service.retrieveById(id, getToken(request));
      auditRetrieveByIdSuccess(id, data, request);
      return data;
    }
    catch (Throwable e)
    {
      auditRetrieveByIdError(id, e, request);
      throw e;
    }
  }

  @PutMapping("/api/audit/{id}")
  public AuditVO review(@RequestBody AuditVO record, HttpServletRequest request) throws ThingAppException
  {
    try
    {
      AuditVO data = _service.review(record, getToken(request));
      // for now lets not audit reviewing
      // auditUpdateSuccess(data, request);
      return data;
    }
    catch (Throwable e)
    {
      auditUpdateError(record, e, request);
      throw e;
    }
  }

  @PutMapping("/api/audit")
  public int reviewAll(HttpServletRequest request) throws ThingAppException
  {
    try
    {
      int count = _service.reviewAll(getToken(request));
      // for now lets not audit reviewing
//      auditUpdateSuccess(new Integer(count), request);
      return count;
    }
    catch (Throwable e)
    {
      auditUpdateError(null, e, request);
      throw e;
    }
  }

  @DeleteMapping("/api/audit/reviewed")
  public void deleteReviewed(HttpServletRequest request) throws ThingAppException
  {
    try
    {
      int num = _service.deleteReviewed(getToken(request));
      auditDeleteSuccess("reviewed: " + num, request);
    }
    catch (Throwable e)
    {
      auditDeleteError("reviewed", e, request);
      throw e;
    }
  }

  @DeleteMapping("/api/audit")
  public void deleteAll(HttpServletRequest request) throws ThingAppException
  {
    try
    {
      int num = _service.deleteAll(getToken(request));
      auditDeleteSuccess("all: " + num, request);
    }
    catch (Throwable e)
    {
      auditDeleteError("all", e, request);
      throw e;
    }
  }

}
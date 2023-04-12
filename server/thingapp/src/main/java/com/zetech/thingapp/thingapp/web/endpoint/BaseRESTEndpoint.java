package com.zetech.thingapp.thingapp.web.endpoint;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zetech.thingapp.thingapp.biz.AuditServiceInterface;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.exceptions.ValidationFailedException;
import com.zetech.thingapp.thingapp.security.UserToken;

/*
 * This is the foundation for all of our REST enpoints
 * This class handles audit, user token management, and Exception translation
 */

public class BaseRESTEndpoint
{
  @Autowired
  private AuditServiceInterface _auditService;

  public BaseRESTEndpoint()
  {
  }

  protected UserToken getToken(HttpServletRequest request) throws ThingAppException
  {
    return (UserToken) request.getSession().getAttribute("TOKEN");
  }

  private String makeJSON(Object o) throws JsonProcessingException
  {
    ObjectMapper mapper = new ObjectMapper();

    // Object to JSON in String
    return mapper.writeValueAsString(o);
  }

  @ExceptionHandler(Throwable.class)
  public void handleException(Throwable e, HttpServletResponse response, HttpServletRequest request)
      throws IOException, ThingAppException
  {
    e.printStackTrace();
    // TODO handle validation failed by writing validation errors
    if (e instanceof ValidationFailedException)
    {
      ValidationFailedException vfe = (ValidationFailedException) e;
      if (null == vfe.getErrors())
      {
        response.sendError(vfe.httpStatus().value(), e.getMessage());
      }
      else
      {
        System.out.println(makeJSON(vfe.getErrors()));
        response.sendError(vfe.httpStatus().value(), makeJSON(vfe.getErrors()));
      }
    }
    else if (e instanceof ThingAppException)
    {
      ThingAppException te = (ThingAppException) e;
      response.sendError(te.httpStatus().value(), e.getMessage());
      if (e.getCause() instanceof ValidationFailedException)
      {
        ValidationFailedException vfe = (ValidationFailedException) e.getCause();
        System.out.println(makeJSON(vfe.getErrors()));
      }
    }

    else
    {
      // TODO audit uncaught exception
      _auditService.auditUnCaughtError(e, getToken(request), extractUrl(request));
      response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

  }

  @ExceptionHandler(HttpMessageConversionException.class)
  public void handleException(HttpMessageConversionException e, HttpServletResponse response,
      HttpServletRequest request) throws IOException, ThingAppException
  {
    System.out.println("got here: " + e.getMessage());
    handleException(new ValidationFailedException(e.getMessage()), response, request);
  }

  private String extractUrl(HttpServletRequest request)
  {
    return request.getRequestURI();
  }

  protected void auditRetrieveAllSuccess(List<?> records, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditRetrieveAllSuccess(records, getToken(request), extractUrl(request));
  }

  protected void auditRetrieveAllError(Throwable e, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditRetrieveAllError(e, getToken(request), extractUrl(request));
  }

  protected void auditRetrieveForSuccess(Object id, List<?> data, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditRetrieveAllForSuccess(id, data, getToken(request), extractUrl(request));
  }

  protected void auditRetrieveForError(Object id, Throwable e, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditRetrieveAllForError(id, e, getToken(request), extractUrl(request));
  }

  protected void auditRetrieveByIdError(Object id, Throwable e, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditRetrieveByIdError(id, e, getToken(request), extractUrl(request));
  }

  protected void auditRetrieveByIdSuccess(Object id, Object data, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditRetrieveByIdSuccess(id, data, getToken(request), extractUrl(request));
  }

  protected void auditCreateError(Object record, Throwable e, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditCreateError(record, e, getToken(request), extractUrl(request));
  }

  protected void auditCreateSuccess(Object data, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditCreateSuccess(data, getToken(request), extractUrl(request));
  }

  protected void auditUpdateError(Object record, Throwable e, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditUpdateError(record, e, getToken(request), extractUrl(request));
  }

  protected void auditUpdateSuccess(Object data, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditUpdateSuccess(data, getToken(request), extractUrl(request));
  }

  protected void auditDeleteError(Object idOrRec, Throwable e, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditDeleteError(idOrRec, e, getToken(request), extractUrl(request));
  }

  protected void auditDeleteSuccess(Object idOrRec, HttpServletRequest request) throws ThingAppException
  {
    _auditService.auditDeleteSuccess(idOrRec, getToken(request), extractUrl(request));
  }

  protected void auditSearchFail(Object criteria, Throwable e) throws ThingAppException
  {
    _auditService.auditSearchFail(criteria, e);
  }

  protected void auditSearchSuccess(Object criteria, List<?> results) throws ThingAppException
  {
    _auditService.auditSearchSuccess(criteria, results);
  }
}

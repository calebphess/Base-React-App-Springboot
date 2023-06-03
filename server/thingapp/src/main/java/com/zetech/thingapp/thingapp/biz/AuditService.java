package com.zetech.thingapp.thingapp.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.constants.CRUDOperation;
import com.zetech.thingapp.thingapp.dal.interfaces.AuditDAOInterface;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.exceptions.FatalException;
import com.zetech.thingapp.thingapp.exceptions.NoDataFoundException;
import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
import com.zetech.thingapp.thingapp.exceptions.ValidationErrors;
import com.zetech.thingapp.thingapp.exceptions.ValidationFailedException;
import com.zetech.thingapp.thingapp.model.AuditVO;
import com.zetech.thingapp.thingapp.security.UserToken;
import com.zetech.thingapp.thingapp.util.StringContentValidator;

@Service
public class AuditService implements AuditServiceInterface
{

  // NOTE this will need to be re-written either to how you plan to do audit
  // Or if there is an enterprise audit system, this should send the data there
  // So to re-iterate, this is just a STUB
  @Autowired
  private AuditDAOInterface _dao;

  private String makeJSON(Object o) throws JsonProcessingException
  {
    ObjectMapper mapper = new ObjectMapper();

    // Object to JSON in String
    return mapper.writeValueAsString(o);
  }

  private String makeJSON(Throwable e) throws JsonProcessingException
  {
    StringBuilder buf = new StringBuilder();
    buf.append("{");
    buf.append("\"type\":\"" + e.getClass().getName() + "\"");
    buf.append("\"message\":\"" + e.getMessage() + "\"");
    if (null != e.getCause())
    {
      buf.append("\"cause\":" + makeJSON(e.getCause()));
    }
    if (e instanceof ValidationFailedException)
    {
      ValidationFailedException vfe = (ValidationFailedException) e;
      buf.append("\"errors\":" + makeJSON(vfe.getErrors()));
    }
    buf.append("}");
    return buf.toString();
  }

  private String makeJSON(Object id, Object data) throws JsonProcessingException
  {
    return "{ \"id\":" + makeJSON(id) + "\"data\":" + makeJSON(data) + "}";
  }

  private String makeJSON(Throwable e, Object data) throws JsonProcessingException
  {
    return "{ \"error\":" + makeJSON(e) + "\"data\":" + makeJSON(data) + "}";
  }

  @Override
  public void auditUnCaughtError(Throwable error, UserToken token, String url)
  {
    // cant throw or itll create infinite loop
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.UNCAUGHT_ERROR);
      audit.setAuditJson(makeJSON(error));
      create(audit, token);
    }
    catch (Throwable e)
    {
      System.out.println("auditUnCaughtError caught exception ");
      e.printStackTrace();
    }
  }

  @Override
  public void auditRetrieveAllSuccess(List<?> records, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.RETRIEVE_ALL);
      audit.setAuditJson(makeJSON(records));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditRetrieveAllSuccess", e);
    }
  }

  @Override
  public void auditRetrieveAllForSuccess(Object id, List<?> data, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.RETRIEVE_FOR);
      audit.setAuditJson(makeJSON(id, data));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditRetrieveForSuccess", e);
    }
  }

  @Override
  public void auditRetrieveByIdSuccess(Object id, Object data, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.RETRIEVE_BY_ID);
      audit.setAuditJson(makeJSON(id, data));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditRetrieveByIdSuccess", e);
    }
  }

  @Override
  public void auditCreateSuccess(Object data, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.CREATE);
      audit.setAuditJson(makeJSON(data));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditCreateSuccess", e);
    }
  }

  @Override
  public void auditUpdateSuccess(Object data, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.UPDATE);
      audit.setAuditJson(makeJSON(data));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditUpdateSuccess", e);
    }
  }

  @Override
  public void auditDeleteSuccess(Object idOrRec, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.DELETE);
      audit.setAuditJson(makeJSON(idOrRec));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditDeleteSuccess", e);
    }
  }

  @Override
  public void auditUpdateError(Object data, Throwable error, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.UPDATE);
      audit.setAuditJson(makeJSON(error, data));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditUpdateError", e);
    }
  }

  @Override
  public void auditRetrieveAllError(Throwable error, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.RETRIEVE_ALL);
      audit.setAuditJson(makeJSON(error));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditRetrieveAllError", e);
    }
  }

  @Override
  public void auditRetrieveAllForError(Object id, Throwable error, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.RETRIEVE_FOR);
      audit.setAuditJson(makeJSON(error, id));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditRetrieveForError", e);
    }
  }

  @Override
  public void auditRetrieveByIdError(Object id, Throwable error, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.RETRIEVE_BY_ID);
      audit.setAuditJson(makeJSON(error, id));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditRetrieveByIdError", e);
    }
  }

  @Override
  public void auditCreateError(Object data, Throwable error, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.CREATE);
      audit.setAuditJson(makeJSON(error, data));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditCreateError", e);
    }
  }

  @Override
  public void auditDeleteError(Object idOrRec, Throwable error, UserToken token, String url) throws ThingAppException
  {
    try
    {
      AuditVO audit = new AuditVO();
      audit.setUrl(url);
      audit.setUserId(token.getUserId());
      audit.setRoles(token.getRoles().toString());
      audit.setOperation(CRUDOperation.CREATE);
      audit.setAuditJson(makeJSON(error, idOrRec));
      create(audit, token);
    }
    catch (Throwable e)
    {
      throw new FatalException("error in auditDeleteError", e);
    }
  }

  @Override
  public List<AuditVO> retrieveAll(UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("user is not admin");
      }
      return _dao.retrieveAll();
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in retrieveAll", e);
    }
  }

  private AuditVO create(AuditVO record, UserToken token) throws ThingAppException
  {
    try
    {
      // validate data
      ValidationErrors errors = new ValidationErrors();

      StringContentValidator.validate(record.getUrl(), "url", "URL", true, 1000, errors);
      StringContentValidator.validate(record.getUserId(), "userId", "User ID", true, 255, errors);
      StringContentValidator.validate(record.getRoles(), "roles", "Roles", true, 1000, errors);
      StringContentValidator.validate(record.getAuditJson(), "auditJson", "Audit JSON", true, 1000000, errors);
      if (null == record.getOperation())
      {
        errors.addError("operation", "Operation cannot be null");
      }

      if (errors.hasErrors())
      {
        throw new ValidationFailedException(errors, "validation failed");
      }
      record.setEventDtg(new Date());
      // call dao
      int ok = _dao.create(record);
      if (1 != ok)
      {
        throw new FatalException("error on create");
      }
      return record;
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in create", e);
    }
  }

  @Override
  public AuditVO retrieveById(Long id, UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("user is not admin");
      }
      AuditVO data = _dao.retrieveById(id);
      if (null == data)
      {
        throw new NoDataFoundException("no record found for id: " + id);
      }
      return data;
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in retrieveById", e);
    }
  }

  @Override
  public AuditVO review(AuditVO record, UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("user is not admin");
      }

      record.setReviewedDtg(new Date());
      record.setReviewerEmail(token.getUserId());

      int ok = _dao.updateReviewed(record);
      if (1 != ok)
      {
        throw new FatalException("error updating record");
      }
      return retrieveById(record.getId(), token);
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in updateReviewed", e);
    }
  }

  @Override
  public int deleteReviewed(UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("user is not admin");
      }
      return _dao.deleteReviewed();
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in deleteReviewed", e);
    }
  }

  @Override
  public int deleteAll(UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("user is not admin");
      }
      return _dao.deleteAll();
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in deleteAll", e);
    }
  }

  @Override
  public int reviewAll(UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("user is not admin");
      }

      List<AuditVO> allRecs = retrieveAll(token);
      for (AuditVO rec : allRecs)
      {
        review(rec, token);
      }
      return allRecs.size();
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("error in updateReviewed", e);
    }
  }

  @Override
  public void auditSearchSuccess(Object criteria, List<?> results) throws ThingAppException
  {
    System.out.println("missing audit for search success");
  }

  @Override
  public void auditSearchFail(Object criteria, Throwable e) throws ThingAppException
  {
    System.out.println("missing audit for search fail");
  }

}

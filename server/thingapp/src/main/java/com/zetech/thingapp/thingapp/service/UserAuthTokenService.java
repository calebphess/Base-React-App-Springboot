package com.zetech.thingapp.thingapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.dal.interfaces.UserAuthTokenDAOInterface;
import com.zetech.thingapp.thingapp.exceptions.NoDataFoundException;
import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
import com.zetech.thingapp.thingapp.exceptions.OptimisticLockFailedException;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.exceptions.ValidationErrors;
import com.zetech.thingapp.thingapp.exceptions.ValidationFailedException;
import com.zetech.thingapp.thingapp.model.UserAuthTokenVO;
import com.zetech.thingapp.thingapp.security.UserToken;
import com.zetech.thingapp.thingapp.util.StringContentValidator;

@Service
public class UserAuthTokenService implements UserAuthTokenServiceInterface
{
    @Autowired
    private UserAuthTokenDAOInterface _dao;

    @Override
    public List<UserAuthTokenVO> retrieveAll(UserToken token) throws ThingAppException 
    {
      return _dao.retrieveAll();
    }

    @Override
    public List<UserAuthTokenVO> retrieveAllForUserId(String userId, UserToken token) throws ThingAppException 
    {
      if (!token.getUserId().contentEquals(userId) && token.missingRole(ApplicationRoles.ADMINISTRATOR)) 
      {
        throw new NotAuthorizedException("User does not have permission to perform this action");
      }

      return _dao.retrieveAllForUserId(userId);
    }

    @Override
    public UserAuthTokenVO retrieveByUuid(String uuid, UserToken token) throws ThingAppException 
    {
      UserAuthTokenVO user =  _dao.retrieveByUuid(uuid);

      if (null == user)
      {
        throw new NoDataFoundException("User auth token not found with UUID: " + uuid);
      }

      return user;
    }

    @Override
    public UserAuthTokenVO create(UserAuthTokenVO record, UserToken token) throws ThingAppException 
    {
        try
        {
          // validate fields
          ValidationErrors errors = new ValidationErrors();

          StringContentValidator.validate(record.getUuid(), "uuid", "UUID", true, 255, errors);
          StringContentValidator.validate(record.getUserId(), "userId", "User ID", true, 45, errors);
          StringContentValidator.validate(record.getClientIpAddress(), "clientIpAddress", "Client IP Address", true, 50, errors);

          if (null == record.getIssuedDtg())
          {
            errors.addError("issuedDtg", "Issued date cannot be null");
          }

          if (null == record.getExpirationDtg())
          {
            errors.addError("expirationDtg", "Expiration date cannot be null");
          }

          if (null == record.getLastActiveDtg())
          {
            errors.addError("lastActiveDtg", "Last active date cannot be null");
          }

          if (errors.hasErrors())
          {
            throw new ValidationFailedException(errors, "Validation failed creating user auth token");
          }

          // set the defaults
          record.setVersion(Long.valueOf(0));

          // create the record
          int ok = _dao.create(record);

          // returns 1 if the record was created successfully
          if (1 != ok)
          {
              throw new FatalBeanException("Error creating user auth token");
          }

          // return the result
          return retrieveByUuid(record.getUuid(), token);
        }
        catch (Throwable e)
        {
          throw e;
          // throw new FatalBeanException("Error creating user auth token");
        }
    }

    @Override
    public UserAuthTokenVO updateLastActive(UserAuthTokenVO record, UserToken token) throws ThingAppException 
    {

      // only the system can make this call
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR)) 
      {
        throw new NotAuthorizedException("User does not have permission to perform this action");
      }

      UserAuthTokenVO oldRecord = retrieveByUuid(record.getUuid(), token);

      Date now = new Date();

      oldRecord.setLastActiveDtg(now);

      int ok = _dao.update(record);

      if (1 != ok)
      {
        throw new OptimisticLockFailedException("error updating user auth token last active date");
      }

      return retrieveByUuid(record.getUuid(), token);
    }

    @Override
    public int delete(UserAuthTokenVO record, UserToken token) throws ThingAppException 
    {
      try
      {
        return  _dao.delete(record);
      }
      catch (Throwable e)
      {
        throw new FatalBeanException("Error deleting user auth token");
      }
    }

    @Override
    public int deleteAllForUser(String userId, UserToken token) throws ThingAppException 
    {
      if (!token.getUserId().contentEquals(userId) && token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("User does not have permission to perform this action");
      }

      try
      {
        return  _dao.deleteAllForUser(userId);
      }
      catch (Throwable e)
      {
        throw new FatalBeanException("Error deleting user auth tokens for user: " + userId);
      }
    }
    
}

package com.zetech.thingapp.thingapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.dal.interfaces.UserDAOInterface;
import com.zetech.thingapp.thingapp.exceptions.FatalException;
import com.zetech.thingapp.thingapp.exceptions.NoDataFoundException;
import com.zetech.thingapp.thingapp.exceptions.OptimisticLockFailedException;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.exceptions.ValidationErrors;
import com.zetech.thingapp.thingapp.exceptions.ValidationFailedException;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.UserToken;
import com.zetech.thingapp.thingapp.util.StringContentValidator;

@Service
public class UserService implements UserServiceInterface
{
    @Autowired
    private UserDAOInterface _dao;

    @Override
    public List<UserVO> retrieveAll(UserToken token) throws ThingAppException 
    {
      return _dao.retrieveAll();
    }

    @Override
    public UserVO retrieveById(String userId, UserToken token) throws ThingAppException 
    {
      UserVO user =  _dao.retrieveById(userId);

      if (null == user)
      {
        throw new NoDataFoundException("User not found for user ID: " + userId);
      }

      return user;
    }

    @Override
    public UserVO retrieveByGoogleId(String googleId, UserToken token) throws ThingAppException 
    {
      UserVO user =  _dao.retrieveByGoogleId(googleId);

      if (null == user)
      {
        throw new NoDataFoundException("User not found for google ID: " + googleId);
      }

      return user;
    }

    @Override
    public UserVO retrieveByEmail(String email, UserToken token) throws ThingAppException 
    {
      UserVO user =  _dao.retrieveByEmail(email);

      if (null == user)
      {
        throw new NoDataFoundException("User not found for email: " + email);
      }

      return user;
    }

    @Override
    public UserVO create(UserVO record, UserToken token) throws ThingAppException 
    {
        try
        {
          ValidationErrors errors = new ValidationErrors();

          // validate fields
          StringContentValidator.validate(record.getUserId(), "userId", "User ID", true, 45, errors);
          StringContentValidator.validateEmail(record.getEmail(), "email", "Email", true, 255, errors);
          StringContentValidator.validate(record.getGoogleId(), "googleId", "Google ID", false, 255, errors);

          if (errors.hasErrors())
          {
            throw new ValidationFailedException(errors, "Validation failed creating user");
          }

          // set defaults
          Date now = new Date();

          record.setCreatedDtg(now);
          record.setUpdatedDtg(now);
          record.setLastLoginDtg(now);
          record.setActive(true);
          record.setVersion(Long.valueOf(0));

          // create the record
          int ok = _dao.create(record);

          // returns 1 if the record was created successfully
          if (1 != ok)
          {
              throw new FatalBeanException("Error creating user");
          }

          // return the result
          return retrieveById(record.getUserId(), token);
        }
        catch (Throwable e)
        {
          throw new FatalBeanException("Error creating user role");
        }
    }

    // TODO: change this to a lock/unlock account and addGoogleId methods
    @Override
    public UserVO update(UserVO record, UserToken token) throws ThingAppException 
    {
      UserVO oldRecord = retrieveById(record.getUserId(), token);

      if (null == oldRecord)
      {
        throw new NoDataFoundException("User not found for user ID: " + record.getUserId()  + ".");
      }

      ValidationErrors errors = new ValidationErrors();

      // validate fields
      String oldGoogleId = oldRecord.getGoogleId();
      if (null != oldGoogleId && !oldGoogleId.contentEquals(record.getGoogleId()))
      {
        errors.addError("googleId", "Google ID cannot be changed");
      }

      if (errors.hasErrors())
      {
        throw new ValidationFailedException(errors, "Validation failed updating user");
      }

      // update fields
      UserVO updatedRecord = oldRecord;
      updatedRecord.setGoogleId(record.getGoogleId());
      updatedRecord.setActive(record.isActive());

      record.setUpdatedDtg(new Date());

      int ok = _dao.update(record);

      if (1 != ok)
      {
        throw new OptimisticLockFailedException("error updating user, try refreshing and updating again");
      }

      return retrieveById(record.getUserId(), token);
    }

  @Override
  public UserVO updateLastLogin(UserToken token) throws ThingAppException
  {
    try
    {
      UserVO record = retrieveById(token.getUserId(), token);

      record.setLastLoginDtg(new Date());

      int ok = _dao.update(record);

      if (1 != ok)
      {
        throw new FatalException("Error updating last login");
      }

      return record;
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalException("Something went wrong updating last login", e);
    }
  }

    @Override
    public int delete(UserVO record, UserToken token) throws ThingAppException 
    {
      try
      {
        return _dao.delete(record);
      }
      catch (Throwable e)
      {
        throw new FatalBeanException("Error deleting user");
      }
    }
    
}

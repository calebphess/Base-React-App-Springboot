package com.zetech.thingapp.thingapp.service;

import java.util.Date;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.dal.interfaces.UserPasswordDAOInterface;
import com.zetech.thingapp.thingapp.exceptions.NoDataFoundException;
import com.zetech.thingapp.thingapp.exceptions.OptimisticLockFailedException;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.exceptions.ValidationErrors;
import com.zetech.thingapp.thingapp.exceptions.ValidationFailedException;
import com.zetech.thingapp.thingapp.model.UserPasswordRequestVO;
import com.zetech.thingapp.thingapp.model.UserPasswordVO;
import com.zetech.thingapp.thingapp.model.UserVO;
import com.zetech.thingapp.thingapp.security.UserToken;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import at.favre.lib.crypto.bcrypt.BCrypt.Verifyer;

@Service
public class UserPasswordService implements UserPasswordServiceInterface
{
    @Autowired
    private UserPasswordDAOInterface _dao;

    @Autowired
    private UserServiceInterface _userService;

    @Override
    public UserPasswordVO retrieveByEmail(String email, UserToken token) throws ThingAppException 
    {
      UserPasswordVO user =  _dao.retrieveByEmail(email);

      if (null == user)
      {
        throw new NoDataFoundException("User password not found for email: " + email);
      }

      return user;
    }

    @Override
    public void create(UserPasswordRequestVO record, UserToken token) throws ThingAppException 
    {
        try
        {
          ValidationErrors errors = new ValidationErrors();

          UserPasswordVO newRecord = new UserPasswordVO();

          // validate foreign keys
          UserVO user = _userService.retrieveByEmail(record.getEmail(), token);
          if (null == user)
          {
            errors.addError("email", "User not found for email: " + record.getEmail());
            throw new ValidationFailedException(errors, "Validation failed creating password");
          }

          // validate fields
          String validPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}";

          if (!record.getPassword().matches(validPassword))
          {
            errors.addError("password", "Password must be 8-20 characters and contain at least one letter, one number, and one special character");
          }

          if (errors.hasErrors())
          {
            throw new ValidationFailedException(errors, "Validation failed creating user password");
          }

          // set defaults
          // get the date
          Date now = new Date();

          // encrypt the password
          Hasher encrypter = BCrypt.withDefaults();

          String passwordHash = encrypter.hashToString(12, record.getPassword().toCharArray());

          newRecord.setEmail(record.getEmail());
          newRecord.setPasswordHash(passwordHash);
          newRecord.setCreatedDtg(now);
          newRecord.setUpdatedDtg(now);
          newRecord.setResetRequired(false);
          newRecord.setVersion(Long.valueOf(0));

          // create the record
          int ok = _dao.create(newRecord);

          // returns 1 if the record was created successfully
          if (1 != ok)
          {
              throw new FatalBeanException("Error creating user password");
          }
        }
        catch (Throwable e)
        {
          throw new FatalBeanException("Error creating user password");
        }
    }

    // TODO: rework this to handle forgot password requests
    // TODO: rework all services to update version at the service tier
    @Override
    public void update(UserPasswordRequestVO record, UserToken token) throws ThingAppException 
    {
      UserPasswordVO oldRecord = retrieveByEmail(record.getEmail(), token);

      if (null == oldRecord)
      {
        throw new NoDataFoundException("User password not found for email: " + record.getEmail());
      }

      ValidationErrors errors = new ValidationErrors();

      // validate fields
       // validate fields
      String validPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}";
      Verifyer verifier = BCrypt.verifyer();

      boolean passwordMatch = verifier.verify(record.getPassword().toCharArray(), oldRecord.getPasswordHash()).verified;

      if (passwordMatch)
      {
        errors.addError("password", "Password must not match the old password");
      }
      else if (!record.getPassword().matches(validPassword))
      {
        errors.addError("password", "Password must be 8-20 characters and contain at least one letter, one number, and one special character");
      }

      if (errors.hasErrors())
      {
        throw new ValidationFailedException(errors, "Validation failed updating user password");
      }

      UserPasswordVO newRecord = oldRecord;

      // set defaults
      // get the date
      Date now = new Date();

      // encrypt the password
      Hasher encrypter = BCrypt.withDefaults();

      String passwordHash = encrypter.hashToString(12, record.getPassword().toCharArray());

      newRecord.setPasswordHash(passwordHash);
      newRecord.setUpdatedDtg(now);
      newRecord.setResetRequired(false);
      int ok = _dao.update(newRecord);

      if (1 != ok)
      {
        throw new OptimisticLockFailedException("error updating user password");
      }
    }

    @Override
    public int delete(UserPasswordVO record, UserToken token) throws ThingAppException 
    {
      try
      {
        return _dao.delete(record);
      }
      catch (Throwable e)
      {
        throw new FatalBeanException("Error deleting user password");
      }
    }
    
}

package com.zetech.thingapp.thingapp.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.dal.interfaces.UserRoleDAOInterface;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.exceptions.NoDataFoundException;
import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
import com.zetech.thingapp.thingapp.model.UserRoleVO;
import com.zetech.thingapp.thingapp.security.UserToken;

@Service
public class UserRoleService implements UserRoleServiceInterface
{
  @Autowired
  private UserRoleDAOInterface _dao;

  @Override
  public List<UserRoleVO> retrieveFor(String email, UserToken token) throws ThingAppException
  {
    try
    {
      if (!token.getEmail().contentEquals(email) && token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("You do not have permissions to access roles for this user");
      }
      return _dao.retrieveFor(email);
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalBeanException("Error retrieving roles for user: " + email + ".");
    }
  }

  @Override
  public UserRoleVO retrieveById(Long id, UserToken token) throws ThingAppException
  {
    try
    {
      UserRoleVO data = _dao.retrieveById(id);
      if (null == data)
      {
        throw new NoDataFoundException("User role not found for id: " + id  + ".");
      }
      if (!data.getEmail().contentEquals(token.getEmail()) && token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("You do not have permission to access roles for this user.");
      }

      return data;
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalBeanException("Error trying to retrieve user role by ID");
    }
  }

  @Override
  public UserRoleVO create(UserRoleVO record, UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("User is not an admin and cannot create user roles.");
      }
      // email is fk, role is enum, not much point in validating null

      record.setCreatedEmail(token.getEmail());
      record.setCreatedDtg(new Date());
      int ok = _dao.create(record);
      if (1 != ok)
      {
        throw new FatalBeanException("Error creating user role.");
      }
      return retrieveById(record.getId(), token);
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalBeanException("Error creating user role.");
    }
  }

  @Override
  public int delete(UserRoleVO record, UserToken token) throws ThingAppException
  {
    try
    {
      if (token.missingRole(ApplicationRoles.ADMINISTRATOR))
      {
        throw new NotAuthorizedException("User is not an admin and cannot delete user roles.");
      }
      return _dao.delete(record);
    }
    catch (ThingAppException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new FatalBeanException("Error deleting user role.");
    }
  }

}


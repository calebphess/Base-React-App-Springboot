package com.zetech.thingapp.thingapp.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.UserRoleVO;
import com.zetech.thingapp.thingapp.security.SystemToken;

@Service
public class SecurityService implements SecurityServiceInterface
{
  @Autowired
  private UserRoleServiceInterface _roleService;

  @Override
  public Set<ApplicationRoles> authorize(String email, SystemToken token) throws ThingAppException
  {
    Set<ApplicationRoles> roles = new TreeSet<ApplicationRoles>();

    List<UserRoleVO> aRoles = _roleService.retrieveFor(email, token);

    for (UserRoleVO ar : aRoles)
    {
      roles.add(ar.getRole());
    }
    
    return roles;
  }
}

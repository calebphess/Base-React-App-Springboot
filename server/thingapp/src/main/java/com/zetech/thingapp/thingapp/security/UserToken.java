package com.zetech.thingapp.thingapp.security;

import java.util.Set;
import java.util.TreeSet;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;

public class UserToken 
{
  private String _userId;
  private String _authTokenId;
  private Set<ApplicationRoles> _roles = new TreeSet<>();

  @SuppressWarnings("unused")
  private UserToken()
  {
    // hidden def ctr
  }

  // constructor for the user token interceptor to create the usertoken from the auth token
  public UserToken(String userId, String authTokenId, Set<ApplicationRoles> roles)
  {
    _userId = userId;
    _authTokenId = authTokenId;
    _roles.addAll(roles);
  }

  // consturctor for if we want to do something on behalf of a user
  public UserToken(String userId, Set<ApplicationRoles> roles)
  {
    _userId = userId;
    if (null != roles)
    {
      _roles.addAll(roles);
    }
  }

  public String getAuthTokenId()
  {
    return _authTokenId;
  }

  public String getUserId()
  {
    return _userId;
  }

  public boolean hasRole(ApplicationRoles role)
  {
    return _roles.contains(role);
  }

  public boolean missingRole(ApplicationRoles role)
  {
    return !hasRole(role);
  }

  public Set<ApplicationRoles> getRoles()
  {
    return new TreeSet<ApplicationRoles>(_roles);
  }
}

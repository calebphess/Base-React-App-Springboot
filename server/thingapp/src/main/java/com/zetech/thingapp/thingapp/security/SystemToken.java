package com.zetech.thingapp.thingapp.security;

import java.util.Set;
import java.util.TreeSet;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;

/*
 * This is the token that our System uses to do things on it's own behalf
 * As such, it has all available application roles
 * System token is used for things like sync jobs, or user lookups for authentication
 * It also helps us audit things that the system is doing by itself without user interaction
 */

public final class SystemToken extends UserToken
{
  public SystemToken()
  {
    // since email is our primary user ID here I want to make it appear valid by giving it the ID of SYSTEM@<Application Name>.com
    super("SYSTEM@thingapp.com", makeRoles());
  }

  // Here we give the system user every available role
  private static Set<ApplicationRoles> makeRoles()
  {
    Set<ApplicationRoles> roles = new TreeSet<>();
    roles.add(ApplicationRoles.ADMINISTRATOR);
    roles.add(ApplicationRoles.USER);
    roles.add(ApplicationRoles.PREMIUM_USER);
    return roles;
  }
}

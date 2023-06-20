package com.zetech.thingapp.thingapp.service;

import java.util.Set;

import com.zetech.thingapp.thingapp.constants.ApplicationRoles;
import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.security.SystemToken;

/*
 * This is the important service that handles our authentication
 * It should look up the user and return all of their user roles
 */

public interface SecurityServiceInterface
{
  Set<ApplicationRoles> authorize(String email, SystemToken token) throws ThingAppException;
}

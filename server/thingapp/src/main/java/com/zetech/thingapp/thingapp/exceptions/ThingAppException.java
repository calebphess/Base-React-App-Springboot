package com.zetech.thingapp.thingapp.exceptions;

import org.springframework.http.HttpStatus;

/*
 * This is the core class for our gracefull exception handling
 * This makes all our exceptions from the server generate pretty messages
 * Because no one wants to have to do digging through console logs and then the backend logs to find what's not working
 */

public abstract class ThingAppException extends Exception
{
  private HttpStatus _httpStatus;

  protected ThingAppException(HttpStatus httpStatus, String message, Throwable cause)
  {
    super(message, cause);
    _httpStatus = httpStatus;
  }

  public HttpStatus httpStatus()
  {
    return _httpStatus;
  }

}
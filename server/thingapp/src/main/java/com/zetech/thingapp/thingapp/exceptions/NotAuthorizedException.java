package com.zetech.thingapp.thingapp.exceptions;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends ThingAppException
{
  public NotAuthorizedException(String message)
  {
    this(message, null);
  }

  public NotAuthorizedException(String message, Throwable cause)
  {
    super(HttpStatus.UNAUTHORIZED, message, cause);
  }

}
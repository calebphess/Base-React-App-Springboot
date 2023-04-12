package com.zetech.thingapp.thingapp.exceptions;

import org.springframework.http.HttpStatus;

public class FatalException extends ThingAppException
{
  public FatalException(String message)
  {
    this(message, null);
  }

  public FatalException(String message, Throwable cause)
  {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
  }

}
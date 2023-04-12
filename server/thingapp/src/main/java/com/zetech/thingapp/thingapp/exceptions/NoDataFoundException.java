package com.zetech.thingapp.thingapp.exceptions;

import org.springframework.http.HttpStatus;

public class NoDataFoundException extends ThingAppException
{
  public NoDataFoundException(String message)
  {
    this(message, null);
  }

  public NoDataFoundException(String message, Throwable cause)
  {
    super(HttpStatus.NOT_FOUND, message, cause);
  }

}
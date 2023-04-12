package com.zetech.thingapp.thingapp.exceptions;

import org.springframework.http.HttpStatus;

public class OptimisticLockFailedException extends ThingAppException
{

  public OptimisticLockFailedException(String message)
  {
    super(HttpStatus.CONFLICT, message, null);
  }

}

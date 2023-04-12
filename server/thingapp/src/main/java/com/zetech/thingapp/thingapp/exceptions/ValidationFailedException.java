package com.zetech.thingapp.thingapp.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationFailedException extends ThingAppException
{
  private ValidationErrors _errors = new ValidationErrors();

  public ValidationFailedException(String message)
  {
    this(null, message);
  }

  public ValidationFailedException(ValidationErrors errors, String message)
  {
    super(HttpStatus.BAD_REQUEST, message, null);
    _errors = errors;
  }

  public ValidationErrors getErrors()
  {
    return _errors;
  }

}

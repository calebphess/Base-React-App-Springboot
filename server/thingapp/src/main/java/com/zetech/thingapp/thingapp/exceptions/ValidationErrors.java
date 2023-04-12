package com.zetech.thingapp.thingapp.exceptions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ValidationErrors
{
  // key=property, value=message
  private Map<String, List<String>> _errors = new TreeMap<>();

  public ValidationErrors() {}

  public void addError(String property, String message)
  {
    List<String> messages = _errors.get(property);
    if (null == messages)
    {
      messages = new LinkedList<>();
      _errors.put(property, messages);
    }
    messages.add(message);
  }

  public boolean hasErrors()
  {
    return !_errors.isEmpty();
  }

  public Map<String, List<String>> getErrors()
  {
    // TODO someday do a clone or something
    // TODO: WTF IS THIS??? ^
    return _errors;
  }

}

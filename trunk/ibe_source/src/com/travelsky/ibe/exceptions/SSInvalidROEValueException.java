package com.travelsky.ibe.exceptions;

public class SSInvalidROEValueException extends IBEException
{
  private static final long serialVersionUID = 4293418529382293754L;

  public SSInvalidROEValueException()
  {
  }

  public SSInvalidROEValueException(String s)
  {
    super(s);
  }

  public SSInvalidROEValueException(Exception param)
  {
    super(param);
  }
}
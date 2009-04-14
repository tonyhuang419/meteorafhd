package com.travelsky.ibe.exceptions;

public class BARRActionCodeException extends IBEException
{
  private static final long serialVersionUID = -6747427050519740414L;

  public BARRActionCodeException()
  {
  }

  public BARRActionCodeException(String s)
  {
    super(s);
  }

  public BARRActionCodeException(Exception param)
  {
    super(param);
  }
}
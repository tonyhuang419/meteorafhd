package com.travelsky.ibe.exceptions;

public class IBEException extends Exception
{
  private static final long serialVersionUID = -1780368167588812830L;

  public IBEException()
  {
    super("IBEException:");
  }

  public IBEException(String s)
  {
    super(s);
  }

  public IBEException(Exception param)
  {
    super(param.getMessage());
  }
}
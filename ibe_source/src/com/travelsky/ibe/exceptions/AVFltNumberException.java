package com.travelsky.ibe.exceptions;

public class AVFltNumberException extends IBEException
{
  private static final long serialVersionUID = 5779271541759700479L;

  public AVFltNumberException()
  {
    super("FltNumberException:");
  }

  public AVFltNumberException(String s)
  {
    super("FltNumberException: " + s);
  }
}
package com.travelsky.ibe.exceptions;

public class SmsFormatException extends IBEException
{
  private static final long serialVersionUID = -8964118411902041335L;

  public SmsFormatException()
  {
  }

  public SmsFormatException(String s)
  {
    super(s);
  }

  public SmsFormatException(Exception param)
  {
    super(param);
  }
}
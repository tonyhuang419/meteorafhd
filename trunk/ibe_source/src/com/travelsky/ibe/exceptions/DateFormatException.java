package com.travelsky.ibe.exceptions;

public class DateFormatException extends IBEException
{
  private static final long serialVersionUID = -6754140451599457337L;

  public DateFormatException()
  {
    super("DateFormatException:");
  }

  public DateFormatException(String s)
  {
    super("DateFormatException: " + s);
  }
}
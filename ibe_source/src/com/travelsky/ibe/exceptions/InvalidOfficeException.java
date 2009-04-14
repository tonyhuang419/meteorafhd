package com.travelsky.ibe.exceptions;

public class InvalidOfficeException extends IBEException
{
  private static final long serialVersionUID = -2227271736174350318L;

  public InvalidOfficeException()
  {
    super("IBEException:");
  }

  public InvalidOfficeException(String s)
  {
    super(s);
  }
}
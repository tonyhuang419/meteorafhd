package com.travelsky.ibe.exceptions;

public class NoDeviceException extends IBEException
{
  private static final long serialVersionUID = 5978164674417547443L;

  public NoDeviceException()
  {
  }

  public NoDeviceException(String s)
  {
    super(s);
  }
}
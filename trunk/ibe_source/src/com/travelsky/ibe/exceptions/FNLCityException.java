package com.travelsky.ibe.exceptions;

public class FNLCityException extends IBEException
{
  private static final long serialVersionUID = 6875768093936328337L;

  public FNLCityException()
  {
    super("FNLCityException:");
  }

  public FNLCityException(String s)
  {
    super("FNLCityException: " + s);
  }
}
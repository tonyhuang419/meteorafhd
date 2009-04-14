package com.travelsky.ibe.exceptions;

public class FDLCityException extends IBEException
{
  private static final long serialVersionUID = 3312477906067302548L;

  public FDLCityException()
  {
    super("FDLCityException:");
  }

  public FDLCityException(String s)
  {
    super("FDLCityException: " + s);
  }
}
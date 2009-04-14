package com.travelsky.ibe.exceptions;

public class FDRForbiddenFlightNumberException extends IBEException
{
  private static final long serialVersionUID = -5043791788185574286L;

  public FDRForbiddenFlightNumberException()
  {
  }

  public FDRForbiddenFlightNumberException(String s)
  {
    super(s);
  }
}
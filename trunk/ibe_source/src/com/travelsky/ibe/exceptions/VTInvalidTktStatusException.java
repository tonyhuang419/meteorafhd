package com.travelsky.ibe.exceptions;

public class VTInvalidTktStatusException extends IBEException
{
  private static final long serialVersionUID = 8095208720651652668L;

  public VTInvalidTktStatusException()
  {
  }

  public VTInvalidTktStatusException(String s)
  {
    super(s);
  }
}
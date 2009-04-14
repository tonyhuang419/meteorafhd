package com.travelsky.ibe.exceptions;

public class PMNameLengthException extends IBEException
{
  private static final long serialVersionUID = 8500992884935638831L;

  public PMNameLengthException()
  {
  }

  public PMNameLengthException(String s)
  {
    super(s);
  }
}
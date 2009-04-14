package com.travelsky.ibe.exceptions;

public class SSNameLengthException extends IBEException
{
  private static final long serialVersionUID = 5178119469815282847L;

  public SSNameLengthException()
  {
  }

  public SSNameLengthException(String s)
  {
    super(s);
  }
}
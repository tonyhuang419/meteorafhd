package com.travelsky.ibe.exceptions;

public class SSNameMissingException extends IBEException
{
  private static final long serialVersionUID = -6464102486696852655L;

  public SSNameMissingException()
  {
  }

  public SSNameMissingException(String s)
  {
    super(s);
  }
}
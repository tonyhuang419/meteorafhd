package com.travelsky.ibe.exceptions;

public class DecodeErrorException extends IBEException
{
  private static final long serialVersionUID = 1894699496149932929L;

  public DecodeErrorException()
  {
    super("DecodeErrorException:");
  }

  public DecodeErrorException(String s)
  {
    super("DecodeErrorException: " + s);
  }
}
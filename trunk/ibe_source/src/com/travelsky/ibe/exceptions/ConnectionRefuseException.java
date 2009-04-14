package com.travelsky.ibe.exceptions;

public class ConnectionRefuseException extends IBEException
{
  private static final long serialVersionUID = 876155052872713116L;

  public ConnectionRefuseException()
  {
  }

  public ConnectionRefuseException(String s)
  {
    super(s);
  }
}
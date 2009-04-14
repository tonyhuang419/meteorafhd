package com.travelsky.ibe.exceptions;

public class DisconnectionException extends IBEException
{
  private static final long serialVersionUID = 1043556190371291360L;

  public DisconnectionException()
  {
  }

  public DisconnectionException(String s)
  {
    super(s);
  }
}
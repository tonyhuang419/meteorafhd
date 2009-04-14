package com.travelsky.ibe.exceptions;

public class NetworkConnectionException extends IBEException
{
  private static final long serialVersionUID = -5285187377947258626L;

  public NetworkConnectionException()
  {
  }

  public NetworkConnectionException(String s)
  {
    super(s);
  }
}
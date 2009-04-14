package com.travelsky.ibe.exceptions;

public class AVNoRoutingException extends IBEException
{
  private static final long serialVersionUID = -4910025475160373606L;

  public AVNoRoutingException()
  {
    super("NoRoutingException:");
  }

  public AVNoRoutingException(String s)
  {
    super("NoRoutingException: " + s);
  }
}
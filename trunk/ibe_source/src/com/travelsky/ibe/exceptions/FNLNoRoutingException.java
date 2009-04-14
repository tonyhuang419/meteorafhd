package com.travelsky.ibe.exceptions;

public class FNLNoRoutingException extends IBEException
{
  private static final long serialVersionUID = -2745217484662792599L;

  public FNLNoRoutingException()
  {
    super("FNLNoRoutingException:");
  }

  public FNLNoRoutingException(String s)
  {
    super("FNLNoRoutingException: " + s);
  }
}
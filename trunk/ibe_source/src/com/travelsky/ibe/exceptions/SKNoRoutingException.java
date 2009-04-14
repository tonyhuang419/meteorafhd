package com.travelsky.ibe.exceptions;

public class SKNoRoutingException extends IBEException
{
  private static final long serialVersionUID = 2325790086650225679L;

  public SKNoRoutingException()
  {
  }

  public SKNoRoutingException(String s)
  {
    super(s);
  }
}
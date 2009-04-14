package com.travelsky.ibe.exceptions;

public class AccessDenyException extends IBEException
{
  private static final long serialVersionUID = 2195734218625710696L;

  public AccessDenyException()
  {
  }

  public AccessDenyException(String s)
  {
    super(s);
  }
}
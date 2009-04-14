package com.travelsky.ibe.exceptions;

public class NoResponseException extends IBEException
{
  private static final long serialVersionUID = 5561993732335989082L;

  public NoResponseException()
  {
  }

  public NoResponseException(String s)
  {
    super(s);
  }
}
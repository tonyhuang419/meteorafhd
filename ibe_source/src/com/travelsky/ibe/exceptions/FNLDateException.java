package com.travelsky.ibe.exceptions;

public class FNLDateException extends IBEException
{
  private static final long serialVersionUID = -5767582180805251355L;

  public FNLDateException()
  {
    super("FNLDateException:");
  }

  public FNLDateException(String s)
  {
    super("FNLDateException: " + s);
  }
}
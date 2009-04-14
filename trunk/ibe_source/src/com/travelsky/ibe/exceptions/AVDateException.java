package com.travelsky.ibe.exceptions;

public class AVDateException extends IBEException
{
  private static final long serialVersionUID = -2594130626741003124L;

  public AVDateException()
  {
  }

  public AVDateException(String s)
  {
    super("DateException: " + s);
  }
}
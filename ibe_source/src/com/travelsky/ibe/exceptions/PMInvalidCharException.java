package com.travelsky.ibe.exceptions;

public class PMInvalidCharException extends IBEException
{
  private static final long serialVersionUID = 7014954538301844013L;

  public PMInvalidCharException()
  {
  }

  public PMInvalidCharException(String s)
  {
    super(s);
  }
}
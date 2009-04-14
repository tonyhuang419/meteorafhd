package com.travelsky.ibe.exceptions;

public class SSInvalidCharException extends IBEException
{
  private static final long serialVersionUID = -5761608129222228198L;

  public SSInvalidCharException()
  {
  }

  public SSInvalidCharException(String s)
  {
    super(s);
  }
}
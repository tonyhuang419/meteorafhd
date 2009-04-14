package com.travelsky.ibe.exceptions;

public class SSPassengerNameErrorException extends IBEException
{
  private static final long serialVersionUID = -5390621231887036601L;

  public SSPassengerNameErrorException()
  {
  }

  public SSPassengerNameErrorException(String s)
  {
    super(s);
  }
}
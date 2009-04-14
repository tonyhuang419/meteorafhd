package com.travelsky.ibe.exceptions;

public class PMPassengerNameErrorException extends IBEException
{
  private static final long serialVersionUID = 2678455009999692172L;

  public PMPassengerNameErrorException()
  {
  }

  public PMPassengerNameErrorException(String s)
  {
    super(s);
  }
}
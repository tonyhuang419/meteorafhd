package com.travelsky.ibe.exceptions;

public class NoAvailableResourceException extends IBEException
{
  private static final long serialVersionUID = 3748720771084008380L;

  public NoAvailableResourceException()
  {
  }

  public NoAvailableResourceException(String s)
  {
    super(s);
  }
}
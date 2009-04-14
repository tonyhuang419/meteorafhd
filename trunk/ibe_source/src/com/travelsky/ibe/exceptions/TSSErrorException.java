package com.travelsky.ibe.exceptions;

public class TSSErrorException extends IBEException
{
  private static final long serialVersionUID = -7546262772137218517L;

  public TSSErrorException()
  {
  }

  public TSSErrorException(String s)
  {
    super(s);
  }

  public TSSErrorException(Exception param)
  {
    super(param);
  }
}
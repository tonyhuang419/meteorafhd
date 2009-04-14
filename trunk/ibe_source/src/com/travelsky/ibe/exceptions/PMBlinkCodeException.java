package com.travelsky.ibe.exceptions;

public class PMBlinkCodeException extends IBEException
{
  private static final long serialVersionUID = 9207894945384264002L;

  public PMBlinkCodeException()
  {
  }

  public PMBlinkCodeException(String s)
  {
    super(s);
  }

  public PMBlinkCodeException(Exception param)
  {
    super(param);
  }
}
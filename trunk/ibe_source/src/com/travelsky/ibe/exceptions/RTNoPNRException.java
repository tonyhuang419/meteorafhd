package com.travelsky.ibe.exceptions;

public class RTNoPNRException extends IBEException
{
  private static final long serialVersionUID = 5008347560829624671L;

  public RTNoPNRException()
  {
    super("NoPNRException:");
  }

  public RTNoPNRException(String s)
  {
    super("NoPNRException: No such PNR:" + s);
  }
}
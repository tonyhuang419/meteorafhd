package com.travelsky.ibe.exceptions;

public class PMNoPNRException extends IBEException
{
  private static final long serialVersionUID = 2501213671252909782L;

  public PMNoPNRException()
  {
    super("NoPNRException:");
  }

  public PMNoPNRException(String s)
  {
    super("NoPNRException: No such PNR:" + s);
  }
}
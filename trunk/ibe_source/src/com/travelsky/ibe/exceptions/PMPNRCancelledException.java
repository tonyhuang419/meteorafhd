package com.travelsky.ibe.exceptions;

public class PMPNRCancelledException extends IBEException
{
  private static final long serialVersionUID = -3431455402933714776L;

  public PMPNRCancelledException()
  {
    super("PNRCancelledException:");
  }

  public PMPNRCancelledException(String s)
  {
    super("PNRCancelledException: PNR:" + s + " was entirely cancelled.");
  }
}
package com.travelsky.ibe.exceptions;

public class RTPNRCancelledException extends IBEException
{
  private static final long serialVersionUID = -6947986395246303902L;

  public RTPNRCancelledException()
  {
    super("PNRCancelledException:");
  }

  public RTPNRCancelledException(String s)
  {
    super("PNRCancelledException: PNR:" + s + " was entirely cancelled.");
  }
}
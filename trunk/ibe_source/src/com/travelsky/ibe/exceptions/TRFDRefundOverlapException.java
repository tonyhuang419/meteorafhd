package com.travelsky.ibe.exceptions;

public class TRFDRefundOverlapException extends IBEException
{
  private static final long serialVersionUID = -1621079165935650133L;

  public TRFDRefundOverlapException()
  {
  }

  public TRFDRefundOverlapException(String s)
  {
    super(s);
  }
}
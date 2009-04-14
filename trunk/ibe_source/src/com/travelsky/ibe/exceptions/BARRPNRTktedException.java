package com.travelsky.ibe.exceptions;

public class BARRPNRTktedException extends IBEException
{
  private static final long serialVersionUID = 5603315209347161460L;

  public BARRPNRTktedException()
  {
  }

  public BARRPNRTktedException(String s)
  {
    super(s); }

  public BARRPNRTktedException(Exception ex) {
    super(ex);
  }
}
package com.travelsky.ibe.exceptions;

public class PMTicketMissingException extends IBEException
{
  private static final long serialVersionUID = -3466290457978272907L;

  public PMTicketMissingException()
  {
  }

  public PMTicketMissingException(String s)
  {
    super(s);
  }
}
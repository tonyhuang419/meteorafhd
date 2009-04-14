package com.travelsky.ibe.exceptions;

public class SSTicketMissingException extends IBEException
{
  private static final long serialVersionUID = 5344587278753182895L;

  public SSTicketMissingException()
  {
  }

  public SSTicketMissingException(String s)
  {
    super(s);
  }
}
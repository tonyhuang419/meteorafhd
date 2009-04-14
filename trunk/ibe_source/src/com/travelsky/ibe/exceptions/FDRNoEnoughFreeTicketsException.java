package com.travelsky.ibe.exceptions;

public class FDRNoEnoughFreeTicketsException extends IBEException
{
  private static final long serialVersionUID = 8513025601007957722L;

  public FDRNoEnoughFreeTicketsException()
  {
  }

  public FDRNoEnoughFreeTicketsException(String s)
  {
    super(s);
  }
}
package com.travelsky.ibe.exceptions;

public class FDLNoRoutingException extends IBEException
{
  private static final long serialVersionUID = -8298003326720539757L;

  public FDLNoRoutingException()
  {
    super("FDLNoRoutingException:");
  }

  public FDLNoRoutingException(String s)
  {
    super("FDLNoRoutingException: " + s);
  }
}
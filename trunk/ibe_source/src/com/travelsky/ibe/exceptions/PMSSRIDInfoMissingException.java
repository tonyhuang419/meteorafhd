package com.travelsky.ibe.exceptions;

public class PMSSRIDInfoMissingException extends IBEException
{
  private static final long serialVersionUID = -9037972800046625717L;

  public PMSSRIDInfoMissingException(String s)
  {
    super(s); }

  public PMSSRIDInfoMissingException() { }

  public PMSSRIDInfoMissingException(Exception e) {
    super(e);
  }
}
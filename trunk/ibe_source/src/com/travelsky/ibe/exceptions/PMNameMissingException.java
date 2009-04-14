package com.travelsky.ibe.exceptions;

public class PMNameMissingException extends IBEException
{
  private static final long serialVersionUID = 1744654781528383812L;

  public PMNameMissingException()
  {
  }

  public PMNameMissingException(String s)
  {
    super(s);
  }
}
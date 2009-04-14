package com.travelsky.ibe.exceptions;

public class FDLDateException extends IBEException
{
  private static final long serialVersionUID = 1465748717172512074L;

  public FDLDateException()
  {
    super("FDLDateException:");
  }

  public FDLDateException(String s)
  {
    super("FDLDateException: " + s);
  }
}
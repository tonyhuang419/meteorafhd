package com.travelsky.ibe.exceptions;

public class SSTooManyTaxInFNException extends IBEException
{
  private static final long serialVersionUID = -5399447660422864209L;

  public SSTooManyTaxInFNException()
  {
  }

  public SSTooManyTaxInFNException(String s)
  {
    super(s);
  }

  public SSTooManyTaxInFNException(Exception param)
  {
    super(param);
  }
}
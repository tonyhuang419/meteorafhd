package com.travelsky.ibe.exceptions;

public class SSInvalidInfoException extends IBEException
{
  private static final long serialVersionUID = 7063989164686263800L;

  public SSInvalidInfoException()
  {
  }

  public SSInvalidInfoException(String s)
  {
    super(s);
  }

  public SSInvalidInfoException(Exception param)
  {
    super(param);
  }
}
package com.travelsky.ibe.exceptions;

public class ADMFailException extends IBEException
{
  private static final long serialVersionUID = -2787340721240699192L;

  public ADMFailException()
  {
  }

  public ADMFailException(String s)
  {
    super(s);
  }

  public ADMFailException(Exception param)
  {
    super(param);
  }
}
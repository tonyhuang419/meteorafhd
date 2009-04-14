package com.travelsky.ibe.exceptions;

public class PMDuplicateException extends IBEException
{
  private static final long serialVersionUID = -38454899416067056L;

  public PMDuplicateException()
  {
  }

  public PMDuplicateException(String s)
  {
    super(s);
  }

  public PMDuplicateException(Exception param)
  {
    super(param);
  }
}
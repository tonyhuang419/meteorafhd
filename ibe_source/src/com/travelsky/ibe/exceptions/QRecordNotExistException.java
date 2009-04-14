package com.travelsky.ibe.exceptions;

public class QRecordNotExistException extends IBEException
{
  private static final long serialVersionUID = 7150935058044335372L;

  public QRecordNotExistException()
  {
  }

  public QRecordNotExistException(String s)
  {
    super(s);
  }
}
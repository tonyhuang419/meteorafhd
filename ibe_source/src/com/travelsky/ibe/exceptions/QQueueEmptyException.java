package com.travelsky.ibe.exceptions;

public class QQueueEmptyException extends IBEException
{
  private static final long serialVersionUID = 2259814934448614253L;

  public QQueueEmptyException()
  {
  }

  public QQueueEmptyException(String s)
  {
    super(s);
  }
}
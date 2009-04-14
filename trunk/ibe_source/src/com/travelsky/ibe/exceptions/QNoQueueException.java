package com.travelsky.ibe.exceptions;

public class QNoQueueException extends IBEException
{
  private static final long serialVersionUID = -3567252075208174288L;

  public QNoQueueException()
  {
  }

  public QNoQueueException(String s)
  {
    super(s);
  }
}
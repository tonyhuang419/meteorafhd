package com.travelsky.ibe.exceptions;

public class QWorkingInOtherQueueException extends IBEException
{
  private static final long serialVersionUID = -1494192151324029257L;

  public QWorkingInOtherQueueException()
  {
  }

  public QWorkingInOtherQueueException(String s)
  {
    super(s);
  }
}
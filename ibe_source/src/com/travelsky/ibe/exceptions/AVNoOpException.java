package com.travelsky.ibe.exceptions;

public class AVNoOpException extends IBEException
{
  private static final long serialVersionUID = 7839161000349223946L;

  public AVNoOpException()
  {
    super("NoOpException:");
  }

  public AVNoOpException(String s)
  {
    super("NoOpException: " + s);
  }
}
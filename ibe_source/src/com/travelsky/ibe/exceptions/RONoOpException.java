package com.travelsky.ibe.exceptions;

public class RONoOpException extends IBEException
{
  private static final long serialVersionUID = 9033192322223449357L;

  public RONoOpException()
  {
  }

  public RONoOpException(String s)
  {
    super(s);
  }

  public RONoOpException(Exception param)
  {
    super(param);
  }

  public static void main(String[] args)
  {
  }
}
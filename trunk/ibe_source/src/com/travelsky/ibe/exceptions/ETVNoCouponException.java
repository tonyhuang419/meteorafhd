package com.travelsky.ibe.exceptions;

public class ETVNoCouponException extends IBEException
{
  private static final long serialVersionUID = 2480828757218291617L;

  public ETVNoCouponException()
  {
  }

  public ETVNoCouponException(String s)
  {
    super(s);
  }
}
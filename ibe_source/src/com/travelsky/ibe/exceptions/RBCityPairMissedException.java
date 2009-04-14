package com.travelsky.ibe.exceptions;

public class RBCityPairMissedException extends IBEException
{
  private static final long serialVersionUID = -8598245215979780984L;

  public RBCityPairMissedException()
  {
  }

  public RBCityPairMissedException(String s)
  {
    super(s);
  }

  public RBCityPairMissedException(Exception param)
  {
    super(param);
  }

  public static void main(String[] args)
  {
  }
}
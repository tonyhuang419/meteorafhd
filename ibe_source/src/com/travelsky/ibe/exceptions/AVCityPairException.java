package com.travelsky.ibe.exceptions;

public class AVCityPairException extends IBEException
{
  private static final long serialVersionUID = -6039370521101702954L;

  public AVCityPairException()
  {
  }

  public AVCityPairException(String s)
  {
    super("CityPairException: " + s);
  }
}
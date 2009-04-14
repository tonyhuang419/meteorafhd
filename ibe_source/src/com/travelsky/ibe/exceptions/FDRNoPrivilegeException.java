package com.travelsky.ibe.exceptions;

public class FDRNoPrivilegeException extends IBEException
{
  private static final long serialVersionUID = -8953921400366129693L;

  public FDRNoPrivilegeException()
  {
  }

  public FDRNoPrivilegeException(String s)
  {
    super(s);
  }
}
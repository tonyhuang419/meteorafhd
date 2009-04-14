package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookOSI extends IBEResult
{
  private static final long serialVersionUID = 1855611343554224139L;
  private String airCode = "";
  private String osi = "";
  private String psgrName = "";

  public BookOSI()
  {
  }

  public BookOSI(String airCode, String osi, String psgrName)
    throws Exception
  {
    this.airCode = airCode;
    this.osi = osi;
    this.psgrName = psgrName;
  }

  public BookOSI(String airCode, String osi)
    throws Exception
  {
    this.airCode = airCode;
    this.osi = osi;
    this.psgrName = "";
  }

  public void setAirCode(String airCode)
  {
    this.airCode = airCode;
  }

  String getAirCode() {
    return this.airCode;
  }

  public void setOsi(String osi)
  {
    this.osi = osi;
  }

  String getOsi() {
    return this.osi;
  }

  public void setpsgrName(String psgrName)
  {
    this.psgrName = psgrName;
  }

  String getpsgrName() {
    return this.psgrName;
  }
}
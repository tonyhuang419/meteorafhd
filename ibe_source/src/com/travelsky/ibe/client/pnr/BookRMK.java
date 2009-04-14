package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookRMK extends IBEResult
{
  private static final long serialVersionUID = 3672561962227004952L;
  private String rmktype = "";
  private String rmkinfo = "";
  private String psgrName = "";

  public BookRMK()
  {
  }

  public BookRMK(String rmktype, String rmkinfo)
    throws Exception
  {
    this.rmktype = rmktype;
    this.rmkinfo = rmkinfo;
    this.psgrName = "";
  }

  public BookRMK(String rmktype, String rmkinfo, String psgrName)
    throws Exception
  {
    this.rmktype = rmktype;
    this.rmkinfo = rmkinfo;
    this.psgrName = psgrName;
  }

  public void setRmktype(String rmktype)
  {
    this.rmktype = rmktype;
  }

  String getRmktype() {
    return this.rmktype;
  }

  public void setRmkinfo(String rmkinfo)
  {
    this.rmkinfo = rmkinfo;
  }

  String getRmkinfo() {
    return this.rmkinfo;
  }

  public void setpsgrName(String psgrName)
  {
    this.psgrName = psgrName;
  }

  String getpsgrName() {
    return this.psgrName;
  }
}
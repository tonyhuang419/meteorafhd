package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRRMK extends IBEResult
{
  private static final long serialVersionUID = 1385512376525610045L;
  private String rmktype;
  private String rmkinfo;
  private String psgrID;
  int lineIndex;

  public PNRRMK()
    throws Exception
  {
  }

  public PNRRMK(String rmktype, String rmkinfo, String psgrID)
    throws Exception
  {
    this.rmktype = rmktype;
    this.rmkinfo = rmkinfo;
    this.psgrID = psgrID;
  }

  void setRmktype(String rmktype)
  {
    this.rmktype = rmktype;
  }

  public String getRmktype()
  {
    return this.rmktype;
  }

  void setRmkinfo(String rmkinfo) {
    this.rmkinfo = rmkinfo;
  }

  public String getRmkinfo()
  {
    return this.rmkinfo;
  }

  void setPsgrID(String psgrID) {
    this.psgrID = psgrID;
  }

  public String getPsgrID()
  {
    return this.psgrID;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }
}
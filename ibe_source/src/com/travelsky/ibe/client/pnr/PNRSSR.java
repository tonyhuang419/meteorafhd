package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRSSR extends IBEResult
{
  private static final long serialVersionUID = 3713355937091713710L;
  String actionCode = "";
  String airline = null;
  int lineIndex;
  protected String psgrID;
  protected String serveInfo;
  String SSRType = "";

  public String getActionCode()
  {
    return this.actionCode;
  }

  public String getAirline()
  {
    return this.airline;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getPsgrID()
  {
    return this.psgrID;
  }

  public String getServeInfo()
  {
    return this.serveInfo;
  }

  public String getSSRType()
  {
    return this.SSRType;
  }

  void setActionCode(String newActionCode)
  {
    this.actionCode = newActionCode;
  }

  void setAirline(String newAirline)
  {
    this.airline = newAirline;
  }

  void setPsgrID(String psgrID) {
    this.psgrID = psgrID;
  }

  void setServeInfo(String serveInfo) {
    this.serveInfo = serveInfo;
  }

  public void setSSRType(String newSSRType)
  {
    this.SSRType = newSSRType;
  }

  public String toString()
  {
    if (this.serveInfo != null)
      return "SSR " + 
        this.serveInfo + 
        "<-->" + 
        this.lineIndex + 
        "." + 
        this.SSRType + 
        "|" + 
        this.airline + 
        "|" + 
        this.actionCode + 
        "|" + 
        this.psgrID;
    return "SSR";
  }
}
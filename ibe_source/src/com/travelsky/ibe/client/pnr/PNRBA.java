package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRBA extends IBEResult
{
  private static final long serialVersionUID = -2958926346675447889L;
  private String BAInfo;
  private String psgrid;
  protected int lineIndex;

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getBAInfo()
  {
    return this.BAInfo;
  }

  public String getPsgrid()
  {
    return this.psgrid;
  }

  void setBAInfo(String string)
  {
    this.BAInfo = string;
  }

  void setPsgrid(String string)
  {
    this.psgrid = string; }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    try {
      sb.append(this.lineIndex + ".");
      sb.append("BA信息:" + this.BAInfo);
      if ((this.psgrid != null) && (this.psgrid.length() > 0))
        sb.append("旅客id:" + this.psgrid);
      return sb.toString(); } catch (Exception e) {
    }
    return toString();
  }
}
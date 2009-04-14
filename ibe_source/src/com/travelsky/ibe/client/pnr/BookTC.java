package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookTC extends IBEResult
{
  private static final long serialVersionUID = 8880811615548279707L;
  boolean infant = false;
  private String freecode;
  private String tc;
  private String psgrName;

  public boolean isInfant()
  {
    return this.infant; }

  public void setInfant(boolean infant) {
    this.infant = infant;
  }

  public BookTC(String tcCode)
  {
    this.freecode = tcCode;
  }

  public BookTC()
  {
    this.freecode = null;
    this.tc = null;
  }

  public String getFreecode()
  {
    return this.freecode;
  }

  public String getTc()
  {
    return this.tc;
  }

  void setFreecode(String newFreecode)
  {
    this.freecode = newFreecode;
  }

  void setTc(String newTc)
  {
    this.tc = newTc;
  }

  public String getPsgrName()
  {
    return this.psgrName;
  }

  public void setPsgrName(String psgrName)
  {
    this.psgrName = psgrName;
  }
}
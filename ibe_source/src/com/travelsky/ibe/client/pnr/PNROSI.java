package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNROSI extends IBEResult
{
  private static final long serialVersionUID = 3066342905802650128L;
  private String osi;
  private String pNum;
  int lineIndex;

  public PNROSI()
    throws Exception
  {
  }

  void setOsi(String osi)
  {
    this.osi = osi;
  }

  public String getOsi()
  {
    return this.osi;
  }

  void setPNum(String pNum) {
    this.pNum = pNum;
  }

  public String getPNum()
  {
    return this.pNum;
  }

  public PNROSI(String osi, String pNum)
    throws Exception
  {
    this.osi = osi;
    this.pNum = pNum;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getPsgrID()
  {
    return this.pNum;
  }
}
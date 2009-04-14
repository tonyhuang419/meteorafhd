package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNROther extends IBEResult
{
  private static final long serialVersionUID = -7457629653493123696L;
  int lineIndex;
  private String other;

  public PNROther()
    throws Exception
  {
  }

  public PNROther(String other)
    throws Exception
  {
    this.other = other;
  }

  void setOther(String other)
  {
    this.other = other;
  }

  public String getOther()
  {
    return this.other;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String toString()
  {
    try
    {
      return this.lineIndex + "." + this.other; } catch (Exception e) {
    }
    return toString();
  }
}
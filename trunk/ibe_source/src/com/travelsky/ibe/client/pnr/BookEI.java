package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookEI extends IBEResult
{
  private static final long serialVersionUID = -9167398218166338715L;
  private String endorse = null;
  private String psgrName = null;

  public BookEI(String endorsement)
  {
    this.endorse = endorsement;
  }

  public BookEI(String endorsement, String psgrname)
  {
    this.endorse = endorsement;
    this.psgrName = psgrname;
  }

  public String getEndorse()
  {
    return this.endorse;
  }

  void setEndorse(String newEndorse)
  {
    this.endorse = newEndorse;
  }

  public String getPsgrName()
  {
    return this.psgrName;
  }

  public void setPsgrName(String string)
  {
    this.psgrName = string;
  }
}
package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookBA extends IBEResult
{
  private static final long serialVersionUID = 559227234350467097L;
  private String BAInfo = null;
  private String psgrname = null;

  public BookBA()
  {
  }

  public BookBA(String ba)
  {
    this.BAInfo = ba;
  }

  public BookBA(String ba, String name)
  {
    this.BAInfo = ba;
    this.psgrname = name;
  }

  public String getBAInfo()
  {
    return this.BAInfo;
  }

  public void setBAInfo(String ba)
  {
    this.BAInfo = ba;
  }

  public void setPsgrname(String name)
  {
    this.psgrname = name;
  }

  public String getPsgrname()
  {
    return this.psgrname;
  }
}
package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import java.util.Date;

public class BookTktStatus extends IBEResult
{
  private static final long serialVersionUID = 2452157412423737149L;
  int type;
  String psgrName = null;
  Date timelimit = null;
  String freetext = null;
  public static final int TIMELIMIT = 16;
  public static final int TICKETED_PLUS_TEXT = 32;
  String tlOffice = null;

  public BookTktStatus()
  {
  }

  public BookTktStatus(Date timelimit)
  {
    this.type = 16;
    this.timelimit = timelimit; }

  public BookTktStatus(String ticketed_text) {
    this.type = 32;
    this.freetext = ticketed_text;
  }

  public String getTlOffice()
  {
    return this.tlOffice; }

  public void setTlOffice(String tlOffice) {
    this.tlOffice = tlOffice; }

  public String getFreetext() {
    return this.freetext;
  }

  public void setFreetext(String freetext)
  {
    this.freetext = freetext;
  }

  public String getPsgrName() {
    return this.psgrName;
  }

  public void setPsgrName(String psgrName)
  {
    this.psgrName = psgrName; }

  public Date getTimelimit() {
    return this.timelimit;
  }

  public void setTimelimit(Date timelimit)
  {
    this.timelimit = timelimit; }

  public int getType() {
    return this.type;
  }

  public void setType(int type)
  {
    this.type = type;
  }
}
package com.travelsky.ibe.client;

import java.util.Date;

public class SkSegment extends IBEResult
{
  private static final long serialVersionUID = 4011390156445095626L;
  String airline = null;
  String orgcity = null;
  String dstcity = null;
  String deptime = null;
  String deptimemodify = null;
  Date depdate = null;
  String arritime = null;
  String arritimemodify = null;
  Date arridate = null;
  String planestyle = null;
  int stopnumber = 0;
  boolean asr = false;
  boolean meal = false;
  boolean isCodeShare = false;
  boolean[] frequency = new boolean[7];
  String effectivedt = null;
  String effectivemonth = null;
  String effectiveyear = null;
  Date effectivedate = null;
  String expirationdt = null;
  String expirationmonth = null;
  String expirationyear = null;
  Date expirationdate = null;
  String link = null;
  String opento = null;
  String[] classlist;
  boolean etkt = false;
  String flightclasses = null;

  public String getAirline()
  {
    return this.airline;
  }

  public String getArritime()
  {
    return this.arritime;
  }

  public String getArritimemodify()
  {
    return this.arritimemodify;
  }

  public boolean getAsr()
  {
    return this.asr;
  }

  public boolean getAvailabilityof(int day)
  {
    if ((day > 6) || (day < 0))
      return false;
    return this.frequency[day];
  }

  public String getDeptime()
  {
    return this.deptime;
  }

  public String getDeptimemodify()
  {
    return this.deptimemodify;
  }

  public String getDstcity()
  {
    return this.dstcity;
  }

  public Date getEffectivedate()
  {
    return this.effectivedate;
  }

  public String getEffectivedt()
  {
    return this.effectivedt;
  }

  public String getEffectivemonth()
  {
    return this.effectivemonth;
  }

  public String getEffectiveyear()
  {
    return this.expirationyear;
  }

  public Date getExpirationdate()
  {
    return this.expirationdate;
  }

  public String getExpirationdt()
  {
    return this.expirationdt;
  }

  public String getExpirationmonth()
  {
    return this.expirationmonth;
  }

  public String getExpirationyear()
  {
    return this.expirationyear;
  }

  public String getLink()
  {
    return this.link;
  }

  public boolean getMeal()
  {
    return this.meal;
  }

  public String getOpento()
  {
    return this.opento;
  }

  public String getOrgcity()
  {
    return this.orgcity;
  }

  public String getPlanestyle()
  {
    return this.planestyle;
  }

  public int getStopnumber()
  {
    return this.stopnumber;
  }

  public String[] getClasslist()
  {
    return this.classlist;
  }

  public String getFlightClasses()
  {
    return this.flightclasses;
  }

  public boolean isEtkt()
  {
    return this.etkt;
  }

  void setEtkt(boolean newEtkt)
  {
    this.etkt = newEtkt;
  }

  public boolean isCodeShare()
  {
    return this.isCodeShare;
  }
}
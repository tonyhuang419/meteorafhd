package com.travelsky.ibe.client;

import java.util.Date;
import java.util.Vector;

public class SkItem extends IBEResult
{
  private static final long serialVersionUID = 890721636288007175L;
  private int s_number = 0;
  private Vector segments = new Vector();

  public String getAirline(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).airline;
  }

  public String getArritime(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).arritime;
  }

  public String getArritimemodify(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).arritimemodify;
  }

  public boolean getAsr(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return false;
    return getSegment(index).asr;
  }

  public boolean getAvailabilityof(int index, int day)
  {
    if ((index >= this.s_number) || (index < 0))
      return false;
    if ((day > 6) || (day < 0))
      return false;

    return getSegment(index).frequency[day];
  }

  public String[] getClasslist(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).classlist;
  }

  public String getDeptime(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).deptime;
  }

  public String getDeptimemodify(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).deptimemodify;
  }

  public String getDstcity(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).dstcity;
  }

  public Date getEffectivedate(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).effectivedate;
  }

  public String getEffectivedt(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).effectivedt;
  }

  public String getEffectivemonth(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).effectivemonth;
  }

  public String getEffectiveyear(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).expirationyear;
  }

  public Date getExpirationdate(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).expirationdate;
  }

  public String getExpirationdt(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).expirationdt;
  }

  public String getExpirationmonth(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).expirationmonth;
  }

  public String getExpirationyear(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).expirationyear;
  }

  public String getLink(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).link;
  }

  public boolean getMeal(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return false;
    return getSegment(index).meal;
  }

  public String getOpento(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).opento;
  }

  public String getOrgcity(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).orgcity;
  }

  public String getPlanestyle(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).planestyle;
  }

  public SkSegment getSegment(int index)
  {
    return ((SkSegment)this.segments.get(index));
  }

  public int getSegmentnumber()
  {
    return this.s_number;
  }

  public int getStopnumber(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return 0;
    return getSegment(index).stopnumber;
  }

  void putSegment(SkSegment ss)
  {
    this.segments.addElement(ss);
    this.s_number += 1;
  }

  public void removeSegmentAt(int index)
  {
    if ((index < 0) || (index > this.s_number))
      return;

    this.segments.removeElementAt(index);
    this.s_number -= 1;
  }

  public String getFlightClasses(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).flightclasses;
  }
}
package com.travelsky.ibe.client;

import java.util.Date;
import java.util.Vector;

public class AvItem extends IBEResult
{
  private static final long serialVersionUID = -4944407197714742042L;
  private int s_number = 0;
  private Vector segments = new Vector();

  public String getAirline(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).airline;
  }

  public Date getArridate(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).arridate;
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

  public String getCangweiinfoOf(int index, char cangweicode)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    AvSegment as = getSegment(index);

    for (int i = 0; i < 26; ++i)
      if (as.cangwei_index[i] == cangweicode)
        return as.cangwei_data[i];

    return null;
  }

  public Date getDepdate(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return getSegment(index).depdate;
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

  /**
   * @deprecated
   */
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

  public AvSegment getSegment(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return null;
    return ((AvSegment)this.segments.get(index));
  }

  public int getSegmentnumber()
  {
    return this.s_number;
  }

  public char getSelectedclass(int index)
  {
    if ((index > this.s_number) || (index < 0))
      return ' ';
    return getSegment(index).selectedClass;
  }

  public int getStopnumber(int index)
  {
    if ((index > this.s_number) || (index < 0))
      return 0;
    return getSegment(index).stopnumber;
  }

  /**
   * @deprecated
   */
  public boolean ifOpen(int index)
  {
    if ((index > this.s_number) || (index < 0))
      return false;
    return getSegment(index).open;
  }

  public void putSegment(AvSegment as)
  {
    this.segments.addElement(as);
    this.s_number += 1;
  }

  public void putSelectedclass(int index, char inputedclass)
  {
    if ((index >= this.s_number) || (index < 0))
      return;
    AvSegment as = getSegment(index);
    as.selectedClass = inputedclass;
  }

  public void removeSegmentAt(int index)
  {
    if ((index < 0) || (index >= this.s_number))
      return;

    this.segments.removeElementAt(index);
    this.s_number -= 1;
  }

  /**
   * @deprecated
   */
  public void setOpen(int index)
  {
    if ((index >= this.s_number) || (index < 0))
      return;

    AvSegment as = getSegment(index);
    as.open = true;
  }
}
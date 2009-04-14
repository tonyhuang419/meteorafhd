package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PNREnvelop extends IBEResult
{
  private static final long serialVersionUID = -3353162674605409719L;
  String agent = "";
  int envelopIdx = 0;
  Calendar envelopTime = new GregorianCalendar();
  String envelopType = "";
  String office = "";

  public String getAgent()
  {
    return this.agent;
  }

  public int getEnvelopIdx()
  {
    return this.envelopIdx;
  }

  public Calendar getEnvelopTime()
  {
    return this.envelopTime;
  }

  public String getEnvelopType()
  {
    return this.envelopType;
  }

  public String getOffice()
  {
    return this.office;
  }

  public void setAgent(String string)
  {
    this.agent = string;
  }

  public void setEnvelopIdx(int i)
  {
    this.envelopIdx = i;
  }

  public void setEnvelopTime(Calendar calendar)
  {
    this.envelopTime = calendar;
  }

  public void setEnvelopType(String string)
  {
    this.envelopType = string;
  }

  public void setOffice(String string)
  {
    this.office = string;
  }

  public String toString() {
    try {
      return this.envelopIdx + " " + this.office + " " + this.agent + QDateTime.calendarToString(this.envelopTime, " ddmmmyy hhmi ") + ((this.envelopType == null) ? "" : this.envelopType); } catch (Exception e) {
    }
    return toString();
  }
}
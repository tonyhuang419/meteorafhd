package com.travelsky.ibe.client;

import com.travelsky.util.QDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class FFResult extends IBEResult
{
  private static final long serialVersionUID = -3361194406799117377L;
  String airNo;
  Date flightDate;
  String planeModel;
  private Vector vCity;
  private Vector vToTime;
  private Vector vFromTime;

  public FFResult()
  {
    this.airNo = "";
    this.flightDate = null;
    this.planeModel = "";
    this.vCity = new Vector();
    this.vFromTime = new Vector();
    this.vToTime = new Vector();
  }

  public String getAirNo()
  {
    return this.airNo;
  }

  public String getCity(int index)
  {
    if ((index < 0) || (index >= this.vCity.size()))
      return null;

    return ((String)this.vCity.elementAt(index));
  }

  public int getCityNumber()
  {
    return this.vCity.size();
  }

  public Date getFlightDate()
  {
    return this.flightDate;
  }

  public String getPlaneModel()
  {
    return this.planeModel;
  }

  public void setDate(String datestring)
    throws Exception
  {
    try
    {
      if (datestring.length() == 5)
      {
        Date date = new Date();

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(5, -1);
        date = cl.getTime();
        this.flightDate = 
          QDateTime.stringToDate(datestring, "DDMMM");
        if (!(this.flightDate.before(date))) return;
        cl.setTime(this.flightDate);
        cl.add(1, 1);
        this.flightDate = cl.getTime(); return;
      }

      if (datestring.length() == 7) {
        this.flightDate = 
          QDateTime.stringToDate(datestring, "DDMMMYY");

        return;
      }

      throw new Exception("bad date string format");
    } catch (Exception e) {
      throw new Exception(e.toString() + " in setDate of MultiResult");
    }
  }

  public String toString()
  {
    StringBuffer strtmp = new StringBuffer();
    if (this == null)
      return "ff is null";

    try
    {
      strtmp.append("航班号：" + this.airNo);
      strtmp.append("    日期：" + this.flightDate);
      strtmp.append("   机型：" + this.planeModel + '\n');
      for (int i = 0; i < this.vCity.size(); ++i) {
        strtmp.append("城市：" + ((String)this.vCity.elementAt(i)) + "    ");
        strtmp.append("到达时间：" + ((Date)this.vToTime.elementAt(i)) + "   ");
        strtmp.append("起飞时间：" + ((Date)this.vFromTime.elementAt(i)) + "  \n");
      }
      return strtmp.toString(); } catch (Exception e) {
    }
    return null;
  }

  public void addItem(String city, Date fromTime, Date toTime)
  {
    this.vCity.addElement(city);
    this.vFromTime.addElement(fromTime);
    this.vToTime.addElement(toTime);
  }

  public Date getFromTime(int index)
  {
    if ((index < 0) || (index >= this.vFromTime.size()))
      return null;

    return ((Date)this.vFromTime.elementAt(index));
  }

  public Date getToTime(int index)
  {
    if ((index < 0) || (index >= this.vToTime.size()))
      return null;

    return ((Date)this.vToTime.elementAt(index));
  }
}
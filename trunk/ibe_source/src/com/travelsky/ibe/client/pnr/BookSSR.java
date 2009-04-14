package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class BookSSR extends IBEResult
{
  private static final long serialVersionUID = -2611653679752241440L;
  private String serveCode = "";
  private String airCode = "";
  private String actionCode = "";
  private int person = 0;
  private String cityPair = "";
  private String airNo = "";
  private char fltClass = '$';
  private Date departureTime = new Date(1L);
  private String serveInfo = "";
  private String psgrName = "";

  public BookSSR()
  {
  }

  public BookSSR(String serveCode, String serveInfo)
    throws Exception
  {
    this.serveCode = serveCode;
    this.serveInfo = serveInfo;
  }

  public BookSSR(String serveCode, String airCode, String actionCode, int person, String cityPair, String airNo, char fltClass, Date departureTime, String serveInfo, String psgrName)
    throws Exception
  {
    this.serveCode = serveCode;
    this.airCode = airCode;
    this.actionCode = actionCode;
    this.person = person;
    this.cityPair = cityPair;
    this.airNo = airNo;
    this.fltClass = fltClass;
    this.departureTime = departureTime;
    this.serveInfo = serveInfo;
    this.psgrName = psgrName;
  }

  public BookSSR(String serveCode, String airCode, String actionCode, int person, String cityPair, String airNo, char fltClass, String departureTime, String serveInfo, String psgrName)
    throws Exception
  {
    this.serveCode = serveCode;
    this.airCode = airCode;
    this.actionCode = actionCode;
    this.person = person;
    this.cityPair = cityPair;
    this.airNo = airNo;
    this.fltClass = fltClass;
    setDepartureTime(departureTime);
    this.serveInfo = serveInfo;
    this.psgrName = psgrName;
  }

  public void setServeCode(String serveCode)
  {
    this.serveCode = serveCode;
  }

  String getServeCode() {
    return this.serveCode;
  }

  public void setAirCode(String airCode)
  {
    this.airCode = airCode;
  }

  String getAirCode() {
    return this.airCode;
  }

  public void setActionCode(String actionCode)
  {
    this.actionCode = actionCode;
  }

  String getActionCode() {
    return this.actionCode;
  }

  public void setPerson(int person)
  {
    this.person = person;
  }

  int getPerson() {
    return this.person;
  }

  public void setCityPair(String cityPair)
  {
    this.cityPair = cityPair;
  }

  String getCityPair() {
    return this.cityPair;
  }

  public void setAirNo(String airNo)
  {
    this.airNo = airNo;
  }

  String getAirNo() {
    return this.airNo;
  }

  public void setFltClass(char fltClass)
  {
    this.fltClass = fltClass;
  }

  char getFltClass() {
    return this.fltClass;
  }

  public void setDepartureTime(Date departureTime)
  {
    this.departureTime = departureTime;
  }

  public void setDepartureTime(String departureTime)
    throws Exception
  {
    try
    {
      if (departureTime == null)
        return;

      if (departureTime.equals("")) return;
      this.departureTime = QDateTime.stringToDate(departureTime, "YYYY-MM-DD");
    }
    catch (Exception e) {
      throw e;
    }
  }

  Date getDepartureTime() {
    return this.departureTime;
  }

  String getDepartureTimeString() {
    return this.departureTime.toString();
  }

  public void setServeInfo(String serveInfo)
  {
    this.serveInfo = serveInfo;
  }

  String getServeInfo() {
    return this.serveInfo;
  }

  public void setpsgrName(String psgrName)
  {
    this.psgrName = psgrName;
  }

  String getpsgrName() {
    return this.psgrName;
  }
}
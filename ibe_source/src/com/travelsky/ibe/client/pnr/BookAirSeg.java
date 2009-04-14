package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class BookAirSeg extends IBEResult
{
  private static final long serialVersionUID = -2125118652145977081L;
  public static final int AIRSEG_NORMAL = 0;
  public static final int AIRSEG_ARNK = 1;
  public static final int AIRSEG_OPEN = 2;
  private String airNo;
  private char fltClass;
  private String orgCity;
  private String desCity;
  private String actionCode;
  private int tktNum;
  private Date departureTime;
  private boolean skChanged;
  private int type;

  public BookAirSeg()
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createOpenAirSeg(String airNo, char fltClass, String orgCity, String desCity)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg();
    airseg.setAirNo(airNo);
    airseg.setFltClass(fltClass);
    airseg.setOrgCity(orgCity);
    airseg.setDesCity(desCity);
    airseg.setType(2);
    return airseg;
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createOpenAirSeg(char fltClass, String orgCity, String desCity)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg();
    airseg.setFltClass(fltClass);
    airseg.setOrgCity(orgCity);
    airseg.setDesCity(desCity);
    airseg.setType(2);
    return airseg;
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createInfoAirSeg(String orgCity, String desCity, Date departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg();
    airseg.setDepartureTime(departureTime);
    airseg.setOrgCity(orgCity);
    airseg.setDesCity(desCity);
    airseg.setType(1);
    return airseg;
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createInfoAirSeg(String orgCity, String desCity, String departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg();
    airseg.setDepartureTime(departureTime);
    airseg.setOrgCity(orgCity);
    airseg.setDesCity(desCity);
    airseg.setType(1);
    return airseg;
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createInfoAirSeg(String orgCity, String desCity) throws Exception
  {
    BookAirSeg airseg = new BookAirSeg();
    airseg.setOrgCity(orgCity);
    airseg.setDesCity(desCity);
    airseg.setType(1);
    return airseg;
  }

  public BookAirSeg(String orgCity, String desCity, String departureTime)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.orgCity = orgCity;
      this.desCity = desCity;
      setDepartureTime(departureTime);
      this.type = 1;
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(String orgCity, String desCity)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.orgCity = orgCity;
      this.desCity = desCity;
      this.type = 1;
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(String orgCity, String desCity, Date departureTime)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.orgCity = orgCity;
      this.desCity = desCity;
      this.departureTime = departureTime;
      this.type = 1;
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, String departureTime, int type)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.airNo = airNo;
      this.fltClass = fltClass;
      this.orgCity = orgCity;
      this.desCity = desCity;
      this.actionCode = actionCode;
      this.tktNum = tktNum;
      setDepartureTime(departureTime);
      this.type = (((type != 1) && (type != 2)) ? 0 : type);
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, Date departureTime, int type)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.airNo = airNo;
      this.fltClass = fltClass;
      this.orgCity = orgCity;
      this.desCity = desCity;
      this.actionCode = actionCode;
      this.tktNum = tktNum;
      this.departureTime = departureTime;
      this.type = (((type != 1) && (type != 2)) ? 0 : type);
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, String departureTime)
    throws Exception
  {
    this(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
  }

  public BookAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, Date departureTime)
    throws Exception
  {
    this(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, Date departureTime)
    throws Exception
  {
    BookAirSeg airseg;
    try
    {
      airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
      return airseg;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * @deprecated
   */
  public static BookAirSeg createAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, String departureTime)
    throws Exception
  {
    BookAirSeg airseg;
    try
    {
      airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
      return airseg;
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(String airNo, char fltClass, String orgCity, String desCity)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.airNo = airNo;
      this.fltClass = fltClass;
      this.orgCity = orgCity;
      this.desCity = desCity;
      this.type = 2;
    } catch (Exception e) {
      throw e;
    }
  }

  public BookAirSeg(char fltClass, String orgCity, String desCity)
    throws Exception
  {
    this.airNo = "";
    this.fltClass = '$';
    this.orgCity = "";
    this.desCity = "";
    this.actionCode = "";
    this.tktNum = 0;
    this.departureTime = new Date(1L);
    this.skChanged = false;
    this.type = 0;
    try
    {
      this.fltClass = fltClass;
      this.orgCity = orgCity;
      this.desCity = desCity;
      this.type = 2;
    } catch (Exception e) {
      throw e;
    }
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

  public void setOrgCity(String orgCity)
  {
    this.orgCity = orgCity;
  }

  String getOrgCity() {
    return this.orgCity;
  }

  public void setDesCity(String desCity)
  {
    this.desCity = desCity;
  }

  String getDesCity() {
    return this.desCity;
  }

  public void setActionCode(String actionCode)
  {
    this.actionCode = actionCode;
  }

  String getActionCode() {
    return this.actionCode;
  }

  public void setTktNum(int tktNum)
  {
    this.tktNum = tktNum;
  }

  int getTktNum() {
    return this.tktNum;
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
      if (!(departureTime.equals(""))) {
        this.departureTime = QDateTime.stringToDate(departureTime, "YYYY-MM-DD"); return;
      }
      throw new Exception("Error BookAirSeg departureTime");
    } catch (Exception e) {
      throw e;
    }
  }

  Date getDepartureTime() {
    return this.departureTime;
  }

  String getDepartureTimeString() {
    return this.departureTime.toString();
  }

  public void setType(int type)
  {
    this.type = type;
  }

  int getType() {
    return this.type; }

  public boolean isSkChanged() {
    return this.skChanged; }

  void setSkChanged(boolean skChanged) {
    this.skChanged = skChanged;
  }
}
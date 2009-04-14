package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class PNRAirSeg extends IBEResult
{
  private static final long serialVersionUID = 5132285697959801699L;
  public static final int AIRSEG_ARNK = 1;
  public static final int AIRSEG_NORMAL = 2;
  public static final int AIRSEG_OPEN = 3;
  private String airNo;
  private char fltClass;
  private String orgCity;
  private String desCity;
  private String actionCode;
  private int tktNum;
  private Date departureTime;
  private Date arrivalTime;
  private boolean skChanged;
  protected int type;
  protected boolean codeShare = false;
  protected String operateCarrier;
  private boolean etktairseg = false;
  int lineIndex;
  private String originalAirSeg = null;

  public PNRAirSeg()
  {
  }

  public PNRAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, Date departureTime, Date arrivalTime, boolean skChanged, int type)
    throws Exception
  {
    this.airNo = airNo;
    this.fltClass = fltClass;
    this.orgCity = orgCity;
    this.desCity = desCity;
    this.actionCode = actionCode;
    this.tktNum = tktNum;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.skChanged = skChanged;
    this.type = type;
  }

  void setAirNo(String airNo)
  {
    this.airNo = airNo;
  }

  public String getAirNo()
  {
    return this.airNo;
  }

  void setFltClass(char fltClass) {
    this.fltClass = fltClass;
  }

  public char getFltClass()
  {
    return this.fltClass;
  }

  void setOrgCity(String orgCity) {
    this.orgCity = orgCity;
  }

  public String getOrgCity()
  {
    return this.orgCity;
  }

  void setDesCity(String desCity) {
    this.desCity = desCity;
  }

  public String getDesCity()
  {
    return this.desCity;
  }

  void setActionCode(String actionCode) {
    this.actionCode = actionCode;
  }

  public String getActionCode()
  {
    return this.actionCode;
  }

  void setTktNum(int tktNum) {
    this.tktNum = tktNum;
  }

  public int getTktNum()
  {
    return this.tktNum;
  }

  void setDepartureTime(Date departureTime) {
    this.departureTime = departureTime;
  }

  void setDepartureTime(String departureTime) throws Exception
  {
    try {
      this.departureTime = QDateTime.stringToDate(departureTime, 
        "YYYYMMDD HH:MI");
    } catch (Exception e) {
      throw e;
    }
  }

  public Date getDepartureTime()
  {
    return this.departureTime;
  }

  public String getDepartureTimeString()
  {
    if (this.departureTime == null)
      return null;

    return this.departureTime.toString();
  }

  void setArrivalTime(Date arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  void setArrivalTime(String arrivalTime) throws Exception
  {
    try {
      this.arrivalTime = QDateTime.stringToDate(arrivalTime, 
        "YYYYMMDD HH:MI");
    } catch (Exception e) {
      throw e;
    }
  }

  public Date getArrivalTime()
  {
    return this.arrivalTime;
  }

  public String getArrivalTimeString()
  {
    if (this.arrivalTime == null)
      return null;

    return this.arrivalTime.toString();
  }

  void setSkChanged(boolean skChanged) {
    this.skChanged = skChanged;
  }

  /**
   * @deprecated
   */
  public boolean getSkChanged()
  {
    return this.skChanged;
  }

  public boolean isSkChanged()
  {
    return this.skChanged;
  }

  void setType(int type) {
    this.type = type;
  }

  public int getType()
  {
    return this.type;
  }

  public boolean isEtktairseg()
  {
    return this.etktairseg;
  }

  void setEtktairseg(boolean newEtktairseg)
  {
    this.etktairseg = newEtktairseg;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String toString()
  {
    if (this.originalAirSeg == null)
      return toString();
    return this.originalAirSeg;
  }

  public String getOriginalAirSeg()
  {
    return this.originalAirSeg;
  }

  void setOriginalAirSeg(String newOriginalAirSeg)
  {
    this.originalAirSeg = newOriginalAirSeg.trim(); }

  public boolean isCodeShare() {
    return this.codeShare;
  }

  public void setCodeShare(boolean codeShare) {
    this.codeShare = codeShare;
  }

  public void setOperateCarrier(String operateCarrier)
  {
    this.operateCarrier = operateCarrier;
  }

  public String getOperateCarrier() {
    return this.operateCarrier;
  }
}
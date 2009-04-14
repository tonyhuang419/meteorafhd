package com.travelsky.ibe.client.pnr;

public class PNRSSR_FOID extends PNRSSR
{
  private static final long serialVersionUID = 1664510506480610813L;
  int personNum = 0;
  String idtype = null;
  String idNo = null;

  public String getActionCode()
  {
    return this.actionCode;
  }

  public String getAirline()
  {
    return this.airline;
  }

  public String getIdNo()
  {
    return this.idNo;
  }

  public String getIdtype()
  {
    return this.idtype;
  }

  public int getPersonNum()
  {
    return this.personNum;
  }

  public void setActionCode(String newActionCode)
  {
    this.actionCode = newActionCode;
  }

  public void setAirline(String newAirline)
  {
    this.airline = newAirline;
  }

  public void setIdNo(String newIdNo)
  {
    this.idNo = newIdNo;
  }

  public void setIdtype(String newIdtype)
  {
    this.idtype = newIdtype;
  }

  public void setPersonNum(int newPersonNum)
  {
    this.personNum = newPersonNum;
  }

  public String toString()
  {
    try
    {
      return "SSR  " + 
        this.SSRType + 
        "  " + 
        this.airline + 
        "  " + 
        this.actionCode + 
        " " + 
        this.personNum + 
        "  " + 
        this.idtype + 
        " " + 
        this.idNo; } catch (Exception e) {
    }
    return super.toString();
  }
}
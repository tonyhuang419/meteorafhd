package com.travelsky.ibe.client;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class FDResult extends IBEResult
{
  private static final long serialVersionUID = 8405092682403491652L;
  private Fares fare;
  private String dstCity;
  private String orgCity;
  private Vector v_Airline;
  private Vector v_CabinDesc;
  private Vector v_CabinType;
  private Vector v_BasicCabinType;
  private Vector v_EndDate;
  private Vector v_MoneyType;
  private Vector v_RoundPrice;
  private Vector v_SinglePrice;
  private Vector v_StartDate;
  private int return_i;
  private Vector v_Rules;
  private Vector v_AirportTax;
  private Vector v_FuelTax;
  private Vector v_DiscountRate;

  /**
   * @deprecated
   */
  String AirlineNo;
  String remark;

  public FDResult()
  {
    this.orgCity = null;
    this.dstCity = null;
    this.AirlineNo = "--";
    this.v_CabinDesc = new Vector();
    this.v_CabinType = new Vector();
    this.v_EndDate = new Vector();
    this.v_MoneyType = new Vector();
    this.v_RoundPrice = new Vector();
    this.v_SinglePrice = new Vector();
    this.v_StartDate = new Vector();
    this.v_Rules = new Vector();
    this.v_AirportTax = new Vector();
    this.v_FuelTax = new Vector();
    this.return_i = 0;
    this.remark = null;
    this.v_Airline = new Vector();
    this.v_BasicCabinType = new Vector();
    this.v_DiscountRate = new Vector();
    this.fare = new Fares();
  }

  public int getElementNum()
  {
    return this.return_i;
  }

  public void setOrg(String org)
  {
    this.orgCity = org;
  }

  public String getOrg()
  {
    return this.orgCity;
  }

  public void setDst(String dst)
  {
    this.dstCity = dst;
  }

  public String getDst()
  {
    return this.dstCity;
  }

  public void putItem(String cabinType, String cabinDesc, String singlePrice, String roundPrice, String startDate, String endDate, String moneyType, String basicCabin, String discountRate, String airline, String rule, String airportTax, String fuelTax)
    throws Exception
  {
    this.v_Airline.addElement(airline);
    this.v_Rules.addElement(rule);
    this.v_CabinDesc.addElement(cabinDesc);
    this.v_CabinType.addElement(cabinType);
    this.v_StartDate.addElement(startDate);
    this.v_EndDate.addElement(endDate);
    this.v_SinglePrice.addElement(singlePrice);
    this.v_RoundPrice.addElement(roundPrice);
    this.v_MoneyType.addElement(moneyType);
    this.v_AirportTax.addElement(airportTax);
    this.v_BasicCabinType.addElement(basicCabin);
    this.v_DiscountRate.addElement(discountRate);
    this.v_FuelTax.addElement(fuelTax);
    FDItem it = new FDItem();
    it.setAirline(airline);
    it.setBasicCabin(basicCabin);
    it.setCabin(cabinType);
    it.setFareBasis(cabinDesc);
    it.setCurrency(moneyType);
    it.setAirportTax(airportTax);
    it.setDiscountRate(discountRate);
    it.setDstCity(this.dstCity);
    it.setFuelTax(fuelTax);
    it.setInvalidDate(endDate);
    it.setOnewayPrice(singlePrice);
    it.setOrgCity(this.orgCity);
    it.setRoundtripPrice(roundPrice);
    it.setRule(rule);
    it.setValidDate(startDate);

    if (this.fare != null)
      this.fare.insertFare(it);
    this.return_i += 1;
  }

  /**
   * @deprecated
   */
  public void putItem(String cabinType, String cabinDesc, String singlePrice, String roundPrice, String startDate, String endDate, String moneyType, String basicCabin, String discountRate, String airline, String rule, String airportTax)
    throws Exception
  {
    this.v_Airline.addElement(airline);
    this.v_Rules.addElement(rule);
    this.v_CabinDesc.addElement(cabinDesc);
    this.v_CabinType.addElement(cabinType);
    this.v_StartDate.addElement(startDate);
    this.v_EndDate.addElement(endDate);
    this.v_SinglePrice.addElement(singlePrice);
    this.v_RoundPrice.addElement(roundPrice);
    this.v_MoneyType.addElement(moneyType);
    this.v_AirportTax.addElement(airportTax);
    this.v_BasicCabinType.addElement(basicCabin);
    this.v_DiscountRate.addElement(discountRate);
    this.v_FuelTax.addElement(null);
    FDItem it = new FDItem();
    it.setAirline(airline);
    it.setBasicCabin(basicCabin);
    it.setCabin(cabinType);
    it.setFareBasis(cabinDesc);
    it.setCurrency(moneyType);
    it.setAirportTax(airportTax);
    it.setDiscountRate(discountRate);
    it.setDstCity(this.dstCity);

    it.setInvalidDate(endDate);
    it.setOnewayPrice(singlePrice);
    it.setOrgCity(this.orgCity);
    it.setRoundtripPrice(roundPrice);
    it.setRule(rule);
    it.setValidDate(startDate);
    if (this.fare != null)
      this.fare.insertFare(it);
    this.return_i += 1;
  }

  /**
   * @deprecated
   */
  public void putItem(String cabinType, String cabinDesc, String singlePrice, String roundPrice, String startDate, String endDate, String moneyType, String airline, String rule, String airportTax)
    throws Exception
  {
    this.v_Airline.addElement(airline);
    this.v_Rules.addElement(rule);
    this.v_CabinDesc.addElement(cabinDesc);
    this.v_CabinType.addElement(cabinType);
    this.v_StartDate.addElement(startDate);
    this.v_EndDate.addElement(endDate);
    this.v_SinglePrice.addElement(singlePrice);
    this.v_RoundPrice.addElement(roundPrice);
    this.v_MoneyType.addElement(moneyType);
    this.v_AirportTax.addElement(airportTax);
    FDItem it = new FDItem();
    it.setAirline(airline);

    it.setCabin(cabinType);
    it.setFareBasis(cabinDesc);
    it.setCurrency(moneyType);
    it.setAirportTax(airportTax);

    it.setDstCity(this.dstCity);

    it.setInvalidDate(endDate);
    it.setOnewayPrice(singlePrice);
    it.setOrgCity(this.orgCity);
    it.setRoundtripPrice(roundPrice);
    it.setRule(rule);
    it.setValidDate(startDate);
    if (this.fare != null)
      this.fare.insertFare(it);
    this.return_i += 1;
  }

  /**
   * @deprecated
   */
  public void putItem(String cabinType, String cabinDesc, String singlePrice, String roundPrice, String startDate, String endDate, String moneyType, String basicCabin, String discountRate)
    throws Exception
  {
    this.v_CabinDesc.addElement(cabinDesc);
    this.v_CabinType.addElement(cabinType);
    this.v_StartDate.addElement(startDate);
    this.v_EndDate.addElement(endDate);
    this.v_SinglePrice.addElement(singlePrice);
    this.v_RoundPrice.addElement(roundPrice);
    this.v_MoneyType.addElement(moneyType);
    this.v_BasicCabinType.addElement(basicCabin);
    this.v_DiscountRate.addElement(discountRate);
    FDItem it = new FDItem();

    it.setBasicCabin(basicCabin);
    it.setCabin(cabinType);
    it.setFareBasis(cabinDesc);
    it.setCurrency(moneyType);

    it.setDiscountRate(discountRate);
    it.setDstCity(this.dstCity);

    it.setInvalidDate(endDate);
    it.setOnewayPrice(singlePrice);
    it.setOrgCity(this.orgCity);
    it.setRoundtripPrice(roundPrice);

    it.setValidDate(startDate);
    if (this.fare != null)
      this.fare.insertFare(it);
    this.return_i += 1;
  }

  /**
   * @deprecated
   */
  public String getAirline()
  {
    return this.AirlineNo;
  }

  /**
   * @deprecated
   */
  public String getCabinDesc(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getFareBasis();
  }

  public String getFareBasis(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getFareBasis();
  }

  public String getDiscountRate(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getDiscountRate();
  }

  public String getBasicCabinType(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getBasicCabin();
  }

  public String getCabinType(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getCabin();
  }

  public String getAirline(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getAirline();
  }

  public String getRule(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getRule();
  }

  public String getAirportTax(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getAirportTax();
  }

  public String getFuelTax(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getFuelTax();
  }

  public char getCabinTYPE(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return ' ';

    return this.fare.getFD(index).getCabin().charAt(0);
  }

  public String getEndDate(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getInvalidDate();
  }

  public String getMoneyType(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getCurrency();
  }

  public String getRoundPrice(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getRoundtripPrice();
  }

  public String getSinglePrice(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getOnewayPrice();
  }

  public String getStartDate(int index)
  {
    if ((index < 0) || (index >= this.return_i))
      return "";

    return this.fare.getFD(index).getValidDate();
  }

  public int getIndexByCabinAndAirline(String airline, String cabin)
  {
    for (int i = 0; i < this.return_i; ++i)
      if ((this.fare.getFD(i).getCabin().equalsIgnoreCase(cabin)) && (airline.toUpperCase().startsWith(this.fare.getFD(i).getAirline())))
        return i;

    return -1;
  }

  /**
   * @deprecated
   */
  public int[] getIndexOfType(String cabin)
    throws Exception
  {
    String temp = "";
    for (int i = 0; i < this.return_i; ++i)
      if (this.fare.getFD(i).getCabin() + this.fare.getFD(i).getCabinDesc().equalsIgnoreCase(cabin))
        temp = temp + String.valueOf(i) + " ";

    if (temp.length() == 0)
      return null;

    temp = temp.trim();
    StringTokenizer st = new StringTokenizer(temp, " ");
    int[] ret = new int[st.countTokens()];
    int j = 0;
    while (st.hasMoreElements()) {
      ret[j] = Integer.parseInt((String)st.nextElement());
      ++j;
    }
    return ret;
  }

  public String toString()
  {
    StringBuffer strtmp = new StringBuffer();
    try {
      strtmp.append("始发地:" + getOrg());
      strtmp.append("    到达地:" + getDst());
      strtmp.append("    查询航空公司:" + getAirline() + "\n\n");

      for (int i = 0; i < getElementNum(); ++i) {
        strtmp.append(getCabinType(i));
        strtmp.append("  ");
        strtmp.append(getCabinDesc(i));
        for (int j = 10; j > getCabinDesc(i).length(); --j)
          strtmp.append(" ");

        strtmp.append(getBasicCabinType(i));
        for (j = 3; j > getBasicCabinType(i).length(); --j)
          strtmp.append(" ");

        strtmp.append(getDiscountRate(i));
        for (j = 6; j > getDiscountRate(i).length(); --j)
          strtmp.append(" ");

        strtmp.append(getSinglePrice(i));
        for (j = 10; j > getSinglePrice(i).length(); --j)
          strtmp.append(" ");

        strtmp.append(getRoundPrice(i));
        for (j = 10; j > getRoundPrice(i).length(); --j)
          strtmp.append(" ");

        strtmp.append(getStartDate(i) + " ");
        strtmp.append(getEndDate(i));
        for (j = 10; j > getEndDate(i).length(); --j)
          strtmp.append(" ");

        strtmp.append(getMoneyType(i) + "   " + getAirline(i) + "  " + getRule(i) + "  " + getAirportTax(i) + "  " + getFuelTax(i) + "\n");
      }
      return strtmp.toString();
    } catch (Exception e) {
      e.printStackTrace(); }
    return null;
  }

  public String getRemark()
  {
    return this.remark;
  }

  /**
   * @deprecated
   */
  public void setAirlineNo(String newAirlineNo)
  {
    this.AirlineNo = newAirlineNo;
  }

  public void setRemark(String newRemark)
  {
    this.remark = newRemark; }

  public void setFare(Fares fare) {
    this.fare = fare;
  }

  public List getFareOfCabin(String airline, String cabin, Date date)
  {
    if ((airline == null) || (airline.length() < 2))
      return null;
    if (airline.length() > 2)
      airline = airline.substring(0, 2);
    return this.fare.getFDs(airline, cabin, date);
  }
}
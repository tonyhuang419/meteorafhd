package com.travelsky.ibe.client;

import java.util.Date;

public class AvSegment extends IBEResult
{
  private static final long serialVersionUID = 3071438967066419245L;
  String airline = null;
  Date arridate = null;
  private String arriTerm = "--";
  String arritime = null;
  String arritimemodify = null;
  boolean asr = false;
  String[] cangwei_data = new String[26];
  char[] cangwei_data_sort;
  char[] cangwei_index = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
  char[] cangwei_index_sort;
  private String Carrier = null;
  private boolean codeShare = false;

  /**
   * @deprecated
   */
  boolean codeShareInternational = false;
  Date depdate = null;
  private String depTerm = "--";
  String deptime = null;
  String deptimemodify = null;
  String dstcity = null;
  boolean etkt = false;
  String link = null;
  boolean meal = false;

  /**
   * @deprecated
   */
  boolean open = false;

  /**
   * @deprecated
   */
  String opento = null;
  String orgcity = null;
  String planestyle = null;
  char selectedClass = '$';
  int stopnumber = 0;

  public AvSegment()
  {
    this.cangwei_index_sort = new char[0];
    this.cangwei_data_sort = new char[0];
  }

  /**
   * @deprecated
   */
  public boolean eTkt()
  {
    return this.etkt;
  }

  public String getAirline()
  {
    return this.airline;
  }

  public Date getArridate()
  {
    return this.arridate;
  }

  public String getArriTerm()
  {
    return this.arriTerm;
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

  public char getCangweiCodeSort(int index)
  {
    if ((index < 0) || (index > this.cangwei_index_sort.length - 1))
      return '-';

    return this.cangwei_index_sort[index];
  }

  public String getCangweiinfoOf(char cangweicode)
  {
    for (int i = 0; i < 26; ++i)
      if (this.cangwei_index[i] == cangweicode)
        return this.cangwei_data[i];

    return null;
  }

  public char getCangweiinfoOfSort(char c_index)
  {
    for (int i = 0; i < this.cangwei_index_sort.length; ++i)
    {
      if (this.cangwei_index_sort[i] == c_index)
        return this.cangwei_data_sort[i];
    }
    return '-';
  }

  public String getCarrier()
  {
    return this.Carrier;
  }

  public Date getDepdate()
  {
    return this.depdate;
  }

  public String getDepTerm()
  {
    return this.depTerm;
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

  /**
   * @deprecated
   */
  public boolean getEtkt()
    throws Exception
  {
    return this.etkt;
  }

  public String getLink()
  {
    return this.link;
  }

  public boolean getMeal()
  {
    return this.meal;
  }

  /**
   * @deprecated
   */
  public String getOpento()
  {
    return this.opento;
  }

  /**
   * @deprecated
   */
  public String getOperateBy() {
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

  public char getSelectedclass()
  {
    return this.selectedClass;
  }

  public int getStopnumber()
  {
    return this.stopnumber;
  }

  /**
   * @deprecated
   */
  public boolean ifOpen()
  {
    return this.open;
  }

  public boolean isCodeShare()
  {
    return this.codeShare;
  }

  /**
   * @deprecated
   */
  public boolean isCodeShareInternational()
  {
    return this.codeShareInternational;
  }

  public boolean isETkt()
  {
    return this.etkt;
  }

  /**
   * @deprecated
   */
  public boolean isOperateBy()
  {
    return this.open;
  }

  void putCangwei(char c_index, String c_data)
  {
    if (c_index == ' ')
      return;
    if ((c_data.equals(".")) || (c_data.equals("-")))
      return;
    for (int i = 0; i < 26; ++i)
      if (this.cangwei_index[i] == c_index)
        this.cangwei_data[i] = c_data;
  }

  boolean putCangweiSort(char c_index, char c_data)
  {
    c_index = String.valueOf(c_index).toUpperCase().charAt(0);
    if ((c_index < 'A') || (c_index > 'Z'))
      return false;

    for (int i = 0; i < this.cangwei_index_sort.length; ++i)
    {
      if (this.cangwei_index_sort[i] == c_index) {
        this.cangwei_data_sort[i] = c_data;
        return true;
      }

      if (this.cangwei_index_sort[i] == '-') {
        this.cangwei_index_sort[i] = c_index;
        this.cangwei_data_sort[i] = c_data;
        return true;
      }
    }

    return false;
  }

  public void putSelectedclass(char inputedclass)
  {
    this.selectedClass = inputedclass;
  }

  public void setAirline(String airline)
  {
    this.airline = airline;
  }

  public void setArridate(Date date)
  {
    this.arridate = date;
  }

  void setArriTerm(String string)
  {
    if ((string == null) || (string.length() == 0))
      this.arriTerm = "--";
    else
      this.arriTerm = string;
  }

  public void setArritime(String arritime)
  {
    this.arritime = arritime;
  }

  public void setArritimemodify(String atm)
  {
    this.arritimemodify = atm;
  }

  public void setAsr(boolean asr)
  {
    this.asr = asr;
  }

  public void setCangweiNumber(int num)
  {
    this.cangwei_index_sort = new char[num];
    this.cangwei_data_sort = new char[num];

    for (int i = 0; i < num; ++i) {
      this.cangwei_data_sort[i] = '-';
      this.cangwei_index_sort[i] = '-';
    }
  }

  void setCarrier(String string)
  {
    this.Carrier = string;
  }

  void setCodeShare(boolean b)
  {
    this.codeShare = b;
  }

  /**
   * @deprecated
   */
  void setCodeShareInternatioal(boolean newCodeShareInternational)
  {
    this.codeShareInternational = newCodeShareInternational;
  }

  public void setDepdate(Date date)
  {
    this.depdate = date;
  }

  void setDepTerm(String string)
  {
    if ((string == null) || (string.length() == 0))
      this.depTerm = "--";
    else
      this.depTerm = string;
  }

  public void setDeptime(String dt)
  {
    this.deptime = dt;
  }

  public void setDeptimemodify(String dtm)
  {
    this.deptimemodify = dtm;
  }

  public void setDstcity(String dst)
  {
    this.dstcity = dst;
  }

  public void setEtkt(boolean eTkt)
  {
    this.etkt = eTkt;
  }

  public void setLink(String link)
  {
    this.link = link;
  }

  public void setMeal(boolean meal)
  {
    this.meal = meal;
  }

  /**
   * @deprecated
   */
  public void setOpen()
  {
    this.open = true;
  }

  /**
   * @deprecated
   */
  public void setOpen(boolean open)
  {
    this.open = open;
  }

  /**
   * @deprecated
   */
  public void setOpento(String open)
  {
    this.opento = open;
  }

  public void setOrgcity(String org)
  {
    this.orgcity = org;
  }

  public void setPlanestyle(String pt)
  {
    this.planestyle = pt;
  }

  public void setSelectedclass(char sc)
  {
    this.selectedClass = sc;
  }

  public void setStopnumber(int sn)
  {
    this.stopnumber = sn;
  }
}
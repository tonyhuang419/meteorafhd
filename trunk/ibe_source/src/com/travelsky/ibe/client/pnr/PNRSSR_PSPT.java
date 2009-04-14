package com.travelsky.ibe.client.pnr;

public class PNRSSR_PSPT extends PNRSSR
{
  private static final long serialVersionUID = 2531106049313347010L;
  String birth = null;
  char holder = ' ';
  boolean infant = false;
  String nationality = null;
  String paxName = null;
  int personNum = 0;
  String psptNo = null;
  char sex = ' ';

  public String getActionCode()
  {
    return this.actionCode;
  }

  public String getAirline()
  {
    return this.airline;
  }

  public String getBirth()
  {
    return this.birth;
  }

  public char getHolder()
  {
    return this.holder;
  }

  public String getNationality()
  {
    return this.nationality;
  }

  public String getPaxName()
  {
    return this.paxName;
  }

  public int getPersonNum()
  {
    return this.personNum;
  }

  public String getPsptNo()
  {
    return this.psptNo;
  }

  public char getSex()
  {
    return this.sex;
  }

  public boolean isInfant()
  {
    return this.infant;
  }

  void setActionCode(String newActionCode)
  {
    this.actionCode = newActionCode;
  }

  void setAirline(String newAirline)
  {
    this.airline = newAirline;
  }

  void setBirth(String newBirth)
  {
    this.birth = newBirth;
  }

  void setHolder(char newHolder)
  {
    this.holder = newHolder;
  }

  void setInfant(boolean newInfant)
  {
    this.infant = newInfant;
  }

  void setNationality(String newNationality)
  {
    this.nationality = newNationality;
  }

  void setPaxName(String newPaxName)
  {
    this.paxName = newPaxName;
  }

  void setPersonNum(int newPersonNum)
  {
    this.personNum = newPersonNum;
  }

  void setPsptNo(String newPsptNo)
  {
    this.psptNo = newPsptNo;
  }

  void setSex(char newSex)
  {
    this.sex = newSex;
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
        "  护照:" + 
        this.psptNo + 
        "  国籍代码:" + 
        this.nationality + 
        "  出生日期:" + 
        this.birth + 
        " 姓名:" + 
        this.paxName + 
        " 性别:" + 
        this.sex + 
        ((this.infant) ? " 婴儿 " : " ") + 
        " 持有人标识:" + 
        this.holder; } catch (Exception e) {
    }
    return super.toString();
  }
}
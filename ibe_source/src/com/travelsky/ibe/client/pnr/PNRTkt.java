package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class PNRTkt extends IBEResult
{
  private static final long serialVersionUID = 3084288696040770292L;
  private String type;
  private Date dateLimit;
  private String office;
  private String psgrID;
  int lineIndex;
  private String rmk;
  private boolean tkted = false;

  public PNRTkt()
    throws Exception
  {
  }

  public PNRTkt(String type, Date dateLimit, String office, String psgrID)
    throws Exception
  {
    this.type = type;
    this.dateLimit = dateLimit;
    this.office = office;
    this.psgrID = psgrID;
  }

  void setType(String type)
  {
    this.type = type;
  }

  public String getType()
  {
    return this.type;
  }

  void setDateLimit(Date dateLimit) {
    this.dateLimit = dateLimit;
  }

  void setDateLimit(String dateLimit) throws Exception
  {
    try {
      this.dateLimit = QDateTime.stringToDate(dateLimit, "YYYYMMDD");
    } catch (Exception e) {
      throw e;
    }
  }

  public Date getDateLimit()
  {
    return this.dateLimit;
  }

  public String getDateLimitString()
  {
    if (this.dateLimit == null)
      return null;

    return this.dateLimit.toString();
  }

  void setOffice(String office) {
    this.office = office;
  }

  public String getOffice()
  {
    return this.office;
  }

  void setPsgrID(String psgrID) {
    this.psgrID = psgrID;
  }

  public String getPsgrID()
  {
    return this.psgrID;
  }

  public PNRTkt(String type, Date dateLimit, String office, String psgrID, String rmk)
    throws Exception
  {
    this.type = type;
    this.dateLimit = dateLimit;
    this.office = office;
    this.psgrID = psgrID;
    this.rmk = rmk;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getRmk()
  {
    return this.rmk;
  }

  void setRmk(String newRmk)
  {
    this.rmk = newRmk;
  }

  public boolean isTkted()
  {
    return this.tkted;
  }

  void setTkted(boolean newTkted)
  {
    this.tkted = newTkted;
  }

  public String toString()
  {
    try
    {
      return getIndex() + "." + getType() + " " + 
        getDateLimitString() + " " + getOffice() + 
        getPsgrID() + ((this.tkted) ? " 已出票 " : "  ") + "  备注:" + 
        getRmk();
    } catch (Exception e) {
    }
    return toString();
  }
}
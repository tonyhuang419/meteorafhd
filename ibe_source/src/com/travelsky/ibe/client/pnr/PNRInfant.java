package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class PNRInfant extends IBEResult
{
  private static final long serialVersionUID = -1505425896029805069L;
  private Date brith;
  private String carrier;
  private String name;
  private String nameInPnr;
  int lineIndex;

  public PNRInfant()
    throws Exception
  {
  }

  public PNRInfant(Date brith, String carrier, String name)
    throws Exception
  {
    this.brith = brith;
    this.carrier = carrier;
    this.name = name;
  }

  public PNRInfant(String brith, String carrier, String name)
    throws Exception
  {
    try
    {
      setBrith(brith);
      this.carrier = carrier;
      this.name = name;
    } catch (Exception e) {
      throw e;
    }
  }

  void setBrith(Date brith)
  {
    this.brith = brith;
  }

  void setBrith(String brith) throws Exception
  {
    try {
      this.brith = QDateTime.stringToDate(brith, "YYYYMMDD");
    } catch (Exception e) {
      throw e;
    }
  }

  public Date getBrith()
  {
    return this.brith;
  }

  public String getBrithString()
  {
    return this.brith.toString();
  }

  void setCarrier(String carrier) {
    this.carrier = carrier;
  }

  public String getCarrier()
  {
    return this.carrier;
  }

  void setName(String name) {
    this.name = name;
  }

  public String getName()
  {
    return this.name;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getNameInPnr()
  {
    return this.nameInPnr;
  }

  void setNameInPnr(String nameInPnr) {
    this.nameInPnr = nameInPnr;
  }
}
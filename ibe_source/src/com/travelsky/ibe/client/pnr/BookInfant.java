package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class BookInfant extends IBEResult
{
  private static final long serialVersionUID = -1804280650985530111L;
  private Date birth = new Date();
  private String carrierName = "";
  private String name = "";

  public BookInfant()
  {
  }

  public BookInfant(Date birth, String carrierName, String name)
    throws Exception
  {
    this.birth = birth;
    this.carrierName = carrierName;
    this.name = name;
  }

  public BookInfant(String birth, String carrierName, String name)
    throws Exception
  {
    try
    {
      setBirth(birth);
      this.carrierName = carrierName;
      this.name = name;
    } catch (Exception e) {
      throw e;
    }
  }

  public void setBirth(Date birth)
  {
    this.birth = birth;
  }

  public void setBirth(String birth)
    throws Exception
  {
    try
    {
      if (!(birth.equals(""))) {
        this.birth = QDateTime.stringToDate(birth, "YYYY-MM-DD"); return;
      }
      throw new Exception("Error Infant birth");
    } catch (Exception e) {
      throw e;
    }
  }

  Date getBirth() {
    return this.birth;
  }

  String getBirthString() {
    return this.birth.toString();
  }

  public void setCarrierName(String carrierName)
  {
    this.carrierName = carrierName;
  }

  String getCarrierName() {
    return this.carrierName;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  String getName() {
    return this.name;
  }
}
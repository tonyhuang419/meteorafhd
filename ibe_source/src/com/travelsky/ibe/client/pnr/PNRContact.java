package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRContact extends IBEResult
{
  private static final long serialVersionUID = 6394776358300463091L;
  private String city;
  private String contact;
  int lineIndex;
  private String psgrID;

  public PNRContact()
    throws Exception
  {
  }

  public PNRContact(String city, String contact)
    throws Exception
  {
    this.city = city;
    this.contact = contact;
  }

  void setCity(String city)
  {
    this.city = city;
  }

  public String getCity()
  {
    return this.city;
  }

  void setContact(String contact) {
    this.contact = contact;
  }

  public String getContact()
  {
    return this.contact;
  }

  public PNRContact(String city, String contact, String psgrID)
    throws Exception
  {
    this.city = city;
    this.contact = contact;
    this.psgrID = psgrID;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getPsgrID()
  {
    return this.psgrID;
  }

  void setPsgrID(String newPsgrID)
  {
    this.psgrID = newPsgrID;
  }
}
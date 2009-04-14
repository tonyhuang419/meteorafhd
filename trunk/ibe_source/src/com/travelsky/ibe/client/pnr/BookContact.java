package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookContact extends IBEResult
{
  private static final long serialVersionUID = -8580716189730797555L;
  private String city = "";
  private String contact = "";
  private String psgrName = "";

  public BookContact()
  {
  }

  public BookContact(String city, String contact, String psgrName)
    throws Exception
  {
    this.city = city;
    this.contact = contact;
    this.psgrName = psgrName;
  }

  public BookContact(String city, String contact)
    throws Exception
  {
    this.city = city;
    this.contact = contact;
    this.psgrName = "";
  }

  public BookContact(String contact)
    throws Exception
  {
    this.contact = contact;
    this.city = "";
    this.psgrName = "";
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  String getCity() {
    return this.city;
  }

  public void setContact(String contact)
  {
    this.contact = contact;
  }

  String getContact() {
    return this.contact;
  }

  public void setpsgrName(String psgrName)
  {
    this.psgrName = psgrName;
  }

  String getpsgrName() {
    return this.psgrName;
  }
}
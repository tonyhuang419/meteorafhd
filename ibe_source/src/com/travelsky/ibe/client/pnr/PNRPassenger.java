package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRPassenger extends IBEResult
{
  public static final int ADULT = 1;
  public static final int CHILD = 2;
  public static final int DR = 128;
  public static final int GM = 32;
  public static final int INFANT = 0;
  public static final int JC = 64;
  public static final int MISS = 2048;
  public static final int MR = 256;
  public static final int MRS = 512;
  public static final int MS = 1024;
  public static final int VVIP = 8;
  public static final int PASSENGER_ADULT = 0;
  public static final int PASSENGER_CHILD = 1;
  public static final int PASSENGER_CHILD_UNACCOMPANIED = 2;
  public static final int PASSENGER_UNACCOMPANIED = 2;
  private static final long serialVersionUID = -1682684771971305380L;
  public static final int UNACCOMPANIED = 4;
  public static final int VIP = 16;
  private int age;
  int lineIndex;
  private String name;
  private String nameInPnr;
  private int passtype = 1;
  private int type;

  public PNRPassenger()
    throws Exception
  {
  }

  public PNRPassenger(int age, String name, int type)
    throws Exception
  {
    this.age = age;
    this.name = name;
    this.type = type;
  }

  public boolean equal(PNRPassenger pass)
  {
    try
    {
      if (pass == null)
        return false;

      if ((this.type == 0) && 
        (this.type == pass.type) && 
        (this.name.equalsIgnoreCase(pass.name)))
        return true;

      if ((this.type == 1) && 
        (this.type == pass.type) && 
        (this.name.equalsIgnoreCase(pass.name))) {
        return true;
      }

      return ((this.type == 2) && 
        (this.type == pass.type) && 
        (this.name.equalsIgnoreCase(pass.name)) && 
        (this.age == pass.age));
    }
    catch (Exception e)
    {
    }
    return false;
  }

  public boolean equals(Object o)
  {
    try
    {
      if (o == null)
        return false;
      if (o instanceof PNRPassenger) {
        PNRPassenger pass = (PNRPassenger)o;

        if ((this.type == 0) && 
          (this.type == pass.type) && 
          (this.name.equalsIgnoreCase(pass.name)))
          return true;

        if ((this.type == 1) && 
          (this.type == pass.type) && 
          (this.name.equalsIgnoreCase(pass.name)))
          return true;

        if ((this.type != 2) || 
          (this.type != pass.type) || 
          (!(this.name.equalsIgnoreCase(pass.name))) || 
          (this.age != pass.age)) break label274;
        return true; break label274: }
      if (o instanceof BookPassenger) {
        BookPassenger pass = (BookPassenger)o;

        if ((this.type == 0) && 
          (this.type == pass.getType()) && 
          (this.name.equalsIgnoreCase(pass.getName())))
          return true;

        if ((this.type == 1) && 
          (this.type == pass.getType()) && 
          (this.name.equalsIgnoreCase(pass.getName())))
          return true;

        if ((this.type != 2) || 
          (this.type != pass.getType()) || 
          (!(this.name.equalsIgnoreCase(pass.getName()))) || 
          (this.age != pass.getAge())) break label274;
        return true; break label274:
      }
      return false;
    } catch (Exception e) {
      return false;
    }
    label274: return false;
  }

  public int getAge()
  {
    return this.age;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getName()
  {
    return this.name;
  }

  public String getNameInPnr()
  {
    return this.nameInPnr;
  }

  public int getPassengerType()
  {
    return this.passtype;
  }

  public int getType()
  {
    return this.type;
  }

  public boolean isPassengerType(int type) {
    return ((this.passtype & type) != 0);
  }

  void setAge(int age)
  {
    this.age = age;
  }

  void setName(String name) {
    this.name = name;
  }

  void setNameInPnr(String nameInPnr) {
    this.nameInPnr = nameInPnr;
  }

  public void setPassengerType(int type, boolean b) {
    if (b)
      this.passtype |= type;
    else
      this.passtype &= (0xFFFFFFFF ^ type);
  }

  void setPassengerType(int i)
  {
    this.passtype = i;
  }

  void setType(int type) {
    this.type = type;
  }
}
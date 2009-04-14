package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class BookPassenger extends IBEResult
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
  public static final int VIP = 16;
  public static final int VVIP = 8;
  public static final int UNACCOMPANIED = 4;
  public static final int PASSENGER_ADULT = 0;
  public static final int PASSENGER_CHILD = 1;
  public static final int PASSENGER_CHILD_UNACCOMPANIED = 2;
  public static final int PASSENGER_UNACCOMPANIED = 2;
  private static final long serialVersionUID = 8168964114357001620L;
  private int age;
  private String name;
  private int passtype;
  private int type;

  public static BookPassenger createAdult(String name)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger();
    passenger.setName(name);
    passenger.setType(0);
    return passenger;
  }

  public static BookPassenger createChild(String name)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger();
    passenger.setName(name);
    passenger.setType(1);
    return passenger;
  }

  public static BookPassenger createChild(String name, int age)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger();
    passenger.setName(name);
    passenger.setAge(age);
    passenger.setType(2);
    return passenger; }

  public BookPassenger() {
    this.age = 0;

    this.name = "";

    this.passtype = 1;

    this.type = 0;
  }

  public BookPassenger(String name)
    throws Exception
  {
    this(name, 0, 0);
  }

  public BookPassenger(String name, int type, int age)
    throws Exception
  {
    this.age = 0;

    this.name = "";

    this.passtype = 1;

    this.type = 0;

    this.name = name;
    this.type = (((type != 1) && (type != 2)) ? 0 : type);
    this.age = age;
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

  int getAge() {
    return this.age;
  }

  String getName() {
    return this.name;
  }

  int getPassengerType()
  {
    return this.passtype;
  }

  int getType() {
    return this.type;
  }

  public boolean isPassengerType(int type)
  {
    return ((this.passtype & type) != 0);
  }

  public boolean hasNamePostfix(int postfix)
  {
    return ((this.passtype & postfix) != 0);
  }

  public void setAge(int age)
  {
    this.age = age;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setPassengerType(int type, boolean b) {
    if (b)
      this.passtype |= type;
    else
      this.passtype &= (0xFFFFFFFF ^ type);
  }

  public void setType(int type)
  {
    this.type = type;
  }
}
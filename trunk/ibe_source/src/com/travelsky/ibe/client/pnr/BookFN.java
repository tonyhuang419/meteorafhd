package com.travelsky.ibe.client.pnr;

import java.util.Vector;

public class BookFN extends FareBox
{
  private static final long serialVersionUID = -6441176416341475073L;
  Vector psgrname = new Vector();

  public BookFN()
  {
  }

  public BookFN(FareBox farebox, String name)
  {
    this.f = farebox.f;
    this.r = farebox.r;
    this.e = farebox.e;
    this.x = farebox.x;
    this.t = farebox.t;
    this.a = farebox.a;
    this.o = farebox.o;
    this.s = farebox.s;

    this.c = farebox.c;
    this.taxCnt = farebox.taxCnt;
    this.oTaxCnt = farebox.oTaxCnt;
    this.infant = farebox.infant;
    this.fn = farebox.fn;
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
  }

  public BookFN(FareBox farebox)
  {
    this.f = farebox.f;
    this.r = farebox.r;
    this.e = farebox.e;
    this.x = farebox.x;
    this.t = farebox.t;
    this.a = farebox.a;
    this.o = farebox.o;
    this.s = farebox.s;

    this.c = farebox.c;
    this.taxCnt = farebox.taxCnt;
    this.oTaxCnt = farebox.oTaxCnt;
    this.infant = farebox.infant;
    this.fn = farebox.fn;
  }

  public void addPsgrname(String newPsgrname)
  {
    if ((newPsgrname != null) && (newPsgrname.length() > 0))
      this.psgrname.addElement(newPsgrname);
  }

  public String getFn()
  {
    return this.fn;
  }

  public Vector getPsgrname()
  {
    return this.psgrname;
  }

  public void setFn(String newFn)
  {
    String newFn1 = newFn.trim().toUpperCase();
    if ((newFn1.startsWith("FN/")) || (newFn1.startsWith("FN:")) || (newFn1.startsWith("FN ")))
      newFn1 = newFn1.substring(3);
    this.fn = newFn1;
  }

  public BookFN(String fn, String name)
  {
    setFn(fn);
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
  }

  public BookFN(String fn, String name, boolean inf)
  {
    setFn(fn);
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
    this.infant = inf;
  }

  public boolean isInfant()
  {
    return this.infant;
  }

  public BookFN(String fn)
  {
    setFn(fn);
  }

  public void setInfant(boolean newInfant)
  {
    this.infant = newInfant;
  }
}
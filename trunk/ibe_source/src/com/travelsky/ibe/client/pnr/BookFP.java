package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import java.util.Vector;

public class BookFP extends IBEResult
{
  private static final long serialVersionUID = 424425144690121895L;
  String fp = null;
  Vector psgrname = new Vector();
  boolean infant = false;

  public BookFP()
  {
  }

  public BookFP(String fp, String name)
  {
    setFp(fp);
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
  }

  public BookFP(String fp, String name, boolean inf)
  {
    setFp(fp);
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
    this.infant = inf;
  }

  public void addPsgrname(String newPsgrname)
  {
    this.psgrname.addElement(newPsgrname);
  }

  public String getFp()
  {
    return this.fp;
  }

  public Vector getPsgrname()
  {
    return this.psgrname;
  }

  public boolean isInfant()
  {
    return this.infant;
  }

  public void setFp(String newFp)
  {
    String newFp1 = newFp.trim().toUpperCase();
    if ((newFp1.startsWith("FP/")) || (newFp1.startsWith("FP:")) || (newFp1.startsWith("FP ")))
      newFp1 = newFp1.substring(3);
    this.fp = newFp1;
  }

  public void setInfant(boolean newInfant)
  {
    this.infant = newInfant;
  }

  public BookFP(String fp)
  {
    setFp(fp);
  }
}
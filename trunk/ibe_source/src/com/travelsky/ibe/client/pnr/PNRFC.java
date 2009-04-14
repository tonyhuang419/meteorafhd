package com.travelsky.ibe.client.pnr;

public class PNRFC extends FareCalculation
{
  private static final long serialVersionUID = -1435695299723113722L;
  String psgrid = null;
  int index = -1;

  public int getIndex()
  {
    return this.index; }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getPsgrid()
  {
    return this.psgrid; }

  public void setPsgrid(String psgrid) {
    this.psgrid = psgrid;
  }
}
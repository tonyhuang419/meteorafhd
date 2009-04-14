package com.travelsky.ibe.client.pnr;

public class PNRFN extends FareBox
{
  private static final long serialVersionUID = 7790953964625006587L;
  int index;
  String psgrid;

  public String getFn()
  {
    return this.fn; }

  public void setFn(String fn) {
    this.fn = fn; }

  public String getPsgrid() {
    return this.psgrid; }

  public void setPsgrid(String psgrid) {
    this.psgrid = psgrid; }

  public int getIndex() {
    return this.index; }

  public void setIndex(int index) {
    this.index = index;
  }
}
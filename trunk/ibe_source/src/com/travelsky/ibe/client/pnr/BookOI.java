package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import java.util.Date;

public class BookOI extends IBEResult
{
  private static final long serialVersionUID = 6246944102200110370L;
  String coupon = null;
  boolean infant = false;
  String issueCity = null;
  Date issueDate = null;
  String issueOffice = null;
  int oitype = 1;
  String psgrid = null;
  String secondcoupon = null;
  String secondtktno = null;
  String tktno = null;

  public String getCoupon()
  {
    return this.coupon; }

  public String getIssueCity() {
    return this.issueCity; }

  public Date getIssueDate() {
    return this.issueDate; }

  public String getIssueOffice() {
    return this.issueOffice; }

  public int getOitype() {
    return this.oitype; }

  public String getPsgrid() {
    return this.psgrid; }

  public String getSecondcoupon() {
    return this.secondcoupon; }

  public String getSecondtktno() {
    return this.secondtktno; }

  public String getTktno() {
    return this.tktno; }

  public boolean isInfant() {
    return this.infant; }

  public void setCoupon(String coupon) {
    this.coupon = coupon; }

  public void setInfant(boolean infant) {
    this.infant = infant; }

  public void setIssueCity(String issueCity) {
    this.issueCity = issueCity; }

  public void setIssueDate(Date issueDate) {
    this.issueDate = issueDate; }

  public void setIssueOffice(String issueOffice) {
    this.issueOffice = issueOffice; }

  public void setOitype(int oitype) {
    this.oitype = oitype; }

  public void setPsgrid(String psgrid) {
    this.psgrid = psgrid; }

  public void setSecondcoupon(String secondcoupon) {
    this.secondcoupon = secondcoupon; }

  public void setSecondtktno(String secondtktno) {
    this.secondtktno = secondtktno; }

  public void setTktno(String tktno) {
    this.tktno = tktno;
  }
}
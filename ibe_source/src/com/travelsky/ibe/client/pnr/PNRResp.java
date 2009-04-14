package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRResp extends IBEResult
{
  private static final long serialVersionUID = -37473442678060611L;
  private String crssign;
  private String pnrno;
  private String officecode;
  int lineIndex;
  private String remark;

  public PNRResp()
    throws Exception
  {
  }

  public PNRResp(String crssign, String pnrno, String officecode)
    throws Exception
  {
    this.crssign = crssign;
    this.pnrno = pnrno;
    this.officecode = officecode;
  }

  void setCrssign(String crssign)
  {
    this.crssign = crssign;
  }

  public String getCrssign()
  {
    return this.crssign;
  }

  void setPnrno(String pnrno) {
    this.pnrno = pnrno;
  }

  public String getPnrno()
  {
    return this.pnrno;
  }

  void setOfficecode(String officecode) {
    this.officecode = officecode;
  }

  public String getOfficecode()
  {
    return this.officecode;
  }

  public PNRResp(String crssign, String pnrno, String officecode, String remark)
  {
    this.crssign = crssign;
    this.pnrno = pnrno;
    this.officecode = officecode;
    this.remark = remark;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setRemark(String newRemark)
  {
    this.remark = newRemark;
  }
}
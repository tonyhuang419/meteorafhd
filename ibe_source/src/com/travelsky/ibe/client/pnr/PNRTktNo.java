package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;

public class PNRTktNo extends IBEResult
{
  private static final long serialVersionUID = -1484320242444723991L;
  private String psgrID;
  private String remark;
  private String tktNo;
  int lineIndex;

  public PNRTktNo()
    throws Exception
  {
  }

  public PNRTktNo(String psgrID, String remark, String tktNo)
    throws Exception
  {
    this.psgrID = psgrID;
    this.remark = remark;
    this.tktNo = tktNo;
  }

  void setPsgrID(String psgrID)
  {
    this.psgrID = psgrID;
  }

  public String getPsgrID()
  {
    return this.psgrID;
  }

  void setRemark(String remark) {
    this.remark = remark;
  }

  public String getRemark()
  {
    return this.remark;
  }

  void setTktNo(String tktNo) {
    this.tktNo = tktNo;
  }

  public String getTktNo()
  {
    return this.tktNo;
  }

  public int getIndex()
  {
    return this.lineIndex;
  }

  public int compareTo(Object o)
    throws ClassCastException
  {
    if (!(o.getClass().equals(getClass())))
      throw new ClassCastException();

    return getTktNo().compareTo(((PNRTktNo)o).getTktNo());
  }
}
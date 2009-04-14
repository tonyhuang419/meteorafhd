package com.travelsky.ibe.client;

import com.travelsky.ibe.client.pnr.RTResult;

public class QueueItem extends IBEResult
{
  private static final long serialVersionUID = 4677762089358649017L;
  String officeCode = null;
  String comment = null;
  int unhandledQueue = 0;
  String message = null;
  boolean deletedPNR = false;
  String PNRNo = null;
  RTResult PNRResult = null;

  public String getComment()
  {
    return this.comment;
  }

  public String getMessage()
  {
    return this.message;
  }

  public String getOfficeCode()
  {
    return this.officeCode;
  }

  public int getUnhandledQueue()
  {
    return this.unhandledQueue;
  }

  public String toString()
  {
    StringBuffer output;
    try
    {
      output = new StringBuffer();

      output.append(this.officeCode + "\t" + this.comment + "\t(" + String.valueOf(this.unhandledQueue) + ")\r\n");

      output.append(this.message + "\r\n");

      output.append("PNR Code: " + this.PNRNo + "\r\n");

      output.append("Deleted Flag: " + this.deletedPNR + "\r\n");

      output.append("Decode Result: \r\n");

      output.append((this.PNRResult == null) ? "No Decode" : this.PNRResult.toString());
      return output.toString(); } catch (Exception e) {
    }
    return null;
  }

  public String getPNRNo()
  {
    return this.PNRNo;
  }

  public RTResult getPNRResult()
  {
    return this.PNRResult;
  }

  public boolean isDeletedPNR()
  {
    return this.deletedPNR;
  }

  public void setPNRResult(RTResult newPNRResult)
  {
    this.PNRResult = newPNRResult;
  }
}
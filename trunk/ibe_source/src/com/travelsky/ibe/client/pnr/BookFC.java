package com.travelsky.ibe.client.pnr;

import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.text.DecimalFormat;
import java.util.Vector;

public class BookFC extends FareCalculation
{
  private static final long serialVersionUID = -1120562473802969277L;
  Vector psgrname = new Vector();

  public BookFC()
  {
  }

  public BookFC(String fc)
  {
    setFc(fc);
  }

  public BookFC(String fc, String name)
  {
    setFc(fc);
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
  }

  public BookFC(String fc, String name, boolean inf)
  {
    setFc(fc);
    if ((name != null) && (name.length() > 0))
      this.psgrname.addElement(name);
    this.infant = inf;
  }

  public void addPsgrname(String newPsgrname)
  {
    if ((newPsgrname != null) && (newPsgrname.length() > 0))
      this.psgrname.addElement(newPsgrname);
  }

  public String getFc()
  {
    return this.fc;
  }

  public Vector getPsgrname()
  {
    return this.psgrname;
  }

  public boolean isInfant()
  {
    return this.infant;
  }

  public void setFc(String newFc)
  {
    String newFc1 = newFc.trim().toUpperCase();
    if ((newFc1.startsWith("FC/")) || (newFc1.startsWith("FC:")) || 
      (newFc1.startsWith("FC ")))
      newFc1 = newFc1.substring(3);
    this.fc = newFc1;
  }

  public void setInfant(boolean newInfant)
  {
    this.infant = newInfant; }

  static String encodeFC(BookFC fc) {
    String[] fcStr;
    try { fcStr = new String[20];
      fcStr[0] = fc.getFc();
      if ((fcStr[0] != null) && (fcStr[0].length() > 0))
        return CommandParser.encode(fcStr);

      if (fc.getFCDate() != null)
        fcStr[1] = QDateTime.dateToString(fc.getFCDate(), "ddmmmyyy");
      fcStr[2] = fc.getPseg();
      fcStr[3] = FareCalculation.format.format(fc.getPfee());
      fcStr[4] = fc.getDseg();
      fcStr[5] = FareCalculation.format.format(fc.getDfee());
      fcStr[6] = fc.getHseg();
      fcStr[7] = FareCalculation.format.format(fc.getHfee());
      fcStr[8] = new DecimalFormat("0.000000").format(fc.getROE());
      fcStr[9] = fc.getExtraInfo();
      fcStr[10] = fc.getCurrency();
      fcStr[11] = String.valueOf(fc.isInfant());
      String[] segments = new String[fc.getSegmentCnt()];
      String[] xts = new String[fc.getExtraTaxCnt()];
      String[] rxts = new String[fc.getRefundExtraTaxCnt()];
      String[] oxts = new String[fc.getOriginalExtraTaxCnt()];
      String[] names = new String[fc.getPsgrname().size()];
      for (int i = 0; i < names.length; ++i)
        names[i] = ((String)fc.getPsgrname().get(i));
      fcStr[16] = CommandParser.encode(names);
      fcStr[17] = FareCalculation.format.format(fc.getTotal());
      fcStr[19] = fc.getFreeText();
      fcStr[18] = fc.getExtraInfo();
      for (i = 0; i < segments.length; ++i) {
        String[] segment = new String[17];
        segment[0] = fc.getCarrier(i);
        segment[1] = fc.getDst(i);
        segment[2] = fc.getFareBasis(i);
        segment[3] = fc.getInvalidateAfter(i);
        segment[4] = fc.getInvalidateBefore(i);
        segment[5] = fc.getMileSurchargeSeg(i);
        segment[6] = String.valueOf(fc.getMileSurcharge(i));
        segment[7] = fc.getOrg(i);
        segment[8] = fc.getPackage(i);
        segment[9] = fc.getQSeg(i);
        segment[10] = fc.getTravelDirection(i);
        segment[11] = String.valueOf(fc.getBranchEnd(i));
        segment[12] = String.valueOf(fc.getBranchStart(i));
        segment[13] = String.valueOf(fc.getE(i));
        segment[14] = String.valueOf(fc.getPrice(i));
        segment[15] = String.valueOf(fc.getQ(i));
        segment[16] = String.valueOf(fc.getStopOver(i));
        segments[i] = CommandParser.encode(segment);
      }
      for (i = 0; i < xts.length; ++i) {
        String[] xt = new String[3];
        xt[0] = fc.getExtraTaxCode(i);
        xt[1] = fc.getExtraTaxCurrency(i);
        xt[2] = FareCalculation.format.format(fc.getExtraTaxAmount(i));
        xts[i] = CommandParser.encode(xt);
      }
      for (i = 0; i < rxts.length; ++i) {
        String[] rxt = new String[3];
        rxt[0] = fc.getRefundExtraTaxCode(i);
        rxt[1] = fc.getRefundExtraTaxCurrency(i);
        rxt[2] = FareCalculation.format.format(fc.getRefundExtraTaxAmount(i));
        rxts[i] = CommandParser.encode(rxt);
      }
      for (i = 0; i < oxts.length; ++i) {
        String[] oxt = new String[3];
        oxt[0] = fc.getOriginalExtraTaxCode(i);
        oxt[1] = fc.getOriginalExtraTaxCurrency(i);
        oxt[2] = FareCalculation.format.format(fc.getOriginalExtraTaxAmount(i));
        oxts[i] = CommandParser.encode(oxt);
      }
      fcStr[12] = CommandParser.encode(segments);
      fcStr[13] = CommandParser.encode(xts);
      fcStr[14] = CommandParser.encode(rxts);
      fcStr[15] = CommandParser.encode(oxts);
      return CommandParser.encode(fcStr);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
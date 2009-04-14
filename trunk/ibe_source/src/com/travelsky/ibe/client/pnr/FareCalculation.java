package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.QDateTime;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class FareCalculation extends IBEResult
{
  static final DecimalFormat format = new DecimalFormat(
    "0.00");
  double dfee = -1.0D;
  String dseg = null;
  String extraInfo = null;
  String fc;
  Date fcDate = null;
  double hfee = -1.0D;
  String hseg = null;
  boolean infant;
  String moneytype = "CNY";
  List otax = new ArrayList();
  double pfee = -1.0D;
  String pseg = null;
  double ROE = -10.0D;
  List rtax = new ArrayList();
  ArrayList segments = new ArrayList();
  List tax = new ArrayList();
  double total = -1.0D;
  String freeText = null;

  public void addBranchFC(FareCalculation bookFC)
  {
    int j = bookFC.getSegmentCnt();

    for (int i = 0; i < j; ++i) {
      fcitem item = bookFC.getSegment(i);
      if (i == 0)
        item.branchstart = true;
      if (i == j - 1)
        item.branchend = true;
      this.segments.add(item);
    }
  }

  public void addFC(FareCalculation bookFC)
  {
    int j = bookFC.getSegmentCnt();

    for (int i = 0; i < j; ++i) {
      fcitem item = bookFC.getSegment(i);
      if ((i != 0) || 
        (i == j - 1));
      this.segments.add(item);
    }
  }

  public void addFC(String org, String dst, String airline, String farebasis, double price)
  {
    fcitem fc = new fcitem(this);
    fc.org = org;
    fc.dst = dst;
    fc.amount = price;
    fc.aircorp = airline;
    fc.fltclass = farebasis;

    this.segments.add(fc);
  }

  public void addFC(String org, String dst, String airline, String farebasis, double price, int pcs, int pweight, Calendar invalidBefore, Calendar invalidAfter)
  {
    fcitem fc = new fcitem(this);
    fc.org = org;
    fc.dst = dst;
    fc.amount = price;
    fc.aircorp = airline;
    fc.fltclass = farebasis;

    fc.invalidBefore = invalidBefore;
    fc.invalidAfter = invalidAfter;

    fc.freePackageCount = pcs;
    fc.freePackageWeight = pweight;
    this.segments.add(fc);
  }

  public void addFC(String org, String dst, String airline, String farebasis, double price, int pcs, int pweight, Calendar invalidBefore, Calendar invalidAfter, boolean E, boolean stopOver, String traveldirect, int milesurcharge, String msseg, double q, String qseg)
  {
    fcitem fc = new fcitem(this);
    fc.org = org;
    fc.dst = dst;
    fc.amount = price;
    fc.aircorp = airline;
    fc.fltclass = farebasis;

    fc.invalidBefore = invalidBefore;
    fc.invalidAfter = invalidAfter;

    fc.freePackageCount = pcs;
    fc.freePackageWeight = pweight;

    fc.traveldirection = traveldirect;
    fc.milesurcharge = milesurcharge;
    fc.msseg = msseg;
    fc.stopover = stopOver;
    fc.e = E;
    fc.q = q;
    fc.qseg = qseg;
    this.segments.add(fc);
  }

  public void addFC(String org, String dst, String airline, String farebasis, double price, String f, String NVB, String NVA, boolean E, boolean stopOver, String traveldirect, int milesurcharge, String msseg, double q, String qseg, boolean branchStart, boolean branchEnd)
  {
    fcitem fc = new fcitem(this);
    fc.org = org;
    fc.dst = dst;
    fc.amount = price;
    fc.aircorp = airline;
    fc.fltclass = farebasis;

    Calendar invalidBefore = null;
    try {
      if (NVB.length() == 0) {
        invalidBefore = null; break label104: }
      if (NVB.equals("B-0")) {
        invalidBefore = new GregorianCalendar(1990, 1, 1); break label104:
      }
      invalidBefore = QDateTime.stringToCalendar(NVB.substring(2), "ddmmmyy"); } catch (Exception localException1) {
    }
    label104: fc.invalidBefore = invalidBefore;
    Object invalidAfter = null;
    try {
      if (NVA.length() == 0) {
        invalidAfter = null; break label173: }
      if (NVA.equals("A-0")) {
        invalidAfter = new GregorianCalendar(1990, 1, 1); break label173:
      }
      invalidAfter = QDateTime.stringToCalendar(NVA.substring(2), "ddmmmyy"); } catch (Exception localException2) {
    }
    label173: fc.invalidAfter = ((Calendar)invalidAfter);

    if (f.length() == 0) {
      fc.freePackageCount = -1;
      fc.freePackageWeight = -1;
    } else if (f.equals("F-0")) {
      fc.freePackageCount = -2;
      fc.freePackageWeight = -1; } else {
      int t;
      if (f.endsWith("PC")) {
        t = Integer.parseInt(f.substring(2, f.length() - 2));
        fc.freePackageCount = t;
        fc.freePackageWeight = -1;
      }
      else if (f.endsWith("KG")) {
        t = Integer.parseInt(f.substring(2, f.length() - 2));
        fc.freePackageCount = -1;
        fc.freePackageWeight = t;
      }

    }

    fc.traveldirection = traveldirect;
    fc.milesurcharge = milesurcharge;
    fc.msseg = msseg;
    fc.stopover = stopOver;
    fc.e = E;
    fc.q = q;
    fc.qseg = qseg;
    this.segments.add(fc);
  }

  public void addFCARNK()
  {
    fcitem fc = new fcitem(this);
    fc.org = "";
    fc.dst = "";
    fc.amount = 0.0D;
    fc.aircorp = "//";
    fc.fltclass = "";

    this.segments.add(fc);
  }

  public void addFCARNKwithGroundTx()
  {
    fcitem fc = new fcitem(this);
    fc.org = "";
    fc.dst = "";
    fc.amount = 0.0D;
    fc.aircorp = "//";
    fc.fltclass = "";
    this.segments.add(fc);
  }

  public void addFCARNKwithoutGroundTx()
  {
    fcitem fc = new fcitem(this);
    fc.org = "";
    fc.dst = "";
    fc.amount = 0.0D;
    fc.aircorp = "/-";
    fc.fltclass = "";

    this.segments.add(fc);
  }

  public String getAircorp(int i)
    throws Exception
  {
    return getSegment(i).aircorp;
  }

  public boolean getBranchEnd(int i)
  {
    return getSegment(i).branchend;
  }

  public boolean getBranchStart(int i)
  {
    return getSegment(i).branchstart;
  }

  public String getCarrier(int i)
    throws Exception
  {
    return getSegment(i).aircorp;
  }

  public String getCurrency()
  {
    return this.moneytype;
  }

  public double getDfee()
  {
    return this.dfee;
  }

  public String getDseg()
  {
    return this.dseg;
  }

  public String getDst(int i)
    throws Exception
  {
    return getSegment(i).dst;
  }

  public boolean getE(int i)
  {
    return getSegment(i).e;
  }

  public double getExtraTaxAmount(int i)
  {
    return ((fctax)this.tax.get(i)).value;
  }

  public int getExtraTaxCnt()
  {
    return this.tax.size();
  }

  public String getExtraTaxCode(int i)
  {
    return ((fctax)this.tax.get(i)).code;
  }

  public String getExtraTaxCurrency(int i)
  {
    return ((fctax)this.tax.get(i)).currency;
  }

  public String getFareBasis(int i)
    throws Exception
  {
    return getSegment(i).fltclass;
  }

  public String getFc()
  {
    return this.fc;
  }

  public Date getFCDate()
  {
    return this.fcDate;
  }

  public double getHfee()
  {
    return this.hfee;
  }

  public String getHseg()
  {
    return this.hseg;
  }

  public String getInvalidateAfter(int i)
    throws Exception
  {
    fcitem item = (fcitem)this.segments.get(i);
    if (item.invalidAfter == null)
      return "";
    if (item.invalidAfter.get(1) < 2000)
      return "A-0";

    return "A-" + 
      QDateTime.calendarToString(item.invalidAfter, "DDMMMYY");
  }

  public String getInvalidateBefore(int i)
    throws Exception
  {
    fcitem item = (fcitem)this.segments.get(i);
    if (item.invalidBefore == null)
      return "";
    if (item.invalidBefore.get(1) < 2000)
      return "B-0";

    return "B-" + 
      QDateTime.calendarToString(item.invalidBefore, "DDMMMYY");
  }

  public int getMileSurcharge(int i)
  {
    return getSegment(i).milesurcharge;
  }

  public String getMileSurchargeSeg(int i)
  {
    return getSegment(i).msseg;
  }

  public String getOrg(int i)
    throws Exception
  {
    return getSegment(i).org;
  }

  public double getOriginalExtraTaxAmount(int i)
  {
    return ((fctax)this.otax.get(i)).value;
  }

  public int getOriginalExtraTaxCnt()
  {
    return this.otax.size();
  }

  public String getOriginalExtraTaxCode(int i)
  {
    return ((fctax)this.otax.get(i)).code;
  }

  public String getOriginalExtraTaxCurrency(int i)
  {
    return ((fctax)this.otax.get(i)).currency;
  }

  public String getPackage(int i)
    throws Exception
  {
    fcitem item = (fcitem)this.segments.get(i);
    if (item.freePackageCount == -2)
      return "F-0";
    if (item.freePackageCount > 0)
      return "F-" + item.freePackageCount + "PC";
    if (item.freePackageWeight > 0)
      return "F-" + item.freePackageWeight + "KG";
    return "";
  }

  public double getPfee()
  {
    return this.pfee;
  }

  public double getPrice(int i)
    throws Exception
  {
    return getSegment(i).amount;
  }

  public String getPseg()
  {
    return this.pseg;
  }

  public double getQ(int i)
    throws Exception
  {
    return getSegment(i).q;
  }

  public String getQSeg(int i)
    throws Exception
  {
    return getSegment(i).qseg;
  }

  public double getRefundExtraTaxAmount(int i)
  {
    return ((fctax)this.rtax.get(i)).value;
  }

  public int getRefundExtraTaxCnt()
  {
    return this.rtax.size();
  }

  public String getRefundExtraTaxCode(int i)
  {
    return ((fctax)this.rtax.get(i)).code;
  }

  public String getRefundExtraTaxCurrency(int i)
  {
    return ((fctax)this.rtax.get(i)).currency;
  }

  public double getROE()
  {
    return this.ROE;
  }

  public fcitem getSegment(int index)
  {
    return ((fcitem)this.segments.get(index));
  }

  public int getSegmentCnt()
  {
    return this.segments.size();
  }

  public boolean getStopOver(int i)
  {
    return getSegment(i).stopover;
  }

  public String getTravelDirection(int i)
  {
    return getSegment(i).traveldirection;
  }

  public void insertTax(String currency, double value, String code) {
    if ((code == null) || (code.length() == 0))
      return;
    if ((currency == null) || (currency.length() == 0))
      return;
    if (value <= -0.0001D)
      return;
    this.tax.add(new fctax(this, currency, value, code));
  }

  public void insertOTax(String currency, double value, String code) {
    if ((code == null) || (code.length() == 0))
      return;
    if ((currency == null) || (currency.length() == 0))
      return;
    if (value <= -0.0001D)
      return;
    this.otax.add(new fctax(this, currency, value, code)); }

  public void insertRTax(String currency, double value, String code) {
    if ((code == null) || (code.length() == 0))
      return;
    if ((currency == null) || (currency.length() == 0))
      return;
    if (value <= -0.0001D)
      return;
    this.rtax.add(new fctax(this, currency, value, code));
  }

  public void insertTaxes(String currecny, Map map) {
    if (map == null)
      return;
    if ((currecny == null) || (currecny.length() == 0))
      return;
    Iterator iter = map.keySet().iterator();
    while (iter.hasNext()) {
      String key = (String)iter.next();
      this.tax.add(new fctax(this, currecny, ((Double)map.get(key)).doubleValue(), key));
    }
  }

  public boolean isInfant()
  {
    return this.infant;
  }

  public void setFc(String newFc)
  {
    this.fc = newFc;
  }

  public void setInfant(boolean newInfant)
  {
    this.infant = newInfant; }

  public String makeString() {
    DecimalFormat format = new DecimalFormat("0.00");
    try {
      StringBuffer sb = new StringBuffer("原文：" + getFc());
      if (getFCDate() != null) {
        sb.append("\r\nFC日期：");
        sb.append(QDateTime.dateToString(getFCDate(), "ddmmmyy"));
      }

      int sc = 0;
      for (int i = 0; i < getSegmentCnt(); ++i) {
        sb.append("\r\nSegment: ");
        sb.append((getBranchEnd(i)) ? "到此航段结束为旁岔程终止。\r\n" : (getBranchStart(i)) ? "自此航段始为旁岔程。\r\n" : "\r\n");
        sb.append("       起始:" + getOrg(i));
        if (getPackage(i).length() > 0)
          sb.append("   行李限制：" + getPackage(i));
        if (getInvalidateAfter(i).length() > 0)
          sb.append("   NVA：" + getInvalidateAfter(i));
        if (getInvalidateBefore(i).length() > 0)
          sb.append("   NVB：" + getInvalidateBefore(i));
        sb.append("  承运人:" + getAircorp(i));

        if ((getTravelDirection(i) != null) && (getTravelDirection(i).length() > 0)) {
          sb.append("  适用" + getTravelDirection(i) + "方向运价");
        }

        if (getE(i))
          sb.append(" 经由此终到点有忧惠");
        if (!(getStopOver(i)))
          sb.append(" 此终到点为非经停点");

        sb.append("  终到:" + getDst(i));

        if (getQ(i) > 0.0D) {
          if ((getQSeg(i) != null) && (getQSeg(i).length() > 0))
            sb.append("  航段:" + getQSeg(i));
          else
            sb.append("   ");
          sb.append("Q附加:" + format.format(getQ(i)));
        }
        if (getMileSurcharge(i) >= 0)
          sb.append(" 至此航段使用里程制运价");
        if ((getMileSurchargeSeg(i) != null) && (getMileSurchargeSeg(i).length() > 0)) {
          if (!(getE(i)))
            sb.append(" 票价提高至此段（" + getMileSurchargeSeg(i) + "）价格");
          else
            sb.append(" 此段（" + getMileSurchargeSeg(i) + "）价格有优惠");
        }
        else if ((((getMileSurchargeSeg(i) == null) || (getMileSurchargeSeg(i).length() == 0))) && (getMileSurcharge(i) > 0))
        {
          boolean E = false;
          for (int j = i; j > 0; --j) {
            if ((j < i) && (getPrice(j) > -0.0001D))
              break;
            if (getE(j)) {
              E = true;
              break;
            }
          }
          if ((!(getE(i))) && (!(E)))
            sb.append(" 票价提高");
          else
            sb.append(" 价格优惠");
        }

        if (getMileSurcharge(i) > 0)
          sb.append(" 比例" + getMileSurcharge(i) + "%");
        if (getPrice(i) > -0.001D) {
          if ((sc > 0) && (getMileSurcharge(i) < 0))
            sb.append(" 至此航段为联程运价");
          sb.append("  运价：" + format.format(getPrice(i)));
          sc = 0;
        } else {
          ++sc;
        }
        if ((getFareBasis(i) != null) && (getFareBasis(i).length() > 0))
          sb.append("  运价基础：" + getFareBasis(i));
      }

      sb.append("\r\n");
      if (getDseg() != null)
        sb.append("  航段：" + getDseg() + "需补齐等级差价" + format.format(getDfee()) + "\r\n");
      if (getPseg() != null) {
        if (this.pseg.length() == 6)
          sb.append("  票价提高至" + getPseg() + "的最低限额,补" + format.format(getPfee()) + "\r\n");
        if (this.pseg.length() == 13)
          sb.append("  " + getPseg().substring(7, 13) + "票价提高至" + getPseg().substring(0, 6) + "的运价,补" + format.format(getPfee()) + "\r\n");
      }
      if ((getHseg() != null) && 
        (this.hseg.length() == 6))
        sb.append("  票价提高至" + getHseg() + "的限额,补" + format.format(getHfee()) + "\r\n");

      sb.append("总运价:" + getCurrency() + "   " + format.format(getTotal()));
      if (getROE() > 0.0D)
        sb.append("  ROE:" + new DecimalFormat("0.000000").format(getROE()));
      if (getTax().size() > 0) {
        sb.append("\r\nEXTRA Tax：");
        for (i = 0; i < this.tax.size(); ++i)
          sb.append("  " + this.tax.get(i));
      }

      if (getRtax().size() > 0) {
        sb.append("\r\nEXTRA Refund Tax：");
        for (i = 0; i < this.rtax.size(); ++i)
          sb.append("  " + this.rtax.get(i));
      }

      if (getOtax().size() > 0) {
        sb.append("\r\nEXTRA Orignal Tax：");
        for (i = 0; i < this.otax.size(); ++i)
          sb.append("  " + this.otax.get(i));
      }

      if ((getExtraInfo() != null) && (getExtraInfo().length() > 0)) {
        sb.append("\r\n附加信息：");
        sb.append(getExtraInfo());
      }
      if ((getFreeText() != null) && (getFreeText().length() > 0)) {
        sb.append("\r\n自由附加文本：");
        sb.append(getFreeText());
      }
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public double getTotal()
  {
    return this.total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public String getExtraInfo()
  {
    return this.extraInfo;
  }

  List getOtax() {
    return this.otax;
  }

  void setOtax(List otax) {
    this.otax = otax;
  }

  List getRtax() {
    return this.rtax;
  }

  void setRtax(List rtax) {
    this.rtax = rtax;
  }

  List getTax() {
    return this.tax;
  }

  void setTax(List tax) {
    this.tax = tax;
  }

  public Date getFcDate() {
    return this.fcDate;
  }

  public void setFcDate(Date fcDate) {
    this.fcDate = fcDate;
  }

  public String getMoneytype() {
    return this.moneytype;
  }

  public void setMoneytype(String moneytype) {
    this.moneytype = moneytype;
  }

  public void setDfee(double dfee) {
    this.dfee = dfee;
  }

  public void setDseg(String dseg) {
    this.dseg = dseg;
  }

  public void setPfee(double pfee) {
    this.pfee = pfee;
  }

  public void setPseg(String pseg) {
    this.pseg = pseg;
  }

  public void setROE(double roe) {
    this.ROE = roe;
  }

  public String getFreeText()
  {
    return this.freeText;
  }

  public void setFreeText(String freeText)
  {
    this.freeText = freeText;
  }

  public void setExtraInfo(String extraInfo)
  {
    this.extraInfo = extraInfo;
  }

  class fcitem
    implements Serializable
  {
    private static final long serialVersionUID = 7348683484826845722L;
    String aircorp;
    double amount;
    boolean branchend;
    boolean branchstart;
    boolean cancelA;
    boolean cancelB;
    boolean cancelF;
    String dst;
    boolean e;
    String fltclass;
    int freePackageCount;
    int freePackageWeight;
    Calendar invalidAfter;
    Calendar invalidBefore;
    int milesurcharge;
    String msseg;
    String org;
    double q;
    String qseg;
    boolean stopover;
    String traveldirection;

    fcitem()
    {
      this.this$0 = paramFareCalculation;

      this.amount = -1.0D;

      this.branchend = false;

      this.branchstart = false;

      this.cancelA = false; this.cancelB = false; this.cancelF = false;

      this.e = false;

      this.fltclass = "";

      this.freePackageCount = -1; this.freePackageWeight = -1;

      this.invalidAfter = null; this.invalidBefore = null;

      this.milesurcharge = -1;

      this.msseg = null;

      this.q = -1.0D;

      this.qseg = null;

      this.stopover = true;

      this.traveldirection = null;
    }

    public String toString()
    {
      return this.org + ' ' + this.aircorp + ' ' + this.dst + ' ' + 
        FareCalculation.format.format(this.amount) + this.fltclass + ' ';
    }
  }

  class fctax
    implements Serializable
  {
    private static final long serialVersionUID = 2990242460423314161L;
    String code;
    String currency;
    double value;

    fctax(, String paramString1, double paramDouble, String paramString2)
    {
      this.this$0 = paramFareCalculation;

      this.code = "";

      this.currency = "CNY";

      this.value = -1.0D;

      this.currency = paramString1;
      this.value = value;
      this.code = paramString2;
    }

    public String toString()
    {
      return this.currency + FareCalculation.format.format(this.value) + this.code;
    }
  }
}
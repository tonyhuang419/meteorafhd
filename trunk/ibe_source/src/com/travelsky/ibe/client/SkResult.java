package com.travelsky.ibe.client;

import com.travelsky.util.QDateTime;
import java.util.Date;
import java.util.Vector;

public class SkResult extends IBEResult
{
  private static final long serialVersionUID = -6148384492540812300L;
  String day1 = null;
  String dt1 = null;
  String month1 = null;
  String year1 = null;
  Date date1 = null;
  String day2 = null;
  String dt2 = null;
  String month2 = null;
  String year2 = null;
  Date date2 = null;
  String org = null;
  String dst = null;
  String sysmsg = null;
  private int items = 0;
  private Vector result = new Vector();

  public Date getDatef()
  {
    return this.date1;
  }

  public Date getDatet()
  {
    return this.date2;
  }

  public String getDayf()
  {
    return this.day1;
  }

  public String getDayt()
  {
    return this.day2;
  }

  public String getDst()
  {
    return this.dst;
  }

  public String getDtf()
  {
    return this.dt1;
  }

  public String getDtt()
  {
    return this.dt2;
  }

  public SkItem getItem(int index)
  {
    return ((SkItem)this.result.get(index));
  }

  public int getItemamount()
  {
    return this.items;
  }

  public String getMonthf()
  {
    return this.month1;
  }

  public String getMontht()
  {
    return this.month2;
  }

  public String getOrg()
  {
    return this.org;
  }

  public String getSysmsg()
  {
    return this.sysmsg;
  }

  public String getYearf()
  {
    return this.year1;
  }

  public String getYeart()
  {
    return this.year2;
  }

  void putItem(SkItem si)
  {
    this.result.addElement(si);
    this.items += 1;
  }

  public void removeItemAt(int index)
  {
    if ((index < 0) || (index > this.items))
      return;

    this.result.removeElementAt(index);
    this.items -= 1;
  }

  public String toString()
  {
    StringBuffer str;
    try
    {
      str = new StringBuffer();
      str.append("查询周起始日  :" + getDatef() + "\n");
      str.append("查询周结束日  :" + getDatet() + "\n");
      str.append("系统讯息：  " + this.sysmsg + "\n");
      str.append("起飞地:" + getOrg() + "   目的地" + getDst() + "\n\n");
      for (int i = 0; i < getItemamount(); ++i) {
        str.append("  线路" + String.valueOf(i + 1) + ":\n");
        SkItem si = getItem(i);
        for (int j = 0; j < si.getSegmentnumber(); ++j) {
          SkSegment ss = si.getSegment(j);
          str.append("      航段" + String.valueOf(j + 1) + "：\n");
          str.append("        航班号    起飞到达    起飞时间   到达时间    机型 经停次数 OPENTO 连接协议 ASR  餐食  电子票 代码共享  运行周期    生效日                        过期日                       舱位列表\n");
          str.append(
            "       " + 
            ss.getAirline() + 
            "   " + 
            ss.getOrgcity() + 
            ss.getDstcity() + 
            "     " + 
            ss.getDeptime() + 
            ss.getDeptimemodify() + 
            "     " + 
            ss.getArritime() + 
            ss.getArritimemodify() + 
            "      " + 
            ss.getPlanestyle() + 
            "    " + 
            ss.getStopnumber() + 
            "      " + 
            ((ss.getOpento() == null) ? "       " : ss.getOpento()) + 
            " " + 
            ((ss.getLink().length() > 0) ? ss.getLink() : "   ") + 
            "    " + 
            ss.getAsr() + 
            " " + 
            ss.getMeal() + 
            " " + 
            ss.isEtkt() + 
            " " + 
            ss.isCodeShare() + 
            "   ");
          for (int k = 0; k < 7; ++k)
            if (ss.getAvailabilityof(k))
              str.append(String.valueOf(k + 1));
            else
              str.append(' ');

          str.append(
            "     " + 
            ((ss.getEffectivedate() != null) ? QDateTime.dateToString(ss.getEffectivedate(), "YYYY MM DD hh:mi:ss  www     ") : "                            ") + 
            " " + 
            ((ss.getExpirationdate() != null) ? QDateTime.dateToString(ss.getExpirationdate(), "YYYY MM DD hh:mi:ss  www     ") : "                            ") + 
            "  " + 
            ss.getFlightClasses() + 
            "\n");
        }
      }
      return new String(str); } catch (Exception e) {
    }
    return null;
  }
}
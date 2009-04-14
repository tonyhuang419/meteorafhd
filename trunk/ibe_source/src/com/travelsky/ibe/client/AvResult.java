package com.travelsky.ibe.client;

import com.travelsky.util.QDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public class AvResult extends IBEResult
{
  private static final long serialVersionUID = 1793075374688969257L;
  String day = null;
  String dt = null;
  String month = null;
  String year = null;
  Date date = null;
  String org = null;
  String dst = null;
  String sysmsg = null;
  private int items = 0;
  private Vector result = new Vector();

  private static boolean AvailableCabinInfo(char cangweiinfoOfSort)
  {
    return AvailableCabinInfo(cangweiinfoOfSort, 0);
  }

  private static boolean AvailableCabinInfo(char cangweiinfoOfSort, int minLimit)
  {
    if (minLimit >= 10)
      minLimit = 9;
    if (minLimit <= 0)
      minLimit = 1;
    if (cangweiinfoOfSort == 'A')
      cangweiinfoOfSort = '9';

    return ((cangweiinfoOfSort >= 48 + minLimit) && (cangweiinfoOfSort <= '9'));
  }

  public void ApplyFilter(AVFilter filter)
  {
    Vector v = new Vector();
    for (int i = 0; i < this.result.size(); ++i) {
      AvItem avi = (AvItem)this.result.elementAt(i);
      boolean keepme = filter.test(avi);

      if (keepme)
        v.addElement(avi);
    }
    this.result.clear();
    this.result = null;
    this.result = v;
    this.items = v.size();
  }

  public void clearItems()
  {
    this.items = 0;
    this.result.clear();
  }

  public Date getDate()
  {
    return this.date;
  }

  public String getDay()
  {
    return this.day;
  }

  public String getDst()
  {
    return this.dst;
  }

  public String getDt()
  {
    return this.dt;
  }

  public AvItem getItem(int index)
  {
    return ((AvItem)this.result.get(index));
  }

  public int getItemamount()
  {
    return this.items;
  }

  public String getMonth()
  {
    return this.month;
  }

  public String getOrg()
  {
    return this.org;
  }

  public String getSysmsg()
  {
    return this.sysmsg;
  }

  public String getYear()
  {
    return this.year;
  }

  void putItem(AvItem ai)
  {
    this.result.addElement(ai);
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
    StringBuffer strtmp = new StringBuffer();
    try
    {
      if (this == null) {
        strtmp.append("AV IS NULL\n");
        return strtmp.toString();
      }
      strtmp.append("主信息：\n");
      strtmp.append("查询日期            始发地        终到地\n");
      strtmp.append(getDt() + getMonth() + getYear() + "(" + 
        getDay() + ")         " + getOrg() + 
        "           " + getDst() + '\n');
      strtmp.append("查询日期date:" + getDate() + '\n');
      strtmp.append("系统信息：\n");
      strtmp.append(getSysmsg() + '\n');
      strtmp.append("航段信息:\n");
      for (int i = 0; i < getItemamount(); ++i) {
        AvItem ai = getItem(i);
        strtmp.append("线路" + String.valueOf(i + 1) + ":\n");
        for (int j = 0; j < ai.getSegmentnumber(); ++j) {
          AvSegment as = ai.getSegment(j);
          strtmp.append("\t航段" + String.valueOf(j + 1) + ":\n");
          strtmp
            .append("\t  航班号   起飞地 到达地 起飞时间(DATE                       )     到达时间(DATE                     )  ASR   餐食 电子票 经停 代码共享(实际执行航班) 联结协议 机型  登机TERM  出港TERM \n");
          strtmp.append("\t" + 
            as.getAirline() + 
            "      " + 
            as.getOrgcity() + 
            "    " + 
            as.getDstcity() + 
            "    " + 
            as.getDeptime() + 
            ((as.getDeptimemodify() == null) ? "  " : 
            as.getDeptimemodify()) + 
            "(" + 
            as.getDepdate() + 
            ")" + 
            "  " + 
            as.getArritime() + 
            ((as.getArritimemodify() == null) ? "  " : 
            as.getArritimemodify()) + 
            "(" + 
            as.getArridate() + ")" + "  " + as.getAsr() + 
            "  " + as.getMeal() + " " + as.isETkt() + "   " + 
            as.getStopnumber() + "     " + as.isCodeShare() + 
            "(" + 
            ((as.isCodeShare()) ? as.getCarrier() : "      ") + 
            ")     " + as.getLink() + "      " + 
            as.getPlanestyle() + "     " + as.getDepTerm() + 
            "        " + as.getArriTerm());
          strtmp.append("\n");
          strtmp.append("\t                舱位信息\n");
          strtmp.append("\t");

          int k = 0;
          while (k < 26) {
            char cangwei = as.getCangweiCodeSort(k);
            if (cangwei == '-')
              break;
            strtmp.append(cangwei);
            strtmp.append(as.getCangweiinfoOfSort(cangwei));

            strtmp.append("\n\t");
            ++k;
          }
          strtmp.append('\n');
          strtmp.append("\t                舱位信息2\n");
          strtmp.append("\t                ");
          for (char ch = 'A'; ch <= 'Z'; ch = (char)(ch + '\1')) {
            String cangwei = as.getCangweiinfoOf(ch);
            if (cangwei != null) {
              strtmp.append(ch);
              strtmp.append(cangwei + " ");
            }
          }
          strtmp.append('\n');
        }
      }
      return strtmp.toString(); } catch (Exception e) {
    }
    return toString();
  }

  public void setDate(Date newDate)
  {
    try
    {
      this.date = newDate;

      datestring = QDateTime.dateToString(
        newDate, "YYYYMMMDDWWW");
      this.dt = datestring.substring(7, 9);
      this.year = datestring.substring(0, 4);
      this.month = datestring.substring(4, 7);
      this.day = datestring.substring(9, 11);
    }
    catch (Exception datestring)
    {
    }
  }

  public void setDst(String newDst)
  {
    this.dst = newDst;
  }

  public void setOrg(String newOrg)
  {
    this.org = newOrg;
  }

  public void setSysmsg(String newSysmsg)
  {
    this.sysmsg = newSysmsg;
  }

  public void sortItems(char option)
  {
    InnerComparator comprator = new InnerComparator(this);
    comprator.option = option;
    Collections.sort(this.result, comprator);
  }

  public void sortItems(Comparator c)
  {
    Collections.sort(this.result, c);
  }

  private class InnerComparator
    implements Comparator
  {
    char option;

    InnerComparator()
    {
      this.this$0 = paramAvResult;

      this.option = ' '; }

    public int compare(, Object o2) {
      AvItem a = (AvItem)o1;
      AvItem b = (AvItem)o2;
      switch (this.option)
      {
      case 'A':
      case 'a':
        if (a.getSegmentnumber() != b.getSegmentnumber())
          return (a.getSegmentnumber() - b.getSegmentnumber());

        int j = a.getSegment(a.getSegmentnumber() - 1)
          .getArridate().compareTo(b.getSegment(
          b.getSegmentnumber() - 1).getArridate());
        if (j != 0)
          return j;

        return (-a.getSegment(0).getDepdate().compareTo(
          b.getSegment(0).getDepdate()));
      case 'E':
      case 'e':
        if (a.getSegmentnumber() != b.getSegmentnumber())
          return (a.getSegmentnumber() - b.getSegmentnumber());

        long la = a.getSegment(a.getSegmentnumber() - 1).getArridate()
          .getTime() - 
          a.getSegment(0).getDepdate().getTime();
        long lb = b.getSegment(b.getSegmentnumber() - 1).getArridate()
          .getTime() - 
          b.getSegment(0).getDepdate().getTime();
        if (lb == la)
          return a.getSegment(0).getDepdate().compareTo(
            b.getSegment(0).getDepdate());

        if (lb > la)
          return -1;
        return 1;
      case ' ':
      case 'P':
      case 'p':
      }
      if (a.getSegmentnumber() != b.getSegmentnumber())
        return (a.getSegmentnumber() - b.getSegmentnumber());

      return a.getSegment(0).getDepdate().compareTo(
        b.getSegment(0).getDepdate());
    }
  }
}
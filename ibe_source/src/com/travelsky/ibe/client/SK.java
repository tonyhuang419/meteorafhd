package com.travelsky.ibe.client;

import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Calendar;
import java.util.Date;

public class SK extends IBEClient
{
  public SkResult getSchedule(String org, String dst)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, new Date(), "ALL", false, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, String date)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), "ALL", false, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, String date, String airline)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), airline, false, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, String date, String airline, boolean direct)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), airline, direct, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, Date date)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, date, "ALL", false, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, Date date, String airline)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, date, airline, false, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, Date date, String airline, boolean direct)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, date, airline, direct, false);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, String date, String airline, boolean direct, boolean nostop)
    throws Exception
  {
    try
    {
      return getSchedule(org, dst, QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), airline, direct, nostop);
    } catch (Exception e) {
      throw e;
    }
  }

  public SkResult getSchedule(String org, String dst, Date date, String airline, boolean direct, boolean nostop)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[7];
      args[0] = "SK";
      args[1] = org;
      args[2] = dst;
      args[3] = QDateTime.dateToString(date, "YYYYMMDD HH:MI");
      args[4] = airline;
      args[5] = ((direct) ? "d" : "");
      args[6] = ((nostop) ? "n" : "");
      String returninfo = query(args);

      if (returninfo.length() <= 80) {
        throw new Exception("null data");
      }

      SkResult sr = decodeSK(returninfo);
      return sr;
    } catch (Exception e) {
      throw e;
    }
  }

  private SkResult decodeSK(String returninfo)
    throws Exception
  {
    SkResult sr = new SkResult();
    if (returninfo != null) {
      String[] result = CommandParser.parse(returninfo);
      sr.date1 = QDateTime.stringToDate(result[0], "YYYYMMDDHHMISS");
      sr.date2 = QDateTime.stringToDate(result[1], "YYYYMMDDHHMISS");
      String date1 = QDateTime.dateToString(sr.date1, "YYYYMMMDDWWW");
      String date2 = QDateTime.dateToString(sr.date2, "YYYYMMMDDWWW");
      sr.year1 = date1.substring(2, 4);
      sr.month1 = date1.substring(4, 7);
      sr.dt1 = date1.substring(7, 9);
      sr.day1 = date1.substring(9, 12);
      sr.year2 = date2.substring(2, 4);
      sr.month2 = date2.substring(4, 7);
      sr.dt2 = date2.substring(7, 9);
      sr.day2 = date2.substring(9, 12);
      sr.org = result[2];
      sr.dst = result[3];
      sr.sysmsg = result[4];
      int itemamount = Integer.parseInt(result[5]);
      String[] items = CommandParser.parse(result[6]);
      if (itemamount != items.length)
        return null;
      for (int i = 0; i < itemamount; ++i) {
        SkItem si = new SkItem();
        String[] item = CommandParser.parse(items[i]);
        int segmentamount = Integer.parseInt(item[0]);
        String[] segments = CommandParser.parse(item[1]);
        if (segmentamount != segments.length)
          return null;
        for (int j = 0; j < segmentamount; ++j) {
          Calendar cal;
          SkSegment ss = new SkSegment();
          String[] segment = CommandParser.parse(segments[j]);
          ss.airline = segment[0];
          ss.orgcity = segment[1];
          ss.dstcity = segment[2];
          ss.deptime = segment[3];
          ss.deptimemodify = segment[4];
          ss.arritime = segment[5];
          ss.arritimemodify = segment[6];
          ss.planestyle = segment[7];
          ss.stopnumber = (segment[8].charAt(0) - '0');
          ss.asr = segment[9].equalsIgnoreCase("true");
          ss.meal = segment[10].equalsIgnoreCase("true");
          ss.etkt = segment[11].equalsIgnoreCase("true");
          ss.link = segment[12];
          String effectivedt = segment[13];
          String expirationdt = segment[14];
          ss.opento = segment[15];
          String flightclasses = segment[16];
          if (effectivedt != null)
            if (effectivedt.length() == 13) {
              cal = QDateTime.stringToCalendar(effectivedt, "DDMMMYYHHMISS");
              ss.effectivedate = cal.getTime();
            } else {
              ss.effectivedate = new Date();
            }
          if (expirationdt != null)
            if (expirationdt.length() == 13) {
              cal = QDateTime.stringToCalendar(expirationdt, "DDMMMYYHHMISS");
              ss.expirationdate = cal.getTime();
            } else {
              ss.expirationdate = new Date();
            }
          String datestr = QDateTime.dateToString(ss.effectivedate, "YYMMMDD");
          ss.effectiveyear = datestr.substring(0, 2);
          ss.effectivemonth = datestr.substring(2, 5);
          ss.effectivedt = datestr.substring(5, 7);

          datestr = QDateTime.dateToString(ss.expirationdate, "YYMMMDD");
          ss.expirationyear = datestr.substring(0, 2);
          ss.expirationdt = datestr.substring(5, 7);
          ss.expirationmonth = datestr.substring(2, 5);

          ss.classlist = new String[(flightclasses.length() > 10) ? flightclasses.length() : 10];
          for (int k = 0; k < flightclasses.length(); ++k)
            ss.classlist[k] = flightclasses.substring(k, k + 1);

          ss.flightclasses = flightclasses;
          for (k = 0; k < 7; ++k)
            ss.frequency[k] = ((segment[17].charAt(k) == 'T') ? 1 : false);

          ss.isCodeShare = segment[18].equalsIgnoreCase("true");

          si.putSegment(ss);
        }
        sr.putItem(si);
      }
    }
    return sr;
  }

  protected Object decodeResponse(String serverStr)
    throws Exception
  {
    return decodeSK(serverStr);
  }
}
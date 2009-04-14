package com.travelsky.ibe.client;

import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class AVW extends IBEClient
{
  public AvResult getAvailability(String airline, String date)
    throws Exception
  {
    try
    {
      return getAvailability(
        airline.toUpperCase(), 
        QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"));
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, String date)
    throws Exception
  {
    try
    {
      return getAvailability(
        org.toUpperCase(), 
        dst.toUpperCase(), 
        QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), 
        "ALL", 
        false, 
        false);
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, String date, String airline)
    throws Exception
  {
    try
    {
      return getAvailability(
        org.toUpperCase(), 
        dst.toUpperCase(), 
        QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), 
        airline.toUpperCase(), 
        false, 
        false);
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, String date, String airline, boolean direct)
    throws Exception
  {
    try
    {
      return getAvailability(
        org.toUpperCase(), 
        dst.toUpperCase(), 
        QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), 
        airline.toUpperCase(), 
        direct, 
        false);
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, Date date)
    throws Exception
  {
    try
    {
      return getAvailability(
        org.toUpperCase(), 
        dst.toUpperCase(), 
        date, 
        "ALL", 
        false, 
        false);
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, Date date, String airline)
    throws Exception
  {
    try
    {
      return getAvailability(
        org.toUpperCase(), 
        dst.toUpperCase(), 
        date, 
        airline.toUpperCase(), 
        false, 
        false);
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct)
    throws Exception
  {
    return getAvailability(org, dst, date, airline, direct, false);
  }

  public AvResult getAvailability(String airline, Date date)
    throws Exception
  {
    AvResult ar = new AvResult();

    String[] args = new String[3];
    args[0] = "AVH";
    args[1] = airline;
    args[2] = QDateTime.dateToString(date, "YYYYMMDD HH:MI");
    try
    {
      String retmsg = query(args);

      if (retmsg == null)
        return null;
      String[] result = CommandParser.parse(retmsg);

      String[] temp = CommandParser.parse(result[0]);
      ar.date = QDateTime.stringToDate(temp[0], "YYYYMMMDD");
      ar.org = temp[1];
      ar.dst = temp[2];
      ar.sysmsg = temp[3];
      String strdate = QDateTime.dateToString(ar.date, "YYYYMMMDDWWW");
      ar.day = strdate.substring(9);
      ar.year = strdate.substring(2, 4);
      ar.month = strdate.substring(4, 7);
      ar.dt = strdate.substring(7, 9);

      String[] items = CommandParser.parse(result[1]);
      if (Integer.parseInt(temp[4]) != items.length)
        throw new Exception("Wrong item amount!");
      for (int i = 0; i < items.length; ++i) {
        String[] item = CommandParser.parse(items[i]);
        AvItem ai = new AvItem();
        String[] segments = CommandParser.parse(item[1]);
        if (Integer.parseInt(item[0]) != segments.length)
          throw new Exception("Wrong segment amount in item" + i + "!");
        for (int j = 0; j < segments.length; ++j) {
          String[] segment = CommandParser.parse(segments[j]);
          AvSegment as = new AvSegment();
          as.airline = segment[0];
          as.arritime = segment[1];
          as.arritimemodify = segment[2];

          as.deptime = segment[3];
          as.deptimemodify = segment[4];

          as.orgcity = segment[5];
          as.dstcity = segment[6];
          as.link = segment[7];
          as.planestyle = segment[8];
          as.stopnumber = Integer.parseInt(segment[9]);
          as.asr = segment[10].equalsIgnoreCase("true");

          as.etkt = segment[11].equalsIgnoreCase("true");

          as.meal = segment[12].equalsIgnoreCase("true");

          as.setCodeShare(segment[13].equalsIgnoreCase("true"));

          as.setCarrier(segment[14]);
          for (char c = 'A'; c <= 'Z'; c = (char)(c + '\1'))
            if (segment[15].charAt(c - 'A') != '-')
              as.putCangwei(c, String.valueOf(segment[15].charAt(c - 'A')));


          as.setCangweiNumber(26);
          for (int k = 0; k < 26; ++k)
            as.putCangweiSort(segment[16].charAt(k), segment[17].charAt(k));

          ai.putSegment(as);
        }
        ar.putItem(ai);
      }
      return ar;
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, String date, String airline, boolean direct, boolean e_ticket)
    throws Exception
  {
    try
    {
      return getAvailability(
        org.toUpperCase(), 
        dst.toUpperCase(), 
        QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), 
        airline.toUpperCase(), 
        direct, 
        e_ticket);
    } catch (Exception e) {
      throw e;
    }
  }

  public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct, boolean e_ticket)
    throws Exception
  {
    AvResult ar = new AvResult();

    String[] args = new String[8];
    args[0] = "AVH";
    args[1] = org;
    args[2] = dst;
    args[3] = QDateTime.dateToString(date, "YYYYMMDD HH:MI");
    args[4] = airline;
    args[5] = ((direct) ? "d" : "");
    args[6] = ((e_ticket) ? "ETKT" : "");
    args[7] = "WEB";
    try
    {
      String retmsg = query(args);

      if (retmsg == null)
        return null;
      String[] result = CommandParser.parse(retmsg);
      if ((result == null) || (result.length == 0))
        return null;
      String[] temp = CommandParser.parse(result[0]);
      ar.date = QDateTime.stringToDate(temp[0], "YYYYMMMDD");
      ar.org = temp[1];
      ar.dst = temp[2];
      ar.sysmsg = temp[3];
      String strdate = QDateTime.dateToString(ar.date, "YYYYMMMDDWWW");
      ar.day = strdate.substring(9);
      ar.year = strdate.substring(2, 4);
      ar.month = strdate.substring(4, 7);
      ar.dt = strdate.substring(7, 9);

      String[] items = CommandParser.parse(result[1]);
      if (Integer.parseInt(temp[4]) != items.length)
        throw new Exception("Wrong item amount!");
      for (int i = 0; i < items.length; ++i) {
        String[] item = CommandParser.parse(items[i]);
        AvItem ai = new AvItem();
        String[] segments = CommandParser.parse(item[1]);
        if (Integer.parseInt(item[0]) != segments.length)
          throw new Exception("Wrong segment amount in item" + i + "!");
        for (int j = 0; j < segments.length; ++j) {
          String[] segment = CommandParser.parse(segments[j]);
          AvSegment as = new AvSegment();
          as.depdate = ar.date;
          as.airline = segment[0];
          as.arritime = segment[1];
          as.arritimemodify = segment[2];
          if ((segment[2] != null) && (segment[2].length() == 2));
          switch (segment[2].charAt(0))
          {
          case '+':
            as.arridate = 
              new Date(ar.date.getTime() + (segment[2].charAt(1) - '0') * 86400000);
            break;
          case '-':
            as.arridate = 
              new Date(ar.date.getTime() - (segment[2].charAt(1) - '0') * 86400000);
            break;
          case ',':
          default:
            as.arridate = ar.date; break label563:

            as.arridate = ar.date; }
          label563: as.deptime = segment[3];
          as.deptimemodify = segment[4];
          if ((segment[4] != null) && (segment[4].length() == 2));
          switch (segment[4].charAt(0))
          {
          case '+':
            as.depdate = 
              new Date(ar.date.getTime() + (segment[4].charAt(1) - '0') * 86400000);
            break;
          case '-':
            as.depdate = 
              new Date(ar.date.getTime() - (segment[4].charAt(1) - '0') * 86400000);
            break;
          case ',':
          default:
            as.depdate = ar.date; break label735:

            as.depdate = ar.date; }
          label735: as.orgcity = segment[5];
          as.dstcity = segment[6];
          as.link = segment[7];
          as.planestyle = segment[8];
          as.stopnumber = Integer.parseInt(segment[9]);
          as.asr = segment[10].equalsIgnoreCase("true");
          as.etkt = segment[11].equalsIgnoreCase("true");
          as.meal = segment[12].equalsIgnoreCase("true");

          as.setCodeShare(segment[13].equalsIgnoreCase("true"));

          as.setCarrier(segment[14]);
          for (char c = 'A'; c <= 'Z'; c = (char)(c + '\1'))
            if (segment[15].charAt(c - 'A') != '-')
              as.putCangwei(c, String.valueOf(segment[15].charAt(c - 'A')));


          as.setCangweiNumber(26);
          for (int k = 0; k < 26; ++k)
            as.putCangweiSort(segment[16].charAt(k), segment[17].charAt(k));

          ai.putSegment(as);
        }

        ar.putItem(ai);
      }
      return ar;
    } catch (Exception e) {
      throw e;
    }
  }
}
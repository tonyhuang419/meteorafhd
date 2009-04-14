package com.travelsky.util;

import com.travelsky.ibe.exceptions.DateFormatException;
import com.travelsky.ibe.exceptions.IBEException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class QDateTime
{
  private static Date StdDate;
  private static final long TZRAW;

  static
  {
    TimeZone tz = TimeZone.getDefault();
    TimeZone tzcn = TimeZone.getTimeZone("BEIST");
    TZRAW = tzcn.getRawOffset() - tz.getRawOffset();

    String s = null;
    try {
      s = System.getProperty("SERVERTYPE");
    } catch (Exception localException) {
    }
    if ("TEST".equals(s)) {
      Calendar cal = Calendar.getInstance();
      cal.add(5, -5);

      StdDate = new Date(getStdDateLong(cal.getTimeInMillis()));
    }
    else
    {
      StdDate = null;
    }
  }

  public static final String calendarToString(Calendar calendar, String s)
    throws IBEException
  {
    if ((s == null) || (calendar == null))
      throw new DateFormatException("Null DATA c2s");

    String s1 = s.toUpperCase();
    String[] as = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", 
      "FRIDAY", "SATURDAY" };
    int i = calendar.get(1);
    int j = calendar.get(2);
    int k = calendar.get(5);
    int l = calendar.get(11);
    int i1 = calendar.get(12);
    int j1 = calendar.get(13);
    int k1 = calendar.get(7) - 1;
    int l1 = s1.indexOf("YYYY");
    if (l1 != -1) {
      s1 = s1.substring(0, l1) + i + s1.substring(l1 + 4);
    } else {
      l1 = s1.indexOf("YY");
      if (l1 != -1)
      {
        int j2 = i % 100;

        s1 = s1.substring(0, l1) + 
          ((j2 >= 10) ? String.valueOf(j2) : new StringBuffer("0").append(j2).toString()) + 
          s1.substring(l1 + 2);
      }
    }
    l1 = s1.indexOf("HH");
    if (l1 != -1) {
      s1 = s1.substring(0, l1) + ((l >= 10) ? String.valueOf(l) : new StringBuffer("0").append(l).toString()) + 
        s1.substring(l1 + 2);
    } else {
      l1 = s1.indexOf("H");
      if (l1 != -1)
        s1 = s1.substring(0, l1) + l + s1.substring(l1 + 1);
    }
    l1 = s1.indexOf("MI");
    if (l1 != -1)
      s1 = s1.substring(0, l1) + 
        ((i1 >= 10) ? String.valueOf(i1) : new StringBuffer("0").append(i1).toString()) + 
        s1.substring(l1 + 2);
    l1 = s1.indexOf("SS");
    if (l1 != -1)
      s1 = s1.substring(0, l1) + 
        ((j1 >= 10) ? String.valueOf(j1) : new StringBuffer("0").append(j1).toString()) + 
        s1.substring(l1 + 2);
    l1 = s1.indexOf("DD");
    if (l1 != -1) {
      s1 = s1.substring(0, l1) + ((k >= 10) ? String.valueOf(k) : new StringBuffer("0").append(k).toString()) + 
        s1.substring(l1 + 2);
    } else {
      l1 = s1.indexOf("D");
      if (l1 != -1)
        s1 = s1.substring(0, l1) + k + s1.substring(l1 + 1);
    }
    l1 = s1.indexOf("MMM");
    if (l1 != -1) {
      s1 = s1.substring(0, l1) + encodeMonth(j) + s1.substring(l1 + 3);
    } else {
      l1 = s1.indexOf("MM");
      if (l1 != -1) {
        s1 = s1.substring(0, l1) + 
          ((j >= 9) ? String.valueOf(j + 1) : new StringBuffer("0").append(j + 1).toString()) + 
          s1.substring(l1 + 2);
      } else {
        l1 = s1.indexOf("M");
        if (l1 != -1)
          s1 = s1.substring(0, l1) + (j + 1) + s1.substring(l1 + 1);
      }
    }
    l1 = s1.indexOf("WWW");
    if (l1 != -1) {
      s1 = s1.substring(0, l1) + as[k1].substring(0, 3) + 
        s1.substring(l1 + 3);
    } else {
      int i2 = s1.indexOf("WW");
      if (i2 != -1)
        s1 = s1.substring(0, i2) + as[k1].substring(0, 2) + 
          s1.substring(i2 + 2);
    }
    return s1;
  }

  public static final String dateToString(Date date, String s) throws IBEException
  {
    if ((date == null) || (s == null))
      throw new DateFormatException("Null Data d2s");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendarToString(calendar, s);
  }

  private static final int decodeMonth(String s) throws IBEException
  {
    String[] as = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", 
      "SEP", "OCT", "NOV", "DEC" };
    for (int i = 0; i < 12; ++i)
      if (as[i].equals(s.toUpperCase()))
        return i;

    throw new DateFormatException("BAD MONTH");
  }

  private static final String encodeMonth(int i) throws IBEException
  {
    String[] as = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", 
      "SEP", "OCT", "NOV", "DEC" };
    if ((i >= 0) && (i < 12))
      return as[i];

    throw new DateFormatException(
      "BAD MONTH");
  }

  public static final Calendar stringToCalendar(String s, String s1) throws IBEException
  {
    return stringToCalendar(s, s1, null);
  }

  public static final Calendar stringToCalendar(String s, String s1, Calendar stdCal) throws IBEException
  {
    if ((s == null) || (s1 == null))
      throw new DateFormatException("Null Data s2c");
    int i = 0;
    int k = 0;
    int l = 1;
    int i1 = 0;
    int j1 = 0;
    int k1 = 0;
    String s2 = s1.toUpperCase();
    try {
      int l1 = s2.indexOf("YYYY");
      if (l1 != -1) {
        i = Integer.parseInt(s.substring(l1, l1 + 4)); break label217:
      }
      int i2 = s2.indexOf("YY");
      if (i2 == -1) break label217;
      i = Integer.parseInt(s.substring(i2, i2 + 2));
      if (stdCal == null)
        stdCal = Calendar.getInstance();
      int stdyear = stdCal.get(1);
      if ((stdyear < 2050) && (stdyear > 1990)) {
        if (i > 50) {
          i += 1900; break label217:
        }
        i += 2000; break label217:
      }
      int year = stdyear / 100 * 100;
      i += year;
      while (stdyear - i > 90)
        i += 100;
      while (i - stdyear > 10)
        i -= 100;

    }
    catch (Exception _ex)
    {
      throw new DateFormatException(
        "BAD YEAR");
    }
    try {
      label217: int j2 = s2.indexOf("HH");
      if (j2 == -1) break label262;
      i1 = Integer.parseInt(s.substring(j2, j2 + 2));
    } catch (Exception j2) {
      throw new DateFormatException(
        "BAD HOUR");
    }
    try {
      label262: int k2 = s2.indexOf("MI");
      if (k2 == -1) break label307;
      j1 = Integer.parseInt(s.substring(k2, k2 + 2));
    } catch (Exception k2) {
      throw new DateFormatException(
        "BAD MIN");
    }
    try {
      label307: int l2 = s2.indexOf("SS");
      if (l2 == -1) break label352;
      k1 = Integer.parseInt(s.substring(l2, l2 + 2));
    } catch (Exception l2) {
      throw new DateFormatException(
        "BAD SECOND");
    }
    try {
      label352: int i3 = s2.indexOf("MMM");
      if (i3 != -1) {
        k = decodeMonth(s.substring(i3, i3 + 3)); break label432:
      }
      int j3 = s2.indexOf("MM");
      if (j3 == -1) break label432;
      k = Integer.parseInt(s.substring(j3, j3 + 2)) - 1;
    }
    catch (Exception i3) {
      throw new DateFormatException(
        "BAD MONTH");
    }
    try {
      label432: int k3 = s2.indexOf("DD");
      if (k3 == -1) break label478;
      l = Integer.parseInt(s.substring(k3, k3 + 2));
    } catch (Exception k3) {
      throw new DateFormatException(
        "BAD DATE");
    }
    label478: Calendar calendar = Calendar.getInstance();
    if (i != 0) {
      calendar.set(i, k, l, i1, j1, k1);
      calendar.set(11, i1);
    } else {
      int j = (stdCal == null) ? Calendar.getInstance().get(1) : 
        stdCal.get(1);
      calendar.set(j, k, l, i1, j1, k1);
      calendar.set(11, i1);
      Calendar calendar1 = (stdCal == null) ? Calendar.getInstance() : 
        stdCal;
      calendar1.set(11, 0);
      calendar1.set(12, 0);
      calendar1.set(13, 0);
      calendar.set(14, 0);
      calendar1.set(14, 0);
      if (calendar.before(calendar1))
        calendar.add(1, 1);
    }
    if (!(calendarToString(calendar, s1).equalsIgnoreCase(s)))
      throw new DateFormatException(
        "IT IS A ILLEGAL DATE" + calendarToString(calendar, s1));

    return calendar;
  }

  public static final Date stringToDate(String s, String s1) throws IBEException
  {
    return stringToDate(s, s1, StdDate);
  }

  public static void setStdDate(Date date)
  {
    if (StdDate != null)
      StdDate = date;
  }

  public static final Date stringToDate(String s, String s1, Date stdDate) throws IBEException {
    if ((s1 == null) || (s == null))
      throw new DateFormatException("Null Data s2d");
    if (stdDate == null) {
      long currTime;
      if (StdDate == null) {
        return stringToCalendar(s, s1, null).getTime();
      }

      if ((currTime = getStdDateLong(System.currentTimeMillis())) - StdDate.getTime() > 432000000L)
        setStdDate(new Date(currTime));

      Calendar cal = Calendar.getInstance();
      cal.setTime(StdDate);
      return stringToCalendar(s, s1, cal).getTime();
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(stdDate);
    return stringToCalendar(s, s1, cal).getTime();
  }

  public static long getStdDateLong(long l)
  {
    l -= TZRAW;
    l -= l % 86400000L;
    return (l + TZRAW);
  }
}
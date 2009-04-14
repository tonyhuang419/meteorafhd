package com.travelsky.ibe.client;

import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.io.PrintStream;
import java.util.Date;

public class FF extends IBEClient
{
  public FFResult flightTime(String airNo, String date)
    throws Exception
  {
    Date flightDate = null;
    try {
      if (date.length() == 5) {
        flightDate = QDateTime.stringToDate(date, "DDMMM"); break label40:
      }
      flightDate = QDateTime.stringToDate(date, "DDMMMYY");
    } catch (Exception e) {
      System.out.println("Bad Date Parameter");
    }

    label40: return flightTime(airNo, flightDate);
  }

  public FFResult flightTime(String airNo, Date date)
    throws Exception
  {
    String retmsg;
    String[] args = new String[3];
    args[0] = "FF";
    args[1] = airNo.trim();
    args[2] = QDateTime.dateToString(date, "DDMMMYY");
    try
    {
      retmsg = query(args);
    } catch (Exception e) {
      throw new Exception(e.toString());
    }

    return decodeStr(retmsg);
  }

  protected Object decodeResponse(String serverStr)
    throws Exception
  {
    return decodeStr(serverStr);
  }

  protected FFResult decodeStr(String retmsg) throws Exception
  {
    try {
      if ((retmsg.startsWith("Error")) || (retmsg == null) || (retmsg.equals("")))
        return null;

      String[] decStr = CommandParser.parse(retmsg);

      if (decStr[0] == null)
        return null;

      FFResult result = new FFResult();

      result.airNo = decStr[0];

      if (decStr[1] == null)
        result.flightDate = null;
      else {
        result.flightDate = QDateTime.stringToDate(decStr[1], "YYYYMMDDHH:MI:SS");
      }

      result.planeModel = decStr[2];

      String[] strCity = CommandParser.parse(decStr[3]);
      String[] strTo = CommandParser.parse(decStr[4]);
      String[] strFrom = CommandParser.parse(decStr[5]);
      for (int i = 0; i < strCity.length; ++i) {
        Date fromTime;
        Date toTime;
        if (strTo[i] == null)
          toTime = null;
        else
          toTime = QDateTime.stringToDate(strTo[i], "YYYYMMDDHH:MI:SS");
        if (strFrom[i] == null)
          fromTime = null;
        else
          fromTime = QDateTime.stringToDate(strFrom[i], "YYYYMMDDHH:MI:SS");
        result.addItem(strCity[i], fromTime, toTime);
      }
      return result;
    } catch (Exception e) {
      throw new Exception("Decode Error");
    }
  }
}
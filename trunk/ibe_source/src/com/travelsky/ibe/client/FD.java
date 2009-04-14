package com.travelsky.ibe.client;

import com.travelsky.ibe.exceptions.DecodeErrorException;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class FD extends IBEClient
{
  protected Object decodeResponse(String serverStr)
    throws Exception
  {
    return decodeFD(serverStr);
  }

  protected FDResult decodeFD(String retmsg) throws Exception {
    String[] decStr = CommandParser.parse(retmsg);

    if (decStr[0] == null)
      throw new DecodeErrorException();

    FDResult result = new FDResult();

    result.setDst(decStr[0]);

    result.setOrg(decStr[1]);

    String[] cabinDesc = CommandParser.parse(decStr[2]);
    String[] cabinType = CommandParser.parse(decStr[3]);
    String[] endDate = CommandParser.parse(decStr[4]);
    String[] moneyType = CommandParser.parse(decStr[5]);
    String[] roundPrice = CommandParser.parse(decStr[6]);
    String[] singlePrice = CommandParser.parse(decStr[7]);
    String[] startDate = CommandParser.parse(decStr[8]);
    String[] airlines = CommandParser.parse(decStr[11]);
    String[] rule = CommandParser.parse(decStr[12]);
    String[] airportTax = CommandParser.parse(decStr[13]);
    String[] basicCabinType = CommandParser.parse(decStr[14]);
    String[] discountRate = CommandParser.parse(decStr[15]);
    String[] fuelTax = CommandParser.parse(decStr[16]);
    for (int i = 0; i < cabinType.length; ++i) {
      result.putItem(cabinType[i], cabinDesc[i], singlePrice[i], 
        roundPrice[i], startDate[i], endDate[i], moneyType[i], 
        basicCabinType[i], discountRate[i], airlines[i], 
        rule[i], airportTax[i], fuelTax[i]);
    }

    if (result.getElementNum() != Integer.parseInt(decStr[9])) {
      return null;
    }

    return result;
  }

  /**
   * @deprecated
   */
  public FDResult findPrice(String org, String dst, Date date, String airline)
    throws Exception
  {
    return findPrice(org, dst, 
      QDateTime.dateToString(date, "DDMMMYY"), airline, null, null, false);
  }

  /**
   * @deprecated
   */
  public FDResult findPrice(String org, String dst, Date date, String airline, String planeModel, String passType)
    throws Exception
  {
    return findPrice(org, dst, 
      QDateTime.dateToString(date, "DDMMMYY"), airline, planeModel, 
      passType, false);
  }

  /**
   * @deprecated
   */
  public FDResult findPrice(String org, String dst, String airline)
    throws Exception
  {
    return findPrice(org, dst, QDateTime.dateToString(new Date(), 
      "DDMMMYY"), airline, null, null, false);
  }

  /**
   * @deprecated
   */
  public FDResult findPrice(String org, String dst, String date, String airline)
    throws Exception
  {
    return findPrice(org, dst, date, airline, null, null, false);
  }

  /**
   * @deprecated
   */
  public FDResult findPrice(String org, String dst, String airline, String planeModel, String passType)
    throws Exception
  {
    return findPrice(org, dst, QDateTime.dateToString(new Date(), 
      "DDMMMYY"), airline, planeModel, passType, false);
  }

  /**
   * @deprecated
   */
  public FDResult findPrice(String org, String dst, String date, String airline, String planeModel, String passType)
    throws Exception
  {
    return findPrice(org, dst, date, airline, planeModel, passType, false);
  }

  public FDResult findPrice(String org, String dst, String date, String airline, String planeModel, String passType, boolean fullFareBasis)
    throws Exception
  {
    String retmsg = null;

    String[] args = new String[10];
    args[0] = "FDN";
    args[1] = org.trim();
    args[2] = dst.trim();
    if (!(date.equalsIgnoreCase("ALL")))
      try {
        QDateTime.stringToDate(date, "DDMMMYY");
      } catch (IBEException ex) {
        throw ex;
      } catch (Exception e) {
        throw new Exception("Invalid Parameter");
      }

    args[3] = date;
    args[4] = airline;
    args[5] = planeModel;
    args[6] = passType;
    if (fullFareBasis)
      args[(args.length - 1)] = "FD";
    else
      args[(args.length - 1)] = "";

    try
    {
      retmsg = query(args);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.toString());
    }

    try
    {
      if ((retmsg.startsWith("Error")) || (retmsg == null) || 
        (retmsg.equals("")))
        throw new DecodeErrorException();
      return decodeFD(retmsg);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new DecodeErrorException("Decode Error");
    }
  }
}
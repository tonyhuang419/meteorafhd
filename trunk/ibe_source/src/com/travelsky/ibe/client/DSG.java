package com.travelsky.ibe.client;

import com.travelsky.ibe.exceptions.DecodeErrorException;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class DSG extends IBEClient
{
  protected Object decodeResponse(String serverStr)
    throws Exception
  {
    return decodeServerString(serverStr);
  }

  private DsgResult decodeServerString(String basic)
    throws DecodeErrorException
  {
    DsgResult dr;
    try
    {
      dr = new DsgResult();
      String[] result = CommandParser.parse(basic);
      int dscount = Integer.parseInt(result[0]);

      String[] segments = CommandParser.parse(result[1]);
      if (segments.length != dscount)
        throw new DecodeErrorException("Transport Error, something lost.");

      for (int i = 0; i < dscount; ++i) {
        DsgSegment ds = new DsgSegment();
        String[] segment = CommandParser.parse(segments[i]);

        String[] arrivaltime = CommandParser.parse(segment[0]);
        ds.arrivaltime = new Date[arrivaltime.length];
        for (int j = 0; j < arrivaltime.length; ++j)
          ds.arrivaltime[j] = QDateTime.stringToDate(arrivaltime[j], "DDMMM HHMI");

        ds.cabin = segment[1].charAt(0);
        String[] departuretime = CommandParser.parse(segment[2]);
        ds.departuretime = new Date[departuretime.length];
        for (int j = 0; j < departuretime.length; ++j)
          ds.departuretime[j] = QDateTime.stringToDate(departuretime[j], "DDMMM HHMI");

        ds.distance = Integer.parseInt(segment[3]);
        ds.dstcity = CommandParser.parse(segment[4]);
        ds.elapsedTime = Integer.parseInt(segment[5]);
        ds.flight = segment[6];
        ds.orgcity = CommandParser.parse(segment[7]);
        ds.planestyle = CommandParser.parse(segment[8]);
        ds.stops = Integer.parseInt(segment[9]);
        String[] stoptime = CommandParser.parse(segment[10]);
        ds.stoptime = new int[stoptime.length];

        for (int j = 0; j < stoptime.length; ++j) {
          ds.stoptime[j] = Integer.parseInt(stoptime[j]);
        }

        String[] mealFlags = CommandParser.parse(segment[11]);
        char[] mealFlag = new char[mealFlags.length];
        for (int j = 0; j < mealFlags.length; ++j)
          mealFlag[j] = mealFlags[j].charAt(0);

        ds.setMealFlag(mealFlag);
        dr.putSegment(ds);
      }

      return dr;
    } catch (Exception e) {
      throw new DecodeErrorException(e.toString());
    }
  }

  public DsgResult displaySegment(String flight)
    throws IBEException
  {
    return displaySegment(flight, 'Y', null, null);
  }

  public DsgResult displaySegment(String pnrno, int[] segIndexInPnr)
    throws IBEException
  {
    try
    {
      int l = (segIndexInPnr == null) ? 0 : segIndexInPnr.length;
      String[] cmds = new String[4];
      cmds[0] = "DSG";
      cmds[1] = "pnr";
      cmds[2] = pnrno;
      String[] indexes = new String[l];
      for (int i = 0; i < l; ++i)
        indexes[i] = String.valueOf(segIndexInPnr[i]);

      cmds[3] = CommandParser.encode(indexes);

      String ret = query(cmds);
      return decodeServerString(ret);
    } catch (IBEException e) {
      throw e;
    } catch (Exception ex) {
      throw new IBEException(ex.toString());
    }
  }

  public DsgResult displaySegment(String flight, char cabin, String date, String segment)
    throws IBEException
  {
    String[] cmds;
    try
    {
      cmds = new String[6];
      cmds[0] = "DSG";
      cmds[1] = "single";
      cmds[2] = flight;
      cmds[3] = String.valueOf(cabin);
      cmds[4] = date;
      cmds[5] = segment;

      String ret = query(cmds);

      return decodeServerString(ret);
    } catch (IBEException e) {
      throw e;
    } catch (Exception ex) {
      throw new IBEException(ex.toString());
    }
  }

  public DsgResult displaySegmentPnr(String pnrno)
    throws IBEException
  {
    return displaySegmentPnr(pnrno, null);
  }

  public DsgResult displaySegmentPnr(String pnrno, int[] segIndexInPnr)
    throws IBEException
  {
    try
    {
      int l = (segIndexInPnr == null) ? 0 : segIndexInPnr.length;
      String[] cmds = new String[4];
      cmds[0] = "DSG";
      cmds[1] = "pnr";
      cmds[2] = pnrno;
      String[] indexes = new String[l];
      for (int i = 0; i < l; ++i)
        indexes[i] = String.valueOf(segIndexInPnr[i]);

      cmds[3] = CommandParser.encode(indexes);

      String ret = query(cmds);
      return decodeServerString(ret);
    } catch (IBEException e) {
      throw e;
    } catch (Exception ex) {
      throw new IBEException(ex.toString());
    }
  }
}
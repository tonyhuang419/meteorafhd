package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEClient;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.ibe.exceptions.PMDuplicateException;
import com.travelsky.ibe.exceptions.PMInvalidRequestAirSegException;
import com.travelsky.ibe.exceptions.PMInvalidRequestPaxException;
import com.travelsky.ibe.exceptions.PMNoPNRException;
import com.travelsky.ibe.exceptions.PMPNRCancelledException;
import com.travelsky.ibe.exceptions.PMTicketMissingException;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class PnrManage extends IBEClient
{

  /**
   * @deprecated
   */
  public static final int RECONFIRM_ALL_ACTION = 10;
  public static final int RECONFIRM_ALL_POSSIBLE_ACTION = 5;
  public static final int RECONFIRM_CHECK_STRICTLY = 0;

  public String deletePnr(String pnrno)
    throws Exception
  {
    return deletePnr(pnrno, null); }

  public String deletePnr(String pnrno, String office) throws Exception {
    String[] args;
    try {
      args = new String[4];

      args[0] = "Manage";
      args[1] = "DeletePnr";
      args[2] = pnrno.trim();
      if (office != null)
        args[3] = office.trim();

      String returninfo = query(args);

      return returninfo;
    } catch (Exception e) {
      throw e;
    }
  }

  public String addGroupName(String pnrno, Vector passengers)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[4];

      args[0] = "Manage";
      args[1] = "AddGroupName";
      args[2] = pnrno.trim();
      args[3] = encodePassenger(passengers);

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  private String encodePassenger(Vector passengers)
  {
    String[] encStr;
    try
    {
      encStr = (String[])null;
      String[] subStr = new String[3];

      int n = passengers.size();

      if (n <= 0)
        return null;

      encStr = new String[n];

      for (int i = 0; i < n; ++i) {
        BookPassenger pass = (BookPassenger)passengers.elementAt(i);
        subStr[0] = pass.getName();
        subStr[1] = String.valueOf(pass.getType());
        subStr[2] = String.valueOf(pass.getAge());
        encStr[i] = CommandParser.encode(subStr);
      }

      return CommandParser.encode(encStr); } catch (Exception e) {
    }
    return null;
  }

  public String reduceGroupSeats(String pnrno, int count)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[4];

      args[0] = "Manage";
      args[1] = "ReduceGroupSeat";
      args[2] = pnrno.trim();
      args[3] = String.valueOf(count);

      return query(args);
    }
    catch (Exception e)
    {
      throw e;
    }
  }

  public String removeName(String pnrno, Vector passengers)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[4];

      args[0] = "Manage";
      args[1] = "RemoveName";
      args[2] = pnrno.trim();
      args[3] = encodePassenger(passengers);

      return query(args);
    }
    catch (Exception e)
    {
      throw e;
    }
  }

  public String splitPNR(String pnrno, Vector passengers, int count)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[5];

      args[0] = "Manage";
      args[1] = "SplitGroup";
      args[2] = pnrno.trim();
      args[3] = encodePassenger(passengers);
      args[4] = String.valueOf(count);

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public String addPnrInfo(String pnrno, BookInfomation bookinfo)
    throws Exception
  {
    String[] args;
    try
    {
      args = BookInfomation.encodeBookinfo(bookinfo, pnrno);
      args[0] = "Manage";
      args[1] = "addPnrInfo";

      String retmsg = query(args);
      if (retmsg.startsWith("Error"))
        throw new Exception(retmsg);

      return retmsg;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public String deleteItem(String pnrno, int[] indexes)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[5];

      args[0] = "Manage";
      args[1] = "DeleteItem";
      args[2] = pnrno.trim().toUpperCase();
      args[3] = encodeIntArray(indexes);

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  private String encodeIntArray(int[] intArray)
  {
    String[] encStr;
    try
    {
      encStr = (String[])null;

      encStr = new String[intArray.length];

      for (int i = 0; i < intArray.length; ++i) {
        encStr[i] = String.valueOf(intArray[i]);
      }

      return CommandParser.encode(encStr); } catch (Exception e) {
    }
    return null;
  }

  public String changePaxName(String pnrno, String oldname, String newname)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[5];

      args[0] = "Manage";
      args[1] = "changePaxName";
      args[2] = pnrno.trim().toUpperCase();

      args[3] = oldname;
      args[4] = newname;

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public String cancelAirSeg(String pnrno, PNRAirSeg segment)
    throws IBEException
  {
    String[] args;
    try
    {
      args = new String[5];
      args[0] = "Manage";
      args[1] = "doNoAirseg";
      args[2] = pnrno;
      if (segment == null) {
        args[3] = null;
        args[4] = null;
      } else {
        args[3] = segment.getAirNo();
        args[4] = QDateTime.dateToString(segment.getDepartureTime(), 
          "DDMMMYY");
      }

      return query(args);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.toString());
    }
  }

  public String confirmAirSeg(String pnrno, PNRAirSeg segment)
    throws IBEException
  {
    String[] args;
    try
    {
      args = new String[5];
      args[0] = "Manage";
      args[1] = "dokkAirseg";
      args[2] = pnrno;
      if (segment == null) {
        args[3] = null;
        args[4] = null;
      } else {
        args[3] = segment.getAirNo();
        args[4] = QDateTime.dateToString(segment.getDepartureTime(), 
          "DDMMMYY");
      }

      return query(args);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.toString());
    }
  }

  public String confirmAirSeg(String pnrno, PNRAirSeg segment, String action)
    throws IBEException
  {
    String[] args;
    try
    {
      args = new String[5];
      args[0] = "Manage";
      args[1] = "dokkAirseg";
      args[2] = pnrno;
      if (segment == null) {
        args[3] = null;
        args[4] = null;
      } else {
        args[3] = segment.getAirNo();
        args[4] = QDateTime.dateToString(segment.getDepartureTime(), 
          "DDMMMYY");
      }
      if ((action == null) || (action.length() != 2))
        args[5] = "HK";
      else
        args[5] = action;
      return query(args);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.toString());
    }
  }

  public String reconfirmAirSeg(String pnrno, PNRAirSeg segment, int force)
    throws IBEException
  {
    String[] args;
    try
    {
      args = new String[6];
      args[0] = "Manage";
      args[1] = "dorrAirseg";
      args[2] = pnrno;
      if (segment == null) {
        args[3] = null;
        args[4] = null;
      } else {
        args[3] = segment.getAirNo();
        args[4] = QDateTime.dateToString(segment.getDepartureTime(), 
          "DDMMMYY");
      }
      args[5] = String.valueOf(force);
      return query(args);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.toString());
    }
  }

  public String changeAirSeg(String pnr, BookAirSeg oldSeg, BookAirSeg newSeg)
    throws IBEException
  {
    String[] args;
    try
    {
      args = new String[6];

      args[0] = "Manage";
      args[1] = "changeAirSeg";
      args[2] = pnr;

      args[3] = QDateTime.dateToString(oldSeg.getDepartureTime(), 
        "ddmmmyy");
      args[4] = oldSeg.getAirNo();

      String[] airsegnormal = new String[8];

      int j = 0;
      airsegnormal[j] = newSeg.getAirNo();
      ++j;

      airsegnormal[j] = String.valueOf(newSeg.getFltClass());
      ++j;
      airsegnormal[j] = newSeg.getOrgCity();
      ++j;
      airsegnormal[j] = newSeg.getDesCity();
      ++j;

      airsegnormal[j] = newSeg.getActionCode();
      if ((airsegnormal[j] == null) || (airsegnormal[j].equals("")))
        airsegnormal[j] = "nn";
      ++j;
      airsegnormal[j] = "0";
      ++j;

      airsegnormal[j] = QDateTime.dateToString(
        newSeg.getDepartureTime(), "ddmmmyy");
      ++j;
      airsegnormal[j] = String.valueOf(newSeg.getType());

      args[5] = CommandParser.encode(airsegnormal);

      return query(args);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.getMessage());
    }
  }

  public String changeAirSeg(String pnr, Vector oldSegs, Vector newSegs)
    throws IBEException
  {
    String[] args;
    try
    {
      args = new String[5];

      args[0] = "Manage";
      args[1] = "changeAirSegs";
      args[2] = pnr;

      if ((oldSegs == null) || (newSegs == null))
        throw new PMInvalidRequestAirSegException();
      String[] oldseg = new String[oldSegs.size()];
      for (int i = 0; i < oldSegs.size(); ++i) {
        String[] airsegnormal = new String[8];
        BookAirSeg segment = null;
        try {
          segment = (BookAirSeg)oldSegs.elementAt(i);
        } catch (ClassCastException e) {
          throw new PMInvalidRequestAirSegException();
        }
        if (segment != null) {
          int j = 0;
          airsegnormal[j] = segment.getAirNo();
          ++j;

          airsegnormal[j] = String.valueOf(segment.getFltClass());
          ++j;
          airsegnormal[j] = segment.getOrgCity();
          ++j;
          airsegnormal[j] = segment.getDesCity();
          ++j;

          airsegnormal[j] = segment.getActionCode();
          if ((airsegnormal[j] == null) || (airsegnormal[j].equals("")))
            airsegnormal[j] = "nn";
          ++j;
          airsegnormal[j] = "0";
          ++j;

          airsegnormal[j] = QDateTime.dateToString(
            segment.getDepartureTime(), "ddmmmyy");
          ++j;
          airsegnormal[j] = String.valueOf(segment.getType());
        } else {
          throw new PMInvalidRequestAirSegException();
        }
        oldseg[i] = CommandParser.encode(airsegnormal);
      }
      args[3] = CommandParser.encode(oldseg);

      String[] newseg = new String[newSegs.size()];
      for (int i = 0; i < newSegs.size(); ++i) {
        String[] airsegnormal = new String[8];
        BookAirSeg segment = null;
        try {
          segment = (BookAirSeg)newSegs.elementAt(i);
        } catch (ClassCastException e) {
          throw new PMInvalidRequestAirSegException();
        }
        if (segment != null) {
          int j = 0;
          airsegnormal[j] = segment.getAirNo();
          ++j;

          airsegnormal[j] = String.valueOf(segment.getFltClass());
          ++j;
          airsegnormal[j] = segment.getOrgCity();
          ++j;
          airsegnormal[j] = segment.getDesCity();
          ++j;

          airsegnormal[j] = segment.getActionCode();
          if ((airsegnormal[j] == null) || (airsegnormal[j].equals("")))
            airsegnormal[j] = "nn";
          ++j;
          airsegnormal[j] = "0";
          ++j;

          airsegnormal[j] = QDateTime.dateToString(
            segment.getDepartureTime(), "ddmmmyy");
          ++j;
          airsegnormal[j] = String.valueOf(segment.getType());
        } else {
          throw new PMInvalidRequestAirSegException();
        }
        newseg[i] = CommandParser.encode(airsegnormal);
      }
      args[4] = CommandParser.encode(newseg);

      return query(args);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.getMessage());
    }
  }

  public String changePax(String pnrno, BookPassenger oldpax, BookPassenger newpax)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[9];

      args[0] = "Manage";
      args[1] = "changePax";
      args[2] = pnrno.trim().toUpperCase();

      args[3] = oldpax.getName();
      args[4] = String.valueOf(oldpax.getType());
      args[5] = String.valueOf(oldpax.getAge());
      args[6] = newpax.getName();
      args[7] = String.valueOf(newpax.getType());
      args[8] = String.valueOf(newpax.getAge());

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public String changePaxId(String pnrno, BookPassenger oldpax, BookPassenger newpax, String foid)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[10];

      args[0] = "Manage";
      args[1] = "changePaxId";
      args[2] = pnrno.trim().toUpperCase();

      args[3] = oldpax.getName();
      args[4] = String.valueOf(oldpax.getType());
      args[5] = String.valueOf(oldpax.getAge());
      args[6] = newpax.getName();
      args[7] = String.valueOf(newpax.getType());
      args[8] = String.valueOf(newpax.getAge());
      args[9] = foid;

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public String changeId(String pnrno, BookPassenger pax, String foid)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[7];

      args[0] = "Manage";
      args[1] = "changeId";
      args[2] = pnrno.trim().toUpperCase();

      args[3] = pax.getName();
      args[4] = String.valueOf(pax.getType());
      args[5] = String.valueOf(pax.getAge());

      args[6] = foid;

      return query(args);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public String changeTkt(String pnrno, BookInfomation bookinfo)
    throws PMNoPNRException, PMPNRCancelledException, PMTicketMissingException, PMDuplicateException, Exception
  {
    String[] args;
    try
    {
      args = BookInfomation.encodeBookinfo(bookinfo, pnrno);

      args[0] = "Manage";
      args[1] = "changeTkt";
      args[2] = "AirSeg:";
      args[3] = "Contact:";
      args[4] = "Infant:";
      args[5] = "OSI:";
      args[6] = "RMK:";
      args[7] = "SSR:";
      args[8] = "Tkt:";
      args[9] = "GN:";
      args[10] = "NM:";
      args[11] = pnrno.trim().toUpperCase();
      args[12] = bookinfo.getEnvelopType();
      args[13] = "FC:";
      args[14] = "FP:";
      args[15] = "FN:";
      args[16] = "BA:";
      args[17] = "EI:";
      args[18] = "TC:";

      args[20] = "OI:";

      return query(args);
    } catch (Exception e) {
      throw e;
    }
  }

  public String changeIds(String pnrno, Map paxfoidMap)
    throws PMInvalidRequestPaxException, IBEException
  {
    String[] args;
    try
    {
      args = new String[4];

      args[0] = "Manage";
      args[1] = "changeIds";
      args[2] = pnrno.trim().toUpperCase();
      args[3] = encodePaxFoidMap(paxfoidMap);

      return query(args);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e);
    }
  }

  public String changePaxIds(String pnrno, List oldPax, List newPax, List foid)
    throws IBEException, PMInvalidRequestPaxException
  {
    String[] args;
    try
    {
      args = new String[6];

      args[0] = "Manage";
      args[1] = "changePaxIds";
      args[2] = pnrno.trim().toUpperCase();
      String[] oldPaxes = new String[oldPax.size()];
      String[] newPaxes = new String[newPax.size()];
      String[] foids = new String[oldPax.size()];

      if (oldPax.size() != newPax.size())
        throw new PMInvalidRequestPaxException("需要修改的旅客和修改到的旅客数目不一致");

      String[] s = new String[3];
      for (int i = 0; i < oldPax.size(); ++i)
      {
        Object o = oldPax.get(i);
        if (o == null)
          throw new PMInvalidRequestPaxException("OLDPAX " + i + 
            " IS null.");
        if (o instanceof String) {
          s[0] = String.valueOf(o).toUpperCase();
          s[1] = String.valueOf(-1);
          s[2] = String.valueOf(0);
        } else if (o instanceof BookPassenger) {
          s[0] = ((BookPassenger)o).getName().toUpperCase();
          s[1] = String.valueOf(((BookPassenger)o).getType());
          s[2] = String.valueOf(((BookPassenger)o).getAge());
        } else if (o instanceof PNRPassenger) {
          s[0] = 
            String.valueOf(((PNRPassenger)o).getName()).toUpperCase();
          s[1] = String.valueOf(((PNRPassenger)o).getType());
          s[2] = String.valueOf(((PNRPassenger)o).getAge());
        } else {
          throw new PMInvalidRequestPaxException("旅客类型不正确"); }
        oldPaxes[i] = CommandParser.encode(s);
        o = newPax.get(i);
        if (o == null)
          throw new PMInvalidRequestPaxException("NEWPAX " + i + 
            " IS null.");

        if (o instanceof String) {
          s[0] = String.valueOf(o);
          s[1] = String.valueOf(0);
          s[2] = String.valueOf(0);
        } else if (o instanceof BookPassenger) {
          s[0] = ((BookPassenger)o).getName();
          s[1] = String.valueOf(((BookPassenger)o).getType());
          s[2] = String.valueOf(((BookPassenger)o).getAge());
        } else {
          throw new PMInvalidRequestPaxException("旅客类型不正确"); }
        newPaxes[i] = CommandParser.encode(s);
        if ((foid != null) && (foid.size() > i))
          foids[i] = ((String)foid.get(i));
        else
          foids[i] = null;
      }
      args[3] = CommandParser.encode(oldPaxes);
      args[4] = CommandParser.encode(newPaxes);
      args[5] = CommandParser.encode(foids);

      return query(args);
    }
    catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new IBEException(e);
    }
  }

  public PATResult doPat(String pnrno, boolean ifinput)
    throws IBEException
  {
    String[] cmd = new String[4];
    cmd[0] = "Manage";
    cmd[1] = "PATM";
    cmd[2] = pnrno;
    cmd[3] = ((ifinput) ? "1" : "0");
    try {
      String res = query(cmd);
      return enResult(res);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.getMessage());
    }
  }

  public PATResult doPat(String pnrno, String option, String type, int index, boolean ifinput)
    throws IBEException
  {
    String[] cmd = new String[7];
    cmd[0] = "Manage";
    cmd[1] = "PATM";
    cmd[2] = pnrno;
    cmd[3] = ((ifinput) ? "1" : "0");
    cmd[4] = ((option == null) ? "" : option.trim());
    cmd[5] = ((type == null) ? "" : type.trim());
    cmd[6] = String.valueOf(index);
    try {
      String res = query(cmd);
      return enResult(res);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.getMessage());
    }
  }

  public PATResult doPatA(String pnrno, boolean ifinput)
    throws IBEException
  {
    String[] cmd = new String[4];
    cmd[0] = "Manage";
    cmd[1] = "PATA";
    cmd[2] = pnrno;
    cmd[3] = ((ifinput) ? "1" : "0");
    try {
      String res = query(cmd);
      return enResult(res);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.getMessage());
    }
  }

  public PATResult doPatASecond(PATResult patres_pre, String pnrno, int index, int psgid)
    throws IBEException
  {
    String patres_str;
    try
    {
      patres_str = deResult(patres_pre);
      String[] cmd = new String[6];
      cmd[0] = "Manage";
      cmd[1] = "PATASECOND";
      cmd[2] = pnrno;
      cmd[3] = String.valueOf(index);
      cmd[4] = patres_str;
      cmd[5] = String.valueOf(psgid);
      String res = query(cmd);
      return enResult(res);
    } catch (IBEException e) {
      throw e;
    } catch (Exception e) {
      throw new IBEException(e.getMessage());
    }
  }

  public PATResult enResult(String rts) throws Exception {
    PATResult patres = new PATResult();
    if (CommandParser.parse(rts).length < 2)
      return null;
    String[] res = CommandParser.parse(rts);
    if (res[0] != null) {
      String[] res_segs = CommandParser.parse(res[0]);
      ArrayList seglist = new ArrayList();
      for (int i = 0; i < res_segs.length; ++i) {
        String seg = res_segs[i];
        seglist.add(seg);
      }
      patres.setSegments(seglist);
    }

    if (res[1] != null) {
      String[] res_fares = CommandParser.parse(res[1]);
      ArrayList farelist = new ArrayList();
      for (int j = 0; j < res_fares.length; ++j)
        if (res_fares[j] != null) {
          String[] res_item = CommandParser.parse(res_fares[j]);
          PATFareItem item = new PATFareItem();
          item.setFare(res_item[0]);
          item.setIndex(Integer.parseInt(res_item[1]));

          if (res_item.length == 4) {
            if (res_item[2] != null)
              item.setFc(RT.decodeFC(res_item[2]));

            if (res_item[3] != null) {
              item.setFn(RT.decodeFN(res_item[3]));
            }

          }

          farelist.add(item);
        }

      patres.setFareItems(farelist);
    }
    if (res[2] != null)
      try {
        int farenumber = Integer.parseInt(res[2]);
        patres.setFarenumber(farenumber);
      } catch (Exception e) {
        throw new Exception("FareNumber wrong");
      }

    return patres;
  }

  public String deResult(PATResult patres) throws Exception {
    String[] res = new String[3];
    if (patres != null) {
      ArrayList segs = patres.getSegments();
      ArrayList farelist = patres.getFareItems();
      if (segs == null) {
        res[0] = null;
      } else {
        String[] res_segs = new String[segs.size()];
        for (int i = 0; i < segs.size(); ++i) {
          String seg = (String)segs.get(i);
          res_segs[i] = seg;
        }
        res[0] = CommandParser.encode(res_segs);
      }
      if (farelist == null) {
        res[1] = null;
      } else {
        String[] res_fares = new String[farelist.size()];
        for (int j = 0; j < farelist.size(); ++j) {
          PATFareItem item = (PATFareItem)farelist.get(j);
          if (item == null) {
            res_fares[j] = null;
          } else {
            String[] res_item = new String[2];
            res_item[0] = item.getFare();
            res_item[1] = String.valueOf(item.getIndex());
            res_fares[j] = CommandParser.encode(res_item);
          }
        }

        res[1] = CommandParser.encode(res_fares);
      }
      res[2] = String.valueOf(patres.getFareNumber());

      return CommandParser.encode(res);
    }
    return null;
  }

  private String encodePaxFoidMap(Map paxfoidMap) throws IBEException {
    String[] encStr;
    try {
      encStr = (String[])null;
      String[] subStr = new String[4];

      if (paxfoidMap.isEmpty())
        return null;

      encStr = new String[paxfoidMap.size()];

      Iterator it = paxfoidMap.keySet().iterator();
      int i = 0;
      while (it.hasNext()) {
        Object key = it.next();
        if (key instanceof String) {
          subStr[0] = ((String)key);
          if (subStr[0].startsWith("P"))
            try {
              Integer.parseInt(subStr[0].substring(1));
            } catch (Exception e) {
              throw new PMInvalidRequestPaxException(
                "PaxFoidMan Key must be String(Pn)");
            }
          else
            throw new PMInvalidRequestPaxException(
              "PaxFoidMan Key must be String(Pn)");

          subStr[1] = String.valueOf(0);
          subStr[2] = String.valueOf(0);
        } else if (key instanceof BookPassenger) {
          BookPassenger pass = (BookPassenger)key;
          subStr[0] = pass.getName();
          subStr[1] = String.valueOf(pass.getType());
          subStr[2] = String.valueOf(pass.getAge());
        } else {
          throw new PMInvalidRequestPaxException(
            "PaxFoidMan Key must be String(Pn) or BookPassenger Object");
        }
        subStr[3] = ((String)paxfoidMap.get(key));
        encStr[i] = CommandParser.encode(subStr);
        ++i;
      }
      return CommandParser.encode(encStr);
    } catch (IBEException ex) {
      throw ex; } catch (Exception e) {
    }
    return null;
  }
}
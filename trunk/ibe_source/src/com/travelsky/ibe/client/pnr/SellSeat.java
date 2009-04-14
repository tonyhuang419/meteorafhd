package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEClient;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class SellSeat extends IBEClient
{
  BookInfomation bif;

  public SellSeat(BookInfomation bookinfo)
  {
    this.bif = bookinfo;
  }

  public SellSeat()
  {
    this.bif = new BookInfomation();
  }

  public void addAdult(String name)
    throws Exception
  {
    this.bif.addAdult(name);
  }

  public void addAirSeg(BookAirSeg airseg)
    throws Exception
  {
    this.bif.addAirSeg(airseg);
  }

  public void addAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, Date departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
    this.bif.addAirSeg(airseg);
  }

  public void addAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, String departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
    this.bif.addAirSeg(airseg);
  }

  public void addBA(BookBA ba)
  {
    this.bif.addBA(ba);
  }

  public void addBA(String ba)
  {
    this.bif.addBA(new BookBA(ba));
  }

  public void addChild(String name)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger(name, 1, 0);
    this.bif.addPassenger(passenger);
  }

  public void addContact(BookContact contact)
    throws Exception
  {
    this.bif.addContact(contact);
  }

  public void addContact(String contactinfo)
    throws Exception
  {
    BookContact contact = new BookContact(contactinfo);
    this.bif.addContact(contact);
  }

  public void addEI(BookEI ei)
  {
    if (ei != null)
      this.bif.addEI(ei);
  }

  public void addFC(BookFC fc)
  {
    this.bif.addFC(fc);
  }

  public void addFC(String fcStr)
  {
    this.bif.addFC(new BookFC(fcStr));
  }

  public void addFN(BookFN fn)
  {
    this.bif.addFN(fn);
  }

  public void addFN(String fnStr)
  {
    this.bif.addFN(new BookFN(fnStr));
  }

  public void addFP(BookFP fp)
  {
    this.bif.addFP(fp);
  }

  public void addFP(String fpStr)
  {
    this.bif.addFP(new BookFP(fpStr));
  }

  public void addInfant(BookInfant infant)
    throws Exception
  {
    this.bif.addInfant(infant);
  }

  public void addInfant(Date birth, String carrierName, String name)
    throws Exception
  {
    BookInfant infant = new BookInfant(birth, carrierName, name);
    this.bif.addInfant(infant);
  }

  public void addInfant(String birth, String carrierName, String name)
    throws Exception
  {
    BookInfant infant = new BookInfant(birth, carrierName, name);
    this.bif.addInfant(infant);
  }

  public void addInfoAirSeg(String orgCity, String desCity)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(orgCity, desCity);
    this.bif.addAirSeg(airseg);
  }

  public void addInfoAirSeg(String orgCity, String desCity, Date departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(orgCity, desCity, departureTime);
    this.bif.addAirSeg(airseg);
  }

  public void addInfoAirSeg(String orgCity, String desCity, String departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(orgCity, desCity, departureTime);
    this.bif.addAirSeg(airseg);
  }

  public void addOI(BookOI oi)
  {
    this.bif.addOI(oi);
  }

  public void addOpenAirSeg(String airNo, char fltClass, String orgCity, String desCity)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity);
    this.bif.addAirSeg(airseg);
  }

  public void addOSI(BookOSI osi)
    throws Exception
  {
    this.bif.addOSI(osi);
  }

  public void addOSI(String airCode, String osiinfo)
    throws Exception
  {
    BookOSI osi = new BookOSI(airCode, osiinfo);
    this.bif.addOSI(osi);
  }

  public void addPassenger(BookPassenger passenger)
    throws Exception
  {
    this.bif.addPassenger(passenger);
  }

  public void addRMK(BookRMK rmk)
    throws Exception
  {
    this.bif.addRMK(rmk);
  }

  public void addRMK(String rmktype, String rmkinfo)
    throws Exception
  {
    BookRMK rmk = new BookRMK(rmktype, rmkinfo);
    this.bif.addRMK(rmk);
  }

  public void addSSR(BookSSR ssr)
    throws Exception
  {
    this.bif.addSSR(ssr);
  }

  public void addSSR_DOCA(String airline, String doca_type, String country, String address, String city, String state, String zip, boolean infant, String name)
    throws Exception
  {
    addSSR(new BookSSR("doca", airline, "hk", 1, "", "", ' ', "", doca_type + "/" + country + "/" + address + "/" + city + "/" + state + "/" + zip + "", name));
  }

  public void addSSR_DOCO_V(String airline, String birthplace, String visaNum, String issueplace, Date issuedate, String issueCountry, boolean infant, String name)
    throws Exception
  {
    addSSR(new BookSSR("Doco", airline, "Hk", 1, "", "", ' ', "", birthplace + "/V/" + visaNum + "/" + issueplace + "/" + QDateTime.dateToString(issuedate, "ddmmmyy") + "/" + issueCountry + "", name));
  }

  public void addSSR_DOCS(String airline, String doc_type, String issueCountry, String doc_No, String nationality, Date birth, String gender, boolean infant, Date expiry_Date, String surname, String firstname, String midname, boolean holder, String name)
    throws Exception
  {
    String expiry;
    if (expiry_Date != null)
      expiry = QDateTime.dateToString(expiry_Date, "ddmmmyy");
    else
      expiry = "";

    addSSR(new BookSSR("DOCS", airline, "hK", 1, "", "", ' ', "", doc_type + "/" + issueCountry + "/" + doc_No + "/" + nationality + "/" + QDateTime.dateToString(birth, "ddmmmyy") + "/" + gender + "/" + expiry + "/" + surname + "/" + firstname + 
      new StringBuffer("/").append(midname).toString() + "", name));
  }

  public void addSSR_FOID(String airline, String idtype, String id, String name)
    throws Exception
  {
    addSSR(new BookSSR("FOID", airline, "hk", 0, "", "", ' ', "", idtype + id, name));
  }

  public void addSSR_FQTV(String cardno, String name)
    throws Exception
  {
    addSSR(new BookSSR("FQTV", cardno.substring(0, 2), "hk", 0, "", "", ' ', "", cardno, name));
  }

  public void addSSR_FQTV(String airline, String cardno, String name)
    throws Exception
  {
    addSSR(new BookSSR("FQTV", airline, "hk", 0, "", "", ' ', "", cardno, name));
  }

  public void addSSR_PSPT(String airline, String psptinfo, String name)
    throws Exception
  {
    addSSR(new BookSSR("PSPT", airline, "hk", 1, "", "", ' ', "", psptinfo, name));
  }

  public void addSSR_PSPT(String airline, String psptNo, String NationCode, Date birth, String sex, String paxname, boolean infant, boolean holder, String name)
    throws Exception
  {
    addSSR(new BookSSR("PSPT", airline, "hk", 1, "", "", ' ', "", psptNo + "/" + NationCode + "/" + QDateTime.dateToString(birth, "ddmmmyy") + "/" + paxname + "/" + sex + "" + "", name));
  }

  public void addTC(BookTC tc)
  {
    if (tc != null)
      this.bif.addTC(tc);
  }

  public void addTktstatus(BookTktStatus bt) {
    if (bt != null)
      this.bif.addTktstatus(bt);
  }

  public void addUnCompanionChild(String name, int age)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger(name, 2, age);
    this.bif.addPassenger(passenger);
  }

  public String commit()
    throws Exception
  {
    String[] args;
    try
    {
      args = BookInfomation.encodeBookinfo(this.bif, "");

      args[0] = "SellSeat";
      String para = args[1];
      args[1] = args[10];
      args[10] = args[12];
      args[11] = args[13];
      args[12] = args[14];
      args[13] = args[15];
      args[14] = "";
      args[15] = args[16];
      args[16] = args[17];
      args[17] = args[18];
      args[18] = para;

      String retmsg = query(args);
      if (retmsg.startsWith("Error")) {
        throw new IBEException(retmsg);
      }

      return retmsg;
    } catch (Exception ex) {
      throw ex;
    }
  }

  public SSResult commit1()
    throws Exception
  {
    String[] args;
    try
    {
      args = BookInfomation.encodeBookinfo(this.bif, "");

      args[0] = "SellSeat";
      String para = args[1];
      args[1] = args[10];
      args[10] = args[12];
      args[11] = args[13];
      args[12] = args[14];
      args[13] = args[15];
      args[14] = "DETAILINFO";
      args[15] = args[16];
      args[16] = args[17];
      args[17] = args[18];
      args[18] = para;

      String retmsg = query(args);
      if (retmsg.startsWith("Error")) {
        throw new IBEException(retmsg);
      }

      SSResult sr = decodeSS(retmsg);
      return sr;
    } catch (Exception ex) {
      throw ex;
    }
  }

  SSResult decodeSS(String retmsg) throws Exception {
    String[] result = CommandParser.parse(retmsg);
    SSResult sr = new SSResult();

    sr.setPnrno(result[2]);
    String[] comments = CommandParser.parse(result[1]);
    String[] segments = CommandParser.parse(result[0]);

    for (int i = 0; i < segments.length; ++i) {
      String[] segment = CommandParser.parse(segments[i]);
      SSSegment seg = new SSSegment();
      seg.setTktNum(Integer.parseInt(segment[0]));
      seg.setAirNo(segment[1]);
      seg.setActionCode(segment[2]);
      seg.setOrgCity(segment[3]);
      seg.setDesCity(segment[4]);
      seg.setOriginalAirSeg(segment[5]);
      seg.setDepartureTime(QDateTime.stringToDate(segment[6], "yyyymmddhhmi"));
      seg.setArrivalTime(QDateTime.stringToDate(segment[7], "yyyymmddhhmi"));
      seg.setFltClass(segment[8].charAt(0));
      sr.addSegment(seg);
    }
    for (i = 0; i < comments.length; ++i)
      sr.addComment(comments[i]);

    return sr;
  }

  public boolean isGroupTicket()
  {
    return this.bif.isGroupticket();
  }

  public void setEnvelopType(String newEnvelopType)
  {
    this.bif.setEnvelopType(newEnvelopType);
  }

  public void setGroupName(String groupName)
    throws Exception
  {
    this.bif.setGroupName(groupName);
  }

  public void setGroupTicket(boolean isGroup)
    throws Exception
  {
    this.bif.setGroupticket(isGroup);
  }

  public void setOffice(String office)
    throws Exception
  {
    this.bif.setOfficeCode(office);
  }

  public void setPassengerNumber(int passengerNumber)
    throws Exception
  {
    this.bif.setPassengernumber(passengerNumber);
  }

  public void setTimelimit(Date dateLimit)
    throws Exception
  {
    this.bif.setTimelimit(QDateTime.dateToString(dateLimit, "YYYY-MM-DD HH:MI:SS"));
  }

  public void setTimelimit(String dateLimit)
    throws Exception
  {
    this.bif.setTimelimit(dateLimit);
  }

  protected Object decodeResponse(String serverStr)
    throws Exception
  {
    if (serverStr.length() < 10)
      return serverStr;
    return decodeSS(serverStr);
  }
}
package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.ibe.exceptions.IBELocalException;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Date;
import java.util.Vector;

public class BookInfomation extends IBEResult
{
  private static final long serialVersionUID = -2999916970547881756L;
  private Vector airsegs;
  private Vector bas;
  private Vector contacts;
  private Vector eis;
  private String envelopType;
  private Vector fcs;
  private Vector fns;
  private Vector fps;
  private String groupName;
  private boolean groupticket;
  private Vector infants;
  private String officeCode = "";
  private Vector ois;
  private Vector osis;
  private int passengernumber;
  private Vector passengers;
  private String pnrno;
  private Vector rmks;
  private Vector ssrs;
  private Vector tcs;
  private Vector tktStatus;
  private String tkttimelimit;

  public BookInfomation()
  {
    this.airsegs = new Vector();
    this.contacts = new Vector();
    this.infants = new Vector();
    this.osis = new Vector();
    this.bas = new Vector();
    this.passengers = new Vector();
    this.rmks = new Vector();
    this.ssrs = new Vector();
    this.tkttimelimit = "";
    this.pnrno = "";
    this.fcs = new Vector();
    this.fps = new Vector();
    this.fns = new Vector();
    this.eis = new Vector();
    this.tcs = new Vector();
    this.tktStatus = new Vector();
    this.ois = new Vector();
  }

  public void addAdult(String name)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger(name);
    this.passengers.addElement(passenger);
  }

  public void addAirSeg(BookAirSeg airseg)
    throws Exception
  {
    this.airsegs.addElement(airseg);
  }

  public void addAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, Date departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
    this.airsegs.addElement(airseg);
  }

  public void addAirSeg(String airNo, char fltClass, String orgCity, String desCity, String actionCode, int tktNum, String departureTime)
    throws Exception
  {
    if (departureTime != null)
      if (departureTime.length() == 7)
        departureTime = QDateTime.dateToString(QDateTime.stringToDate(departureTime, "ddmmmyy"), "yyyy-mm-dd");
      else if (departureTime.length() == 5)
        departureTime = QDateTime.dateToString(QDateTime.stringToDate(departureTime, "ddmmm"), "yyyy-mm-dd");
    BookAirSeg airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity, actionCode, tktNum, departureTime, 0);
    this.airsegs.addElement(airseg);
  }

  public void addBA(BookBA ba)
  {
    this.bas.addElement(ba);
  }

  public void addBA(String ba)
  {
    this.bas.addElement(new BookBA(ba));
  }

  public void addChild(String name)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger(name, 1, 0);
    this.passengers.addElement(passenger);
  }

  public void addContact(BookContact contact)
    throws Exception
  {
    this.contacts.addElement(contact);
  }

  public void addContact(String contactinfo)
    throws Exception
  {
    BookContact contact = new BookContact(contactinfo);
    this.contacts.addElement(contact);
  }

  public void addEI(BookEI ei)
  {
    if (ei != null)
      this.eis.addElement(ei);
  }

  public void addFC(BookFC fc)
  {
    this.fcs.addElement(fc);
  }

  public void addFC(String fcStr)
  {
    this.fcs.addElement(new BookFC(fcStr));
  }

  public void addFN(BookFN fn)
  {
    this.fns.addElement(fn);
  }

  public void addFN(String fnStr)
  {
    this.fns.addElement(new BookFN(fnStr));
  }

  public void addFP(BookFP fp)
  {
    this.fps.addElement(fp);
  }

  public void addFP(String fpStr)
  {
    this.fps.addElement(new BookFP(fpStr));
  }

  public void addInfant(BookInfant infant)
    throws Exception
  {
    this.infants.addElement(infant);
  }

  public void addInfant(Date birth, String carrierName, String name)
    throws Exception
  {
    BookInfant infant = new BookInfant(birth, carrierName, name);
    this.infants.addElement(infant);
  }

  public void addInfant(String birth, String carrierName, String name)
    throws Exception
  {
    if (birth.length() == 7)
      birth = QDateTime.dateToString(QDateTime.stringToDate(birth, "ddmmmyy"), "yyyy-mm-dd");
    BookInfant infant = new BookInfant(birth, carrierName, name);
    this.infants.addElement(infant);
  }

  public void addInfoAirSeg(String orgCity, String desCity)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(orgCity, desCity);
    this.airsegs.addElement(airseg);
  }

  public void addInfoAirSeg(String orgCity, String desCity, Date departureTime)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(orgCity, desCity, departureTime);
    this.airsegs.addElement(airseg);
  }

  public void addInfoAirSeg(String orgCity, String desCity, String departureTime)
    throws Exception
  {
    if ((departureTime != null) && (departureTime.length() > 0))
      if (departureTime.length() == 7)
        departureTime = QDateTime.dateToString(QDateTime.stringToDate(departureTime, "ddmmmyy"), "yyyy-mm-dd");
      else if (departureTime.length() == 5)
        departureTime = QDateTime.dateToString(QDateTime.stringToDate(departureTime, "ddmmm"), "yyyy-mm-dd");
    BookAirSeg airseg = new BookAirSeg(orgCity, desCity, departureTime);
    this.airsegs.addElement(airseg);
  }

  public void addOI(BookOI oi)
  {
    this.ois.add(oi);
  }

  public void addOpenAirSeg(String airNo, char fltClass, String orgCity, String desCity)
    throws Exception
  {
    BookAirSeg airseg = new BookAirSeg(airNo, fltClass, orgCity, desCity);
    this.airsegs.addElement(airseg);
  }

  public void addOSI(BookOSI osi)
    throws Exception
  {
    this.osis.addElement(osi);
  }

  public void addOSI(String airCode, String osiinfo)
    throws Exception
  {
    BookOSI osi = new BookOSI(airCode, osiinfo);
    this.osis.addElement(osi);
  }

  public void addPassenger(BookPassenger passenger)
    throws Exception
  {
    this.passengers.addElement(passenger);
  }

  public void addRMK(BookRMK rmk)
    throws Exception
  {
    this.rmks.addElement(rmk);
  }

  public void addRMK(String rmktype, String rmkinfo)
    throws Exception
  {
    BookRMK rmk = new BookRMK(rmktype, rmkinfo);
    this.rmks.addElement(rmk);
  }

  public void addSSR(BookSSR ssr)
    throws Exception
  {
    this.ssrs.addElement(ssr);
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

  public void addSSR_INFT_occupying_seat(String airline, String segment, String fltNo, String fltClass, String date, String infantName, String infantBirth, String freeText)
    throws Exception
  {
    if ((airline == null) || (airline.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter airline is null or \"\"");
    if ((segment == null) || (segment.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter segment is null or \"\"");
    if ((fltNo == null) || (segment.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter fltNo is null or \"\"");

    if ((fltClass == null) || (fltClass.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter fltClass is null or \"\"");
    if ((infantName == null) || (infantName.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter infantName is null or \"\"");
    try
    {
      QDateTime.stringToCalendar(infantBirth, (infantBirth.length() == 5) ? "ddmmm" : "ddmmmyy");
    } catch (Exception e) {
      throw new IBEException("addSSR_INFT(),parameter infantName is " + infantBirth);
    }

    addSSR(new BookSSR("INFt", airline, "nn", 1, "", "", ' ', "", segment + " " + fltNo + " " + fltClass + " " + date + " " + infantBirth + " Occupying seat " + new StringBuffer("/").append(freeText).toString(), infantName));
  }

  public void addSSR_INFT(String airline, String segment, String fltNo, String fltClass, String date, String infantName, String infantBirth, String freeText, String carrierName)
    throws Exception
  {
    if ((airline == null) || (airline.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter airline is null or \"\"");
    if ((segment == null) || (segment.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter segment is null or \"\"");
    if ((fltNo == null) || (segment.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter fltNo is null or \"\"");

    if ((fltClass == null) || (fltClass.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter fltClass is null or \"\"");
    if ((infantName == null) || (infantName.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter infantName is null or \"\"");
    try
    {
      QDateTime.stringToCalendar(infantBirth, (infantBirth.length() == 5) ? "ddmmm" : "ddmmmyy");
    } catch (Exception e) {
      throw new IBEException("addSSR_INFT(),parameter infantBirth is " + infantBirth);
    }
    try {
      QDateTime.stringToCalendar(date, (date.length() == 5) ? "ddmmm" : "ddmmmyy");
    } catch (Exception e) {
      throw new IBEException("addSSR_INFT(),parameter date is " + infantBirth);
    }
    if ((carrierName == null) || (carrierName.length() == 0))
      throw new IBEException("addSSR_INFT(),parameter carrierName is null or \"\"");

    addSSR(new BookSSR("INFt", airline, "nn", 1, "", "", ' ', "", segment + " " + fltNo + " " + fltClass + " " + date + " " + infantName + " " + infantBirth + new StringBuffer("/").append(freeText).toString(), carrierName));
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
    addSSR(new BookSSR("FQTV", airline, "hk", 0, "", "", ' ', "", cardno, name)); }

  public void addSSR_FQTV(String airline, String cardno, String name, String segidx) throws Exception {
    BookSSR ssr = new BookSSR("FQTV", airline, "hk", 0, "", "", ' ', "", cardno, name);
    ssr.setSegidx(segidx);
    addSSR(ssr);
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

  public void addSSR_TKNE(String airline, String fltno, String citypair, char cabin, Date date, String tkno, int counponNo, String name)
    throws Exception
  {
    addSSR(new BookSSR("TKNE", airline, "HK", 1, citypair, fltno, cabin, date, tkno + "/" + counponNo, name));
  }

  public void addTC(BookTC tc)
  {
    if (tc != null)
      this.tcs.addElement(tc);
  }

  public void addTktstatus(BookTktStatus bt) {
    if (bt != null)
      this.tktStatus.addElement(bt);
  }

  public void addUnCompanionChild(String name, int age)
    throws Exception
  {
    BookPassenger passenger = new BookPassenger(name, 2, age);
    this.passengers.addElement(passenger);
  }

  public Vector getAirsegs()
  {
    return this.airsegs;
  }

  public Vector getBa()
  {
    return this.bas;
  }

  public Vector getContacts()
  {
    return this.contacts;
  }

  public Vector getEi()
  {
    return this.eis;
  }

  public String getEnvelopType()
  {
    return this.envelopType;
  }

  public Vector getFc()
  {
    return this.fcs;
  }

  public Vector getFn()
  {
    return this.fns;
  }

  public Vector getFp()
  {
    return this.fps;
  }

  public String getGroupName() {
    return this.groupName;
  }

  public Vector getInfants()
  {
    return this.infants; }

  public String getOfficeCode() {
    return this.officeCode;
  }

  public Vector getOis() {
    return this.ois;
  }

  public Vector getOsis()
  {
    return this.osis;
  }

  public Vector getPassengers()
  {
    return this.passengers;
  }

  public String getPnrno()
  {
    return this.pnrno;
  }

  public Vector getRmks()
  {
    return this.rmks;
  }

  public Vector getSsrs()
  {
    return this.ssrs;
  }

  public Vector getTc()
  {
    return this.tcs;
  }

  public Vector getTktStatus()
  {
    return this.tktStatus;
  }

  public String getTkttimelimit()
  {
    return this.tkttimelimit;
  }

  public boolean isGroupticket() {
    return this.groupticket;
  }

  public void setEnvelopType(String newEnvelopType)
  {
    this.envelopType = newEnvelopType;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public void setGroupticket(boolean groupticket) {
    this.groupticket = groupticket; }

  public void setOfficeCode(String officeCode) {
    this.officeCode = officeCode;
  }

  public void setOis(Vector ois) {
    this.ois = ois;
  }

  public void setTimelimit(Date dateLimit)
    throws Exception
  {
    this.tkttimelimit = QDateTime.dateToString(dateLimit, "YYYY-MM-DD HH:MI:SS");
  }

  public void setTimelimit(String dateLimit)
    throws Exception
  {
    this.tkttimelimit = dateLimit; }

  public int getPassengernumber() {
    return this.passengernumber; }

  public void setPassengernumber(int passengernumber) {
    this.passengernumber = passengernumber; }

  public void setAirsegs(Vector airsegs) {
    this.airsegs = airsegs; }

  static String[] encodeBookinfo(BookInfomation bookinfo, String pnrno) throws Exception {
    String[] args;
    try { int j;
      int j;
      args = new String[21];

      args[1] = "Office:" + bookinfo.getOfficeCode();
      args[2] = "AirSeg:";
      args[3] = "Contact:";
      args[4] = "Infant:";
      args[5] = "OSI:";
      args[6] = "RMK:";
      args[7] = "SSR:";
      args[8] = "Tkt:";
      args[9] = "GN:";
      args[10] = "NM:";
      args[11] = pnrno;
      args[12] = bookinfo.getEnvelopType();
      args[13] = "FC:";
      args[14] = "FP:";
      args[15] = "FN:";
      args[16] = "BA:";
      args[17] = "EI:";
      args[18] = "TC:";
      args[19] = "TK:";
      args[20] = "OI:";

      if (bookinfo.getPassengers().size() != 0) {
        String[] psgrStrings = new String[bookinfo.getPassengers().size()];
        for (int i = 0; i < bookinfo.getPassengers().size(); ++i) {
          int j = 0;
          BookPassenger p1 = 
            (BookPassenger)bookinfo.getPassengers().elementAt(i);
          String[] psgrString = new String[4];

          psgrString[j] = p1.getName();
          ++j;

          psgrString[j] = String.valueOf(p1.getType());
          ++j;

          psgrString[j] = String.valueOf(p1.getAge());
          ++j;
          psgrString[(j++)] = String.valueOf(p1.getPassengerType());

          psgrStrings[i] = CommandParser.encode(psgrString);
        }

        args[10] = args[10] + CommandParser.encode(psgrStrings);
      }

      if (bookinfo.getAirsegs().size() != 0) {
        String[] airsegStrings = new String[bookinfo.getAirsegs().size()];
        for (int i = 0; i < bookinfo.getAirsegs().size(); ++i) {
          BookAirSeg airseg = 
            (BookAirSeg)bookinfo.getAirsegs().elementAt(i);

          String[] airsegnormal = new String[8];
          j = 0;
          airsegnormal[j] = airseg.getAirNo();
          ++j;

          airsegnormal[j] = String.valueOf(airseg.getFltClass());
          ++j;
          airsegnormal[j] = airseg.getOrgCity();
          ++j;
          airsegnormal[j] = airseg.getDesCity();
          ++j;

          airsegnormal[j] = airseg.getActionCode();
          if ((airsegnormal[j] == null) || 
            (airsegnormal[j].equals("")))
            airsegnormal[j] = "nn";
          ++j;
          airsegnormal[j] = "1";
          ++j;

          airsegnormal[j] = 
            QDateTime.dateToString(
            airseg.getDepartureTime(), 
            "yyyy-mm-dd hh:mi:ss");
          ++j;

          airsegnormal[j] = String.valueOf(airseg.getType());

          airsegStrings[i] = CommandParser.encode(airsegnormal);
        }

        args[2] = args[2] + CommandParser.encode(airsegStrings);
      }

      if (bookinfo.getContacts().size() != 0) {
        String[] contactStrings = new String[bookinfo.getContacts().size()];
        for (int i = 0; i < bookinfo.getContacts().size(); ++i) {
          String[] contactString = new String[3];
          j = 0;
          BookContact contact = 
            (BookContact)bookinfo.getContacts().elementAt(i);
          contactString[j] = contact.getCity();
          ++j;
          contactString[j] = contact.getContact();
          ++j;
          contactString[j] = contact.getpsgrName();
          ++j;
          contactStrings[i] = CommandParser.encode(contactString);
        }

        args[3] = args[3] + CommandParser.encode(contactStrings);
      }

      if (bookinfo.getInfants().size() != 0) {
        String[] infantStrings = new String[bookinfo.getInfants().size()];
        for (int i = 0; i < bookinfo.getInfants().size(); ++i) {
          String[] infantString = new String[3];
          int j = 0;
          BookInfant infant = 
            (BookInfant)bookinfo.getInfants().elementAt(i);

          infantString[j] = infant.getName();
          ++j;
          infantString[j] = 
            QDateTime.dateToString(
            infant.getBirth(), 
            "yyyy-mm-dd hh:mi:ss");
          ++j;
          infantString[j] = infant.getCarrierName();
          ++j;
          infantStrings[i] = CommandParser.encode(infantString);
        }

        args[4] = args[4] + CommandParser.encode(infantStrings);
      }

      if (bookinfo.getOsis().size() != 0) {
        String[] osiStrings = new String[bookinfo.getOsis().size()];
        for (int i = 0; i < bookinfo.getOsis().size(); ++i) {
          String[] osiString = new String[3];
          BookOSI osi = (BookOSI)bookinfo.getOsis().elementAt(i);
          int j = 0;
          osiString[j] = osi.getAirCode();
          ++j;
          osiString[j] = osi.getOsi();
          ++j;
          osiString[j] = osi.getpsgrName();
          ++j;
          osiStrings[i] = CommandParser.encode(osiString);
        }
        args[5] = args[5] + CommandParser.encode(osiStrings);
      }

      if (bookinfo.getRmks().size() != 0) {
        String[] rmkStrings = new String[bookinfo.getRmks().size()];
        for (int i = 0; i < bookinfo.getRmks().size(); ++i) {
          String[] rmkString = new String[3];
          BookRMK rmk = (BookRMK)bookinfo.getRmks().elementAt(i);
          j = 0;
          rmkString[j] = rmk.getRmktype();
          ++j;
          rmkString[j] = rmk.getRmkinfo();
          ++j;
          rmkString[j] = rmk.getpsgrName();
          ++j;
          rmkStrings[i] = CommandParser.encode(rmkString);
        }
        args[6] = args[6] + CommandParser.encode(rmkStrings);
      }

      if (bookinfo.getSsrs().size() != 0) {
        String[] ssrStrings = new String[bookinfo.getSsrs().size()];
        for (int i = 0; i < bookinfo.getSsrs().size(); ++i) {
          String[] ssrString = new String[11];
          j = 0;
          BookSSR ssr = (BookSSR)bookinfo.getSsrs().elementAt(i);
          ssrString[j] = ssr.getServeCode();
          ++j;
          ssrString[j] = ssr.getAirCode();
          ++j;
          ssrString[j] = ssr.getActionCode();
          ++j;

          ssrString[j] = String.valueOf(ssr.getPerson());
          ++j;
          ssrString[j] = ssr.getCityPair();
          ++j;
          ssrString[j] = ssr.getAirNo();
          ++j;

          ssrString[j] = String.valueOf(ssr.getFltClass());
          ++j;
          ssrString[j] = 
            QDateTime.dateToString(
            ssr.getDepartureTime(), 
            "yyyy-mm-dd hh:mi:ss");
          ++j;
          ssrString[j] = ssr.getServeInfo();
          ++j;
          ssrString[j] = ssr.getpsgrName();
          ++j;
          ssrString[j] = ssr.getSegidx();
          ++j;
          ssrStrings[i] = CommandParser.encode(ssrString);
        }

        args[7] = args[7] + CommandParser.encode(ssrStrings);
      }

      if ((bookinfo.getTkttimelimit() != null) && 
        (!(bookinfo.getTkttimelimit().equals("")))) {
        String[] tktarr = new String[1];
        tktarr[0] = bookinfo.getTkttimelimit();
        args[8] = "Tkt:" + CommandParser.encode(tktarr);
      }

      if (bookinfo.getFc().size() > 0) {
        String[] fcs = new String[bookinfo.getFc().size()];
        for (int i = 0; i < bookinfo.getFc().size(); ++i) {
          String[] fcStrings = new String[4];
          BookFC bookfc = (BookFC)bookinfo.getFc().elementAt(i);
          fcStrings[0] = bookfc.getFc();
          fcStrings[1] = String.valueOf(bookfc.isInfant());
          String[] names = new String[bookfc.getPsgrname().size()];
          for (int j = 0; j < names.length; ++j)
            names[j] = ((String)bookfc.getPsgrname().elementAt(j));
          fcStrings[2] = CommandParser.encode(names);
          if ((bookfc.getFc() == null) || (bookfc.getFc().length() == 0))
            fcStrings[3] = BookFC.encodeFC(bookfc);
          fcs[i] = CommandParser.encode(fcStrings);
        }
        args[13] = args[13] + CommandParser.encode(fcs);
      }

      if (bookinfo.getFp().size() > 0) {
        String[] fps = new String[bookinfo.getFp().size()];
        for (int i = 0; i < bookinfo.getFp().size(); ++i) {
          String[] fpStrings = new String[3];
          BookFP bookfp = (BookFP)bookinfo.getFp().elementAt(i);
          fpStrings[0] = bookfp.getFp();
          fpStrings[1] = String.valueOf(bookfp.isInfant());
          String[] names = new String[bookfp.getPsgrname().size()];
          for (int j = 0; j < names.length; ++j)
            names[j] = ((String)bookfp.getPsgrname().elementAt(j));
          fpStrings[2] = CommandParser.encode(names);
          fps[i] = CommandParser.encode(fpStrings);
        }
        args[14] = args[14] + CommandParser.encode(fps);
      }

      if (bookinfo.getFn().size() > 0) {
        String[] fns = new String[bookinfo.getFn().size()];
        for (int i = 0; i < bookinfo.getFn().size(); ++i) {
          String[] fnStrings = new String[4];
          FareBox bookfn = (FareBox)bookinfo.getFn().elementAt(i);

          fnStrings[0] = bookfn.getFn();
          fnStrings[1] = String.valueOf(bookfn.isInfant());
          if (bookfn instanceof BookFN) {
            BookFN bookfn1 = (BookFN)bookfn;
            String[] names = new String[bookfn1.getPsgrname().size()];
            for (int j = 0; j < names.length; ++j)
              names[j] = ((String)bookfn1.getPsgrname().elementAt(j));
            fnStrings[2] = CommandParser.encode(names);
          }
          if ((fnStrings[0] == null) || (fnStrings[0].length() == 0))
            fnStrings[3] = FareBox.encodeFN(bookfn);
          fns[i] = CommandParser.encode(fnStrings);
        }
        args[15] = args[15] + CommandParser.encode(fns);
      }

      if (bookinfo.getBa().size() > 0) {
        String[] bas = new String[bookinfo.getBa().size()];
        for (int i = 0; i < bookinfo.getBa().size(); ++i) {
          String[] baStrings = new String[2];
          BookBA ba = (BookBA)bookinfo.getBa().elementAt(i);
          baStrings[0] = ba.getBAInfo();
          baStrings[1] = ba.getPsgrname();
          bas[i] = CommandParser.encode(baStrings);
        }
        args[16] = args[16] + CommandParser.encode(bas);
      }

      if (bookinfo.getTc().size() > 0) {
        String[] tcs = new String[bookinfo.getTc().size()];
        for (int i = 0; i < bookinfo.getTc().size(); ++i) {
          String[] tcStrings = new String[4];
          BookTC tc = (BookTC)bookinfo.getTc().elementAt(i);
          tcStrings[0] = tc.getFreecode();
          tcStrings[1] = tc.getTc();
          tcStrings[2] = tc.getPsgrName();
          tcStrings[3] = String.valueOf(tc.infant);
          tcs[i] = CommandParser.encode(tcStrings);
        }
        args[18] = args[18] + CommandParser.encode(tcs);
      }

      if (bookinfo.getEi().size() > 0) {
        String[] eis = new String[bookinfo.getEi().size()];
        for (int i = 0; i < bookinfo.getEi().size(); ++i) {
          String[] eiStrings = new String[2];
          BookEI ei = (BookEI)bookinfo.getEi().elementAt(i);
          eiStrings[0] = ei.getEndorse();
          eiStrings[1] = ei.getPsgrName();
          eis[i] = CommandParser.encode(eiStrings);
        }
        args[17] = args[17] + CommandParser.encode(eis);
      }

      if (bookinfo.getTktStatus().size() > 0) {
        String[] tk = new String[bookinfo.getTktStatus().size()];
        for (int i = 0; i < bookinfo.getTktStatus().size(); ++i) {
          String[] tkStr = new String[4];
          BookTktStatus bt = 
            (BookTktStatus)bookinfo.getTktStatus().elementAt(i);
          tkStr[0] = String.valueOf(bt.type);
          if (bt.getType() == 32)
            tkStr[1] = bt.getFreetext();
          else if (bt.getType() == 16)
            tkStr[1] = bt.getTlOffice();
          tkStr[2] = ((bt.timelimit == null) ? "01Jan19700000" : QDateTime.dateToString(bt.timelimit, "ddmmmyyyyhhmi"));
          tkStr[3] = bt.psgrName;
          tk[i] = CommandParser.encode(tkStr);
        }
        args[19] = args[19] + CommandParser.encode(tk);
      }

      if (bookinfo.getOis().size() > 0) {
        String[] oi = new String[bookinfo.getOis().size()];
        for (int i = 0; i < bookinfo.getOis().size(); ++i) {
          String[] oistr = new String[10];
          BookOI boi = (BookOI)bookinfo.getOis().elementAt(i);
          oistr[0] = boi.getTktno();
          oistr[1] = boi.getCoupon();
          oistr[2] = boi.getIssueCity();
          oistr[3] = boi.getIssueOffice();
          if (boi.getIssueDate() != null)
            oistr[4] = QDateTime.dateToString(boi.getIssueDate(), "DDMMMYYYY");
          else
            oistr[4] = "01JAN1970";
          oistr[5] = boi.getPsgrid();
          oistr[6] = boi.getSecondtktno();
          oistr[7] = boi.getSecondcoupon();
          oistr[8] = String.valueOf(boi.getOitype());
          oistr[9] = String.valueOf(boi.isInfant());
          oi[i] = CommandParser.encode(oistr);
        }
        args[20] = args[20] + CommandParser.encode(oi);
      }

      if (bookinfo.isGroupticket()) {
        String[] groupinfo = new String[2];

        groupinfo[0] = String.valueOf(bookinfo.getPassengernumber());
        groupinfo[1] = bookinfo.getGroupName().toUpperCase();
        args[9] = args[9] + CommandParser.encode(groupinfo);
      }

      return args;
    } catch (Exception e) {
      e.printStackTrace();
      throw new IBELocalException("Encode parameters error.");
    }
  }
}
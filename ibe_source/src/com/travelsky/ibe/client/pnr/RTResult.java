package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RTResult extends IBEResult
{
  private static final long serialVersionUID = -6750720019960989368L;
  boolean group;
  String groupname;
  int passengerNumber;
  int groupNumber;
  String pnrcode;
  Vector airSegs;
  Vector contacts;
  Vector infants;
  Vector osis;
  Vector passengers;
  PNRResp resp;
  Vector rmks;
  Vector ssrs;
  Vector tkts;
  Vector tktnos;
  Vector others;
  Vector bas;
  List fcs;
  String OringinalRT;
  List fns;

  public RTResult()
  {
    this.group = false;
    this.groupname = null;
    this.passengerNumber = 0;
    this.groupNumber = 0;
    this.pnrcode = "";
    this.airSegs = new Vector();
    this.contacts = new Vector();
    this.infants = new Vector();
    this.osis = new Vector();
    this.passengers = new Vector();
    this.resp = new PNRResp("", "", "", "");
    this.rmks = new Vector();
    this.ssrs = new Vector();
    this.tkts = new Vector();
    this.tktnos = new Vector();
    this.others = new Vector();
    this.fcs = new ArrayList();
    this.fns = new ArrayList();
    this.bas = new Vector();
  }

  public Vector getAirSegs()
  {
    return this.airSegs;
  }

  public int getAirSegsCount()
  {
    return this.airSegs.size();
  }

  public PNRAirSeg getAirSegAt(int n)
  {
    if (n <= this.airSegs.size() - 1)
      return ((PNRAirSeg)this.airSegs.elementAt(n));

    return null;
  }

  public Vector getContacts()
  {
    return this.contacts;
  }

  public String getGroupname()
  {
    return this.groupname;
  }

  public int getGroupNumber()
  {
    return this.groupNumber;
  }

  public Vector getInfants()
  {
    return this.infants;
  }

  public Vector getOsis()
  {
    return this.osis;
  }

  public Vector getOthers()
  {
    return this.others;
  }

  public int getPassengerNumber()
  {
    return this.passengerNumber;
  }

  public Vector getPassengers()
  {
    return this.passengers;
  }

  public String getPnrcode()
  {
    return this.pnrcode;
  }

  public PNRResp getResp()
  {
    return this.resp;
  }

  public Vector getRmks()
  {
    return this.rmks;
  }

  public Vector getSsrs()
  {
    return this.ssrs;
  }

  public Vector getTktnos()
  {
    return this.tktnos;
  }

  public Vector getTkts()
  {
    return this.tkts;
  }

  public boolean isGroup()
  {
    return this.group;
  }

  public String toString()
  {
    StringBuffer strtmp;
    try
    {
      strtmp = new StringBuffer();
      if (this == null) {
        strtmp.append("空pnr\n");
        return strtmp.toString();
      }
      strtmp.append("PNRNO:" + getPnrcode() + "\n");
      strtmp.append("\n");
      strtmp.append("团体票标识:");
      if (isGroup()) {
        strtmp.append("团体票   ");
        strtmp.append("团名:");
        strtmp.append((getGroupname() == null) ? "" : getGroupname());
        strtmp.append("   团体人数:");
        strtmp.append(getGroupNumber());
      } else {
        strtmp.append("散客票\n");
      }

      strtmp.append("\n姓名组\n");
      Vector tmp = getPassengers();
      for (int i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNRPassenger p = (PNRPassenger)tmp.elementAt(i);
        strtmp.append("." + p.getIndex() + "." + p.getName());
        if (p.getType() == 0)
          strtmp.append("  成人  " + p.getNameInPnr() + "\n");
        else if (p.getType() == 1)
          strtmp.append("  儿童  " + p.getNameInPnr() + "\n");
        else if (p.getType() == 2)
          strtmp.append("  无人陪伴旅客  " + p.getNameInPnr() + "  年龄: " + p.getAge() + "\n");
      }
      strtmp.append("\n航段组\n");
      tmp = getAirSegs();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNRAirSeg a = (PNRAirSeg)tmp.elementAt(i);
        strtmp.append("." + a.getIndex() + ". ");
        strtmp.append(a.getAirNo() + " " + a.getFltClass() + " " + a.getOrgCity() + a.getDesCity() + " " + a.getActionCode() + a.getTktNum() + " " + a.getDepartureTimeString() + " " + a.getArrivalTimeString() + ((a.isEtktairseg()) ? " 电子票" : "       ") + ((a.isSkChanged()) ? " 变更" : "     ") + ((a.isCodeShare()) ? "  代码共享航班由" + a.operateCarrier + "执行" : "                          "));
        if (a.getType() == 2)
          strtmp.append("  普通航段\n");
        else if (a.getType() == 1)
          strtmp.append("  信息航段\n");
        else if (a.getType() == 3)
          strtmp.append("  开放航段\n");

        strtmp.append(a.toString() + "\n");
      }
      strtmp.append("\n联系组\n");
      tmp = getContacts();
      for (i = 0; i < tmp.size(); ++i) {
        PNRContact c = (PNRContact)tmp.elementAt(i);
        strtmp.append(i + 1);
        strtmp.append("." + c.getIndex() + '.');
        strtmp.append(c.getCity() + " " + c.getContact() + "\n");
      }
      strtmp.append("\n开账地址组（BA）\n");
      tmp = getBas();
      for (i = 0; i < tmp.size(); ++i) {
        PNRBA b = (PNRBA)tmp.elementAt(i);
        strtmp.append(String.valueOf(i + 1) + ".");
        strtmp.append(b.toString() + "\n");
      }
      strtmp.append("\n出票组\n");
      tmp = getTkts();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNRTkt t = (PNRTkt)tmp.elementAt(i);
        strtmp.append("." + t.toString());
      }
      strtmp.append("\n票号组\n");
      tmp = getTktnos();
      for (i = 0; i < tmp.size(); ++i) {
        PNRTktNo t = (PNRTktNo)tmp.elementAt(i);
        strtmp.append(i + 1);
        strtmp.append("." + t.getIndex() + '.');
        strtmp.append(t.getTktNo() + " " + t.getPsgrID() + " " + t.getRemark() + "\n");
      }
      strtmp.append("\n备注组\n");
      tmp = getRmks();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNRRMK r = (PNRRMK)tmp.elementAt(i);
        strtmp.append("." + r.getIndex() + "." + r.getRmkinfo() + " " + r.getPsgrID() + "\n");
      }
      strtmp.append("\n特别服务组\n");
      tmp = getSsrs();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNRSSR s = (PNRSSR)tmp.elementAt(i);
        strtmp.append("." + s.getIndex() + "." + s.toString() + " " + s.getPsgrID() + "\n");
      }

      strtmp.append("\n其他服务组\n");
      tmp = getOsis();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNROSI o = (PNROSI)tmp.elementAt(i);
        strtmp.append("." + o.getIndex() + "." + o.getOsi() + " " + o.getPNum() + "\n");
      }
      strtmp.append("\n婴儿组\n");
      tmp = getInfants();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNRInfant f = (PNRInfant)tmp.elementAt(i);
        strtmp.append("." + f.getIndex() + ".姓名：" + f.getName() + "  出生日期：" + f.getBrithString() + "  携带：" + f.getCarrier() + "  PNR显示名:" + f.getNameInPnr() + "\n");
      }
      strtmp.append("\n其他\n");
      tmp = getOthers();
      for (i = 0; i < tmp.size(); ++i) {
        strtmp.append(i + 1);
        PNROther o = (PNROther)tmp.elementAt(i);
        strtmp.append("." + o.toString() + "\n");
      }
      strtmp.append("\n责任组\n");
      PNRResp r = getResp();
      if (r.getOfficecode().equals(""))
        strtmp.append("1." + r.getIndex() + "." + r.getCrssign() + " " + r.getPnrno() + " " + r.getRemark() + "\n");
      else
        strtmp.append("1." + r.getIndex() + "." + r.getCrssign() + " " + r.getPnrno() + " " + r.getOfficecode() + "\n");

      return strtmp.toString(); } catch (Exception e) {
    }
    return toString();
  }

  public PNRContact getContactAt(int n)
  {
    if (n <= this.contacts.size() - 1)
      return ((PNRContact)this.contacts.elementAt(n));

    return null;
  }

  public int getContactsCount()
  {
    return this.contacts.size();
  }

  public PNRInfant getInfantAt(int n)
  {
    if (n <= this.infants.size() - 1)
      return ((PNRInfant)this.infants.elementAt(n));

    return null;
  }

  public int getInfantsCount()
  {
    return this.infants.size();
  }

  public PNROSI getOSIAt(int n)
  {
    if (n <= this.osis.size() - 1)
      return ((PNROSI)this.osis.elementAt(n));

    return null;
  }

  public int getOSIsCount()
  {
    return this.osis.size();
  }

  public PNROther getOtherAt(int n)
  {
    if (n <= this.others.size() - 1)
      return ((PNROther)this.others.elementAt(n));

    return null;
  }

  public int getOthersCount()
  {
    return this.others.size();
  }

  public PNRPassenger getPassengerAt(int n)
  {
    if (n <= this.passengers.size() - 1)
      return ((PNRPassenger)this.passengers.elementAt(n));

    return null;
  }

  public int getPassengersCount()
  {
    return this.passengers.size();
  }

  public PNRRMK getRMKAt(int n)
  {
    if (n <= this.rmks.size() - 1)
      return ((PNRRMK)this.rmks.elementAt(n));

    return null;
  }

  public int getRMKsCount()
  {
    return this.rmks.size();
  }

  public PNRSSR getSSRAt(int n)
  {
    if (n <= this.ssrs.size() - 1)
      return ((PNRSSR)this.ssrs.elementAt(n));

    return null;
  }

  public int getSSRsCount()
  {
    return this.ssrs.size();
  }

  public PNRTkt getTktAt(int n)
  {
    if (n <= this.tkts.size() - 1)
      return ((PNRTkt)this.tkts.elementAt(n));

    return null;
  }

  public PNRTktNo getTktnoAt(int n)
  {
    if (n <= this.tktnos.size() - 1)
      return ((PNRTktNo)this.tktnos.elementAt(n));

    return null;
  }

  public int getTktnosCount()
  {
    return this.tktnos.size();
  }

  public int getTktsCount()
  {
    return this.tkts.size();
  }

  public Vector getBas()
  {
    return this.bas;
  }

  public int getBasCount()
  {
    return this.bas.size();
  }

  public PNRBA getBaAt(int i)
  {
    if ((i < this.bas.size()) && (i >= 0))
      return ((PNRBA)this.bas.elementAt(i));

    return null;
  }

  public String getOringinalRT()
  {
    return this.OringinalRT;
  }

  public void setOringinalRT(String string)
  {
    this.OringinalRT = string;
  }

  public PNRFC getFCAt(int i)
  {
    if ((this.fcs.size() <= i) || (i < 0))
      return null;
    return ((PNRFC)this.fcs.get(i)); }

  public int getFCsCount() {
    return this.fcs.size();
  }

  public PNRFN getFNAt(int i)
  {
    if ((this.fns.size() <= i) || (i < 0))
      return null;
    return ((PNRFN)this.fns.get(i)); }

  public int getFNsCount() {
    return this.fns.size();
  }
}
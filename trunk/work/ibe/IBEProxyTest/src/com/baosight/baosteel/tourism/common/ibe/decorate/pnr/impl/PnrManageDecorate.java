package com.baosight.baosteel.tourism.common.ibe.decorate.pnr.impl;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.baosight.baosteel.tourism.common.ibe.decorate.pnr.IPnrManageDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.pnr.IPnrManageProxyService;
import com.travelsky.ibe.client.pnr.BookAirSeg;
import com.travelsky.ibe.client.pnr.BookInfomation;
import com.travelsky.ibe.client.pnr.BookPassenger;
import com.travelsky.ibe.client.pnr.PNRAirSeg;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.ibe.exceptions.PMDuplicateException;
import com.travelsky.ibe.exceptions.PMInvalidRequestPaxException;
import com.travelsky.ibe.exceptions.PMNoPNRException;
import com.travelsky.ibe.exceptions.PMPNRCancelledException;
import com.travelsky.ibe.exceptions.PMTicketMissingException;


public class PnrManageDecorate implements IPnrManageDecorate{

	private IPnrManageProxyService pnrManageProxyService;

	public String addGroupName(String pnrno, Vector passengers)
			throws Exception {
		return pnrManageProxyService.addGroupName(pnrno, passengers);
	}

	public String addPnrInfo(String pnrno, BookInfomation bookinfo)
			throws Exception {
		return pnrManageProxyService.addPnrInfo(pnrno, bookinfo);
	}

	public String cancelAirSeg(String pnrno, PNRAirSeg segment)
			throws IBEException {
		return pnrManageProxyService.cancelAirSeg(pnrno, segment);
	}

	public String changeAirSeg(String pnr, BookAirSeg oldSeg, BookAirSeg newSeg)
			throws IBEException {
		return pnrManageProxyService.changeAirSeg(pnr, oldSeg, newSeg);
	}

	public String changeAirSeg(String pnr, Vector oldSegs, Vector newSegs)
			throws IBEException {
		return pnrManageProxyService.changeAirSeg(pnr, oldSegs, newSegs);
	}

	public String changeId(String pnrno, BookPassenger pax, String foid)
			throws Exception {
		return pnrManageProxyService.changeId(pnrno, pax, foid);
	}

	public String changeIds(String pnrno, Map paxfoidMap)
			throws PMInvalidRequestPaxException, IBEException {
		return pnrManageProxyService.changeIds(pnrno, paxfoidMap);
	}

	public String changePax(String pnrno, BookPassenger oldpax,
			BookPassenger newpax) throws Exception {
		return pnrManageProxyService.changePax(pnrno, oldpax, newpax);
	}

	public String changePaxId(String pnrno, BookPassenger oldpax,
			BookPassenger newpax, String foid) throws Exception {
		return pnrManageProxyService.changePaxId(pnrno, oldpax, newpax, foid);
	}

	public String changePaxIds(String pnrno, List oldPax, List newPax, List foid)
			throws IBEException, PMInvalidRequestPaxException {
		return pnrManageProxyService.changePaxIds(pnrno, oldPax, newPax, foid);
	}

	public String changePaxName(String pnrno, String oldname, String newname)
			throws Exception {
		return pnrManageProxyService.changePaxName(pnrno, oldname, newname);
	}

	public String changeTkt(String pnrno, BookInfomation bookinfo)
			throws PMNoPNRException, PMPNRCancelledException,
			PMTicketMissingException, PMDuplicateException, Exception {
		return pnrManageProxyService.changeTkt(pnrno, bookinfo);
	}

	public String confirmAirSeg(String pnrno, PNRAirSeg segment, String action)
			throws IBEException {
		return pnrManageProxyService.confirmAirSeg(pnrno, segment, action);
	}

	public String confirmAirSeg(String pnrno, PNRAirSeg segment)
			throws IBEException {
		return pnrManageProxyService.confirmAirSeg(pnrno, segment);
	}

	public String deleteItem(String pnrno, int[] indexes) throws Exception {
		return pnrManageProxyService.deleteItem(pnrno, indexes);
	}

	public String deletePnr(String pnrno, String office) throws Exception {
		return pnrManageProxyService.deletePnr(pnrno, office);
	}

	public String reconfirmAirSeg(String pnrno, PNRAirSeg segment, int force)
			throws IBEException {
		return pnrManageProxyService.reconfirmAirSeg(pnrno, segment, force);
	}

	public String reduceGroupSeats(String pnrno, int count) throws Exception {
		return pnrManageProxyService.reduceGroupSeats(pnrno, count);
	}

	public String removeName(String pnrno, Vector passengers) throws Exception {
		return pnrManageProxyService.removeName(pnrno, passengers);
	}

	public String splitPNR(String pnrno, Vector passengers, int count)
			throws Exception {
		return pnrManageProxyService.splitPNR(pnrno, passengers, count);
	}

	public IPnrManageProxyService getPnrManageProxyService() {
		return pnrManageProxyService;
	}

	public void setPnrManageProxyService(
			IPnrManageProxyService pnrManageProxyService) {
		this.pnrManageProxyService = pnrManageProxyService;
	}


}

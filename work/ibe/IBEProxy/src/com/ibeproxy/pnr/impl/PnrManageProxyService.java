package com.ibeproxy.pnr.impl;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.pnr.IPnrManageProxyService;
import com.travelsky.ibe.client.pnr.BookAirSeg;
import com.travelsky.ibe.client.pnr.BookInfomation;
import com.travelsky.ibe.client.pnr.BookPassenger;
import com.travelsky.ibe.client.pnr.PNRAirSeg;
import com.travelsky.ibe.client.pnr.PnrManage;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.ibe.exceptions.PMDuplicateException;
import com.travelsky.ibe.exceptions.PMInvalidRequestPaxException;
import com.travelsky.ibe.exceptions.PMNoPNRException;
import com.travelsky.ibe.exceptions.PMPNRCancelledException;
import com.travelsky.ibe.exceptions.PMTicketMissingException;

public class PnrManageProxyService implements IPnrManageProxyService{

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public String addGroupName(String pnrno, Vector passengers)
			throws Exception {
		logger.info("addGroupName has be called");
		return new PnrManage().addGroupName(pnrno, passengers);
	}

	public String addPnrInfo(String pnrno, BookInfomation bookinfo)
			throws Exception {
		logger.info("addPnrInfo has be called");
		return new PnrManage().addPnrInfo(pnrno, bookinfo);
	}

	public String cancelAirSeg(String pnrno, PNRAirSeg segment)
			throws IBEException {
		logger.info("cancelAirSeg has be called");
		return new PnrManage().cancelAirSeg(pnrno, segment);
	}

	public String changeAirSeg(String pnr, BookAirSeg oldSeg, BookAirSeg newSeg)
			throws IBEException {
		logger.info("changeAirSeg has be called");
		return new PnrManage().changeAirSeg(pnr, oldSeg, newSeg);
	}

	public String changeAirSeg(String pnr, Vector oldSegs, Vector newSegs)
			throws IBEException {
		logger.info("changeAirSeg has be called");
		return new PnrManage().changeAirSeg(pnr, oldSegs, newSegs);
	}

	public String changeId(String pnrno, BookPassenger pax, String foid)
			throws Exception {
		logger.info("changeId has be called");
		return new PnrManage().changeId(pnrno, pax, foid);
	}

	public String changeIds(String pnrno, Map paxfoidMap)
			throws PMInvalidRequestPaxException, IBEException {
		logger.info("changeIds has be called");
		return new PnrManage().changeIds(pnrno, paxfoidMap);
	}

	public String changePax(String pnrno, BookPassenger oldpax,
			BookPassenger newpax) throws Exception {
		logger.info("changePax has be called");
		return new PnrManage().changePax(pnrno, oldpax, newpax);
	}

	public String changePaxId(String pnrno, BookPassenger oldpax,
			BookPassenger newpax, String foid) throws Exception {
		logger.info("changePaxId has be called");
		return new PnrManage().changePaxId(pnrno, oldpax, newpax, foid);
	}

	public String changePaxIds(String pnrno, List oldPax, List newPax, List foid)
			throws IBEException, PMInvalidRequestPaxException {
		logger.info("changePaxIds has be called");
		return new PnrManage().changePaxIds(pnrno, oldPax, newPax, foid);
	}

	public String changePaxName(String pnrno, String oldname, String newname)
			throws Exception {
		logger.info("changePaxName has be called");
		return new PnrManage().changePaxName(pnrno, oldname, newname);
	}

	public String changeTkt(String pnrno, BookInfomation bookinfo)
			throws PMNoPNRException, PMPNRCancelledException,
			PMTicketMissingException, PMDuplicateException, Exception {
		logger.info("flightTime has be called");
		return new PnrManage().changeTkt(pnrno, bookinfo);
	}

	public String confirmAirSeg(String pnrno, PNRAirSeg segment, String action)
			throws IBEException {
		logger.info("confirmAirSeg has be called");
		return new PnrManage().confirmAirSeg(pnrno, segment, action);
	}

	public String confirmAirSeg(String pnrno, PNRAirSeg segment)
			throws IBEException {
		logger.info("confirmAirSeg has be called");
		return new PnrManage().confirmAirSeg(pnrno, segment);
	}

	public String deleteItem(String pnrno, int[] indexes) throws Exception {
		logger.info("deleteItem has be called");
		return new PnrManage().deleteItem(pnrno, indexes);
	}

	public String deletePnr(String pnrno, String office) throws Exception {
		logger.info("deletePnr has be called");
		return new PnrManage().deletePnr(pnrno);
	}

	public String reconfirmAirSeg(String pnrno, PNRAirSeg segment, int force)
			throws IBEException {
		logger.info("reconfirmAirSeg has be called");
		return new PnrManage().reconfirmAirSeg(pnrno, segment, force);
	}

	public String reduceGroupSeats(String pnrno, int count) throws Exception {
		logger.info("reduceGroupSeats has be called");
		return new PnrManage().reduceGroupSeats(pnrno, count);
	}

	public String removeName(String pnrno, Vector passengers) throws Exception {
		logger.info("removeName has be called");
		return new PnrManage().removeName(pnrno, passengers);
	}

	public String splitPNR(String pnrno, Vector passengers, int count)
			throws Exception {
		logger.info("splitPNR has be called");
		return new PnrManage().splitPNR(pnrno, passengers, count);
	}

}

package com.baosight.baosteel.tourism.common.ibe.proxy.pnr;

import java.util.List;
import java.util.Map;
import java.util.Vector;

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

public interface IPnrManageProxyService {

	public String addGroupName(String pnrno, Vector passengers)
	throws Exception;

	public String addPnrInfo(String pnrno, BookInfomation bookinfo)
	throws Exception;

	public String cancelAirSeg(String pnrno, PNRAirSeg segment)
	throws IBEException;


	public String changeAirSeg(String pnr, BookAirSeg oldSeg, BookAirSeg newSeg)
	throws IBEException;

	public String changeAirSeg(String pnr, Vector oldSegs, Vector newSegs)
	throws IBEException;

	public String changeId(String pnrno, BookPassenger pax, String foid)
	throws Exception;

	public String changeIds(String pnrno, Map paxfoidMap)
	throws PMInvalidRequestPaxException, IBEException;

	public String changePax(String pnrno, BookPassenger oldpax, BookPassenger newpax)
	throws Exception;


	public String changePaxId(String pnrno, BookPassenger oldpax, BookPassenger newpax, String foid)
	throws Exception;

	public String changePaxIds(String pnrno, List oldPax, List newPax, List foid)
	throws IBEException, PMInvalidRequestPaxException;

	public String changePaxName(String pnrno, String oldname, String newname)
	throws Exception;


	public String changeTkt(String pnrno, BookInfomation bookinfo)
	throws PMNoPNRException, PMPNRCancelledException, PMTicketMissingException, PMDuplicateException, Exception;


	public String confirmAirSeg(String pnrno, PNRAirSeg segment, String action)
	throws IBEException;


	public String confirmAirSeg(String pnrno, PNRAirSeg segment)
	throws IBEException;


	public String deleteItem(String pnrno, int[] indexes)
	throws Exception;

	public String deletePnr(String pnrno, String office) throws Exception ;

//	public PATResult doPat(String pnrno, boolean ifinput);
//
//	public PATResult doPat(String pnrno, String option, String type, int index, boolean ifinput)
//	throws IBEException;
//
//	public PATResult doPatA(String pnrno, boolean ifinput)
//	throws IBEException;
//
//	public PATResult doPatASecond(PATResult patres_pre, String pnrno, int index, int psgid)
//	throws IBEException;

	public String reconfirmAirSeg(String pnrno, PNRAirSeg segment, int force)
	throws IBEException;

	public String reduceGroupSeats(String pnrno, int count)
	throws Exception;

	public String removeName(String pnrno, Vector passengers)
	throws Exception;

	public String splitPNR(String pnrno, Vector passengers, int count)
	throws Exception;

}

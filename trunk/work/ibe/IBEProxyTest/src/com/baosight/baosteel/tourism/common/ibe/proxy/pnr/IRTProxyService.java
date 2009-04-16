package com.baosight.baosteel.tourism.common.ibe.proxy.pnr;

import com.travelsky.ibe.client.pnr.PNREnvelop;
import com.travelsky.ibe.client.pnr.RTResult;
import com.travelsky.ibe.exceptions.IBEException;

public interface IRTProxyService {

	public RTResult retrieve(String pnrno) throws Exception;

	public RTResult retrieveCompletely(String pnrno) throws Exception;

	public PNREnvelop retrieveFirstEnvelop(String pnrno) throws IBEException;
	
}

package com.baosight.baosteel.tourism.common.ibe.decorate.pnr.impl;

import com.baosight.baosteel.tourism.common.ibe.decorate.pnr.ISellSeatDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.pnr.ISellSeatProxyService;
import com.travelsky.ibe.client.pnr.SSResult;


public class SellSeatDecorate implements ISellSeatDecorate{

	private ISellSeatProxyService sellSeatProxyService;

	public String commit() throws Exception{
		return sellSeatProxyService.commit();
	}

	public SSResult commit1() throws Exception{
		return sellSeatProxyService.commit1();
	}

	public ISellSeatProxyService getSellSeatProxyService() {
		return sellSeatProxyService;
	}

	public void setSellSeatProxyService(ISellSeatProxyService sellSeatProxyService) {
		this.sellSeatProxyService = sellSeatProxyService;
	}

}

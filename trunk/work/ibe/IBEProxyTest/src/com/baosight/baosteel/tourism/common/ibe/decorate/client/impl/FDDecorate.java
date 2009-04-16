package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IFDDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IFDProxyService;
import com.travelsky.ibe.client.FDResult;


public class FDDecorate implements IFDDecorate{

	private IFDProxyService fdProxyService;

	public FDResult findPrice(String org, String dst, 
			String date, String airline, 
			String planeModel, String passType, 
			boolean fullFareBasis) throws Exception{
		return fdProxyService.findPrice(org, dst, date, airline, planeModel, passType, fullFareBasis);
	}

	public IFDProxyService getFdProxyService() {
		return fdProxyService;
	}

	public void setFdProxyService(IFDProxyService fdProxyService) {
		this.fdProxyService = fdProxyService;
	}

}

package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.beans.factory.support.MethodReplacer;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.ISKDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.ISKProxyService;
import com.travelsky.ibe.client.SkResult;


public class SKDecorate implements ISKDecorate , MethodReplacer{

	private ISKProxyService skProxyService;

	public SkResult getSchedule(String org, String dst, Date date, String airline, boolean direct, boolean nostop) throws Exception{
		return skProxyService.getSchedule(org, dst, date, airline, direct, nostop);
	}

	
	public Object reimplement(Object obj, Method method, Object[] args)
	throws Throwable {
		System.out.println(args[0].toString());
		System.out.println(args[1].toString());
		System.out.println((Date)args[2]);
		System.out.println(args[3].toString());
		System.out.println(Boolean.valueOf(args[4].toString()).booleanValue());
		System.out.println(Boolean.valueOf(args[5].toString()).booleanValue());
		
		return (SkResult)this.getSchedule(args[0].toString(), 
				args[1].toString(), (Date)args[2], 
				args[3].toString(), Boolean.valueOf(args[4].toString()).booleanValue(), 
				Boolean.valueOf(args[5].toString()).booleanValue());
	}

	
//	public SkResult getSchedule(String org, String dst, Date date)  throws Exception{
//		try{
//			return skProxyService.getSchedule(org, dst, date, "ALL", false, false);
//		}
//		catch(Exception e){
//			throw e;
//		}
//	}
	
	
	public ISKProxyService getSkProxyService() {
		return skProxyService;
	}

	public void setSkProxyService(ISKProxyService skProxyService) {
		this.skProxyService = skProxyService;
	}

}

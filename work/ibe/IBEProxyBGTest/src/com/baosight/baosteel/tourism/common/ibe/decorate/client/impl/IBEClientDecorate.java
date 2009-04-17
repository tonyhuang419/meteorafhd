package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

import com.baosight.baosteel.tourism.common.ibe.proxy.client.IIBEClientService;

public class IBEClientDecorate implements MethodReplacer { 

	private IIBEClientService ibeClientService;

	public Object reimplement(Object obj, Method method, Object[] args)
	throws Throwable {
		/////////print info///////////////
		System.out.println("has called the replacement method");
		String s[] = (String[])args[0];
		for(int i=0;i<s.length;i++){
			System.out.println(s[i]);
		}

		return ibeClientService.query(s);
	}

	public IIBEClientService getIbeClientService() {
		return ibeClientService;
	}

	public void setIbeClientService(IIBEClientService ibeClientService) {
		this.ibeClientService = ibeClientService;
	}


}

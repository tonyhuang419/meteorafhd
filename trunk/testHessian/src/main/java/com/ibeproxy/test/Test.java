package com.ibeproxy.test;

import com.caucho.hessian.client.HessianProxyFactory;

public class Test {
	public static void main(String args[]) throws Exception   
	{   
		String url = "http://127.0.0.1:8080/testHessian/ibeClientService";      
		HessianProxyFactory factory = new HessianProxyFactory();      
		IIBEClientService ibeclient = (IIBEClientService) factory.create(IIBEClientService.class, url);  
		System.out.println(ibeclient); 
		ibeclient.query(new String[2]);
	}  
}

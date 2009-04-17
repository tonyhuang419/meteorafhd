package com.baosight.baosteel.tourism.common.ibe.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvResult;

public class Test {

	public static void main(String[] args) throws Exception {
		ApplicationContext content = new ClassPathXmlApplicationContext("IBEProxy.xml");
		AV av = (AV)content.getBean("avBG");
		AvResult avr  =  av.getAvailability("SHA","PEK", new Date());
		System.out.println(avr.getItemamount());
	}
}

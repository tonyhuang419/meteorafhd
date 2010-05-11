package com.baosight.baosteel.tourism.common.ibe.test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvResult;

public class Test {

	public static void main(String[] args) throws Exception {
		ApplicationContext content = new ClassPathXmlApplicationContext("IBEProxy.xml");
		int x = 100;
		ExecutorService exec = Executors.newFixedThreadPool(1);
		while(x-->0){
			exec.execute(new UserThread( content , x ));
		}
		exec.shutdown();
	}
}


class UserThread implements Runnable{

	private ApplicationContext content;
	private int i = 0;
	
	public UserThread(ApplicationContext content , int _i){
		this.content = content;
		this.i = _i;
	}

	public void run() {
		AV av = (AV)content.getBean("avBG");
		AvResult avr;
		try {
			System.out.println("================"+i);
			avr = av.getAvailability("SHA","PEK", new Date());
			System.out.println(avr.getItemamount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

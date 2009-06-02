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
		ExecutorService exec = Executors.newFixedThreadPool(2);
		while(--x>0){
			exec.execute(new UserThread( content ));
		}
		exec.shutdown();
		
//		for(int k=0;k<100;k++){
//			AV av = (AV)content.getBean("avBG");
//			AvResult avr  =  av.getAvailability("SHA","PEK", new Date());
//			System.out.println(avr.getItemamount());
//			System.out.println(avr);

//			FD fd = (FD)content.getBean("fdBG");
//			FDResult fdr = fd.findPrice("TAO", "SHA", "29APR09", "ALL", "",  "",true);
//			System.out.println(fdr);
//			System.out.println("机场费："+fdr.getAirportTax(0));
//			System.out.println("燃油税："+fdr.getFuelTax(0));


//			System.out.println(avr.getItemamount());
//			for (int j = 0; j < avr.getItemamount(); j++) {
//			AvItem item = avr.getItem(j);
//			for (int i = 0; i < item.getSegmentnumber(); i++) {
//			AvSegment seg = item.getSegment(i);
//			System.out.println("............");
//			if (seg.getOrgcity().equals("SHA")
//			&& seg.getDstcity().equals("NYC")) {
//			System.out.print("Airline:" + seg.getAirline() + "|");
//			System.out.print("Arridate:" + seg.getArridate() + "|");
//			System.out.print("Arridate:" + seg.getArridate() + "|");
//			System.out.print("Depdate:" + seg.getDepdate() + "|");
//			System.out.print("Deptime:" + seg.getDeptime() + "|");

//			System.out.print("Dstcity:" + seg.getDstcity() + "|");
//			System.out.print("Orgcity:" + seg.getOrgcity() + "|");
//			System.out.print("Planestyle:" + seg.getPlanestyle() + "|");
//			System.out.print("Selectedclass:" + seg.getSelectedclass() + "|");
//			System.out.print("Stopnumber:" + seg.getStopnumber() + "|");
//			System.out.print("CangweiinfoOfSort:" + seg.getCangweiinfoOfSort('A')
//			+ "|");
//			System.out.print("CangweiinfoOf" + seg.getCangweiinfoOf('F')
//			+ "|");
//			System.out.println("CangweiCodeSort"
//			+ seg.getCangweiCodeSort(1));
//			}
//			else{
//			System.out.print(seg.getOrgcity()+"|");
//			System.out.println(seg.getDstcity());
//			}
//			}
		}


//		System.out.println("sk.............................................");
//		SK sk = (SK)content.getBean("skBG");
//		SkResult skr = sk.getSchedule("SHA", "NYC", new Date());
//		System.out.println(skr.getItemamount());
//		System.out.print(skr.getDayf());
//		System.out.print(skr.getDayt());
//		for (int j = 0; j < skr.getItemamount(); j++) {
//		SkItem item = skr.getItem(j);
//		for (int i = 0; i < item.getSegmentnumber(); i++) {
//		SkSegment seg = item.getSegment(i);
//		if (seg.getOrgcity().equals("SHA")
//		&& seg.getDstcity().equals("NYC")) {
//		System.out.print(seg.getOrgcity() + "|");
//		System.out.print(seg.getDstcity() + "|");
//		System.out.print(seg.getAirline() + "|");
//		System.out.print(seg.getDeptime() + "|");
//		System.out.print(seg.getArritime() + "|");
//		System.out.println(seg.getPlanestyle() );
//		}
//		}
//		}
//		}

//		SellSeat ss = (SellSeat)content.getBean("sellSeatBG");
//		SellSeat ss2 = (SellSeat)content.getBean("sellSeatBG");
//		System.out.println(ss.hashCode());
//		System.out.println(ss2.hashCode());
//		System.out.println(ss.equals(ss2));

//		TServices ssx = (TServices)content.getBean("tservices");
//		System.out.println(ssx.getSellSeatBG().hashCode());
//		System.out.println(ssx.getSellSeatBG().hashCode());

//	}
}


class UserThread implements Runnable{

	private ApplicationContext content;
	
	public UserThread(ApplicationContext content){
		this.content = content;
	}
	
	public void run() {
		AV av = (AV)content.getBean("avBG");
		AvResult avr;
		try {
			avr = av.getAvailability("SHA","PEK", new Date());
			System.out.println(avr.getItemamount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ApplicationContext getContent() {
		return content;
	}

	public void setContent(ApplicationContext content) {
		this.content = content;
	}

}

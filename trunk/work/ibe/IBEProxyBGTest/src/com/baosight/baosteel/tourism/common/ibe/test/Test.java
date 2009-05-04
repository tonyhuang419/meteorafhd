package com.baosight.baosteel.tourism.common.ibe.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvResult;
import com.travelsky.ibe.client.pnr.SellSeat;

public class Test {

	public static void main(String[] args) throws Exception {
		ApplicationContext content = new ClassPathXmlApplicationContext("IBEProxy.xml");

		for(int k=0;k<100;k++){
			AV av = (AV)content.getBean("avBG");
			//NYC
			AvResult avr  =  av.getAvailability("SHA","NYC", new Date());
//			System.out.println(avr);
//			System.out.println(avr.getItemamount());
//			for (int j = 0; j < avr.getItemamount(); j++) {
//				AvItem item = avr.getItem(j);
//				for (int i = 0; i < item.getSegmentnumber(); i++) {
//					AvSegment seg = item.getSegment(i);
//					System.out.println("............");
//					if (seg.getOrgcity().equals("SHA")
//							&& seg.getDstcity().equals("NYC")) {
//						System.out.print("Airline:" + seg.getAirline() + "|");
//						System.out.print("Arridate:" + seg.getArridate() + "|");
//						System.out.print("Arridate:" + seg.getArridate() + "|");
//						System.out.print("Depdate:" + seg.getDepdate() + "|");
//						System.out.print("Deptime:" + seg.getDeptime() + "|");
//
//						System.out.print("Dstcity:" + seg.getDstcity() + "|");
//						System.out.print("Orgcity:" + seg.getOrgcity() + "|");
//						System.out.print("Planestyle:" + seg.getPlanestyle() + "|");
//						System.out.print("Selectedclass:" + seg.getSelectedclass() + "|");
//						System.out.print("Stopnumber:" + seg.getStopnumber() + "|");
//						System.out.print("CangweiinfoOfSort:" + seg.getCangweiinfoOfSort('A')
//								+ "|");
//						System.out.print("CangweiinfoOf" + seg.getCangweiinfoOf('F')
//								+ "|");
//						System.out.println("CangweiCodeSort"
//								+ seg.getCangweiCodeSort(1));
//					}
//					else{
//						System.out.print(seg.getOrgcity()+"|");
//						System.out.println(seg.getDstcity());
//					}
//				}
			}

//
//			System.out.println("sk.............................................");
//			SK sk = (SK)content.getBean("skBG");
//			SkResult skr = sk.getSchedule("SHA", "NYC", new Date());
//			System.out.println(skr.getItemamount());
//			System.out.print(skr.getDayf());
//			System.out.print(skr.getDayt());
//			for (int j = 0; j < skr.getItemamount(); j++) {
//				SkItem item = skr.getItem(j);
//				for (int i = 0; i < item.getSegmentnumber(); i++) {
//					SkSegment seg = item.getSegment(i);
//					if (seg.getOrgcity().equals("SHA")
//							&& seg.getDstcity().equals("NYC")) {
//						System.out.print(seg.getOrgcity() + "|");
//						System.out.print(seg.getDstcity() + "|");
//						System.out.print(seg.getAirline() + "|");
//						System.out.print(seg.getDeptime() + "|");
//						System.out.print(seg.getArritime() + "|");
//						System.out.println(seg.getPlanestyle() );
//					}
//				}
//			}
//		}
		
		SellSeat ss = (SellSeat)content.getBean("sellSeatBG");
		SellSeat ss2 = (SellSeat)content.getBean("sellSeatBG");
		System.out.println(ss.hashCode());
		System.out.println(ss2.hashCode());
		System.out.println(ss.equals(ss2));

	}
}
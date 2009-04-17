package com.baosight.baosteel.tourism.common.ibe.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvItem;
import com.travelsky.ibe.client.AvResult;
import com.travelsky.ibe.client.AvSegment;
import com.travelsky.ibe.client.SK;
import com.travelsky.ibe.client.SkItem;
import com.travelsky.ibe.client.SkResult;
import com.travelsky.ibe.client.SkSegment;

public class Test {

	public static void main(String[] args) throws Exception {

		ApplicationContext content = new ClassPathXmlApplicationContext("IBEProxy.xml");

		for(int k=0;k<100;k++){
			System.out.println("av.............................................");
			AV av = (AV)content.getBean("avBG");
			AvResult avr  =  av.getAvailability("SHA","PEK", new Date());
			System.out.println(avr.getItemamount());
			for (int j = 0; j < avr.getItemamount(); j++) {
				AvItem item = avr.getItem(j);
				for (int i = 0; i < item.getSegmentnumber(); i++) {
					AvSegment seg = item.getSegment(i);
					System.out.print("Airline:" + seg.getAirline() + "|");
					System.out.print("Arridate:" + seg.getArridate() + "|");
					System.out.print("Arridate:" + seg.getArridate() + "|");
					System.out.print("Depdate:" + seg.getDepdate() + "|");
					System.out.print("Deptime:" + seg.getDeptime() + "|");

					System.out.print("Dstcity:" + seg.getDstcity() + "|");
					System.out.print("Orgcity:" + seg.getOrgcity() + "|");
					System.out.print("Planestyle:" + seg.getPlanestyle() + "|");
					System.out.print("Selectedclass:" + seg.getSelectedclass() + "|");
					System.out.print("Stopnumber:" + seg.getStopnumber() + "|");
					System.out.print("CangweiinfoOfSort:" + seg.getCangweiinfoOfSort('A')
							+ "|");
					System.out.print("CangweiinfoOf" + seg.getCangweiinfoOf('F')
							+ "|");
					System.out.println("CangweiCodeSort"
							+ seg.getCangweiCodeSort(1));
				}
			}


			System.out.println("sk.............................................");
			SK sk = (SK)content.getBean("skBG");
			SkResult skr = sk.getSchedule("PEK", "CAN", new Date());
			System.out.print(skr.getDayf());
			System.out.print(skr.getDayt());
			for (int j = 0; j < skr.getItemamount(); j++) {
				SkItem item = skr.getItem(j);
				for (int i = 0; i < item.getSegmentnumber(); i++) {
					SkSegment seg = item.getSegment(i);
					if (seg.getOrgcity().equals("PEK")
							&& seg.getDstcity().equals("CAN")) {
						System.out.print(seg.getOrgcity() + "|");
						System.out.print(seg.getDstcity() + "|");
						System.out.print(seg.getAirline() + "|");
						System.out.print(seg.getDeptime() + "|");
						System.out.print(seg.getArritime() + "|");
						System.out.println(seg.getPlanestyle() );
					}
				}
			}
		}

	}
}

package com.baosight.baosteel.tourism.common.ibe.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IAVDecorate;
import com.travelsky.ibe.client.AvItem;
import com.travelsky.ibe.client.AvResult;
import com.travelsky.ibe.client.AvSegment;

public class Test {


	public static void main(String[] args) throws Exception {
		ApplicationContext content = new ClassPathXmlApplicationContext("IBEProxy.xml");

		IAVDecorate av = (IAVDecorate)content.getBean("avDecorate");
		AvResult avr = av.getAvailability("PEK", "CAN", new Date());
		System.out.println(avr.getItemamount());
		for (int j = 0; j < avr.getItemamount(); j++) {
			AvItem item = avr.getItem(j);
			for (int i = 0; i < item.getSegmentnumber(); i++) {
				AvSegment seg = item.getSegment(i);
				System.out.print("航班号：" + seg.getAirline() + "|");
				System.out.print("到达时刻的DATE型表示：" + seg.getArridate() + "|");
				System.out.print("到达时间：" + seg.getArritime() + "|");
				System.out.print("起飞时刻DATE表示：" + seg.getDepdate() + "|");
				System.out.print("起飞时刻：" + seg.getDeptime() + "|");

				System.out.print("到达城市三字码:" + seg.getDstcity() + "|");
				System.out.print("起始城市三字码：" + seg.getOrgcity() + "|");
				System.out.print("机型：" + seg.getPlanestyle() + "|");
				System.out.print("舱位代号：" + seg.getSelectedclass() + "|");
				System.out.print("经停次数：" + seg.getStopnumber() + "|");
				System.out.print("舱位信息的字符形式表示：" + seg.getCangweiinfoOfSort('A')
						+ "|");
				System.out.print(" 取得制定舱位的可用信息：" + seg.getCangweiinfoOf('F')
						+ "|");
				System.out.println("获取AV/H查询结果中指定位置上的舱位代码："
						+ seg.getCangweiCodeSort(1));
			}
		}
//		new AV().getAvailability("PEK", "CAN", new Date());
		

	}
}

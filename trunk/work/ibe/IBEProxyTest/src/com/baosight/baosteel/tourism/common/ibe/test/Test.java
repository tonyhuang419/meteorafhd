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
				System.out.print("����ţ�" + seg.getAirline() + "|");
				System.out.print("����ʱ�̵�DATE�ͱ�ʾ��" + seg.getArridate() + "|");
				System.out.print("����ʱ�䣺" + seg.getArritime() + "|");
				System.out.print("���ʱ��DATE��ʾ��" + seg.getDepdate() + "|");
				System.out.print("���ʱ�̣�" + seg.getDeptime() + "|");

				System.out.print("�������������:" + seg.getDstcity() + "|");
				System.out.print("��ʼ���������룺" + seg.getOrgcity() + "|");
				System.out.print("���ͣ�" + seg.getPlanestyle() + "|");
				System.out.print("��λ���ţ�" + seg.getSelectedclass() + "|");
				System.out.print("��ͣ������" + seg.getStopnumber() + "|");
				System.out.print("��λ��Ϣ���ַ���ʽ��ʾ��" + seg.getCangweiinfoOfSort('A')
						+ "|");
				System.out.print(" ȡ���ƶ���λ�Ŀ�����Ϣ��" + seg.getCangweiinfoOf('F')
						+ "|");
				System.out.println("��ȡAV/H��ѯ�����ָ��λ���ϵĲ�λ���룺"
						+ seg.getCangweiCodeSort(1));
			}
		}
//		new AV().getAvailability("PEK", "CAN", new Date());
		

	}
}

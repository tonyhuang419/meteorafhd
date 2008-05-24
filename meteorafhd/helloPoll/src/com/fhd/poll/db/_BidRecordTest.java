package com.fhd.poll.db;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fhd.poll.db.DAO.IDAOBidRecord;

public class _BidRecordTest
{
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/applicationContext-DB.xml");

		IDAOBidRecord dao = (IDAOBidRecord)context.getBean("DAOBidRecord");
		BidRecord bidRecord = (BidRecord)context.getBean("bidRecord");

//		bidRecord.setMjid(66);
//		bidRecord.setPrice(111);
//		bidRecord.setWhoBuy("xxxx");
//		bidRecord.setBidDate(new Date(1999,1,1));
//		dao.save(bidRecord);

//		dao.delete(1);

//		bidRecord = dao.getBidRecord(6);
//		bidRecord.setPrice(111);
//		bidRecord.setWhoBuy("aaaa");
//		bidRecord.setBidDate(new Date(999,1,1));
//		dao.update(bidRecord);

//		System.out.println(dao.getMaxBidPrice(2));

		List list = dao.seeBidRecord(2);
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] ser = (Object[])it.next();

			String p = (String)ser[0];
			System.out.print(p+"\t");

			String price = String.valueOf(ser[1]);
			System.out.print(price+"\t");

			String date = String.valueOf(ser[2]);
			System.out.println(date);	
		}

//		List list = dao.read();	
//		for(Iterator it = list.iterator();it.hasNext();){
//			bidRecord = (BidRecord)it.next();
//			System.out.print(bidRecord.getId()+"  ");
//			System.out.print(bidRecord.getMjid()+"  ");
//			System.out.print(bidRecord.getPrice()+"  ");
//			System.out.print(bidRecord.getWhoBuy()+"   ");
//			System.out.println(bidRecord.getBidDate()+"   ");
//		}
	}
}

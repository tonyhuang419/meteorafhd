package com.fhd.poll.db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fhd.poll.db.DAO.IDAOPoll;


public class _PollTest
{
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/applicationContext-DB.xml");

		IDAOPoll dao = (IDAOPoll)context.getBean("DAOPoll");
		Poll poll = (Poll)context.getBean("Poll");
		
		poll.setName("zzzz"); //name具有唯一性
		poll.setPrice(1111);
		poll.setDeal(false);
		dao.save(poll);

//		dao.delete(1);
		
//		poll = dao.getPoll(4);
//		poll.setName("112111");
//		dao.update(poll);
		
//		System.out.println(dao.isReName("112111"));
//		System.out.println(dao.getMaxPrice(4));
				
//		List list = dao.read();	
//		for(Iterator it = list.iterator();it.hasNext();){
//			poll = (Poll)it.next();
//			System.out.print(poll.getId()+"  ");
//			System.out.print(poll.getName()+"  ");
//			System.out.print(poll.getPrice()+"  ");
//			System.out.println(poll.isDeal());
//		}
	}
}
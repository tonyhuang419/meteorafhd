package dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import entity.Std;

public class DAOTest {

	private static final Logger logger = Logger.getLogger(DAOTest.class);

	public static void main(String[] args){
		ApplicationContext context = new FileSystemXmlApplicationContext("applicationContext.xml");
		Std std = null;
		IDAO dao = (IDAO)context.getBean("DAOImp");
		
		
//		std = new Std();
//		//std.setSid(111);   spring ÎÞÊÓ hibernate POJOÖ÷¼ü
//		std.setSname("xxx");
//		std.setAddr("xxxx");
//		dao.save(std);

//		dao.delete(1);
		
		//dao.modify();
//
		List list = dao.findAll();	
		for(Iterator it = list.iterator();it.hasNext();){
			std = (Std)it.next();
			System.out.print(std.getSid()+"  ");
			System.out.print(std.getSname()+"  ");
			System.out.println(std.getAddr());
		}
		
//		std = new Std();
//		std.setSid(3);
//		std.setSname("xx");
//		std.setAddr("yy");
//		dao.update(std);
		
	}
}

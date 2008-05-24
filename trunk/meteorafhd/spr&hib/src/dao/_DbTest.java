package dao;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import entity.Std;


public class _DbTest extends  TestCase {
	ApplicationContext context = new FileSystemXmlApplicationContext("applicationContext.xml");

	IDAO dao = (IDAO)context.getBean("DAOImp");
	Std std = (Std)context.getBean("std");

//	public void testC() throws Exception {
//	std.setSname("1");
//	std.setAddr("2");
//	dao.save(std);

//	std.setSname("1");
//	std.setAddr("2");
//	dao.save(std);			
//	}

//	public void testD() throws Exception {
//	dao.delete(4);
//	}

//	public void testU() throws Exception {
//	std = dao.getStd(12);
//	System.out.println(std.getSid());
//	System.out.println(std.getSname());
//	std.setSname("xxxxxxx");
//	//std.setAddr("11111");
//	dao.update(std);
//	}

	public void testU() throws Exception {
		std = dao.getStd(7);
		std.setSname("yyyyy");
		std.setAddr("xxxxxx");
		dao.update(std);
	}

	public void testR() throws Exception {
		List list = dao.findAll();	
		for(Iterator it = list.iterator();it.hasNext();){
			Std std = (Std)it.next();
			System.out.print(std.getSid()+"  ");
			System.out.print(std.getSname()+"  ");
			System.out.println(std.getAddr());
		}		
	}
}

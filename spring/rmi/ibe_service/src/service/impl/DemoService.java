package service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import service.IDemoService;
import entity.Stu;

public class DemoService implements IDemoService{

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public String hello(String str){
		logger.info("hello method has be called");
		return "Hello:"+str;
	}
	
	
	public Stu getStu(String name){
		logger.info("getStu method has be called");
		Stu stu = new Stu();
		stu.setName(name);
		return stu;
	}
	
}

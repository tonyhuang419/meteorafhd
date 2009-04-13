package service.impl;

import service.IDemoService;
import entity.Stu;

public class DemoService implements IDemoService{

	public String hello(String str){
		return "Hello:"+str;
	}
	
	
	public Stu getStu(String name){
		Stu stu = new Stu();
		stu.setName(name);
		return stu;
	}
	
}

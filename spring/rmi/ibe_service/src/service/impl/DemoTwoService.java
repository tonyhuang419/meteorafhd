package service.impl;

import service.IDemoTwoService;
import entity.Stu;

public class DemoTwoService implements IDemoTwoService{

	public String helloTwo(String str){
		return "Hello:"+str;
	}
	
	
	public Stu getStuTwo(String name){
		Stu stu = new Stu();
		stu.setName(name);
		return stu;
	}
	
}

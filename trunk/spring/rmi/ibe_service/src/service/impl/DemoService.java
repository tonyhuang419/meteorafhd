package service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import service.IDemoService;
import entity.Stu;

public class DemoService implements IDemoService{

	protected Log logger = LogFactory.getLog(this.getClass());

	private int i = 0;
	private byte[] lock = new byte[0];


	public String hello(String str){
		
		if(++i>10){
			i--;
			System.out.println("同时请求数过大");
			return "同时请求数过大";
		}
		
		synchronized(lock){
			i--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			logger.info("hello method has be called");
			return str;
		}
	}


	public Stu getStu(String name){
		logger.info("getStu method has be called");
		Stu stu = new Stu();
		stu.setName(name);
		return stu;
	}



}

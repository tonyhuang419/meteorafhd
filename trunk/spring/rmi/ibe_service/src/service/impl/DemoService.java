package service.impl;

import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import service.IDemoService;
import entity.Stu;

public class DemoService implements IDemoService{

	protected Log logger = LogFactory.getLog(this.getClass());

	private LinkedList queue = new LinkedList();
	private byte[] lock = new byte[0];


	public String hello(String str){
		if( !this.add(str)){
			return "null";
		}
		synchronized(lock){
			logger.info("hello method has be called");
			String s = (String)this.remove(queue);
			return "Hello:"+s;
		}
	}


	public Stu getStu(String name){
		logger.info("getStu method has be called");
		Stu stu = new Stu();
		stu.setName(name);
		return stu;
	}



	private Object remove( LinkedList queue ){
		synchronized(queue) {
			return  queue.removeFirst();	
		}
	}


	private boolean add(String args){
		synchronized(queue) {
			if(queue.size()<2){
				queue.add(args);
				return true;
			}
			else{
				System.out.println("同时请求数过大：2");
				return false;
			}
		}
	}

}

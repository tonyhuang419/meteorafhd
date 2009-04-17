package com.ibeproxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartIBEProxy {


	protected Log logger = LogFactory.getLog(this.getClass());


	public void startServer(){
		logger.info("sever start");
		ApplicationContext act = new ClassPathXmlApplicationContext("ApplicationContext.xml");
	}

	public static void main(String[] args) {
		new StartIBEProxy().startServer();
	}

}

package com.fstock.run;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fstock.service.IStockService;

public class StockRun {



	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IStockService 	stockService  = (IStockService)ctx.getBean("stockService");
//		stockService.getAllStockAndPersist();
		stockService.updateAllAverageLevel();
	}

}

package com.fstock.run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fstock.entity.Stock;
import com.fstock.service.ICommonService;
import com.fstock.service.IStockService;

public class StockRun {



	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ICommonService  commonService  = (ICommonService)ctx.getBean("commonService");
		IStockService 	stockService  = (IStockService)ctx.getBean("stockService");
		
		StockRun sr = new StockRun();
//		sr.getAllStockAndPersist(stockService);
		sr.saveAverageLevel(commonService, stockService );
		
	}
	
	
	public void getAllStockAndPersist(IStockService 	stockService){
		stockService.getAllStockAndPersist();
	}

	@SuppressWarnings("unchecked")
	public void saveAverageLevel(ICommonService  commonService , IStockService 	stockService){
		List<Stock> list = commonService.listHql(" from Stock s ", " order by s.id asc ");
		ExecutorService exec = Executors.newFixedThreadPool(3);
		for(Stock stock:list){
			exec.execute(new UserThread( stock , stockService ));
		}
		exec.shutdown();
	}
	
}

class UserThread implements Runnable{

	private Stock stock;
	private IStockService 	stockService;


	public UserThread( Stock stock , IStockService 	stockService){
		this.stock = stock;
		this.stockService = stockService;
	}

	public void run() {
		stockService.saveAverageLevel(stock);
	}

}
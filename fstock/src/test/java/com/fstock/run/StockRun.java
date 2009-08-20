package com.fstock.run;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fstock.entity.Stock;
import com.fstock.service.ICommonService;
import com.fstock.service.IStockService;
import com.fstock.util.ConstantValue;
import com.fstock.util.UtilTools;

public class StockRun {

	protected Log logger = LogFactory.getLog(this.getClass());

	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ICommonService  commonService  = (ICommonService)ctx.getBean("commonService");
		IStockService 	stockService  = (IStockService)ctx.getBean("stockService");

		StockRun sr = new StockRun();

		//sr.getAllStockAndPersist(stockService);

		//		sr.saveAverageLevel(commonService, stockService );

		//		sr.scanNewestAveragerLevel(stockService , "5");

		//		sr.findDateLevel(stockService, "20090819", "5");

		sr.findDateLevel(stockService, "20090819", "20090820", "5");

	}


	public void getAllStockAndPersist(IStockService 	stockService){
		stockService.getAllStockAndPersist();
	}

	@SuppressWarnings("unchecked")
	public void saveAverageLevel(ICommonService  commonService , IStockService 	stockService){
		List<Stock> list = commonService.listHql(" from Stock s ", " order by s.id asc ");
		ExecutorService exec = Executors.newFixedThreadPool(ConstantValue.threadSize);
		for(Stock stock:list){
			exec.execute(new UserThread( stock , stockService ));
		}
		exec.shutdown();
	}

	public void scanNewestAveragerLevel( IStockService stockService ,String level ){
		List<Object[]> stockList = stockService.scanNewestAveragerLevel(level);
		if(stockList.size()!=0){
			logger.info("lastest average "+ level + " star:");
		}
		else{
			logger.info("lastest average " + " no level " + level );
		}
		for(Object obj[] : stockList){
			logger.info( obj[0] +"-" + obj[1] +":" + obj[2] );
		}
	}

	public void findDateLevel( IStockService stockService , String date , String level){
		List<Stock> stockList = stockService.findDateOrganizationLevel(stockService, date, level);
		if(stockList.size()!=0){
			logger.info(date + " level " + level + " has :");
		}else{
			logger.info(date + " no level " + level );
		}
		for(Stock stock:stockList){
			logger.info(stock.getCode() +"-"+stock.getName()+"-"+stock.getOrganizationLevelDate()+"-"+stock.getOrganizationLevel());
		}
	}

	public void findDateLevel( IStockService stockService , String startDate , String endDate , String level){
		List<Stock> stockList = stockService.findDateOrganizationLevel(stockService, startDate , endDate , level);
		if(stockList.size()!=0){
			logger.info(startDate +" to "+ endDate + " level " + level + " has :");
		}
		else{
			logger.info(startDate +" to "+ endDate + " no level " + level);
		}
		for(Stock stock:stockList){
			logger.info(stock.getCode() +"-"+stock.getName()+"-"+stock.getOrganizationLevelDate()+"-"+stock.getOrganizationLevel());
		}
	}

}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());
	private Stock stock;
	private IStockService 	stockService;


	public UserThread( Stock stock , IStockService 	stockService){
		this.stock = stock;
		this.stockService = stockService;
	}

	public void run() {
		synchronized (stock){
			if( StringUtils.isBlank(stock.getAverageLevelDate())
					|| stock.getAverageLevelDate().indexOf(UtilTools.getDateFormat(new Date() ,"yyyyMMdd")) == -1){

				stockService.saveStockLevel(stock);
			}else{
				logger.info( stock.getCode() + " today has got level");	
			}
		}
	}

}
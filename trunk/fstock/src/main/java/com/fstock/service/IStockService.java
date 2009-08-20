package com.fstock.service;

import java.util.List;

import com.fstock.entity.Stock;

public interface IStockService {

	/**
	 * query all stocks and persost their
	 */
	public void getAllStockAndPersist();
	
	
	/**
	 * save stock average level
	 */
	public void saveStockLevel(Stock stock);
	
	/**
	 * is or not exist repeat
	 */
	public boolean existRepeatStock(Stock stock);
	

	public void updateAllAverageLevel();
	
	
	public List<Object[]> scanNewestAveragerLevel(String level);

	
	public List<Stock> findDateOrganizationLevel( IStockService stockService , String date , String level);
	
	public List<Stock> findDateOrganizationLevel( IStockService stockService , String startDate , String endDate ,  String level);
		
}

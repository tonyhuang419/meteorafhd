package com.fstock.service;

import com.fstock.entity.Stock;

public interface IStockService {

	/**
	 * query all stocks and persost their
	 */
	public void getAllStockAndPersist();
	
	
	/**
	 * save stock average level
	 */
	public void saveAverageLevel(Stock stock);
	
	/**
	 * is or not exist repeat
	 */
	public boolean existRepeatStock(Stock stock);
	

	public void updateAllAverageLevel();
	
}

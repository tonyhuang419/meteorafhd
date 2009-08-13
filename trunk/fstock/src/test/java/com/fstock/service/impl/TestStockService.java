package com.fstock.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fstock.BaseTest;
import com.fstock.service.IStockService;


@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestStockService extends BaseTest {



	@Autowired
	@Qualifier("stockService")
	private IStockService 	stockService;
	
	@Rollback(false)
	@Test
	public void run(){
		stockService.getAllStockAndPersist();
	}
	
}
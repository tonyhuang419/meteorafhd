package com.baoz.yx.service.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoz.yx.service.MonthHarvestPlanService;


public class TestMonthHarvestPlanService extends TestCase {


	private ApplicationContext context = null;
	private MonthHarvestPlanService monthHarvestPlanService  = null;


	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}


	public void tearDown() throws Exception {
		Thread.sleep(2000);
	}
	
//	public void testGenerateCurrentMonthPlan(){
//		monthHarvestPlanService = (MonthHarvestPlanService)context.getBean("monthHarvestPlanService");
//		monthHarvestPlanService.generateCurrentMonthPlan();
//	}
}


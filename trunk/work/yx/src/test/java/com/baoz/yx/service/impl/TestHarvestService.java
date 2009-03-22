package com.baoz.yx.service.impl;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IHarvestService;

public class TestHarvestService extends TestCase {

	private ApplicationContext context = null;
	private IHarvestService harvestService = null;
	private IForamlContractService foramlContractService = null;

	protected Log logger = LogFactory.getLog(this.getClass());

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public void tearDown() throws Exception {
		Thread.sleep(2000);
	}

	// public void testTestGetReveinfo(){
	// harvestService = (IHarvestService)context.getBean("harvestService");
	// harvestService.testGetReveinfo();
	// }

	// ok
	// public void testTestDelReveInfoByID(){
	// harvestService = (IHarvestService)context.getBean("harvestService");
	// harvestService.delReveInfoByID(1L);
	// }

	// ok
	public void testGetItemBillAmoutByItemID() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		logger.info(harvestService.getItemBillAmoutByItemID(1905L));
		logger.info(harvestService.getItemBillAmoutByItemID(1906L));
	}

	public void testGetBalanceByItemID() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		logger.info("........" + harvestService.getBalanceByItemID(1905L));
		logger.info("........" + harvestService.getBalanceByItemID(1906L));
	}

	// ok
	public void testGetReveInfoByItemId() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		List<ReveInfo> r = harvestService.getReveInfoByItemId(1905L);
		List<ReveInfo> r2 = harvestService.getReveInfoByItemId(1906L);
		logger.info(r.size());
		logger.info(r2.size());
	}

	// ok
	public void testGetSumByItemId() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		logger.info(harvestService.getSumByItemId(1905L));
		logger.info(harvestService.getSumByItemId(1906L));
	}

	// ok
	public void testGetItemBillAmoutByConID() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		logger.info(harvestService.getItemBillAmoutByConID(2982L));
	}

	// ok
	public void testGetSumByConId() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		logger.info(harvestService.getSumByConId(2982L));
	}

	// ok
	public void testGetBalanceByConID() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		logger.info(harvestService.getBalanceByConID(2982L));
	}

	public void testModifyRealArriveAmount() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		harvestService.modifyRealArriveAmount(2382L, 1884L, new Date(), 5L);
	}

	public void testModifyInvoiceAmount() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		harvestService.modifyInvoiceAmount(2982L);
	}
	
	public void testGetItemBillAmoutByItemList() {
		harvestService = (IHarvestService) context.getBean("harvestService");
		foramlContractService = (IForamlContractService) context.getBean("ForamlContractService");
		
		List<ContractItemMaininfo> iteminfolist = foramlContractService.loadContractItemMainInfo(4112L);	
		harvestService.getItemBillAmoutByItemList(iteminfolist);
	}

	// ok
	// public void testSaveOrUpdateReveInfo(){
	// harvestService = (IHarvestService)context.getBean("harvestService");
	// ReveInfo r = new ReveInfo();
	// r.setId(1L);
	// harvestService.saveOrUpdateReveInfo(r);
	// }

	// public void testDelReveInfo(){
	// harvestService = (IHarvestService)context.getBean("harvestService");
	// ReveInfo r = new ReveInfo();
	// r.setId(1L);
	// harvestService.delReveInfo(r);
	// }

}

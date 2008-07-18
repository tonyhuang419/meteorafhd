package com.baoz.yx.service.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoz.yx.service.IInvoiceService;


public class TestInvoiceService extends TestCase {


	private ApplicationContext context = null;
	private IInvoiceService invoiceService  = null;


	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}


	public void tearDown() throws Exception {
		Thread.sleep(2000);
	}


//	public void testGetItemNo(){
//	invoiceService = (IInvoiceService)context.getBean("invoiceService");
//	System.out.println(invoiceService.getItemNo(1L));
//	}

//	public void testGetItemName(){
//	invoiceService = (IInvoiceService)context.getBean("invoiceService");
//	System.out.println(invoiceService.getCimiName(1L));
//	}

//	public void testLoadAM(){
//	invoiceService = (IInvoiceService)context.getBean("invoiceService");
//	System.out.println(invoiceService.loadAM(299L));
//	}

//	public void testMakePurOut(){
//	invoiceService = (IInvoiceService)context.getBean("invoiceService");
//	invoiceService.makePurOut(299L);
//	}

//	public void testGetPlanDate(){
//		invoiceService = (IInvoiceService)context.getBean("invoiceService");
//		System.out.println(invoiceService.getPlanDate(308L));
//		System.out.println(invoiceService.getPlanDate(302L));
//		System.out.println(invoiceService.getPlanDate(297L));
//		System.out.println(invoiceService.getPlanDate(298L));
//	}

//	public void testGetIsUniteBill(){
//		invoiceService = (IInvoiceService)context.getBean("invoiceService");
//		System.out.println(invoiceService.getIsUniteBill(308L));
//
//	}
	
//	public void testGetInvoiceInfoFromApplySid(){
//		invoiceService = (IInvoiceService)context.getBean("invoiceService");
//		System.out.println(invoiceService.getInvoiceInfoFromApplySid(316L));
//	}

	public void testRelateContract(){
		invoiceService = (IInvoiceService)context.getBean("invoiceService");
		invoiceService.relateContract(316L,2153L);
	}
	
	public void testUnRelateContract(){
		invoiceService = (IInvoiceService)context.getBean("invoiceService");
		invoiceService.unRelateContract(316L);
	}
}


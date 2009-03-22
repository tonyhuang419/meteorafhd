package com.baoz.yx.tools;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IHarvestService;


public class TestExlObject extends TestCase {


	private ApplicationContext context = null;
	private IForamlContractService foramlContractService  = null;
	private IHarvestService harvestService = null;


	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}


	public void tearDown() throws Exception {
		Thread.sleep(2000);
	}

	//HQL测试通过
	public void testHQL() throws Exception {
//		foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//		List<Object[]> ab = foramlContractService.loadRCPlanAndItem(2171L);
//		ExlObject eo = new ExlObject("tt","tt");
//		eo.setTitle(new String[]{"计划","项目号"});
//		eo.addRows(ab);
//		eo.outputExcel();
	}
	
	//SQL测试通过
	public void testSQL() throws Exception {
//		harvestService = (IHarvestService)context.getBean("harvestService");
//		List<Object[]> ab = harvestService.test();
//		ExlObject eo = new ExlObject("tt","tt");
//		eo.setTitle(new String[]{"x","xx"});
//		eo.addRows(ab);
//		eo.outputExcel();
	}

}


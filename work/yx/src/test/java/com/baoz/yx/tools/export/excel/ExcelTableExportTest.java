package com.baoz.yx.tools.export.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;

/**
 * 类ExcelTableExportTest.java的实现描述：
 * @author xurong Oct 22, 2008 2:26:43 PM
 */
public class ExcelTableExportTest extends TestCase {

	private ApplicationContext context = null;
	private IForamlContractService foramlContractService  = null;
	private IHarvestService harvestService = null;


	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	//HQL ok
	public void testHQL() throws Exception{
		foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
		List<Object[]> ab = foramlContractService.loadRCPlanAndItem(2171L);
		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"1","2"});
		export.setTableName("xxx");
		for(Object[] obj:ab){
			export.addRow(obj);
		}

		export.export(new FileOutputStream(new File("xx.xls")));
	}

	//SQL ok
	public void testSQL() throws Exception{
		harvestService = (IHarvestService)context.getBean("harvestService");
		List<Object[]> ab = harvestService.test();

		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"1","2"});
		export.setTableName("yyy");
		for(Object[] obj:ab){
			export.addRow(obj);
		}

		export.export(new FileOutputStream(new File("yy.xls")));
		
	}
	
	
}

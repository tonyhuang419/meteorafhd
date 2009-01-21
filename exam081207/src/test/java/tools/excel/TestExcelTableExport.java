package tools.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.exam.tools.excel.TableExport;
import com.exam.tools.excel.TableExportFactory;


public class TestExcelTableExport extends TestCase {

	public void  testExl ()  throws Exception {
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"序号","组别" });
		export.setTableName("售前合同统计");
		System.out.println(new Date());
		System.out.println(Calendar.getInstance().getTime());
		export.addRow(new Object[]{ "1",new Date()		,Calendar.getInstance().getTime()	
		});
		FileOutputStream os = new FileOutputStream(new File("c:\\test.xls"));
		export.export(os);
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


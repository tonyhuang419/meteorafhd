package jasperReport;

import java.io.File;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class FirstJasperReports {
	static String fileName="HelloWorld.jrxml";
	public static void main(String[] args)throws Exception{
		long startTime=System.currentTimeMillis();
		//将报表的定义文件HelloWorld.jrxml编译成HelloWorld.jasper文件
		String jasperFile=JasperCompileManager.compileReportToFile(fileName);
		//向HelloWorld.jasper文件中填充数据，这一步将生产出HelloWorld .jrprint文件
		String jrprintFile=JasperFillManager.fillReportToFile(jasperFile,null,new JREmptyDataSource());
		//将.jrprint文件转换成HTML格式
		JasperExportManager.exportReportToHtmlFile(jrprintFile);
		//将.jrprint文件转换成PDF格式
		JasperExportManager.exportReportToPdfFile(jrprintFile);
		//将.jrprint文件转换成XML格式
		JasperExportManager.exportReportToXmlFile(jrprintFile,false);
		//将.jrprint文件转换成XLS格式(即Excel文件)，需要用到POI类库.
		File sourceFile = new File(jrprintFile);
		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xls");
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		exporter.exportReport();
		long endTime=System.currentTimeMillis();
		long time=(endTime-startTime)/1000;
		System.out.println("success with "+time+" s");
	}
}

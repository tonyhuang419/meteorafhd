package com.baoz.yx.action.system.hisdata;

import java.io.File;

import jxl.Workbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.yx.service.IExcelImportService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 类ImoprtHistoryContractAction.java的实现描述:
 * @author xurong Jun 30, 2008 4:16:48 PM
 */
public class ImoprtHistoryContractAction extends ActionSupport {
	private static Logger logger = Logger.getLogger(ImoprtHistoryContractAction.class);
	@Autowired
	@Qualifier("excelImportService")
	private IExcelImportService excelImportService;
	private File excelFile;
	
	@Override
	public String execute() throws Exception {
		return "input";
	}
	public String importData(){
		if(excelFile != null){
			try {
				logger.info("开始导入合同......");
				Workbook workbook = Workbook.getWorkbook(excelFile);
				logger.info("开始导入工程类");
				excelImportService.importHistoryEngineeringContract(workbook.getSheet(0));
				logger.info("开始导入外协类");
				excelImportService.importHistoryAssistanceContract(workbook.getSheet(1));
				logger.info("开始导入集成类");
				excelImportService.importHistoryIntegrationContract(workbook.getSheet(2));
				workbook.close();
				logger.info("合同导入结束");
				return "success";
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				return "input";
			}
		}else{
			return "input";
		}
	}
	public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
}

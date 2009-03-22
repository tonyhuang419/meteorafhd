package com.baoz.yx.tools.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.WritableFonts;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.StringCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.utils.ExcelUtils;

public class ContractAmountCompareTest extends TestCase/*extends YingXiaoBaseTest*/  {
	private static Logger logger = Logger.getLogger(ContractAmountCompareTest.class);
//	IYXCommonDao yxCommonDao;
//
//	@Override
//	protected void prepareTestInstance() throws Exception {
//		super.setAutowireMode(AUTOWIRE_BY_NAME);
//		super.prepareTestInstance();
//	}
//
//	public IYXCommonDao getYxCommonDao() {
//		return yxCommonDao;
//	}
//
//	public void setYxCommonDao(IYXCommonDao commonDao) {
//		this.yxCommonDao = commonDao;
//	}
//	
	public void testContractAmountCompare()  throws Exception {
		List<ContractAmount> planSheetList = importPlanSheet();
		//大表金额
		Map<String, ContractAmount> planSheetProjMap = new HashMap<String, ContractAmount>();
		Map<String, ContractAmount> planSheetConMap = new HashMap<String, ContractAmount>();
		//
		for (ContractAmount contractAmount : planSheetList) {
			if(StringUtils.isNotBlank(contractAmount.getProjectCode())){
				ContractAmount projContractAmount = defaultIfNull(planSheetProjMap.get(contractAmount.getProjectCode()));
				projContractAmount.setContractCode(contractAmount.getContractCode());
				projContractAmount.setProjectCode(contractAmount.getProjectCode());
				projContractAmount.setProjectAmount(defaultIfNull(projContractAmount.getProjectAmount())+defaultIfNull(contractAmount.getProjectAmount()));
				projContractAmount.setArriveAmount(defaultIfNull(projContractAmount.getArriveAmount())+defaultIfNull(contractAmount.getArriveAmount()));
				projContractAmount.setBillAmount(defaultIfNull(projContractAmount.getBillAmount())+defaultIfNull(contractAmount.getBillAmount()));
				planSheetProjMap.put(contractAmount.getProjectCode(), projContractAmount);	
			}
			///////////////////////////////////
			if(StringUtils.isNotBlank(contractAmount.getContractCode())){
				ContractAmount conContractAmount = defaultIfNull(planSheetConMap.get(contractAmount.getContractCode()));
				conContractAmount.setContractCode(contractAmount.getContractCode());
				conContractAmount.setProjectCode(contractAmount.getProjectCode());
				conContractAmount.setContractAmount(defaultIfNull(conContractAmount.getContractAmount()));
				conContractAmount.setProjectAmount(defaultIfNull(conContractAmount.getProjectAmount())+defaultIfNull(contractAmount.getProjectAmount()));
				conContractAmount.setArriveAmount(defaultIfNull(conContractAmount.getArriveAmount())+defaultIfNull(contractAmount.getArriveAmount()));
				conContractAmount.setBillAmount(defaultIfNull(conContractAmount.getBillAmount())+defaultIfNull(contractAmount.getBillAmount()));
				planSheetConMap.put(contractAmount.getContractCode(), conContractAmount);		
			}
		}
		//ERP金额
		Map<String, ContractAmount> erpSheetProjMap = new HashMap<String, ContractAmount>();
		Map<String, ContractAmount> erpSheetConMap = new HashMap<String, ContractAmount>();
		List<ContractAmount> erpSheetList = importERPSheet();
		for (ContractAmount contractAmountErp : erpSheetList) {
			if(StringUtils.isNotBlank(contractAmountErp.getProjectCode())){
				ContractAmount projContractAmount = defaultIfNull(erpSheetProjMap.get(contractAmountErp.getProjectCode()));
				projContractAmount.setContractCode(contractAmountErp.getContractCode());
				projContractAmount.setProjectCode(contractAmountErp.getProjectCode());
				projContractAmount.setProjectAmount(defaultIfNull(projContractAmount.getProjectAmount())+defaultIfNull(contractAmountErp.getProjectAmount()));
				projContractAmount.setArriveAmount(defaultIfNull(projContractAmount.getArriveAmount())+defaultIfNull(contractAmountErp.getArriveAmount()));
				projContractAmount.setBillAmount(defaultIfNull(projContractAmount.getBillAmount())+defaultIfNull(contractAmountErp.getBillAmount()));
				erpSheetProjMap.put(contractAmountErp.getProjectCode(), projContractAmount);	
			}
			///////////////////////////////////
			if(StringUtils.isNotBlank(contractAmountErp.getContractCode())){
				ContractAmount conContractAmount = defaultIfNull(erpSheetConMap.get(contractAmountErp.getContractCode()));
				conContractAmount.setContractCode(contractAmountErp.getContractCode());
				conContractAmount.setProjectCode(contractAmountErp.getProjectCode());
				conContractAmount.setContractAmount(defaultIfNull(contractAmountErp.getContractAmount()));
				conContractAmount.setProjectAmount(defaultIfNull(conContractAmount.getProjectAmount())+defaultIfNull(contractAmountErp.getProjectAmount()));
				conContractAmount.setArriveAmount(defaultIfNull(conContractAmount.getArriveAmount())+defaultIfNull(contractAmountErp.getArriveAmount()));
				conContractAmount.setBillAmount(defaultIfNull(conContractAmount.getBillAmount())+defaultIfNull(contractAmountErp.getBillAmount()));
				erpSheetConMap.put(contractAmountErp.getContractCode(), conContractAmount);		
			}			
		}
		//写入列表
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\合同金额集成类对比模板.xls"));
		WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File("C:\\Users\\raddle\\Desktop\\合同金额集成类对比数据.xls"),workbook);
		WritableSheet writableSheet = writableWorkbook.getSheet(0);
		for (int i = 1; i < writableSheet.getRows(); i++) {
			String contractCode = contractCode = writableSheet.getCell(ExcelUtils.getColumn("B"), i).getContents();
			String projectCode = writableSheet.getCell(ExcelUtils.getColumn("C"), i).getContents();
			if(writableSheet.getCell(ExcelUtils.getColumn("C"), i) != null){
				projectCode = writableSheet.getCell(ExcelUtils.getColumn("C"), i).getContents();
			}
			//集成类无项目，项目号和合同号相同
			if(StringUtils.isBlank(projectCode) && contractCode.length() == 5){
				projectCode = contractCode;
			}
			if(projectCode != null){
				//大表金额
				ContractAmount projContractAmount = planSheetProjMap.get(projectCode);
				if(projContractAmount != null){
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("N"), i,projContractAmount.getProjectAmount()));
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("O"), i,projContractAmount.getBillAmount()));
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("P"), i,projContractAmount.getArriveAmount()));
				}
				//erp金额
				ContractAmount erpProjContractAmount = erpSheetProjMap.get(projectCode);
				if(erpProjContractAmount != null){
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("J"), i,erpProjContractAmount.getProjectAmount()));
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("K"), i,erpProjContractAmount.getBillAmount()));
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("L"), i,erpProjContractAmount.getArriveAmount()));
				}
			}
			//大表金额
			ContractAmount conContractAmount = planSheetConMap.get(contractCode);
			if(conContractAmount != null){
				if(conContractAmount.getContractAmount() != 0){
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("M"), i,conContractAmount.getContractAmount()));
				}else if(conContractAmount.getProjectAmount() != 0){
					writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("M"), i,conContractAmount.getProjectAmount()));
				}
			}
			//erp金额
			ContractAmount erpConContractAmount = erpSheetConMap.get(contractCode);
			if(erpConContractAmount != null){
				writableSheet.addCell(createNumberCell(ExcelUtils.getColumn("I"), i,erpConContractAmount.getContractAmount()));
			}
		}
		writableWorkbook.write();
		writableWorkbook.close();
		workbook.close();
	}
	private List<ContractAmount> importPlanSheet() throws Exception {
		List<ContractAmount> amountList = new ArrayList<ContractAmount>();
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\2007年制造业营销部合同计划开票、收款明细表080912.xls"));
		Sheet sheet = workbook.getSheet(0);
		RowRuleImpl rowRule = new RowRuleImpl();
		// //////////////////////////////////////设置读取规则
		rowRule.addCellRule(new CellRuleImpl("V","contractCode"));
		rowRule.addCellRule(new CellRuleImpl("X","projectCode"));
		rowRule.addCellRule(new CellRuleImpl("AH","contractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("AI","projectAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("AU","billAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("AW","arriveAmount",new NumberCellValueConvertor()));
		/////
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ContractAmount.class);
		contractBuilder.setRule(29, sheet.getRows(), rowRule);
		RowResult<ContractAmount>[] cons = contractBuilder.parseExcel();
		int headRow = 28;
		for (RowResult<ContractAmount> result : cons) {
			if(!result.hasErrors()){
				ContractAmount contractAmount = result.getRowObject();
				if(StringUtils.isBlank(contractAmount.getContractCode()) && StringUtils.isBlank(contractAmount.getProjectCode())){
					loggerMessage(getPrefixStr(sheet,"V",result,headRow)+"为空,"+getPrefixStr(sheet,"X",result,headRow)+"为空");
					continue;
				}
				//集成类无项目，项目号和合同号相同
				if(StringUtils.isBlank(contractAmount.getProjectCode()) && contractAmount.getContractCode() != null && contractAmount.getContractCode().length() == 5){
					contractAmount.setProjectCode(contractAmount.getContractCode());
				}
				amountList.add(contractAmount);
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : result.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				loggerMessage(errorMsg.toString());
			}
		}
		workbook.close();
		return amountList;
	}
	
	private List<ContractAmount> importERPSheet() throws Exception {
		List<ContractAmount> amountList = new ArrayList<ContractAmount>();
		// ERP清单
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\财务--项目状态清单20080911.xls"));
		Sheet sheet = workbook.getSheet(0);
		RowRuleImpl rowRule = new RowRuleImpl();
		// //////////////////////////////////////设置读取规则
		rowRule.addCellRule(new CellRuleImpl("T","contractCode"));
		rowRule.addCellRule(new CellRuleImpl("C","projectCode"));
		rowRule.addCellRule(new CellRuleImpl("U","contractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("V","projectAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("AQ","billAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("AS","arriveAmount",new NumberCellValueConvertor()));
		/////
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ContractAmount.class);
		contractBuilder.setRule(1, sheet.getRows(), rowRule);
		RowResult<ContractAmount>[] cons = contractBuilder.parseExcel();
		int headRow = 0;
		for (RowResult<ContractAmount> result : cons) {
			if(!result.hasErrors()){
				ContractAmount contractAmount = result.getRowObject();
				if(StringUtils.isBlank(contractAmount.getContractCode()) && StringUtils.isBlank(contractAmount.getProjectCode())){
					loggerMessage(getPrefixStr(sheet,"T",result,headRow)+"为空,"+getPrefixStr(sheet,"C",result,headRow)+"为空");
					continue;
				}
				//集成类无项目，项目号和合同号相同
				if(StringUtils.isBlank(contractAmount.getProjectCode()) && contractAmount.getContractCode() != null && contractAmount.getContractCode().length() == 5){
					contractAmount.setProjectCode(contractAmount.getContractCode());
				}
				amountList.add(contractAmount);
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : result.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				loggerMessage(errorMsg.toString());
			}
		}
		workbook.close();
		//Erp清单,集成类
		// ERP清单
		Workbook workbookProject = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\集成类订单明细.xls"));
		Sheet sheetProject = workbookProject.getSheet(0);
		RowRuleImpl rowRuleProject = new RowRuleImpl();
		// //////////////////////////////////////设置读取规则
		rowRuleProject.addCellRule(new CellRuleImpl("A","contractCode"));
		rowRuleProject.addCellRule(new CellRuleImpl("B","contractAmount",new NumberCellValueConvertor()));
		rowRuleProject.addCellRule(new CellRuleImpl("C","billAmount",new NumberCellValueConvertor()));
		rowRuleProject.addCellRule(new CellRuleImpl("D","arriveAmount",new NumberCellValueConvertor()));
		/////
		JExcelRowObjectBuilder contractBuilderProject = new JExcelRowObjectBuilder();
		contractBuilderProject.setSheet(sheetProject);
		contractBuilderProject.setTargetClass(ContractAmount.class);
		contractBuilderProject.setRule(1, sheetProject.getRows(), rowRuleProject);
		RowResult<ContractAmount>[] consProject = contractBuilderProject.parseExcel();
		int headRowProject = 1;
		for (RowResult<ContractAmount> result : consProject) {
			if(!result.hasErrors()){
				ContractAmount contractAmount = result.getRowObject();
				if(StringUtils.isBlank(contractAmount.getContractCode())){
					loggerMessage(getPrefixStr(sheet,"A",result,headRowProject)+"为空");
					continue;
				}
				//集成类无项目，项目号和合同号相同
				if(StringUtils.isBlank(contractAmount.getProjectCode()) && contractAmount.getContractCode() != null && contractAmount.getContractCode().length() < 10){
					contractAmount.setProjectCode(contractAmount.getContractCode());
				}
				amountList.add(contractAmount);
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : result.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				loggerMessage(errorMsg.toString());
			}
		}
		workbookProject.close();
		return amountList;
	}
	private jxl.write.Number createNumberCell(int column, int row, double d){
		WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.FLOAT); 
		jxl.write.Number floatNumber = new jxl.write.Number(column, row, d, floatFormat); 
		return floatNumber; 
	}
	private String getPrefixStr(Sheet sheet,String column,RowResult result,int headRow){
		return "行"+result.getRow()+":"+new StringCellValueConvertor().getCellValue(sheet.getCell(ExcelUtils.getColumn(column),headRow));
	}
	private void loggerMessage(String message){
		System.out.println(message);
	}
	private ContractAmount defaultIfNull(ContractAmount contractAmount){
		return contractAmount!=null?contractAmount:new ContractAmount();
	}
	private Double defaultIfNull(Double d){
		return d!=null?d:new Double(0);
	}
	public static class ContractAmount{
		private String contractCode;
		private String projectCode;
		private Double contractAmount;
		private Double projectAmount;
		private Double billAmount;
		private Double arriveAmount;
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public String getProjectCode() {
			return projectCode;
		}
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}
		public Double getContractAmount() {
			return contractAmount;
		}
		public void setContractAmount(Double contractAmount) {
			this.contractAmount = contractAmount;
		}
		public Double getProjectAmount() {
			return projectAmount;
		}
		public void setProjectAmount(Double projectAmount) {
			this.projectAmount = projectAmount;
		}
		public Double getBillAmount() {
			return billAmount;
		}
		public void setBillAmount(Double billAmount) {
			this.billAmount = billAmount;
		}
		public Double getArriveAmount() {
			return arriveAmount;
		}
		public void setArriveAmount(Double arriveAmount) {
			this.arriveAmount = arriveAmount;
		}
	}
}

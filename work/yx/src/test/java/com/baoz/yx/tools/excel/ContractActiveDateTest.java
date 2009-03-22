package com.baoz.yx.tools.excel;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;
import jxl.Sheet;
import jxl.Workbook;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;

public class ContractActiveDateTest extends YingXiaoBaseTest   {
	IYXCommonDao yxCommonDao;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(false);
		super.prepareTestInstance();
	}

	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}

	public void setYxCommonDao(IYXCommonDao commonDao) {
		this.yxCommonDao = commonDao;
	}
	public static class ContractActiveDate{
		private String contractCode;
		private Date activeDate;
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public Date getActiveDate() {
			return activeDate;
		}
		public void setActiveDate(Date activeDate) {
			this.activeDate = activeDate;
		}
	}
	public void testImportProjectActiveDate() throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\合同转正式日期.xls"));
		Sheet sheet = workbook.getSheet(0); 
		System.out.println(sheet.getName());
		RowRuleImpl rowRule = new RowRuleImpl();
		rowRule.addCellRule(new CellRuleImpl("A","contractCode"));
		rowRule.addCellRule(new CellRuleImpl("B","activeDate",new DateCellValueConvertor()));
		
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ContractActiveDate.class);
		contractBuilder.setRule(1, sheet.getRows(), rowRule);
		RowResult<ContractActiveDate>[] cons = contractBuilder.parseExcel();
		int i = 0;
		for (RowResult<ContractActiveDate> result : cons) {
			if(!result.hasErrors()){
				ContractActiveDate contractActiveDate = result.getRowObject();
				if(contractActiveDate.getActiveDate() == null){
					System.out.println("can't find conatrct ["+contractActiveDate.getContractCode()+"]");
				}
				ContractMainInfo contract = (ContractMainInfo) yxCommonDao.uniqueResult("from ContractMainInfo m where m.conId = ? ", contractActiveDate.getContractCode());
				if(contract != null){
					contract.setActiveDate(contractActiveDate.getActiveDate());
					yxCommonDao.update(contract);
					i++;
				}else{
					System.out.println("can't find conatrct ["+contractActiveDate.getContractCode()+"]");
				}
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : result.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				System.out.println(errorMsg);
			}
		}
		System.out.println("项目类更新"+i+"条");
	}
	public void testImportInteActiveDate() throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\合同转正式日期.xls"));
		Sheet sheet = workbook.getSheet(1);
		System.out.println(sheet.getName());
		RowRuleImpl rowRule = new RowRuleImpl();
		rowRule.addCellRule(new CellRuleImpl("A","contractCode"));
		rowRule.addCellRule(new CellRuleImpl("B","activeDate",new DateCellValueConvertor()));
		
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ContractActiveDate.class);
		contractBuilder.setRule(1, sheet.getRows(), rowRule);
		RowResult<ContractActiveDate>[] cons = contractBuilder.parseExcel();
		int i = 0;
		for (RowResult<ContractActiveDate> result : cons) {
			if(!result.hasErrors()){
				ContractActiveDate contractActiveDate = result.getRowObject();
				if(contractActiveDate.getActiveDate() == null){
					System.out.println("can't find conatrct ["+contractActiveDate.getContractCode()+"]");
				}
				ContractMainInfo contract = (ContractMainInfo) yxCommonDao.uniqueResult("from ContractMainInfo m where m.conId = ? ", contractActiveDate.getContractCode());
				if(contract != null){
					contract.setActiveDate(contractActiveDate.getActiveDate());
					yxCommonDao.update(contract);
					i++;
				}else{
					System.out.println("can't find conatrct ["+contractActiveDate.getContractCode()+"]");
				}
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : result.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				System.out.println(errorMsg);
			}
		}
		System.out.println("集成类更新"+i+"条");
	}
}

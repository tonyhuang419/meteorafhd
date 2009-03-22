package com.baoz.yx.tools.excel;

import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.StringArrayCellValueConvertor;
import com.baoz.yx.excel.rule.CellRule;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.vo.ExcelEngineeringHistoryContract;
import com.baoz.yx.vo.ExcelIntegrationHistoryContract;

/**
 * 类JExcelTest.java的实现描述：
 * @author xurong Jun 30, 2008 2:56:54 PM
 */
public class JExcelTest extends TestCase {
	public void testJexcel() throws Exception{
		Workbook workbook = Workbook.getWorkbook(JExcelTest.class.getClassLoader().getResourceAsStream("08年合同清单.xls"));
		Sheet sheet = workbook.getSheet(0); 
		System.out.println(sheet.getName());
		RowRuleImpl rowRule = new RowRuleImpl();
		rowRule.addCellRule(new CellRuleImpl(5,"conName"));
		rowRule.addCellRule(new CellRuleImpl(8,"conTaxTamount",new NumberCellValueConvertor()));
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ContractMainInfo.class);
		contractBuilder.setRule(1, 10, rowRule);
		System.out.println(contractBuilder.parseExcel());
		RowResult<ContractMainInfo>[] cons = contractBuilder.parseExcel();
		for (RowResult<ContractMainInfo> result : cons) {
			System.out.println(result.getRowObject().getConName()+"  "+result.getRowObject().getConTaxTamount());
		}
		workbook.close();
	}
	
	public void testHistoryContrat() throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\合同清单复制\\08年合同清单.xls"));
		Sheet sheet = workbook.getSheet(0); 
		System.out.println(sheet.getName());
		RowRuleImpl rowRule = new RowRuleImpl();
		rowRule.addCellRule(new CellRuleImpl("B","jiaContractCodes",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl("C","jiaProjectCodes",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl("D","contractCode"));
		rowRule.addCellRule(new CellRuleImpl("E","projectCode",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl("F","contractName"));
		rowRule.addCellRule(new CellRuleImpl("G","clientName"));
		rowRule.addCellRule(new CellRuleImpl("H","taxContractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("I","noTaxContractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("J","preDecide"));
		rowRule.addCellRule(new CellRuleImpl("K","deviceAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("L","noDeviceAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("M","softDevelopAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("N","taxOutsourcingAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("O","noTaxOutsourcingAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("P","sellerName"));
		rowRule.addCellRule(new CellRuleImpl("Q","mainProjectDepartment",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl("R","contractSignDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("S","contractEndDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("V","technicalContract"));
		rowRule.addCellRule(new CellRuleImpl("W","clientQuality"));
		rowRule.addCellRule(new CellRuleImpl("X","clientSteelIndustry"));
		rowRule.addCellRule(new CellRuleImpl("AC","contractClientProjCategory"));
		rowRule.addCellRule(new CellRuleImpl("AF","bidContract"));
		rowRule.addCellRule(new CellRuleImpl("AG","approvedDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("AH","contractMemo"));
		
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ExcelEngineeringHistoryContract.class);
		contractBuilder.setRule(1, sheet.getRows(), rowRule);
		System.out.println(contractBuilder.parseExcel());
		RowResult<ExcelEngineeringHistoryContract>[] cons = contractBuilder.parseExcel();
		System.out.println("===============================================");
		BeanWrapperImpl warp = new BeanWrapperImpl(false);
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<table width='100%' border='1'>\n");
		htmlBuilder.append("<tr>");
		CellRule[] cellRuls = rowRule.getCellRules();
		for (CellRule cellRule : cellRuls) {
			htmlBuilder.append("<td nowrap>"+sheet.getCell(cellRule.getColumn(), 0).getContents()+"</td>");
		}
		htmlBuilder.append("</tr>\n");
		Set<String> set = new HashSet<String>();
		for (RowResult<ExcelEngineeringHistoryContract> result : cons) {
			htmlBuilder.append("<tr>");
			warp.setWrappedInstance(result.getRowObject());
			CellRule[] cellRuls1 = rowRule.getCellRules();
			for (CellRule cellRule : cellRuls1) {
				Object obj = warp.getPropertyValue(cellRule.getProperty());
				if(obj != null && obj.getClass().isArray()){
					obj = StringUtils.join((Object[])obj, ",");
				}
				htmlBuilder.append("<td>"+obj+"</td>");
			}
			htmlBuilder.append("</tr>\n");
			//部门
			String[] dep = getDepartmentInfo(result.getRowObject().getMainProjectDepartment());
			for (String string : dep) {
				set.add(string);
			}
		}
		htmlBuilder.append("</table>\n");
		//System.out.println(htmlBuilder);
		System.out.println("rows:"+sheet.getRows()+", colums:"+sheet.getColumns());
		FileWriter w = new FileWriter(new File("C:\\Users\\raddle\\Desktop\\import.html"));
		w.append(htmlBuilder);
		w.flush();
		w.close();
		workbook.close();
		for (String dep : set) {
			System.out.println(dep);
		}
		
	}
	private String[] getDepartmentInfo(String[] departmentStrs){
		if(departmentStrs == null || departmentStrs.length == 0){
			return new String[0];
		}
		String[] ret = new String[departmentStrs.length];
		for (int i = 0; i < departmentStrs.length; i++) {
			String dstr = departmentStrs[i];
			int indexLBracket = Math.max(dstr.indexOf("("), dstr.indexOf("（"));
			//只有部门名称
			if(indexLBracket == -1){
				ret[i]= dstr.trim();
			}else{
				ret[i] = dstr.substring(0,indexLBracket).trim();
			}
		}
		return ret;
	}
	
	public void testInegrationHistory() throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\raddle\\Desktop\\合同清单复制\\08年合同清单.xls"));
		Sheet sheet = workbook.getSheet(2);
		System.out.println(sheet.getName());
		RowRuleImpl rowRule = new RowRuleImpl();
		rowRule.addCellRule(new CellRuleImpl(1,"jiaContractCodes",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl(2,"jiaProjectCodes",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl(3,"contractCode"));
		rowRule.addCellRule(new CellRuleImpl(4,"contractName"));
		rowRule.addCellRule(new CellRuleImpl(5,"clientName"));
		rowRule.addCellRule(new CellRuleImpl(6,"taxContractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(7,"noTaxContractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(8,"sellerName"));
		rowRule.addCellRule(new CellRuleImpl(9,"contractSignDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(10,"mainProjectDepartment"));
		rowRule.addCellRule(new CellRuleImpl(11,"clientQuality"));
		rowRule.addCellRule(new CellRuleImpl(12,"clientSteelIndustry"));
		rowRule.addCellRule(new CellRuleImpl(20,"contractMemo"));
		
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(sheet);
		contractBuilder.setTargetClass(ExcelIntegrationHistoryContract.class);
		contractBuilder.setRule(1, sheet.getRows(), rowRule);
		System.out.println(contractBuilder.parseExcel());
		RowResult<ExcelIntegrationHistoryContract>[] cons = contractBuilder.parseExcel();
		System.out.println("===============================================");
		BeanWrapperImpl warp = new BeanWrapperImpl(false);
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<table width='100%' border='1'>\n");
		htmlBuilder.append("<tr>");
		CellRule[] cellRuls = rowRule.getCellRules();
		for (CellRule cellRule : cellRuls) {
			htmlBuilder.append("<td nowrap>"+sheet.getCell(cellRule.getColumn(), 0).getContents()+"</td>");
		}
		htmlBuilder.append("</tr>\n");
		Set<String> set = new HashSet<String>();
		for (RowResult<ExcelIntegrationHistoryContract> result : cons) {
			htmlBuilder.append("<tr>");
			warp.setWrappedInstance(result.getRowObject());
			CellRule[] cellRuls1 = rowRule.getCellRules();
			for (CellRule cellRule : cellRuls1) {
				Object obj = warp.getPropertyValue(cellRule.getProperty());
				if(obj != null && obj.getClass().isArray()){
					obj = StringUtils.join((Object[])obj, ",");
				}
				htmlBuilder.append("<td>"+obj+"</td>");
			}
			htmlBuilder.append("</tr>\n");
			//部门
			set.add(result.getRowObject().getMainProjectDepartment());
			
		}
		htmlBuilder.append("</table>\n");
		for (String dep : set) {
			System.out.println(dep);
		}
		//System.out.println(htmlBuilder);
		System.out.println("rows:"+sheet.getRows()+", colums:"+sheet.getColumns());
		workbook.close();
	}
}

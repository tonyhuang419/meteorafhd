package com.baoz.yx.tools.excel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import junit.framework.TestCase;
import jxl.Sheet;
import jxl.Workbook;

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
		Workbook workbook = Workbook.getWorkbook(JExcelTest.class.getClassLoader().getResourceAsStream("08年合同清单.xls"));
		Sheet sheet = workbook.getSheet(0); 
		System.out.println(sheet.getName());
		RowRuleImpl rowRule = new RowRuleImpl();
		rowRule.addCellRule(new CellRuleImpl(1,"jiaContractCodes",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl(2,"jiaProjectCodes",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl(3,"contractCode"));
		rowRule.addCellRule(new CellRuleImpl(4,"projectCode",new StringArrayCellValueConvertor(true)));
		rowRule.addCellRule(new CellRuleImpl(5,"contractName"));
		rowRule.addCellRule(new CellRuleImpl(6,"clientName"));
		rowRule.addCellRule(new CellRuleImpl(7,"taxContractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(8,"noTaxContractAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(9,"preDecide"));
		rowRule.addCellRule(new CellRuleImpl(10,"deviceAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(11,"noDeviceAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(12,"softDevelopAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(13,"taxOutsourcingAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(14,"noTaxOutsourcingAmount",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(15,"sellerName"));
		rowRule.addCellRule(new CellRuleImpl(16,"mainProjectDepartment"));
		rowRule.addCellRule(new CellRuleImpl(17,"contractSignDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(18,"contractEndDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(21,"technicalContract"));
		rowRule.addCellRule(new CellRuleImpl(22,"clientQuality"));
		rowRule.addCellRule(new CellRuleImpl(23,"clientSteelIndustry"));
		rowRule.addCellRule(new CellRuleImpl(28,"contractClientProjCategory"));
		rowRule.addCellRule(new CellRuleImpl(31,"bidContract"));
		rowRule.addCellRule(new CellRuleImpl(32,"approvedDate",new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(33,"contractMemo"));
		
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
		}
		htmlBuilder.append("</table>\n");
		System.out.println(htmlBuilder);
		System.out.println("rows:"+sheet.getRows()+", colums:"+sheet.getColumns());
		workbook.close();
		
	}
	
	public void testInegrationHistory() throws Exception{
		Workbook workbook = Workbook.getWorkbook(JExcelTest.class.getClassLoader().getResourceAsStream("08年合同清单.xls"));
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
		}
		htmlBuilder.append("</table>\n");
		System.out.println(htmlBuilder);
		System.out.println("rows:"+sheet.getRows()+", colums:"+sheet.getColumns());
		workbook.close();
	}
}

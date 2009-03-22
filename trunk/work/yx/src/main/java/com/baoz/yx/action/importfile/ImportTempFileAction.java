package com.baoz.yx.action.importfile;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.entity.importfile.ImportTempFile;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.opensymphony.xwork2.ActionSupport;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/importfile/importFile.jsp")
})	
public class ImportTempFileAction extends ActionSupport {

	private static Logger logger = Logger.getLogger(ImportTempFileAction.class);
	private static final long serialVersionUID = 281092169L;

	private File excelFile;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if (excelFile != null) {
			Workbook workbook = Workbook.getWorkbook(excelFile);
			Sheet sheet = workbook.getSheet(0);
			RowRuleImpl rowRule = new RowRuleImpl();
			// //////////////////////////////////////设置读取规则
			for (int i = 0; i < sheet.getRow(28).length; i++) {
				Cell cellName = sheet.getCell(i, 28);
				if (StringUtils.equals(cellName.getContents(), "合同编号")) {
					rowRule.addCellRule(new CellRuleImpl(i, "contractCode"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "项目编号")) {
					rowRule.addCellRule(new CellRuleImpl(i, "projectCode"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "项目责任部门")) {
					rowRule
							.addCellRule(new CellRuleImpl(i,
									"projectDepartment"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "项目负责人")) {
					rowRule.addCellRule(new CellRuleImpl(i, "projectPerson"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "开票确定收入标志")) {
					rowRule.addCellRule(new CellRuleImpl(i, "incomeSymbol"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "收款和开票阶段")) {
					rowRule.addCellRule(new CellRuleImpl(i, "gatheringPhase"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "发票类型")) {
					rowRule.addCellRule(new CellRuleImpl(i, "invoiceType"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "预计收款日期")) {
					rowRule.addCellRule(new CellRuleImpl(i, "gatheringDate",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "预期开票日期")) {
					rowRule.addCellRule(new CellRuleImpl(i, "invoiceDate",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "项目阶段性预计完成日期")) {
					rowRule.addCellRule(new CellRuleImpl(i, "projectPhaseDate",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "预计开票金额")) {
					rowRule.addCellRule(new CellRuleImpl(i, "invoiceMoney",
							new NumberCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "收据金额")) {
					rowRule.addCellRule(new CellRuleImpl(i, "receiptMoney",
							new NumberCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "尾款标志")) {
					rowRule.addCellRule(new CellRuleImpl(i, "lastSymbol"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "发票号")) {
					rowRule.addCellRule(new CellRuleImpl(i, "invoiceCode"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "实际到款金额")) {
					rowRule.addCellRule(new CellRuleImpl(i, "realMoney",
							new NumberCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "实际到款日期")) {
					rowRule.addCellRule(new CellRuleImpl(i, "realDate",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "合同登记年")) {
					rowRule.addCellRule(new CellRuleImpl(i, "registerYear",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "合同登记月")) {
					rowRule.addCellRule(new CellRuleImpl(i, "registerMonth",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "合同登记日")) {
					rowRule.addCellRule(new CellRuleImpl(i, "registerDate",
							new DateCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "应收款金额")) {
					rowRule.addCellRule(new CellRuleImpl(i, "realReveMoney",
							new NumberCellValueConvertor()));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "销售人员")) {
					rowRule.addCellRule(new CellRuleImpl(i, "conSaleMan"));
					continue;
				}
				if (StringUtils.equals(cellName.getContents(), "实际开票金额")) {
					rowRule.addCellRule(new CellRuleImpl(i, "realBillMoney",
							new NumberCellValueConvertor()));
					continue;
				}
				if(StringUtils.equals(cellName.getContents(), "合同名称（包含甲方合同号）")){
					rowRule.addCellRule(new CellRuleImpl(i,"conName"));
					continue;
				}
				if(StringUtils.equals(cellName.getContents(), "合同金额(含税)")){
					rowRule.addCellRule(new CellRuleImpl(i,"conMoney",
							new NumberCellValueConvertor()));
					continue;
				}
				
			}
			JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
			contractBuilder.setSheet(sheet);
			contractBuilder.setTargetClass(ExcelContractGatheringHistory.class);
			contractBuilder.setRule(29, sheet.getRows(), rowRule);

			RowResult<ExcelContractGatheringHistory>[] cons = contractBuilder
					.parseExcel();
			// 标志开始
			logger.error("********************导入excel数据到临时表中开始"
					+ System.currentTimeMillis() + "*****************");
			for (RowResult<ExcelContractGatheringHistory> rowResult : cons) {
				if (!rowResult.hasErrors()) {
					ExcelContractGatheringHistory excelContract = rowResult
							.getRowObject();
					ImportTempFile tempFile=new ImportTempFile();
					tempFile.setConId(excelContract.getContractCode());
					tempFile.setConName(excelContract.getConName());
					tempFile.setRealBillMoney(BigDecimalUtils.toBigDecial(excelContract.getRealBillMoney()));
					tempFile.setRealReveMoney(BigDecimalUtils.toBigDecial(excelContract.getRealReveMoney()));
					tempFile.setRealArriveMoney(BigDecimalUtils.toBigDecial(excelContract.getRealMoney()));
					tempFile.setConSaleMan(excelContract.getConSaleMan());
					tempFile.setConMoney(BigDecimalUtils.toBigDecial(excelContract.getConMoney()));
					tempFile.setConItemId(excelContract.getProjectCode());
					tempFile.setSign("01");
					service.save(tempFile);
				}else{
					StringBuilder errorMsg = new StringBuilder();
					for (BuildError error : rowResult.getErrors()) {
						errorMsg.append(error.getMessage());
					}
					logger.error("导入excel数据到临时表中： " + errorMsg.toString());
				}
			}
			logger.error("********************导入excel数据到临时表中结束"
					+ System.currentTimeMillis() + "*****************");

		}
		return "success";
	}
	private String getRowMessage(RowResult result) {
		// TODO Auto-generated method stub
		return " 行:" + (result.getRow() + 1) + " ";
	}

}

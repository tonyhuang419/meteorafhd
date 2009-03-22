package com.baoz.yx.action.system.hisdata;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.vo.ExcelFullBillAndReceHisImport;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name="success",value="/WEB-INF/jsp/system/hisdata/createBillInvoiceAndReveInfo.jsp")
	
})
public class CreateBillInvoiceAndReveInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123434233L;
	private static Logger logger=Logger.getLogger(CreateBillInvoiceAndReveInfoAction.class);
	private File excelFile;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;
	
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
	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}
	public void setImportHisDataService(IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}
	public String execute() throws Exception {
		if (excelFile != null) {
			Workbook workbook = Workbook.getWorkbook(excelFile);
			Sheet sheet = workbook.getSheet(0);
			RowRuleImpl rowRule = new RowRuleImpl();
			// //////////////////////////////////////设置读取规则
			rowRule.addCellRule(new CellRuleImpl("B", "contractCode"));
			rowRule.addCellRule(new CellRuleImpl("C", "projectCode"));
			rowRule.addCellRule(new CellRuleImpl("Q", "billRemain"));
			rowRule.addCellRule(new CellRuleImpl("R", "receRemain"));

			JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
			contractBuilder.setSheet(sheet);
			contractBuilder.setTargetClass(ExcelFullBillAndReceHisImport.class);
			contractBuilder.setRule(1, sheet.getRows(), rowRule);

			RowResult<ExcelFullBillAndReceHisImport>[] cons = contractBuilder
					.parseExcel();
			//标志开始
			logger.info("********************通过合同号生成开票申请，发票和收款开始"+System.currentTimeMillis()+"*****************");
			for (RowResult<ExcelFullBillAndReceHisImport> rowResult : cons) {
				if (!rowResult.hasErrors()) {
					ExcelFullBillAndReceHisImport excelContract = rowResult
							.getRowObject();
					// 检查是否合同为空
					if (StringUtils.isBlank(excelContract.getContractCode())) {
						logger.error(getRowMessage(rowResult) + ",合同号为空");
						continue;
					}
					// 获得合同主体信息表
					ContractMainInfo contract = (ContractMainInfo) service.uniqueResult("from ContractMainInfo where conId = ?",excelContract.getContractCode());
					if(contract==null){
						logger.error(getRowMessage(rowResult) + ",合同号"+excelContract.getContractCode()+"在系统中不存在");
						continue;
					}
					//集成类
					if(contract.getContractType().equals("2")){
							importHisDataService.createFullByImoprtClosedCon(
								contract, excelContract.getBillRemain() != null
										&& excelContract.getBillRemain() == 0,
								excelContract.getReceRemain() != null
										&& excelContract.getReceRemain() == 0);
					}else{
						// 工程类
						// 检查是否项目为空
						if (StringUtils.isBlank(excelContract.getProjectCode())) {
							logger.error(getRowMessage(rowResult) + ",项目号为空");
							continue;
						}
						// 获得合同主体信息表
						ContractItemMaininfo project = (ContractItemMaininfo) service.uniqueResult("from ContractItemMaininfo where conItemId = ?",excelContract.getProjectCode());
						if(contract==null){
							logger.error(getRowMessage(rowResult) + ",项目号"+excelContract.getProjectCode()+"在系统中不存在");
							continue;
						}
						importHisDataService.createFullBillAndReceProject(
								project, excelContract.getBillRemain() != null
										&& excelContract.getBillRemain() == 0,
								excelContract.getReceRemain() != null
										&& excelContract.getReceRemain() == 0);
					}
				} else {
					StringBuilder errorMsg = new StringBuilder();
					for (BuildError error : rowResult.getErrors()) {
						errorMsg.append(error.getMessage());
					}
					logger.error("全额开票收款： " + errorMsg.toString());
				}
			}
			logger.error("********************通过合同号生成开票申请，发票和收款结束"+System.currentTimeMillis()+"*****************");

		}
		return "success";
	}
	private String getRowMessage(RowResult result) {
		// TODO Auto-generated method stub
		return " 行:" + (result.getRow() + 1) + " ";
	}
	
	
}

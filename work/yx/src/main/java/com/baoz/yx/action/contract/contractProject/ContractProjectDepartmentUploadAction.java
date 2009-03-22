package com.baoz.yx.action.contract.contractProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.action.system.hisdata.ImportHistoryContractGatheringAction;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.baoz.yx.vo.ExcelContractProjectDepartment;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/contract/contractProject/projectExcelLogger.jsp"),
	@Result(name = "input", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputFail.jsp")
	})
	
	public class ContractProjectDepartmentUploadAction extends ActionSupport{
	private static Logger logger = Logger.getLogger(ContractProjectDepartmentUploadAction.class);
	
	private static final long serialVersionUID = 572146812454l ;
    
    @Autowired
	 @Qualifier("commonService")
	 private ICommonService service;
	 private File excelFile;
	 private StringBuilder loggerSuccess = new StringBuilder();
	 private StringBuilder loggerError = new StringBuilder();
	 
	 @Autowired
	 @Qualifier("contractCommonService")
	 private IContractCommonService contractCommonService;
	
	 public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}


	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}


	public String execute() throws Exception{
		 if(excelFile!=null){
			 Workbook workbook = Workbook.getWorkbook(excelFile);
			 Sheet sheet = workbook.getSheet(0);
			 RowRuleImpl rowRule = new RowRuleImpl();
			 
			 for(int i=0;i< sheet.getRow(0).length;i++){
				 Cell cellName = sheet.getCell(i, 0);
				 if(StringUtils.equals(cellName.getContents(), "协议编号")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"contractCode"));
        			 continue;
        		 }
				 if(StringUtils.equals(cellName.getContents(), "项目号")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"projectCode"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "项目组织")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"projectDepartment"));
        			 continue;
        		 }			 
			 }
			 
//			 StringBuffer str = new StringBuffer();
			 
			 JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
        	 contractBuilder.setSheet(sheet);
        	 contractBuilder.setTargetClass(ExcelContractProjectDepartment.class);
        	 contractBuilder.setRule(1, sheet.getRows(), rowRule);
        	 
        	 RowResult<ExcelContractProjectDepartment>[] cons = contractBuilder.parseExcel();
        	 for(RowResult<ExcelContractProjectDepartment> rowResult : cons){
        		 if(!rowResult.hasErrors()){
        			 ExcelContractProjectDepartment excelContract = rowResult.getRowObject();
        			 //
        			 // 检查是否合同号为空 || 检查是否部门编号为空
        			 if(StringUtils.isBlank(excelContract.getContractCode())){
        				 logger.error(getRowMessage(rowResult)+",协议编号为空");
        				 loggerError.append(getRowMessage(rowResult)+",协议编号为空<br>");
     					 continue;
        			 }else if(StringUtils.isBlank(excelContract.getProjectDepartment())){
        				 logger.error(getRowMessage(rowResult)+",项目组织为空");
        				 loggerError.append(getRowMessage(rowResult)+",项目组织为空<br>");
     					 continue;
        			 }else if(StringUtils.isBlank(excelContract.getProjectCode())){
        				 logger.error(getRowMessage(rowResult)+",项目号为空");
        				 loggerError.append(getRowMessage(rowResult)+",项目号为空<br>");
     					 continue;
        			 }
        			 else{
        				 if(StringUtils.substringBefore(excelContract.getProjectDepartment(), "_")==null){
        					 logger.error(getRowMessage(rowResult)+",项目组织没有项目编号");
        					 loggerError.append(getRowMessage(rowResult)+",项目组织没有项目编号<br>");
         					 continue;
        				 }
        				 else{
        					// 获得合同主体信息表
        	     				ContractMainInfo contract = (ContractMainInfo) service.uniqueResult("from ContractMainInfo where conId = ?", excelContract.getContractCode());
        	     				if(contract == null){
        	     					logger.error(getRowMessage(rowResult)+",合同号为"+excelContract.getContractCode()+"的合同不存在");
        	     					loggerError.append(getRowMessage(rowResult)+",合同号为"+excelContract.getContractCode()+"的合同不存在<br>");
        	     					continue;
        	     				}
        	     				else{
        	     					
        	     				     // 获得合同项目主体信息表
                     				 ContractItemMaininfo contractItem = (ContractItemMaininfo) service.uniqueResult("from " +
                     				 		"ContractItemMaininfo where itemResDept = ? and contractMainInfo = ? ", 
                     				 		StringUtils.substringBefore(excelContract.getProjectDepartment(), "_"),
                     				 		contract.getConMainInfoSid());
                     				 if(contractItem == null){
                     					logger.error(getRowMessage(rowResult)+",部门编号"+StringUtils.substringBefore(excelContract.getProjectDepartment(), "_")+",合同号"+excelContract.getContractCode()+"的项目不存在");
                     					loggerError.append(getRowMessage(rowResult)+",部门编号"+StringUtils.substringBefore(excelContract.getProjectDepartment(), "_")+",合同号"+excelContract.getContractCode()+"的项目不存在");
                   					    continue;
                     				 }
                     				 else{
                     					 List<ArrayList> itemList = service.list("from ContractItemMaininfo where conItemMinfoSid <> ? and conItemId = ?  ",contractItem.getConItemMinfoSid(),excelContract.getProjectCode());
                     					 if(itemList.size()>0){
                     						logger.error(getRowMessage(rowResult)+",项目号"+excelContract.getProjectCode()+"重复");
                         					loggerError.append(getRowMessage(rowResult)+",项目号"+excelContract.getProjectCode()+"重复<br>");
                         					continue;
                     					 }
                     					 else{
                     						////获取原来的项目号
                     						String oldItemNo=contractItem.getConItemId();
                     						contractItem.setConItemId(excelContract.getProjectCode());
                     						////获取现在的项目号
                     						String newItemNo=contractItem.getConItemId();
                         					service.update(contractItem);
                         					////修改项目主体信息编号的同时修改申购采购的项目编号和外协合同中的项目编号
                         					contractCommonService.updateItemNoOnMessageAndAssistanceContract(oldItemNo, newItemNo);
                         					logger.error(getRowMessage(rowResult)+",项目合同号成功更新数据库");
                         					loggerSuccess.append(getRowMessage(rowResult)+",项目号"+contractItem.getConItemId()+"导入成功<br>");
                         					ActionContext.getContext().put("isSuccess", "true");
                     					 }
                     				 }
        	     				}
        					 
        				 }
        				 
        			 }
        			 
        		 }
        	 }			 		
		 }
		 
		 return "success";
	 }
	 
	 
	private String getRowMessage(RowResult result) {
			// TODO Auto-generated method stub
			return " 行:" + (result.getRow() + 1) + " ";
		} 
	 
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}


	public StringBuilder getLoggerSuccess() {
		return loggerSuccess;
	}


	public void setLoggerSuccess(StringBuilder loggerSuccess) {
		this.loggerSuccess = loggerSuccess;
	}


	public StringBuilder getLoggerError() {
		return loggerError;
	}


	public void setLoggerError(StringBuilder loggerError) {
		this.loggerError = loggerError;
	}
	
}
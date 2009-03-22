package com.baoz.yx.action.contract.contractProject;

import java.io.File;

import javax.servlet.ServletRequest;

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
import com.baoz.core.service.IQueryService;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IContractService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
@Result(name = "success", value = "/WEB-INF/jsp/contract/contractProject/projectExcelLogger.jsp"),
@Result(name = "input", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputFail.jsp")
})
 
 public class ContractProjectUploadAction extends ActionSupport{
	 private static Logger logger = Logger.getLogger(ContractProjectUploadAction.class);
	
     private static final long serialVersionUID = 572146812454l ;
    
     @Autowired
 	 @Qualifier("commonService")
 	 private ICommonService service;
 	 @Qualifier("queryService")
 	 private IQueryService queryService;
	@Qualifier("contractService")
	private IContractService contractService;

 	 private ServletRequest request;
 	 private File excelFile;
 	 
 	 private StringBuilder loggerSuccess = new StringBuilder();
 	 private StringBuilder loggerError = new StringBuilder();
     
    
     public String execute() throws Exception {
    	 if(excelFile!=null){
    		 Workbook workbook = Workbook.getWorkbook(excelFile);
        	 Sheet sheet = workbook.getSheet(0);
//        	 ContractItemMaininfo im = new ContractItemMaininfo();
        	 for(int i=2; i<sheet.getRows(); i++){
        		 Cell cellConId = sheet.getCell(6, i);
        		 //EXCEL里合同号为空则continue
        		 if(StringUtils.isBlank(cellConId.getContents())){continue;}
            	 if(cellConId!=null && StringUtils.isNotBlank(cellConId.getContents())){
            		 String conId = cellConId.getContents().trim();
            		 ContractMainInfo m = (ContractMainInfo) service.uniqueResult("from ContractMainInfo obj where obj.conId='" + conId + "'");
            		 if( m != null ){
            			 Cell cellConItemId = sheet.getCell(1, i);
            			 if(StringUtils.isBlank(cellConItemId.getContents())){continue;}
            			 if(cellConItemId!=null && StringUtils.isNotBlank(cellConItemId.getContents())){
            				 String conItemId = cellConItemId.getContents().trim();
            				 ContractItemMaininfo temp = (ContractItemMaininfo) service.
                        	 uniqueResult("from ContractItemMaininfo obj where obj.conItemId= ? and obj.contractMainInfo = ? " ,conItemId,m.getConMainInfoSid());
            				 if(temp!=null&&(temp.getConItemCostSure()==null||temp.getConItemCostSure()==0||temp.getConItemCostSure()==1)){
            					 Cell cell3 = sheet.getCell(3, i);
            					 logger.info(cell3.getContents());
            					 if(StringUtils.isBlank(cell3.getContents())){
            						 temp.setRemainAssistance(0.0);  
            					 }else{
            						 temp.setRemainAssistance(Double.parseDouble(cell3.getContents())); 
            					 }     					 
            					 Cell cell4 = sheet.getCell(4, i);
            					 logger.info(cell4.getContents());
            					 if(StringUtils.isBlank(cell4.getContents())){
            						 temp.setRemainBill(0.0);   
            					 }else{
            						 temp.setRemainBill(Double.parseDouble(cell4.getContents())); 
            					 } 
            					 
            					 temp.setConItemCostSure(1L);
            					 //插入本系统内的剩余外协
            					 contractService.sumRemainAssCon(conItemId);
            					//插入本系统内的剩余发票
            					 contractService.sumRemainBIll(temp.getConItemMinfoSid());
            					 service.update(temp);
            					 logger.error("项目合同号成功更新数据库"+(i+2));
            					 loggerSuccess.append("项目号为"+conItemId+"的项目成本导入成功<br>");
            					 ActionContext.getContext().put("isSuccess", "true");
            				 }
            				 else{
            					 logger.error("数据库里该合同项目不存在或者该合同项目状态>1"+(i+2));
            					 if(temp == null){
            						 loggerError.append(" 行:"+(i+2)+"，项目号为"+conItemId+"的项目不存在<br>");
            					 }else{
            						 loggerError.append(" 行:"+(i+2)+"，项目号为"+conItemId+"的项目已经提交确认,不能导入<br>");
            					 }
            				 }
            			 }
            			 else{
            				 logger.error("EXCEL里合同项目号为空"+(i+2));
            				 loggerError.append(" 行:"+(i+2)+"，EXCEL里合同项目号为空<br>");
            			 }       			 
            		 }
            		 else{
            			 logger.error("数据库里该合同不存在"+(i+2));
            			 loggerError.append(" 行:"+(i+2)+"，合同号为"+conId+"的合同不存在<br>");
            		 }
            	 }
        	 }
    	 }   	   	 
         return "success";
 	}

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

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
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

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}


} 
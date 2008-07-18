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

 	 private ServletRequest request;
 	 private File excelFile;
 	 
 	 private StringBuilder loggerResult = new StringBuilder();
     
    
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
            		 String conId = cellConId.getContents();
            		 ContractMainInfo m = (ContractMainInfo) service.uniqueResult("from ContractMainInfo obj where obj.conId='" + conId + "'");
            		 if( m != null ){
            			 Cell cellConItemId = sheet.getCell(1, i);
            			 if(StringUtils.isBlank(cellConItemId.getContents())){continue;}
            			 if(cellConItemId!=null && StringUtils.isNotBlank(cellConItemId.getContents())){
            				 String conItemId = cellConItemId.getContents();
            				 ContractItemMaininfo temp = (ContractItemMaininfo) service.
                        	 uniqueResult("from ContractItemMaininfo obj where obj.conItemId= ? and obj.contractMainInfo = ? " ,conItemId,m.getConMainInfoSid());
            				 if(temp!=null&&(temp.getConItemCostSure()==0||temp.getConItemCostSure()==1)){
            					 Cell cell3 = sheet.getCell(3, i);
            					 if(StringUtils.isNotBlank(cell3.getContents())){
            						 temp.setRemainAssistance(Double.parseDouble(cell3.getContents()));   
            					 }     					 
            					 Cell cell4 = sheet.getCell(4, i);
            					 if(StringUtils.isNotBlank(cell4.getContents())){
            						 temp.setRemainBill(Double.parseDouble(cell4.getContents()));
            					 }
            					 
            					 temp.setConItemCostSure(1L);
            					 service.update(temp);
            					 logger.error("项目合同号成功更新数据库"+(i+2));
            					 ActionContext.getContext().put("isSuccess", "true");
            				 }
            				 else{
            					 logger.error("数据库里该合同项目不存在或者该合同项目状态>1"+(i+2));
            					 loggerResult.append(" 行:"+(i+2)+"，数据库里合同项目："+conItemId+"不存在或者该合同项目状态既不是未录入也不是已录入状态<br>");
            				 }
            			 }
            			 else{
            				 logger.error("EXCEL里合同项目号为空"+(i+2));
            				 loggerResult.append(" 行:"+(i+2)+"，EXCEL里合同项目号为空<br>");
            			 }       			 
            		 }
            		 else{
            			 logger.error("数据库里该合同不存在"+(i+2));
            			 loggerResult.append(" 行:"+(i+2)+"，数据库里合同："+conId+"不存在<br>");
            		 }
            	 }
            	 /*else{
            		 logger.error("EXCEL里合同号为空"+(i+2));
            	 }*/
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

	public StringBuilder getLoggerResult() {
		return loggerResult;
	}

	public void setLoggerResult(StringBuilder loggerResult) {
		this.loggerResult = loggerResult;
	}

} 
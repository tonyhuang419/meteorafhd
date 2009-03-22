package com.baoz.yx.action.system.hisdata;

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
import com.baoz.yx.entity.contract.ItemCostingSure;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
@Result(name = "success", value = "/WEB-INF/jsp/system/hisdata/importHistoryContractProjectRemark.jsp"),
@Result(name = "input", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputFail.jsp")
})
 
 public class ImportHistoryContractProjectRemarkAction extends ActionSupport{
	 private static Logger logger = Logger.getLogger(ImportHistoryContractProjectRemarkAction.class);
	
     private static final long serialVersionUID = 572146812454l ;
    
     @Autowired
 	 @Qualifier("commonService")
 	 private ICommonService service;
 	 @Qualifier("queryService")
 	 private IQueryService queryService;

 	 private ServletRequest request;
 	 private File excelFile;

	
     
    
     public String execute() throws Exception {
    	 if(excelFile!=null){
    		 Workbook workbook = Workbook.getWorkbook(excelFile);
        	 Sheet sheet = workbook.getSheet(0);
//        	 ContractItemMaininfo im = new ContractItemMaininfo();
        	 for(int i=11; i<sheet.getRows(); i++){
        		 Cell cellConId = sheet.getCell(7, i);
        		 //合同编号为空则continue
        		 if(StringUtils.isBlank(cellConId.getContents())){continue;}
            	 if(cellConId!=null && StringUtils.isNotBlank(cellConId.getContents())){
            		 //获得合同编号
            		 String conId = cellConId.getContents();
            		 //获得合同主体信息表
            		 ContractMainInfo m = (ContractMainInfo) service.uniqueResult("from ContractMainInfo obj where obj.conId='" + conId + "'");
            		 if( m != null ){
            			 //获得项目号
            			 Cell cellConItemId = sheet.getCell(1, i);
            			 if(StringUtils.isBlank(cellConItemId.getContents())){continue;}
            			 if(cellConItemId!=null && StringUtils.isNotBlank(cellConItemId.getContents())){
            				 //获得项目号
            				 String conItemId = cellConItemId.getContents();
            				 //获得合同项目主体信息表
            				 ContractItemMaininfo temp = (ContractItemMaininfo) service.
                        	 uniqueResult("from ContractItemMaininfo obj where obj.conItemId= ? and obj.contractMainInfo = ? " ,conItemId,m.getConMainInfoSid());
            				 if(temp!=null&&(temp.getConItemCostSure()==null||temp.getConItemCostSure()==0||temp.getConItemCostSure()==1)){
            					 //获得剩余外协
            					 Cell cell3 = sheet.getCell(3, i);
            					 if(StringUtils.isBlank(cell3.getContents())){
            						 temp.setRemainAssistance(0.0);   
            					 }else{
            						 temp.setRemainAssistance(Double.parseDouble(cell3.getContents())); 
            					 }    
            					 
            					 //获得剩余发票
            					 Cell cell4 = sheet.getCell(4, i);
            					 if(StringUtils.isBlank(cell4.getContents())){
            						 temp.setRemainBill(0.0);   
            					 }else{
            						 temp.setRemainBill(Double.parseDouble(cell4.getContents())); 
            					 } 
            					 
            					 temp.setConItemCostSure(1L);
            					 service.update(temp);
            					 //获得反馈信息
            					 Cell cell13 = sheet.getCell(13, i);
            					 Cell cell14 = sheet.getCell(14, i);
            					 if(StringUtils.isNotBlank(cell13.getContents())||StringUtils.isNotBlank(cell14.getContents())){
            						 
                					 if(StringUtils.isBlank(cell13.getContents())){
                						 ItemCostingSure s = new ItemCostingSure();
                    					 s.setContractItemMaininfo(temp.getConItemMinfoSid());
                						 s.setFeedbackInfo("未开票具体说明:"+StringUtils.defaultString(cell14.getContents()));
                						 service.save(s); 
                					 }
                					 else if(StringUtils.isBlank(cell14.getContents())){
                						 ItemCostingSure s = new ItemCostingSure();
                    					 s.setContractItemMaininfo(temp.getConItemMinfoSid());
                						 s.setFeedbackInfo("未签字原因:"+StringUtils.defaultString(cell13.getContents()));
                						 service.save(s); 
                					 }else{
                						 ItemCostingSure s = new ItemCostingSure();
                    					 s.setContractItemMaininfo(temp.getConItemMinfoSid());
                						 s.setFeedbackInfo("未签字原因:"+StringUtils.defaultString(cell13.getContents())+"；未开票具体说明:"+StringUtils.defaultString(cell14.getContents()));
                						 service.save(s); 
                					 }
                					 
            					 }
            					 else{
            						 logger.error("EXCEL里该反馈信息为空"+(i+11));
            					 }
            					// ActionContext.getContext().put("isSuccess", "true");
            				 }
            				 else{
            					 logger.error("数据库里该合同项目不存在或者该合同项目状态>1"+(i+11));
            				 }
            			 }
            			 else{
            				 logger.error("EXCEL里合同项目号为空"+(i+11));
            			 }       			 
            		 }
            		 else{
            			 logger.error("数据库里该合同不存在"+(i+11));
            		 }
            	 }
            	 /*else{
            		 logger.error("EXCEL里合同号为空"+(i+11));
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

} 
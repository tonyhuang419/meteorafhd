package com.baoz.yx.action.system.hisdata;

import java.io.File;
import java.math.BigDecimal;

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
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.opensymphony.xwork2.ActionSupport;

@Results({
@Result(name = "success", value = "/WEB-INF/jsp/system/hisdata/importHistoryContractGathering.jsp"),
@Result(name = "input", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputFail.jsp")
})
 
 public class ImportHistoryContractGatheringAction extends ActionSupport{
	 private static Logger logger = Logger.getLogger(ImportHistoryContractGatheringAction.class);
	
     private static final long serialVersionUID = 572146812454l ;
    
     @Autowired
 	 @Qualifier("commonService")
 	 private ICommonService service;
 	 private File excelFile;
    
    
     public String execute() throws Exception {
    	 if(excelFile!=null){
    		 Workbook workbook = Workbook.getWorkbook(excelFile);
        	 Sheet sheet = workbook.getSheet(0);
        	 RowRuleImpl rowRule = new RowRuleImpl();
             ////////////////////////////////////////设置读取规则      	 
        	 for(int i=0;i< sheet.getRow(28).length;i++){
        		 Cell cellName = sheet.getCell(i, 28);
        		 if(StringUtils.equals(cellName.getContents(), "合同编号")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"contractCode"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "项目编号")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"projectCode"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "项目责任部门")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"projectDepartment"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "项目负责人")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"projectPerson"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "开票确定收入标志")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"incomeSymbol"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "收款和开票阶段")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"gatheringPhase"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "发票类型")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"invoiceType"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "预计收款日期")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"gatheringDate",new DateCellValueConvertor()));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "预期开票日期")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"invoiceDate",new DateCellValueConvertor()));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "项目阶段性预计完成日期")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"projectPhaseDate",new DateCellValueConvertor()));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "预计开票金额")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"invoiceMoney",new NumberCellValueConvertor()));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "收据金额")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"receiptMoney",new NumberCellValueConvertor()));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "尾款标志")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"lastSymbol"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "发票号")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"invoiceCode"));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "实际到款金额")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"realMoney",new NumberCellValueConvertor()));
        			 continue;
        		 }
        		 if(StringUtils.equals(cellName.getContents(), "实际到款日期")){
        			 rowRule.addCellRule(new CellRuleImpl(i,"realDate",new DateCellValueConvertor()));
        			 continue;
        		 }      		 
        	 }
        	 JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
        	 contractBuilder.setSheet(sheet);
        	 contractBuilder.setTargetClass(ExcelContractGatheringHistory.class);
        	 contractBuilder.setRule(29, sheet.getRows(), rowRule);
        	 
        	 RowResult<ExcelContractGatheringHistory>[] cons = contractBuilder.parseExcel();
        	 for(RowResult<ExcelContractGatheringHistory> rowResult : cons){
        		 if(!rowResult.hasErrors()){
        			 ExcelContractGatheringHistory excelContract = rowResult.getRowObject();
        			 //
        			 // 检查是否合同为空
        			 if(StringUtils.isBlank(excelContract.getContractCode())){
        				 logger.error(getRowMessage(rowResult)+",合同号为空");
     					 continue;
        			 }
        			// 获得合同主体信息表
     				ContractMainInfo contract = (ContractMainInfo) service.uniqueResult("from ContractMainInfo where conId = ?", excelContract.getContractCode());
     				if(contract == null){
     					logger.error(getRowMessage(rowResult)+",合同号为"+excelContract.getContractCode()+"不存在");
     					continue;
     				}
     				else{
     				    // 获得合同项目主体信息表
         				ContractItemMaininfo contractItem = (ContractItemMaininfo) service.uniqueResult("from ContractItemMaininfo where conItemId = ? and contractMainInfo = ? ", excelContract.getProjectCode(),contract.getConMainInfoSid());
         				if(contractItem == null){
         					contractItem = new ContractItemMaininfo();
         					contractItem.setContractMainInfo(contract.getConMainInfoSid());
             				contractItem.setConItemId(excelContract.getProjectCode());
             				YXTypeManage tm = (YXTypeManage) service.uniqueResult("from YXTypeManage where t.typeBig=1018 and typeName = ?", excelContract.getProjectDepartment());
             				if(tm!=null){
             					contractItem.setItemResDept(tm.getTypeSmall());
             				}
             				else{
             					logger.error(getRowMessage(rowResult)+",工程责任部门"+excelContract.getProjectDepartment()+"不存在");
             				}
             				contractItem.setItemResDeptP(excelContract.getProjectPerson());
             				service.save(contractItem);
         				}
         				else{
         					contractItem.setContractMainInfo(contract.getConMainInfoSid());
             				contractItem.setConItemId(excelContract.getProjectCode());
             				YXTypeManage tm = (YXTypeManage) service.uniqueResult("from YXTypeManage where t.typeBig=1018 and typeName = ?", excelContract.getProjectDepartment());
             				if(tm!=null){
             					contractItem.setItemResDept(tm.getTypeSmall());
             				} 
             				else{
             					logger.error(getRowMessage(rowResult)+",工程责任部门"+excelContract.getProjectDepartment()+"不存在");
             				}
             				contractItem.setItemResDeptP(excelContract.getProjectPerson());
             				service.update(contractItem);
         				}     				
         				
         				// 获得合同项目阶段表
         				ContractItemStage itemStage = (ContractItemStage) service.uniqueResult("from ContractItemStage where ItemStageName = ? and contractMainSid = ? ", excelContract.getGatheringPhase(),contract.getConMainInfoSid());
         				if(itemStage == null){
         					itemStage = new ContractItemStage();
         					itemStage.setContractMainSid(contract.getConMainInfoSid());
         					itemStage.setItemStageName(excelContract.getGatheringPhase());
         					if(StringUtils.equals(excelContract.getLastSymbol(), "Y")){
         						itemStage.setLastAmount(true);
         					}
         					else{
         						itemStage.setLastAmount(false);
         					}   					
         					service.save(itemStage);
         				}
         				else{
         					itemStage.setContractMainSid(contract.getConMainInfoSid());
             				itemStage.setItemStageName(excelContract.getGatheringPhase());
             				if(StringUtils.equals(excelContract.getLastSymbol(), "Y")){
         						itemStage.setLastAmount(true);
         					}
         					else{
         						itemStage.setLastAmount(false);
         					}  
         					service.update(itemStage);
         				}       				
         				
     				    // 获得实际合同开票收款计划表
     					RealContractBillandRecePlan realPlan = (RealContractBillandRecePlan) service.uniqueResult(
     							"from RealContractBillandRecePlan where conMainInfoSid = ? and contractItemMaininfo = ? and " +
     							"conItemStage = ?", contract.getConMainInfoSid(),contractItem.getConItemMinfoSid(),itemStage.getConIStageSid());
     					if(realPlan == null){
     						realPlan = new RealContractBillandRecePlan();
         					realPlan.setConMainInfoSid(contract.getConMainInfoSid());
         					realPlan.setConItemStage(itemStage.getConIStageSid());
         					realPlan.setContractItemMaininfo(contractItem.getConItemMinfoSid());
         					// 发票类型
         					if(StringUtils.equals(excelContract.getInvoiceType(),"普票")){
         						YXTypeManage it = (YXTypeManage) service.uniqueResult("from YXTypeManage where typeName = ?", "普票");
         						if(it!=null){
         							realPlan.setBillType(it.getTypeSmall());
         						}
         						else{
                 					logger.error(getRowMessage(rowResult)+",票据类型"+excelContract.getInvoiceType()+"在类型管理表中不存在");
                 				}
         					}
         					if(StringUtils.equals(excelContract.getInvoiceType(),"增票")){
         						YXTypeManage it = (YXTypeManage) service.uniqueResult("from YXTypeManage where typeName = ?", "增票");
         						if(it!=null){
         							realPlan.setBillType(it.getTypeSmall());
         						}
         						else{
                 					logger.error(getRowMessage(rowResult)+",票据类型"+excelContract.getInvoiceType()+"在类型管理表中不存在");
                 				}
         					}
         					if(StringUtils.equals(excelContract.getInvoiceType(),"收据")){
         						YXTypeManage it = (YXTypeManage) service.uniqueResult("from YXTypeManage where typeName = ?", "收据");
         						if(it!=null){
         							realPlan.setBillType(it.getTypeSmall());
         						}
         						else{
                 					logger.error(getRowMessage(rowResult)+",票据类型"+excelContract.getInvoiceType()+"在类型管理表中不存在");
                 				}
         					}
         					// 预计收款日期
         					realPlan.setRealPredReceDate(excelContract.getGatheringDate());
         					// 预期开票日期
         					realPlan.setRealPredBillDate(excelContract.getInvoiceDate());
         					// 项目阶段性预计完成日期
         					
         					// 预计开票金额
         					// 收据金额
         					if(StringUtils.equals(excelContract.getInvoiceType(),"收据")){
         						realPlan.setRealBillAmount(BigDecimal.valueOf(excelContract.getReceiptMoney()));
         					}
         					if((StringUtils.equals(excelContract.getInvoiceType(),"普票")||StringUtils.equals(excelContract.getInvoiceType(),"增票"))&&excelContract.getReceiptMoney()!=null)
         					{
         						realPlan.setRealBillAmount(BigDecimal.valueOf(excelContract.getInvoiceMoney()));
         					}
         					
         					// 开票确定收入标志
         					if(StringUtils.equals(excelContract.getIncomeSymbol(), "Y")){
         						realPlan.setBillSureSign(true);
         					}
         					else{
         						realPlan.setBillSureSign(false);
         					} 
         					service.save(realPlan);
     					}
     					else{
         					realPlan.setConMainInfoSid(contract.getConMainInfoSid());
         					realPlan.setConItemStage(itemStage.getConIStageSid());
         					realPlan.setContractItemMaininfo(contractItem.getConItemMinfoSid());
         					// 发票类型
         					if(StringUtils.equals(excelContract.getInvoiceType(),"普票")){
         						YXTypeManage it = (YXTypeManage) service.uniqueResult("from YXTypeManage where typeName = ?", "普票");
         						if(it!=null){
         							realPlan.setBillType(it.getTypeSmall());
         						}	
         					}
         					if(StringUtils.equals(excelContract.getInvoiceType(),"增票")){
         						YXTypeManage it = (YXTypeManage) service.uniqueResult("from YXTypeManage where typeName = ?", "增票");
         						if(it!=null){
         							realPlan.setBillType(it.getTypeSmall());
         						}	
         					}
         					if(StringUtils.equals(excelContract.getInvoiceType(),"收据")){
         						YXTypeManage it = (YXTypeManage) service.uniqueResult("from YXTypeManage where typeName = ?", "收据");
         						if(it!=null){
         							realPlan.setBillType(it.getTypeSmall());
         						}	
         					}
         					// 预计收款日期
         					realPlan.setRealPredReceDate(excelContract.getGatheringDate());
         					// 预期开票日期
         					realPlan.setRealPredBillDate(excelContract.getInvoiceDate());
         					// 项目阶段性预计完成日期
         					
         					// 预计开票金额			
         					// 收据金额
         					if(StringUtils.equals(excelContract.getInvoiceType(),"收据")&&excelContract.getReceiptMoney()!=null){
         						realPlan.setRealBillAmount(BigDecimal.valueOf(excelContract.getReceiptMoney()));
         					}
         					if((StringUtils.equals(excelContract.getInvoiceType(),"普票")||StringUtils.equals(excelContract.getInvoiceType(),"增票"))&&excelContract.getReceiptMoney()!=null)
         					{
         						realPlan.setRealBillAmount(BigDecimal.valueOf(excelContract.getInvoiceMoney()));
         					}
         					
         					// 开票确定收入标志
         					if(StringUtils.equals(excelContract.getIncomeSymbol(), "Y")){
         						realPlan.setBillSureSign(true);
         					}
         					else{
         						realPlan.setBillSureSign(false);
         					} 
         					service.update(realPlan);
     					}
     				    // 获得发票
     				
     				}
     				
        		 }else{
     				StringBuilder errorMsg = new StringBuilder();
    				for (BuildError error : rowResult.getErrors()) {
    					errorMsg.append(error.getMessage());
    				}
    				logger.error("工程类： "+errorMsg.toString());
    			}
        	 }
        	 
    	 }   	   	 
         return "success";
 	}

	private String getRowMessage(RowResult result) {
		// TODO Auto-generated method stub
		return " 行:" + (result.getRow() + 1) + " ";
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


} 
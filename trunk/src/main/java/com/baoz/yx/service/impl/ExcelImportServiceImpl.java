package com.baoz.yx.service.impl;

import jxl.Sheet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.StringArrayCellValueConvertor;
import com.baoz.yx.excel.rule.CellRule;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.IExcelImportService;
import com.baoz.yx.utils.YxConstants;
import com.baoz.yx.vo.ExcelEngineeringHistoryContract;
import com.baoz.yx.vo.ExcelHistoryAssistanceContract;
import com.baoz.yx.vo.ExcelIntegrationHistoryContract;

/**
 * 类IExcelImportServiceImpl.java的实现描述：导入excel数据
 * @author xurong Jul 1, 2008 4:49:49 PM
 */
@Service("excelImportService")
@Transactional
public class ExcelImportServiceImpl implements IExcelImportService {
	private Logger logger =  Logger.getLogger(ExcelImportServiceImpl.class);
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao	commonDao;
	
	public void importHistoryAssistanceContract(Sheet assistanceSheet) {
		RowRuleImpl rowRule = new RowRuleImpl();
		////////////////////////////////////////////////设置读取规则
		rowRule.addCellRule(new CellRuleImpl(1, "assistanceContractCode"));
		rowRule.addCellRule(new CellRuleImpl(2, "mainProjectCode"));
		rowRule.addCellRule(new CellRuleImpl(3, "mainProjectName"));
		rowRule.addCellRule(new CellRuleImpl(4, "supplyName"));
		rowRule.addCellRule(new CellRuleImpl(5, "totalMoney", new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(6, "contractMaker"));
		rowRule.addCellRule(new CellRuleImpl(7, "signDate", new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(8, "expiration", new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl(9, "payMethod"));
		rowRule.addCellRule(new CellRuleImpl(11, "remark"));
		////////////////////////////////////////////////
		JExcelRowObjectBuilder assistanceContractBuilder = new JExcelRowObjectBuilder();
		assistanceContractBuilder.setSheet(assistanceSheet);
		assistanceContractBuilder.setTargetClass(ExcelHistoryAssistanceContract.class);
		assistanceContractBuilder.setRule(1, assistanceSheet.getRows(), rowRule);
		// 解析excel
		RowResult<ExcelHistoryAssistanceContract>[] cons = assistanceContractBuilder.parseExcel();
		// 处理外协合同
		BeanWrapperImpl warp = new BeanWrapperImpl(false);
		CellRule[] cellRuls = rowRule.getCellRules();
		for (RowResult<ExcelHistoryAssistanceContract> result : cons) {
			if(!result.hasErrors()){
				ExcelHistoryAssistanceContract ec = result.getRowObject();
				AssistanceContract ac = (AssistanceContract) commonDao.uniqueResult("from AssistanceContract ac where ac.assistanceId = '"+ec.getAssistanceContractCode()+"'");
				if(ac == null){
					ac = new AssistanceContract();
				}
				ac.setImportFromFile(Boolean.TRUE);
				ac.setIs_active("1");
				ac.setAssistanceContractType("0");
				ac.setAssistanceId(ec.getAssistanceContractCode());
				ac.setMainProjectId(ec.getMainProjectCode());
				ac.setAssistanceName(ec.getMainProjectName());
				ac.setMainProjectName(ec.getMainProjectName());
				ac.setContractMoney(ec.getTotalMoney());
				ac.setContractDate(ec.getSignDate());
				ac.setEndDate(ec.getExpiration());
				ac.setContractContent(StringUtils.defaultString(ec.getPayMethod())+StringUtils.defaultString(ec.getRemark()));
				String hql = "from SupplierInfo si where si.supplierName = '"+ec.getSupplyName()+"'";
				SupplierInfo si = (SupplierInfo)commonDao.uniqueResult(hql);
				if(si != null){
					ac.setSupplyId(si.getSupplierid());
				}else{
					logger.warn("外协类： "+getRowMessage(result)+"供应商["+ec.getSupplyName()+"]不存在，请在供应商表中添加");
				}
				if(ac.getId() != null){
					commonDao.update(ac);
				}else{
					commonDao.save(ac);
				}
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : result.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				logger.error("外协类： "+errorMsg.toString());
			}
		}
	}

	public void importHistoryEngineeringContract(Sheet engineeringSheet) {
		RowRuleImpl rowRule = new RowRuleImpl();
		////////////////////////////////////////设置读取规则
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
		/////////////////////////////////////////////////////////////////////
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(engineeringSheet);
		contractBuilder.setTargetClass(ExcelEngineeringHistoryContract.class);
		contractBuilder.setRule(1, engineeringSheet.getRows(), rowRule);
		// 解析excel
		RowResult<ExcelEngineeringHistoryContract>[] cons = contractBuilder.parseExcel();
		// 处理合同
		for (RowResult<ExcelEngineeringHistoryContract> rowResult : cons) {
			if(!rowResult.hasErrors()){
				ExcelEngineeringHistoryContract excelContract = rowResult.getRowObject();
				// 保存合同主体
				// 检查是否合同为空
				if(StringUtils.isBlank(excelContract.getContractCode())){
					logger.error("工程类： "+getRowMessage(rowResult)+",合同号为空");
					continue;
				}
				// 获得合同
				ContractMainInfo contract = (ContractMainInfo) commonDao.uniqueResult("from ContractMainInfo where conId = ?", excelContract.getContractCode());
				if(contract == null){
					contract = new ContractMainInfo();
				}
				// 获得other信息
				ContractOtherInfo otherInfo = null;
				if(contract.getConMainInfoSid() != null){
					otherInfo = (ContractOtherInfo) commonDao.uniqueResult("from ContractOtherInfo where contractMainSid = ?", contract.getConMainInfoSid());
				}
				if(otherInfo == null){
					otherInfo = new ContractOtherInfo();
				}
				// 甲方合同号
				contract.setPartyAConId(StringUtils.join(excelContract.getJiaContractCodes(),","));
				// 甲方项目号
				contract.setPartyAProId(StringUtils.join(excelContract.getJiaProjectCodes(),","));
				// 合同号
				contract.setConId(excelContract.getContractCode());
				// 项目名称 -- 合同名称
				contract.setConName(excelContract.getContractName());
				// 客户名称
				if(StringUtils.isNotBlank(excelContract.getClientName())){
					YXClientCode client =  (YXClientCode) commonDao.uniqueResult("from YXClientCode where name = ?", excelContract.getClientName());
					if(client != null){
						contract.setConCustomer(client.getId());
					}else{
						logger.warn("工程类： "+getRowMessage(rowResult)+"客户["+excelContract.getClientName()+"]不存在，请在客户表中添加");
					}
				}else{
					logger.warn("工程类： "+getRowMessage(rowResult)+"客户名称为空");
				}
				// 合同金额（含税）
				contract.setConTaxTamount(excelContract.getTaxContractAmount());
				// 合同金额（不含税）
				
				// 预决算(Y/N)
				contract.setFinalAccount("Y".equals(excelContract.getPreDecide())?"1":"0");
				// 设备金额
				// 非设备金额
				// 软件开发金额
				// 服务外包费（含税）
				// 服务外包费（不含税）
				// 合同经办人
				if(StringUtils.isNotBlank(excelContract.getSellerName())){
					Employee employee = (Employee) commonDao.uniqueResult("from Employee where name = ?", excelContract.getSellerName());
					if(employee != null){
						contract.setSaleMan(employee.getId());
					}else{
						logger.warn("工程类： "+getRowMessage(rowResult)+"合同经办人["+excelContract.getSellerName()+"]不存在，请在员工表中添加");
					}
				}else{
					logger.warn("工程类： "+getRowMessage(rowResult)+"合同经办人为空");
				}
				// 合同签定日期
				contract.setConSignDate(excelContract.getContractSignDate());
				if(contract.getConStartDate() == null){
					contract.setConStartDate(excelContract.getContractSignDate());
				}
				// 合同有效期
				contract.setConEndDate(excelContract.getContractEndDate());
				// 技术开发(Y/N)
				// 股份/股份其他/集团/宝钢外
				// 钢铁/非钢铁(Y/N)
				// 工程/科研
				// 中标/非中标(Y/N)
				contract.setConBid("Y".equals(excelContract.getBidContract())?Boolean.TRUE:Boolean.FALSE);
				// 合同及概况说明提交日期
				
				// 备注
				otherInfo.setOtherRemarks(excelContract.getContractMemo());
				//保存合同
				// 草稿合同
				contract.setConState(0L);
				contract.setImportFromFile(Boolean.TRUE);
				contract.setContractType(YxConstants.ENGINEERING_CONTRACT);
				commonDao.saveOrUpdate(contract);
				otherInfo.setContractMainSid(contract.getConMainInfoSid());
				otherInfo.setImportFromFile(Boolean.TRUE);
				commonDao.saveOrUpdate(otherInfo);
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : rowResult.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				logger.error("工程类： "+errorMsg.toString());
			}
		}
		
	}

	public void importHistoryIntegrationContract(Sheet engineeringSheet) {
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
		///////////////////////////////////////////
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(engineeringSheet);
		contractBuilder.setTargetClass(ExcelIntegrationHistoryContract.class);
		contractBuilder.setRule(1, engineeringSheet.getRows(), rowRule);
		RowResult<ExcelIntegrationHistoryContract>[] cons = contractBuilder.parseExcel();
		// 解析excel
		// 处理合同
		for (RowResult<ExcelIntegrationHistoryContract> rowResult : cons) {
			if(!rowResult.hasErrors()){
				ExcelIntegrationHistoryContract excelContract = rowResult.getRowObject();
				// 保存合同主体
				// 检查是否合同为空
				if(StringUtils.isBlank(excelContract.getContractCode())){
					logger.error("集成类： "+getRowMessage(rowResult)+",合同号为空");
					continue;
				}
				// 获得合同
				ContractMainInfo contract = (ContractMainInfo) commonDao.uniqueResult("from ContractMainInfo where conId = ?", excelContract.getContractCode());
				if(contract == null){
					contract = new ContractMainInfo();
				}
				// 获得other信息
				ContractOtherInfo otherInfo = null;
				if(contract.getConMainInfoSid() != null){
					otherInfo = (ContractOtherInfo) commonDao.uniqueResult("from ContractOtherInfo where contractMainSid = ?", contract.getConMainInfoSid());
				}
				if(otherInfo == null){
					otherInfo = new ContractOtherInfo();
				}
				// 甲方合同号
				contract.setPartyAConId(StringUtils.join(excelContract.getJiaContractCodes(),","));
				// 甲方项目号
				contract.setPartyAProId(StringUtils.join(excelContract.getJiaProjectCodes(),","));
				// 合同号
				contract.setConId(excelContract.getContractCode());
				// 项目名称 -- 合同名称
				contract.setConName(excelContract.getContractName());
				// 客户名称
				if(StringUtils.isNotBlank(excelContract.getClientName())){
					YXClientCode client =  (YXClientCode) commonDao.uniqueResult("from YXClientCode where name = ?", excelContract.getClientName());
					if(client != null){
						contract.setConCustomer(client.getId());
					}else{
						logger.warn("集成类： "+getRowMessage(rowResult)+"客户["+excelContract.getClientName()+"]不存在，请在客户表中添加");
					}
				}else{
					logger.warn("集成类： "+getRowMessage(rowResult)+"客户名称为空");
				}
				// 合同金额（含税）
				contract.setConTaxTamount(excelContract.getTaxContractAmount());
				// 合同金额（不含税）
				
				// 合同经办人
				if(StringUtils.isNotBlank(excelContract.getSellerName())){
					Employee employee = (Employee) commonDao.uniqueResult("from Employee where name = ?", excelContract.getSellerName());
					if(employee != null){
						contract.setSaleMan(employee.getId());
					}else{
						logger.warn("集成类： "+getRowMessage(rowResult)+"合同经办人["+excelContract.getSellerName()+"]不存在，请在员工表中添加");
					}
				}else{
					logger.warn("集成类： "+getRowMessage(rowResult)+"合同经办人为空");
				}
				// 合同签定日期
				contract.setConSignDate(excelContract.getContractSignDate());
				if(contract.getConStartDate() == null){
					contract.setConStartDate(excelContract.getContractSignDate());
				}
				// 股份/股份其他/集团/宝钢外
				// 钢铁/非钢铁(Y/N)

				// 备注
				otherInfo.setOtherRemarks(excelContract.getContractMemo());
				//保存合同
				//草稿合同
				contract.setConState(0L);
				contract.setImportFromFile(Boolean.TRUE);
				contract.setContractType(YxConstants.INTEGRATION_CONTRACT);
				commonDao.saveOrUpdate(contract);
				otherInfo.setContractMainSid(contract.getConMainInfoSid());
				otherInfo.setImportFromFile(Boolean.TRUE);
				commonDao.saveOrUpdate(otherInfo);
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : rowResult.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				logger.error("集成类： "+errorMsg.toString());
			}
		}
	}
	
	private String getRowMessage(RowResult result) {
		return " 行:" + (result.getRow() + 1) + " ";
	}
	
	private boolean isProjectCodeValid(String[] projectCodes) {
		if (projectCodes != null) {
			for (String pcode : projectCodes) {
				if(pcode != null && pcode.length() != 8){
					return false;
				}
			}
		}
		return true;
	}
}

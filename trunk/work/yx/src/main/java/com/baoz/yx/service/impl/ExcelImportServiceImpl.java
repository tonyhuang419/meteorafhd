package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
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
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.ExcelUtils;
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
				//外协
				ExcelHistoryAssistanceContract ec = result.getRowObject();
				AssistanceContract ac = (AssistanceContract) commonDao.uniqueResult("from AssistanceContract ac where ac.assistanceId = '"+ec.getAssistanceContractCode()+"'");
				if(ac == null){
					ac = new AssistanceContract();
				}
				//项目号
				if(StringUtils.isNotBlank(ec.getMainProjectCode())){
					ContractItemMaininfo itemMain = (ContractItemMaininfo) commonDao.uniqueResult("from ContractItemMaininfo where conItemId = ?", ec.getMainProjectCode());
					if(itemMain != null){
						ac.setContractId(itemMain.getContractMainInfo());
					}else{
						logger.error("外协类： "+getRowMessage(result)+"项目号为"+ec.getMainProjectCode()+"的项目不存在");
					}
				}else{
					logger.error("外协类： "+getRowMessage(result)+"项目号为空");
				}
				//
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
		/////////////////////////////////////////////////////////////////////
		JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
		contractBuilder.setSheet(engineeringSheet);
		contractBuilder.setTargetClass(ExcelEngineeringHistoryContract.class);
		contractBuilder.setRule(1, engineeringSheet.getRows(), rowRule);
		// 解析excel
		RowResult<ExcelEngineeringHistoryContract>[] cons = contractBuilder.parseExcel();
		// 处理合同
		int totalCount = cons.length;
		int successCount = 0;
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
				if(StringUtils.isBlank(excelContract.getContractName())){
					contract.setConName("合同编号："+excelContract.getContractCode());
				}
				// 客户名称
				if(StringUtils.isNotBlank(excelContract.getClientName())){
					YXClientCode client =  (YXClientCode) commonDao.uniqueResult("from YXClientCode where fullName = ?", excelContract.getClientName());
					if(client != null){
						contract.setConCustomer(client.getId());
						contract.setBillCustomer(client.getId());
						contract.setItemCustomer(client.getId());
					}else{
						logger.warn("工程类： "+getRowMessage(rowResult)+"客户["+excelContract.getClientName()+"]不存在，请在客户表中添加");
						logger.warn("工程类："+StringUtils.leftPad("", 200," ")+StringUtils.leftPad(excelContract.getClientName(), 50," ")+","+excelContract.getSellerName());
					}
				}else{
					logger.warn("工程类： "+getRowMessage(rowResult)+"客户名称为空");
				}
				// 项目主负责部门
				List<ImportDepartment> mainDepList = getDepartmentInfo(excelContract.getProjectCode(),excelContract.getMainProjectDepartment(),excelContract.getTaxContractAmount(),rowResult);
				if(mainDepList.size() > 0){
					ImportDepartment mainDepartment = mainDepList.get(0);
					YXTypeManage department = (YXTypeManage) commonDao.uniqueResult("from YXTypeManage where typeName = ? and typeBig = 1018 and is_active = '1' ", mainDepartment.getDepartmentName());
					if(department != null){
						contract.setMainItemDept(department.getTypeSmall());
					}
				}
				// 货币单位(人民币)
				contract.setCopeck("CNY");
				// 基准汇率
				contract.setBaserate(1.0);
				// 基准（含税）
				contract.setStandard("1");
				// 合同金额（含税）
				contract.setConTaxTamount(excelContract.getTaxContractAmount());
				// 合同金额（不含税）
				contract.setConNoTaxTamount(excelContract.getNoTaxContractAmount());
				// 预决算(Y/N)
				contract.setFinalAccount("Y".equals(excelContract.getPreDecide())?"1":"0");
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
				if("Y".equals(excelContract.getTechnicalContract())){
					contract.setConType("1");
				}else{
					// 其他
					contract.setConType("17");
				}
				// 股份/股份其他/集团/宝钢外 -- 在客户管理页面里维护
				// 钢铁/非钢铁(Y/N) -- 在客户管理页面里维护
				// 工程/科研
				if("工程".equals(excelContract.getContractClientProjCategory())){
					contract.setCustomereventtype("1");
				}else if("科研".equals(excelContract.getContractClientProjCategory())){
					contract.setCustomereventtype("3");
				}else{
					//其他
					contract.setCustomereventtype("4");
				}
				// 中标/非中标(Y/N)
				contract.setConBid("Y".equals(excelContract.getBidContract())?Boolean.TRUE:Boolean.FALSE);
				// 退税
				contract.setConDrawback(Boolean.FALSE);
				// 纳入年度运维合同
				contract.setIntoYearCon(Boolean.FALSE);
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
				//费用组成
				//设置默认值
				if(excelContract.getTaxContractAmount() == null){
					excelContract.setTaxContractAmount(0.0);
				}
				if(excelContract.getNoTaxContractAmount() == null){
					excelContract.setNoTaxContractAmount(0.0);
				}
				if(excelContract.getDeviceAmount() == null){
					excelContract.setDeviceAmount(0.0);
				}
				if(excelContract.getNoDeviceAmount() == null){
					excelContract.setNoDeviceAmount(0.0);
				}
				if(excelContract.getSoftDevelopAmount() == null){
					excelContract.setSoftDevelopAmount(0.0);
				}
				if(excelContract.getTaxOutsourcingAmount() == null){
					excelContract.setTaxOutsourcingAmount(0.0);
				}
				if(excelContract.getNoTaxOutsourcingAmount() == null){
					excelContract.setNoTaxOutsourcingAmount(0.0);
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				// 费用组成
				List<ContractMaininfoPart> partList = new ArrayList<ContractMaininfoPart>();
				// 设备金额
				if(excelContract.getDeviceAmount() != 0.0){
					ContractMaininfoPart devicePart = (ContractMaininfoPart) commonDao.uniqueResult("from ContractMaininfoPart where contractmainid = ? and moneytype = '101' ", contract.getConMainInfoSid());
					if(devicePart == null){
						devicePart = new ContractMaininfoPart();
					}
					devicePart.setContractmainid(contract.getConMainInfoSid());
					devicePart.setMoney(excelContract.getDeviceAmount());
					//设备费
					devicePart.setMoneytype("101");
					//增值税发票
					devicePart.setTicketType("2");
					commonDao.saveOrUpdate(devicePart);
					partList.add(devicePart);
				}
				// 软件开发金额
				if(excelContract.getSoftDevelopAmount() != 0.0){
					ContractMaininfoPart developPart = (ContractMaininfoPart) commonDao.uniqueResult("from ContractMaininfoPart where contractmainid = ? and moneytype = '002' ", contract.getConMainInfoSid());
					if(developPart == null){
						developPart = new ContractMaininfoPart();
					}
					developPart.setContractmainid(contract.getConMainInfoSid());
					developPart.setMoney(excelContract.getSoftDevelopAmount());
					//软件开发费
					developPart.setMoneytype("002");
					//服务业普通发票
					developPart.setTicketType("1");
					commonDao.saveOrUpdate(developPart);
					partList.add(developPart);
				}
				// 服务外包费（含税）
				// 服务外包费（不含税）
				if(excelContract.getTaxOutsourcingAmount() != 0.0){
					String moneyType = null;
					String ticketType = null;
					if(excelContract.getTaxOutsourcingAmount().equals(excelContract.getNoTaxOutsourcingAmount())){
						//服务外包费（普）
						moneyType = "007";
						//服务业普通发票
						ticketType = "1";
					}else{
						//服务外包费（增）
						moneyType = "006";
						//增值税发票
						ticketType = "2";
					}
					ContractMaininfoPart outsourcingPart = (ContractMaininfoPart) commonDao.uniqueResult("from ContractMaininfoPart where contractmainid = ? and moneytype = '"+moneyType+"' ", contract.getConMainInfoSid());
					if(outsourcingPart == null){
						outsourcingPart = new ContractMaininfoPart();
					}
					outsourcingPart.setContractmainid(contract.getConMainInfoSid());
					outsourcingPart.setMoney(excelContract.getTaxOutsourcingAmount());
					//服务外包费
					outsourcingPart.setMoneytype(moneyType);
					//发票类型
					outsourcingPart.setTicketType(ticketType);
					commonDao.saveOrUpdate(outsourcingPart);
					partList.add(outsourcingPart);
				}
				//非设备费
				//非设备费 - 软件开发费 - 服务外包费
				Double otherAmount = excelContract.getNoDeviceAmount() - excelContract.getSoftDevelopAmount() - excelContract.getTaxOutsourcingAmount();
				if(otherAmount > 0.0){
					ContractMaininfoPart otherPart = (ContractMaininfoPart) commonDao.uniqueResult("from ContractMaininfoPart where contractmainid = ? and moneytype = '009' ", contract.getConMainInfoSid());
					if(otherPart == null){
						otherPart = new ContractMaininfoPart();
					}
					otherPart.setContractmainid(contract.getConMainInfoSid());
					otherPart.setMoney(otherAmount);
					//其他费用
					otherPart.setMoneytype("009");
					//增值税发票
					otherPart.setTicketType("2");
					commonDao.saveOrUpdate(otherPart);
					partList.add(otherPart);
				}
				//金额验证
				if(excelContract.getDeviceAmount() + excelContract.getNoDeviceAmount() != excelContract.getTaxContractAmount()){
					logger.warn("工程类： "+getRowMessage(rowResult)+",设备费加非设备费不等于合同金额");
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				//合同部门
				List<ImportDepartment> importDepList = getDepartmentInfo(excelContract.getProjectCode(),excelContract.getMainProjectDepartment(),excelContract.getTaxContractAmount(),rowResult);
				//验证金额
				double sumDepAmount = 0;
				for (ImportDepartment importDepartment : importDepList) {
					sumDepAmount+=importDepartment.getDepartmentAmount();
				}
				if(sumDepAmount != excelContract.getTaxContractAmount()){
					logger.warn("工程类： "+getRowMessage(rowResult)+",部门总金额不等于合同金额");
				}
				//保存部门信息
				//平分费用金额
				double[] avgAmount = new double[partList.size()];
				double[] remainAmount = new double[partList.size()];
				for (int i = 0; i < partList.size(); i++) {
					ContractMaininfoPart part = partList.get(i);
					avgAmount[i] = part.getMoney()/importDepList.size();
					remainAmount[i] = part.getMoney();
				}
				//
				StringBuilder warnMessage = new StringBuilder();
				for (int depIndex = 0; depIndex < importDepList.size(); depIndex++) {
					ImportDepartment importDepartment = importDepList.get(depIndex);
					YXTypeManage department = (YXTypeManage) commonDao.uniqueResult("from YXTypeManage where typeName = ? and typeBig = 1018 and is_active = '1' ", importDepartment.getDepartmentName());
					if(department == null){
						logger.warn("工程类： "+getRowMessage(rowResult)+"部门名称为["+importDepartment.getDepartmentName()+"]的部门在类型管理中不存在");
						continue;
					}
					// 部门是否存在
					ContractItemMaininfo itemMain = (ContractItemMaininfo) commonDao.uniqueResult("from ContractItemMaininfo where contractMainInfo = ? and itemResDept = ? ", contract.getConMainInfoSid(),department.getTypeSmall());
					// 不存在才处理，存在就手动调整，自动生成的金额本来就需要调整
					if(itemMain == null){
						itemMain = new ContractItemMaininfo();
						itemMain.setContractMainInfo(contract.getConMainInfoSid());
						itemMain.setItemResDept(department.getTypeSmall());
						itemMain.setConItemId(importDepartment.getProjectCode());
						itemMain.setImportFromFile(Boolean.TRUE);
						commonDao.save(itemMain);
						//
						double depAmount = 0;
						//创建费用
						for (int partIndex = 0; partIndex < partList.size(); partIndex++) {
							ContractMaininfoPart part = partList.get(partIndex);
							ContractItemInfo itemInfo = new ContractItemInfo();
							itemInfo.setContractItemMaininfo(itemMain.getConItemMinfoSid());
							itemInfo.setMainInfoPartId(part.getId());
							// 最后一个用余额，除法可能出现小数误差
							if(depIndex == importDepList.size()-1){
								itemInfo.setConItemAmountWithTax(BigDecimalUtils.toBigDecial(remainAmount[partIndex]));
								depAmount+=remainAmount[partIndex];
							}else{
								itemInfo.setConItemAmountWithTax(BigDecimalUtils.toBigDecial(avgAmount[partIndex]));
								depAmount+=avgAmount[partIndex];
							}
							commonDao.save(itemInfo);
							remainAmount[partIndex] = remainAmount[partIndex] - avgAmount[partIndex];
						}
						if(depAmount != importDepartment.getDepartmentAmount()){
							warnMessage.append("部门名称为["+importDepartment.getDepartmentName()+"]的部门金额"+importDepartment.getDepartmentAmount()+"和分拆的费用金额总"+depAmount+"和不等,");
						}
					}
				}
				if(warnMessage.length() > 0){
					//不打印，大部分都不等
					//logger.warn("工程类： "+getRowMessage(rowResult)+warnMessage);
				}
				successCount++;
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : rowResult.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				logger.error("工程类： "+errorMsg.toString());
			}
		}
		logger.info("工程类：总条数："+totalCount+",成功："+successCount+",失败："+(totalCount-successCount)+"");
	}
	/**
	 * 分析部门信息
	 * @param projectCodes
	 * @param departmentStrs
	 * @param contractAmount
	 * @param rowResult
	 */
	private List<ImportDepartment> getDepartmentInfo(String[] projectCodes,String[] departmentStrs,Double contractAmount,RowResult rowResult){
		if(projectCodes == null || projectCodes.length == 0){
			//编号为空的太多
			//logger.warn("工程类： "+getRowMessage(rowResult)+",项目编号为空");
		}
		if(departmentStrs == null || departmentStrs.length == 0){
			logger.error("工程类： "+getRowMessage(rowResult)+",工程部门为空");
			return new ArrayList<ImportDepartment>();
		}
		if(projectCodes != null && projectCodes.length > 0 && projectCodes.length != departmentStrs.length ){
			logger.warn("工程类： "+getRowMessage(rowResult)+",项目编号和工程部门个数不匹配");
		}
		List<ImportDepartment> dList = new ArrayList<ImportDepartment>();
		for (int i = 0; i < departmentStrs.length; i++) {
			ImportDepartment importDep = getImportDepartment(departmentStrs[i]);
			if(departmentStrs.length == 1){
				importDep.setDepartmentAmount(contractAmount);
			}
			if(projectCodes != null && projectCodes.length == departmentStrs.length ){
				importDep.setProjectCode(projectCodes[i]);
			}
			dList.add(importDep);
		}
		return dList;
	}
	/**
	 * 分析字符传中的数据，返回部门名称和金额
	 * @param dstr
	 * @return
	 */
	private ImportDepartment getImportDepartment(String dstr){
		ImportDepartment d = new ImportDepartment();
		int indexLBracket = Math.max(dstr.indexOf("("), dstr.indexOf("（"));
		int indexWan = dstr.indexOf("万");
		//只有部门名称
		if(indexLBracket == -1){
			d.setDepartmentName(dstr.trim());
			d.setDepartmentAmount(0.0);
		}else{
			d.setDepartmentName(dstr.substring(0,indexLBracket).trim());
			d.setDepartmentAmount(Double.parseDouble(dstr.substring(indexLBracket+1,indexWan))*10000);
		}
		return d;
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
		int totalCount = cons.length;
		int successCount = 0;
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
				//客户项目类型 - 其他
				contract.setCustomereventtype("4");
				// 客户名称
				if(StringUtils.isNotBlank(excelContract.getClientName())){
					YXClientCode client =  (YXClientCode) commonDao.uniqueResult("from YXClientCode where fullName = ?", excelContract.getClientName());
					if(client != null){
						contract.setConCustomer(client.getId());
						contract.setBillCustomer(client.getId());
						contract.setItemCustomer(client.getId());
					}else{
						logger.warn("集成类： "+getRowMessage(rowResult)+"客户["+excelContract.getClientName()+"]不存在，请在客户表中添加");
						logger.warn("集成类："+StringUtils.leftPad("", 200," ")+StringUtils.leftPad(excelContract.getClientName(), 50," ")+","+excelContract.getSellerName());
					}
				}else{
					logger.warn("集成类： "+getRowMessage(rowResult)+"客户名称为空");
				}
				// 项目主负责部门
				YXTypeManage department = (YXTypeManage) commonDao.uniqueResult("from YXTypeManage where typeName = ? and typeBig = 1018 and is_active = '1' ", excelContract.getMainProjectDepartment());
				if(department != null){
					contract.setMainItemDept(department.getTypeSmall());
				}
				// 货币单位(人民币)
				contract.setCopeck("CNY");
				// 基准汇率
				contract.setBaserate(1.0);
				// 基准（含税）
				contract.setStandard("1");
				// 合同金额（含税）
				contract.setConTaxTamount(excelContract.getTaxContractAmount());
				// 合同金额（不含税）
				contract.setConNoTaxTamount(excelContract.getNoTaxContractAmount());
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
				if(contract.getConEndDate() == null){
					contract.setConEndDate(excelContract.getContractSignDate());
				}
				// 股份/股份其他/集团/宝钢外 -- 在客户管理页面里维护
				// 钢铁/非钢铁(Y/N) -- 在客户管理页面里
				//合同性质 - 设备供货
				contract.setConType("8");
				// 中标/非中标(Y/N)
				contract.setConBid(Boolean.FALSE);
				// 退税
				contract.setConDrawback(Boolean.FALSE);
				// 纳入年度运维合同
				contract.setIntoYearCon(Boolean.FALSE);
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
				//////////////////////////////////////////
				//费用组成
				ContractMaininfoPart devicePart = (ContractMaininfoPart) commonDao.uniqueResult("from ContractMaininfoPart where contractmainid = ? and moneytype = '101' ", contract.getConMainInfoSid());
				if(devicePart == null){
					devicePart = new ContractMaininfoPart();
				}
				devicePart.setContractmainid(contract.getConMainInfoSid());
				devicePart.setMoney(contract.getConTaxTamount());
				//设备费
				devicePart.setMoneytype("101");
				//增值税发票
				devicePart.setTicketType("2");
				commonDao.saveOrUpdate(devicePart);
				successCount++;
			}else{
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : rowResult.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				logger.error("集成类： "+errorMsg.toString());
			}
		}
		logger.info("集成类：总条数："+totalCount+",成功："+successCount+",失败："+(totalCount-successCount)+"");
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
	private class ImportDepartment{
		private String projectCode;
		private String departmentName;
		private Double departmentAmount;
		public String getProjectCode() {
			return projectCode;
		}
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
		public Double getDepartmentAmount() {
			return departmentAmount;
		}
		public void setDepartmentAmount(Double departmentAmount) {
			this.departmentAmount = departmentAmount;
		}
	}
}

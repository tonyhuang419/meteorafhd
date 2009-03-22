package com.baoz.yx.action.system.hisdata;

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
import com.baoz.core.service.impl.CommonService;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.IExcelImportService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.opensymphony.xwork2.ActionSupport;

@Results( { @Result(name = "success", value = "/WEB-INF/jsp/system/hisdata/importHistoryInitBillAndpro.jsp") })
public class ImpportHistoryContractInitPlanAction extends ActionSupport {

	private static Logger logger = Logger
			.getLogger(ImpportHistoryContractInitPlanAction.class);

	private static final long serialVersionUID = 572146812454l;

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	private File excelFile;

	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		return super.doDefault();
	}

	public String execute() throws Exception {
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

			}
			JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
			contractBuilder.setSheet(sheet);
			contractBuilder.setTargetClass(ExcelContractGatheringHistory.class);
			contractBuilder.setRule(29, sheet.getRows(), rowRule);
			//标识导入开始
			logger.error("********************导入开票收款计划开始"+System.currentTimeMillis()+"*****************");
			RowResult<ExcelContractGatheringHistory>[] cons = contractBuilder
					.parseExcel();
			for (RowResult<ExcelContractGatheringHistory> rowResult : cons) {
				if (!rowResult.hasErrors()) {
					ExcelContractGatheringHistory excelContract = rowResult
							.getRowObject();
					// 检查是否合同为空
					if (StringUtils.isBlank(excelContract.getContractCode())) {
						//合同号没有找项目号
						if(StringUtils.isBlank(excelContract.getProjectCode())){
							logger.error(getRowMessage(rowResult) + ",合同号和项目号都为空");
							continue;
						}else{
							ContractItemMaininfo itemMain = (ContractItemMaininfo) service.uniqueResult("from ContractItemMaininfo im where im.conItemId = ? ", excelContract.getProjectCode());
							if(itemMain == null){
								logger.error(getRowMessage(rowResult) + ",合同号为空，项目号"+excelContract.getProjectCode()+"不存在");
								continue;
							}else{
								ContractMainInfo mCon = (ContractMainInfo) service.load(ContractMainInfo.class, itemMain.getContractMainInfo());
								excelContract.setContractCode(mCon.getConId());
							}
						}
					}
					// 获得合同主体信息表
					ContractMainInfo contract = (ContractMainInfo) service
							.uniqueResult(
									"from ContractMainInfo where conId = ?",
									excelContract.getContractCode());
					if (contract == null) {
						if(StringUtils.isNotBlank(excelContract.getProjectCode())){
							//合同号没找到，通过项目号找
							ContractItemMaininfo itemMain = (ContractItemMaininfo) service.uniqueResult("from ContractItemMaininfo im where im.conItemId = ? ", excelContract.getProjectCode());
							if(itemMain == null){
								logger.error(getRowMessage(rowResult) + ",合同号为"+excelContract.getContractCode()+"，项目号"+excelContract.getProjectCode()+"不存在");
								continue;
							}else{
								contract = (ContractMainInfo) service.load(ContractMainInfo.class, itemMain.getContractMainInfo());
							}
						}else{
							logger.error(getRowMessage(rowResult) + ",合同号为"+ excelContract.getContractCode() + "不存在");
							continue;
						}
					}
					String hql = " from ContractItemMaininfo itemMainInfo where itemMainInfo.contractMainInfo=?";
					List<ContractItemMaininfo> itemMainInfoList = service.list(
							hql, contract.getConMainInfoSid());
					// 判断合同是集成类还是工程类，如果是工程类的话。开票收款计划就是项目数量*开票收款阶段
					if (contract.getContractType().equals("1")) {
						if (itemMainInfoList == null
								|| itemMainInfoList.size() <= 0) {
							logger.error(getRowMessage(rowResult) + ",合同号为"
									+ excelContract.getContractCode()
									+ "没有导入项目");
							continue;
						}
						 String
						 itemResDept=typeManageService.getYXTypeSmallByName(1018L,
						 excelContract.getProjectDepartment());
						 if(itemResDept==null){
						 logger.error(getRowMessage(rowResult) + ",合同号为"
						 + excelContract.getContractCode() + ",导入的工程责任部门["+excelContract.getProjectDepartment()+"]不存在！");
						 continue;
						 }
						 String itemHql="from ContractItemMaininfo itemMainInfo where itemMainInfo.contractMainInfo=? and itemMainInfo.itemResDept=?";
						 ContractItemMaininfo  itemMainInfo=(ContractItemMaininfo)service.uniqueResult(itemHql,contract.getConMainInfoSid(),itemResDept);
						 if(itemMainInfo==null){
						 logger.error(getRowMessage(rowResult) + ",合同号为"
						 + excelContract.getContractCode() +
						 ",项目负责部门是："+excelContract.getProjectPerson()+"的项目不存在！");
						 continue;
						 }
						String itemStageName = typeManageService
								.getYXTypeSmallByName(1023L, excelContract
										.getGatheringPhase());
						if (itemStageName == null) {
							logger.error(getRowMessage(rowResult) + ",合同号为"
									+ excelContract.getContractCode()
									+ ",导入的阶段名称"
									+ excelContract.getGatheringPhase()
									+ "不存在。");
							continue;
						}
						String stageHql = "select stage from  ContractItemStage stage,ContractMainInfo mainInfo where mainInfo.conMainInfoSid=stage.contractMainSid and mainInfo.conMainInfoSid=? and stage.itemStageName=?";
						ContractItemStage itemStage = (ContractItemStage) service
								.uniqueResult(stageHql, contract
										.getConMainInfoSid(), itemStageName);
						if (itemStage == null) {
							logger.error(getRowMessage(rowResult) + ",合同号为"
									+ excelContract.getContractCode()
									+ ",导入的阶段名称"
									+ excelContract.getGatheringPhase()
									+ "的收款阶段不存在。");
							continue;
						}
						if(excelContract.getInvoiceDate()==null){
							logger.error(getRowMessage(rowResult) + ",合同号为"
									+ excelContract.getContractCode()
									+ ",阶段名称为："
									+ excelContract.getGatheringPhase()
									+ "的预计开票日期没有填写");
						}
						if(excelContract.getInvoiceMoney()==null){
//							logger.error(getRowMessage(rowResult) + ",合同号为"
//									+ excelContract.getContractCode()
//									+ ",阶段名称为："
//									+ excelContract.getGatheringPhase()
//									+ "的预计开票金额没有填写");
							excelContract.setInvoiceMoney(0.0);
						}
							String initProHql = "select init from InitContractBillpro init"
									+ " where init.conMainInfoSid=? "
									+ " and init.conItemInfo=? and init.conItemStage=?";
							List<InitContractBillpro> initProList = service
									.list(initProHql, contract.getConMainInfoSid(), itemMainInfo.getConItemMinfoSid(), itemStage.getConIStageSid());
							
							String partHql="from ContractMaininfoPart part where part.contractmainid=?";
							List<ContractMaininfoPart> partList=service.list(partHql, contract.getConMainInfoSid());
							int count =partList.size();
							Double invoiceMoney = excelContract.getInvoiceMoney();
							double opMoney = 0.0;
							if (invoiceMoney != null) {
								opMoney = invoiceMoney.doubleValue();
							}
							opMoney = opMoney / count;
							for (InitContractBillpro initContractBillpro : initProList) {
								if (contract.getStandard().equals("1")) {
									initContractBillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(opMoney));
									initContractBillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(opMoney));
									initContractBillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(opMoney));
									initContractBillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(opMoney));
								} else {
									initContractBillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(opMoney));
									initContractBillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(opMoney,initContractBillpro.getBillNature())));
									initContractBillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(opMoney));
									initContractBillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(opMoney,initContractBillpro.getBillNature())));
								}
								initContractBillpro.setInitBillDate(excelContract.getInvoiceDate());
								initContractBillpro.setInitReceDate(excelContract.getGatheringDate());
								initContractBillpro.setImportFromFile(Boolean.TRUE);
								service.update(initContractBillpro);
							}
						// 如果是集成类的话开票收款就是1*开票收款阶段
					} else if (contract.getContractType().equals("2")) {
						String itemStageName = typeManageService.getYXTypeSmallByName(1023L, excelContract.getGatheringPhase());
						if (itemStageName == null) {
							logger.error(getRowMessage(rowResult) + ",合同号为"
							+ excelContract.getContractCode()
								+ ",导入的阶段名称"
							+ excelContract.getGatheringPhase()
							+ "不存在。");
							continue;
						}  
						String stageHql = "select stage from  ContractItemStage stage,ContractMainInfo mainInfo where mainInfo.conMainInfoSid=stage.contractMainSid and mainInfo.conMainInfoSid=? and stage.itemStageName=?";
						ContractItemStage itemStage = (ContractItemStage) service
						.uniqueResult(stageHql, contract
								.getConMainInfoSid(), itemStageName);
						if (itemStage == null) {
							logger.error(getRowMessage(rowResult) + ",合同号为"
							+ excelContract.getContractCode()
							+ ",导入的阶段名称"
							+ excelContract.getGatheringPhase()
							+ "的收款阶段不存在。");
							continue;
						}
						String initProHql = "select init from InitContractBillpro init"
							+ " where init.conMainInfoSid=? "
							+ "and init.conItemStage=?";
						List<InitContractBillpro> initProList = service
								.list(initProHql, contract.getConMainInfoSid(), itemStage.getConIStageSid());
						String partHql="from ContractMaininfoPart part where part.contractmainid=?";
						List<ContractMaininfoPart> partList=service.list(partHql, contract.getConMainInfoSid());
						int count =partList.size();
						Double invoiceMoney = excelContract.getInvoiceMoney();
						double opMoney = 0.0;
						if (invoiceMoney != null) {
							opMoney = invoiceMoney.doubleValue();
						}
						opMoney = opMoney / count;
						for (InitContractBillpro initContractBillpro : initProList) {
							if (contract.getStandard().equals("1")) {
								initContractBillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(opMoney));
								initContractBillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(opMoney));
								initContractBillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(opMoney));
								initContractBillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(opMoney));
							} else {
								initContractBillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(opMoney));
								initContractBillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(opMoney,initContractBillpro.getBillNature())));
								initContractBillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(opMoney));
								initContractBillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(opMoney,initContractBillpro.getBillNature())));
							}
							initContractBillpro.setInitBillDate(excelContract.getInvoiceDate());
							initContractBillpro.setInitReceDate(excelContract.getGatheringDate());
							initContractBillpro.setImportFromFile(Boolean.TRUE);
							service.update(initContractBillpro);
						}
					}
					String year = excelContract.getRegisterYear();
					String month = excelContract.getRegisterMonth();
					String date = excelContract.getRegisterDate();
					//不用比较
//					if (contract.getConSignDate() != null) {
//						String signDate = NumberToTime.getDateFormat(contract
//								.getConSignDate(), "yyyyMd");
//						if (!StringUtils.equals(signDate, year + month + date)) {
//							logger.error(getRowMessage(rowResult) + ",合同号"
//									+ excelContract.getContractCode()
//									+ "的合同签订日期和合同登记的日期不一致");
//						}
//					}
				} else {
					StringBuilder errorMsg = new StringBuilder();
					for (BuildError error : rowResult.getErrors()) {
						errorMsg.append(error.getMessage());
					}
					logger.error("开票收款计划： " + errorMsg.toString());
				}
			}
			//标识导入开始
			logger.error("********************导入开票收款计划结束"+System.currentTimeMillis()+"*****************");
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

}

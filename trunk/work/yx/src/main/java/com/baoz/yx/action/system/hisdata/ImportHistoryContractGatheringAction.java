package com.baoz.yx.action.system.hisdata;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.opensymphony.xwork2.ActionSupport;

@Results( {
		@Result(name = "success", value = "/WEB-INF/jsp/system/hisdata/importHistoryContractGathering.jsp")
})	
public class ImportHistoryContractGatheringAction extends ActionSupport {
	private static Logger logger = Logger
			.getLogger(ImportHistoryContractGatheringAction.class);

	private static final long serialVersionUID = 572146812454l;

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	private File excelFile;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	
	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;
	
	private List<ContractItemStagePlan> tempStagePlanList=new ArrayList<ContractItemStagePlan>();//导入阶段的时候如果阶段导入成功就把该条记录存起来 


	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		return "success";
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
			}
			JExcelRowObjectBuilder contractBuilder = new JExcelRowObjectBuilder();
			contractBuilder.setSheet(sheet);
			contractBuilder.setTargetClass(ExcelContractGatheringHistory.class);
			contractBuilder.setRule(29, sheet.getRows(), rowRule);

			RowResult<ExcelContractGatheringHistory>[] cons = contractBuilder
					.parseExcel();
			//标志开始
			logger.error("********************导入阶段开始"+System.currentTimeMillis()+"*****************");
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
							logger.error(getRowMessage(rowResult) + ",合同号为"
									+ excelContract.getContractCode() + "不存在");
							continue;
						}
					} else {
						if(excelContract.getGatheringPhase() != null){
							excelContract.setGatheringPhase(excelContract.getGatheringPhase().trim());
						}
						String typeSmall=typeManageService.getYXTypeSmallByName(1023L, excelContract.getGatheringPhase());
						if(typeSmall==null){
							logger.error(getRowMessage(rowResult) + ",名称为："
									+ excelContract.getGatheringPhase() + "的阶段不存在");
							continue;
						}
						List<ContractMaininfoPart> mainInfoPartList = service
								.list(
										"from ContractMaininfoPart part where part.contractmainid=?",
										contract.getConMainInfoSid());
						if (mainInfoPartList != null
								&& mainInfoPartList.size() > 0) {
							// 获得合同项目阶段表
							ContractItemStage itemStage = (ContractItemStage) service
									.uniqueResult(
											"select itemStage from ContractItemStage  itemStage where itemStage.itemStageName =? and itemStage.contractMainSid = ? ",
											typeSmall,
											contract.getConMainInfoSid());
							if (itemStage == null) {
								itemStage = new ContractItemStage();
								itemStage.setContractMainSid(contract
										.getConMainInfoSid());
								itemStage.setItemStageName(typeSmall);
								itemStage.setImportFromFile(Boolean.TRUE);
								service.save(itemStage);
							} else {
								itemStage.setContractMainSid(contract
										.getConMainInfoSid());
								itemStage.setItemStageName(typeSmall);
								itemStage.setImportFromFile(Boolean.TRUE);
								service.update(itemStage);
							}

							List<ContractItemStage> itemStateList = service
									.list(
											" from ContractItemStage itemStage where itemStage.contractMainSid=?",
											contract.getConMainInfoSid());
							for (ContractMaininfoPart maininfo : mainInfoPartList) {
								for (ContractItemStage item : itemStateList) {
									String hql = "from ContractItemStagePlan stagePlan where stagePlan.contractItemStage=? and stagePlan.contractMaininfoPart=? and stagePlan.contractMainSid=?";
									List<ContractItemStagePlan> itemPlanList = service
											.list(hql, item, maininfo,contract.getConMainInfoSid());

									if (itemPlanList != null&&itemPlanList.size()>0) {
									} else {
										ContractItemStagePlan plan = new ContractItemStagePlan();
										plan.setContractItemStage(item);
										plan.setContractMaininfoPart(maininfo);
										if(excelContract.getInvoiceDate()!= null){
											plan.setMakeInvoiceDate(excelContract.getInvoiceDate());
										}else{
											plan.setMakeInvoiceDate(new Date());
										}
										plan.setContractMainSid(contract
												.getConMainInfoSid());
										plan.setStageAmout(new Double("0"));
										plan.setTicketType(maininfo.getTicketType());
										service.save(plan);
										tempStagePlanList.add(plan);
										//导入阶段的同时还要生成开票收款计划
										importHisDataService.GeneratePlanByStageId(contract.getConMainInfoSid(), item.getConIStageSid(), maininfo.getMoneytype(), new Double("0.0"));
									}
								}
							}
						} else {
							logger.error(getRowMessage(rowResult) + ",还没有导入费用组成");
							continue;
						}
					}

				} else {
					StringBuilder errorMsg = new StringBuilder();
					for (BuildError error : rowResult.getErrors()) {
						errorMsg.append(error.getMessage());
					}
					logger.error("开票收款阶段： " + errorMsg.toString());
				}
			}
			if(tempStagePlanList!=null&&tempStagePlanList.size()>0){
				for (ContractItemStagePlan plan : tempStagePlanList) {
					/**通过plan去找费用组成。然后查询同一个费用组成下的plan，然后平分金额**/
					ContractMaininfoPart part =plan.getContractMaininfoPart();
					double totalMoney=0.0;
					if(part.getMoney()!=null){
						totalMoney=part.getMoney().doubleValue();
					}
					String opPlanHql="from ContractItemStagePlan plan where plan.contractMaininfoPart=?";
					List<ContractItemStagePlan> opStagePlanList=service.list(opPlanHql, part);
					int count=opStagePlanList.size();
					double preMoney=totalMoney/count;
					for (ContractItemStagePlan contractItemStagePlan : opStagePlanList) {
						contractItemStagePlan.setStageAmout(preMoney);
						service.update(contractItemStagePlan);
					}
				}
			}
			logger.error("********************导入阶段结束"+System.currentTimeMillis()+"*****************");

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

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<ContractItemStagePlan> getTempStagePlanList() {
		return tempStagePlanList;
	}

	public void setTempStagePlanList(List<ContractItemStagePlan> tempStagePlanList) {
		this.tempStagePlanList = tempStagePlanList;
	}

	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}

}
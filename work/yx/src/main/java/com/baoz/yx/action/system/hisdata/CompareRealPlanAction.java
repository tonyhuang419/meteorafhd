package com.baoz.yx.action.system.hisdata;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.vo.ExcelCompareRealPlanHistory;
import com.opensymphony.xwork2.ActionSupport;

public class CompareRealPlanAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(CompareRealPlanAction.class);
	private File excelFile;

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;

	@Override
	public String execute() throws Exception {
		if (excelFile != null) {
			Workbook workBook = Workbook.getWorkbook(excelFile);
			Sheet sheet = workBook.getSheet(0);
			RowRuleImpl rowRule = new RowRuleImpl();
			// ///////设置读取规则
			rowRule.addCellRule(new CellRuleImpl("A", "realPlanId",
					new NumberCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("B", "conName"));
			rowRule.addCellRule(new CellRuleImpl("C", "customerName"));
			rowRule.addCellRule(new CellRuleImpl("D", "conNo"));
			rowRule.addCellRule(new CellRuleImpl("E", "itemNo"));
//			ROWRULE.ADDCELLRULE(NEW CELLRULEIMPL("E", "PARTNAME"));
//			ROWRULE.ADDCELLRULE(NEW CELLRULEIMPL("F", "STAGENAME"));
//			ROWRULE.ADDCELLRULE(NEW CELLRULEIMPL("G", "DEPARTMENTNAME"));
//			ROWRULE.ADDCELLRULE(NEW CELLRULEIMPL("I", "BILLTYPE"));
			rowRule.addCellRule(new CellRuleImpl("I", "preBillDate",
					new DateCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("J", "preBillAmount",
					new NumberCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("Q", "realBillAmount",
					new NumberCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("L", "preReceiptDate",
					new DateCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("M", "preReceiptAmount",
					new NumberCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("S", "realReceiptAmount",
					new NumberCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("O", "saleMan"));
			rowRule.addCellRule(new CellRuleImpl("P", "realBillDate",
					new DateCellValueConvertor()));
			rowRule.addCellRule(new CellRuleImpl("R", "realReceiptDate",
					new DateCellValueConvertor()));
			
			
			JExcelRowObjectBuilder builder = new JExcelRowObjectBuilder();
			builder.setSheet(sheet);
			builder.setTargetClass(ExcelCompareRealPlanHistory.class);
			builder.setRule(1, sheet.getRows(), rowRule);
			RowResult<ExcelCompareRealPlanHistory>[] cons = builder
					.parseExcel();
			/** 标志比较开始* */
			for (RowResult<ExcelCompareRealPlanHistory> rowResult : cons) {
				if (!rowResult.hasErrors()) {
					ExcelCompareRealPlanHistory history = rowResult
							.getRowObject();
					// 检查计划id是否为空
					if (history.getRealPlanId() == null) {
						logger.error(getRowMessage(rowResult) + ",计划系统主键为空");
						continue;
					}
					if(history.getRealPlanId().equals(751L)){
						logger.error("nihao");
					}
					RealContractBillandRecePlan plan = (RealContractBillandRecePlan) service
							.uniqueResult(
									"from RealContractBillandRecePlan p where p.realConBillproSid=?",
									history.getRealPlanId());
					if (StringUtils.isBlank(history.getConNo())) {
						logger.error(getRowMessage(rowResult) + ",合同号为空");
						continue;
					}
					// 通过plan load出合同和项目
					ContractMainInfo mainInfo = (ContractMainInfo) service
							.load(ContractMainInfo.class, plan
									.getConMainInfoSid());
					if (!StringUtils.equals(mainInfo.getConId(), history
							.getConNo())) {
						logger.error(getRowMessage(rowResult) + ",系统主键为："
								+ history.getRealPlanId() + "的合同号和系统中的不匹配");
						continue;
					}
					// /如果合同是项目类，比较项目号在系统中是否存在
					if (mainInfo.getContractType().equals("1")) {
						if (StringUtils.isBlank(history.getItemNo())) {
							logger.error(getRowMessage(rowResult) + ",系统主键为："
									+ history.getRealPlanId()
									+ ",合同为项目 ,项目号为空");
							continue;
						}
						ContractItemMaininfo itemMainInfo = (ContractItemMaininfo) service
								.load(ContractItemMaininfo.class, plan
										.getContractItemMaininfo());
						if (!StringUtils.equals(itemMainInfo.getConItemId(),
								history.getItemNo())) {
							logger.error(getRowMessage(rowResult) + ",系统主键为："
									+ history.getRealPlanId() + "的项目号和系统中的不匹配");
							continue;
						}
					}
					if (history.getPreBillAmount() == null) {
						logger.error(getRowMessage(rowResult) + ",系统主键为："
								+ history.getRealPlanId() + "的预计开票金额为空");
						continue;
					}
					if (history.getPreReceiptAmount() == null) {
						logger.error(getRowMessage(rowResult) + ",系统主键为："
								+ history.getRealPlanId() + "的预计收款金额为空");
						continue;
					}
					Double preBillAmount = plan.getRealTaxBillAmount()
							.doubleValue();
					Double preReceiptAmount = plan.getRealTaxReceAmount()
							.doubleValue();
					if (!preBillAmount.equals(history.getPreBillAmount())) {
						logger.error(getRowMessage(rowResult) + ",系统主键为："
								+ history.getRealPlanId() + "的预计开票金额和系统中的不匹配");
						continue;
					}
					if (!preReceiptAmount.equals(history.getPreReceiptAmount())) {
						logger.error(getRowMessage(rowResult) + ",系统主键为："
								+ history.getRealPlanId() + "的预计收款金额和系统中的不匹配");
						continue;
					}
					Boolean fullBill = false;
					fullBill = history.getPreBillAmount().equals(
							history.getRealBillAmount());
					Boolean fullRece = false;
					fullRece = history.getPreReceiptAmount().equals(
							history.getRealReceiptAmount());
					importHisDataService.createFullBillAndRecePlan(plan,
							fullBill, fullRece,history.getRealBillDate(),history.getRealReceiptDate());

				} else {
					StringBuffer errorMsg = new StringBuffer();
					for (BuildError error : rowResult.getErrors()) {
						errorMsg.append(error.getMessage());
					}
					logger.error("未关闭合同开票收款： " + errorMsg.toString());
				}
			}

		}
		return null;
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

	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(
			IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}
}

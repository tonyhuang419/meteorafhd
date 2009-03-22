package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.dao.impl.YXCommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.ImportDueFromCompare;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.NoContractRecevieAmount;
import com.baoz.yx.entity.PlanReceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.entity.importfile.TempImportReveInfo;
import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.builder.jexcel.JExcelRowObjectBuilder;
import com.baoz.yx.excel.cell.jexcel.DateCellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.NumberCellValueConvertor;
import com.baoz.yx.excel.rule.impl.CellRuleImpl;
import com.baoz.yx.excel.rule.impl.RowRuleImpl;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.tools.SplitStr;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.baoz.yx.vo.ExcelHistoryReveInfo;
import com.baoz.yx.vo.ExcelImportDueFromComp;
import com.baoz.yx.vo.ProcessResult;


@Service("importHisDataService")
@Transactional
public class ImportHisDataService implements IImportHisDataService {

	private Logger logger = Logger.getLogger(ImportHisDataService.class);

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;

	@Autowired
	@Qualifier("harvestService")
	private IHarvestService harvestService;// 添加收款信息

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService codeGenerateService;

	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public IYXCommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IHarvestService getHarvestService() {
		return harvestService;
	}

	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public void importBillAndRecePlan(ExcelContractGatheringHistory history,
			List<RealContractBillandRecePlan> planList, ContractMainInfo mainCon) {
		// ///生成开票申请记录
		ApplyBill bill = new ApplyBill();
		bill.setContractMainInfo(mainCon.getConMainInfoSid()); // 主体合同信息系统号
		bill.setApplyId(new Date());
		bill.setBillType(history.getInvoiceType()); // 开票类型
		bill.setBase(1L); // 是否含税（ 0-不含税；1-含税；）...........
		// PS：合同新建时，#@java.util.TreeMap@{1:'含税',2:'不含税'}
		bill.setEmployeeId(mainCon.getSaleMan());
		bill.setBillSpot("宝山");
		bill.setIsNoContract(false); // 已签开票申请....false
		bill.setSign(Boolean.TRUE);
		bill.setInitIsNoContract(0L); // 已签申请
		bill.setOneOut(false);
		bill.setApplyBillState(6L);
		bill.setCustomerId(mainCon.getConCustomer());
		bill.setBillCustomer(mainCon.getBillCustomer());
		bill.setContactName(mainCon.getConName());
		bill.setBillNature(planList.get(0).getBillNature()); // 开票性质
		bill.setApplyWay(1L); // 申请入口： 合同已签开票申请
		Double noTaxMoney = TaxChange.TaxToNoTaxDouble(history
				.getRealBillAmount(), history.getInvoiceType());
		bill.setBillAmountTax(history.getRealBillAmount());
		bill.setBillAmountNotax(noTaxMoney);
		bill.setBillContent(planList.get(0).getBillContent());
		bill.setImportFromFile(Boolean.TRUE);
		bill.setBillApplyNum(codeGenerateService.generateSBillCode());
		commonDao.save(bill);
		commonDao.flushSession();
		/** ************************************************************************************ */
		// 保存到关联表 BillandProRelaion
		for (RealContractBillandRecePlan plan : planList) {
			BillandProRelaion relation = new BillandProRelaion();
			relation.setApplyBill(bill.getBillApplyId());
			relation
			.setRealContractBillandRecePlan(plan.getRealConBillproSid());
			relation.setRelateAmount(Double.valueOf(plan.getRealBillAmount()
					.doubleValue()));
			commonDao.save(relation);
			// 设置统一开票客户
			plan.setUniteBill(Boolean.FALSE);
			commonDao.update(plan);
			commonDao.flushSession();
		}

		/** *********************************************************************************** */
		List<String> invoiceNo = SplitStr.split(history.getInvoiceCode());// 把发票号统一
		// 有发票号，根据发票号的多少开票。没有发票号。只录入一张
		if (invoiceNo != null && invoiceNo.size() > 0) {
			for (int k = 0; k < invoiceNo.size(); k++) {
				// ////保存发票信息
				InvoiceInfo ii = new InvoiceInfo();
				ii.setInvoiceNo(invoiceNo.get(k));

				if (StringUtils.equals(history.getInvoiceType(), "4")) {
					ii.setReceAmount(history.getReceiptMoney()
							/ invoiceNo.size());
				} else {
					ii.setReceAmount(history.getRealMoney() / invoiceNo.size());
				}
				ii.setById(UserUtils.getUserDetail().getUser().getId());
				ii.setIs_active("1");
				ii.setUpdateBy(new Date());
				ii.setRecordDate(new Date());
				ii.setContractMainSid(bill.getContractMainInfo());
				ii.setApplyInvoiceId(bill.getBillApplyId());
				ii.setInvoiceAmount(history.getRealBillAmount());
				ii.setInvoiceDate(history.getInvoiceDate());
				ii.setInputState("0");
				ii.setImportFromFile(Boolean.TRUE);
				ii.setType(history.getInvoiceType());
				commonDao.save(ii);
				commonDao.flushSession();
			}
		} else {
			// ////保存发票信息
			InvoiceInfo ii = new InvoiceInfo();
			if (invoiceNo != null && invoiceNo.size() == 1) {
				ii.setInvoiceNo(invoiceNo.get(0));
			}

			if (StringUtils.equals(history.getInvoiceType(), "4")) {
				ii.setReceAmount(history.getReceiptMoney());
			} else {
				ii.setReceAmount(history.getRealMoney());
			}
			ii.setById(UserUtils.getUserDetail().getUser().getId());
			ii.setIs_active("1");
			ii.setUpdateBy(new Date());
			ii.setRecordDate(new Date());
			ii.setContractMainSid(bill.getContractMainInfo());
			ii.setApplyInvoiceId(bill.getBillApplyId());
			ii.setInvoiceAmount(history.getRealBillAmount());
			ii.setInvoiceDate(history.getInvoiceDate());
			ii.setInputState("0");
			ii.setImportFromFile(Boolean.TRUE);
			ii.setType(history.getInvoiceType());
			commonDao.save(ii);
			commonDao.flushSession();
		}
		contractCommonService.calBillInvoiceAmount(bill.getBillApplyId());
		/** *********************************************************************************** */

		/** *********************************************************************************** */

		if (history.getRealMoney() != null && history.getRealMoney() > 0) {
			// ////生成收款记录
			ReveInfo reveInfo = new ReveInfo();
			reveInfo.setBillSid(planList.get(0).getContractItemMaininfo());
			reveInfo.setConMainInfoSid(planList.get(0).getConMainInfoSid());
			reveInfo.setAmount(history.getRealMoney());
			reveInfo.setAmountTime(history.getRealDate());
			reveInfo.setIs_active("1");
			reveInfo.setById(UserUtils.getUser().getId());
			reveInfo.setUpdateBy(new Date());
			reveInfo.setReceType(typeManageService.getYXTypeSmallByName(1017L,
			"转帐到款"));
			reveInfo.setHasSure("0");
			reveInfo.setImportFromFile(Boolean.TRUE);
			harvestService.saveOrUpdateReveInfo(reveInfo);
			commonDao.flushSession();
		}
	}

	public void GeneratePlanByStageId(Long mainid, Long stageid,
			String moneytype, Double money) {
		// 查找合同下有此费用的项目
		ContractMaininfoPart cmaininfopart = (ContractMaininfoPart) commonDao
		.uniqueResult(
				"from ContractMaininfoPart where contractmainid=? and moneytype=?",
				mainid, moneytype);
		List<ContractItemInfo> iteminfolist = commonDao
		.list(
				"select ii from ContractItemInfo ii ,ContractItemMaininfo m where ii.contractItemMaininfo = m.conItemMinfoSid and m.contractMainInfo=? and ii.mainInfoPartId=?",
				mainid, cmaininfopart.getId());

		ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);

		// 工程类合同
		if ("1".equals(maininfo.getContractType())) {
			if (iteminfolist != null) {
				for (ContractItemInfo iteminfo : iteminfolist) {
					InitContractBillpro contractbillpro = new InitContractBillpro();
					// 添加项目号
					contractbillpro.setConItemInfo(iteminfo
							.getContractItemMaininfo());
					// 添加阶段号
					contractbillpro.setConItemStage(stageid);
					// 添加计划开票日期
					ContractItemStagePlan cisp = (ContractItemStagePlan) commonDao
					.uniqueResult(
							"from ContractItemStagePlan where contractMainSid=? and contractItemStage.conIStageSid = ? and contractMaininfoPart.moneytype = ?",
							mainid, stageid, moneytype);
					contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
					// 添加计划收款日期
					contractbillpro.setInitReceDate(DateUtils.addMonths(cisp
							.getMakeInvoiceDate(), typeManageService
							.getHarvestMonth(moneytype)));
					// 添加开票性质
					contractbillpro.setBillNature(moneytype);
					// 添加票据类型
					contractbillpro.setBillType(cmaininfopart.getTicketType());
					// 添加主合同号
					contractbillpro.setConMainInfoSid(mainid);
					contractbillpro.setImportFromFile(Boolean.TRUE);

					// 添加开票内容
					contractbillpro.setBillInfo(generateBillContent(mainid,
							moneytype).toString());
					// 添加计划开票金额
					// 此费用的项目金额
					Double a = iteminfo.getConItemAmountWithTax().doubleValue();
					// 阶段金额
					Double b = money;
					// 此费用总金额
					Double c = cmaininfopart.getMoney();
					// 计算出的开票金额
					Double d = a * b / c;
					if (maininfo.getStandard().equals("1")) {
						contractbillpro.setInitBillAmount(BigDecimalUtils
								.toBigDecial(d));
						contractbillpro.setInitTaxBillAmount(BigDecimalUtils
								.toBigDecial(d));
					} else {
						contractbillpro.setInitBillAmount(BigDecimalUtils
								.toBigDecial(d));
						ContractMaininfoPart cmp = (ContractMaininfoPart) commonDao
						.load(ContractMaininfoPart.class, cisp
								.getContractMaininfoPart().getId());
						contractbillpro.setInitTaxBillAmount(BigDecimalUtils
								.toBigDecial(TaxChange.NoTaxToTaxDouble(d, cmp
										.getTicketType())));
					}

					commonDao.save(contractbillpro);
				}
			}
		}
		if ("2".equals(maininfo.getContractType())) {
			InitContractBillpro contractbillpro = new InitContractBillpro();
			// 添加阶段号
			contractbillpro.setConItemStage(stageid);
			// 添加计划开票日期
			ContractItemStagePlan cisp = (ContractItemStagePlan) commonDao
			.uniqueResult(
					"from ContractItemStagePlan where contractMainSid=? and contractItemStage.conIStageSid = ? and contractMaininfoPart.moneytype = ?",
					mainid, stageid, moneytype);
			ContractMaininfoPart cmp = (ContractMaininfoPart) commonDao.load(
					ContractMaininfoPart.class, cisp.getContractMaininfoPart()
					.getId());
			contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
			// 添加开票性质
			contractbillpro.setBillNature(moneytype);
			// 添加票据类型
			contractbillpro.setBillType(cmaininfopart.getTicketType());
			// 添加主合同号
			contractbillpro.setConMainInfoSid(mainid);
			// 添加开票内容
			contractbillpro.setBillInfo(generateBillContent(mainid, moneytype)
					.toString());

			// 添加收款日起
			contractbillpro.setInitReceDate(DateUtils.addMonths(cisp
					.getMakeInvoiceDate(), typeManageService
					.getHarvestMonth(moneytype)));
			// 集成类 阶段金额既开票金额
			if (maininfo.getStandard().equals("1")) {
				contractbillpro.setInitBillAmount(BigDecimalUtils
						.toBigDecial(money));
				contractbillpro.setInitTaxBillAmount(BigDecimalUtils
						.toBigDecial(money));
			} else {
				contractbillpro.setInitBillAmount(BigDecimalUtils
						.toBigDecial(money));
				contractbillpro.setInitTaxBillAmount(BigDecimalUtils
						.toBigDecial(TaxChange.NoTaxToTaxDouble(money, cmp
								.getTicketType())));
			}
			commonDao.save(contractbillpro);
		}

	}

	private StringBuffer generateBillContent(Long mainid, String moneytype) {
		StringBuffer str = new StringBuffer();
		ContractMainInfo cmaininfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);
		if (cmaininfo.getConName() != null) {
			str.append(cmaininfo.getConName().toString());
			str.append("/");
		}
		if (cmaininfo.getPartyAConId() != null) {
			str.append(cmaininfo.getPartyAConId().toString());
			str.append("/");
		}
		if (cmaininfo.getPartyAProId() != null) {
			str.append(cmaininfo.getPartyAProId().toString());
		}
		str.append(typeManageService.getYXTypeManage(1012L, moneytype)
				.getTypeName());
		return str;
	}
	public void importReve(Sheet reveSheet)throws Exception{
		RowRuleImpl rowRule = new RowRuleImpl();
		// //////////////////////////////////////////////设置读取规则
		rowRule.addCellRule(new CellRuleImpl("A", "amountTime",
				new DateCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("B", "customerName"));
		rowRule.addCellRule(new CellRuleImpl("C", "amount",
				new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("D", "conId"));
		rowRule.addCellRule(new CellRuleImpl("F", "isPerArrive"));
		rowRule.addCellRule(new CellRuleImpl("H", "saleMan"));
		rowRule.addCellRule(new CellRuleImpl("I", "receType"));
		// //////////////////////////////////////////////
		JExcelRowObjectBuilder reveBuilder = new JExcelRowObjectBuilder();
		reveBuilder.setSheet(reveSheet);
		reveBuilder.setTargetClass(ExcelHistoryReveInfo.class);
		reveBuilder.setRule(0, reveSheet.getRows(), rowRule);
		Long batch=codeGenerateService.generateBatchByImportReve();
		RowResult<ExcelHistoryReveInfo>[] cons = reveBuilder.parseExcel();
		for (RowResult<ExcelHistoryReveInfo> rowResult : cons) {
			if (!rowResult.hasErrors()) {
				ExcelHistoryReveInfo excelReveInfo = rowResult.getRowObject();
				if (excelReveInfo.getCustomerName().equals("小计")
						|| excelReveInfo.getCustomerName().equals("累计")) {
					continue;
				}
				boolean flag=false;
				loggerError.delete(0, loggerError.length());
				if (StringUtils.isBlank(excelReveInfo.getConId())) {
					logger.error("收款明细导入：" + getRowMessage(rowResult)
							+ ",合同号为空");
					if (StringUtils.isBlank(excelReveInfo.getSaleMan())) {
						logger.error("收款明细导入：" + getRowMessage(rowResult)
								+ ",合同号为：" + excelReveInfo.getConId()
								+ ",销售员为空");
						loggerError.append("销售员为空/");
						flag=true;
					}
					String saleManHql = " from Employee e where e.name=?";
					Employee employee = (Employee) commonDao.uniqueResult(
							saleManHql, excelReveInfo.getSaleMan());
					if (employee == null) {
						logger.error("收款明细导入：" + getRowMessage(rowResult)
								+ ",合同号为：" + excelReveInfo.getConId() + ",销售员"
								+ excelReveInfo.getSaleMan() + "系统中不存在");
						loggerError.append("销售员在系统中不存在 /");
						flag=true;
					}
					if (StringUtils.isBlank(excelReveInfo.getCustomerName())) {
						logger.error("收款明细导入：" + getRowMessage(rowResult)
								+ ",合同号为：" + excelReveInfo.getConId()
								+ ",客户名称为空");
						loggerError.append("客户名称为空 /");
						flag=true;
					}
					String customerHql = "from YXClientCode code where code.fullName=?";
					YXClientCode customer = (YXClientCode) commonDao
					.uniqueResult(customerHql, excelReveInfo
							.getCustomerName());
					if (customer == null) {
						logger.error("收款明细导入：" + getRowMessage(rowResult)
								+ ",合同号为：" + excelReveInfo.getConId() + ",客户名称"
								+ excelReveInfo.getCustomerName() + "在系统中不存在");
						loggerError.append("客户名称在系统中不存在 /");
						flag=true;
					}
					if(excelReveInfo.getAmountTime()==null){
						logger.error("收款明细导入：" + getRowMessage(rowResult)
								+ ",合同号为：" + excelReveInfo.getConId() + ",收款日期"
								+ excelReveInfo.getAmountTime() + "为空");
						loggerError.append("收款日期为空 /");
						flag=true;
					}
//					Date opDate=DateUtils.truncate(excelReveInfo.getAmountTime(), Calendar.MONTH);
//					Date nowDate=DateUtils.truncate(new Date(), Calendar.MONTH);
//					if(!nowDate.equals(opDate)){
//						logger.error("收款明细导入：" + getRowMessage(rowResult)
//								+ ",合同号为：" + excelReveInfo.getConId() + ",收款日期"
//								+ excelReveInfo.getAmountTime() + "不在本月");
//						loggerError.append("收款日期不在本月 /");
//						flag=true;
//					}
					logger.error(getRowMessage(rowResult) + ",无合同号收款明细导入");
					insertOneRecord(excelReveInfo, flag, loggerError.toString(),batch,rowResult.getRow()+1);
					continue;
				}
				if(excelReveInfo.getAmountTime()==null){
					logger.error("收款明细导入：" + getRowMessage(rowResult)
							+ ",合同号为：" + excelReveInfo.getConId() + ",收款日期"
							+ excelReveInfo.getAmountTime() + "为空");
					loggerError.append("收款日期为空 /");
					flag=true;
				}
//				Date opDate=DateUtils.truncate(excelReveInfo.getAmountTime(), Calendar.MONTH);
//				Date nowDate=DateUtils.truncate(new Date(), Calendar.MONTH);
//				if(!nowDate.equals(opDate)){
//					logger.error("收款明细导入：" + getRowMessage(rowResult)
//							+ ",合同号为：" + excelReveInfo.getConId() + ",收款日期"
//							+ excelReveInfo.getAmountTime() + "不在本月");
//					loggerError.append("收款日期不在本月 /");
//					flag=true;
//				}
				ContractMainInfo contract = null;
				ContractItemMaininfo itemMainInfo = null;
				/** 如果合同号的长度为5，说明该合同集成类合同* */
				if (excelReveInfo.getConId().length() == 5) {
					String mainHql = "from ContractMainInfo mainInfo where mainInfo.conId=?";
					contract = (ContractMainInfo) commonDao.uniqueResult(
							mainHql, excelReveInfo.getConId());
				} else {
					String mainHql = "select mainInfo from ContractMainInfo mainInfo,ContractItemMaininfo item "
						+ "where item.contractMainInfo=mainInfo.conMainInfoSid and item.conItemId=?";
					contract = (ContractMainInfo) commonDao.uniqueResult(
							mainHql, excelReveInfo.getConId());
					String itemHql = "from ContractItemMaininfo item where item.conItemId=?";
					itemMainInfo = (ContractItemMaininfo) commonDao
					.uniqueResult(itemHql, excelReveInfo.getConId());
					if(itemMainInfo==null){
						logger.error("收款明细导入：" + getRowMessage(rowResult) + ",项目号在"
								+ excelReveInfo.getConId() + "在系统中不存在");
						loggerError
						.append("项目号在系统中不存在 /");
						flag=true;
					}
				}
				if (contract == null) {
					logger.error("收款明细导入：" + getRowMessage(rowResult) + ",合同号"
							+ excelReveInfo.getConId() + "在系统中不存在");
					loggerError
					.append("合同号在系统中不存在 /");
					flag=true;
				}
				if(contract!=null){
					if(contract.getConState().longValue()<4){
						logger.error("收款明细导入：" + getRowMessage(rowResult) + ",合同号"
								+ excelReveInfo.getConId() + "合同还未确认");
						loggerError
						.append("还未转成正式合同 /");
						flag=true;
					}
				}
				String customerHql = "from YXClientCode code where code.fullName=?";
				YXClientCode customer = (YXClientCode) commonDao
				.uniqueResult(customerHql, excelReveInfo
						.getCustomerName());
				if (customer == null) {
					logger.error("收款明细导入：" + getRowMessage(rowResult)
							+ ",合同号为：" + excelReveInfo.getConId() + ",客户名称"
							+ excelReveInfo.getCustomerName() + "在系统中不存在");
					if(contract !=null){
						loggerError.append("<font color='orange'>客户名称和合同中客户名称不一致</font> /");
					}else{
						loggerError.append("客户名称在系统中不存在 /");
						flag=true;
					}
				}
				if (StringUtils.isBlank(excelReveInfo.getSaleMan())) {
					logger.error("收款明细导入：" + getRowMessage(rowResult) + ",合同号"
							+ excelReveInfo.getConId() + ",销售员为空");
					loggerError.append("销售员为空 /");
					flag=true;
				}
				String saleManHql = "from Employee e where e.name=?";
				List<Employee> employeeList = commonDao.list(saleManHql,
						excelReveInfo.getSaleMan());
				if (employeeList == null || employeeList.size() == 0) {
					logger.error("收款明细导入：" + getRowMessage(rowResult) + ",合同号"
							+ excelReveInfo.getConId() + ",销售员"
							+ excelReveInfo.getSaleMan() + "系统中不存在");
					loggerError.append("销售员在系统中不存在 /");
					flag=true;
				}

				Boolean f = false;
				if(contract!=null){
					for (Employee employee : employeeList) {
						if (employee.getId().equals(contract.getSaleMan())) {
							f = true;
							break;
						}
					}
					if (!f) {
						logger.error("收款明细导入：" + getRowMessage(rowResult) + "合同号为："
								+ excelReveInfo.getConId() + ",销售员为："
								+ excelReveInfo.getSaleMan() + "与系统中的不一致");
						loggerError.append("销售员与系统中的不一致 /");
						flag=true;

					}
				}
				Double opMoney = 0.0;
				if(contract!=null){
					// 如果是工程类，判断项目里面的到款总额小于等于项目费用
					if (itemMainInfo != null) {
						String itemAmountHql = "select sum(info.realTaxBillAmount) from RealContractBillandRecePlan info where info.contractItemMaininfo=?";
						Double itemSumAmount=0.00;
						BigDecimal itemAmount = (BigDecimal) commonDao
						.uniqueResult(itemAmountHql, itemMainInfo
								.getConItemMinfoSid());
						Double realArriveAmount = itemMainInfo
						.getRealArriveAmount() == null ? 0.0 : itemMainInfo
								.getRealArriveAmount();
						if(itemAmount!=null){
							itemSumAmount = itemAmount.doubleValue();
						}
						opMoney = itemSumAmount - realArriveAmount;
						if (opMoney - excelReveInfo.getAmount() < -0.001 ) {
							logger.error("收款明细导入：" + getRowMessage(rowResult)
									+ "合同号为：" + excelReveInfo.getConId()
									+ "工程类,导入的收款金额大于项目金额减去实际到款金额的差,"+opMoney+"-"+excelReveInfo.getAmount());
							loggerError.append("工程类,导入的收款金额加上实际到款金额大于项目金额 /");
							flag=true;
						}
					} else {// 如果是集成类，没有项目，判断收款金额小于等于合同费用
						Double conTaxTamount = contract.getConTaxTamount();
						Double realArriveAmount = contract.getRealArriveAmount() == null ? 0.0
								: contract.getRealArriveAmount();
						opMoney = conTaxTamount - realArriveAmount;
						if (opMoney - excelReveInfo.getAmount() < -0.001 ) {
							logger.error("收款明细导入：" + getRowMessage(rowResult)
									+ "合同号为：" + excelReveInfo.getConId()
									+ "集成类,导入的收款金额大于合同金额减去实际到款金额的差,"+opMoney+"-"+excelReveInfo.getAmount());
							loggerError.append("集成类,导入的收款金额加上实际到款金额大于合同金额 /");
							flag=true;
						}
					}
				}
				insertOneRecord(excelReveInfo, flag, loggerError.toString(),batch,rowResult.getRow()+1);
				logger.error("收款明细导入：" + getRowMessage(rowResult)
						+ ",有合同号收款明细导入");
			} else {
				StringBuilder errorMsg = new StringBuilder();
				for (BuildError error : rowResult.getErrors()) {
					errorMsg.append(error.getMessage());
				}
				logger.error("收款明细： " + errorMsg.toString());
				loggerError.append("收款明细： " + errorMsg.toString());
				ExcelHistoryReveInfo excelReveInfo = rowResult.getRowObject();
				insertOneRecord(excelReveInfo, true, loggerError.toString(),batch,rowResult.getRow()+1);
			}
		}

	}

	private TempImportReveInfo insertOneRecord(ExcelHistoryReveInfo excelReveInfo,Boolean flag,String errorMsg,Long batch,Integer excelRowNo)throws Exception{

		TempImportReveInfo reveInfo=new TempImportReveInfo();
		reveInfo.setReveDate(excelReveInfo.getAmountTime());
		reveInfo.setCustomerName(excelReveInfo.getCustomerName());
		reveInfo.setReveAmount(BigDecimalUtils.toBigDecial(excelReveInfo.getAmount()));
		reveInfo.setBatch(batch);
		reveInfo.setExcelRowNo(excelRowNo);
		String str=excelReveInfo.getConId();
		if(!StringUtils.isBlank(str)){//表示无项目号也无合同号
			ContractMainInfo contract=null;
			ContractItemMaininfo item=null;
			if(str.length()==5){/** 如果合同号的长度为5，说明该合同为集成类合同,此时的合同号就是合同号* */
				reveInfo.setConNo(str);
				String contractHql="from ContractMainInfo m where m.conId=?";
				contract=(ContractMainInfo)commonDao.uniqueResult(contractHql,str);
			}else{/** 如果合同号的长度不为5，说明该合同为项目类合同,此时的合同号是项目号* */
				String contractHql="select m from ContractMainInfo m,ContractItemMaininfo item where item.contractMainInfo=m.conMainInfoSid and item.conItemId=?";
				contract=(ContractMainInfo)commonDao.uniqueResult(contractHql,str);
				if(contract!=null){
					reveInfo.setConNo(contract.getConId());
				}
				String itemHql="from ContractItemMaininfo item where item.conItemId=?";
				item=(ContractItemMaininfo)commonDao.uniqueResult(itemHql, str);
				reveInfo.setItemNo(str);
			}
			if(flag){
				reveInfo.setErrorState(1L);
			}else{
				reveInfo.setErrorState(0L);
				/**
				 *判断正常情况的三种方式,给客户提示
				 **/
				/**step1.无项目号叫未签到款*/
				/**step2.项目中的开票金额为0叫未开票到款*/
				/**step3.其他叫正常到款*/
				boolean notBill=false;
				if(contract!=null){
					if(contract.getContractType().equals("1")){//项目类
						double should = item.getShouldReceAmount() == null?0.0:item.getShouldReceAmount();
						double arrive = item.getRealArriveAmount() == null?0.0:item.getRealArriveAmount();
						if(should == 0 || (should - arrive < excelReveInfo.getAmount() )){
							notBill=true;
						}else{
							notBill=false;
						}
					}else if(contract.getContractType().equals("2")){//2集成类
						double should = contract.getShouldReceAmount() == null?0.0:contract.getShouldReceAmount();
						double arrive = contract.getRealArriveAmount() == null?0.0:contract.getRealArriveAmount();
						if(should==0||(should - arrive < excelReveInfo.getAmount())){
							notBill=true;
						}else{
							notBill=false;
						}
					}
				}
				if(notBill){
					reveInfo.setErrorMsg("未开票收款");
				}else{
					reveInfo.setErrorMsg("正常到款");
				}
				///////判断是否重复导入
				/***
				 * 条件：同一项目号，同一合同号，同一到款金额，同一到款日期
				 */
				String countHql = "select count(*) from ReveInfo reve where reve.conMainInfoSid = ? and  ( reve.billSid = ? or reve.billSid is null ) and reve.amount = ? and reve.amountTime = ?";
				Long count=(Long)commonDao.uniqueResult(countHql, contract.getConMainInfoSid(),item==null?-1:item.getConItemMinfoSid(),reveInfo.getReveAmount().doubleValue(),reveInfo.getReveDate());
				if(count>0){
					reveInfo.setErrorMsg(reveInfo.getErrorMsg()+" / <font color='orange'>存在相同的收款信息</font>");
				}

			}

		}else{
			if(flag){
				reveInfo.setErrorState(1L);
			}else{
				reveInfo.setErrorState(0L);
				reveInfo.setErrorMsg("未签到款");
				/*
				 * 判断未签到款是否重复
				 * 收款日期，收款金额。销售员，客户名称
				 **/
				Long eid=-2L;
				Long cid=-2L;
				Employee employee = (Employee)commonDao.uniqueResult("from Employee e where e.name=?",excelReveInfo.getSaleMan());
				YXClientCode customer = (YXClientCode)commonDao.uniqueResult("from YXClientCode cl where cl.fullName=?", excelReveInfo.getCustomerName());
				String noContractHql = "select count(*) from NoContractRecevieAmount nocontract " +
				"where nocontract.recevieAmount = ? and nocontract.recevieDate = ? " +
				"and nocontract.saleMan = ? and nocontract.customerid = ?";
				if(employee!=null){
					eid = employee.getId();
				}
				if(customer!=null){
					cid=customer.getId();
				}
				Long count=(Long)commonDao.uniqueResult(noContractHql, excelReveInfo.getAmount(),excelReveInfo.getAmountTime(),eid,cid);
				if(count>0){
					reveInfo.setErrorMsg(reveInfo.getErrorMsg()+" / <font color='orange'>存在相同的收款信息</font>");
				}
			}
			reveInfo.setIsPerArrive(excelReveInfo.getIsPerArrive());
		}
		reveInfo.setSaleMan(excelReveInfo.getSaleMan());
		reveInfo.setArriveType(excelReveInfo.getReceType());
		reveInfo.setAuthorId(UserUtils.getUser().getId());
		if(reveInfo.getErrorMsg()!=null){
			reveInfo.setErrorMsg(reveInfo.getErrorMsg()+" / "+errorMsg);
		}else{
			reveInfo.setErrorMsg(errorMsg);
		}
		reveInfo.setIsActive(1L);
		commonDao.save(reveInfo);
		return reveInfo;

	}
	public void importReveInfo(List<TempImportReveInfo> excelReveInfoList) throws Exception {
		for (TempImportReveInfo excelReveInfo : excelReveInfoList) {
			if(excelReveInfo.getErrorState().equals(0L)){
				if(StringUtils.isBlank(excelReveInfo.getConNo())){//如果没有项目号，则为无项目号导入
					NoContractRecevieAmount recevieAmount = new NoContractRecevieAmount();
					recevieAmount.setRecevieAmount(excelReveInfo.getReveAmount().doubleValue());
					recevieAmount.setRecevieDate(excelReveInfo.getReveDate());
					recevieAmount.setOpPerson(UserUtils.getUser().getId());
					recevieAmount.setOpTime(new Date());
					Employee employee=(Employee)commonDao.uniqueResult("from Employee e where e.name=?",excelReveInfo.getSaleMan());
					recevieAmount.setSaleMan(employee.getId());
					YXClientCode customer=(YXClientCode)commonDao.uniqueResult("from YXClientCode cl where cl.fullName=?", excelReveInfo.getCustomerName());
					recevieAmount.setCustomerid(customer.getId());
					recevieAmount.setState("0");
					recevieAmount.setImportFromFile(Boolean.TRUE);
					if(excelReveInfo.getIsPerArrive()!=null&&"预收".equals(excelReveInfo.getIsPerArrive())){
						recevieAmount.setIsPerArrive(1L);//预收
					}else{
						recevieAmount.setIsPerArrive(0L);//非预收
					}
					commonDao.save(recevieAmount);
				}else{
					ContractMainInfo contract = null;
					ContractItemMaininfo itemMainInfo = null;
					String mainHql = "from ContractMainInfo mainInfo where mainInfo.conId=?";
					contract = (ContractMainInfo) commonDao.uniqueResult(
							mainHql, excelReveInfo.getConNo());

					String itemHql = "from ContractItemMaininfo item where item.conItemId=?";
					itemMainInfo = (ContractItemMaininfo) commonDao
					.uniqueResult(itemHql, excelReveInfo.getItemNo());

					ReveInfo reveInfo = new ReveInfo();
					reveInfo.setAmountTime(excelReveInfo.getReveDate());
					reveInfo.setAmount(excelReveInfo.getReveAmount().doubleValue());
					reveInfo.setConMainInfoSid(contract.getConMainInfoSid());
					if (itemMainInfo != null) {
						reveInfo.setBillSid(itemMainInfo.getConItemMinfoSid());
					}
					reveInfo.setHasSure("0");
					reveInfo.setIs_active("1");
					reveInfo.setById(UserUtils.getUser().getId());
					reveInfo.setUpdateBy(new Date());
					reveInfo.setImportFromFile(Boolean.TRUE);
					if (StringUtils.equals(excelReveInfo.getArriveType(), "汇票")) {
						reveInfo.setReceType("3");// 3为银行承兑汇票
					} else {
						reveInfo.setReceType("1");// 1为转账到款
					}
					commonDao.save(reveInfo);
					Long itemId = null;
					if (itemMainInfo != null) {
						itemId = itemMainInfo.getConItemMinfoSid();
					}
					harvestService.modifyCurrentArriveAmount(contract
							.getConMainInfoSid(), itemId);
				}
				excelReveInfo.setIsActive(0L);
				commonDao.update(excelReveInfo);
			}
		}

	}

	public void createFullByImoprtClosedCon(ContractMainInfo contract,
			boolean fullBill, boolean fullRece) {
		// 集成类
		if (contract.getContractType().equals("2")) {
			String planHql = "from RealContractBillandRecePlan plan where plan.conMainInfoSid=?";
			List<RealContractBillandRecePlan> planList = commonDao.list(
					planHql, contract.getConMainInfoSid());
			/** 一个计划生成一张申请单，生成一张发票，生成一张收款* */
			for (RealContractBillandRecePlan plan : planList) {
				createFullBillAndRecePlan(plan, fullBill, fullRece);
			}
		} else {
			// 项目类
			String projectHql = "from ContractItemMaininfo im where im.contractMainInfo = ? ";
			List<ContractItemMaininfo> projectList = commonDao.list(projectHql,
					contract.getConMainInfoSid());
			/** 一个计划生成一张申请单，生成一张发票，生成一张收款* */
			for (ContractItemMaininfo itemMain : projectList) {
				createFullBillAndReceProject(itemMain, fullBill, fullRece);
			}
		}
		// 合同是否建议关闭
		contractService.contractIsCloseByContractId(contract
				.getConMainInfoSid());
	}

	public void createFullBillAndReceProject(ContractItemMaininfo project,
			boolean fullBill, boolean fullRece) {
		String planHql = "from RealContractBillandRecePlan plan where plan.contractItemMaininfo = ?";
		List<RealContractBillandRecePlan> planList = commonDao.list(planHql,
				project.getConItemMinfoSid());
		/** 一个计划生成一张申请单，生成一张发票，生成一张收款* */
		for (RealContractBillandRecePlan plan : planList) {
			createFullBillAndRecePlan(plan, fullBill, fullRece);
		}
		// 项目是否建议关闭,内部调用了合同是否建议关闭
		contractService.itemIsCloseByItem(project.getConItemMinfoSid());
	}

	public void createFullBillAndRecePlan(RealContractBillandRecePlan plan,
			boolean fullBill, boolean fullRece) {
		ContractMainInfo contract = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		if (fullBill) {
			// 没开票的才能生成
			if (plan.getBillInvoiceAmount() == null
					|| plan.getBillInvoiceAmount() == 0) {
				/**
				 * step1.生成开票申请
				 */
				ApplyBill bill = createApplyBill(plan, contract, plan
						.getRealPredBillDate());
				/**
				 * step2.生成发票
				 */
				createInvoiceInfo(bill, plan);
				///计算应收金额
				contractCommonService.calBillInvoiceAmount(bill.getBillApplyId());
				//修改计划里面的统计字段
				contractCommonService.updatePlanStatistic(plan.getRealPredBillDate(), plan.getRealConBillproSid(), false);
			}
		}
		if (fullRece && fullBill) {
			// 没收款的才能生成
			if (plan.getRealArriveAmount() == null
					|| plan.getRealArriveAmount().equals(BigDecimal.ZERO)) {
				/**
				 * step3.生成收款
				 */
				createReveInfo(plan);
			}

		}// 合同建议关闭，项目建议关闭
		if (contract.getContractType().equals("2")) {
			contractService.contractIsCloseByContractId(contract
					.getConMainInfoSid());
		} else {
			contractService.itemIsCloseByItem(plan.getContractItemMaininfo());
		}
	}

	public ProcessResult createFullBillAndRecePlan(RealContractBillandRecePlan plan,
			boolean fullBill, boolean fullRece, Date realBillDate,
			Date realArrivedDate) {
		ProcessResult result = new ProcessResult();
		ContractMainInfo contract = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		if (fullBill) {

			//没有开票申请的才能生成	BillandProRelaion
			String relationHql="select count(*) from BillandProRelaion relation where relation.realContractBillandRecePlan=?";
			Long count=(Long)commonDao.uniqueResult(relationHql, plan.getRealConBillproSid());
			// 没开票的才能生成
			if (count==0&&(plan.getBillInvoiceAmount() == null
					|| plan.getBillInvoiceAmount() == 0)&&plan.getRealBillAmount().doubleValue()>0.0) {
				/**
				 * step1.生成开票申请
				 */
				ApplyBill bill = createApplyBill(plan, contract, realBillDate);

				/**
				 * step2.生成发票
				 */
				createInvoiceInfo(bill, plan, realBillDate);
				///计算应收金额
				contractCommonService.calBillInvoiceAmount(bill.getBillApplyId());
				//修改计划里面的统计字段
				contractCommonService.updatePlanStatistic(realBillDate, plan.getRealConBillproSid(), false);
			}else{
				if(plan.getRealBillAmount().doubleValue() == 0){
					result.addErrorMessage("开票金额是0，不用开票");
				}
				if(count > 0){
					result.addErrorMessage("已经提交过申请");
				}
				if(plan.getBillInvoiceAmount() != null && plan.getBillInvoiceAmount() > 0){
					result.addErrorMessage("已经开过票");
				}
			}
		}
		if (fullRece && fullBill) {
			// 全额开票的才能收款
			if(getDefaultIfNull(plan.getRealTaxBillAmount()).equals(getDefaultIfNull(plan.getBillInvoiceAmount()))){
				//获得申请单
				String relationHql="select bill from BillandProRelaion relation,ApplyBill bill where bill.billApplyId=relation.applyBill and relation.realContractBillandRecePlan = ? ";
				ApplyBill bill=(ApplyBill)commonDao.uniqueResult(relationHql, plan.getRealConBillproSid());
				if((bill != null && bill.getApplyBillState() == 6) || plan.getRealBillAmount().doubleValue() == 0){
					// 没收款的才能生成
					if (plan.getRealArriveAmount() == null
							|| plan.getRealArriveAmount().equals(BigDecimal.ZERO)) {
						/**
						 * step3.生成收款
						 */
						createReveInfo(plan, realArrivedDate);
					}else{
						result.addErrorMessage("已经收过款");
					}
				}else{
					result.addErrorMessage("申请单还未签收");
				}
			}else{
				result.addErrorMessage("还没有全额开票");
			}
		}
		// 合同建议关闭，项目建议关闭
		if (contract.getContractType().equals("2")) {
			contractService.contractIsCloseByContractId(contract
					.getConMainInfoSid());
		} else {
			contractService.itemIsCloseByItem(plan.getContractItemMaininfo());
		}
		result.setSuccess(true);
		return result;
	}

	public void updateRealBillAndReceDate(RealContractBillandRecePlan plan,
			Date realBillDate, Date realReceDate) {
		/***********************************************************************
		 * 更新计划里面的最后开票日期和invoiceInfo里面的开票日期
		 **********************************************************************/
		if (realBillDate != null) {
			String invoiceHql = "select invoice from RealContractBillandRecePlan plan,"
				+ "BillandProRelaion relation,"
				+ "ApplyBill apply,"
				+ "InvoiceInfo invoice where relation.realContractBillandRecePlan = plan.realConBillproSid "
				+ "and relation.applyBill = apply.billApplyId and invoice.applyInvoiceId = apply.billApplyId "
				+ "and plan.realConBillproSid = ?";
			List<InvoiceInfo> invoiceList = commonDao.list(invoiceHql, plan
					.getRealConBillproSid());
			if (invoiceList != null && invoiceList.size() == 1) {
				InvoiceInfo invoiceInfo = invoiceList.get(0);
				if (invoiceInfo != null) {
					invoiceInfo.setInvoiceDate(realBillDate);
					commonDao.update(invoiceInfo);
					plan.setRealNowBillDate(realBillDate);
					commonDao.update(plan);
				}
			}
		}
		if (realReceDate != null) {
			String receHql = "select reve,pr from ReveInfo reve,PlanReceInfo pr,RealContractBillandRecePlan plan "
				+ "where pr.planId = plan.realConBillproSid and pr.reveInfoId = reve.id and plan.realConBillproSid = ?";
			List<Object[]> reveList = commonDao.list(receHql, plan
					.getRealConBillproSid());
			if (reveList != null && reveList.size() == 1) {
				ReveInfo reveInfo = (ReveInfo) reveList.get(0)[0];
				reveInfo.setAmountTime(realReceDate);
				commonDao.update(reveInfo);
				plan.setRealNowReceDate(realReceDate);
				commonDao.update(plan);
				PlanReceInfo planRece = (PlanReceInfo) reveList.get(0)[1];
				planRece.setAmountTime(realReceDate);
				commonDao.update(planRece);
			}
		}
	}

	private void createReveInfo(RealContractBillandRecePlan plan) {
		createReveInfo(plan, plan.getRealPredReceDate());
	}

	/**
	 * 通过计划创建收款信息
	 */
	private void createReveInfo(RealContractBillandRecePlan plan,
			Date realArrivedDate) {
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		Long conId = plan.getConMainInfoSid();
		Long itemId = plan.getContractItemMaininfo();
		ReveInfo reveInfo = new ReveInfo();
		reveInfo.setUpdateBy(new Date());
		reveInfo.setAmountTime(realArrivedDate);
		reveInfo.setAmount(plan.getRealTaxReceAmount().doubleValue());
		reveInfo.setConMainInfoSid(conId);
		reveInfo.setBillSid(itemId);
		reveInfo.setHasSure("1"); // 确认收款
		reveInfo.setIs_active("1");
		reveInfo.setById(UserUtils.getUser().getId());
		reveInfo.setImportFromFile(Boolean.TRUE);
		reveInfo.setReceType("1"); // 1为转账到款
		commonDao.save(reveInfo);
		// ///设置收款金额
		plan.setRealArriveAmount(plan.getRealTaxReceAmount());
		plan.setCurrentArriveAmount(0.0);
		plan.setRealNowReceDate(reveInfo.getAmountTime());
		commonDao.update(plan);
		// ///记录计划收款履历
		// 创建计划收款日志
		PlanReceInfo planReceInfo = new PlanReceInfo();
		planReceInfo.setAmount(plan.getRealTaxReceAmount().doubleValue());
		planReceInfo.setAmountTime(reveInfo.getAmountTime());
		planReceInfo.setPlanId(plan.getRealConBillproSid());
		planReceInfo.setReveInfoId(reveInfo.getId());
		commonDao.save(planReceInfo);
		//更新月度计划
		harvestService.updateMonthlyRecePlan(plan, realArrivedDate);
		// ///////////////session清空
		commonDao.flushSession();

		// /////////修改到款金额的值

		/**
		 * 修改合同到款金额的值
		 */
		String arriveMainHql = "select sum(p.realArriveAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ?";
		BigDecimal arriveMainAmount = (BigDecimal) commonDao.uniqueResult(
				arriveMainHql, mainInfo.getConMainInfoSid());
		if (arriveMainAmount == null) {
			mainInfo.setRealArriveAmount(0.0);
		} else {
			mainInfo.setRealArriveAmount(arriveMainAmount.doubleValue());
		}
		commonDao.update(mainInfo);
		/**
		 * 修改项目到款金额的值
		 */
		if (StringUtils.equals(mainInfo.getContractType(), "1")) {
			// 项目类
			ContractItemMaininfo itemMainInfo = (ContractItemMaininfo) commonDao
			.load(ContractItemMaininfo.class, plan
					.getContractItemMaininfo());
			String arriveHql = "select sum(p.realArriveAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo=?";
			BigDecimal amount = (BigDecimal) commonDao.uniqueResult(arriveHql,
					itemMainInfo.getConItemMinfoSid());
			if (amount == null) {
				itemMainInfo.setRealArriveAmount(0.0);
			} else {
				itemMainInfo.setRealArriveAmount(amount.doubleValue());
			}
			commonDao.update(itemMainInfo);
		}
	}

	private void createInvoiceInfo(ApplyBill bill,
			RealContractBillandRecePlan plan) {
		createInvoiceInfo(bill, plan, plan.getRealPredBillDate());
	}

	/**
	 * 通过开票申请和开票收款计划生成开票
	 */
	private void createInvoiceInfo(ApplyBill bill,
			RealContractBillandRecePlan plan, Date realBillDate) {
		InvoiceInfo ii = new InvoiceInfo();
		ii.setById(UserUtils.getUser().getId());
		ii.setIs_active("1");
		ii.setUpdateBy(new Date());
		ii.setRecordDate(new Date());
		ii.setType(bill.getBillType());
		ii.setContractMainSid(bill.getContractMainInfo());
		ii.setInvoiceDate(realBillDate);
		ii.setApplyInvoiceId(bill.getBillApplyId());
		ii.setInvoiceAmount(bill.getBillAmountTax());
		ii.setReceAmount(plan.getRealTaxReceAmount().doubleValue());
		ii.setInputState("1");// 已确认
		ii.setInvoiceNo(codeGenerateService.generateInvoveNo());
		ii.setImportFromFile(Boolean.TRUE);
		commonDao.save(ii);
		// 设置开票金额
		plan.setBillInvoiceAmount(ii.getInvoiceAmount());
		// 设置应收金额
		plan.setShouldReceAmount(plan.getRealReceAmount().doubleValue());
		// 开票日期
		plan.setRealNowBillDate(ii.getInvoiceDate());
		commonDao.update(plan);

		// 在开票的时候统计计划里面的收据金额，发票金额和应收金额分别到合同和项目中去
		commonDao.flushSession();
		/**
		 * 通过计划去查询合同和项目,把收据金额，发票金额和应收金额插入到合同统计数据中去
		 */
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		String invoiceHql = "select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? and p.billType!='4'";
		String receiptHql = "select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? and p.billType='4'";
		String shouldReceHql = "select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ?";
		Double receiptAmount = (Double) commonDao.uniqueResult(receiptHql,
				mainInfo.getConMainInfoSid());
		Double invoiceAmount = (Double) commonDao.uniqueResult(invoiceHql,
				mainInfo.getConMainInfoSid());
		Double shouldReveAmount = (Double) commonDao.uniqueResult(
				shouldReceHql, mainInfo.getConMainInfoSid());
		mainInfo.setBillReceiptAmount(receiptAmount);
		mainInfo.setBillInvoiceAmount(invoiceAmount);
		mainInfo.setShouldReceAmount(shouldReveAmount);
		commonDao.update(mainInfo);
		/**
		 * 判断合同是项目类还是集成类，项目类合同还要修改项目中的统计字段 ，集成类合同由于没有项目故不做操作
		 */

		if (StringUtils.equals(mainInfo.getContractType(), "1")) {
			// 项目类
			ContractItemMaininfo itemMainInfo = (ContractItemMaininfo) commonDao
			.load(ContractItemMaininfo.class, plan
					.getContractItemMaininfo());
			String invoiceHqlItem = "select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? and p.billType!='4'";
			String receiptHqlItem = "select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? and p.billType='4'";
			String shouldReceHqlItem = "select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ?";
			Double receiptAmountItem = (Double) commonDao.uniqueResult(
					invoiceHqlItem, itemMainInfo.getConItemMinfoSid());
			Double invoiceAmountItem = (Double) commonDao.uniqueResult(
					receiptHqlItem, itemMainInfo.getConItemMinfoSid());
			Double shouldReveAmountItem = (Double) commonDao.uniqueResult(
					shouldReceHqlItem, itemMainInfo.getConItemMinfoSid());
			itemMainInfo.setBillReceiptAmount(receiptAmountItem);
			itemMainInfo.setBillInvoiceAmount(invoiceAmountItem);
			itemMainInfo.setShouldReceAmount(shouldReveAmountItem);
			commonDao.update(itemMainInfo);
		}
	}

	/**
	 * 通过开票收款计划生成开票申请
	 * 
	 * @param applyDate
	 */
	private ApplyBill createApplyBill(RealContractBillandRecePlan plan,
			ContractMainInfo contract, Date applyDate) {
		ApplyBill bill = new ApplyBill();
		bill.setContractMainInfo(plan.getConMainInfoSid()); // 主体合同信息系统号
		bill.setItemSid(plan.getContractItemMaininfo());// 设置项目信息系统号
		bill.setBillContent(plan.getBillContent()); // 开票内容
		bill.setApplyId(applyDate);
		bill.setBillType(plan.getBillType()); // 开票类型
		bill.setBase(1L); // 是否含税（ 0-不含税；1-含税；）...........
		// PS：合同新建时，#@java.util.TreeMap@{1:'含税',2:'不含税'}
		bill.setEmployeeId(contract.getSaleMan());
		bill.setBillSpot("宝山");
		bill.setIsNoContract(false); // 已签开票申请....false
		bill.setSign(Boolean.TRUE);
		bill.setInitIsNoContract(0L); //
		bill.setOneOut(false);
		bill.setApplyBillState(6L);
		bill.setCustomerId(contract.getConCustomer());
		bill.setBillCustomer(contract.getBillCustomer());
		bill.setContactName(contract.getConName());
		bill.setBillNature(plan.getBillNature()); // 开票性质
		bill.setRealPlanId(plan.getRealConBillproSid());
		bill.setApplyWay(1L); // 申请入口： 合同已签开票申请
		bill.setBillAmountTax(plan.getRealTaxBillAmount().doubleValue());
		Double noTaxMoney = TaxChange.TaxToNoTaxDouble(plan
				.getRealTaxBillAmount().doubleValue(), plan.getBillType());
		bill.setBillAmountNotax(noTaxMoney);
		bill.setBillApplyNum(codeGenerateService.generateSBillCode());
		bill.setImportFromFile(Boolean.TRUE);
		bill.setStockOrg("1");
		commonDao.save(bill);
		// 保存到关联表 BillandProRelaion
		BillandProRelaion relation = new BillandProRelaion();
		relation.setApplyBill(bill.getBillApplyId());
		relation.setRealContractBillandRecePlan(plan.getRealConBillproSid());
		relation.setRelateAmount(Double.valueOf(plan.getRealTaxBillAmount()
				.toString()));
		relation.setImportFromFile(Boolean.TRUE);
		relation.setRelateInvoiceAmount(Double.valueOf(plan
				.getRealTaxBillAmount().toString()));
		commonDao.save(relation);
		// 设置统一开票客户
		plan.setUniteBill(Boolean.TRUE);
		plan.setApplyBillState(6L);// 申请单已经确认通过
		commonDao.update(plan);
		return bill;
	}

	public void cancelPlanBill(long planId, String contractCode, String saleName) {
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonDao
		.load(RealContractBillandRecePlan.class, planId);
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		Employee saleMain = (Employee) commonDao.load(Employee.class, mainInfo
				.getSaleMan());
		if (!StringUtils.equals(mainInfo.getConId(), contractCode)) {
			logger.error(planId + ":合同号不一致" + contractCode + "-"
					+ mainInfo.getConId());
			return;
		}
		if (!StringUtils.equals(saleMain.getName(), saleName)) {
			logger.error(planId + ":销售员不一致" + saleName + "-"
					+ saleMain.getName());
			return;
		}
		cancelPlanBill(planId);
	}

	public ProcessResult cancelPlanBill(long planId) {
		ProcessResult result = new ProcessResult();
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, planId);
		if(plan.getBillInvoiceAmount() == null || plan.getBillInvoiceAmount().doubleValue() == 0 ){
			logger.error(planId + "该计划没有开票");
			result.addErrorMessage("该计划没有开票");
			return result;
		}
		// 如果有收款，先去掉收款
		if (plan.getRealArriveAmount() != null
				&& plan.getRealArriveAmount().doubleValue() > 0) {
			logger.error(planId + "已经有收款,先取消收款");
			result.addErrorMessage("该计划已经有收款,先取消收款");
			return result;
		}
		// 如果有关联金额，要手工修正
		if (!plan.getBillType().equals("4") && getDefaultIfNull(plan.getRelationReceAmount())>0) {
			logger.error(planId + "已经有关联过收据,先取消收据关联");
			result.addErrorMessage("该计划已经关联过收据,需要手工处理");
			return result;
		}
		// 如果合并开票,需要手工处理
		List<BillandProRelaion> brList = commonDao.list(" from BillandProRelaion br where br.realContractBillandRecePlan = ? ", planId);
		for (BillandProRelaion br : brList) {
			List<BillandProRelaion> newbrList = commonDao.list(" from BillandProRelaion br where br.applyBill = ?", br.getApplyBill());
			if(newbrList.size() > 1){
				logger.error(br.getApplyBill() + "为合并开票");
				result.addErrorMessage("该计划为合并开票");
				return result;
			}
		}

		// 如果有月度计划,需要手工修正
		List<MonthlyBillpro> mbList = commonDao.list("from MonthlyBillpro mb where mb.realContractBillandRecePlan = ? ", plan);
		if(mbList.size() > 1){
			logger.error(planId + "有多个月度开票计划");
			result.addErrorMessage("该计划已经在多个月有月度开票计划,不能取消开票");
			return result;
		}else if(mbList.size() == 1){
			MonthlyBillpro mb = mbList.get(0);
			//计划内的更新
			if(mb.getIsInsidePlan() == 0){
				mb.setActualBillDate(null);
				mb.setPlanBillAmount(0.0);
				commonDao.update(mb);
			}else{
				//计划外的删除
				commonDao.delete(mb);
			}
		}
		// 删除发票
		commonDao
		.executeUpdate(
				"delete from InvoiceInfo ii where exists ( select 1 from BillandProRelaion br where br.applyBill = ii.applyInvoiceId and br.realContractBillandRecePlan = ? )",
				planId);
		// 删除申请单
		commonDao
		.executeUpdate(
				"delete from ApplyBill ab where exists ( select 1 from BillandProRelaion br where br.applyBill = ab.billApplyId and br.realContractBillandRecePlan = ? )",
				planId);
		// 删除计划-申请单关联
		commonDao
		.executeUpdate(
				"delete from BillandProRelaion br where br.realContractBillandRecePlan = ? ",
				planId);
		// 清除计划的统计字段
		plan.setBillInvoiceAmount(0.0);
		if(!plan.getBillType().equals("4")){
			plan.setShouldReceAmount(0.0);
		}else{
			//如果是收据，要重新算应收
			contractCommonService.updateReciptShouldAmount(plan);
		}
		plan.setRealNowBillDate(null);
		plan.setApplyBillState(null);
		commonDao.update(plan);
		// 更新项目的统计字段
		commonDao.flushSession();
		ContractItemMaininfo itemMain = null;
		if (plan.getContractItemMaininfo() != null) {
			itemMain = (ContractItemMaininfo) commonDao.load(
					ContractItemMaininfo.class, plan.getContractItemMaininfo());
			if (itemMain != null) {
				Number billAmount = (Number) commonDao
				.uniqueResult(
						"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.billType <> '4' and p.contractItemMaininfo = ? ",
						plan.getContractItemMaininfo());
				Number receiptAmount = (Number) commonDao
				.uniqueResult(
						"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where billType = '4' and p.contractItemMaininfo = ? ",
						plan.getContractItemMaininfo());
				Number shouldAmount = (Number) commonDao
				.uniqueResult(
						"select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? ",
						plan.getContractItemMaininfo());
				itemMain.setBillInvoiceAmount(getDefaultIfNull(billAmount));
				itemMain.setBillReceiptAmount(getDefaultIfNull(receiptAmount));
				itemMain.setShouldReceAmount(getDefaultIfNull(shouldAmount));
				commonDao.update(itemMain);
			}
		}
		// 更新合同的统计字段
		ContractMainInfo contract = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		Number billAmount = (Number) commonDao
		.uniqueResult(
				"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.billType <> '4' and p.conMainInfoSid = ? ",
				plan.getConMainInfoSid());
		Number receiptAmount = (Number) commonDao
		.uniqueResult(
				"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where billType = '4' and p.conMainInfoSid = ? ",
				plan.getConMainInfoSid());
		Number shouldAmount = (Number) commonDao
		.uniqueResult(
				"select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? ",
				plan.getConMainInfoSid());
		contract.setBillInvoiceAmount(getDefaultIfNull(billAmount));
		contract.setBillReceiptAmount(getDefaultIfNull(receiptAmount));
		contract.setShouldReceAmount(getDefaultIfNull(shouldAmount));
		commonDao.update(contract);
		result.setSuccess(true);
		return result;
	}

	public void cancelPlanRece(long planId, String contractCode, String saleName) {
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, planId);
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		Employee saleMain = (Employee) commonDao.load(Employee.class, mainInfo
				.getSaleMan());
		if (!StringUtils.equals(mainInfo.getConId(), contractCode)) {
			logger.error(planId + ":合同号不一致" + contractCode + "-"
					+ mainInfo.getConId());
			return;
		}
		if (!StringUtils.equals(saleMain.getName(), saleName)) {
			logger.error(planId + ":销售员不一致" + saleName + "-"
					+ saleMain.getName());
			return;
		}
		cancelPlanRece(planId);
	}

	public ProcessResult cancelPlanRece(long planId) {
		ProcessResult result = new ProcessResult();
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, planId);
		if(plan.getRealArriveAmount() == null || plan.getRealArriveAmount().doubleValue() == 0 ){
			logger.error(planId + "该计划没有收款");
			result.addErrorMessage("该计划没有收款");
			return result;
		}
		// 如果有月度计划,需要手工修正
		List<MonthlyRecepro> mrList = commonDao.list("from MonthlyRecepro mr where mr.realContractBillandRecePlan = ? ", plan);
		if(mrList.size() > 1){
			logger.error(planId + "有多个月度计划");
			result.addErrorMessage("该计划在多个月份有月度计划，不能取消收款");
			return result;
		}else if(mrList.size() == 1){
			MonthlyRecepro mr = mrList.get(0);
			//计划内的更新
			if(mr.getIsInsidePlan() == 0){
				mr.setActualArrivedDate(null);
				mr.setAlreadyArrivedAmount(0.0);
				commonDao.update(mr);
			}else{
				//外的删除
				commonDao.delete(mr);
			}
		}
		//删除计划收款记录
		List<PlanReceInfo> planReceInfoList = commonDao.list("from PlanReceInfo pi where pi.planId = ? )", planId);
		if(planReceInfoList.size() > 1){
			logger.error(planId + "有多次收款，需要手工处理");
			result.addErrorMessage("该计划有多次收款，需要手工处理");
			return result;
		}else if(planReceInfoList.size() == 1){
			//删除项目收款记录
			ReveInfo reveInfo = (ReveInfo) commonDao.load(ReveInfo.class, planReceInfoList.get(0).getReveInfoId());
			if(reveInfo.getAmount().equals(planReceInfoList.get(0).getAmount())){
				commonDao.delete(planReceInfoList.get(0));
				commonDao.delete(reveInfo);
			}else{
				logger.error(planId + "和其他项目公用了一次收款，需要手工处理");
				result.addErrorMessage("该计划只占了一次到款记录的部分金额，需要手工处理");
				return result;
			}
		}else{
			//删除项目收款记录
			List<ReveInfo> reveInfoList = commonDao.list("from ReveInfo ri where ri.conMainInfoSid = ? and ( ri.billSid is null or ri.billSid = ? ) and ri.amount = ? ", plan.getConMainInfoSid(),plan.getContractItemMaininfo(),plan.getRealArriveAmount());
			if(reveInfoList.size() == 0 ){
				logger.error(planId + "没有找到收款信息，需要手工处理");
				result.addErrorMessage("该计划没有找到收款记录，不能取消收款");
				return result;
			}else if(reveInfoList.size() > 1){
				logger.error(planId + "该计划对应的到款记录有多条，需要手工处理");
				result.addErrorMessage("该计划对应的到款记录有多条，需要手工处理");
				return result;
			}
		}
		//清除统计字段
		plan.setCurrentArriveAmount(0.0);
		plan.setRealArriveAmount(null);
		plan.setRealNowReceDate(null);
		commonDao.update(plan);
		// 更新项目的统计字段
		commonDao.flushSession();
		ContractItemMaininfo itemMain = null;
		if (plan.getContractItemMaininfo() != null) {
			itemMain = (ContractItemMaininfo) commonDao.load(
					ContractItemMaininfo.class, plan.getContractItemMaininfo());
			if (itemMain != null) {
				Number realArriveAmount = (Number) commonDao
				.uniqueResult(
						"select sum(p.realArriveAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? ",
						plan.getContractItemMaininfo());
				itemMain.setRealArriveAmount(getDefaultIfNull(realArriveAmount));
				commonDao.update(itemMain);
			}
		}
		// 更新合同的统计字段
		ContractMainInfo contract = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, plan.getConMainInfoSid());
		Number realArriveAmount = (Number) commonDao
		.uniqueResult(
				"select sum(p.realArriveAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? ",
				plan.getConMainInfoSid());
		contract.setRealArriveAmount(getDefaultIfNull(realArriveAmount));
		commonDao.update(contract);		

		if(contract.getContractType().equals("1")){
			contractService.itemIsCloseByItem(plan.getContractItemMaininfo());
		}else{
			contractService.contractIsCloseByContractId(plan.getConMainInfoSid());
		}
		harvestService.modifyInfo(plan.getConMainInfoSid(), plan.getContractItemMaininfo() );
		result.setSuccess(true);
		return result;
	}



	/**
	 * 取消收款by收款
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult cancelReceByRece(long receId){
		ProcessResult result = new ProcessResult();

		//获取收款信息
		ReveInfo receInfo = (ReveInfo) commonDao.load(ReveInfo.class,receId );
		//获取月度计划
		List<MonthlyRecepro> mrListX =  commonDao.list(" select mr   from PlanReceInfo pi , RealContractBillandRecePlan r ,  MonthlyRecepro mr " +
				"   where  pi.planId = r.realConBillproSid    " +
				" and   r.realConBillproSid  = mr.realContractBillandRecePlan   " +
				" and  pi.reveInfoId = ? " +
				"  order by r.realPredReceDate desc)",receInfo.getId());
		//更新月度计划
		Double balance = receInfo.getAmount();
		for( MonthlyRecepro mr: mrListX ){
			balance = balance - mr.getAlreadyArrivedAmount();
			if( balance>=0 ){
				if( mr.getIsInsidePlan() == 0){  //计划内的更新
					mr.setActualArrivedDate(null);
					mr.setAlreadyArrivedAmount(0.0);
					commonDao.update(mr);
				}
				else{
					//外的删除
					commonDao.delete(mr);
				}	
				if( balance==0 ){
					break;
				}
			}
			else{    //balance<0  说明计划中的金额>收款金额，故不需理会 计划内or计划外
				Date maxReceDate = this.getMaxReceDate(  receInfo.getId() , mr.getRealContractBillandRecePlan().getRealConBillproSid()  );
				mr.setActualArrivedDate(maxReceDate);
				mr.setAlreadyArrivedAmount(balance*-1);
				commonDao.update(mr);
				break;
			}
		}
		//////////////add by xusheng ////////////
		//获取 实际计划-收款关联
		List<PlanReceInfo> planReceInfoList = commonDao.list("from PlanReceInfo pi where pi.reveInfoId = ? order by pi.amountTime desc )",receInfo.getId());
		//获取实际计划
		List<RealContractBillandRecePlan> rcbrpList = commonDao.list(" select r  from PlanReceInfo pi , RealContractBillandRecePlan r  " +
				"   where  pi.planId = r.realConBillproSid  " +
				"  and  pi.reveInfoId = ? " +
				" order by r.realPredReceDate desc)",receInfo.getId());
		for (PlanReceInfo planReceInfo : planReceInfoList) {
			commonDao.delete(planReceInfo);
		}
		commonDao.flushSession();
		for(RealContractBillandRecePlan realPlan : rcbrpList){
			String planHql = "select sum(pi.amount),max(pi.amountTime) from  PlanReceInfo pi,RealContractBillandRecePlan rp " +
					"where pi.planId = rp.realConBillproSid and rp.realConBillproSid = ?";
			Object[] planResult = (Object[])commonDao.uniqueResult(planHql, realPlan.getRealConBillproSid());
			Double realArriveAmount = null;
			if(planResult[0] != null){
				realArriveAmount = (Double)planResult[0];
			}
			
			Date realArriveDate = null;
			if(planResult[1] != null ){
				realArriveDate = (Date)planResult[1];
			}
			if(realArriveAmount == null ){
				realPlan.setRealArriveAmount(null);
			}else{
				realPlan.setRealArriveAmount(BigDecimal.valueOf(realArriveAmount));
			}
			realPlan.setRealNowReceDate(realArriveDate);
			commonDao.update(realPlan);
		}
		
		/*
		 * 删除收款信息
		 */
		commonDao.delete(receInfo);
		
		///////////////end////////
		
		/*
		 *  修改合同...到款总额
		 */
		ContractMainInfo contract = (ContractMainInfo) commonDao.load( 	ContractMainInfo.class, receInfo.getConMainInfoSid());
		Number realArriveAmountCon = (Number) commonDao.uniqueResult(
				"select sum(p.realArriveAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? ", receInfo.getConMainInfoSid());
		contract.setRealArriveAmount(getDefaultIfNull(realArriveAmountCon));
		commonDao.update(contract);		

		/*
		 *  修改项目...到款总额
		 */
		ContractItemMaininfo itemMain = null;
		if (StringUtils.equals(contract.getContractType(), "1")) {
			itemMain = (ContractItemMaininfo) commonDao.load( ContractItemMaininfo.class,  receInfo.getBillSid() );
			Number realArriveAmountItem = (Number) commonDao.uniqueResult(
					"select sum(p.realArriveAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? ", receInfo.getBillSid() );
			itemMain.setRealArriveAmount(getDefaultIfNull(realArriveAmountItem));
			commonDao.update(itemMain);
		}

		/*
		 * 处理INVOICEINFO
		 */
		harvestService.modifyInfo(receInfo.getConMainInfoSid(),receInfo.getBillSid());

		

		/*
		 * 合同是否能关闭
		 */
		if(contract.getContractType().equals("1")){
			contractService.itemIsCloseByItem(receInfo.getBillSid());
		}else{
			contractService.contractIsCloseByContractId(receInfo.getConMainInfoSid());
		}

		result.setSuccess(true);
		return result;
	}

	private Date getMaxReceDate(  Long reveId , Long planId ){
		Date maxReceDate = (Date)commonDao.uniqueResult("  select max(r.amountTime)  from  ReveInfo r , PlanReceInfo p " +
				"  where r.id = p.reveInfoId and  r.id<>?   and  p.planId = ?  ", reveId   ,planId );
		return maxReceDate;
	}

	private Double getDefaultIfNull(Number n) {
		if (n != null) {
			return n.doubleValue();
		} else {
			return 0.0;
		}
	}

	private String getRowMessage(RowResult result) {
		return " 行:" + (result.getRow() + 1) + " ";
	}

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	public RealContractBillandRecePlan getPlan(Long id) {
		return (RealContractBillandRecePlan) commonDao.load(
				RealContractBillandRecePlan.class, id);
	}
	public void updateAndCal(Long planId){
		/**
		 * 通过计划id去关联表中查找申请单
		 */
		String relationHql="select bill from BillandProRelaion relation,ApplyBill bill where bill.billApplyId=relation.applyBill and relation.realContractBillandRecePlan=?";
		ApplyBill bill=(ApplyBill)commonDao.uniqueResult(relationHql, planId);
		/**
		 * load出计划的对象
		 */
		RealContractBillandRecePlan plan = getPlan(planId);
		///计算应收金额
		contractCommonService.calBillInvoiceAmount(bill.getBillApplyId());
		//修改计划里面的统计字段
		if(plan.getRealNowBillDate()!=null){
			contractCommonService.updatePlanStatistic(plan.getRealNowBillDate(), plan.getRealConBillproSid(), false);
		}
	}



	public void importDueFromCompare(Sheet compareSheet) throws Exception{
		Cell cell = compareSheet.getCell(0, 0);
		String format = "yyyy-MM-dd";
		DateCellValueConvertor dateCell = new DateCellValueConvertor(format);
		Date tempDate = dateCell.getCellValue(cell);
		Date d = NumberToTime.getStringFormatDate(NumberToTime.getDateFormat(tempDate, format), format);
		RowRuleImpl rowRule = new RowRuleImpl();
		// //////////////////////////////////////////////设置读取规则
		rowRule.addCellRule(new CellRuleImpl("A", "customerName"));
		rowRule.addCellRule(new CellRuleImpl("B", "orderOrItemNo"));
		rowRule.addCellRule(new CellRuleImpl("C", "itemName"));
		rowRule.addCellRule(new CellRuleImpl("E", "saleManName"));
		rowRule.addCellRule(new CellRuleImpl("F", "billFee",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("L", "logicDayAccountAge",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("M", "logicMonthAccountAge",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("T", "firstThreeMonth",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("U", "secondThreeMonth",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("V", "thridSixMonth",new NumberCellValueConvertor()));
		rowRule.addCellRule(new CellRuleImpl("W", "blowOneYear",new NumberCellValueConvertor()));

		/////////////////////////////////////////////////
		JExcelRowObjectBuilder reveBuilder = new JExcelRowObjectBuilder();
		reveBuilder.setSheet(compareSheet);
		reveBuilder.setTargetClass(ExcelImportDueFromComp.class);
		reveBuilder.setRule(2, compareSheet.getRows(), rowRule);
		RowResult<ExcelImportDueFromComp>[] cons = reveBuilder.parseExcel();
		for (RowResult<ExcelImportDueFromComp> rowResult : cons) {
			if (!rowResult.hasErrors()) {
				ExcelImportDueFromComp excelCompare = rowResult.getRowObject();
				ImportDueFromCompare dueFromCompare = new ImportDueFromCompare();
				BeanUtils.copyProperties(dueFromCompare, excelCompare);
				dueFromCompare.setBaseDate(d);
				dueFromCompare.setOpPerson(UserUtils.getUser().getId());
				commonDao.save(dueFromCompare);
			}
		}

	}

	public ProcessResult checkDueFromCompare(Long compareId){
		ProcessResult result = new ProcessResult();
		result.setErrorCode(0);
		result.setSuccess(Boolean.TRUE);
		ImportDueFromCompare compare = (ImportDueFromCompare)commonDao.load(ImportDueFromCompare.class, compareId);
		Long opPerson = UserUtils.getUser().getId();
		/**
		 * step.1判断长度
		 */
		String orderOrItemNo = compare.getOrderOrItemNo();
		if(StringUtils.isBlank(orderOrItemNo)){
			result.setErrorCode(7);
			result.addErrorMessage("订单号/项目号不符合规则!");
			result.setSuccess(Boolean.FALSE);
			return result;
		}
		if(!(orderOrItemNo.trim().length() == 5 || orderOrItemNo.trim().length() == 8)){
			result.setErrorCode(7);
			result.addErrorMessage("订单号/项目号不符合规则!");
			result.setSuccess(Boolean.FALSE);
			return result;
		}
		/**
		 * step.2判断订单号/项目号是否存在
		 */
		ContractMainInfo contract =null;
		if(orderOrItemNo.trim().length() == 5 ){///如果订单号/项目号是5位，则是集成类的合同号。如果是8位则是项目类的项目号
			String contractHql = " from ContractMainInfo minfo where minfo.conId = ?";
			contract = (ContractMainInfo)commonDao.uniqueResult(contractHql, orderOrItemNo.trim());
			if(contract == null){
				result.setErrorCode(1);
				result.addErrorMessage("订单号/项目号不存在!");
				result.setSuccess(Boolean.FALSE);
				return result;
			}
		}
		ContractItemMaininfo itemMain = null;
		if(orderOrItemNo.trim().length() == 8 ){
			String itemHql = "from ContractItemMaininfo item where item.conItemId = ?";
			itemMain = (ContractItemMaininfo)commonDao.uniqueResult(itemHql, orderOrItemNo.trim());
			if(itemMain == null){
				result.setErrorCode(1);
				result.addErrorMessage("订单号/项目号不存在!");
				result.setSuccess(Boolean.FALSE);
				return result;
			}
		}

		/**
		 * step3.查找销售人员不一致
		 */

		String[] sale = compare.getSaleManName().split(",");
		String saleMan = sale[0];
		if(orderOrItemNo.trim().length() == 5 ){//集成类直接拿合同比较
			String saleHql = "select count(*) from ContractMainInfo minfo,Employee emp where " +
			"minfo.saleMan = emp.id and minfo.conMainInfoSid = ? and emp.name = ?";
			Number count = (Number)commonDao.uniqueResult(saleHql, contract.getConMainInfoSid(),saleMan);
			if(count ==null || count.longValue() <= 0){
				result.setErrorCode(2);
				Employee emp = (Employee)commonDao.load(Employee.class, contract.getSaleMan());
				result.addErrorMessage("销售员不一致!系统中为："+emp.getName());
				result.setSuccess(Boolean.FALSE);
				return result;
			}
		}else if(orderOrItemNo.trim().length() == 8){
			String saleHql = "select count(*) from ContractMainInfo minfo,Employee emp," +
			"ContractItemMaininfo item  where " +
			"minfo.saleMan = emp.id and minfo.conMainInfoSid = item.contractMainInfo " +
			" and item.conItemMinfoSid = ? and emp.name = ?";
			Number count = (Number)commonDao.uniqueResult(saleHql, itemMain.getConItemMinfoSid(),saleMan);
			if(count ==null || count.longValue() <= 0){
				ContractMainInfo mInfo = (ContractMainInfo)commonDao.uniqueResult("select minfo from ContractMainInfo minfo,ContractItemMaininfo item " +
						" where item.contractMainInfo = minfo.conMainInfoSid and item.conItemMinfoSid = ?", itemMain.getConItemMinfoSid());
				result.setErrorCode(2);
				Employee emp = (Employee)commonDao.load(Employee.class, mInfo.getSaleMan());
				result.addErrorMessage("销售员不一致!系统中为："+emp.getName());
				result.setSuccess(Boolean.FALSE);
				return result;
			}
		}
		/**
		 * step.4检查发票余额是否等于系统中的余额
		 */
		String sumComBillFeeHql = "select sum(ic.billFee) from ImportDueFromCompare ic where ic.orderOrItemNo = ? and ic.opPerson = ?";
		Number sumCombillFeeNumber = (Number)commonDao.uniqueResult(sumComBillFeeHql, orderOrItemNo,opPerson);
		Double sumComBillFeeAmount = 0.00;

		if(sumCombillFeeNumber!=null){
			sumComBillFeeAmount = sumCombillFeeNumber.doubleValue();
		}
		if(orderOrItemNo.trim().length() == 5){
			String invoiceHql = "select sum (invoice.invoiceAmount) from InvoiceInfo invoice,ContractMainInfo minfo " +
			" where invoice.contractMainSid = minfo.conMainInfoSid " +
			" and invoice.inputState = '1' " +
			" and invoice.type != '4' " +
			" and trunc(invoice.invoiceDate,'dd') <= ? " +
			" and minfo.conMainInfoSid = ?";
			Number invoiceAmountNumber = (Number)commonDao.uniqueResult(invoiceHql, compare.getBaseDate(),contract.getConMainInfoSid());
			Double invoiceAmount = 0.00;
			if(invoiceAmountNumber != null ){
				invoiceAmount = invoiceAmountNumber.doubleValue();
			}
			String reveHql = "select sum(reve.amount) from ReveInfo reve, ContractMainInfo minfo " +
			" where reve.conMainInfoSid = minfo.conMainInfoSid and reve.hasSure = '1' " +
			" and trunc(reve.amountTime,'dd') <= ? " +
			" and minfo.conMainInfoSid = ?";
			Number amountNumber = (Number)commonDao.uniqueResult(reveHql, compare.getBaseDate(),contract.getConMainInfoSid());
			Double amount = 0.00;
			if(amountNumber != null ){
				amount = amountNumber.doubleValue();
			}
			if(Math.abs(invoiceAmount - amount-sumComBillFeeAmount) > 0.01){
				result.setErrorCode(4);
				result.addErrorMessage("应收不一致!系统中为："+BigDecimalUtils.doubleToString(invoiceAmount - amount)+"!");
				result.setSuccess(Boolean.FALSE);
				return result;
			}
		}else if(orderOrItemNo.trim().length() == 8){
			ContractMainInfo mInfo = (ContractMainInfo)commonDao.uniqueResult("select minfo from ContractMainInfo minfo,ContractItemMaininfo item " +
					" where item.contractMainInfo = minfo.conMainInfoSid and item.conItemMinfoSid = ?", itemMain.getConItemMinfoSid());
			String invoiceHql = "select sum (invoice.invoiceAmount) from InvoiceInfo invoice," +
			" ContractMainInfo minfo, " +
			" ContractItemMaininfo item, " +
			" ApplyBill apply, " +
			" BillandProRelaion relation, " +
			" RealContractBillandRecePlan p " +
			" where invoice.contractMainSid = minfo.conMainInfoSid " +
			" and invoice.applyInvoiceId = apply.billApplyId " +
			" and relation.applyBill = apply.billApplyId " +
			" and relation.realContractBillandRecePlan = p.realConBillproSid " +
			" and p.contractItemMaininfo = item.conItemMinfoSid " +
			" and p.conMainInfoSid = minfo.conMainInfoSid" +
			" and invoice.inputState = '1' " +
			" and invoice.type != '4' " +
			" and trunc(invoice.invoiceDate,'dd') <= ? " +
			" and minfo.conMainInfoSid = ? " +
			" and item.conItemMinfoSid = ?";
			Number invoiceAmountNumber = (Number)commonDao.uniqueResult(invoiceHql, compare.getBaseDate(),mInfo.getConMainInfoSid(),itemMain.getConItemMinfoSid());
			Double invoiceAmount = 0.00;
			if(invoiceAmountNumber != null ){
				invoiceAmount = invoiceAmountNumber.doubleValue();
			}
			String reveHql = "select sum(reve.amount) from ReveInfo reve, ContractMainInfo minfo,ContractItemMaininfo item " +
			" where reve.conMainInfoSid  = minfo.conMainInfoSid " +
			" and reve.billSid = item.conItemMinfoSid and reve.hasSure = '1' " +
			" and trunc(reve.amountTime,'dd') <= ? " +
			" and minfo.conMainInfoSid = ? and item.conItemMinfoSid = ?";
			Number amountNumber = (Number)commonDao.uniqueResult(reveHql, compare.getBaseDate(),mInfo.getConMainInfoSid(),itemMain.getConItemMinfoSid());
			Double amount = 0.00;
			if(amountNumber != null ){
				amount = amountNumber.doubleValue();
			}
			if(Math.abs(invoiceAmount - amount -sumComBillFeeAmount) > 0.01){
				result.setErrorCode(4);
				result.addErrorMessage("应收不一致!系统中为："+BigDecimalUtils.doubleToString(invoiceAmount - amount)+"!");
				result.setSuccess(Boolean.FALSE);
				return result;
			}
		}

		return result;
	}


}

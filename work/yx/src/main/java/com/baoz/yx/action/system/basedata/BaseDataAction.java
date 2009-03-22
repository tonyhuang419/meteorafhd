package com.baoz.yx.action.system.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IBaseData;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.MonthHarvestPlanService;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.ProcessResult;

@Results( {
		@Result(name = "success", value = "/WEB-INF/jsp/system/basedata/mainShow.jsp"),
		@Result(name = "leftShow", value = "/WEB-INF/jsp/system/basedata/leftShow.jsp"),
		@Result(name = "opBillByPlan", value = "/WEB-INF/jsp/system/basedata/opBillByPlan.jsp"),
		@Result(name = "contentMain", value = "/WEB-INF/jsp/system/basedata/contentMain.jsp"),
		@Result(name = "cancelBillAndRece", value = "/WEB-INF/jsp/system/basedata/cancelBillAndRece.jsp"),
		@Result(name = "relationBillAndReceiptPlan", value = "/WEB-INF/jsp/system/basedata/relationBillAndReceiptPlan.jsp"),
		@Result(name = "releaseRelationBillAndReceiptPlan", value = "/WEB-INF/jsp/system/basedata/releaseRelationBillAndReceiptPlan.jsp"),
		@Result(name = "modifyContractBaseInfoList", value = "/WEB-INF/jsp/system/basedata/modifyContractBaseInfoList.jsp"),
		@Result(name = "listNoBillApplyPlan", value = "/WEB-INF/jsp/system/basedata/listNoBillApplyPlan.jsp"),
		@Result(name = "editNoApplyBillPlan", value = "/WEB-INF/jsp/system/basedata/editNoApplyBillPlan.jsp"),
		@Result(name = "hongChongBill", value = "/WEB-INF/jsp/system/basedata/hongChongBill.jsp"),
		@Result(name = "updateNoApplyBillPlan", value = "/commons/jsp/closeAndCallBackOpener.jsp"),
		@Result(name = "cancelReceByReceQuery", value = "/WEB-INF/jsp/system/basedata/cancelReceByReceQuery.jsp"),
		@Result(name = "cancelMonthBillQuery", value = "/WEB-INF/jsp/system/basedata/cancelMonthBillQuery.jsp"),
		@Result(name = "cancelMonthReceQuery", value = "/WEB-INF/jsp/system/basedata/cancelMonthReceQuery.jsp") })
public class BaseDataAction extends DispatchAction {

	private String conId;

	private String itemId;

	private String invoiceId;

	private Long invoiceIds;

	private Date preBillDate;

	private Date preReceDate;

	private Long planId;

	private Long receId;

	private Long conSid;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;

	@Autowired
	@Qualifier("monthHarvestPlanService")
	private MonthHarvestPlanService monthHarvestPlanService;

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;

	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService billService;

	@Autowired
	@Qualifier("baseDate")
	private IBaseData baseDate;

	private List<Object[]> planList = new ArrayList<Object[]>();// 计划列表

	private PageInfo info;

	private String[] xuanze;

	private ProcessResult processResult;

	private String proSids;

	private String sign; // noFind 不做查询，跳过HQL

	private RealContractBillandRecePlan noApplyBillPlan;

	private Map<Long, List<String>> itemNoList; // 开票申请系统号，项目号

	private String alertInfo;

	// 主页面
	public String doDefault() throws Exception {
		return SUCCESS;
	}

	// 左边的显示超链接的地方
	public String leftShow() throws Exception {
		return "leftShow";
	}

	// 点击第一个按钮进来的页面
	public String opBillByPlan() throws Exception {

		return "opBillByPlan";
	}

	public String queryPlanByconId() throws Exception {

		String select = "select r,yc,cm,"
				+ "(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid),"
				+ "(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage),"
				+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
				+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , "
				+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
				+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ),e ";
		StringBuffer from = new StringBuffer();
		from
				.append("from RealContractBillandRecePlan r,Employee e,YXClientCode yc,ContractMainInfo cm,YXTypeManage ym where "
						+ "cm.conCustomer = yc.id "
						+ "and r.conMainInfoSid = cm.conMainInfoSid "
						+ "and r.billType = ym.typeSmall "
						+ "and ym.typeBig = 1004 " + "and cm.saleMan = e.id ");
		if (StringUtils.isNotBlank(conId)) {
			from.append(" and lower(cm.conId) = '").append(
					StringUtils.lowerCase(StringUtils.trim(conId))).append("'");
			from.append(" order by r.realPredBillDate");
			info = queryService.listQueryResult("select count(*) " + from,
					select + from, info);
		}

		return "opBillByPlan";
	}

	public String confirm() throws Exception {
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonService
				.load(RealContractBillandRecePlan.class, planId);
		boolean fullBill = false;
		boolean fullRece = false;
		for (int i = 0; i < xuanze.length; i++) {
			if (StringUtils.equals(xuanze[i], "0")) {
				fullBill = true;
			}
			if (StringUtils.equals(xuanze[i], "1")) {
				fullRece = true;
			}
		}
		processResult = importHisDataService.createFullBillAndRecePlan(plan,
				fullBill, fullRece, preBillDate, preReceDate);
		return queryPlanByconId();
	}

	// 显示主页面! 1
	public String contentMain() throws Exception {
		return "contentMain";
	}

	public String checkIsReceAndRelationAmount() throws Exception {
		Long[] monthlyBillproSids = { planId };
		if (!billService.judgementBill(monthlyBillproSids)) {// 收款金额+关联金额=开票金额
			this.renderText("false");// 要去关联收据
		} else {
			this.renderText("true");// 不用关联收据
		}
		return null;
	}

	public String cancelBillAndRece() throws Exception {
		queryPlanByconId();
		return "cancelBillAndRece";
	}

	// 取消月度开票Query
	public String cancelMonthBillQuery() throws Exception {
		info = ParameterUtils.preparePageInfo(info, "cancelMonthBillQuery");
		if (this.processSign()) {
			info = baseDate.cancelMonthBillQuery(info, conId);
		}
		return "cancelMonthBillQuery";
	}

	// 取消月度收款Query
	public String cancelMonthReceQuery() throws Exception {
		info = ParameterUtils.preparePageInfo(info, "cancelMonthReceQuery");
		if (this.processSign()) {
			info = baseDate.cancelMonthReceQuery(info, conId);
		}
		return "cancelMonthReceQuery";
	}

	// 取消月度开票
	public String cancelMonthBill() throws Exception {
		processResult = baseDate.cancelMonthBill(planId);
		return cancelMonthBillQuery();
	}

	// 取消月度收款
	public String cancelMonthRece() throws Exception {
		processResult = baseDate.cancelMonthRece(planId);
		return cancelMonthReceQuery();
	}

	public String cancelReceByRece() throws Exception {
		logger.info(receId);
		processResult = importHisDataService.cancelReceByRece(receId);
		return cancelReceByReceQuery();
	}

	public String cancelBill() throws Exception {
		processResult = importHisDataService.cancelPlanBill(planId);
		return cancelBillAndRece();
	}

	public String cancelRece() throws Exception {
		processResult = importHisDataService.cancelPlanRece(planId);
		return cancelBillAndRece();
	}

	public String relationBillAndReceiptPlan() throws Exception {
		String select = "select r,yc,cm,"
				+ "(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid),"
				+ "(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage),"
				+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
				+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , "
				+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
				+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ),e ";
		StringBuffer from = new StringBuffer();
		from
				.append("from RealContractBillandRecePlan r,Employee e,YXClientCode yc,ContractMainInfo cm,YXTypeManage ym where "
						+ "cm.conCustomer = yc.id "
						+ "and r.conMainInfoSid = cm.conMainInfoSid "
						+ "and r.billType = ym.typeSmall "
						+ "and ym.typeBig = 1004 "
						+ "and cm.saleMan = e.id "
						+ "and r.realTaxBillAmount - r.realTaxReceAmount > decode(r.relationReceAmount,null,0,r.relationReceAmount) "
						+ "and r.billInvoiceAmount > 0 "
						+ "and r.billType <> '4' " + "and cm.conState <> 10 ");
		if (StringUtils.isNotBlank(conId)) {
			from.append(" and lower(cm.conId) = '").append(
					StringUtils.lowerCase(StringUtils.trim(conId))).append("'");
		}
		from.append(" order by r.realPredBillDate");
		info = queryService.listQueryResult("select count(*) " + from, select
				+ from, info);
		return "relationBillAndReceiptPlan";
	}

	public String releaseRelationBillAndReceiptPlan() throws Exception {
		if (this.processSign()) {
			String select = "select r,yc,cm,"
					+ "(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid),"
					+ "(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage),"
					+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
					+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , "
					+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
					+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ),e ";
			StringBuffer from = new StringBuffer();
			from
					.append("from RealContractBillandRecePlan r,Employee e,YXClientCode yc,ContractMainInfo cm,YXTypeManage ym where "
							+ "cm.conCustomer = yc.id "
							+ "and r.conMainInfoSid = cm.conMainInfoSid "
							+ "and r.billType = ym.typeSmall "
							+ "and ym.typeBig = 1004 "
							+ "and cm.saleMan = e.id "
							+ "and r.relationReceAmount > 0  "
							+ "and r.billType <> '4' "
							+ "and cm.conState <> 10 ");
			if (StringUtils.isNotBlank(conId)) {
				from.append(" and lower(cm.conId) = '").append(
						StringUtils.lowerCase(StringUtils.trim(conId))).append(
						"'");
			}
			from.append(" order by r.realPredBillDate");
			info = queryService.listQueryResult("select count(*) " + from,
					select + from, info);
		}
		return "releaseRelationBillAndReceiptPlan";
	}

	public String cancelReceByReceQuery() throws Exception {
		info = ParameterUtils.preparePageInfo(info, "cancelReceByReceQuery");
		if (this.processSign()) {
			info = baseDate.cancelReceByReceQuery(info, conId);
		}
		return "cancelReceByReceQuery";
	}

	public String releaseRelationBillRecipt() throws Exception {
		contractCommonService.releaseRelationAndReceiptShouldAmount(Long
				.valueOf(proSids));
		return releaseRelationBillAndReceiptPlan();
	}

	public String modifyContractBaseInfo() throws Exception {
		info = ParameterUtils.preparePageInfo(info, "modifyContractBaseInfo");
		if (this.processSign()) {
			info = baseDate.queryModifyContractBaseInfo(info, conId, itemId);
		}
		return "modifyContractBaseInfoList";
	}

	public String toFormal() throws Exception {
		foramlContractService.modifyConState(conSid, 4L);
		alertInfo = "合同修改成功";
		return modifyContractBaseInfo();
	}

	public String toClose() throws Exception {
		foramlContractService.modifyConState(conSid, 10L);
		alertInfo = "合同修改成功";
		return modifyContractBaseInfo();
	}

	/**
	 * 没有申请单的计划，可以用来调整计划金额
	 * 
	 * @return
	 */
	public String listNoBillApplyPlan() {
		String select = "select r,yc,cm,"
				+ "(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid),"
				+ "(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage),"
				+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
				+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , "
				+ "(select count(*) from ApplyBill b ,BillandProRelaion br where "
				+ " b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ),e ";
		StringBuffer from = new StringBuffer();
		from
				.append("from RealContractBillandRecePlan r,Employee e,YXClientCode yc,ContractMainInfo cm,YXTypeManage ym where "
						+ "cm.conCustomer = yc.id "
						+ "and r.conMainInfoSid = cm.conMainInfoSid "
						+ "and r.billType = ym.typeSmall "
						+ "and ym.typeBig = 1004 "
						+ "and cm.saleMan = e.id "
						+ "and not exists (select 1 from BillandProRelaion br where br.realContractBillandRecePlan = r.realConBillproSid ) "
						+ "and ( r.realArriveAmount is null or r.realArriveAmount = 0 )"
						+ "and cm.conState <> 10 ");
		if (StringUtils.isNotBlank(conId)) {
			from.append(" and lower(cm.conId) = '").append(
					StringUtils.lowerCase(StringUtils.trim(conId))).append("'");
			from.append(" order by r.realPredBillDate");
			info = queryService.listQueryResult("select count(*) " + from,
					select + from, info);
		}
		return "listNoBillApplyPlan";
	}

	/**
	 * 查询红冲发票
	 * 
	 * @return
	 */
	public String hongChongBill() {
		String select = "select bill,cm,yc,emp,ii ";
		StringBuffer from = new StringBuffer();
		from
				.append("from InvoiceInfo ii,ApplyBill bill,ContractMainInfo cm,YXClientCode yc,Employee emp where "
						+ "cm.conCustomer = yc.id "
						+ "and ii.applyInvoiceId = bill.billApplyId "
						+ "and bill.contractMainInfo = cm.conMainInfoSid "
						+ "and emp.id = cm.saleMan ");
		if (StringUtils.isNotBlank(invoiceId)) {
			from.append(" and lower(ii.invoiceNo) = '").append(
					StringUtils.lowerCase(StringUtils.trim(invoiceId))).append(
					"'");
			info = queryService.listQueryResult("select count(*) " + from,
					select + from, info);

			List<Object[]> applyBillList = (List<Object[]>) info.getResult();
			if (applyBillList != null && applyBillList.size() > 0) {
				itemNoList = invoiceService
						.getItemNumFromApplyBilll(applyBillList);
			}
		}
		return "hongChongBill";
	}

	/**
	 * 执行红冲操作
	 * 
	 * @return
	 */
	public String hongChongBillService() {
		processResult = billService.returnInvoicePlan(invoiceIds);
		return hongChongBill();
	}

	public String editNoApplyBillPlan() {
		noApplyBillPlan = (RealContractBillandRecePlan) commonService.load(
				RealContractBillandRecePlan.class, planId);
		return "editNoApplyBillPlan";
	}

	public String updateNoApplyBillPlan() {
		processResult = contractCommonService
				.updateNoApplyBillPlan(noApplyBillPlan);
		return "updateNoApplyBillPlan";
	}

	private boolean processSign() {
		if (sign == null) {
			sign = "";
		}
		if (sign.equals("noFind")) {
			return false;
		} else {
			return true;
		}
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public Date getPreBillDate() {
		return preBillDate;
	}

	public void setPreBillDate(Date preBillDate) {
		this.preBillDate = preBillDate;
	}

	public Date getPreReceDate() {
		return preReceDate;
	}

	public void setPreReceDate(Date preReceDate) {
		this.preReceDate = preReceDate;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(
			IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<Object[]> getPlanList() {
		return planList;
	}

	public void setPlanList(List<Object[]> planList) {
		this.planList = planList;
	}

	public IApplyBillService getBillService() {
		return billService;
	}

	public void setBillService(IApplyBillService billService) {
		this.billService = billService;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getProSids() {
		return proSids;
	}

	public void setProSids(String proSids) {
		this.proSids = proSids;
	}

	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}

	public String[] getXuanze() {
		return xuanze;
	}

	public void setXuanze(String[] xuanze) {
		this.xuanze = xuanze;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	public RealContractBillandRecePlan getNoApplyBillPlan() {
		return noApplyBillPlan;
	}

	public void setNoApplyBillPlan(RealContractBillandRecePlan noApplyBillPlan) {
		this.noApplyBillPlan = noApplyBillPlan;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}

	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}

	public Long getInvoiceIds() {
		return invoiceIds;
	}

	public void setInvoiceIds(Long invoiceIds) {
		this.invoiceIds = invoiceIds;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public IBaseData getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(IBaseData baseDate) {
		this.baseDate = baseDate;
	}

	public Long getReceId() {
		return receId;
	}

	public void setReceId(Long receId) {
		this.receId = receId;
	}

	public MonthHarvestPlanService getMonthHarvestPlanService() {
		return monthHarvestPlanService;
	}

	public void setMonthHarvestPlanService(
			MonthHarvestPlanService monthHarvestPlanService) {
		this.monthHarvestPlanService = monthHarvestPlanService;
	}

	public String getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}

	public Long getConSid() {
		return conSid;
	}

	public void setConSid(Long conSid) {
		this.conSid = conSid;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}

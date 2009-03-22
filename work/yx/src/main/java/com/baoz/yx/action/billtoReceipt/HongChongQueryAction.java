package com.baoz.yx.action.billtoReceipt;

import java.math.BigDecimal;
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
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.bill.HongChongApplyBill;
import com.baoz.yx.entity.bill.HongChongInvoiceInfo;
import com.baoz.yx.entity.bill.HongChongRelationPlan;
import com.baoz.yx.entity.bill.RelationBillAndReceipt;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;
@Results({
			@Result(name="hongChongBill",value = "/WEB-INF/jsp/billtoReceipt/hongChongBill.jsp"),
			@Result(name="showQuery",value = "/WEB-INF/jsp/billtoReceipt/hongChongQuery.jsp"),
			@Result(name="confirmHongchong",value = "/WEB-INF/jsp/billtoReceipt/hongChongQuery.jsp"),
			@Result(name="showHongChongInfo",value = "/WEB-INF/jsp/billtoReceipt/hongChongInfo.jsp"),
			@Result(name="showManagerQuery",value = "/WEB-INF/jsp/billtoReceipt/hongChongManagerQuery.jsp"),
			@Result(name="showManagerMain",value = "/WEB-INF/jsp/billtoReceipt/hongChongManagerMain.jsp"),
			@Result(name="showHongChongManager",value = "/WEB-INF/jsp/billtoReceipt/hongChongManager.jsp"),
			@Result(name="showConfirmQuery",value = "/WEB-INF/jsp/billtoReceipt/hongChongConfirmQuery.jsp"),
			@Result(name="showConfirmMain",value = "/WEB-INF/jsp/billtoReceipt/hongChongConfirmMain.jsp"),
			@Result(name="showHongChongConfirm",value = "/WEB-INF/jsp/billtoReceipt/hongChongConfirm.jsp"),
			@Result(name="showReturnRelation",value = "/WEB-INF/jsp/billtoReceipt/returnHongChong.jsp"),
			@Result(name="showConfirmHongChong",value = "/WEB-INF/jsp/billtoReceipt/hongChongUpdateOrConfirm.jsp"),
			@Result(name="showMain",value = "/WEB-INF/jsp/billtoReceipt/hongChongMain.jsp")
		})
public class HongChongQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 										commonService;
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 			billService;
	
	private ProcessResult processResult;
	private PageInfo info;
	private Long[] invoiceId;
	private Long invoiceIds;
	private String invoiceNo;   //发票号
	private String contractNo;  //合同号
	private String itemNo;//项目号
	private Map<Long,List<String>> itemNoList;   //开票申请系统号，项目号
	private List<Object> invoiceBillList;
	private List<Object[]> hongChongPlanList;
	private String confirmHC;//判断是否是从确认页面上
	private String returnReason; //退回理由
	private String hongChongState;  //红冲状态
	private List<HongChongRelationPlan> hongChongRelation;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"hongChongQueryParameters",this,new String[]{"invoiceNo","contractNo","itemNo","hongChongState"});
	
	@Override
	public String doDefault() throws Exception {
		return null;
	}
	
	public String showQuery(){
		
		return "showQuery";
	}
	
	public String showMain(){
		return "showMain";
	}
	
	/**
	 * 显示红冲发票
	 * @return
	 */
	public String showHongChongBill(){
		ParameterUtils.prepareParameters(holdTool);
		String select = "select bill," +
				"(select cm from ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid)," +
				"(select yc from YXClientCode yc,ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid and cm.conCustomer = yc.id)," +
				"(select emp from Employee emp,ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid and emp.id = cm.saleMan),ii ";
		StringBuffer from = new StringBuffer();
		from.append("from InvoiceInfo ii,ApplyBill bill where " +
				" ii.applyInvoiceId = bill.billApplyId " +
				"and ii.inputState = 1 " +
				//"and bill.billType <> 4 " +
				"and bill.employeeId = " + UserUtils.getUser().getId());
		if(StringUtils.isNotBlank(invoiceNo)){
			from.append(" and lower(ii.invoiceNo) like '%").append(StringUtils.lowerCase(StringUtils.trim(invoiceNo))).append("%'");
		}
		if(StringUtils.isNotBlank(contractNo)){
			from.append(" and exists (select 1 from ContractMainInfo cm where cm.conMainInfoSid = bill.contractMainInfo and lower(cm.conId) like '%").append(StringUtils.lowerCase(StringUtils.trim(contractNo))).append("%' )");
		}
		if(StringUtils.isNotBlank(itemNo)){
			from.append(" and exists (select 1 from ContractItemMaininfo im,RealContractBillandRecePlan real,BillandProRelaion br " +
					"where lower(im.conItemId) " +
					"like '%"+StringUtils.lowerCase(StringUtils.trim(itemNo))+"%' " +
					"and real.contractItemMaininfo = im.conItemMinfoSid " +
					"and br.applyBill = bill.billApplyId " +
					"and br.realContractBillandRecePlan = real.realConBillproSid ) ");
		}
		from.append(" order by decode(ii.hongChongState,null,0,ii.hongChongState),ii.invoiceDate desc,bill.billApplyId DESC");
		info = ParameterUtils.preparePageInfo(info, "hongChongQueryPage");
		info=queryService.listQueryResult("select count(*) "+from, select+from, info);	
		
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			itemNoList = invoiceService.getItemNumFromApplyBilll(applyBillList);
		}
		return "hongChongBill";
	}
	
	/**
	 * 显示出发票信息提交申请
	 * @return
	 */
	public String showHongChongInfo(){
		invoiceBillList = billService.shouInvoiceInfo(invoiceId[0]);
		hongChongPlanList = billService.showPlanHongChong(invoiceId[0]);
		return "showHongChongInfo";
	}
	/**
	 * 红冲申请
	 * 提交申请到红冲申请表中
	 * @return
	 */
	public String hongChongApply(){
		processResult = billService.applyHongChong(invoiceIds, hongChongRelation);
		return showHongChongBill();
	}
	/**
	 * 显示红冲管理
	 * @return
	 */
	public String showHongChongManager(){
		showSameHongChong(false);
		return "showHongChongManager";
	}
	/**
	 * 显示确认
	 * @return
	 */
	public String showHongChongConfirm(){
		showSameHongChong(true);
		return "showHongChongConfirm";
	}
	private void showSameHongChong(boolean isConfirm) {
		ParameterUtils.prepareParameters(holdTool);
		String select = "select hbill," +
				"(select cm from ContractMainInfo cm where hbill.contractMainInfo = cm.conMainInfoSid)," +
				"(select yc from YXClientCode yc,ContractMainInfo cm where hbill.contractMainInfo = cm.conMainInfoSid and cm.conCustomer = yc.id)," +
				"(select emp from Employee emp,ContractMainInfo cm where hbill.contractMainInfo = cm.conMainInfoSid and emp.id = cm.saleMan)," +
				"hii ";
		StringBuffer from = new StringBuffer();
		from.append(" from HongChongApplyBill hbill,HongChongInvoiceInfo hii where " +
				" hii.hongChongBIllId = hbill.id  ");
		if(isConfirm){
			from.append(" and hbill.hongChongState <> '1' ");
		}
		else{
			from.append(" and hbill.employeeId = " + UserUtils.getUser().getId());
		}
		if(StringUtils.isNotBlank(hongChongState)){
			from.append(" and hbill.hongChongState = '"+ hongChongState+"' ");
		}
		else{
			if(confirmHC!=null){
				if(confirmHC.equals("1")){
					from.append(" and hbill.hongChongState in (3) ");
				}
				if(confirmHC.equals("2")){
					from.append(" and hbill.hongChongState in (1,3,4) ");
				}
			}
		}
		if(StringUtils.isNotBlank(invoiceNo)){
			from.append(" and lower(hii.invoiceNo) like '%").append(StringUtils.lowerCase(StringUtils.trim(invoiceNo))).append("%'");
		}
		if(StringUtils.isNotBlank(contractNo)){
			from.append(" and exists (select 1 from ContractMainInfo cm where cm.conMainInfoSid = hbill.contractMainInfo and lower(cm.conId) like '%").append(StringUtils.lowerCase(StringUtils.trim(contractNo))).append("%' )");
		}
		if(StringUtils.isNotBlank(itemNo)){
			from.append(" and exists (select 1 from ContractItemMaininfo im,RealContractBillandRecePlan real,BillandProRelaion br " +
					"where lower(im.conItemId) " +
					"like '%"+StringUtils.lowerCase(StringUtils.trim(itemNo))+"%' " +
					"and real.contractItemMaininfo = im.conItemMinfoSid " +
					"and br.applyBill = hbill.applyBillId " +
					"and br.realContractBillandRecePlan = real.realConBillproSid ) ");
		}
		from.append("order by decode(hbill.hongChongState,3,2,2,3,hbill.hongChongState),hbill.id DESC");
		info = ParameterUtils.preparePageInfo(info, "hongChongManagerPage");
		info = queryService.listQueryResult("select count(*) "+from, select+from,info);
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			itemNoList = invoiceService.getItemNumFromHongChongApplyBilll(applyBillList);
		}
	}
	/**
	 * 销售员提交红冲申请
	 * @return
	 */
	public String submitApply(){
		ProcessResult result = new ProcessResult();
		HongChongInvoiceInfo hii = (HongChongInvoiceInfo) commonService.load(HongChongInvoiceInfo.class, invoiceIds);
		HongChongApplyBill hbill = (HongChongApplyBill) commonService.load(HongChongApplyBill.class, hii.getHongChongBIllId());
		hbill.setHongChongState("3");  //已确认
		commonService.update(hbill);
		InvoiceInfo ii = (InvoiceInfo) commonService.load(InvoiceInfo.class, hii.getInvoiceId());
		ii.setHongChongState("3");
		commonService.update(ii);
		result.setSuccess(true);
		result.addSuccessMessage("已确认提交");
		processResult = result;
		return showHongChongManager();
	}
	/**
	 * 显示填写退回理由
	 * @return
	 */
	public String showReturnRelation(){
		return "showReturnRelation";
	}
	/**
	 * 退回红冲申请
	 * @return
	 */
	public String cancelSubmit(){
		ProcessResult result = new ProcessResult();
		HongChongInvoiceInfo hii = (HongChongInvoiceInfo) commonService.load(HongChongInvoiceInfo.class, invoiceIds);
		HongChongApplyBill hbill = (HongChongApplyBill) commonService.load(HongChongApplyBill.class, hii.getHongChongBIllId());
		hbill.setHongChongState("4");  //确认退回
		hbill.setReturnReason(returnReason);
		commonService.update(hbill);
		InvoiceInfo ii = (InvoiceInfo) commonService.load(InvoiceInfo.class, hii.getInvoiceId());
		ii.setHongChongState("4");
		commonService.update(ii);
		result.setSuccess(true);
		result.addSuccessMessage("已退回申请");
		processResult = result;
		return showHongChongConfirm();
	}
	
	/**
	 * 取消红冲申请,删除红冲申请单,红冲发票信息表,关联表,修改发票信息表状态
	 * @return
	 */
	public String cancelHongChongApply(){
		ProcessResult result = new ProcessResult();
		HongChongInvoiceInfo hii = (HongChongInvoiceInfo) commonService.load(HongChongInvoiceInfo.class, invoiceId[0]);
		HongChongApplyBill hbill = (HongChongApplyBill) commonService.load(HongChongApplyBill.class, hii.getHongChongBIllId());
		List<HongChongRelationPlan> brList = commonService.list(" from HongChongRelationPlan hr where hr.hongChongApplyBill = ? ", hbill.getId());
		for (HongChongRelationPlan hr : brList) {
			commonService.delete(hr);
		}
		InvoiceInfo ii = (InvoiceInfo) commonService.load(InvoiceInfo.class, hii.getInvoiceId());
		ii.setHongChongState(null);
		commonService.update(ii);
		commonService.delete(hbill);
		commonService.delete(hii);
		result.setSuccess(true);
		result.addSuccessMessage("已取消该发票红冲申请");
		processResult = result;
		return showHongChongManager();
	}
	/**
	 * 判断计划中realReceAmount金额减去红冲的金额是否大于0
	 * @return
	 */
	public String checkReceAmount(){
		hongChongPlanList = billService.showConfirmPlanInfo(invoiceIds);
		Double realBillAmount = 0.00;
		for (Object[] obj : hongChongPlanList) {
			RealContractBillandRecePlan realPlan = (RealContractBillandRecePlan) obj[0];
			realBillAmount += realPlan.getBillInvoiceAmount();
		}
		HongChongInvoiceInfo hii = (HongChongInvoiceInfo) commonService.load(HongChongInvoiceInfo.class, invoiceIds);
		if(hii.getInvoiceAmount().doubleValue() == realBillAmount){
			this.renderText("2");
			return null;
		}
		for (Object[] obj : hongChongPlanList) {
			RealContractBillandRecePlan plan = (RealContractBillandRecePlan) obj[0];
			//HongChongInvoiceInfo hi = (HongChongInvoiceInfo) commonService.load(HongChongInvoiceInfo.class, invoiceIds);
			if(BigDecimalUtils.defaultIfNull(plan.getRealReceAmount().doubleValue()) - hii.getInvoiceAmount() < 0){
				this.renderText("1");
			}else{
				this.renderText("0");
			}
		}
		return null;
	}
	
	/**
	 * 确认红冲时候显示明细查看确认红冲
	 * @return
	 */
	public String showConfirmHongChong(){
		invoiceBillList = billService.showConfirmInvoiceInfo(invoiceId[0]);
		hongChongPlanList = billService.showConfirmPlanInfo(invoiceId[0]);
		return "showConfirmHongChong";
	}
	/**
	 * 执行红冲操作
	 * @return
	 */
	public String hongChongBillService(){
		processResult = billService.returnInvoicePlan(invoiceIds);
		if(processResult.getErrorCode() == 1){
			logger.info(processResult.getErrorMessages());
		}
		return showHongChongConfirm();
	}
	/**
	 * 显示红冲发票管理查询页面
	 * @return
	 */
	public String showManagerQuery(){
		return "showManagerQuery";
	}
	/**
	 * 显示红冲发票管理页面主页面
	 * @return
	 */
	public String showManagerMain(){
		return "showManagerMain";
	}
	/**
	 * 显示红冲发票确认查询页面
	 * @return
	 */
	public String showConfirmQuery(){
		return "showConfirmQuery";
	}
	/**
	 * 显示红冲发票确认页面主页面
	 * @return
	 */
	public String showConfirmMain(){
		return "showConfirmMain";
	}
	
	/**
	 * 判断收据是否被关联两次以上
	 * @return
	 */
	public void checkReceipt(){
		 InvoiceInfo ii = (InvoiceInfo) commonService.load(InvoiceInfo.class, invoiceIds);
		 ApplyBill bill = (ApplyBill) commonService.load(ApplyBill.class, ii.getApplyInvoiceId());
		 if(bill.getBillType().equals("4")){
			 List<BillandProRelaion> proRelationList = commonService.list("from BillandProRelaion br where br.applyBill = ? ", bill.getBillApplyId());
			 for (BillandProRelaion br : proRelationList) {
				List<RelationBillAndReceipt> receiptList = 
					commonService.list("from RelationBillAndReceipt rr where rr.receiptRealId = ? ", br.getRealContractBillandRecePlan());
				if(receiptList.size() > 1 ){
					this.renderText("1");
					return;
				}
			}
		 }
		 else{
			this.renderText("2");
		 }
	}
	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}

	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}

	public Long getInvoiceIds() {
		return invoiceIds;
	}

	public void setInvoiceIds(Long invoiceIds) {
		this.invoiceIds = invoiceIds;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Long[] getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long[] invoiceId) {
		this.invoiceId = invoiceId;
	}

	public List<Object> getInvoiceBillList() {
		return invoiceBillList;
	}

	public void setInvoiceBillList(List<Object> invoiceBillList) {
		this.invoiceBillList = invoiceBillList;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public String getConfirmHC() {
		return confirmHC;
	}

	public void setConfirmHC(String confirmHC) {
		this.confirmHC = confirmHC;
	}

	public String getHongChongState() {
		return hongChongState;
	}

	public void setHongChongState(String hongChongState) {
		this.hongChongState = hongChongState;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public List<HongChongRelationPlan> getHongChongRelation() {
		return hongChongRelation;
	}

	public void setHongChongRelation(List<HongChongRelationPlan> hongChongRelation) {
		this.hongChongRelation = hongChongRelation;
	}

	public List<Object[]> getHongChongPlanList() {
		return hongChongPlanList;
	}

	public void setHongChongPlanList(List<Object[]> hongChongPlanList) {
		this.hongChongPlanList = hongChongPlanList;
	}
	
}

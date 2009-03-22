package com.baoz.yx.action.billtoReceipt;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/relationContract.jsp"),
	@Result(name = "showContract", value = "/WEB-INF/jsp/billtoReceipt/searchContract.jsp"),
	@Result(name = "showRelation", value = "/WEB-INF/jsp/billtoReceipt/onRelationReceipt.jsp"),
	@Result(name = "relation", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
})
public class RelationContractQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;

	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 		billService;
	
	
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	
	
	private List<Object>				billList;
	
	private List<Object>				receiptList;
	private List<Object>                realRelationList;
	private PageInfo					info;
	private String 						cName;	//合同名称
	private String 						cId;	//合同号
	private String 						itemId;   //项目号
	private Long 						cmId;  			//合同表的id
	private Long 						realId;				//实际表ID
	private Long						billId;            //bill表的id
	
	private String 						realPlanId;   //修改后存计划ID
	
	private String 						succSave;
	private Long[] 						monthlyBillproSids;
	private Double 						billAmount;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"relationContractQParameters",this,new String[]{"conId","conName","itemId"}); 
	
	@Override
	public String doDefault() throws Exception {
		logger.info("显示未签开票");
		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		Long uid = new UserUtils().getUser().getId();
		info=queryService.listQueryResult(" from ApplyBill bill where bill.applyBillState in (3,5,6,7) and bill.isNoContract = 1 and bill.employeeId = "+uid+"  order by bill.billApplyId desc ", info);
		return SUCCESS;
	}
	/**
	 * 查询显示合同进行关联
	 * 
	 * @return
	 */
	public String searchContract()
	{
		logger.info("查询合同进行关联");
		//showRealRelation();
		return "showContract";
	}
	public String isRelation(){
		ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, billId);
		if(bill.getContractMainInfo() != null){
			this.renderText("1");
		}
		else{
			this.renderText("0");
		}
		return null;
	}
	public String showRealRelation(){
		ParameterUtils.prepareParameters(holdTool);
		//设置当前页，如果session中有值会用session中的当前页
		logger.info("发票号是:"+billId);
		realRelationList = service.list(billService.showRealRelation(cName, cId, itemId,billId)[1]);
		return "showContract";
	}
	
	public String relationCon()
	{
		logger.info("确定关联合同");
		logger.info(realId);
		logger.info(realPlanId);
		String[] str = StringUtils.split(realPlanId,",");
		for(int j = 0 ;j <str.length; j ++ ){
			realId = Long.valueOf(str[j]);
			logger.info(billId);
			logger.info(realId);
			if(billService.judgementBillSingle(realId)){
				RealContractBillandRecePlan plan = (RealContractBillandRecePlan) service.load(RealContractBillandRecePlan.class, realId);
				ApplyBill bill =(ApplyBill) service.load(ApplyBill.class, billId);
				bill.setContractMainInfo(plan.getConMainInfoSid());
				service.update(bill);
				BillandProRelaion relation = new BillandProRelaion();
				relation.setApplyBill(billId);
				relation.setRealContractBillandRecePlan(realId);
				relation.setRelateAmount(Double.valueOf(plan.getRealTaxBillAmount().toString()));
				service.save(relation);
				contractService.itemIsCloseByApplyId(billId);
				//开票申请开得票关联合同
				invoiceService.relateContract(billId, plan.getConMainInfoSid());	
				//更新计划的已开票金额和应收
				contractCommonService.calBillInvoiceAmount(billId);
				//更新统计字段和计划中的开票日期，月度开票计划
				String dateHql="select max(invoice.invoiceDate) from InvoiceInfo invoice where invoice.applyInvoiceId=?";
				Date invoiceDate = (Date)service.uniqueResult(dateHql, billId);
				if(invoiceDate != null ){
					contractCommonService.updatePlanStatistic(invoiceDate, plan.getRealConBillproSid(), false);
				}
			}
			else{
				billList = service.list(billService.showSelectRealReceipt(realId));
				String[] billNature=new String[billList.size()];
				Long[] conId = new Long[billList.size()];
				for(int i=0;i<billList.size();i++){
					Object[] realPlan=(Object[])billList.get(i);
					billNature[i]=((RealContractBillandRecePlan)realPlan[0]).getBillNature();
					conId[i]=((RealContractBillandRecePlan)realPlan[0]).getConMainInfoSid();
				}
				if(billNature.length > 0){
					receiptList = service.list(billService.showRecitpQuery(billNature, conId));
				}
				return "showRelation";
			}
		}
		
		return "relation";
	}
	
	public String checkRealIsBill(){
		logger.info(StringUtils.join(monthlyBillproSids,","));
		if(billService.isSameRealById(monthlyBillproSids)){
			List<RealContractBillandRecePlan> realList = service.list("from RealContractBillandRecePlan r where r.realConBillproSid in("+StringUtils.join(monthlyBillproSids,",")+") ");
			BigDecimal realBillAmount = new BigDecimal(0.00);
			for (RealContractBillandRecePlan plan : realList) {
				realBillAmount = realBillAmount.add(plan.getRealBillAmount());
			}
			if (realBillAmount.doubleValue() == this.billAmount) {
				this.renderText("1");  // 金额相同,可以关联
			}else{
				this.renderText("2");  // 金额不相同,不可以关联
			}
		}else{
			this.renderText("3");//选择的计划不可以同时关联
		}
		return null;
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
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}


	public String getCName() {
		return cName;
	}

	public void setCName(String name) {
		cName = name;
	}

	public String getCId() {
		return cId;
	}

	public void setCId(String id) {
		cId = id;
	}

	public Long getCmId() {
		return cmId;
	}

	public void setCmId(Long cmId) {
		this.cmId = cmId;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public Long getRealId() {
		return realId;
	}
	public void setRealId(Long realId) {
		this.realId = realId;
	}
	public List<Object> getBillList() {
		return billList;
	}
	public void setBillList(List<Object> billList) {
		this.billList = billList;
	}
	public List<Object> getReceiptList() {
		return receiptList;
	}
	public void setReceiptList(List<Object> receiptList) {
		this.receiptList = receiptList;
	}
	public String getSuccSave() {
		return succSave;
	}
	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}
	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}
	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}
	public List<Object> getRealRelationList() {
		return realRelationList;
	}
	public void setRealRelationList(List<Object> realRelationList) {
		this.realRelationList = realRelationList;
	}
	public Long[] getMonthlyBillproSids() {
		return monthlyBillproSids;
	}
	public void setMonthlyBillproSids(Long[] monthlyBillproSids) {
		this.monthlyBillproSids = monthlyBillproSids;
	}
	public Double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}
	public String getRealPlanId() {
		return realPlanId;
	}
	public void setRealPlanId(String realPlanId) {
		this.realPlanId = realPlanId;
	}
}

package com.baoz.yx.action.billtoReceipt;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;

@Results( {
		@Result(name = "succ", type = ServletRedirectResult.class, value = "/billtoReceipt/billApplyVerifyQuery.action"),
		@Result(name = "tempSuc", value = "/WEB-INF/jsp/billtoReceipt/billApplyVerifyTemp.jsp"),
		@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/billApplyVerifyList.jsp"),
		@Result(name = "pinfo", value = "/WEB-INF/jsp/programEconomy/projectInfo.jsp"),
		@Result(name = "showpurs", value = "/WEB-INF/jsp/purchase/showpurs.jsp"),
		@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
		@Result(name = "economyInfo", value = "/WEB-INF/jsp/programEconomy/projectEconomyInfo.jsp"),
		@Result(name = "economyView", value = "/WEB-INF/jsp/programEconomy/economyView.jsp"),
		@Result(name = "showClientsList", value = "/WEB-INF/jsp/programEconomy/showClients.jsp"),
		@Result(name = "edit", value = "/WEB-INF/jsp/programEconomy/economyEdit.jsp"),
		@Result(name = "succDetail", value = "/WEB-INF/jsp/invoiceManagement/guodu2.jsp")		
 })
public class BillApplyVerifyAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService 	codeGenerateService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("noticeService")
	private INoticeService noticeservice;
	private PageInfo info;
	private String bId;
	private String action;
	private Long expId;
	private HttpServletRequest request = null;
	private List<Object> listExp;
	private Long[]	billApplyId;
	
	private String fowardType;//跳转提示
	private String alertInfo;
	private String returnReason;//退回理由
	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getFowardType() {
		return fowardType;
	}

	public void setFowardType(String fowardType) {
		this.fowardType = fowardType;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public String doDefault() throws Exception {
		logger.info("=================executeHere!");
		return "";
	}

	/**
	 * 开票申请确认状态   3  已通过 
 	 * 
	 * @return
	 */
	public String verifyState() {
		logger.info(StringUtils.join(billApplyId,","));  
		for(int i=0;i<billApplyId.length;i++){
			ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, billApplyId[i]);
			bill.setApplyBillState(3L);
			invoiceService.makePurOut(billApplyId[i]);          //设置出库
			if(bill.getInitIsNoContract() == 1){
				bill.setBillApplyNum(codeGenerateService.generateWBillCode());
			}
			if(bill.getInitIsNoContract() == 0){
				bill.setBillApplyNum(codeGenerateService.generateSBillCode());
			}
			//更新剩余发票
			List<RealContractBillandRecePlan> rbList = service.list("select p from RealContractBillandRecePlan p ,BillandProRelaion br where br.realContractBillandRecePlan = p.realConBillproSid and br.applyBill = ?", bill.getBillApplyId());
			for (RealContractBillandRecePlan realContractBillandRecePlan : rbList) {
				if(realContractBillandRecePlan.getContractItemMaininfo() != null){
					contractService.sumRemainBIll(realContractBillandRecePlan.getContractItemMaininfo());
				}
				//更新计划中已确认字段
				realContractBillandRecePlan.setApplyBillState(3L);
				service.update(realContractBillandRecePlan);
			}
			service.update(bill);
		}
		if(fowardType !=null && fowardType.equals("parents")){
			alertInfo = "开票申请已确认通过";
			return "succDetail";
		}
		return "succ";
	}

	/**
	 * 开票申请确认状态   4  确认退回 
 	 * 
	 * @return
	 */
	public String verifyStateT() {
		logger.info(StringUtils.join(billApplyId,","));  
		for(int i=0;i<billApplyId.length;i++){
			ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, billApplyId[i]);
			bill.setApplyBillState(4L);
			bill.setReturnReason(returnReason);  //退回理由
			bill.setReturnDate(new Date());      //退回理由填写时间
			//bill.setBillApplyNum(null);
			service.update(bill);
			invoiceService.updatePlanApplyBillState(4L, bill.getBillApplyId());
			String str1 = null;
			if(bill.getContractMainInfo() != null){
				ContractMainInfo cmaininfo = (ContractMainInfo)service.load(ContractMainInfo.class, bill.getContractMainInfo());
				str1 = "合同号为:"+cmaininfo.getConId()+"的开票申请单已被退回.";
			}
			else{
				str1 = "你有一张为未签开票申请已被退回.";
			}
			noticeservice.addNotice(str1, bill.getEmployeeId());
		}
		alertInfo = "开票申请已退回";
		return "succDetail";
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public String queryByExployee() {
		//用BillApplyVerifyQueryAction查询
//		String expHql = "from Employee d where d.is_active=1";
//		listExp = service.list(expHql);
		
		String expHql = "from Employee d where  exists ( select a.employeeId from ApplyBill a where a.applyBillState = 2 " +
		" and d.id = a.employeeId )";
		listExp = service.list(expHql);


		String hqlms;
		if (expId != null && !"".equals(expId)) {
			logger
					.info("===============only=expId==" + expId
							+ this.getExpId());
			hqlms = ("select ab,e.name from ApplyBill ab,Employee e  where"
					+ " (ab.employeeId=e.id and e.id=" + expId + ") and ab.applyBillState=2");
		} else {
			logger.info("===============all=expId==" + expId + this.getExpId());
			hqlms = ("select ab,e.name from ApplyBill ab,Employee e  where"
					+ " (e.id = ab.employeeId or e.id is null)  and ab.applyBillState=2");
		}
		request = ServletActionContext.getRequest();
		request.getSession().setAttribute("BillVerifyAllHql", hqlms);
		String hql = (String) request.getSession().getAttribute(
				"BillVerifyAllHql");
		logger.info("===============================hql===" + hql);
		if (hql != null)
			info = queryService.listQueryResult(hql, info);
		return "success";
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getBId() {
		return bId;
	}

	public void setBId(String id) {
		bId = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public Long[] getBillApplyId() {
		return billApplyId;
	}

	public void setBillApplyId(Long[] billApplyId) {
		this.billApplyId = billApplyId;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}
}

package com.baoz.yx.action.billtoReceipt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;


/**
 * 收据转发票
 *
 *  
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/receiptToBillList.jsp"),
	@Result(name = "showRelationInfo", value = "/WEB-INF/jsp/billtoReceipt/searchrelation.jsp")})

public class ReceiptToBillQueryAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 		billService;	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	private PageInfo					info;
	private ServletRequest				request;
	private List<Object>				relationAmount = new ArrayList<Object>();
	private String 						contractId;
	private Double						minBillAmount;
	private Double						maxBillAmount;
	private String						stratReceiptDate;
	private String						endReceiptDate;
	private Long 						customerId;
	private List<Object>				conManinList;
	private Integer                     relationState;//0未关联发票,1部分关联,2全额关联
	
	private List<Object[]>              feeMoneyList=new ArrayList<Object[]>();//剩余金额

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"reciptToBillParameters",this,new String[]{"relationState","customerId","contractId","minBillAmount","maxBillAmount","stratReceiptDate","endReceiptDate"}); 

	
	public List<Object[]> getFeeMoneyList() {
		return feeMoneyList;
	}


	public void setFeeMoneyList(List<Object[]> feeMoneyList) {
		this.feeMoneyList = feeMoneyList;
	}


	@Override				
	public String doDefault() throws Exception {
		this.logger.info("收据转发票");
		ParameterUtils.prepareParameters(holdTool);
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "ReceiptToBillQueryPage");
		info = queryService.listQueryResult(billService.showReceiptToBillQuery(relationState, customerId, contractId, minBillAmount, maxBillAmount, stratReceiptDate, endReceiptDate)[0],billService.showReceiptToBillQuery(relationState, customerId, contractId, minBillAmount, maxBillAmount, stratReceiptDate, endReceiptDate)[1], info);
		List<Object[]> billList =(List<Object[]>)info.getResult();
		for (Object[] objects : billList) {
			relationAmount.add(service.list(" from RelationAmount r,InvoiceInfo ii where r.invoiceToInvoice = ii.id and r.invoiceToReceip = '"+((InvoiceInfo)objects[2]).getId()+"'"));
			feeMoneyList.add(new Object[]{service.uniqueResult("select sum(r.relateAmount) from RelationAmount r where r.invoiceToReceip=?", ((InvoiceInfo)objects[2]).getId()),((InvoiceInfo)objects[2]).getId()});
		}
		return SUCCESS;
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

	public List<Object> getRelationAmount() {
		return relationAmount;
	}

	public void setRelationAmount(List<Object> relationAmount) {
		this.relationAmount = relationAmount;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}



	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public Double getMinBillAmount() {
		return minBillAmount;
	}

	public void setMinBillAmount(Double minBillAmount) {
		this.minBillAmount = minBillAmount;
	}

	public Double getMaxBillAmount() {
		return maxBillAmount;
	}

	public void setMaxBillAmount(Double maxBillAmount) {
		this.maxBillAmount = maxBillAmount;
	}

	public String getStratReceiptDate() {
		return stratReceiptDate;
	}

	public void setStratReceiptDate(String stratReceiptDate) {
		this.stratReceiptDate = stratReceiptDate;
	}

	public String getEndReceiptDate() {
		return endReceiptDate;
	}

	public void setEndReceiptDate(String endReceiptDate) {
		this.endReceiptDate = endReceiptDate;
	}


	public List<Object> getConManinList() {
		return conManinList;
	}


	public void setConManinList(List<Object> conManinList) {
		this.conManinList = conManinList;
	}


	public Integer getRelationState() {
		return relationState;
	}


	public void setRelationState(Integer relationState) {
		this.relationState = relationState;
	}


}

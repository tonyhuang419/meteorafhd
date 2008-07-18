package com.baoz.yx.action.billtoReceipt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;


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
	
	private List<Object[]>              feeMoneyList=new ArrayList<Object[]>();//剩余金额

	public List<Object[]> getFeeMoneyList() {
		return feeMoneyList;
	}


	public void setFeeMoneyList(List<Object[]> feeMoneyList) {
		this.feeMoneyList = feeMoneyList;
	}


	@Override				
	public String doDefault() throws Exception {
		this.logger.info("收据转发票");
		String select="select bill,yc,ii,(select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = ii.contractMainSid)," +
				"(select count(*) from InvoiceInfo i where i.applyInvoiceId = bill.billApplyId ) ";
		StringBuffer str = new StringBuffer();
		str.append(" from ApplyBill bill,YXClientCode yc,InvoiceInfo ii " +
				"where yc.is_active != 0 " +
				" and bill.customerId = yc.id " +
				" and ii.applyInvoiceId = bill.billApplyId " +
				"and ii.type = 4 ");
		if (StringUtils.isNotBlank(contractId)) {
			str.append(" and exists (select 1 from ContractMainInfo cm where cm.conId like '%").append(contractId).append("%'")
			.append(" and cm.conMainInfoSid = ii.contractMainSid) ");
		}
		if (customerId!=null&&!"".equals(customerId)) {
			str.append(" and bill.customerId=").append(customerId);
		}
		if (minBillAmount!=null&&!"".equals(minBillAmount)) {
			str.append(" and ii.invoiceAmount >= ").append(minBillAmount);
		}
		if (maxBillAmount!=null&&!"".equals(maxBillAmount)) {
			str.append(" and ii.invoiceAmount <= ").append(maxBillAmount);
		}
		if (stratReceiptDate != null && !stratReceiptDate.equals("")) {
			str.append(" and ii.invoiceDate >= to_date('" + stratReceiptDate
					+ "','yyyy-mm-dd')");
		}
		if (endReceiptDate != null && !endReceiptDate.equals("")) {
			str.append(" and ii.invoiceDate <= to_date('" + endReceiptDate
					+ "','yyyy-mm-dd')");
		}
		str.append(" order by ii.id DESC");	
		info = queryService.listQueryResult("select count(*) "+str.toString(),select+str.toString(), info);
	
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


}

package com.baoz.yx.action.billtoReceipt;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.sun.mail.iap.Response;

/**
 *	开票收据管理
 *
 *  
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/billReceiptManager.jsp"),
	@Result(name = "showselect", value = "/WEB-INF/jsp/personnelManager/searchPersonnel.jsp")})

public class BillReceiptQueryAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;								
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	private PageInfo 			info;
	private ServletRequest		request;
	private List<InvoiceInfo> 	invoiceList;
	private Long				selectState;
	private Long 				confirm;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("开票收据管理");
		StringBuffer str = new StringBuffer();
		logger.info(confirm);
		logger.info(selectState);
		str.append("select bill,yc,emp from ApplyBill bill,YXClientCode yc,Employee emp " +
				"where bill.employeeId = emp.id " +      
				"and yc.is_active != 0 " +
				"and bill.customerId = yc.id "); /*+
				"and bill.applyBillState = 3");*/
		if(confirm!=null&&!"".equals(confirm)) {
			if (confirm == 1) {
				str.append(" and bill.billApplyId in(select inf.applyInvoiceId from InvoiceInfo inf)");
			} 
			else if(confirm == 2){
				str.append(" and bill.billApplyId not in(select inf.applyInvoiceId from InvoiceInfo inf)");
			}
			
		}
		if(selectState!=null&&!"".equals(selectState)) {
			if (selectState == 1) {
				str.append(" and bill.sign = 0");
			}
			else if(selectState == 2){
				str.append(" and bill.sign = 1");
			}
			else{
				str.append("");
			}
		}
		str.append(" order by bill.billApplyId DESC");	
		info = queryService.listQueryResult(str.toString(),info);
		invoiceList=service.list(" from InvoiceInfo invoice");
		logger.info("=================info="+info);
		return SUCCESS;
	}

	public  String selectState()
	{
		logger.info("根据选择");
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

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<InvoiceInfo> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceInfo> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public Long getSelectState() {
		return selectState;
	}

	public void setSelectState(Long selectState) {
		this.selectState = selectState;
	}

	public Long getConfirm() {
		return confirm;
	}

	public void setConfirm(Long confirm) {
		this.confirm = confirm;
	}





}


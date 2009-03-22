package com.baoz.yx.action.billtoReceipt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.struts2.ServletActionContext;
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
import com.baoz.yx.entity.bill.RelationAmount;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;


/**
 * 显示关联发票选择
 *
 *  
 */
@Results( {	
	@Result(name = "showRelationInfo", value = "/WEB-INF/jsp/billtoReceipt/searchRelation.jsp")})

public class ShowSelectRelationAction extends DispatchAction implements ServletRequestAware{

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
	private List<InvoiceInfo>			invoiceList;
	private Long 						idss;
	
	private List<RelationAmount> 		relationList;
	
	private Double 						lastSum;
	@Override				
	public String doDefault() throws Exception {
		logger.info("显示要关联信息");
		Long uid = new UserUtils().getUser().getId();
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
		info=queryService.listQueryResult(
				"select count(*) from InvoiceInfo ii where ii.type != 4 and ii.byId = '"+uid+"'"
				,"select ii, (select sum(ra.relateAmount) from RelationAmount ra where ra.invoiceToInvoice = ii.id ) from InvoiceInfo ii where ii.type != 4 and ii.byId = '"+uid+"'",info);
		logger.info(idss);
		invoiceList = service.list(" from InvoiceInfo ii where ii.id = "+idss);
		double sum = invoiceList.get(0).getInvoiceAmount();
		
		relationList = service.list(" from RelationAmount ra where ra.invoiceToReceip = " + idss);
		
		double relaSum = 0;
		
		for(int i = 0; i < relationList.size(); i++){
			relaSum += relationList.get(i).getRelateAmount();
		}
		logger.info("总金额"+sum);
		
		logger.info("已关联总金额"+relaSum);
		
		double lastSum = sum - relaSum ;
		
		logger.info("还可以关联"+lastSum);
		 
		ServletActionContext.getRequest().setAttribute("lastSum", lastSum);
		ServletActionContext.getRequest().setAttribute("ids", idss);
		return "showRelationInfo";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
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

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public List<InvoiceInfo> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceInfo> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public Long getIdss() {
		return idss;
	}

	public void setIdss(Long idss) {
		this.idss = idss;
	}

	public List<RelationAmount> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<RelationAmount> relationList) {
		this.relationList = relationList;
	}

	public Double getLastSum() {
		return lastSum;
	}

	public void setLastSum(Double lastSum) {
		this.lastSum = lastSum;
	}
}


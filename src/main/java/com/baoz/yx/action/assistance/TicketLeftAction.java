package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/ticketLeft.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/ticketList.jsp")})
public class TicketLeftAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private PageInfo info;
	private String supply;
	private String type;
	private String sDate;
	private String eDate;
	private List list;
	@Override
	public String doDefault() throws Exception {
		this.logger.info("外协发票查询");
		return "success";
	}	
	public String query() throws Exception {
		/*StringAppender sa = new StringAppender();
		sa.append("from AssistanceTicket at where at.is_active = '1' ");
		sa.appendIfEmpty(" and exists(select s.supplierid from SupplierInfo s where s.supplierid = at.customerId and s.supplierName like '%"+supply+"%')  ", supply);
		sa.appendIfEmpty(" and at.type = '"+type+"'", type);
		sa.appendIfNotEmpty(" and to_date('"+sDate+"', 'yyyy-MM-dd') <= at.ticket_Time  ", sDate);	
		sa.appendIfNotEmpty(" and at.ticket_Time <= to_date('"+eDate+"', 'yyyy-MM-dd') ", eDate);*/
		StringBuffer sb = new StringBuffer();
		sb.append("select at, si.supplierName from AssistanceTicket at , SupplierInfo si where si.supplierid = at.customerId and at.is_active = '1' and at.is_related='0'");
		if(supply!=null && !"".equals(supply)){
			sb.append("and exists(select s.supplierid from SupplierInfo s where s.supplierid = at.customerId and s.supplierName like '%"+supply+"%')");
		}
		if(type!=null && !"".equals(type)){
			sb.append("and at.type = '"+type+"'");
		}
		if(sDate!=null && !"".equals(sDate)){
			sb.append("and to_date('"+sDate+"', 'yyyy-MM-dd') <= at.ticket_Time  ");
		}
		if(eDate!=null && !"".equals(eDate)){
			sb.append("and at.ticket_Time <= to_date('"+eDate+"', 'yyyy-MM-dd') ");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		System.out.println(ActionContext.getContext().getParameters());
		return "queryList";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSDate() {
		return sDate;
	}

	public void setSDate(String date) {
		sDate = date;
	}

	public String getEDate() {
		return eDate;
	}

	public void setEDate(String date) {
		eDate = date;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
}

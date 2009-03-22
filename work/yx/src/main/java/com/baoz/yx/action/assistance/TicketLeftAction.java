package com.baoz.yx.action.assistance;

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
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

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
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	private PageInfo info;
	private String ticketType;///发票类型
	private String startDate;///开票时间开始
	private String endDate;//开票时间结束
	private Long supplierid;//供应商id
	
	private String assistanceNo;///外协合同号
	
	
	private List<YXTypeManage> invoiceList ;
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"ticketLeftParameters",this,new String[]{"supplierid","ticketType","startDate","endDate","assistanceNo"});
	
	@Override
	public String doDefault() throws Exception {		
		invoiceList = typeManageService.getYXTypeManage(1004L);		
		this.logger.info("外协发票查询");
		return "success";
	}	
	public String query() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sb = new StringBuffer();
		sb.append("select at, si.supplierName, " +
				" contract " +
				" from AssistanceTicket at , " +
				" SupplierInfo si," +
				" AssistanceContract contract  " +
				" where si.supplierid = at.customerId " +
				" and contract.id = at.contractId " +
				" and at.is_active = '1' ");
		if(supplierid != null){
			sb.append(" and si.supplierid = "+supplierid+"  ");
		}
		if(ticketType!=null && !"".equals(ticketType)){
			sb.append(" and at.type = '"+ticketType+"' ");
		}
		if(startDate!=null && !"".equals(startDate)){
			sb.append("and to_date('"+startDate+"', 'yyyy-MM-dd') <= at.ticket_Time  ");
		}
		if(endDate!=null && !"".equals(endDate)){
			sb.append("and trunc(at.ticket_Time,'dd') <= to_date('"+endDate+"', 'yyyy-MM-dd') ");
		}
		if(StringUtils.isNotBlank(assistanceNo)){
			sb.append(" and lower(contract.assistanceId) = '"+StringUtils.lowerCase(assistanceNo)+"' ");
		}
		//权限
		UserDetail user = UserUtils.getUserDetail();
		String expId = null;
		String groupId = null;
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId() + "";
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if(StringUtils.isNotBlank(expId))
		{
			sb.append(" and at.person = "+expId+" ");
		}
		if(StringUtils.isNotBlank(groupId)){
			sb.append(" and at.person in( select e.id from Employee e where e.position in(select o.id from OrganizationTree o where o.organizationCode like '"+groupId+"%'))");
		}
		sb.append(" order by at.is_related,at.id DESC");
		ParameterUtils.preparePageInfo(info, "ticketLeftPageInfo");
		info = queryService.listQueryResult(sb.toString(), info);
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
	public List<YXTypeManage> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<YXTypeManage> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
	public Long getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}
	
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAssistanceNo() {
		return assistanceNo;
	}
	public void setAssistanceNo(String assistanceNo) {
		this.assistanceNo = assistanceNo;
	}

}

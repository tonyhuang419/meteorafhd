package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;
import com.opensymphony.xwork2.ActionContext;
@Results({
	@Result(name = "enterSave", value = "/WEB-INF/jsp/assistance/newAssistanceTicket.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/assistance/editAssistanceTicket.jsp"),
	@Result(name = "enterRelationContract", value = "/WEB-INF/jsp/assistance/relationContract.jsp"),
	@Result(name = "success", type = ServletRedirectResult.class, value = "/assistance/ticketLeft.action?method=query"),
	@Result(name = "relationBack", value = "/WEB-INF/jsp/assistance/relationReturn.jsp"),
	@Result(name = "editBack", value = "/WEB-INF/jsp/assistance/editTicketReturn.jsp"),
	@Result(name = "back", value = "/WEB-INF/jsp/assistance/ticketReturn.jsp")})

public class AssistanceTicketAction extends DispatchAction {
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
	@Qualifier("systemService")
	private ISystemService systemService;
	private AssistanceTicket at;
	private String ids;
	private List<AssistanceTicket> list;
	private String ticketDate;
	private PageInfo info;
	private AssistanceContract ac ;
	private String ticketId;
	private String supplierName;
	private String supplierid;
	private String id;
	private String name;
	
	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		logger.info("开票新增");
		return ENTER_SAVE;
	}
	public String saveAssistanceTicket() throws Exception {
		this.logger.info("新增发票");
		at.setCustomerId(Long.parseLong(supplierid));
		at.setTicket_Time(DateUtil.parse(ticketDate, "yy-MM-dd"));
		at.setIs_active("1");
		at.setIs_related("0");
		service.save(at);
		return "back";
	}
	public String editAssistanceTicket() throws Exception {
		this.logger.info("修改发票");
		if(supplierid!=null && !"".equals(supplierid)){
			at.setCustomerId(Long.parseLong(supplierid));
		}
		at.setTicket_Time(DateUtil.parse(ticketDate, "yy-MM-dd"));
		service.update(at);
		return "editBack";
	}
	public String enterUpdate() throws Exception {
		this.logger.info("edit ticket....");
		String hql = "select at,si.supplierName from AssistanceTicket at, SupplierInfo si  where si.supplierid = at.customerId and at.id = "+Long.parseLong(ids);
		info = queryService.listQueryResult(hql, info);
		at = (AssistanceTicket)((Object[])((List)info.getResult()).get(0))[0];
		return "enterUpdate";
	}
	public String del() throws Exception{
		this.logger.info("删除发票");
		String hql=" update AssistanceTicket obj set obj.is_active= '0' ";
		int a = systemService.deleteChose(ids, hql);
		if (a > 0) {
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}
	public String enterRelationContract() throws Exception{
		this.logger.info("进入关联外协合同");
		StringBuffer sb = new StringBuffer();
		sb.append("select ac, si.supplierName from AssistanceContract ac, SupplierInfo si where si.supplierid = ac.supplyId and ac.is_active = '1'");
		if(id!=null && !"".equals(id)){
			sb.append(" and ac.id=").append(id);
		}
		if(name!=null && !"".equals(name)){
			sb.append(" and ac.assistanceName like '%").append(name).append("%'");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		return "enterRelationContract";
	}
	public String relationContract() throws Exception {
		logger.info("关联外协合同");
		String hql = "from AssistanceTicket at where at.id = "+Long.parseLong(ticketId);
		at = (AssistanceTicket)service.uniqueResult(hql);
		at.setContractId(Long.parseLong(ids));
		at.setIs_related("1");
		service.update(at);
		return "success";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) { 
		this.service = service;
	}
	public AssistanceTicket getAt() {
		return at;
	}
	public void setAt(AssistanceTicket at) {
		this.at = at;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List getList() {
		return list;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
	public String getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}
	public void setList(List<AssistanceTicket> list) {
		this.list = list;
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
	public ISystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	public AssistanceContract getAc() {
		return ac;
	}
	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

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
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.utils.UserUtils;
@Results({
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceList.jsp"),
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/assistanceListMore.jsp")})
public class AssistanceQueryAction extends DispatchAction  {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("systemService")
	private ISystemService systemService;
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private PageInfo info ;
	private List list;
	private String id;
	private AssistanceContract ac;
	private String expName;
	private String supplierName;
	private String userName;
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("外协合同管理");
		StringBuffer sb = new StringBuffer();
		sb.append("from AssistanceContract ac where ac.id not in(0) and ac.is_active='1'");
		if(expName!=null && !"".equals(expName)){
			sb.append("and ac.");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		List queryList = (List)info.getResult();
		list = assistanceService.queryAssistanceContract(queryList);
		return "queryList";
	}
	public String info() throws Exception {
		this.logger.info("详细信息");
		String hql = "from AssistanceContract ac where ac.id ="+id+" and ac.is_active='1'";
		ac = (AssistanceContract)service.uniqueResult(hql);
		String sHql = "from SupplierInfo si where si.supplierid = "+ac.getSupplyId();
		SupplierInfo si = (SupplierInfo)service.uniqueResult(sHql);
		supplierName = si.getSupplierName();
		String cHql = "from ContractMainInfo c where c.conMainInfoSid = "+ac.getContractId();
		ContractMainInfo contract = (ContractMainInfo)service.uniqueResult(cHql);
		String sellHql = "from Employee e where e.id = "+contract.getSaleMan();
		Employee e = (Employee)service.uniqueResult(sellHql);
		userName = e.getName();
		return "success";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public AssistanceContract getAc() {
		return ac;
	}
	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
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
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

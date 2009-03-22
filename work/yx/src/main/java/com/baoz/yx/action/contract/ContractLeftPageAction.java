package com.baoz.yx.action.contract;

import java.util.ArrayList;
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
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/contractEconomicProjects.jsp"),
	@Result(name = "leftFram",value = "/contract/contractSeconomicprojects.action")})
public class ContractLeftPageAction extends DispatchAction{
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	private List<ContractMainInfo> listSup; // 查询显示所有的合同
	private String contractId;
	private String clientName;
	private Long expId;
	private String groupId;
	private PageInfo info;
	private String contractType; //合同性质
	private String start; //开工  
	private String physical;//实物
	private String completion;//竣工  
	
	
	private List<YXTypeManage>		dutyDepartmentIdList;
	
	private List<Object> 			listExp;   // 查询显示所有的销售员
	private List<Department>		groupList;	  //组别	
	private List<Object>        	yxClientCodeList;   // 查询显示所有的客户
   
    
	
	
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getPhysical() {
		return physical;
	}
	public void setPhysical(String physical) {
		this.physical = physical;
	}
	public String getCompletion() {
		return completion;
	}
	public void setCompletion(String completion) {
		this.completion = completion;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"ContractLeftPageParameters",this,new String[]{"groupId","contractId","clientName"
    				,"expId","contractType","start","physical","completion"});
    
	public String doDefault() throws Exception {
		yxClientCodeList = new ArrayList<Object>();		
		dutyDepartmentIdList = typeManageService.getYXTypeManage(1018L);		
		
		listExp = new ArrayList<Object>();
		groupList = UserUtils.getUserDetail().getDepartments();
		
	
		StringBuffer str = new StringBuffer();		
		str.append("from ContractMainInfo d,YXClientCode yc,Employee emp,ContractOtherInfo o,OrganizationTree org where " +
				" d.conState > 3  and  emp.position = org.id and o.contractMainSid = d.id and d.itemCustomer = yc.id and d.saleMan = emp.id");
		ParameterUtils.prepareParameters(holdTool);
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId!=null&&!"".equals(groupId)) {
			str.append(" and org.organizationCode like '").append(groupId+"%'");
		}	
		if (contractId!=null&&!"".equals(contractId)) {
			str.append(" and d.conId  like '%").append(contractId+"%'");
		}	
		if (clientName!=null&&!"".equals(clientName)) {
		
			str.append(" and yc.name like '%").append(clientName+"%'");
		}	
		if (expId!=null&&!"".equals(expId)) {
			str.append(" and emp.id =").append(expId);
		}	
		if (contractType!=null&&!"".equals(contractType)) {
			str.append(" and d.conType =").append(contractType);
		}
		if("1".equals(start)){ //满足 以确定 条件
			str.append(" and o.needPerativeReport = 1 and o.perativeReport is not null");
		}
		if("2".equals(start)){ //满足 未确盯 条件
			str.append(" and o.needPerativeReport = 1 and o.perativeReport is null ");
		}
		if("4".equals(start)){  //满足 不需要 条件
			str.append(" and o.needPerativeReport = 0");
		}
		if("1".equals(physical)){
			str.append(" and o.needRecivedThing =1 and o.recivedThing is not null");
		}
		if("2".equals(physical)){
			str.append(" and o.needRecivedThing =1 and o.recivedThing is null");
		}
		if("4".equals(physical)){
			str.append(" and o.needRecivedThing = 0");
		}
		if("1".equals(completion)){
			str.append(" and o.needFinallyReport = 1 and o.finallyReport is not null");
		}
		if("2".equals(completion)){
			str.append(" and o.needFinallyReport = 1 and o.finallyReport is null");
		}
		if("4".equals(completion)){
			str.append(" and o.needFinallyReport =0");
		}
		info = queryService.listQueryResult(str.toString(),info);
		
		return SUCCESS;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<ContractMainInfo> getListSup() {
		return listSup;
	}
	public void setListSup(List<ContractMainInfo> listSup) {
		this.listSup = listSup;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}

	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getExpId() {
		return expId;
	}
	public void setExpId(Long expId) {
		this.expId = expId;
	}
	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}
	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}
	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
	}
	public List<Object> getListExp() {
		return listExp;
	}
	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}

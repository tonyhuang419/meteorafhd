package com.baoz.yx.action.sellbefore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

/**
 *	根据查询条件显示售前合同管理
 *  
 */

@Result(name = "queryList", value = "/WEB-INF/jsp/sellbefore/sellBeforeManager.jsp")

public class SelectSellBeforeAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService 				systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo 					info;
    private Long 						customerId;
    private String 						projectName;
    private String						dutyDepartmentId;
    private String 						groupId;
    private Long 						expId;		
    private String						state;
    private List<Object>				programEconomy = new ArrayList<Object>();
    
    private String                      proSave;
    private String 						message;
    private String						hasChange;
	private Long						dayAfter;						//最后变更时间
    
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"SelectSellBeforeParameters",this,new String[]{"customerId","projectName","dutyDepartmentId","groupId","expId","state","hasChange","dayAfter"});    
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("查询售前管理结果");
		if(ActionContext.getContext().getSession().get("proSave")!=null){
			proSave = (String)ActionContext.getContext().getSession().get("proSave");
			ActionContext.getContext().getSession().remove("proSave");
		}
		
		Long uid = UserUtils.getUser().getId();
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append("select cbs,yc,orgTree,emp , " +
				" ( select cmi.conMainInfoSid from ContractMainInfo cmi where cmi.preConSysid = cbs.id  ) " +
				" from ContractBeforeSell cbs,YXClientCode yc,OrganizationTree orgTree,Employee emp " +
				"where cbs.is_active='1' " +
				"and cbs.employeeId = emp.id " +
				"and cbs.customerId=yc.id " +
				"and emp.position=orgTree.id " +
				"and cbs.byId = "+ uid);
		
		if (customerId!=null&&!"".equals(customerId)) {
			str.append(" and cbs.customerId=").append(customerId);
		}
		if (StringUtils.isNotBlank(projectName)) {
			str.append(" and cbs.projectName like '%").append(projectName+"%'");
		}
		if (dutyDepartmentId!=null&&!"".equals(dutyDepartmentId)) {
			str.append(" and cbs.dutyDepartmentId= '").append(dutyDepartmentId).append("'");
		}
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId != null && !"".equals(groupId)) {
			str.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}
		if (expId != null && !"".equals(expId)) {
			str.append(" and emp.id= ").append(expId);
		}
		if (state != null && !"".equals(state)) {
			str.append(" and cbs.projectStateSelect= '").append(state).append("'");
		}
		if (hasChange!=null && hasChange.equals("true")){
			str.append(" and exists (  select 1 from ContractMainInfo cmi where cmi.preConSysid = cbs.id   )");
		}
		else{
			str.append(" and not exists (  select 1 from ContractMainInfo cmi where cmi.preConSysid = cbs.id   )");
		}
		if (dayAfter != null && !"".equals(dayAfter)) {
			str.append(" and ( to_date('" + DateUtil.format(new Date(), "yyyy-MM-dd") + "','yyyy-MM-dd' ) - trunc(cbs.updateBy,'dd') ) <=  " +dayAfter );
		}
		str.append(" order by cbs.updateBy desc ,cbs.id desc");				
		info = queryService.listQueryResult( SqlUtils.getCountSql(str.toString()),  str.toString(), info );
//		List<Object[]> billList =(List<Object[]>)info.getResult();
//		for (Object[] objects : billList) {
//			programEconomy.add(service.list(" select cm.preConSysid from ContractMainInfo cm where cm.preConSysid = '"+((ContractBeforeSell)objects[0]).getID()+"'"));
//		}
		if(message!=null && message.equals("modSucc")){
			message = "修改成功";
		}
		return "queryList";
	}


	public IQueryService getQueryService() {
		return queryService;
	}


	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}


	public ISystemService getSystemService() {
		return systemService;
	}


	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}


	public PageInfo getInfo() {
		return info;
	}


	public void setInfo(PageInfo info) {
		this.info = info;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public Long getExpId() {
		return expId;
	}


	public void setExpId(Long expId) {
		this.expId = expId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getDutyDepartmentId() {
		return dutyDepartmentId;
	}


	public void setDutyDepartmentId(String dutyDepartmentId) {
		this.dutyDepartmentId = dutyDepartmentId;
	}


	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}


	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public ICommonService getService() {
		return service;
	}


	public void setService(ICommonService service) {
		this.service = service;
	}


	public List<Object> getProgramEconomy() {
		return programEconomy;
	}


	public void setProgramEconomy(List<Object> programEconomy) {
		this.programEconomy = programEconomy;
	}


	public String getProSave() {
		return proSave;
	}


	public void setProSave(String proSave) {
		this.proSave = proSave;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getHasChange() {
		return hasChange;
	}


	public void setHasChange(String hasChange) {
		this.hasChange = hasChange;
	}


	public Long getDayAfter() {
		return dayAfter;
	}


	public void setDayAfter(Long dayAfter) {
		this.dayAfter = dayAfter;
	}







}


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
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.append.helper.StringAppender;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
@Results({
	@Result(name="queryList", value="/WEB-INF/jsp/assistance/assistanceList.jsp"),
	@Result(name="success", value="/WEB-INF/jsp/assistance/affirmAssistanceContractLeft.jsp")})
public class AffirmAssistanceContractAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private String supplyName;
	private PageInfo info;
	private Double minConMoney;
	private Double maxConMoney;
	private String startDate;
	private String endDate;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	private String assistanceContractType;   //外协合同状态
	
	private String expId;//销售员id
	
	private String groupId;//销售员的部门
	
	private Long supplierid ;//供应商id
	
	private String isSuccess;////提示信息
	
	private List<String> messages = null;
	private String alertInfo;
	private Long ids[];
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"AffirmAssistanceContractParameters",this,new String[]{"expId","groupId",
					"supplierid","minConMoney","maxConMoney","startDate","endDate","assistanceContractType"});
	

	@Override
	public String doDefault() throws Exception {
		logger.info("外协合同确认左查询");
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "success";
	}
	
	public String query() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringAppender sb = new StringAppender();
		sb.append("select ac,supply.supplierName,contract,emp.name from AssistanceContract ac," +
				"SupplierInfo supply,ContractMainInfo contract,Employee emp  " +
				"where ac.is_active='1' " +
				" and ac.contractId = contract.conMainInfoSid" +
				" and ac.supplyId = supply.supplierid " +
				" and emp.id = ac.employeeId ");
		
		if(supplierid!=null){
			sb.append(" and supply.supplierid = "+supplierid+" ");
		}
		if(minConMoney != null){
			sb.append(" and "+minConMoney+" <= ac.contractMoney ");
		}
		if(maxConMoney != null){
			sb.append(" and ac.contractMoney <= "+maxConMoney+" ");
		}
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and to_date('"+startDate+"', 'yyyy-MM-dd') <= ac.contractDate  ");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append(" and trunc(ac.contractDate,'dd') <= to_date('"+endDate+"', 'yyyy-MM-dd') ");
		}
		if(StringUtils.isNotBlank(assistanceContractType)){
			sb.append(" and ac.assistanceContractType='" + assistanceContractType+"'");
		}
		
		UserDetail user = UserUtils.getUserDetail();
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId() + "";
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if(expId!=null&&!"".equals(expId))
		{
			sb.append(" and  emp.id ="+expId+" ");
		}
		if(groupId!=null&&!"".equals(groupId)){
			sb.append(" and  emp.id in (select e.id from Employee e where e.position in (select o.id from OrganizationTree o where o.organizationCode like '"+groupId+"%')) ");
		}
		
		sb.append("order by ac.id desc");
		info = ParameterUtils.preparePageInfo(info, "AffirmAssistanceContractPageInfo");
		info = queryService.listQueryResult(sb.toString(), info);
		return "queryList";
	}	
	
	@SuppressWarnings("unchecked")
	public String cancelSure() throws Exception {
		Object[] oArray =  assistanceService.cancelSure(ids);
		messages = (List<String>)oArray[0];
		alertInfo = (String)oArray[1];
		return  query();
	}
	
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Double getMinConMoney() {
		return minConMoney;
	}

	public void setMinConMoney(Double minConMoney) {
		this.minConMoney = minConMoney;
	}

	public Double getMaxConMoney() {
		return maxConMoney;
	}

	public void setMaxConMoney(Double maxConMoney) {
		this.maxConMoney = maxConMoney;
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
	public Long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getExpId() {
		return expId;
	}

	public void setExpId(String expId) {
		this.expId = expId;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getAssistanceContractType() {
		return assistanceContractType;
	}

	public void setAssistanceContractType(String assistanceContractType) {
		this.assistanceContractType = assistanceContractType;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

}

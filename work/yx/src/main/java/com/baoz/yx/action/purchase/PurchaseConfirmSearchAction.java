package com.baoz.yx.action.purchase;

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
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/purchase/confirmPurchase.jsp"),
	@Result(name = "confirm", value = "/WEB-INF/jsp/purchase/confirmMain.jsp")
})


public class PurchaseConfirmSearchAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;

	@Autowired
	@Qualifier("purService")
	private IPurService pruService;

	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	private ApplyMessage am;
	private List<YXTypeManage>      contractTypeList;
	private PageInfo 				info;
	private List<Object>        	yxClientCodeList;
	private List<YXTypeManage>		dutyDepartmentIdList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	private String select5; // 申购类型
	private String str8; //申购日期1
	private String str9; //申购日期2
	private String  groupId;//小组expId
	private Long  expId;//销售员
	private String  clientName;//客户
	private String opState; 

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"SelectPerQueryParameters",this,new String[]{"groupId","expId","clientName","select5","str8","str9"});	


	public String doFrame( ){
		return "confirm";
	}
	
	public String doDefault() throws Exception {
		//按条件查询
		StringBuffer temp=new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		temp.append("select am from ApplyMessage am,YXClientCode yc,Employee emp,OrganizationTree org where emp.position = org.id and am.is_active =1 and am.customerId = yc.id and am.sellmanId = emp.id and am.affirmState =1 ");

		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId!=null&&!"".equals(groupId)) {
			temp.append(" and org.organizationCode like '").append(groupId+"%'");
		}	
		if (expId!=null&&!"".equals(expId)) {
			temp.append(" and emp.id  =").append(expId);
		}
		if (select5!=null&&!"".equals(select5)) {
			temp.append(" and am.applyType  =").append(select5);
		}	
		if (clientName!=null&&!"".equals(clientName)) {

			temp.append(" and yc.name like '%").append(clientName+"%'");
		}	

		if (str8!=null&&!"".equals(str8)) {
			temp.append(" and am.applyDate > to_date('").append(str8).append("','yyyy-MM-dd') ");
		}
		if(str9!=null&&!"".equals(str9)){
			temp.append(" and am.applyDate < to_date('").append(str9).append("','yyyy-MM-dd') ");
		}
		temp.append(" order by am.updateBy desc , am.id desc");

		info = ParameterUtils.preparePageInfo(info, "purchaseConfirmQueryPage");
		info = queryService.listQueryResult(temp.toString(), info);		
		opState = (String)ActionContext.getContext().getSession().get("opState");
		ActionContext.getContext().getSession().remove("opState");

		return "success";
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
	public IPurService getPruService() {
		return pruService;
	}
	public void setPruService(IPurService pruService) {
		this.pruService = pruService;
	}
	public ApplyMessage getAm() {
		return am;
	}
	public void setAm(ApplyMessage am) {
		this.am = am;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}
	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
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
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public String getSelect5() {
		return select5;
	}
	public void setSelect5(String select5) {
		this.select5 = select5;
	}
	public String getStr8() {
		return str8;
	}
	public void setStr8(String str8) {
		this.str8 = str8;
	}
	public String getStr9() {
		return str9;
	}
	public void setStr9(String str9) {
		this.str9 = str9;
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOpState() {
		return opState;
	}

	public void setOpState(String opState) {
		this.opState = opState;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

}

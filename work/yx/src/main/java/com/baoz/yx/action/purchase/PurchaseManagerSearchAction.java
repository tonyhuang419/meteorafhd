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
	@Result(name = "success", value = "/WEB-INF/jsp/purchase/purchaseManage.jsp"),
	@Result(name = "manage", value = "/WEB-INF/jsp/purchase/manageMain.jsp")
})

public class PurchaseManagerSearchAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("purService")
	private IPurService pruService;

	private ApplyMessage am;
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	private List<YXTypeManage>      contractTypeList;



	private PageInfo 				info;
	private List<Object>        	yxClientCodeList;
	private List<YXTypeManage>		dutyDepartmentIdList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;

	private String select6; // 申购状态
	private String select5; // 申购类型
	private String select2; // 申购单状态
	private String str8; //申购日期1
	private String str9; //申购日期2
	private String statc; //出库状态
	private String  groupId;//小组expId
	private Long  expId;//销售员
	private Long  clientId ;//客户

	private String opState; 


	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"SelectPerQueryParameters",this,new String[]{"groupId","expId","clientId","select6","select5","select2","statc","str8","str9"});	

	public String doFrame( ){
		return "manage";
	}


	public String doDefault() throws Exception {
		//按条件查询
		StringBuffer temp=new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		temp.append("select am from ApplyMessage am,YXClientCode yc,Employee emp,OrganizationTree org where emp.position = org.id and am.is_active =1 and am.customerId = yc.id and am.sellmanId = emp.id ");

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

		if (clientId != null && !"".equals(clientId)) {
			temp.append(" and yc.id =").append(clientId);//客户
		}
		if (select6!=null&&!"".equals(select6)) {
			temp.append(" and am.applyState =").append(select6);
		}
		if (select5!=null&&!"".equals(select5)) {
			temp.append(" and am.applyType =").append(select5);
		}
		if (select2!=null&&!"".equals(select2)) {
			temp.append(" and am.affirmState =").append(select2);
		}
		if (statc!=null&&!"".equals(statc)) {
			temp.append(" and am.outState =").append(statc);
		}
		if (str8!=null&&!"".equals(str8)) {
			temp.append(" and am.applyDate > to_date('").append(str8).append("','yyyy-MM-dd') ");
		}
		if(str9!=null&&!"".equals(str9)){
			temp.append(" and am.applyDate < to_date('").append(str9).append("','yyyy-MM-dd') ");
		}
		temp.append(" order by  decode(am.affirmState, 3,0,0,1,1,2,2,3), am.id desc  )   am.id desc ");
		info = ParameterUtils.preparePageInfo(info, "purchaseManageQueryPage");
		info = queryService.listQueryResult(temp.toString(), info);	
		opState = (String)ActionContext.getContext().getSession().get("opState");
		ActionContext.getContext().getSession().remove("opState");

		return "success";
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

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
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
	public IQueryService getQueryService() {
		return queryService;
	}

	public String getSelect5() {
		return select5;
	}

	public void setSelect5(String select5) {
		this.select5 = select5;
	}

	public String getSelect2() {
		return select2;
	}

	public void setSelect2(String select2) {
		this.select2 = select2;
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

	public String getStatc() {
		return statc;
	}

	public void setStatc(String statc) {
		this.statc = statc;
	}

	public String getSelect6() {
		return select6;
	}

	public void setSelect6(String select6) {
		this.select6 = select6;
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

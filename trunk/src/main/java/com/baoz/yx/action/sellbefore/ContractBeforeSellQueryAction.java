
package com.baoz.yx.action.sellbefore;


import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 *	售前合同查询显示执行新增功能
 * 
 */
@Result(name = "contractBeforeSellList", value = "/WEB-INF/jsp/sellbefore/addSellBeforeManager.jsp")

public class ContractBeforeSellQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	private PageInfo 			info;
	private YXClientCode		ycc;
	private List<Object>		yxLinkManList;
	private List<Object>        yxClientCodeList;			
	private List<Object>		projectDutyList;					
	private List<YXTypeManage>		projectTypeList;					//客户项目类型LIST
	private List<YXTypeManage>		businessTypeList;					//行业类别LIST
	private List<YXTypeManage>		dutyDepartmentIdList;				//工程责任部门list
	private List<YXTypeManage>		projectStateFollowList;             //项目跟踪状态List
	private List<YXTypeManage>      contractTypeList;
	private List<YXTypeManage>      projectStateList;
	private String					succSave;
	@Override
	public String doDefault() throws Exception {
		this.logger.info("售前合同查询显示");
		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		Long uid = new UserUtils().getUser().getId();
		yxClientCodeList = commonService.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		yxLinkManList=commonService.list("from YXLinkMan emp where emp.is_active=1");
		projectTypeList=typeManageService.getYXTypeManage(1007L);
		businessTypeList=typeManageService.getYXTypeManage(1002L);
		dutyDepartmentIdList=typeManageService.getYXTypeManage(1018L);
		projectStateFollowList=typeManageService.getYXTypeManage(1009L);
		contractTypeList=typeManageService.getYXTypeManage(1008L);
		projectStateList=typeManageService.getYXTypeManage(1006L);
		
		this.logger.info("查询完毕");
		return "contractBeforeSellList";
	}

	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public YXClientCode getYcc() {
		return ycc;
	}
	public void setYcc(YXClientCode ycc) {
		this.ycc = ycc;
	}
	public List<Object> getYxLinkManList() {
		return yxLinkManList;
	}
	public void setYxLinkManList(List<Object> yxLinkManList) {
		this.yxLinkManList = yxLinkManList;
	}
	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}
	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
	public List<Object> getProjectDutyList() {
		return projectDutyList;
	}
	public void setProjectDutyList(List<Object> projectDutyList) {
		this.projectDutyList = projectDutyList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getProjectTypeList() {
		return projectTypeList;
	}

	public void setProjectTypeList(List<YXTypeManage> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}

	public List<YXTypeManage> getBusinessTypeList() {
		return businessTypeList;
	}

	public void setBusinessTypeList(List<YXTypeManage> businessTypeList) {
		this.businessTypeList = businessTypeList;
	}

	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}

	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
	}

	public List<YXTypeManage> getProjectStateFollowList() {
		return projectStateFollowList;
	}

	public void setProjectStateFollowList(List<YXTypeManage> projectStateFollowList) {
		this.projectStateFollowList = projectStateFollowList;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public List<YXTypeManage> getProjectStateList() {
		return projectStateList;
	}

	public void setProjectStateList(List<YXTypeManage> projectStateList) {
		this.projectStateList = projectStateList;
	}

	public String getSuccSave() {
		return succSave;
	}

	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}
	
}


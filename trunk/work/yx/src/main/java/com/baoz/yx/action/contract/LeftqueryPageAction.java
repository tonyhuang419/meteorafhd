package com.baoz.yx.action.contract;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
@Result(name = "success", value = "/WEB-INF/jsp/contract/leftpage.jsp")
public class LeftqueryPageAction extends DispatchAction {
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
	@Override
	public String doDefault() throws Exception {
		this.logger.info("售前合同查询显示");
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yxClientCodeList =new ArrayList<Object>();		
		dutyDepartmentIdList = typeManageService.getYXTypeManage(1018L);		
		listExp = new ArrayList<Object>();
		
		groupList = UserUtils.getUserDetail().getDepartments();
		return SUCCESS;
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

}

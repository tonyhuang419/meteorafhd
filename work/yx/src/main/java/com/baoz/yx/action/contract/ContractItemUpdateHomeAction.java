package com.baoz.yx.action.contract;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
@Results({@Result(name="home",value="/WEB-INF/jsp/contract/contractItemUpdateHome.jsp"),
	@Result(name="left",value="/WEB-INF/jsp/contract/contractItemUpdateLeft.jsp")})
public class ContractItemUpdateHomeAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")	
	private ICommonService commonService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private List<Department> groupList;
	private List<YXTypeManage>contractTypeList;//合同性质列表
	private List<Object> yxClientCodeList;//客户选择列表
	private List<Object> listExp; 			// 查询显示所有的销售员
	private List<YXTypeManage>projectDeptTypeList;//工程部门列表
	@Override
	public String doDefault() throws Exception {
		
		return "home";
	}
	@SuppressWarnings("unchecked")
	public String leftFram()throws Exception{
		groupList = UserUtils.getUserDetail().getDepartments();
		yxClientCodeList = new ArrayList<Object>();
		listExp=new ArrayList<Object>();
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return "left";
	}
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}
	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
	public List<Object> getListExp() {
		return listExp;
	}
	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}
	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getProjectDeptTypeList() {
		return projectDeptTypeList;
	}
	public void setProjectDeptTypeList(List<YXTypeManage> projectDeptTypeList) {
		this.projectDeptTypeList = projectDeptTypeList;
	}

}

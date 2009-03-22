package com.baoz.yx.action.sellbefore;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

/**
 *	售前合同管理显示
 *  
 */
@Result(name = "showSellBeforeList", value = "/WEB-INF/jsp/sellbefore/sellBeforeSearch.jsp")

public class SellBeforeSearchQuery extends DispatchAction {
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 			queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private PageInfo 				info;
	private List<Object>        	yxClientCodeList;
	private List<YXTypeManage>		dutyDepartmentIdList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;		
	private String						state;

	@SuppressWarnings("unchecked")
	@Override
	public String doDefault() throws Exception {
		Long uid = UserUtils.getUser().getId();
		state = "1";
		yxClientCodeList = commonService.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		dutyDepartmentIdList = typeManageService.getYXTypeManage(1018L);		
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");	
		groupList = UserUtils.getUserDetail().getDepartments();	
		return "showSellBeforeList";
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

	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}
	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
	}
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}


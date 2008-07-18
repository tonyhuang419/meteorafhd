package com.baoz.yx.action.contract.formalContractManage;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractManageSearch.jsp")
})
public class FormalContractManageSearchAction extends DispatchAction{

	private String start_date = "";       //起始日期
	private String end_date = "";          //结束日期

	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 			queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private List<Object>        	yxClientCodeList;
	
	private List<Object> 			listSale; 			// 查询显示所有的销售员
	private List<Department>		groupList;		   //组别
	private List<Object>            listCli;        // 查询显示所有的客户
	
	 
	
	public String doDefault() throws Exception {
		//logger.info("user id is : " + UserUtils.getUser().getId());
		
		yxClientCodeList = commonService.list("from YXClientCode d where d.id not in(0) and d.is_active=1");				
		listSale = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");	
		groupList = UserUtils.getUserDetail().getDepartments();
		
		return SUCCESS;
	}

	public ICommonService getCommonService() {
		return commonService;
	}


	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
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


	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}


	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public List<Object> getListSale() {
		return listSale;
	}

	public void setListSale(List<Object> listSale) {
		this.listSale = listSale;
	}

	public List<Department> getGroupList() {
		return groupList;
	}


	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public List<Object> getListCli() {
		return listCli;
	}

	public void setListCli(List<Object> listCli) {
		this.listCli = listCli;
	}

}

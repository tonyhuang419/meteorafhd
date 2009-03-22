package com.baoz.yx.action.fileManager;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;

/**
 *	文件下载显示
 *  
 */
@Result(name = "showDownLoadQuery", value = "/WEB-INF/jsp/filemanage/fileSearch.jsp")

public class FileDownQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo 			info;
	private List<YXTypeManage>  fileTypeList;
	private List<YXClientCode>	clientList;
	private List<Employee>	employeeList;
//	private Map	yxFiletype;
//	private Map yxPersoncode;



	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
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

	@Override
	public String doDefault() throws Exception {
		this.logger.info("文件管理查询显示");
		fileTypeList = typeManageService.getYXTypeManage(1014L);
		clientList=commonService.list("select distinct(c) from YXClientCode c,YXFileManage f where f.clientcode=c.id");
		employeeList=commonService.list("select distinct(e) from Employee e,YXFileManage f where f.personcode=e.id");
	    return "showDownLoadQuery";
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

	public List<YXTypeManage> getFileTypeList() {
		return fileTypeList;
	}

	public void setFileTypeList(List<YXTypeManage> fileTypeList) {
		this.fileTypeList = fileTypeList;
	}

	public List<YXClientCode> getClientList() {
		return clientList;
	}

	public void setClientList(List<YXClientCode> clientList) {
		this.clientList = clientList;
	}


}


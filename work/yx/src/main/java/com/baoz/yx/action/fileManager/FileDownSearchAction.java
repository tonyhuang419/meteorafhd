package com.baoz.yx.action.fileManager;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * 文件下载查询操作
 */
@Result(name = "setFileDown", value = "/WEB-INF/jsp/filemanage/fileDownSearchList.jsp")

public class FileDownSearchAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private List<YXTypeManage>  fileTypeList;
	private List<YXClientCode>	clientList;
//	private PageInfo 			info;

	@Override
	public String doDefault() throws Exception {
//		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		this.logger.info("获取文件下载列表");
		fileTypeList=commonService.list("select t.filetype, count(*) as count from YXFileManage t group by t.filetype");
		clientList=commonService.list("select t.filetype,t.clientcode,c.name, count(*) as fcount from YXFileManage t,YXClientCode c where c.id = t.clientcode group by t.filetype,t.clientcode,c.name");
//		for (filetype:fileTypeList) 
//		{
//			if (delSelf.getId() != null)
//			{
//				clientList=commonService.list("select t.client_code, count(*) as fcount from yx_file_manage t where t.file_type = '2' group by t.client_code");
//			}
//		}
		return "setFileDown";
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

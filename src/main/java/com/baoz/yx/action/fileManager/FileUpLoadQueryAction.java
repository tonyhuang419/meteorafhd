package com.baoz.yx.action.fileManager;

import java.util.List;
import java.util.Map;

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
import com.baoz.yx.utils.YxConstants;

/**
 *	文件上传显示
 *  
 */
@Result(name = "showUpLoadQuery", value = "/WEB-INF/jsp/filemanage/fileUpLoad.jsp")

public class FileUpLoadQueryAction extends DispatchAction {
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
	private List<Object>        yxTypeCodeList;
	private List<YXTypeManage>  fileTypeList;
	private List<YXClientCode>	clientList;
//	private Map	yxFiletype;
//	private Map yxPersoncode;



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
		this.logger.info("类型管理查询显示");
		fileTypeList = typeManageService.getYXTypeManage(1014L);
		clientList=commonService.list(" from YXClientCode d where d.id not in(0) and d.is_active=1");
		
	    return "showUpLoadQuery";
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

	public List<Object> getYxTypeCodeList() {
		return yxTypeCodeList;
	}

	public void setYxTypeCodeList(List<Object> yxTypeCodeList) {
		this.yxTypeCodeList = yxTypeCodeList;
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


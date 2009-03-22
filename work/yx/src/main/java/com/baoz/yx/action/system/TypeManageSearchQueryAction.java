package com.baoz.yx.action.system;

import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.utils.YxConstants;

/**
 *	类型管理显示
 *  
 */
@Result(name = "showSellBeforeList", value = "/WEB-INF/jsp/manage/system/typeManage/typeManageSearch.jsp")

public class TypeManageSearchQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	private PageInfo 			info;
	private List<Object>        yxTypeCodeList;
	private Map	yxBigTypeMap;

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public String doDefault() throws Exception {
		this.logger.info("类型管理查询显示");
		yxBigTypeMap=YxConstants.typeBigMap;
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

	public List<Object> getYxTypeCodeList() {
		return yxTypeCodeList;
	}

	public void setYxTypeCodeList(List<Object> yxTypeCodeList) {
		this.yxTypeCodeList = yxTypeCodeList;
	}

	public Map getYxBigTypeMap() {
		return yxBigTypeMap;
	}

	public void setYxBigTypeMap(Map yxBigTypeMap) {
		this.yxBigTypeMap = yxBigTypeMap;
	}



}


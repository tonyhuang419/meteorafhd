package com.baoz.yx.action.system.manage;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

@Result(name = "queryList", value = "/WEB-INF/jsp/system/manage/organizationTreeList.jsp")
@ParentPackage("yx")
public class OrganizationTreeQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	private PageInfo info;

	public String doDefault() throws Exception {

		info = queryService.listQueryResult("FROM OrganizationTree as ot where ot.is_active='1'", info);

		return QUERY_LIST;
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
}

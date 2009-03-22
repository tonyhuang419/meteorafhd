package com.baoz.yx.action.system;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

/**
 * 部门查询操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/department/departmentList.jsp")
@ParentPackage("yx")
@SuppressWarnings("unchecked")
public class DepartmentQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")
	private PageInfo info;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("部门列表");

		info = queryService.listQueryResult(
				"from Department d where d.id not in(0)", info);
		return "queryList";
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

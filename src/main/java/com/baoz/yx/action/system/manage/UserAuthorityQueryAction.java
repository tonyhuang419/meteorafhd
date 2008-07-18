package com.baoz.yx.action.system.manage;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @author shaoyx
 *
 * Jun 23, 2008, 11:46:35 AM
 *
 * todo: 权限列表
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/manage/userAuthorityList.jsp")
@SuppressWarnings("unchecked")
@ParentPackage("yx")
public class UserAuthorityQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService	queryService;

	private PageInfo		info;

	@Override
	public String doDefault() throws Exception {

		info = queryService.
			listQueryResult("select count(*) from Employee e", "select e, (select ot.organizationName from OrganizationTree ot where ot.id=e.position ) from Employee e ", info);
		
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

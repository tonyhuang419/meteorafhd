package com.baoz.yx.action.system;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IUserService;


@Result(name = "queryList", value = "/WEB-INF/jsp/system/user/userList.jsp")
@ParentPackage("yx")
@SuppressWarnings("unchecked")
public class UserQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("userService")
	private IUserService userService;		
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;	
	
	public String doDefault() throws Exception {
		info = queryService.listQueryResult(
				" from Employee u where u.id not in(0) and u.is_active=1 ", info);
		logger.info(info.getResult());		
		return "queryList";
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
}
	
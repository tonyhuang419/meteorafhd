package com.baoz.yx.action.system.manage;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
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
@Results( { 
	@Result(name = "frame", value = "/WEB-INF/jsp/system/manage/userAuthorityFrame.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/system/manage/userAuthorityList.jsp"),
	@Result(name = "search", value = "/WEB-INF/jsp/system/manage/userAuthoritySearch.jsp")
})

@ParentPackage("yx")
public class UserAuthorityQueryAction extends DispatchAction {

	private String					clientName;     //用户名称
	private String 					userName;		//用户登陆名称
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService	queryService;

	private PageInfo		info;

	
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"SelectPerQueryParameters",this,new String[]{"userName","clientName"});
    
	@Override
	public String doDefault() throws Exception {
		return "frame";
	}
	
	public String quryList(){
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sb =  new StringBuffer(" select e, (select ot.organizationName from " +
				" OrganizationTree ot where ot.id=e.position ) from Employee e where e.is_active='1'" );	
		if (StringUtils.isNotBlank(userName)) {
			userName = userName.trim();
			sb.append(" and e.username like '%").append(userName+"%'");
		}
		if (StringUtils.isNotBlank(clientName)) {
			clientName = clientName.trim();
			sb.append(" and e.name like '%").append(clientName+"%'");
		}
		sb.append(" order by e.id desc ");
		info = queryService.listQueryResult(SqlUtils.getCountSql(sb.toString()), sb.toString() , info);
		return QUERY_LIST;
	}
	
	public String search(){	
		return "search";
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

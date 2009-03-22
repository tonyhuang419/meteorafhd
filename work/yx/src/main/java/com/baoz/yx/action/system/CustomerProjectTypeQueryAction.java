package com.baoz.yx.action.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
/**
 * 客户项目类型查询操作
 * 
 * @author Alvin (mixb@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/customerProjectType/customerProjectTypeList.jsp")
public class CustomerProjectTypeQueryAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("客户项目类型查询");
		info = queryService.listQueryResult(
				" from CustomerProjectType cpt where cpt.id not in(0)", info);
		return "queryList";
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
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


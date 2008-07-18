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
 * 地域代码查询操作
 * 
 * @author Alvin (mixb@baoz.com.cn)
 */

@Result(name = "queryList", value = "/WEB-INF/jsp/system/zoneCode/zoneCodeList.jsp")
public class ZoneCodeQueryAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("地域代码查询");
		info = queryService.listQueryResult(
				" from ZoneCode zc where zc.id not in(0) and zc.is_active=1 ", info);
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
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub

	}

}

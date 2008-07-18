package com.baoz.yx.action.system;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Gonggao;

/**
 * 公告查询操作
 * 
 * @author 
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/manage/system/reportManage/reportManageList.jsp")
public class GongGaoQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private Gonggao gg;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("公告查询");
		info = queryService.listQueryResult(
				"select g,e from Gonggao g,Employee e where g.byId=e.id and g.is_active=1 order by g.updateBy desc", info);
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
	
	public Gonggao getGg() {
		return gg;
	}
	
	public void setGg(Gonggao gg) {
		this.gg = gg;
	}
	
}

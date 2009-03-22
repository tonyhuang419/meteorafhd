package com.baoz.yx.action.system;


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
@Result(name = "queryList", value = "/WEB-INF/jsp/system/dutyList.jsp")

public class DutyCodeQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("职责查询");
		info = queryService.listQueryResult(
				" from YXDutyCode dc where dc.id not in(0) and dc.is_active!=0", info);
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
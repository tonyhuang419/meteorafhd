package com.baoz.yx.action.harvestMeansManager;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;



/**
 * 收款合同
 * 
 */
@Result(name = "moonHarvestProgramList", value = "/WEB-INF/jsp/harvestMeansManager/moonHarvestProgram.jsp")
public class MoonHarvestProgramQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("月度收款计划查询");
		info = queryService.listQueryResult(
				"from YXClientCode d where d.id not in(0) and d.is_active!=0",
				info);
		logger.info("查询完毕");
		return "moonHarvestProgramList";
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

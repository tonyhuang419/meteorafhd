
package com.baoz.yx.action.system;


import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	联系人性质查询操作
 * 
 * 
 */
@Result(name = "supplyTypeCodeList", value = "/WEB-INF/jsp/system/supplyType/supplyTypeCode.jsp")

public class SupplyTypeCodeQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("供应商类别查询");
		info = queryService.listQueryResult(
				" from SupplyTypeCode stc where stc.id not in(0) and stc.is_active !=0", info);
		logger.info("=================info="+info);
		return "supplyTypeCodeList";
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


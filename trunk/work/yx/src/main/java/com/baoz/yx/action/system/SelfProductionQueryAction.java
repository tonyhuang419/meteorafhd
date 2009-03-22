package com.baoz.yx.action.system;

import java.util.Date;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.SelfProduction;

/**
 * 自有产品查询操作
 * 
 * @author 
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/manage/system/selfproductManage/selfproductManageList.jsp")
public class SelfProductionQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private SelfProduction sp;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("自有产品查询");
		info = queryService.listQueryResult(
				" from SelfProduction sp where sp.is_active=1 and sp.validateTime >= to_date('"+DateUtil.format(new Date(), "yyyy-MM-dd")+"','yyyy-MM-dd')", info);
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
	
	public SelfProduction getSp() {
		return sp;
	}
	
	public void setSp(SelfProduction sp) {
		this.sp = sp;
	}
	
}

package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;

@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceMList.jsp")
public class AssistanceMQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("systemService")
	private ISystemService systemService;
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private PageInfo info ;
	private List list;
	@Override
	public String doDefault() throws Exception {
		this.logger.info("外协管理查询");
		StringBuffer sb = new StringBuffer();
		sb.append("from AssistanceContract ac where ac.id not in(0) and ac.is_active='1'");
		info = queryService.listQueryResult(sb.toString(), info);
		List queryList = (List)info.getResult();
		list = assistanceService.queryAssistanceContract(queryList);
		return "queryList";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	
}

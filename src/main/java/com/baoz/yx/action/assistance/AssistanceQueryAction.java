package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;
@Results({
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceList.jsp"),
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/assistanceListMore.jsp")})
public class AssistanceQueryAction extends DispatchAction  {
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
	private String id;
	private AssistanceContract ac;
	private String expName;
	@Override
	public String doDefault() throws Exception {
		this.logger.info("外协合同管理");
		StringBuffer sb = new StringBuffer();
		sb.append("from AssistanceContract ac where ac.id not in(0) and ac.is_active='1'");
		if(expName!=null && !"".equals(expName)){
			sb.append("and ac.");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		List queryList = (List)info.getResult();
		list = assistanceService.queryAssistanceContract(queryList);
		return "queryList";
	}
	public String info() throws Exception {
		this.logger.info("详细信息");
		StringBuffer strb = new StringBuffer();
		strb.append("from AssistanceContract ac where ac.assistanceId ="+id+" and ac.is_active='1'");
		List queryList= service.list(strb.toString());
		list = assistanceService.queryAssistanceContract(queryList);
		return "success";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public AssistanceContract getAc() {
		return ac;
	}
	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

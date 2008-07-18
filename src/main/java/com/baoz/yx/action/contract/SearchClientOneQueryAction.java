package com.baoz.yx.action.contract;



import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;


	@Result(name = "success", value = "/WEB-INF/jsp/contract/contractClineQuery.jsp")
	
public class SearchClientOneQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private String clientnameone;
	
	public String doDefault() throws Exception {
		logger.info("====================state");
		System.out.print("chaxun 查询");
		
		String shql = "from ContractMainInfo d,YXClientCode yc,Employee emp,ContractOtherInfo o " +
				"where o.contractMainSid =s d.id and d.itemCustomer = yc.id and d.saleMan = emp.id";
		info = queryService.listQueryResult(shql, info);
		logger.info("test");
		return SUCCESS;
	}
	public String relationTicket() throws Exception {
 	
		System.out.print(this.getClientnameone()+"   chaxun .................查询");
		StringBuffer str = new StringBuffer();		
		str.append("from ContractMainInfo d,YXClientCode yc,Employee emp,ContractOtherInfo o " +
				"where o.contractMainSid = d.id and d.itemCustomer = yc.id and d.saleMan = emp.id");
		if (this.getClientnameone()!=null&&!"".equals(this.getClientnameone())) {
			
			str.append(" and yc.name like '%").append(this.getClientnameone()+"%'");
		}	
		info = queryService.listQueryResult(str.toString(),info);
		return SUCCESS;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
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
	public String getClientnameone() {
		return clientnameone;
	}
	public void setClientname(String clientnameone) {
		this.clientnameone = clientnameone;
	}
	public void setClientnameone(String clientnameone) {
		this.clientnameone = clientnameone;
	}
}

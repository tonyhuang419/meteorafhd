package com.exam.action.demo;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.service.IQueryService;
import com.exam.utils.PageInfo;

@Results( {
	@Result(name = "demoPage", value = "/WEB-INF/jsp/demo/page.jsp")
})
public class PageAction extends BaseAction{
	private static final long serialVersionUID = -6929869573044077058L;


	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	private PageInfo info;

	public String demoPage(){
		ServletActionContext.getRequest ().getParameter("pageNo");
		info = queryService.listQueryResult(" from Book  ", info);
//		commonService.execModifyProcedure();
		return "demoPage";
	}
	
	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}


}



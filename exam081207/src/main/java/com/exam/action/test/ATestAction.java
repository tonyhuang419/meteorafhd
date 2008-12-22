package com.exam.action.test;


import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.service.IQueryService;
import com.exam.utils.PageInfo;

@Results( {
	@Result(name = "ff", value = "/WEB-INF/jsp/test/atest.jsp")
})
public class ATestAction extends BaseAction{
	private static final long serialVersionUID = -6929869573044077058L;


	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	private PageInfo info;

	public String ff(){
		info = queryService.listQueryResult(" from Book  ", info);
		return "ff";
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}


}



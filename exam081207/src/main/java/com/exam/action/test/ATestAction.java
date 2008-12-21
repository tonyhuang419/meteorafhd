package com.exam.action.test;


import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.service.IQueryService;

@Results( {
	@Result(name = "ff", value = "/WEB-INF/jsp/test/atest.jsp")
})
public class ATestAction extends BaseAction{
	private static final long serialVersionUID = -6929869573044077058L;
	
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	
	public String ff(){
//		PageInfo info = queryService.listQueryResult(" from Book  ", null);
		return "ff";
	}

}



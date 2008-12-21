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
	
	
//	private Integer total=40;
//	private Integer pageNo=5;
	public String ff(){
		PageInfo info = queryService.listQueryResult(" from Book  ", null);
		return "ff";
	}
//	public Integer getTotal() {
//		return total;
//	}
//
//	public void setTotal(Integer total) {
//		this.total = total;
//	}
//
//	public Integer getPageNo() {
//		return pageNo;
//	}
//
//	public void setPageNo(Integer pageNo) {
//		this.pageNo = pageNo;
//	}
}



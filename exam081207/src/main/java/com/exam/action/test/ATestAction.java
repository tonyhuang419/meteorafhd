package com.exam.action.test;


import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.exam.action.BaseAction;

@Results( {
	@Result(name = "ff", value = "/WEB-INF/jsp/test/atest.jsp")
})
public class ATestAction extends BaseAction{
	private static final long serialVersionUID = -6929869573044077058L;
	
	private Integer total=40;
	private Integer pageNo=5;
	public String ff(){
		return "ff";
	}
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}



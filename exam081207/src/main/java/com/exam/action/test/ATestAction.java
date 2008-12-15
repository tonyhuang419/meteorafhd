package com.exam.action.test;


import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.exam.action.BaseAction;

@Results( {
	@Result(name = "ff", value = "/WEB-INF/jsp/test/atest.jsp")
})
public class ATestAction extends BaseAction{
	private static final long serialVersionUID = -6929869573044077058L;
	
	private String test="test";
	private Integer total=40;
	private Integer pageNo=5;
	private Long test1=5L;
	private Float test2=9.8F;
	private int test3=8;    
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

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public Long getTest1() {
		return test1;
	}

	public void setTest1(Long test1) {
		this.test1 = test1;
	}

	public Float getTest2() {
		return test2;
	}

	public void setTest2(Float test2) {
		this.test2 = test2;
	}

	public int getTest3() {
		return test3;
	}

	public void setTest3(int test3) {
		this.test3 = test3;
	}

}



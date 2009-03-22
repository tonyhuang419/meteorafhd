package com.baoz.yx.action;


import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.TestCollection;

@Results( { @Result(name = "success", value = "/WEB-INF/jsp/test.jsp")})
public class TestAction extends DispatchAction{
	List<TestCollection> tests;

	@Override
	public String doDefault() throws Exception {
		System.out.println(tests);
		return super.doDefault();
	}

	public List<TestCollection> getTests() {
		return tests;
	}

	public void setTests(List<TestCollection> tests) {
		this.tests = tests;
	}
	
}

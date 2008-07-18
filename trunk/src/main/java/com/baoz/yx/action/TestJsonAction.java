package com.baoz.yx.action;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.components.json.JSONResult;
import com.baoz.components.json.annotations.JSON;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.TestCollection;

/**
 * 类TestJsonAction.java的实现描述：测试json
 * @author xurong Jun 10, 2008 5:35:45 PM
 */
@ParentPackage(value = "json-default")
@Results( { @Result(name = "success", type=JSONResult.class , value = "")})
public class TestJsonAction extends DispatchAction{
	private TestCollection[] testArray = new TestCollection[2];
	private String param;
	@Override
	public String doDefault() throws Exception {
		testArray[0] = new TestCollection();
		testArray[0].setName("11111");
		testArray[0].setCode("vvvvv");
		testArray[1] = new TestCollection();
		testArray[1].setName("22222");
		testArray[1].setCode("wwwww");
		return "success";
	}
	public TestCollection[] getTestArray() {
		return testArray;
	}
	public void setTestArray(TestCollection[] testArray) {
		this.testArray = testArray;
	}
	@JSON(serialize=false)
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}

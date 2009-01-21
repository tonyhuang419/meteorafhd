package com.teststruts.action;

import servletunit.struts.MockStrutsTestCase;

public class LoginActionTest  extends MockStrutsTestCase {

	public void setUp()throws Exception { 
		super.setUp(); 
	}

	public void tearDown() throws Exception {
		super.tearDown(); 
	}

	public LoginActionTest(String testName) { 
		super(testName); 
	}


	public void testExecute(){ 
		setConfigFile("struts-config.xml");
		setRequestPathInfo("/login.do");
		addRequestParameter("username","fhd");
		addRequestParameter("password","amiga");
		actionPerform();
		verifyForward("s");
				verifyInputForward();
	}

}


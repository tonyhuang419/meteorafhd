package test.gl.mock;

import baosight.baosteel.uup.client.model.UserSummary;


public class TestTools {
	
	static public UserSummary doMakeAUSER(){
		UserSummary us = new UserSummary();
		us.setLoginName("testuser");
		return us;
	}
}

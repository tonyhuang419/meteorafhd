package mytest.action.utils;

import junit.framework.TestCase;

import com.baoz.yx.entity.Employee;
import com.baoz.yx.utils.UserUtils;

import mytest.mock.*;

public class UserUtilsTest extends TestCase {


	public void testGetUser(){
		MockUser u = new MockUser();
		u.mockAdmin();
		Employee e = UserUtils.getUser();
		assertEquals( e.getId(),new Long(-1));	
	}
}

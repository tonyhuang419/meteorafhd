package com.exam;


import org.springframework.test.AbstractTransactionalSpringContextTests;

public class YingXiaoBaseTest extends
		AbstractTransactionalSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[]{"applicationContext.xml"};
	}
}

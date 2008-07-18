package com.baoz.yx;

import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * 类EntityDBTypeMatchTest.java的实现描述：营销测试基类
 * @author xurong Jun 16, 2008 2:41:46 PM
 */
public class YingXiaoBaseTest extends
		AbstractTransactionalSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[]{"applicationContext.xml"};
	}
}

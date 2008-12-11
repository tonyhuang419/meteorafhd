package com.exam;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public class ExamBaseTest extends AbstractTransactionalSpringContextTests {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	protected String[] getConfigLocations() {
		return new String[]{"applicationContext.xml"};
	}
}

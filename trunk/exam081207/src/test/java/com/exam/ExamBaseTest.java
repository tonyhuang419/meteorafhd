package com.exam;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ExamBaseTest {

	protected Log logger = LogFactory.getLog(this.getClass());

}

//public class ExamBaseTest extends AbstractTransactionalSpringContextTests {
//
//	protected Log logger = LogFactory.getLog(this.getClass());
//
//	@Override
//	protected String[] getConfigLocations() {
//		return new String[]{"applicationContext.xml"};
//	}
//}


//@Override
//protected void prepareTestInstance() throws Exception {
//	super.setAutowireMode(AUTOWIRE_BY_NAME);
//	super.prepareTestInstance();
//	super.setDefaultRollback(false);
//}

package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class SubTest extends BaseTest {

	@Before
	public void runBeforeTest(){
		super.runBeforeTest();
		System.out.println("I'm subtest's before");
	}

	@Test
	public void subTestOne(){
		System.out.println("I'm subtest's testOne");
	}

	@After
	public void runAfterTest(){
		System.out.println("I'm subtest's After");
		super.runAfterTest();
	}
}

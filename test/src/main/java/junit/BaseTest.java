package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BaseTest {

	@Before
	public void runBeforeTest(){
		System.out.println("I'm basetest's before");
	}

	@Test
	public void baseTestOne(){
		System.out.println("I'm basetest's testOne");
	}

	@After
	public void runAfterTest(){
		System.out.println("I'm basetest After");
	}
}

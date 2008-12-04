package def;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class JUnitTest {

	@Before 
	public void runBeforeTest(){

	}

	@Test
	public void testOne(){
		String s = "第一份合同";
		assertTrue(s.equals("第一份合同"));
		assertEquals(s, "第一份合同");
	}

	@Test
	public void testTwo(){

		Set<String> set = new HashSet();
		set.add("第1份合同");
		set.add("第2份合同");
		set.add("第2份合同");
		assertTrue(set.contains("第1份合同"));
	}

}

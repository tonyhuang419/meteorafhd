package testNGDemo;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StringUtilsTest {
	
	@Test(groups = { "a" })
	public void isEmpty()
	{
		System.out.println("### test_isEmpty");
		assert StringUtils.isBlank(null);
		assert StringUtils.isBlank("");
	}
	
	@Test(groups = { "b" })
	public void trim()
	{
		System.out.println("### test_trim");
		assert "foo".equals(StringUtils.trim("  foo   "));
	}

//	@Configuration(beforeTestClass = true, groups = {"tests.workflow"})
//	public void setUp()
//	{
//	   System.out.println("Initializing...");
//	}
//	@Configuration(afterTestMethod = true, beforeTestMethod = true, groups = {"tests.workflow"})
//	public void aroundTestMethods()
//	{
//	   System.out.println("Around Test");
//	}
	
	@BeforeTest
	public void beforeTestMethods()
	{
	   System.out.println("...Before Test");
	}
	
	@AfterTest
	public void afterTestMethods()
	{
	   System.out.println("...After Test");
	}
	
	@BeforeMethod
	public void beforeMethods()
	{
	   System.out.println(">>>BeforeMethod");
	}
	
	@AfterMethod
	public void afterMethods()
	{
	   System.out.println("<<<AfterMethod");
	}
	
	@AfterGroups(groups={"a","b"})
	public void afterGroups()
	{
	   System.out.println(",,,,,AfterGroups");
	}
	
	@BeforeGroups(groups={"a","b"})
	public void beforeGroups()
	{
	   System.out.println(",,,,,BeforeGroups");
	}

}

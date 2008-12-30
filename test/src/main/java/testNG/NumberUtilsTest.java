package testNG;

import org.apache.commons.lang.math.NumberUtils;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

public class NumberUtilsTest {
	@Test(groups = {"tests.math"})
	@ExpectedExceptions(NumberFormatException.class)
	public void test()
	{
		NumberUtils.createDouble("12.23.45");
		assert false;  //shouldn't be invoked
	}
}

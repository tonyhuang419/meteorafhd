package testNG;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

public class StringUtilsTest {
	
	@Test
	public void isEmpty()
	{
		assert StringUtils.isBlank(null);
		assert StringUtils.isBlank("");
	}
	@Test
	public void trim()
	{
		assert "foo".equals(StringUtils.trim("  foo   "));
	}

}

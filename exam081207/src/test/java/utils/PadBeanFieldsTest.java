package utils;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.exam.tools.PadBeanFields;

public class PadBeanFieldsTest {


	@Test
	public void testX(){
		TestUse t = new TestUse();
		t.setX("xx");
		TestUse tt = new TestUse();
		PadBeanFields.padBean( t, tt , null);
		System.out.println(tt.getX());

		TestUse ttS = new TestUse();
		BeanUtils.copyProperties(t, ttS);
		System.out.println(ttS.getX());

	}

}


class TestUse {
	private String X;
	private String Y;
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}

}



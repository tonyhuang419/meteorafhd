package def;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;

public class Test2 {

	/**
	 * apache和spring继承属性也会赋值
	 * null值也会赋值
	 * 他们似乎都依赖bean的getter setter
	 */
	public void copyBean(){
		SubClass a = new SubClass();
		a.setBaseString("1");
		a.setSubString("2");
		
		SubClass b = new SubClass();
		SubClass c = new SubClass();
		SubClass d = new SubClass();
	
		BeanUtils.copyProperties( a , b);
		
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(c, a);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		PadBeanFields.padBean(a, d, null);
		
		System.out.println(b.getBaseString());
		System.out.println(b.getSubString());
		
		System.out.println(c.getBaseString());
		System.out.println(c.getSubString());
		
		System.out.println(d.getBaseString());
		System.out.println(d.getSubString());
	}
	
	public void trimDemo(){
		String s = "123\n";
		System.out.println(s);
		System.out.println("========");
		System.out.println(s.trim());
		System.out.println("========");
	}
	
	
	public static void main(String[] args) {
		Test2 t = new Test2();
//		t.trimDemo();
		t.copyBean();
	}
}

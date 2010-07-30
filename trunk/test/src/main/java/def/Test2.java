package def;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

public class Test2 {

	
	private SubClass getSubClass(){
		SubClass sub = new SubClass();
		sub.setBaseString("1");
		sub.setSubString("2");
		return sub;
	}
	
	private BaseClass getBaseClass  (){
		BaseClass bc = new BaseClass();
		bc.setBaseString("3");
		return bc;
	}
	
	/**
	 * apache和spring继承属性也会赋值
	 * null值也会赋值
	 * 他们似乎都依赖bean的getter setter
	 * spring 需要对象类型相同,否则会报错
	 * apache 则不需要
	 */
	public void copyBeanDemo(){
		
		//spring copy
		//copy beanSelf
		SubClass spring = new SubClass();
		BeanUtils.copyProperties( getSubClass() , spring );
		System.out.println(StringUtils.center("spring copy", 50, "#"));
		System.out.println(spring.getBaseString());
		System.out.println(spring.getSubString());
		
		//sub class copy father class
		BaseClass springB = new BaseClass();
		System.out.println(StringUtils.center("spring2 copy", 50, "#"));
		BeanUtils.copyProperties( getSubClass() , springB );
		System.out.println(springB.getBaseString());
		
		//father class copy sub class
		SubClass springS = new SubClass();
		System.out.println(StringUtils.center("spring3 copy", 50, "#"));
		BeanUtils.copyProperties( getBaseClass() , springS );
		System.out.println(springS.getBaseString());
		
		
		//apache copy
		//copy beanSelf
		SubClass apache = new SubClass();
		System.out.println(StringUtils.center("apache copy", 50, "#"));
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(apache, getSubClass() );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(apache.getBaseString());
		System.out.println(apache.getSubString());
		
		
		//sub class copy father class
		BaseClass apacheB = new BaseClass();
		System.out.println(StringUtils.center("apache2 copy", 50, "#"));
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(apacheB, getSubClass() );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(apacheB.getBaseString());
		
		
		//father class copy sub class
		SubClass apacheS = new SubClass();
		System.out.println(StringUtils.center("apache3 copy", 50, "#"));
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(  apacheS , getBaseClass() );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(apacheS.getBaseString());
		
		
		//my copy, too sucks, don't use,just looking
		//father class copy sub class
		System.out.println(StringUtils.center("my copy", 50, "#"));
		SubClass subMy = new SubClass();
		PadBeanFields.padBean(  getBaseClass() , subMy , null);
		System.out.println( subMy.getBaseString());

		
	}
	
	public void trimDemo(){
		String s = "123\n";
		System.out.println(s);
		System.out.println("========");
		// \n could be trimed
		System.out.println(s.trim());
		System.out.println("========");
	}
	
	
	public static void main(String[] args) {
		Test2 t = new Test2();
//		t.trimDemo();
		t.copyBeanDemo();
	}
}

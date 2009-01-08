package commons.beanutils;

import org.springframework.beans.BeanUtils;

import com.sun.accessibility.internal.resources.accessibility;

public class TestBeanUtils {

	class A{
		int age;
		String name;
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public A getAInstance(){
		A a = new A();
		a.age = 11;
		a.name=null;
		return a;
	}
	
	public void test1(){
		A tar = new A();
		A src = this.getAInstance();
		tar.setAge(99);
		tar.setName("111");
		BeanUtils.copyProperties( src ,  tar );
		System.out.println(tar.getAge());
		System.out.println(tar.getName());
	}
	
	
	public static void main(String[] args) throws Exception {
		new TestBeanUtils().test1();
	}
}

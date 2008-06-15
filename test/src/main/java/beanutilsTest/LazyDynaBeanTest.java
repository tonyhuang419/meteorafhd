package beanutilsTest;

import java.util.Map;

import org.apache.commons.beanutils.LazyDynaBean;

public class LazyDynaBeanTest {
	public static void main(String[] args) {
		//创建一个LazyDynaBean的实例，它就和普通的bean一样
		LazyDynaBean customer = new LazyDynaBean();
		customer.set("name","Tom");
		customer.set("sex","male");
		customer.set("age",new Integer(25));
		//以map方式给属性赋值，第一个参数是属性名，第二个参数是键，第三个参数是值
		customer.set("address","province","hunan");
		//如果属性是一个数组或集合，可以以索引方式赋值
		//第一个参数是数组名或集合名，第二个参数是索引，第三个参数是数组和集合中存的值
		customer.set("orders",0,"order001");
		customer.set("orders",1,"order002");

		System.out.println("name: " + customer.get("name"));
		System.out.println("sex: " + customer.get("sex"));
		System.out.println("age: " + customer.get("age"));
		System.out.println("address: " + ((Map)customer.get("address")).get("province"));
		System.out.println("orders: " + customer.get("orders",0) + " " + customer.get("orders",1));
	}
}

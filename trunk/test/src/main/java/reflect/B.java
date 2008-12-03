package reflect;

import java.lang.reflect.Field;

public class B {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException {
		Class clazz=A.class;
		Field  field=clazz.getDeclaredField("rubbish");              // 要是私有要用这个方法

		Field  field1=clazz.getDeclaredField("rubbish1");          

		Field  field2=clazz.getField("rubbish1");       //只能访问public

		field.setAccessible(true);//要是私有这个很重要

		A obj=(A)clazz.newInstance();

		System.out.println("私有"+field.get((obj)));
		System.out.println("公有"+field1.get((obj)));
		System.out.println("公有"+field2.get((obj)));
	}
}

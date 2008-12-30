package annotation.inherited;

import java.lang.reflect.Method;


public class TestInherit {
	public static void main(String[] args) throws Exception{   
		//      Class< Parent > clazz = Parent.class;   
		Class< Child > clazz = Child.class;   
		if(clazz.isAnnotationPresent(MyInherit.class)){   
			MyInherit myInherit = clazz.getAnnotation(MyInherit.class);   
			System.out.println(myInherit.value());   
		}   

		Method method = clazz.getMethod("doSomething", new Class[]{});   
		if(method.isAnnotationPresent(MyInherit.class)){   
			MyInherit myInherit = method.getAnnotation(MyInherit.class);   
			System.out.println(myInherit.value());   
		}   
	}    
}

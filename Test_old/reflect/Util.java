package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Util {
	public static Base convertStr2ServiceBean(String beanName,String fieldSetter,String paraValue){
		Base base = null ;
		try {
			Class cls = Class.forName(beanName) ;
			base = (Base)cls.newInstance() ;
			Class[] paraTypes = new Class[]{String.class };
			Method method = cls.getMethod(fieldSetter, paraTypes) ;
			String[] paraValues = new String[]{paraValue} ;
			method.invoke(base, paraValues) ;
		} catch (Throwable e) {
			System.err.println(e);
		}
		return base ;
	} 
	public static void main(String[] args) throws Exception{
		Son1 son1 =(Son1) Util.convertStr2ServiceBean("reflect.Son1","setName","wang da sha");
		System.out.println("son1.getName() :"+son1.getName()) ;

		Class son2 = Class.forName("reflect.Son2") ;
		Method method[] = son2.getDeclaredMethods();
		for (int i = 0; i < method.length; i++){
			System.out.println(method[i].toString() );
		}
		
//		Class son1 = Class.forName("reflect.Son1") ;
//		Field Field[] = son1.getDeclaredFields();
//		for (int i = 0; i < Field.length; i++){
//			System.out.println(Field[i].toString() );
//		}
		
//		Class son1 = Class.forName("reflect.Son1") ;
//		Field Field[] = son1.getFields();
//		for (int i = 0; i < Field.length; i++){
//			System.out.println(Field[i].toString() );
//		}
	}
} 
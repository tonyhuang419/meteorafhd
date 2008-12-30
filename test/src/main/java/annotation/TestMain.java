package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class TestMain {
	//可以通过AnnotatedElement来获得到Annotation类的信息。Method,Field等都是AnnotatedElement的实现类       
	public static void main(String[] args) throws Exception {       

		MyTest mt = new MyTest();       
		Class clazz = MyTest.class;       


		//Method method = clazz.getMethod("say", null);//因为是无参数的，所以直接写个null也可以       
		Method method = clazz.getMethod("say", new Class[]{});       
		method.invoke(mt, new Object[]{});       

		//该方法是否使用了MyAnnotation这个注解类       
		boolean isExist = method.isAnnotationPresent(MyAnnotation.class) ;       
		if(isExist){       
			MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);       
			System.out.println(annotation.hello());       
			System.out.println(annotation.world());       
		}       

		Annotation[] annotations = method.getAnnotations();       
		for(     Annotation    annotation   :    annotations   ){       
			//打印出这样的结果：com.yahaitt.annotation.MyAnnotation       
			System.out.println(annotation.annotationType().getName());       

			//不能通过getClass().getName()的方式获得了。这样获得的结果类似这样的：$Proxy3，主要原因是因为Annotation在运行时刻，是通过java的动态代理实现的，   
			//每次得到一个annotation，实际上并不是得到该annotation的实例，而是得到了它的一个代理，这个代理java在命名上采用的是$Proxy1,$Proxy2...的形式，数字是按照出现的顺序来定的   
			//而getClass()方法返回的是该对象运行时刻所代表的真实对象,在这里也就是代理对象了   
			System.out.println(annotation.getClass().getName());    

			//输出结果为：java.lang.reflect.Proxy   
			System.out.println(annotation.getClass().getSuperclass().getName());     
		}       


	}   
}

//增强一个已有类

package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ATest {	
	public static void main(String[] args) {		
		Enhancer enhancer = new Enhancer();		
		enhancer.setSuperclass(ClassA.class);		
		enhancer.setCallback( new MethodInterceptorImpl() );	
		ClassA my = (ClassA)enhancer.create();		
		my.method();	
	}	
	private static class MethodInterceptorImpl implements MethodInterceptor {	
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {	
			System.out.println(method);		
			proxy.invokeSuper(obj, args);		
			return null;		
		}
	}
}


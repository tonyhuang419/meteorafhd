// π”√CallbackFilter

package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
public class BTest {	
	public static void main(String[] args) {	
		Callback[] callbacks = new Callback[] {
				new MethodInterceptorImpl(), NoOp.INSTANCE 
		};		
		Enhancer enhancer = new Enhancer();	
		enhancer.setSuperclass(ClassB.class);
		enhancer.setCallbacks( callbacks );		
		enhancer.setCallbackFilter(new CallbackFilterImpl());	
		ClassB my = (ClassB)enhancer.create();	
		my.method();
		my.method2();
	}	
	private static class CallbackFilterImpl implements CallbackFilter {
		public int accept(Method method) {		
			if ( method.getName().equals("method2") ) {		
				System.out.println("123");
				return 1;	
			} 
			else {
				return 0;	
			}	
		}	
	}	
	private static class MethodInterceptorImpl implements MethodInterceptor {
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {	
			System.out.println(method);		
			return proxy.invokeSuper(obj, args);	
		}	
	}
}

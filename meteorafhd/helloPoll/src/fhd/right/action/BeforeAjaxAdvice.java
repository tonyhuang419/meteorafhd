package fhd.right.action;

import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

//public class BeforeAjaxAdvice implements MethodBeforeAdvice {
//public void before(Method m, Object[] args, Object target) throws Throwable {
////WebContext ctx = WebContextFactory.get();//唯一被DWR污染的地方
//HttpSession session = (HttpSession)args[4];
//System.out.println(session.getAttribute("username"));
//}
//}

public class BeforeAjaxAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		HttpSession session = (HttpSession)invocation.getArguments()[4];
		if( session.getAttribute("username")!= ""){
			Object s = invocation.proceed();
			return s;
		}
		else{
			return "请登录先";
		}
	}
}

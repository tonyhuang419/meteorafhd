package aop;

import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
		IAccount account = (IAccount) Proxy.newProxyInstance(
				IAccount.class.getClassLoader(),
				new Class[] { IAccount.class },
				new SecurityProxyInvocationHandler(new AccountImpl())
		);
		account.operation();
	}
}

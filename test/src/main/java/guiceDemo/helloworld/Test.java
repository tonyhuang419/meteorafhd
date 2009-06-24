package guiceDemo.helloworld;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * http://www.ibm.com/developerworks/cn/java/j-guice.html
 * 
 * 普通方式
 * 需要类MyModule
 * http://www.99inf.net/SoftwareDev/Java/50755.htm
 * 
 * Annotation方式
 * 在接口使用如下代码：exp@ImplementedBy(DemoServiceImpl.class)
 * http://tech.ddvip.com/2009-01/1231516803105463.html
 */

public class Test {

	public static void main(String[] args) throws Exception {
		
		System.out.println("-----------config binding----------");
		MyModule myModule = new MyModule();
		Injector in = Guice.createInjector(myModule);
		Client client= new Client();
		in.injectMembers(client);
		client.go();
		
		client.go();
		
		
//		Method method = client.getClass().getMethod("injectService", null);
//		method.invoke(client, null);

		System.out.println("--------linked binding-------");
		Injector injector = Guice.createInjector();
		DemoService demoService = injector.getInstance(DemoService.class);
		System.out.println("demoService hashcode:"+demoService.hashCode());
		demoService = injector.getInstance(DemoService.class);
		System.out.println("demoService hashcode:"+demoService.hashCode());
		demoService.go();
		
		System.out.println("--------get config info from config class-------");
		client.demo2();
		
		System.out.println("--------provider-------");
		client.providerDemo();
	}
}

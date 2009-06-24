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
		
		System.out.println("-----------普通方式----------");
		MyModule myModule = new MyModule();
		Injector in = Guice.createInjector(myModule);
		Client client= new Client();
		in.injectMembers(client);
		client.go();
		
//		Method method = client.getClass().getMethod("injectService", null);
//		method.invoke(client, null);

		System.out.println("--------Annotation方式-------");
		
		Injector injector = Guice.createInjector();
		DemoService demoService = injector.getInstance(DemoService.class);
		demoService.go();
		
		System.out.println("--------从绑定模块获取配置信息-------");
		client.demo2();
		
		System.out.println("--------provider-------");
		client.providerDemo();
	}
}

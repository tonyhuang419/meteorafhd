package guiceDemo.helloworld;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class Client {



	/**
	 * 构造输入
	 * 	private DemoService demoService;
	 * 	 @Inject
	 *	public Client(DemoService demoService){
	 *	 this.demoService = demoService;
	 *	}
	 */

	/**
	 * 字段注入
	 * @Inject private DemoService demoService;
	 */

	/**
	 * 方法注入
	 */
	private DemoService demoService;

	@Inject
	public void injectService(DemoService demoService) {
		this.demoService = demoService;
	}


	public void go() {
		demoService.go();
	}


	/**
	 * http://www.blogjava.net/aoxj/archive/2008/08/22/223811.html
	 */
	@Inject
	@Named("username")
	private String username;

	@Inject
	@Named("password")
	private String password;

	@Inject
	@Named("url")
	private String url;



	public void demo2(){
		System.out.println(username);
		System.out.println(password);
		System.out.println(url);
	}


	//provider
	@Inject
	public  Provider<String> stringProvider;
	
	public void providerDemo(){
		System.out.println(stringProvider.get());
	}
	
}
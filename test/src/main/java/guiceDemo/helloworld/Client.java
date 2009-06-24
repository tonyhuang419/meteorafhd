package guiceDemo.helloworld;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.internal.Nullable;
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
		System.out.println("demoService hashcode:"+demoService.hashCode());
		demoService.go();
	}


	/**
	 * http://www.blogjava.net/aoxj/archive/2008/08/22/223811.html
	 */
	@Inject(optional=true)
	@Named("username")
	@Nullable
	private String username;

	@Inject
	@Named("password")
	private String password;

	@Inject
	@Named("url")
	private String url;



	public void demo2(){
		//如果字段有null会报异常
//		System.out.println(Preconditions.checkNotNull(username,"username"));
//		System.out.println(Preconditions.checkNotNull(password,"password"));
		System.out.println(username);
		System.out.println(password);
		System.out.println(url);
	}


	//provider
	public  Provider<String> stringProvider;
	
	@Inject
	public void getProviderString(Provider<String> stringProvider){
		this.stringProvider = stringProvider;
	}

	public void providerDemo(){
		System.out.println(stringProvider.get());
	}
	
}
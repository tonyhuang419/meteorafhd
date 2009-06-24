package guiceDemo.helloworld;

import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class DemoServiceImpl implements DemoService {

	@Named("go")
	public void go() {
		System.out.println("go...");
	}

}

package guiceDemo.helloworld;

import com.google.inject.Singleton;

@Singleton
public class DemoServiceImpl implements DemoService {

	public void go() {
		System.out.println("go...");
	}

}

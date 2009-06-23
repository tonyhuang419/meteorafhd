package guiceDemo.helloworld;

import com.google.inject.Inject;

public class Client {

	private DemoService demoService;

	@Inject
	public void injectService(DemoService demoService) {
		this.demoService = demoService;
	}

	public void go() {
		demoService.go();
	}
}
package guiceDemo.helloworld;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {
	public void configure(Binder binder) {
		binder.bind(DemoService.class).to(DemoServiceImpl.class).in(Scopes.SINGLETON);
	}
}

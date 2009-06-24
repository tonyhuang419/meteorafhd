package guiceDemo.helloworld;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class MyModule implements Module {
	public void configure(Binder binder) {
		binder.bind(DemoService.class).
		annotatedWith(Blue.class).
		to(DemoServiceImpl.class).
		in(Scopes.SINGLETON);
		
		binder.bindConstant().annotatedWith(Names.named("username")).to("root");
		binder.bindConstant().annotatedWith(Names.named("password")).to("root");
	}
}

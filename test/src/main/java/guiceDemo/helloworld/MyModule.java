package guiceDemo.helloworld;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

public class MyModule implements Module {

	public void configure(Binder binder) {
		binder.bind(DemoService.class).
		annotatedWith(Blue.class).
		to(DemoServiceImpl.class).
		in(Scopes.SINGLETON);

		binder.bindConstant().annotatedWith(Names.named("username")).to("root");
		binder.bindConstant().annotatedWith(Names.named("password")).to("root");

		binder.bind(String.class).
		annotatedWith(Names.named("url")).toInstance("www.fhdone.com");
		
//		binder.bind(String.class).toProvider(ProviderServiceImpl.class);

		binder.bindInterceptor(Matchers.any(),
				Matchers.annotatedWith(Names.named("go")),
				new InterceptorService());
		
	}

	@Provides
	public String provideString() {
		return "provide string";
	}
	
}

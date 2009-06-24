package guiceDemo.helloworld;

import com.google.inject.Provider;

public class ServiceProviderImpl implements Provider<String> , ServiceProvider{

	@Override
	public String get() {
		return "ServiceProvider class provider string";
	}

}

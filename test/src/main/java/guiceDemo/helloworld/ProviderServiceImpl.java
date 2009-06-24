package guiceDemo.helloworld;

import com.google.inject.Provider;

public class ProviderServiceImpl implements Provider<String> , ProviderService{

	@Override
	public String get() {
		return "ServiceProvider class provider string";
	}

}

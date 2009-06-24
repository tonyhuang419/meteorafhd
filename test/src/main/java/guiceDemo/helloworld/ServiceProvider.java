package guiceDemo.helloworld;

import com.google.inject.Provider;

public class ServiceProvider implements Provider<String>{

	@Override
	public String get() {
		return "ServiceProvider class provider string";
	}

}

package guiceDemo.helloworld;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * http://www.99inf.net/SoftwareDev/Java/50755.htm
 */

public class Test {

	public static void main(String[] args) {
		MyModule myModule = new MyModule();
		Injector in = Guice.createInjector(myModule);
		Client client= new Client();
		in.injectMembers(client);
		client.go();
	}
}

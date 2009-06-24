package guiceDemo.helloworld;

import com.google.inject.ProvidedBy;


@ProvidedBy(ServiceProviderImpl.class)
interface ServiceProvider {

	
}

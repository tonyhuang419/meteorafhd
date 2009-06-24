package guiceDemo.helloworld;

import com.google.inject.ProvidedBy;


@ProvidedBy(ProviderServiceImpl.class)
interface ProviderService {

	
}

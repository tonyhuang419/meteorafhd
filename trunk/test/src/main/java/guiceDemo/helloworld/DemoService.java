package guiceDemo.helloworld;

import com.google.inject.ImplementedBy;


@ImplementedBy(DemoServiceImpl.class)
public interface DemoService {
	void go();
}
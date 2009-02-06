package xfire;

import java.util.ArrayList;
import java.util.List;

public class HelloServiceImpl implements IHelloService {

	public String sayHello(String ttt) {
		return "Hello, "+ttt;
	}

}

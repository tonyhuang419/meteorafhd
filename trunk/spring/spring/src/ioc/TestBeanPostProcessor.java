package ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TestBeanPostProcessor implements BeanPostProcessor {

	
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		System.out.println("after get bean "+name);
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		System.out.println("before get bean "+name);
		return bean;
	}
	
}

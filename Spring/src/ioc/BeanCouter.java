package ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BeanCouter implements BeanFactoryPostProcessor {


	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory)
			throws BeansException {
		System.out.println(factory.getBeanDefinitionCount());
	}
}

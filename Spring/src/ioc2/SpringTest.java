package ioc2;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;



public class SpringTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SpringTest.class);

	public static void main(String[] args)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start");
		}

		//读取配置文件
		ApplicationContext context = new FileSystemXmlApplicationContext("set-config.xml");
		//获取id="setBean"对象
		SomeBean someBean=(SomeBean)context.getBean("someBean");

		//获取someStrArray,someObjArray
		String[] strs=someBean.getSomeStrArray();
		ObjectSet[] some=someBean.getSomeObjArray();

		for(int i=0;i<strs.length;i++)
			System.out.println(strs[i]+","+some[i]);

		//获取someList
		List someList=(List)someBean.getSomeList();
		for(int i=0;i<someList.size();i++)
			System.out.println(someList.get(i));

		//获取someMap
		Map someMap=someBean.getSomeMap();
		System.out.println(someMap);

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end");
		}
	}
}

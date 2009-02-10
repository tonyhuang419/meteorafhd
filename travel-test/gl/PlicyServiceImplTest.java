package test.gl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baosight.baosteel.tourism.gl.airorder.plicy.service.PlicyService;

public class PlicyServiceImplTest {

	public void doGetCitynameByAircodeTest(){
		System.out.println(this.getClass().getResource("/").toString());
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/TestUseApplicationContext.xml");
//		String[] s= context.getBeanDefinitionNames();
//		for(int i=0;i<s.length;i++){
//		System.out.println(s[i]);
//		}
		PlicyService ps = (PlicyService)context.getBean("plicyService");
		System.out.println(ps.doGetCitynameByAircode("CHG"));
	}

	public static void main(String[] args){
		new PlicyServiceImplTest().doGetCitynameByAircodeTest();
	}

}

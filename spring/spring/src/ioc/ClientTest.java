package ioc;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ClientTest {
	public final static String CHINESE = "Chinese";
	public final static String AMERICAN = "American";

	public static void main(String[] args) {
//		Human human = null;
//		human = new Factory().getHuman(Factory.CHINESE);
//		human.eat();
//		human.walk();
//		human = new Factory().getHuman(Factory.AMERICAN);
//		human.eat();
//		human.walk();

//		Resource resource = new ClassPathResource("bean.xml");
//		BeanFactory factory = new XmlBeanFactory(resource);
//		Human h = (Human)factory.getBean("Chinese");
//		h.eat();
//		Human h2 = (Human)factory.getBean("American");
//		h2.eat();
		
		//系统目录下的xml
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		Human human = null;
		human = (Human) ctx.getBean(CHINESE);
		human.eat();
		human.walk();
		human = (Human) ctx.getBean(AMERICAN);
		human.eat();
		human.walk();
	}
}
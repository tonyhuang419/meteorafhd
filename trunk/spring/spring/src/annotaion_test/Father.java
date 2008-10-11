package annotaion_test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("father")
//@Service("father")
public class Father {
	@Autowired
	@Qualifier("son1")
	private Son1 s1;

	@Autowired
	@Qualifier("son2")
	private Son2 s2;

	@PostConstruct
	public void postConstruct1(){
		System.out.println("postConstruct1");
	}

	@PreDestroy
	public void preDestroy1(){
		System.out.println("preDestroy1"); 
	}

	public Son1 getS1() {
		return s1;
	}
	public void setS1(Son1 s1) {
		this.s1 = s1;
	}
	public Son2 getS2() {
		return s2;
	}
	public void setS2(Son2 s2) {
		this.s2 = s2;
	}

}

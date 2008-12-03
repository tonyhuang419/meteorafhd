package assemble;

import java.util.HashSet;
import java.util.Set;

public class TestSet {
	public void t1(){
		Set<String> s = new HashSet<String>();
		s.add("1");
		s.add("1");
		s.add("2");
		System.out.println(s.size());
	}
	
	public void test(){
		this.t1();
	}
	
	public static void main(String[] args) {
		new TestSet().test();
	}
}

package iterator_test;

import java.util.Iterator;

public class Test {

	public static void main(String[] args) {
		Arr a = new Arr();
		for(Iterator i = a.iterator(); i.hasNext(); ){
			People p = (People)i.next();
			System.out.print(p.getName()+"   ");
			System.out.println(p.getAge());
		}
	}
}

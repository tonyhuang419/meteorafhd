package def;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger {

	private int i;
	
	public void testX(){
		//i will be set a default value : 0 
		System.out.println(this.getI()+1);
	}
	
	public static void main(String[] args) {
		AtomicInteger ai = new AtomicInteger();
		System.out.println(ai);
		System.out.println(ai.getAndIncrement());
		System.out.println(ai.getAndIncrement());
		
		new TestAtomicInteger().testX();
		
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}

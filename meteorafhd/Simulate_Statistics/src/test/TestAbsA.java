package test;

public class TestAbsA {
	public static void main(String args[]){
		AbstractX b = new AbsB();
		AbstractX a = new AbsA();
		
		a.printa();
		b.printa();
	}

}

package test;

public class TestAssert {
	public static void main(String args[]){
		int i = 3;
		assert i == 2 : "i was 2 for some reason";
		System.out.println(i);
	}
}

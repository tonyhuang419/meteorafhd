package def;

import java.util.Random;

public class TestRandom {

	public static void main(String[] args) {
		for(int i=0; i<10;i++){
			System.out.println((long)(Math.random()*10000000) );
		}
		System.out.println("==================");
		Random r = new Random();
		for(int i=0; i<10;i++){
			System.out.println(r.nextLong()*100);
		}
	}
}

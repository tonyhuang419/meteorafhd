package def;

import java.util.Random;

public class TestRandom {
	
	static Random rnd = new Random();
	public int random(int n){
		int i = Math.abs(rnd.nextInt())%n; 
		System.out.println(i);
		return i;
	}
	

	public static void main(String[] args) {
//		for(int i=0; i<10;i++){
//			System.out.println((long)(Math.random()*10000000) );
//		}
//		System.out.println("==================");
		Random r = new Random();
		int count = 0;
		int n = 2*(Integer.MAX_VALUE/3);
		for(int i=0; i<10000000;i++){
//			System.out.println(r.nextLong()*1000000);
			if(r.nextLong()*n < n/2 ){
				count++;;
			}
		}
		System.out.println(count);
		
//		TestRandom tr = new TestRandom();
//		/**
//		 * effective page 145 translate version
//		 */
////		int n = 2*(Integer.MAX_VALUE/3);
//		int n = 100;
//		int low = 0;
//		for(int i=0;i<100;i++){
//			if(tr.random(n)<n/2){
//				low++;
//			}
//		}
//		System.out.println(low);
		
		
	}
}

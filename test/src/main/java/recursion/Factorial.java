package recursion;

public class Factorial {

	static public int calc(int i){
		if(i<0){
			System.out.println("input error");
			return 0;
		}
		else if(i==0){
			return 1;
		}
		return i * calc(i-1);
	}
	
	
	public static void main(String[] args) {
		System.out.println(Factorial.calc(-5));
		System.out.println(Factorial.calc(2));
		System.out.println(Factorial.calc(5));
		System.out.println(Factorial.calc(10));
		System.out.println(Factorial.calc(50)); //overflow
	}
}

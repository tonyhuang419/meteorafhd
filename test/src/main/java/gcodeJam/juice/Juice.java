package gcodeJam.juice;

import java.util.Comparator;

public class Juice {

	final public static int APPLE_Code = 1;
	final public static int BANANA_CODE = 2;
	final public static int CARROT_CODE = 3;
	
	int apple;
	int banana;
	int carrot;

	public Juice(int apple,int banana,int carrot){
		this.apple = apple;
		this.banana = banana;
		this.carrot = carrot;
	}

}


class CompareApple implements Comparator<Juice>{
	
	public int compare(Juice juice1 , Juice juice2) {
		if(juice1.apple >  juice2.apple){
			return 1;
		}
		else if( juice1.apple ==  juice2.apple ){
			return 0;
		}
		return -1;
	}
}

class CompareBanana implements Comparator<Juice>{
	
	public int compare(Juice juice1 , Juice juice2) {
		if(juice1.banana >  juice2.banana){
			return 1;
		}
		else if( juice1.banana ==  juice2.banana ){
			return 0;
		}
		return -1;
	}
}

class CompareCarrot implements Comparator<Juice>{
	
	public int compare(Juice juice1 , Juice juice2) {
		if(juice1.carrot >  juice2.carrot){
			return 1;
		}
		else if( juice1.carrot ==  juice2.carrot ){
			return 0;
		}
		return -1;
	}
}

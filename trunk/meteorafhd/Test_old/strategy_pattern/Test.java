package strategy_pattern;

public class Test {
	public static void main(String[] args){
		Duck duck = new Duck();
		duck.fly(new Fly_on());
		duck.fly(new Fly_off());
	}
}

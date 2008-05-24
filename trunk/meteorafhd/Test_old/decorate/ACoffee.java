package decorate;

public class ACoffee extends Coffee {
	public ACoffee(){
		description = "ACoffee";
	}
	@Override
	public double cost() {
		return 2.5;
	}
}

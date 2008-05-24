package decorate;

public class BCoffee extends Coffee {
	public BCoffee(){
		description = "BCoffee";
	}
	@Override
	public double cost() {
		return 3.11;
	}
}


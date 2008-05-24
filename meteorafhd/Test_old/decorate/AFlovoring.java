package decorate;

public class AFlovoring extends Flovoring {
	Coffee coffee;
	AFlovoring(Coffee coffee){
		this.coffee = coffee;
	}
	@Override
	public String getDescription() {
		return coffee.getDescription()+"  AFlovoring";
	}

	@Override
	public double cost() {
		return .2 +  coffee.cost();
	}
}

package decorate;

public class BFlovoring extends Flovoring {
	Coffee coffee;
	BFlovoring(Coffee coffee){
		this.coffee = coffee;
	}
	@Override
	public String getDescription() {
		return coffee.getDescription()+"  BFlovoring";
	}

	@Override
	public double cost() {
		return .3 +  coffee.cost();
	}
}


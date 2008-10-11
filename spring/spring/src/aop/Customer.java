package aop;

public class Customer {
	private String name;

	Customer(String _name){
		this.name = _name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

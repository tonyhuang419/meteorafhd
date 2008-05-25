package polymorphism;

class Animal{
	void sound(){};
}

class Tiger extends Animal{
	Tiger(){
		sound();
	}
	public void sound(){
		System.out.println("tiger");
	}
}

class Lion extends Animal{
	Lion(){
		sound();
	}
	public void sound(){
		System.out.println("lion");
	}
}


public class PolymorphismDemo {
	public static void main(String[] args) {
		Animal t = new Tiger();
		Animal l = new Lion();
	}
}

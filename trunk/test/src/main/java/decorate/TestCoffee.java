package decorate;

public class TestCoffee {

    public static void main(String[] args) {
            Coffee coffee1 = new ACoffee();
            coffee1 = new AFlovoring(coffee1);
            System.out.println(coffee1.getDescription() +"   "+ coffee1.cost());
    }
}

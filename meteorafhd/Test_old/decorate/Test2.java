package decorate;

abstract class Shop{
	String description = "Unknow";
	public String getDescription(){
		return description;
	}
	public abstract double cost();
}

abstract class BookDecorator extends  Shop{
	public abstract String getDescription();
}

class HarryPotterTOOTP extends Shop{

	public double cost() {
		return 10;
	}

	public String getDescription() {
		return "HarryPotterTOOTP";
	}
}

class Love extends Shop{

	public double cost() {
		return 20;
	}

	public String getdescription() {
		return "Love";
	}
}
class UserShopCart extends BookDecorator{
	Shop shop;
	public UserShopCart(Shop _shop){
		this.shop = _shop;
	}
	public String getDescription() {
		return shop.getDescription()+",cart";
	}
	public double cost() {
		return shop.cost();
	}
	
}
public class Test2 {

	public static void main(String[] args) {
		Shop  s = new UserShopCart(new HarryPotterTOOTP());
		s = new UserShopCart(s);

		System.out.println(s.getDescription()+"  "+s.cost());
	}

}

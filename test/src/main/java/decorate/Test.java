//package decorate;
//
////abstract class Shop{
////	String description = "no";
////	public String getDescription(){
////		return description;
////	}
////	public abstract double cost();
////}
////
////class HarryPotterTOOTP extends Shop{
////	HarryPotterTOOTP(){
////		description = "HarryPotterTOOTP";
////	}
////	public double cost() {
////		return 10.11;
////	}
////}
////
////class Love extends Shop{
////	Love(){
////		description = "Love";
////	}
////	public double cost() {
////		return 20.11;
////	}
////}
////
////class Pencil extends Shop{
////	Shop shop;
////	Pencil(Shop _shop){
////		this.shop = _shop;
////	}
////	public String getDescription(){
////		return shop.getDescription()+", Pencil";
////	}
////	public double cost() {
////		return 1.1+shop.cost();
////	}
////}
//      
//class A{
//	A tempa;
//	A(){}
//	A(A _a){
//		tempa = _a;
//	}
//	String x(){
//		return "xxx"+ tempa.x();
//	}
//}
//
//
//
//public class Test {
//	public static void main(String[] args) {
////		Shop b1 = new HarryPotterTOOTP();
////		System.out.println(b1.getDescription()+" "+b1.cost());
////		b1 = new Pencil(b1);
////		System.out.println(b1.getDescription()+" "+b1.cost());
////		b1 = new Pencil(b1);
////		System.out.println(b1.getDescription()+" "+b1.cost());
////		b1 = new Pencil(b1);
////		System.out.println(b1.getDescription()+" "+b1.cost());
////			
////		b1 = new HarryPotterTOOTP();
////		System.out.println(b1.getDescription()+" "+b1.cost());
//
//		
//}

package groovy



public class Calculate{

	def add(a,b){
		a+b;
	}
	
	public static void main(def args){
		def sum = new Calculate().add(3,5);
		println sum;
	}
	
}

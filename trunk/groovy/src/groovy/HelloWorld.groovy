package groovy;

public class HelloWorld{
	
	public static void main(def args){
		new HelloWorld().testX();
	}
	
	
	def testX(){
//		testHelloWorld("hello world X");
//		testType();
		testRepeat();
	}
	
	

	def testRepeat(){
		for(def i = 0; i < 5; i++){
			/*
			 * output:  0 1 2 3 4
			 */
			print i+" ";    //comment:semicolon can be omitted
		}
		
		println "";
		 for(i in 0..4){
			  print i+" ";
			 }

		println "";
			for(i in 0..<5){
				print i+" ";
			}	 

	}
	
	def testType(){
		def str = "s";
		println str.class;   	//output:  class java.lang.String
		str = 1;
		println str.class;   	//output: class java.lang.Integer
		def add = 0.00000000000001;
		println str.class;    	 //output: class java.lang.Integer
		def added = 99.99999999999999991;
		println str.class;		 //output: class java.lang.Integer
		def sum = add+added
		println sum+ "  "+ sum.class;
	}
	
	
	def  testHelloWorld(str){
		println str;
		println str.class
	}
	


}


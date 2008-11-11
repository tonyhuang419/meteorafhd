package groovy;

class Song {
	 def name
	 String  artist
	 def genre
	 
	 def getGenre(){
		 genre?.toUpperCase();
	}

	 
	 String toString(){
		 "${name}, ${artist}, ${genre}"
	}

}

public class HelloWorld{
	
	public static void main(def args){
		new HelloWorld().testX();
	}
		
	def testX(){
//		testHelloWorld("hello world X");
//		testType();
//		testRepeat();
//		testCollection();
//		testMap();
//		testClosure();
		this.testClass();
	}
	
	
	def testClass(){
		 def sng = new Song(name:"Le Freak", artist:"Chic", genre:"Disco");
		 println(sng.name);
		 
		 def sng2 = new Song(name:"Kung Fu Fighting", genre:"Disco");
		 println(sng2.name);
		 println(sng2.toString());
		 
		 def sng3 = new Song()
		 sng3.name = "Funkytown"
		 sng3.artist = "Lipps Inc."
		 sng3.setGenre("Disco")
		 println(sng3.name);
		 println(sng3.toString());
		 println(sng3.hashCode());
		 println(sng3.getGenre());  //notice: the function in Class is " genre?.toUpperCase();"

	}

	
	def testClosure(){
		def acoll = ["Groovy", "Java", "Ruby"];
		acoll.each{
			println it;     //comment: "it" is a default value;
		}
		
		acoll.each{ value ->   // value replace "it"
			println value;
		}

		def hash = [name:"Andy", "VPN-#":45];
		hash.each{ key, value ->
		 	println "${key} : ${value}"
		}

		def coll = ["Groovy", "Java", "Ruby"];
		coll.add("FHDone");
		coll.each{
			println it.toLowerCase();
		}

	}
	
	def testMap(){
		def hash = [name:"Andy", "VPN-#":45];
		println hash.getClass();    //output:  class java.util.LinkedHashMap
		assert hash.getClass() == java.util.LinkedHashMap;
		hash.put("id", 23);
		assert hash.get("name") == "Andy";
		println(hash.get("id"));	 //output:  23
		println(hash.id);			//output:  23
		println(hash.name);			//output:  Andy
		
		/**********************************************/
		def str = "s";
		println str.class;   		//output:  class java.lang.String
		println str.getClass();   	//output:  class java.lang.String
		str = 1.111111;
		println str.class;   		//output:  class java.math.BigDecimal
		println str.getClass();   	//output:  class java.math.BigDecimal

	}
	
	def testCollection(){
		def coll = ["Groovy", "Java", "Ruby"];
		assert  coll instanceof Collection;
		assert  coll instanceof ArrayList;
		
		coll.add("Python");
		coll << "Smalltalk";
		coll[5] = "Perl";

		println(coll[1]);
	}
	
	
	def testRepeat(){
		/*
		 * output:  0 1 2 3 4
		 */
		for(def i = 0; i < 5; i++){
			print i+" ";    //comment:semicolon can be omitted
		}
	
		/*
		 * output:  0 1 2 3 4
		 */
		println "";
		 for(i in 0..4){
			  print i+" ";
			 }

		 /*
		  * output:  0 1 2 3 4
		  */
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





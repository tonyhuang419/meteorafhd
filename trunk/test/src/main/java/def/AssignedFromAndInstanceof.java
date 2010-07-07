package def;

public class AssignedFromAndInstanceof {

	public  void  testIsAssignedFrom1()      {      
		System.out.println(  String.class.isAssignableFrom(Object.class) ) ;      
	}      

	public  void  testIsAssignedFrom2()      {      
		System.out.println(  Object.class.isAssignableFrom(Object.class) );      
	}      

	public  void  testIsAssignedFrom3()      {      
		System.out.println( Object.class.isAssignableFrom(String.class) );      
	}      

	public  void  testInstanceOf1()      {      
		System.out.println(  "ss"  instanceof Object );      
	}      

	public  void  testInstanceOf2()      {      
		Object  o = new   Object();      
		System.out.println(  o  instanceof  Object );    
	} 
	
	public  void  testInstanceOf3()      {      
		Object  o = new   Object();      
		System.out.println(  o  instanceof  String );    
	} 
	

	public static void main(String[] args){   
		AssignedFromAndInstanceof test = new AssignedFromAndInstanceof();   
		
		/**
		 * 右边对象 包含 左边对象
		 */
		test.testIsAssignedFrom1();   
		test.testIsAssignedFrom2();   
		test.testIsAssignedFrom3();   
		
		/**
		 * 左边对象 包含 右边对象
		 */
		test.testInstanceOf1();   
		test.testInstanceOf2();   
		test.testInstanceOf3();   
	}    
}

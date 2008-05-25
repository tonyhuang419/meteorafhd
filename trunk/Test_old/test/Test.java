package test;

class Dog{
	String name;
	int id;
	public Dog(String _name,int _id){
		this.name = _name;
		this.id = _id;
	}
	
}

class Tool{
	static public void modify(Dog _dog){
		_dog.name = "modified";
	}
}
public class Test {

	public static void main(String argv[]) {
//		Dog d = new Dog("dog",1);
//		System.out.println(d.name);
//		System.out.println(d.id);
//		Tool.modify(d);
//		System.out.println(d.name);
//		System.out.println(d.id);



//		System.out.println("out no change "+d.i);
//		d.cInt();
//		System.out.println("out  changed "+d.i);



//		Float f=new Float(4.2f); 
//		Float c; 
//		Double d=new Double(4.2); 
//		float fl=4.2f; 
//		c=f; 
//		System.out.println(c==f );

//		String s = null;
//		System.out.println("s = "+s); 

		Integer ten=new Integer(10);
		Long nine=new Long (9);
		System.out.println(ten + nine);
		int i=1;
		System.out.println(i + ten); 

	} 
}
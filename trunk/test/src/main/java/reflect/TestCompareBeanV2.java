package reflect;

import java.util.List;

public class TestCompareBeanV2 {

	public void testCompare(){
		Test t1 = new Test(1L,"fhdone1",1);
		Test t2 = new Test(2L,"fhdone2",2);
		CompareBeanV2 cv2 = new CompareBeanV2();
		
		
		List<CompareContext> ccList =  cv2.compare(t1,t2,  new String[]{"id"});
		for( CompareContext c : ccList  ){
			System.out.print(c.getFieldName()+":");
			System.out.print(c.getSrcContext()+"/");
			System.out.println(c.getTargetContext());
		}
		
//		new SeeBeanFields().seeBeanFields("2");
	}
	
	public static void main(String[] args ){
		new TestCompareBeanV2().testCompare();
	}
	
	class Test{
		Long id;
		String name;
		int age;
		
		public Test(Long id,String name,int age){
			this.id = id;
			this.name = name;
			this.age = age;
		}
	}
}

package reflect;

public class TestCompare {
	public static void main(String[] args ){
		
		CompareBean cb = new CompareBean();
		
//		@Test1
		Stu s1 = new Stu();
		s1.setName("jack");
		s1.setAge(19);
		
		Stu s2 = new Stu();
		s2.setName("tom");
		s2.setAge(19);
		cb.trav(s1,s2);
		System.out.println("********************");

//		@Test2
		Teacher t1 = new Teacher();
		t1.setName("mary");
		t1.sex = false;
		t1.setAge(25);
		t1.setMarks("i'm a teacher");
		
		Teacher t2 = new Teacher();
		t2.setName("mary");
		t2.sex = false;
		t2.setAge(26);
		t2.setMarks("i'm a teacher");
		cb.trav(t1,t2);
		System.out.println("********************");
		cb.trav(s1, t1);
		System.out.println("********************");
	}
}

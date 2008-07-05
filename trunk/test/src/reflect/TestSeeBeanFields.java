package reflect;

import org.apache.commons.lang.StringUtils;

public class TestSeeBeanFields {

	public static void main(String[] args ){
		SeeBeanFields see = new SeeBeanFields();

		//test 1
		Stu s1 = new Stu();
		s1.setName("jack");
		s1.setAge(19);
		
		see.seeBeanFields(s1);

		System.out.println(StringUtils.center(" another test ", 40, "="));
		
//		test2
		Teacher t1 = new Teacher();
		t1.setName("jerry");
		t1.setAge(25);
		t1.setSex(true);
		t1.setMarks("nothing");
		
		see.seeBeanFields(t1);


	}
}

package reflect;

import java.lang.reflect.Field;

public class TraversalBean {


	public void trav(Object obj){
		try {
			Class c = obj.getClass();
			Field fields[] = c.getDeclaredFields(); 
			System.out.println(fields.length);
		
			fields[0].setAccessible(true);
			System.out.println(fields[0].get(obj));
			fields[1].setAccessible(true);
			System.out.println(fields[1].getLong(obj));
		
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args ){
		Stu s1 = new Stu();
		s1.setName("jack");
		s1.setAge(19);

		new TraversalBean().trav(s1);
	}

}

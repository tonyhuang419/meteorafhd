package reflect;

import java.lang.reflect.Field;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class SeeBeanFieldsX {

	public void travBean(Object obj){
		Class c = obj.getClass();
		Field srcFields[] = c.getDeclaredFields(); 		
		BeanWrapper objBW = new BeanWrapperImpl(obj);
			
		int beanLength = srcFields.length;
		for(int i = 0;i < beanLength; i++){
			System.out.print(srcFields[i].getName() + " :   ");
			System.out.println(objBW.getPropertyValue(srcFields[i].getName()));
		}
	}
	public static void main(String[] args) {
		Stu s1 = new Stu();
		s1.setName("jack");
		s1.setAge(19);
		new SeeBeanFieldsX().travBean(s1);
	}
}

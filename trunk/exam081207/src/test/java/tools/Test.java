package tools;

import java.lang.reflect.Field;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.exam.entity.Employee;

public class Test {

	public void test(){
		Employee e = new Employee();
		e.setId(111L);
	    Class c = e.getClass();
	    Field objFields[] = c.getDeclaredFields();      
        int length = objFields.length;

	    
		BeanWrapper targetBean = new BeanWrapperImpl(e);
//		targetBean.setWrappedInstance(e);
		for(int i=0;i<length;i++){
			System.out.println(targetBean.getPropertyValue(objFields[i].getName()));
		}

	}
	public static void main(String[] args) {
		new Test().test();

	}

}

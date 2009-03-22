package com.baoz.yx.tools;

import java.lang.reflect.Field;

public class SeeBeanFields {

	static synchronized public void travBean(Object obj){
		Class c = obj.getClass();

		Field objFields[] = c.getDeclaredFields();      
		int length = objFields.length;

		Field.setAccessible(objFields, true); 
		for(int i = 0;i < length; i++){
			try{
				System.out.print(objFields[i].getName()+" : ");
				System.out.println(objFields[i].get(obj));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

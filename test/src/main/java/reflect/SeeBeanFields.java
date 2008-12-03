package reflect;

import java.lang.reflect.Field;

public class SeeBeanFields {
	public void  seeBeanFields(Object obj){

		Class c = obj.getClass();
		
		Field objFields[] = c.getDeclaredFields(); 	
//		System.out.println(objFields.length);
		
//		Field objFields2[] = c.getFields();
//		System.out.println(objFields2.length);
		
		
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
		
//		BeanWrapper srcBW = new BeanWrapperImpl(obj);
//		for(int i = 0;i < length; i++){
//			Object srcPropertyValue = srcBW.getPropertyValue(objFields[i].getName());
//			System.out.println(objFields[i].getName()+ ":" + srcPropertyValue.toString());
//		}
	}
}

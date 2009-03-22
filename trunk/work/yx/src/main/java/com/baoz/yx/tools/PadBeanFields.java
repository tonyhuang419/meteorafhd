package com.baoz.yx.tools;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PadBeanFields {

	static synchronized public Object  padBean(Object objPaded ,  Object objTarget ,String excludeField[]){

		BeanWrapper padedBean = new BeanWrapperImpl(false);
		BeanWrapper tarBean = new BeanWrapperImpl(false);
		padedBean.setWrappedInstance(objPaded);
		tarBean.setWrappedInstance(objTarget);

		Class paded = objPaded.getClass();
		Class tar = objTarget.getClass();

		if( paded.equals(tar)){    //class类型匹配
			Field tarFields[] = tar.getDeclaredFields();      
			Field.setAccessible(tarFields, true); 

			int length = tarFields.length;
			int mark=0;
			for(int i = 0;i < length; i++){
				try{
					if(excludeField!=null){
						for(String fieldName : excludeField){
							if( tarFields[i].getName().equals(fieldName) ){
								mark=1;
								break;
							}
						}
						if(mark==1){
							mark=0;
							continue;
						}
					}
					if( tarFields[i].get(objTarget)!=null  ){
						padedBean.setPropertyValue( PadBeanFields.makeFristLetterLower( tarFields[i].getName())  ,tarFields[i].get(objTarget))		;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return objPaded;
	}
	
	synchronized static private String   makeFristLetterLower(String str){
		if(str!=null){
			String fristLetter = StringUtils.lowerCase(str.substring(0,1));
			str = fristLetter + str.substring(1,str.length());
		}
		return str;
	}
	
//	public static void main(String[] args){
//		System.out.println( PadBeanFields.makeFristLetterLower("AA"));
//	}
}

package com.exam.tools;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PadBeanFields {

	/**
	 * objSrc属性覆盖objPaded属性，如果属性为null，不覆盖 
	 */
	static public Object padBean( Object objSrc , Object objPaded  ,String excludeField[]){

		BeanWrapper padedBean = new BeanWrapperImpl(false);
		BeanWrapper tarBean = new BeanWrapperImpl(false);
		padedBean.setWrappedInstance(objPaded);
		tarBean.setWrappedInstance(objSrc);

		Class paded = objPaded.getClass();
		Class tar = objSrc.getClass();

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
					if( tarFields[i].get(objSrc)!=null  ){
						//避免属性第一个字母大写 调用方法makeFristLetterLower
						padedBean.setPropertyValue( PadBeanFields.makeFristLetterLower( tarFields[i].getName())  ,tarFields[i].get(objSrc))		;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return objPaded;
	}
	
	static private String makeFristLetterLower(String str){
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

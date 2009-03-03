package com.exam.tools;

import java.util.Date;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.exam.entity.Book;

public class PadProperty {

	static synchronized public Object  padBean(Object objPaded ,  String propertyName , Object propertyValue ){
		BeanWrapper padedBean = new BeanWrapperImpl(false);
		padedBean.setWrappedInstance(objPaded);
//		Class paded = objPaded.getClass();
		padedBean.setPropertyValue(propertyName  , propertyValue);
		return objPaded;
	}
	
	
	
	public static void main(String[] args){
		Book b = new Book();
		System.out.println(b.getTitle());
		b = (Book)PadProperty.padBean(b, "title", "xxx" );
		b = (Book)PadProperty.padBean(b, "createdby", new Date() );
		System.out.println(b.getTitle());
		System.out.println(b.getCreatedby());
	}
}

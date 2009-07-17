package com.jbpm_ssh.tools;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PadProperty {

	static synchronized public Object  padBean(Object objPaded ,  String propertyName , Object propertyValue ){
		BeanWrapper padedBean = new BeanWrapperImpl(false);
		padedBean.setWrappedInstance(objPaded);
//		Class paded = objPaded.getClass();
		padedBean.setPropertyValue(propertyName  , propertyValue);
		return objPaded;
	}
	
}

package com.baoz.yx.tools.compare;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import com.baoz.yx.tools.compare.beans.ClassInfo;
import com.baoz.yx.tools.compare.beans.PropertyInfo;

/**
 * 类CompareFactory.java的实现描述：注册beanInfo
 * @author xurong Jun 13, 2008 6:15:24 PM
 */
public class ClassInfoRegister {
	public final static Map<Class, ClassInfo> classInfoCache = new HashMap<Class, ClassInfo>();
	/**
	 * 注册一个CLassInfo
	 * @param classInfo
	 */
	public static void registerClassInfo(ClassInfo classInfo){
		if(!classInfoCache.containsKey(classInfo.getClassType())){
			classInfoCache.put(classInfo.getClassType(), classInfo);
		}
	}
	/**
	 * 获得targetClass对应的classInfo
	 * @param targetClass
	 * @return
	 */
	public static ClassInfo getClassInfo(Class targetClass){
		return classInfoCache.get(targetClass);
	}
	/**
	 * 根据calss自动生成并注册ClassInfo
	 * @param targetClass
	 * @return
	 */
	public static ClassInfo autoCreateAndRegister(Class targetClass){
		if(classInfoCache.containsKey(targetClass)){
			return classInfoCache.get(targetClass);
		}
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(targetClass);
		} catch (IntrospectionException e) {
			new RuntimeException(e.getMessage(),e);
		}
		/////////////////
		ClassInfo classInfo = new ClassInfo(targetClass);
		/////////////////
		classInfo.setClassType(targetClass);
		classInfo.setShortDesc(targetClass.getSimpleName());
		classInfo.setDescription(targetClass.getName());
		/////////////////
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : props) {
			// 跳过系统属性
			if(propertyDescriptor.getName().equals("class")){
				continue;
			}
			PropertyInfo propInfo = new PropertyInfo(propertyDescriptor.getPropertyType(),propertyDescriptor.getName());
			propInfo.setClassInfo(classInfo);
			propInfo.setShortDesc(propertyDescriptor.getShortDescription());
			propInfo.setDescription(propertyDescriptor.getShortDescription());
			classInfo.addPropertyInfo(propInfo);
		}
		registerClassInfo(classInfo);
		return classInfo;
	}
}

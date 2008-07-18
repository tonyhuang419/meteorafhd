package com.baoz.yx.tools.compare.beans;

import com.baoz.yx.tools.compare.ClassInfoRegister;

/**
 * 类CompareObject.java的实现描述：用于比较的对象
 * @author xurong Jun 13, 2008 5:27:38 PM
 */
public class CompareObject {
	/**
	 * 待比较的对象的类信息
	 */
	private ClassInfo classInfo;
	/**
	 * 待比较的对象
	 */
	private Object targetObject;

	public CompareObject() {
	}
	
	public CompareObject(ClassInfo classInfo, Object targetObject) {
		this.classInfo = classInfo;
		this.targetObject = targetObject;
	}
	
	public CompareObject(Class targetType, Object targetObject) {
		this.classInfo = ClassInfoRegister.autoCreateAndRegister(targetType);
		this.targetObject = targetObject;
	}
	
	public ClassInfo getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	public Object getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}
	
}

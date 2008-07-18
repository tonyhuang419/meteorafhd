package com.baoz.yx.tools.compare.beans;
/**
 * 类ComparePropertyObject.java的实现描述：用于比较的属性对象
 * @author xurong Jun 13, 2008 5:50:10 PM
 */
public class ComparePropertyObject {
	/**
	 * 待比较的属性信息
	 */
	private PropertyInfo propertyInfo;
	/**
	 * 待比较的属性值
	 */
	private Object propertyValue;
	
	public ComparePropertyObject() {
	}
	
	public ComparePropertyObject(PropertyInfo propertyInfo, Object propertyValue) {
		this.propertyInfo = propertyInfo;
		this.propertyValue = propertyValue;
	}
	public PropertyInfo getPropertyInfo() {
		return propertyInfo;
	}
	public void setPropertyInfo(PropertyInfo propertyInfo) {
		this.propertyInfo = propertyInfo;
	}
	public Object getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
}

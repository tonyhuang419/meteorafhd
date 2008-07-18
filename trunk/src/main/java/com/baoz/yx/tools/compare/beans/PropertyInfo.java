package com.baoz.yx.tools.compare.beans;
/**
 * 类PropertyInfo.java的实现描述：属性的描述信息
 * @author xurong Jun 13, 2008 5:24:21 PM
 */
public class PropertyInfo {
	/**
	 * 所属类的信息
	 */
	private ClassInfo classInfo;
	/**
	 * 属性类型
	 */
	private Class propertyType;
	/**
	 * 属性名称
	 */
	private String propertyName;
	/**
	 * 属性简短描述
	 */
	private String shortDesc;
	/**
	 * 属性描述
	 */
	private String description;
	
	public PropertyInfo(Class propertyType, String propertyName) {
		this.propertyType = propertyType;
		this.propertyName = propertyName;
	}
	public Class getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(Class propertyType) {
		this.propertyType = propertyType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ClassInfo getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
}

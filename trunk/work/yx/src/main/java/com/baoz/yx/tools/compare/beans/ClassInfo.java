package com.baoz.yx.tools.compare.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 类ClassInfo.java的实现描述：类的描述信息
 * @author xurong Jun 13, 2008 4:51:07 PM
 */
public class ClassInfo {
	/**
	 * 类的类型
	 */
	private Class classType;
	/**
	 * 类的简短描述
	 */
	private String shortDesc;
	/**
	 * 类的描述
	 */
	private String description;
	/**
	 * 类的属性集合
	 */
	private List<PropertyInfo> propertyInfos = new ArrayList<PropertyInfo>();
	
	public ClassInfo(Class classType) {
		super();
		this.classType = classType;
	}
	/**
	 * 新增属性的描述
	 * @param propertyInfo
	 */
	public void addPropertyInfo(PropertyInfo propertyInfo){
		propertyInfo.setClassInfo(this);
		propertyInfos.add(propertyInfo);
	}
	/**
	 * 新增属性的描述
	 * @param propertyInfo
	 */
	public void addPropertyInfo(String propertyName,Class propertyType,String shortDesc){
		PropertyInfo propInfo = new PropertyInfo(propertyType,propertyName);
		propInfo.setShortDesc(shortDesc);
		addPropertyInfo(propInfo);
	}
	/**
	 * 删除属性的描述
	 * @param propertyName
	 */
	public void removePropertyInfo(String propertyName){
		PropertyInfo propertyInfo = getPropertyInfo(propertyName);
		if(propertyInfo != null){
			propertyInfos.remove(propertyInfo);
		}
	}
	/**
	 * 获得所有属性
	 * @return
	 */
	public PropertyInfo[] getPropertyInfos(){
		return propertyInfos.toArray(new PropertyInfo[propertyInfos.size()]);
	}
	/**
	 * 获得一个属性
	 * @param propertyName
	 * @return
	 */
	public PropertyInfo getPropertyInfo(String propertyName){
		for (PropertyInfo propertyInfo : propertyInfos) {
			if(propertyInfo.getPropertyName().equals(propertyName)){
				return propertyInfo;
			}
		}
		return null;
	}
	public Class getClassType() {
		return classType;
	}
	public void setClassType(Class classType) {
		this.classType = classType;
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
	@Override
	public boolean equals(Object obj) {
		if(obj != null && (obj instanceof ClassInfo) && classType != null ){
			return classType.equals(((ClassInfo)obj).getClassType());
		}
		return false;
	}
	@Override
	public int hashCode() {
		if(classType != null){
			return classType.hashCode();
		}else{
			return super.hashCode();
		}
	}
}

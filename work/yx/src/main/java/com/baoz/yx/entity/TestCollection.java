package com.baoz.yx.entity;

import java.util.List;
import java.util.Set;

import com.baoz.components.json.annotations.JSON;

/**
 * 类TestCollection.java的实现描述：TODO 类实现描述 
 * @author xurong Jun 10, 2008 2:00:00 PM
 */
public class TestCollection {
	private String name;
	private String code;
	private List<YXTypeManage> types;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@JSON(serialize=false,deserialize=false)
	public List<YXTypeManage> getTypes() {
		return types;
	}
	public void setTypes(List<YXTypeManage> types) {
		this.types = types;
	}	
}

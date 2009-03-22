package com.baoz.yx.tools.compare.beans;

public class CompareContext {
	String fieldName;
	String srcContext;
	String targetContext;

	
	public CompareContext(){	}
	
	public  CompareContext(String fieldName,String srcContext,String targetContext){
		this.fieldName = fieldName;
		this.srcContext = srcContext;
		this.targetContext = targetContext;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getSrcContext() {
		return srcContext;
	}
	public void setSrcContext(String srcContext) {
		this.srcContext = srcContext;
	}
	public String getTargetContext() {
		return targetContext;
	}
	public void setTargetContext(String targetContext) {
		this.targetContext = targetContext;
	}
}

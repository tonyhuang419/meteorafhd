package com.baoz.yx.tools.compare.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 类CompareResult.java的实现描述：比较的结果 
 * @author xurong Jun 13, 2008 5:24:03 PM
 */
public class CompareResult {
	private boolean isChanged = false;
	private CompareObject sourceCompareObject;
	private CompareObject targetCompareObject;
	private List<PropertyCompareResult> propertyCompareResults = new ArrayList<PropertyCompareResult>();
	
	public CompareResult(){
	}
	
	public CompareResult(CompareObject sourceCompareObject,
			CompareObject targetCompareObject) {
		this.sourceCompareObject = sourceCompareObject;
		this.targetCompareObject = targetCompareObject;
	}

	public void addPropertyCompareResult(PropertyCompareResult propertyCompareResult){
		if(!isChanged && propertyCompareResult.isChanged()){
			isChanged = true;
		}
		propertyCompareResults.add(propertyCompareResult);
	}
	
	public PropertyCompareResult[] getPropertyCompareResults(){
		return propertyCompareResults.toArray(new PropertyCompareResult[propertyCompareResults.size()]);
	}
	
	public PropertyCompareResult[] getChangedPropertyCompareResults(){
		return getUnChangedPropertyCompareResults(true);
	}
	
	public PropertyCompareResult[] getUnChangedPropertyCompareResults(){
		return getUnChangedPropertyCompareResults(false);
	}
	/**
	 * 获得修改过和未修改的值
	 * @param isChanged
	 * @return
	 */
	private PropertyCompareResult[] getUnChangedPropertyCompareResults(boolean isChanged){
		List<PropertyCompareResult> retList = new ArrayList<PropertyCompareResult>();
		for (PropertyCompareResult propertyResult : propertyCompareResults) {
			if(propertyResult.isChanged() == isChanged){
				retList.add(propertyResult);
			}
		}
		return retList.toArray(new PropertyCompareResult[retList.size()]);
	}
	
	public PropertyCompareResult getPropertyCompareResult(String propertyName){
		for (PropertyCompareResult propertyResult : propertyCompareResults) {
			if(propertyResult.getSourcePropertyObject().getPropertyInfo().getPropertyName().equals(propertyName)){
				return propertyResult;
			}
		}
		return null;
	}
	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public CompareObject getSourceCompareObject() {
		return sourceCompareObject;
	}

	public void setSourceCompareObject(CompareObject sourceCompareObject) {
		this.sourceCompareObject = sourceCompareObject;
	}

	public CompareObject getTargetCompareObject() {
		return targetCompareObject;
	}

	public void setTargetCompareObject(CompareObject targetCompareObject) {
		this.targetCompareObject = targetCompareObject;
	}

	public void setPropertyCompareResults(
			List<PropertyCompareResult> propertyCompareResults) {
		this.propertyCompareResults = propertyCompareResults;
	}
}
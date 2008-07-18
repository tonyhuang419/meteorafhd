package com.baoz.yx.tools.compare.beans;
/**
 * 类PropertyChangeResult.java的实现描述：属性比较结果
 * @author xurong Jun 13, 2008 5:36:59 PM
 */
public class PropertyCompareResult {
	/**
	 * 0相等，非0不等
	 */
	private int result = 0;
	private boolean isChanged = false;
	private ComparePropertyObject sourcePropertyObject;
	private ComparePropertyObject targetPropertyObject;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
		isChanged = result != 0;
	}
	public boolean isChanged() {
		return isChanged;
	}
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}
	public ComparePropertyObject getSourcePropertyObject() {
		return sourcePropertyObject;
	}
	public void setSourcePropertyObject(ComparePropertyObject sourcePropertyObject) {
		this.sourcePropertyObject = sourcePropertyObject;
	}
	public ComparePropertyObject getTargetPropertyObject() {
		return targetPropertyObject;
	}
	public void setTargetPropertyObject(ComparePropertyObject targetPropertyObject) {
		this.targetPropertyObject = targetPropertyObject;
	}
	
}

package com.baoz.yx.excel.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 类RowResult.java的实现描述：每行的结果
 * @author xurong Jul 1, 2008 11:23:07 AM
 */
public class RowResult<T> {
	private int row;
	private List<BuildError> errorList = new ArrayList<BuildError>();
	private T rowObject;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void addError(BuildError error){
		errorList.add(error);
	}
	
	public BuildError[] getErrors() {
		return errorList.toArray(new BuildError[errorList.size()]);
	}

	public boolean hasErrors() {
		return errorList.size() > 0;
	}
	
	public T getRowObject() {
		return rowObject;
	}
	public void setRowObject(T rowObject) {
		this.rowObject = rowObject;
	}
}

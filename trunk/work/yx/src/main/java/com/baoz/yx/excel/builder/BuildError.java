package com.baoz.yx.excel.builder;
/**
 * 类BuildError.java的实现描述：构建过程中的错误
 * @author xurong Jul 1, 2008 11:14:59 AM
 */
public class BuildError {
	int column;
	int row;
	String message;
	String cellValue;
	Throwable exception;
	public BuildError(int column, int row, String cellValue, String message) {
		this.column = column;
		this.row = row;
		this.cellValue = cellValue;
		this.message = message;
	}
	
	public BuildError(int column, int row, String cellValue, String message,
			Throwable exception) {
		this.column = column;
		this.row = row;
		this.cellValue = cellValue;
		this.message = message;
		this.exception = exception;
	}
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public boolean isHasException() {
		return exception != null;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCellValue() {
		return cellValue;
	}
	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
}

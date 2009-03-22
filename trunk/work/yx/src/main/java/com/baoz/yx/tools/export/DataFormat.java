package com.baoz.yx.tools.export;
/**
 * 类DataFormat.java的实现描述：导出数据的格式
 * @author xurong Oct 22, 2008 1:36:13 PM
 */
public class DataFormat {
	private int columnIndex;
	private Class dateType;
	private String format;
	
	public DataFormat(int columnIndex, Class dateType, String format) {
		super();
		this.columnIndex = columnIndex;
		this.dateType = dateType;
		this.format = format;
	}
	
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public Class getDateType() {
		return dateType;
	}
	public void setDateType(Class dateType) {
		this.dateType = dateType;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}

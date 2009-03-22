package com.baoz.yx.tools.export;

import java.io.OutputStream;
import java.util.List;

/**
 * 类TableExport.java的实现描述：导出表格 
 * @author xurong Oct 22, 2008 9:42:16 AM
 */
public interface TableExport {
	public void addTitle(String[] strArray);
	public void addRow(Object[] row);
	public void addRow(Object[] row,DataFormat[] dataFormats);
	public void addRows(List<Object[]> rows);
	public void addRows(List<Object[]> rows, DataFormat[] dataFormats);
	public void setTableName(String tableName);
	public String getTableName();
	public void export(OutputStream output);
}
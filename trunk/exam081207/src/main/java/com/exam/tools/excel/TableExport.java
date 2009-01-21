package com.exam.tools.excel;

import java.io.OutputStream;
import java.util.List;

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
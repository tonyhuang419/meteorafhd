package com.exam.tools.excel;


public abstract class TableExportFactory {
	public static TableExport createExcelTableExport(){
		return new ExcelTableExport();
	}
}

package com.baoz.yx.tools.export;

import com.baoz.yx.tools.export.excel.ExcelTableExport;

/**
 * 类TableExportFactory.java的实现描述：导出类的工厂类 
 * @author xurong Oct 22, 2008 2:01:16 PM
 */
public abstract class TableExportFactory {
	public static TableExport createExcelTableExport(){
		return new ExcelTableExport();
	}
}

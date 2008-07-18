package com.baoz.yx.excel.builder;

import com.baoz.yx.excel.rule.RowRule;

/**
 * 类ExcelObjectBuilder.java的实现描述： 
 * @author xurong Jun 30, 2008 8:40:04 PM
 */
public interface RowObjectBuilder {
	public void setRule(int startRow,int endRow , RowRule rowRule);
	public RowResult[] parseExcel();
}

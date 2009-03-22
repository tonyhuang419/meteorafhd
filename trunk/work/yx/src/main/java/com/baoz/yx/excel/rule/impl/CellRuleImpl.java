package com.baoz.yx.excel.rule.impl;

import com.baoz.yx.excel.cell.CellValueConvertor;
import com.baoz.yx.excel.cell.jexcel.StringCellValueConvertor;
import com.baoz.yx.excel.rule.CellRule;
import com.baoz.yx.utils.ExcelUtils;

/**
 * 类CellRuleImpl.java的实现描述： 
 * @author xurong Jun 30, 2008 8:30:10 PM
 */
public class CellRuleImpl implements CellRule{
	private int column;
	private String property;
	private CellValueConvertor cellValueConvertor;
	
	
	public CellRuleImpl(int column, String property) {
		this.column = column;
		this.property = property;
		this.cellValueConvertor = new StringCellValueConvertor();
	}
	
	public CellRuleImpl(String columnPos, String property) {
		this.column = ExcelUtils.getColumn(columnPos);
		this.property = property;
		this.cellValueConvertor = new StringCellValueConvertor();
	}
	
	public CellRuleImpl(int column, String property,
			CellValueConvertor cellValueConvertor) {
		this.column = column;
		this.property = property;
		this.cellValueConvertor = cellValueConvertor;
		if(cellValueConvertor == null){
			this.cellValueConvertor = new StringCellValueConvertor();
		}
	}
	
	public CellRuleImpl(String columnPos, String property,
			CellValueConvertor cellValueConvertor) {
		this.column = ExcelUtils.getColumn(columnPos);;
		this.property = property;
		this.cellValueConvertor = cellValueConvertor;
		if(cellValueConvertor == null){
			this.cellValueConvertor = new StringCellValueConvertor();
		}
	}

	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public CellValueConvertor getCellValueConvertor() {
		return cellValueConvertor;
	}
	public void setCellValueConvertor(CellValueConvertor cellValueConvertor) {
		this.cellValueConvertor = cellValueConvertor;
	}
	
}

package com.baoz.yx.excel.cell.jexcel;

import com.baoz.yx.excel.cell.CellValueConvertor;

import jxl.Cell;

/**
 * 类SimpleJExcelCellValue.java的实现描述：获得string值
 * @author xurong Jun 30, 2008 6:54:27 PM
 */
public class StringCellValueConvertor implements CellValueConvertor<String,Cell> {

	public String getCellValue(Cell cell) {
		if(cell == null){
			return null;
		}
		return cell.getContents();
	}
}

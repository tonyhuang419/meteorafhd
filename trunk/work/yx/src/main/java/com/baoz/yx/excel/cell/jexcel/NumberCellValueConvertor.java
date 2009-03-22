package com.baoz.yx.excel.cell.jexcel;

import java.text.DecimalFormat;
import java.text.ParseException;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;

import org.apache.commons.lang.math.NumberUtils;

import com.baoz.yx.excel.cell.CellValueConvertor;

/**
 * 类NumberJExcelCellValue.java的实现描述：获得cell里的number值
 * 
 * @author xurong Jun 30, 2008 6:56:16 PM
 */
public class NumberCellValueConvertor implements CellValueConvertor<Number,Cell> {

	private DecimalFormat decimalFormat;
	
	public NumberCellValueConvertor() {
		
	}

	public NumberCellValueConvertor(String decimalFormat) {
		this.decimalFormat = new DecimalFormat(decimalFormat);
	}

	public Number getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		} else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
			NumberCell nc = (NumberCell) cell;
			return nc.getValue();
		} else if(cell.getContents().trim().length()>0){
			if(decimalFormat != null){
				try {
					return decimalFormat.parse(cell.getContents());
				} catch (ParseException e) {
					throw new RuntimeException(e.getMessage()+",cell column:"+cell.getColumn()+" row:"+cell.getRow()+" value:["+cell.getContents()+"]",e);
				}
			}else if(NumberUtils.isDigits(cell.getContents())){
				return Double.parseDouble(cell.getContents());
			}else{
				throw new RuntimeException("cell column:"+cell.getColumn()+" row:"+cell.getRow()+" value:["+cell.getContents()+"] is not a valid number.");
			}
		}
		return null;
	}
}

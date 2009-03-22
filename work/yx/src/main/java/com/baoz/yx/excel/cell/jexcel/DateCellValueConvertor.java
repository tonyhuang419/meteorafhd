package com.baoz.yx.excel.cell.jexcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;

import com.baoz.yx.excel.cell.CellValueConvertor;

public class DateCellValueConvertor implements CellValueConvertor<Date,Cell> {
	
	private SimpleDateFormat dateFormat;
	
	public DateCellValueConvertor() {
	}
	
	public DateCellValueConvertor(String dateFormat) {
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}
	
	public Date getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		} else if (cell.getType() == CellType.DATE || cell.getType() == CellType.DATE_FORMULA) {
			DateCell nc = (DateCell) cell;
			return nc.getDate();
		} else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
			NumberCell nc = (NumberCell) cell;
			return HSSFDateUtil.getJavaDate(nc.getValue());
		}
		else if(cell.getContents().trim().length()>0){
			if(dateFormat != null){
				try {
					return dateFormat.parse(cell.getContents());
				} catch (ParseException e) {
					throw new RuntimeException(e.getMessage()+",cell column:"+cell.getColumn()+" row:"+cell.getRow()+" value:["+cell.getContents()+"]",e);
				}
			}
		}
		return null;
	}
}

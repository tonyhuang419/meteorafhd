package com.baoz.yx.excel.cell.jexcel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;

import org.apache.commons.lang.StringUtils;

import com.baoz.yx.excel.cell.CellValueConvertor;

/**
 * 类StringArrayCellValueConvertor.java的实现描述：
 * @author xurong Jul 1, 2008 3:03:46 PM
 */
public class StringArrayCellValueConvertor implements CellValueConvertor<String[],Cell> {
	private boolean isSplitByNewLine;
	private boolean isSplitBySeparator = true;
	private String splitSeparator = ",";
	
	public StringArrayCellValueConvertor() {
	}

	public StringArrayCellValueConvertor(boolean isSplitByNewLine) {
		this.isSplitByNewLine = isSplitByNewLine;
		this.isSplitBySeparator = !isSplitByNewLine;
	}

	public StringArrayCellValueConvertor(String splitSeparator) {
		this.splitSeparator = splitSeparator;
		this.isSplitByNewLine = false;
		this.isSplitBySeparator = true;
	}

	/* (non-Javadoc)
	 * @see com.baoz.yx.excel.cell.CellValueConvertor#getCellValue(java.lang.Object)
	 */
	public String[] getCellValue(Cell cell) {
		if(cell == null){
			return null;
		}
		String content = cell.getContents();
		if(isSplitByNewLine){
			BufferedReader reader = new BufferedReader(new StringReader(content));
			try {
				List<String> list = new ArrayList<String>();
				String line = reader.readLine();
				while(line != null){
					list.add(line);
					line = reader.readLine();
				}
				return (String[]) list.toArray(new String[list.size()]);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage()+",cell column:"+cell.getColumn()+" row:"+cell.getRow()+" value:["+cell.getContents()+"]",e);
			}
		}else if(isSplitBySeparator){
			return StringUtils.split(content,splitSeparator);
		}
		if(StringUtils.isNotBlank(content)){
			return new String[] { content};
		}
		return null;
	}

	public boolean isSplitByNewLine() {
		return isSplitByNewLine;
	}

	public void setSplitByNewLine(boolean isSplitByNewLine) {
		this.isSplitByNewLine = isSplitByNewLine;
	}

	public boolean isSplitBySeparator() {
		return isSplitBySeparator;
	}

	public void setSplitBySeparator(boolean isSplitBySeparator) {
		this.isSplitBySeparator = isSplitBySeparator;
	}

	public String getSplitSeparator() {
		return splitSeparator;
	}

	public void setSplitSeparator(String splitSeparator) {
		this.splitSeparator = splitSeparator;
	}
}

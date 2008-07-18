package com.baoz.yx.excel.builder.jexcel;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import com.baoz.yx.excel.builder.BuildError;
import com.baoz.yx.excel.builder.RowObjectBuilder;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.excel.rule.CellRule;
import com.baoz.yx.excel.rule.RowRule;
import com.baoz.yx.tools.append.utils.JudgeUtils;

/**
 * 类JExcelObjectBuilder.java的实现描述：
 * 
 * @author xurong Jun 30, 2008 8:45:45 PM
 */
public class JExcelRowObjectBuilder implements RowObjectBuilder {
	private static Logger logger = Logger.getLogger(JExcelRowObjectBuilder.class);
	private int startRow;
	private int endRow;
	private RowRule rowRule;
	private Sheet sheet;
	private Class targetClass;
	private BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
	private boolean isSkipBlankRow = true;

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}

	public boolean isSkipBlankRow() {
		return isSkipBlankRow;
	}

	public void setSkipBlankRow(boolean isSkipBlankRow) {
		this.isSkipBlankRow = isSkipBlankRow;
	}

	public JExcelRowObjectBuilder() {

	}

	public JExcelRowObjectBuilder(Sheet sheet, Class targetClass) {
		this.sheet = sheet;
		this.targetClass = targetClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baoz.yx.excel.builder.ExcelObjectBuilder#addRule(int, int,
	 *      com.baoz.yx.excel.rule.RowRule)
	 */
	public void setRule(int startRow, int endRow, RowRule rowRule) {
		this.startRow = startRow;
		this.endRow = endRow;
		this.rowRule = rowRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baoz.yx.excel.builder.ExcelObjectBuilder#parseExcel()
	 */
	public RowResult[] parseExcel() {
		List<RowResult> resultList = new ArrayList<RowResult>();
		for (int i = startRow; i <= Math.min(endRow, sheet.getRows()-1); i++) {
			CellRule[] cellRules = rowRule.getCellRules();
			Object target = null;
			try {
				target = targetClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			beanWrapper.setWrappedInstance(target);
			// 创建结果
			RowResult rowResult = new RowResult();
			rowResult.setRowObject(target);
			rowResult.setRow(i);
			// ////////
			boolean isNotEmpty = false;
			for (int j = 0; j < cellRules.length; j++) {
				CellRule cellRule = cellRules[j];
				try {
					if(cellRule.getColumn() < sheet.getRow(i).length){
						Object cellValue = cellRule.getCellValueConvertor().getCellValue(sheet.getCell(cellRule.getColumn(), i));
						if(cellValue != null){
							beanWrapper.setPropertyValue(cellRule.getProperty(),cellValue);
						}
						if(!isNotEmpty && JudgeUtils.isNotEmpty(cellValue)){
							isNotEmpty = true;
						}
					}else{
						//只记日志，不记录error
						logger.warn("rule column:"+cellRule.getColumn()+" large than max row colums:"+(sheet.getRow(i).length-1)+" at row "+i);
					}
				} catch (RuntimeException e) {
					logger.error(e.getMessage(),e);
					// 获得cell
					Cell cell = sheet.getCell(cellRule.getColumn(), i);
					// 获得cell值
					String cellValue = null;
					if(cell != null){
						cellValue = cell.getContents();
					}
					// 记录错误
					BuildError error = new BuildError(j,i,cellValue,e.getMessage(),e);
					rowResult.addError(error);
				}
			}
			if(isNotEmpty || !isSkipBlankRow){
				resultList.add(rowResult);
			}
		}
		return (RowResult[]) resultList.toArray(new RowResult[resultList.size()]);
	}
}

package com.baoz.yx.excel.cell;
/**
 * 类CellEditor.java的实现描述：获取cell的值
 * @author xurong Jun 30, 2008 6:40:29 PM
 */
public interface CellValueConvertor<T,V> {
	public T getCellValue(V cell);
}

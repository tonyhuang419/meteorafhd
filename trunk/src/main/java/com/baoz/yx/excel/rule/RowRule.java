package com.baoz.yx.excel.rule;
/**
 * 类RowRule.java的实现描述：定义一行的获取规则 
 * @author xurong Jun 30, 2008 8:16:09 PM
 */
public interface RowRule {
	public void addCellRule(CellRule cellRule);
	public CellRule[] getCellRules();
}

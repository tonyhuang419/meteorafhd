package com.baoz.yx.excel.rule.impl;

import java.util.ArrayList;
import java.util.List;

import com.baoz.yx.excel.rule.CellRule;
import com.baoz.yx.excel.rule.RowRule;

/**
 * 类RowRuleImpl.java的实现描述：
 * @author xurong Jun 30, 2008 8:33:19 PM
 */
public class RowRuleImpl implements RowRule {
	List<CellRule> cellRuleList = new ArrayList<CellRule>();

	/* (non-Javadoc)
	 * @see com.baoz.yx.excel.rule.RowRule#addCellRule(int, com.baoz.yx.excel.rule.CellRule)
	 */
	public void addCellRule(CellRule cellRule) {
		cellRuleList.add(cellRule);
	}

	/* (non-Javadoc)
	 * @see com.baoz.yx.excel.rule.RowRule#getCellRules()
	 */
	public CellRule[] getCellRules() {
		return cellRuleList.toArray(new CellRule[cellRuleList.size()]);
	}

}

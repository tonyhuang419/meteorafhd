package com.baoz.yx.service;
/**
 * 类ShowMoonBillQueryService.java的实现描述：月度开票计划
 * @author xurong Jul 9, 2008 1:01:46 PM
 */
public interface ShowMoonBillQueryService {
	/**
	 * 判断本月是否生成过计划(是当月是否生成当月的计划，而不是上个月是否生成当月的计划)
	 * @return
	 */
	public boolean isGeneratedCurrentMonthBillPlan();
	/**
	 * 生成当月计划和下月预估计划，下月计划将在下月生成时重新生成
	 */
	public void generateCurrentMonthBillPlan();
}

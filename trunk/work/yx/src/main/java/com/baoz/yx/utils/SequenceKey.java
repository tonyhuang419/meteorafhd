package com.baoz.yx.utils;
/**
 * 类SequenceKey.java的实现描述： 序列号的key值
 * @author xurong Jul 23, 2008 11:17:39 AM
 */
public enum SequenceKey {
	/**
	 * 正式合同流水号
	 */
	contractnumber,
	/**
	 * 售前合同流水号
	 */
	sellbeforeNumber,
	/**
	 * 外协合同流水号
	 */
	assistanceNumber,
	/**
	 * 未签发票编号流水号
	 */
	wBillNumber,
	/**
	 * 已签发票编号流水号
	 */
	sBillNumber,
	/**
	 * 实际开票收款计划变更表系统id,从10000000000L开始
	 */
	changeRealConPlan("changeRealConPlan",10000000000L),
	/**
	 * 合同变更备份表中的批次
	 */
	contractChangeBatch,
	
	/**
	 * 项目主体信息变更表系统id,从10000000000L开始
	 */
	changeItemMainInfo("changeItemMainInfo",10000000000L),
	
	/**
	 * 项目组成信息变更表系统id,从10000000000L开始
	 */
	changeItemInfo("changeItemInfo",10000000000L),
	
	/**
	 * 阶段变更表系统id，从10000000000开始
	 */
	changeItemStageInfo("changeItemStageInfo",10000000000L),
	
	/**
	 * 阶段计划变更表系统id，从10000000000开始
	 */
	changeItemStagePlan("changeItemStagePlan",10000000000L),
	
	/**
	 * 合同费用组成变更表系统id，从10000000000开始
	 */
	changeMainInfoPart("changeMainInfoPart",10000000000L);
	
	private final String key;

	private final long initValue;
	
	private SequenceKey(){
		this.key = this.name();
		this.initValue = 1;
	}
	private SequenceKey(String key){
		this.key = key;
		this.initValue = 1;
	}
	
	private SequenceKey(String key , long initValue){
		this.key = key;
		this.initValue = initValue;
	}

	public String getKey() {
		return key;
	}
	
	public long getInitValue() {
		return initValue;
	}
}

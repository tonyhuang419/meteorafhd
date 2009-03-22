package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.vo.ProcessResult;

public interface ISystemOpAssistanceService {

	/**
	 * 自动创建一个外协付款申请。
	 * @param contract  从数据库中查询出来的
	 * @param sectionList  ///从数据库中查询出来的
	 * @param payInfo   //自己构建的，目前只传入了三个值，分别是付款申请日期，是否预付，申请人
	 */
	public void createAssistanceApplyPayInfo(AssistanceContract contract,List<AssistanceSection> sectionList,AssistancePayInfo payInfo,List<AssistancePayInfo> payInfoList);

	/**
	 * 取消外协付款申请，传入的是外协付款申请的id的数组
	 * @param payInfoIds
	 */
	public void cancelAssistanceApplyPayInfo(Long[] payInfoIds);
	/**
	 * 取消外协付款申请。传入的是单个的外协付款申请id
	 * @param payInfoId
	 */
	public ProcessResult cancelAssistanceApplyPayInfo(Long payInfoId);
	
	/**
	 * 取消外协付款申请的关联，传入的是正常付款的外协付款申请的id，把该申请所关联的所有的预付都取消掉
	 * @param payInfoId
	 * @return
	 */
	public ProcessResult cancelPrep(Long payInfoId);
}

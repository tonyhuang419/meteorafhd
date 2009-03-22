package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.vo.ProcessResult;

public interface IAssistancePayService {

	/**
	 * 建立付款申请和票之间的关联并且计算票据余额
	 * @param payInfoList
	 * @param ticketList
	 */
	public void createRelationBetweenPayInfoandTicket(List<AssistancePayInfo> prepPayInfoList,List<AssistanceTicket> ticketList,AssistancePayInfo payInfo);
	 
	/**
	 * 建立外协阶段和付款申请之间的关联
	 * @param sectionList
	 * @param payInfo
	 */
	public void createRelationBetweenSectionandPayInfo(List<AssistanceSection> sectionList,AssistancePayInfo payInfo);
	
	/**
	 * 传入外协付款申请单的对象列表（关联的预付申请单）
	 * 传入外协付款申请单（本次支付的申请单）
	 * 建立预付和非预付之间的关联 传入的对象要从数据库中读取
	 * @param payInfoList
	 */
	public void createRelationBetweenPrePayInfoandPayInfo(List<AssistancePayInfo> payInfoList,AssistancePayInfo payInfo);

	/**
	 * 保存付款申请
	 * @param payInfo
	 * @return
	 */
	public AssistancePayInfo saveAssistancePayInfo(AssistancePayInfo payInfo);
	
	/**
	 * 通过传入的阶段的id的数组，然后计算出阶段的总金额
	 * @param assistanceSectionIds
	 * @return
	 */
	public Double getRelationSectionAmountBySectionId(Long[] assistanceSectionIds);
	
	/**
	 * 删除该申请单下面的关联
	 * @param payInfoId
	 */
	public void deleteHasRelationSectionByPayInfoId(Long payInfoId);
	
	/**
	 * 通过传入的阶段id的数组获取阶段的list
	 * @param assistanceSectionIds
	 * @return
	 */
	public List<AssistanceSection> getSectionBySectionIds(Long[] assistanceSectionIds);
	
	/**
	 * 通过传入的发票和申请单的关联表查询出发票的所有信息
	 * @param assistanceList
	 * @return
	 */
	public List<AssistanceTicket> changeTicketToAssistance(List<Assistance> assistanceList);

	/**
	 * 传入非预付的id查询出与之相关的预付申请的
	 * @param payInfo
	 * @return
	 */
	public Double getAllRelationPayInfoMoney(AssistancePayInfo payInfo);
	
	/**
	 * 修改保存外协付款申请（只针对AssistancePayInfo）
	 * @param payInfo
	 * @return
	 */
	public AssistancePayInfo updateAssistancePayInfo(AssistancePayInfo payInfo);
	
	public AssistancePayInfo saveAssistancePayInfo(AssistancePayInfo payInfo,List<AssistancePayInfo> payInfoList,List<AssistanceTicket> ticketList,List<AssistanceSection> assistanceSectionList);
	
	public void unchainRelation(Assistance assistance);
	
	public Assistance doTicketByUpdateApply(Assistance assistance,AssistancePayInfo payInfo,List<AssistancePayInfo> prepPayInfoList);

	
	/**
	 * 删除外协付款申请（单个）
	 * @param payInfoId
	 */
	public ProcessResult deleteAssistancePayInfo(Long payInfoId);
	
	/**
	 * 删除外协发票信息列表
	 * @param ticketIds
	 */
	public void deleteAssistanceTicket(Long[] ticketIds);
	
	/**
	 * 删除单个外协发票信息
	 * @param ticketId
	 */
	public void deleteAssistanceTicket(Long ticketId);
	
	
	/**
	 * 通过传入的外协付款申请的id获取付款申请关联的发票
	 * @param payInfo
	 * @return
	 */
	public List<AssistanceTicket> getTicketByAssistancePayInfo(AssistancePayInfo payInfo);
	
	/**
	 * 通过被关联的非预付款申请查询出关联的预付款信息
	 * @param notPrep
	 * @return
	 */
	public List<AssistancePayInfo> getPrepPayInfoListByRelationNotPrep(AssistancePayInfo notPrep);
	/**
	 * 根据外协合同查询出所有的开票信息
	 * @param assConId
	 * @return
	 */
	public List<AssistanceTicket> getTicketByConId(Long assConId);
	
}

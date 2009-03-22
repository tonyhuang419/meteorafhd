package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.vo.MyBean;

public interface IAssistanceService {
	public List listA(String hql);
	public List queryAssistanceContract(List list);
	/**
	 *  修改合同状态(提交确认)
	 * @param String[] id
	 * @return
	 */
	public int updateState(String[] id);
	
	/**
	 *  修改合同状态(确认通过)
	 * @param String[] id
	 * @return
	 */
	public int updateState2(String[] id);
	
	/**
	 *  修改合同状态(确认退回)
	 * @param String[] id
	 * @return
	 */
	public int back(String[] id);
	
	/**
	 *  查询外协合同和供应商
	 * @param list
	 * @return
	 */
	public List queryAssisContract(List list);
	
	/**
	 *  返回供应商Id
	 * @param String name
	 * @return
	 */
	public List queryId(String supplier);
	
	/**
	 *  查询外协开票和供应商
	 * @param List list
	 * @return
	 */
	public List getSupply(List list);
	
	/**
	 *  查询发票和供应商
	 * @param List list
	 * @return
	 */
	public List queryTicket(List list);
	/**
	 * 查询条件拼接合同查询的SQL语句
	 * @return 
	 */
	public String oql(String conName,String conId,String mainItemDept,String partyAProId,String contype);
	/**
	 * 查询正式合同的所有信息
	 * @return 
	 */
	public  List<Object> contractList(List list);
	/**
	 * 查询条件拼接客户查询的SQL语句
	 * @return 
	 */
	public String oql1(String clientName);
	
	/**
	 * 获取外协合同 所属主合同 销售员名称
	 * 2008年8月18日13:52:00
	 */
	public String getAContractName(Long assistanceId);
	
	/**
	 * 判断外协合同是否能够确认通过
	 * 返回值虽然是数组，但是里面存了三个字段，第一个是是否有错误，第二个是错误的合同id，第三个是错误的代码
	 * @param ids
	 * @return
	 */
	public Object[] isSureSubmit(String[] ids);
	/**
	 * 拆分外协阶段金额
	 * @param sectionId
	 * @param SplitAmount
	 */
	public void splitSectionAmount(Long sectionId,Double SplitAmount);
	/**
	 * 合并阶段信息
	 * @param monthlyBillproSids
	 */
	public void mergeRealPlan(Long[] sectionId);
	
	public List<MyBean> getStageByItemSid(Long itemSid);	
	
	/**
	 * 判断该阶段是否关联过发票申请，如果关联过了不能被选中
	 * @param section
	 * @return true 表示已经关联过了。
	 * false表示没有被关联过
	 */
	public Boolean checkSectionIsRelation(Long section);
	
	public List<AssistanceSection> getAssistanceSectionByContractId(Long assistanceContractId);
	
	/**
	 * 判断付款申请中的任务号是否存在，如果存在返回true，如果不存在返回false
	 * @param assignmentId
	 * @return
	 */
	public Boolean checkAssignmentIdExists(String assignmentId);
	
	/**
	 * 判断付款申请中的任务号是否存在（除了自己以外），如果存在返回true，如果不存在返回false
	 * @param assignmentId
	 * @return
	 */
	public Boolean checkAssignmentIdExists(String assisgnmentId,Long payInfoId);
	
	/**
	 * 判断付款申请中的接受号是否存在，如果存在返回true，如果不存在返回false
	 * @param reveNum
	 * @return
	 */
	public Boolean checkreceivNumExists(String reveNum);
	
	
	/**
	 * 判断付款申请中的接受号是否存在（除了自己以外），如果存在返回true，如果不存在返回false
	 * @param reveNum
	 * @return
	 */
	public Boolean checkreceivNumExists(String reveNum,Long payInfoId);
	
	/**
	 * 检查该阶段不在该payInfo中，
	 * @param sectionId
	 * @param payInfoId
	 * @return
	 */
	public Boolean checkSectionNotRelation(Long sectionId,Long payInfoId);
	
	/**
	 * 获取该阶段时候在在payInfo中
	 * @param sectionId
	 * @param payInfoId
	 * @return
	 */
	public Boolean getCheckedSectionRelation(Long sectionId,Long payInfoId);
	
	/**
	 * 获取已经选中的id
	 * @param payInfoId
	 * @return
	 */
	public Long[] getcheckedSectionByPayInfoId(Long payInfoId);
	
	/**
	 * 查询出与外协付款申请相关的阶段列表
	 * @param payInfoId
	 * @return
	 */
	public List<AssistanceSection> getAssistanceSEctionByPayInfoId(Long payInfoId);
	
	/**
	 * 检查外协合同号是否存在（除自己以外），如果不存在返回false，如果存在返回true
	 * @param assistanceNo
	 * @param assistanceId
	 * @return true表示存在
	 * @return false表示不存在
	 */
	public Boolean checkAssistanceNoExists(String assistanceNo,Long assistanceId);
	
	/**
	 * 判断外协付款申请是否能确认通过
	 * @param payInfoId
	 * @return  Object[]{Boolean.FALSE（FALSE表示没有错误，TRUE表示有错误）,-1L（返回错误的申请单号）,"-1"（返回错误类型）}
	 */
	public Object[] isSureSubmitApply(Long payInfoId);
	
	/**
	 * 通过外协付款申请获取外协合同信息
	 * @param payInfoId
	 * @return
	 */
	public AssistanceContract getContactByPayInfoId(Long payInfoId);
	
	
	/**
	 * 外协付款申请确认处理（单个）
	 * @param payInfoId
	 */
	public void confirmPayInfoOperator(Long payInfoId);
	
	
	/**
	 * 外协付款申请确认处理（多个）
	 * @param payInfoId
	 */
	public void confirmPayInfoOperator(String[] ids);
	
	/**
	 * 外协付款申请确认通过（单个）
	 * @param payInfoId
	 */
	public void confirmPayInfoPass(Long payInfoId);
	
	/**
	 * 外协付款申请确认通过（多个）
	 * @param payInfoId
	 */
	public void confirmPayInfoPass(String[] ids);
	
	/**
	 * 通过外协合同号获取外协供应商信息
	 * @param contractId
	 * @return
	 */
	public SupplierInfo getSupplierByAssistanceContractId(Long contractId);
	
	
	/**
	 * 获取同一个供应商,同一个销售员下面的预付款信息(确认处理以后的)
	 * @param supplierId
	 * @return
	 */
	public List<AssistancePayInfo> getAssistancepayInfoBySupplierId(AssistanceContract contract);
	
	public SupplierInfo getSupplierBySupplyId(Long supplyId);
	
	/**
	 * 传入一个付款申请单id，然后查询出该申请单对应的合同下面的付款申请履历，除自己以外
	 * @param payInfoId
	 * @return
	 */
	public List<AssistancePayInfo> getPayInfoRecordNotContained(Long payInfoId);
	
	/**
	 * 传入一个付款申请单id，查询出该申请单下面的预付款申请记录的list
	 * @param payInfoId
	 * @return
	 */
	public List<AssistancePayInfo> getRelationPayInfoByPayInfo(Long payInfoId);
	
	/**
	 * 外协合同取消确认
	 */
	public Object[] cancelSure(Long[] outsourceContractId);
	
	/**
	 * 外协付款申请取消处理（多个）
	 * @param ids
	 */
	public void cancelPayInfoOperator(String[] ids);
	
	/**
	 * 外协付款申请取消处理（单个）
	 * @param id
	 */
	public void cancelPayInfoOperator(Long id);

}

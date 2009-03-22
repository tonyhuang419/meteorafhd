/**
 * 
 */
package com.baoz.yx.service;

import java.util.Date;

/**
 * @author T-08-D-121
 *
 */
public interface ICodeGenerateService {
	/**
	 * 生成合同编号
	 * @param clientId 客户id
	 * @return
	 */
	public String generateContractCode(Long clientId);
	/**
	 * 生成售前合同编号
	 * @param clientId 客户id
	 * @return
	 */
	public String generateSellbeforeCode(Long clientId);
	/**
	 * 生成外协合同编号
	 * @param projectDepartmentCode 项目的工程责任部门
	 * @param submitDate 提交确认日期
	 * @param clientId 客户id
	 * @return
	 */
	public String generateAssistanceCode(String projectDepartmentCode,Date submitDate , Long clientId);
	/**
	 * 未签发票收据编号
	 * 
	 * @return
	 */
	public String generateWBillCode();
	/**
	 * 已签发票收据编号
	 * @return
	 */
	public String generateSBillCode();
	/**
	 * 红冲申请单号
	 * @return
	 */
	public String generateHCBillCode();
	
	/**
	 * 根据工程类或者集成类申购生成合同号
	 * 1为工程类 2为集成类
	 * @return
	 */
	public String generateApplyCode(String applytype);
	
	/**
	 * 根据外协合同号生成申购单号
	 * @param assid 外协合同系统号
	 * @return
	 */
	public String assApplyInfoCode(Long assid);
	
	/**
	 * 自动生成发票号
	 * @return
	 */
	public String generateInvoveNo();
	
	public Long generateBatchByImportReve();
	
	/**
	 * 自动生成外协发票号
	 * @return
	 */
	public String generateAssistanceTicketNo();
	
	/**
	 * 自动生成任务号
	 * @return
	 */
	public String generateAssistancePayInfoAssignmentId();
	
	/**
	 * 自动生成接受号
	 * @return
	 */
	public String generateAssistancePayInfoReceivNum();
}

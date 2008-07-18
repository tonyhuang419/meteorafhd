package com.baoz.yx.service;

import java.util.List;

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
}

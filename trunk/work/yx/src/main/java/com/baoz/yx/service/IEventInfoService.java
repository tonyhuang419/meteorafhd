package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;

/**
 * 
 * @author xusheng
 *@version 1.0
 *create By Date:2008年7月10日
 */
public interface IEventInfoService {

	/**
	 * 通过传入的合同id查询合同费用列表
	 * @param conId
	 * @return
	 */
	public List<ContractMaininfoPart> getMainInfoPartByConId(Long conId);
	
	/**
	 * 通过合同id查询项目列表
	 * @param conId
	 * @return
	 */
	public List<ContractItemMaininfo> getItemMainInfoByConId(Long conId);
	
	/**
	 * 传入项目列表的对象，项目费用的对象，把他插入到数据库！
	 * @param itemMainInfo
	 * @param itemInfo
	 */
	public void saveEventInfo(ContractItemMaininfo itemMainInfo,ContractItemInfo itemInfo,String operator);
	

	
	/**
	 * 通过费用id查询费用信息和项目信息
	 * @param id
	 * @return
	 */
	public ContractItemInfo selectContractItemInfoById(Long id);
	
	/**
	 * 修改费用信息
	 * @param itemInfo
	 */
	public void updateContractItemInfo(ContractItemInfo itemInfo);
	
	/**
	 * 获取每个合同费用下面的所有的项目费用的和
	 * @param conid   合同id
	 * @return
	 */
	public List getFeeMoneyByPartId(Long conid);
	
	/**
	 * 查询某一个合同的可编辑余额
	 * @param itemInfoId
	 * @return
	 */
	public Double getOneFeeMoney(Long itemInfoId);
	
	/**
	 * 插入售前合同的同时插入责任部门和负责人姓名
	 * @param cbs
	 */
	public void saveContractBeforeSell(ContractBeforeSell cbs);

	/**
	 * 通过传入的部门code查询该部门的负责人列表
	 * @param code
	 */
	public List selectDepartmentListByDepartCode(String code);
}

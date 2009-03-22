package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.contract.ContractMainInfo;

public interface IPurService {
	/**
	 * 查询所有草稿的审购采购信息
	 * @param list
	 * @return
	 */
	public List queryVerityPurchase(List list);
	/**
	 *  确认状态 0-待确认；1-确认通过；2-确认退回4-草稿
	 * @param sb
	 * @param id
	 * @return
	 */
	public int updateState(StringBuffer sb);
	/**
	 * 查询正式合同的所有信息
	 * @return 
	 */
	public  List<Object> contractList(List list);
	/**
	 * 查询条件拼接合同查询的SQL语句
	 * @return 
	 */
	public String oql(String conName,String conId,String mainItemDept,String partyAProId,String applyType);
	
	public String oqlfromWhere(String conName, String conId, String mainItemDept,String partyAProId ,String applyType);
	/**
	 * 查询条件拼接客户查询的SQL语句
	 * @return 
	 */
	public String oql1(String clientName);
	/**
	 * 根据页面的选择和填写的内容充实ApplyMessage类
	 * @param am
	 * @param idstate1
	 * @param idstate2
	 * @param projectName1
	 * @param projectName2
	 * @param cNameId1
	 * @param cNameId2
	 * @param state
	 * @return
	 */
	public ApplyMessage fillAM(ApplyMessage am, String idstate1, String idstate2, String projectName1, String projectName2, Long cNameId1, Long cNameId2, String state);
	/**
	 * 根据合同中申购页面的选择和填写的内容充实ApplyMessage类
	 * @param am
	 * @param idstate1
	 * @param idstate2
	 * @param state
	 * @return
	 */
	public ApplyMessage fillContractAM(ApplyMessage am, String idstate1, String idstate2, String state);
	/**
	 * 查询申购的SQL语句
	 * @return
	 */
	public String applyMessageOql();
	/**
	 * 查询审核申购的SQL语句
	 * @return
	 */
	public String applyMessageOql1();
	/**
	 * 根据id查询出员工
	 * @param id
	 * @return
	 */
	public Employee getEmployee(Long id);
	/**
	 * 根据合同号系统号查询出合同
	 * @param id
	 * @return
	 */
	public ContractMainInfo getContractMainInfo(Long id);
	/**
	 * 根据客户系统号查出客户
	 * @param id
	 * @return
	 */
	public YXClientCode getYXClientCode(Long id);
	/**
	 * 查询用户，合同
	 */
	public void search();
	/**
	 * 删除申购单
	 * @param ids（申购单id）
	 */
	public void delPurchase(String amids);
	/**
	 * 关联合同
	 * @param amids（申购单id）
	 * @param ids（合同id）
	 */
	public void linkCountract(String amids,String ids);
	/**
	 * 确认提交申购单
	 * @param amids（申购单id）
	 */
	public void submitPurchase(String amids);
	/**
	 * 根据页面的修改和填写的内容充实ApplyMessage类
	 * @param am
	 * @param idstate1
	 * @param idstate2
	 * @param projectName1
	 * @param projectName2
	 * @param cNameId1
	 * @param cNameId2
	 * @param state
	 * @return
	 */
	
	
	/**
	 * 判断确认状态
	 * @param state
	 * @return
	 */
	public String setAS(String state);
	
	
	
	public ApplyMessage updateFillPurchase(ApplyMessage am,ApplyMessage am1, String idstate1, String idstate2, String projectName1, String projectName2, Long cNameId1, Long cNameId2, String state);
	/**
	 * 确认通过、确认退回根据传入的i判断（0-确认退回 1-确认通过）
	 * @param i
	 */
	public void checkConfirm(int j,String amids , String returnReason);
}

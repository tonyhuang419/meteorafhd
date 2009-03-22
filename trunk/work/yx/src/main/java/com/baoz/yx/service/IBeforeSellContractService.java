package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.ImportantProject;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;


public interface IBeforeSellContractService {

	/**
	 * 记录售前合同修改历史，返回是否存在修改
	 */
	public boolean recordCBSChange(ContractBeforeSell cbs , Long impId);
	
	
	/**
	 * 记录重点项目合同修改历史，返回是否存在修改
	 */
	public boolean recordIMPChange(ImportantProject imp);
	
	/**
	 * 获取项目跟踪结果List
	 */
	public List<YXTypeManage>  loadTrackList( String projectStateSelect );
	
	
	/**
	 * 获取员工对应客户List
	 */
	public List<YXOEmployeeClient>  getEmployeeClientList( );
	
	/**
	 * 处理员工对应客户List
	 * 如果该重点工程项目客户不在该该销售员客户列表中，增加
	 */
	public List<YXOEmployeeClient>  processEmployeeClientList(Long cid );
	
	/**
	 *  获取重点工程项目历史
	 */
	public   List<Object[]>  doGetImpModHistory( Long impId ); 
	
	/**
	 *  保存重点工程项目
	 */
	public void  saveIMP(   ImportantProject  imp);
	
	/**
	 *  修改重点工程项目
	 */
	public void  modIMP(   ImportantProject  imp);
	
	/**
	 *  searchIMP
	 */
	public PageInfo  searchIMP(   Long  customerId  , String 	projectNum  ,  String projectName  ,   PageInfo info);
	
	/**
	 * 保存重点工程和售前关联
	 */
	public void saveImpAndCBSRelation( Long cbsSid , Long impSid  );
	
	/**
	 * 删除售前合同 
	 */
	public void delCBS(Long ids[]);
	
}

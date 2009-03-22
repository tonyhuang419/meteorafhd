package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.exception.ServiceException;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.programEconomy.YXOSectionInfo;
import com.baoz.yx.entity.programEconomy.YXOSectionRecord;

/**
 * 如在此service中增加方法请写上svn帐户名和增加时间还有该方法的用途 新增加的方法请写在最下面！！！！！！！！！！！！！！！！！！！！ 如 add
 * by yangyuan 6.20 查询销售员返回一个列表 public List<Employee> listEmployee()
 * @author Administrator
 *
 */
public interface ISystemService {
	/**
	 * 删除选择的对象
	 * 
	 * @param idss
	 * @param entityName
	 * @return
	 */
	public int deleteChose(String idss, String hql);

	/**
	 * 查询显示所有的客户
	 * 
	 * @param hql
	 * @return
	 */
	public PageInfo purList(String hql);

	/**
	 * 查询所有待确认的审购采购信息
	 * 
	 * @param list
	 * @return
	 */
	public List queryVerityPurchase(List list);

	/**
	 * 返回一个列表
	 * 
	 * @param hql
	 * @return
	 */
	public List listA(String hql);

	/**
	 * 返回一个列表
	 * 
	 * @param hql
	 * @return
	 */
	public List listB(String hql);

	/**
	 * 查询一个类，返回条件为id的对象
	 * 
	 * @param hql
	 * @param id
	 * @return
	 */
	public Object uniqueQuery(String hql, Long id);

	/**
	 * 确认状态 0-待确认；1-确认通过；2-确认退回4-草稿
	 * 
	 * @param action
	 * @param sb
	 * @param id
	 * @return
	 */
	public int updateState(String action, StringBuffer sb);

	/**
	 * 
	 * @param idstate1
	 * @param idstate2
	 * @param act
	 * @param pNam
	 * @param pName
	 * @param cusId
	 */
	public ApplyMessage saveSet(String idstate1, String idstate2, String act,
			String pNam, String pName, Long cusId);

	// ->->->->->->->->->->->->->->->->以下是工程经济模块中所使用的方法<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-//
	/**
	 * 返回一个查询字符串
	 * 
	 * @param money1
	 * @param money2
	 * @param str3
	 * @param str4
	 * @param id
	 * @param sid
	 * @param str7
	 * @param str8
	 * @param hql
	 * @return
	 * @throws ServiceException
	 */
	public String getSqlA(String hql, String money1, String money2,
			String str3, String str4, Long id, Long sid, String str7,
			String str8, String str9, String groupId) throws ServiceException;

	/**
	 * 返回一个查询字符串
	 * 
	 * @param hql
	 * @param money1
	 * @param money2
	 * @param time3
	 * @param str4
	 * @param id
	 * @param sid
	 * @param str7
	 * @param str8
	 * @return
	 * @throws ServiceException
	 */
	public String getSqlB(String hql, String money1, String money2,
			String time3, String str4, Long id, Long sid, String str7,
			String str8, String str9, String groupId) throws ServiceException;

	/**
	 * 返回一个查询字符串
	 * 
	 * @param hql
	 * @param str3
	 * @param str4
	 * @param id
	 * @param sid
	 * @param str7
	 * @param str8
	 * @param str9
	 * @return
	 * @throws ServiceException
	 */
	public String getSqlC(String hql, String str3, String str4, Long id,
			Long sid, String str7, String str8, String str9, String str10)
			throws ServiceException;

	/**
	 * 工程经济确认查询
	 * 
	 * @param list
	 * @return
	 */
	public List queryByDwr(Long id);

	public List queryVerityEconomy(List list);

	public List queryEconomyManage(List list);

	/**
	 * 用于更新工程经济状态
	 * 
	 * @param action
	 * @param sb
	 * @param updateStr1
	 * @param updateStr2
	 * @return
	 */
	public int updateProEconomyState(String action, StringBuffer sb,
			String updateStr1, String updateStr2);

	/**
	 * 
	 * @param action
	 * @param sb
	 * @return
	 */
	public int updateProEconomyState(String action, StringBuffer sb);

	/**
	 * 查询该销售员的所有客户
	 * 
	 * @param EmployeeId
	 * @return
	 */
	public List queryClients(Long exployeeId);

	/**
	 * 通过id删除某个对象
	 * 
	 * @param id
	 * @param hql
	 * @return
	 */
	public int deleteById(Long id, String hql);

	/**
	 * get name by id
	 * 
	 * @param list
	 * @return
	 */
	public List getNameById(List list);

	public List getNameById2(List list);

	/**
	 * 招标文件信息列表
	 * 
	 * @param list
	 * @return
	 */
	public List BidList(List list);

	/**
	 * 设备清单列表
	 * 
	 * @param list
	 * @return
	 */
	public List equipList(List list);

	/**
	 * 
	 * @param hql
	 * @return
	 */
	public List<YXOSectionRecord> recordList(String hql);

	// 最新修改,显示某个工程经济主体信息的所有阶段信息
	public List<YXOSectionInfo> sectionInfoList(String hql, Long id);

	// /////////////////////////////////////////////////////////////////
	// 开票签收管理
	/**
	 * 返回开票签收管理左查询的参数
	 */
	public String getSignSql( Long clientId, Long expId,
			String billNumber, Long signState);

	// add by xiaoping
	/**
	 * 如在此service中增加方法请写上svn帐户名和增加时间还有该方法的用途 新增加的方法请写在最下面！！！！！！！！！！！！！！！！！！！！ 如
	 * add by yangyuan 6.20 查询销售员返回一个列表 public List<Employee> listEmployee()
	 * 
	 *
	 */
	public List<YXClientCode> queryAllClients();
	/**
	 * 保存选择客户时候把客户添加到当前用户所关联的客户
	 * @param clientId
	 */
	public void addRelation(Long clientId);
	/**
	 * 删除发票申请金额
	 * @param amountId
	 */
	public void deleteAmount(Object obj,Long amountId);
	/**
	 * 修改密码
	 * @param pwd
	 * @param id
	 */
	public void updatePwd(String pwd,Long id);
	/**
	 * 根据ID删除对象
	 * @param obj
	 * @param id
	 */
	public void deleteObj(Class obj,Long id);
	
	/**
	 * 保存供应商修改历史
	 */
	public void recordSupplyInfoChange (  SupplierInfo si   );
	
	/**
	 * 验证供应商代码
	 */
	public boolean uniqueSupNum(String supNum);
	
}

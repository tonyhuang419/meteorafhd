package com.baoz.yx.service;


import java.util.List;
import java.util.Map;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;

public interface IForamlContractService {

	/**
	 * 按系统号获取 合同主体信息
	 */
	public ContractMainInfo loadContractMainInfo(long conSysID);

	/**
	 * 按合同系统号获取合 同项目信息
	 */
	public List<ContractItemMaininfo> loadContractItemMainInfo(long conSysID);

	/**
	 * 按项目主体信息系统号 获取 合同项目内容信息
	 */
	public List<ContractItemInfo> loadContractItemInfo(long cimiSysID);

	/**
	 * 按合同系统号 获取 合同项目阶段信息
	 */
	public List<ContractItemStage> loadContractItemStageInfo(long conSysID);

	/**
	 * 按合同系统号 获取 合同其它信息
	 */
	public ContractOtherInfo loadContractOtherInfo(long conSysID);


	/**
	 * 按合同系统号 获取 合同申购采购信息
	 */
	public List<ApplyMessage> loadContractApplyInfo(long conSysID);


	/**
	 * 按合同系统号 获取 合同实际开票和收款计划
	 */
	public List<RealContractBillandRecePlan> loadRealContractBillandRecePlan(long conSysID);

	/**
	 * 按合同系统号 获取 合同实际开票和收款计划、项目信息
	 */
	public List<Object[]> loadRCPlanAndItem (Long conSysID);

	/**
	 * 按合同系统号获取 合同开票信息
	 */
	public List<InvoiceInfo> loadInvoiceInfo(long conSysID);


	/**
	 * 按合同系统号获取 合同外协信息
	 */
	public List<Object[]> loadContractAssistanceContract(long conSysID);


	/**
	 * 按合同外协系统号获取 合同支付信息
	 */
	public List<AssistancePayInfo> loadAssistancePayInfo(long acSysID);


	/**
	 * put in a mass assistance info,push out a mass assistance pay information
	 */
	public List<List<AssistancePayInfo>> loadAssistancePayInfoList(List<Object[]> acList);


	/**
	 * 按合同系统号获取 合同自有产品信息
	 */
	public List<ContractOwnProduce> loadContractOwnProduce(long conSysID);


	/**
	 * 按合同开票、收据明细系统号获取 合同收款信息
	 */
	public List<ReveInfo> loadReceInfo(long invoiceInfoSid);


	/**
	 * 按合同一堆开票信息，获取一堆收款信息
	 */
	public List<List<ReveInfo>> loadReceInfoList (List<InvoiceInfo> conInvoiceInfoList);

	/**
	 * 获取一堆收款信息 按合同
	 */
	public List<Object[]> loadReceInfoListByCon (Long conId);

	/**
	 *   abandon method
	 * 按合同开票、收据明细系统号获取 合同发票、收款信息
	 */
//	public List<Object[]> loadBillReceInfo(long invoiceInfoSid);


	/**
	 * 按自有产品信息系统号 获取产品名称
	 */
	public String getContractOwnProduceName(long ownProductSysId);

	/**
	 * 按合同合同项目阶段号返回 阶段名称
	 */
	public String getItemStageName(long itemStageSysId);

	/**
	 * 按合同项目主体序号返回 负责部门名
	 */
	public String getResDeptName(long conItemMinfoSid);


	/**
	 * 按开票申请系统号获取开票内容
	 */
	public String getInvoiceContent(long applyInvoiceId);


	/**
	 * 按开票申请系统号获取开票性质
	 */
	public String getInvoiceNature(long applyInvoiceId);


	/**
	 * 按客户系统号获取客户名称 (简称)
	 */
	public String getCustomerName(long customerSysID);
	
	

	/**
	 * 按客户系统号获取客户名称 (全称)
	 */
	public String getCustomerFullName(long customerSysID);

	/**
	 * 按联系人代码，获取联系人名称
	 */
	public String getClientLinkManName(long linkManSysId);

	/**
	 * 按合同主体号，获取项目数
	 */
	public Long getItemNum(long conSysID);
	/**
	 * 按合同主体号，获取阶段数
	 */
	public Long getStageNum(long conSysID);

	/**
	 * 按合同状态序号，获取合同状态名
	 */
	public String covConStateSnToName(long conStateNum);


	/**
	 * 按登陆员工系统号，检索出名下所有合同的客户索引.....正式合同
	 */
	public List<Object[]> loadSaleContractCustomerIndex(Long eid);


	/**
	 * 按客户分类搜索合同，销售员过滤
	 */
	public Map<Long ,List<String[]>> loadSaleContractGroupByCustomer(Long eid);

	/**
	 * 按客户系统号，搜索合同。。。。正式合同
	 */
	public List<String[]> loadConNameByCid(Long cid);


	/**
	 * 按实际开票计划系统号 获取 合同实际开票计划
	 */
	public List<RealContractBillandRecePlan> loadRealContractBillpro(String rcplansid[]);


	/**
	 * 通过登陆id，获取Employee
	 */
	public Employee getEmployee(Long sid);


	/**
	 * 通过登陆id，获取员工名
	 */
	public String getName(Long sid);


	/**
	 * 通过登陆id，获取员工部门
	 */
	public String getDept(Long sid);


	/**
	 * 按合同主体信息系统号，获取客户系统号
	 */
	public Long getCustomerFromConSid(long consysid);



	/**
	 * 按客户系统号 获取客户
	 */
	public YXClientCode getCustomer(long customerSysID);

	/**
	 * 按合同主体系统号 获取合同名称
	 */
	public String getConName(long conMainSid);

	/**
	 * 按合同主体系统号 获取合同名称号
	 */
	public String getConSn(long conMainSid);

	/**
	 * 按实际计划，获取开票性质、票据类型、基准
	 */
	public Object[] getRCListInfo(long rcplansid);

	/**
	 * 按供应商代码，获得供应商名
	 */
	public String getSupplyName(long supplysid);

	/**
	 * 按项目系统号获取合同项目号
	 */
	public  String getItemNo(long conItemSn);


	/**
	 * 按项目系统号获取合同项目号
	 */
	public  List<String> getItemNoByConId(long conSid);

	/**
	 * 载入项目号列表，查询用
	 */
	public  Map<Long,List<String>> loadItemNoByPageInfo(PageInfo info);


	/**
	 * 按合同主体信息系统号，检查合同是否全额开票
	 * 0 未开 1存在收据 2部分未开票 3除尾款外全额开票 4全额开票
	 */
	public Long getAllTicket(long consid);

	/**
	 ** 按合同主体信息系统号，检查合同是否全额收款
	 ** 0 未收款 1部分收款  2除尾款外全额到款   3全额到款
	 */
	public Long getAllMoney(long consid);



	/**
	 * 按合同主体系统号， 合同是否存在开票申请(包含收据)
	 * type 1：包含所有票据类型
	 * type 2：不包含收据
	 */
	public boolean existApplyBill(Long consid , int type);



	/**
	 * 按合同主体系统号， 合同是否存在发票 (不是收据)
	 */
	public boolean existInvoiceF(Long consid);



	/**
	 * 按合同主体系统号，统计开票申请总额（含税金额，不包括收据）
	 */
	public Double statApplyBillAmount(Long consid);



	/**
	 * 按合同主体系统号，统计发票总额
	 * type 1：包含所有票据类型
	 * type 2：不包含收据
	 */
	public Double stateInvoiceAmount(Long consid , int type);

	/**
	 * 按合同主体系统号，是否全额开票（不包括收据）
	 * type 1：包含所有票据类型
	 * type 2：不包含收据
	 */
	public boolean allInvoice(Long consid , int type);


	/**
	 * 按合同主体系统号，统计到款总额
	 */
	public Double stateReceAmount(Long consid);


	/**
	 * 按合同主体系统号，是否全额收款
	 */
	public boolean allRece(Long consid);



	/**
	 * 按合同主体系统号，获取合同总金额（含税价）
	 */
	public Double getConTaxAmout(Long consid);


	/**
	 * 按合同主体系统号，获取"收据"标识
	 */
	public String getReciptSn();



	/**
	 * 按系统号，获取发票List，不是收据
	 */
	public List<InvoiceInfo> getInvoiceInfoList(Long consid);

	/**
	 * 按合同主体系统号，获取开票申请List
	 * type 1：包含所有票据类型
	 * type 2：不包含收据 
	 */
	public List<ApplyBill> getApplyBillList(Long consid ,int sign);


	/**
	 * 按合同主体系统号，是否存在剩余收据
	 */
	public boolean existReceipt(Long consid);



	/**
	 * 按合同主体系统号， 合同是否存在收据
	 */
	public boolean existInvoice(Long consid);


	/**
	 * 按合同主体系统号， 找到合同所有外协
	 */
	public List<AssistanceContract> getAllAssistance(Long consid);


	/**
	 * 按合同主体系统号， 合同外协是否全部结束
	 */
	public boolean allAssistanceOver(Long consid);

	/**
	 * 按合同主体系统号，搜合同所有收据
	 */
	public List<InvoiceInfo>  getReceiptInfoList(Long consid);


	/**
	 * 按合同主体系统号，修改合同状态
	 */
	public  void  modifyConState(Long consid, Long conState);



	/**
	 * 更新合同主体信息
	 */
	public  void updateConMainInfo(ContractMainInfo cmi);



	/**
	 * 翻译开票状态
	 */
	public String tranBillStateToName(int billState);


	/**
	 * 按合同主体系统号，返回合同开票状态
	 */
	public String getContractBillStateName(Long consid);


	/**
	 * 按合同主体系统号，返回合同收款状态
	 */
	public String getContractReceStateName(Long consid);
	
	/**
	 * 按合同主体系统号，返回外协状态
	 */
	public String getOutSoureStatName(Long consid);


	/**
	 * 翻译收款状态
	 */
	public String tranReceStateToName(int receState);


	/**
	 * 开票申请已签、未签（开票申请系统号）
	 * 2008年7月14日14:34:53
	 */
	public boolean applyHasNoCon(Long applyBillSid);


	/**
	 * 计划是否同一个合同
	 * 2008年7月14日14:34:53
	 */
	public boolean isOneContract(String[] rcbrps);




//	/**
//	* 按合同主体系统号，查找是否需要工程经济
//	* 001 开工报告
//	* 010 实物交接
//	* 100 竣工验收
//	*/
//	public String needPE(Long consid);


//	/**
//	* 按合同主体系统号，返回工程经济是否全部完成
//	*/
//	public boolean allPEOver(Long consid);

	/**
	 * 按合同主体系统号，返回项目成本确认
	 */
	public boolean allItemCostSure(Long consid);

	/**
	 * 按项目负责人系统号，返回项目负责人
	 */
	public String getChargeManName(Long id);


	/**
	 * 按客户系统号，搜索合同。。。。正式合同..还有自己的
	 * uid....没有用，通过权限自动获取
	 */
	public List<String[]> loadConNameByCidandUid(Long cid,Long uid);


	/**
	 * 按合同主体系统号 count一个实体有没有数据。。。
	 * 实体名。。。字段名。。。。号（Long）
	 */
	public boolean hasData(String entityName, String sidColumnName , Long conSid);



	/**
	 * 按实际合同开票收款计划系统号。。获取计划实例
	 */
	public RealContractBillandRecePlan getRCPlan(String rcpalnSid);


	/**
	 * 按实际合同开票收款计划系统号。。计划系统号，金额
	 */
	public Map<Long, Double> getRcpalnList(String[] rcpalnSid);


	/**
	 * 按计划系统号，返回是否已提交申请。。不包括收据
	 */
	public boolean hasApply(Long rcpalnSid);


	/**
	 * 遍历收款信息，获取收款总额
	 */
	public Double getReceAmount(List<List<ReveInfo>> llr);


	/**
	 * 通过合同号，获得到款总额
	 */
	public Double getReceAmountFromConsid( Long conSid ); 


	/**
	 * 通过实际计划号，返回计划余额。。。未收款金额...收据不带
	 */
	public Double getRemainAmountByRealPro( Long realConBillproSid );
	


	/**
	 * 查找getAllFormalContract返回的合同
	 * 合同主体系统号，返回和否能够建议关闭
	 */
	public void doSuggustClose( ); 

	/**
	 * 检查是否能设置合同状态为建议关闭——8
	 * if can , he will do it
	 */
	public void modConState( Long conSid, Long conState ); 

	/**
	 * getAllFormalContract  >3 && <8
	 */
	public List<ContractMainInfo> getAllFormalContract( ); 
	
	/**
	 * 获取项目部门和金额from合同主体号
	 */
	public List<Object[]> getItemAmountInfo(Long conMainSid); 
	
	/**
	 * 获取项目部门和金额from合同主体号(变更表)
	 */
	public List<Object[]> getChangeItemAmountInfo(Long conMainSid); 
	
	/**
	 * 获取客户通过id
	 */
	public YXClientCode getYXClientCodeByCid(Long cid); 
	
	/**
	 * 获取销售员通过组别
	 */
	public List<Employee> getSaleManByGroupId(String gid); 
	


	/**
	 * 按条件返回合同主体信息
	 */
	public PageInfo queryContractMainInfo(
//			BigDecimal max_amount,
//			BigDecimal min_amount,
			String start_date,
			String end_date,
			int conStateSn,
			Long saleId, 
			Long customerId, 
			String groupId,
			String  conType,
			String conSn,
			String conName,
			String finalAccount,
			String contractType,
			String partyAProId,
			String partyAConId,
			String deliveryStartDate,
			String deliveryEndDate,
			Double maxAmount,
			Double minAmount,
			String hasClosed,
			String itemSn,
			PageInfo info );


	/*	获取页面统计值................
	 *  1 sumContractSum    2  billContractSum   3 receiptContractSum  4  reveContractSum
	 */
	public Double getStatSum(   
			String start_date,
			String end_date,
			int conStateSn,
			Long saleId, 
			Long customerId, 
			String groupId,
			String conType,
			String conSn,
			String conName,
			String finalAccount,
			String contractType,
			String partyAProId,
			String partyAConId,
			String deliveryStartDate,
			String deliveryEndDate,
			Double maxAmount,
			Double minAmount,
			String hasClosed,
			String itemSn,
			int sign );




	/***
	 * 通过合同id查询变更合同的详细信息
	 * @param id
	 * @return
	 */
	public ChangingContractMainInfo getChangingContractMainInfoById(Long id);

	/**
	 * 获得需要计算帐龄的开票收款计划
	 * @return
	 */
	public List<RealContractBillandRecePlan> getToBeUpdatedAccountAgePlan();
	/**
	 * 更新开票收款计划的帐龄
	 * @param plan
	 */
	public void updateAccountAge(RealContractBillandRecePlan plan);


	/**
	 * 通过登陆id，获取OrganizationCode
	 */
	public String getOrganizationCodeByEid(Long eid);
	
	/**
	 * 验证合同号
	 */
	public boolean uniqueConNum(String conNum);
}

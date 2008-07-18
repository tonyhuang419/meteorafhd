package com.baoz.yx.service;

import java.math.BigDecimal;
import java.util.List;

import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.vo.MyBean;


public  interface IContractService {
	
	/**
	 *  保存合同信息
	 * @param list
	 * @return
	 */
	public void SaveContract(List list);
	

	/**
	 *  写入合同主体信息并创建项目和阶段对象且返回合同主体对象
	 * @param 
	 * @return
	 */
	public ContractMainInfo saveContractMainInfo(ContractMainInfo c);
	
	/**
	 *  按系统号获取合同主体信息
	 * @param 
	 * @return
	 */	
	public ContractMainInfo loadContractMainInfo(long conID);
	

	/**
	 *  查询项目客户 
	 * @param 
	 * @return list<YXClientCode> 
	 */
	public List<YXClientCode> findEventClient(Long id);


	/**
	 *  查开票客户 
	 * @param 
	 * @return list<YXClientCode> 
	 */
	public List<YXClientCode> findAllClient(Long id);
	
	/**
	 *  查客户 
	 * @param 
	 * @return list<YXClientCode> 
	 */
	public List<YXClientCode> findClient(Long id);
	
	
	/**
	 *  根据联系人主键号查询联系人
	 * @param 
	 * @return 
	 */
	public List<YXLinkMan> findlinkMan(Long id);


	/**
	 *  根据主合同号查询合同项目
	 * @param 
	 * @return list<YXContractItemMaininfo> 
	 */
	public List<ContractItemMaininfo> findEventByMainInfoId(Long conMainInfoSid);
	/**
	 * 根据项目系统号查询主项目
	 * @param conMainItemSid
	 * @return
	 */
	public ContractItemMaininfo getContractItemMaininfo(Long conMainItemSid);
		
	
	/**
	 * 更新合同状态
	 * @param ids 合同主键
	 * @param conState 合同状态值
	 */
	public void updateContractMainState(Long[]ids,Long conState);
	
	/**
	 *  根据主合同号查询合同阶段
	 * @param 
	 * @return list<YXContractItemStage> 
	 */
	public List<ContractItemStage> findItemStageByMainInfoId(Long conMainInfoSid);
	
	/**
	 *  根据主合同号查询合同阶段计划
	 * @param 
	 * @return list<ContractItemStagePlan> 
	 */
	public List<ContractItemStagePlan> findItemStagePlanByMainInfoId(Long conMainInfoSid);

	/**
	 *  保存&更新合同阶段信息
	 * @param 
	 *  
	 */
	public void saveContractStage(List<ContractItemStage> contractItemStage);
	/**
	 *  保存合同阶段计划信息
	 * @param 
	 *  
	 */
	public void saveContractStagePlan(ContractItemStagePlan contractItemStagePlan);
	/**
	 *  同一个费用组成里，阶段是否重复
	 * @param 
	 *  
	 */
	public boolean isStageOfStagePlanDuplicate(ContractItemStagePlan contractItemStagePlan);
	/**
	 *  更新合同阶段计划信息
	 * @param 
	 *  
	 */
	public void updateContractStagePlan(ContractItemStagePlan contractItemStagePlan);
	/**
	 *  删除合同阶段计划信息
	 * @param 
	 *  
	 */
	public void deleteContractStagePlan(ContractItemStagePlan contractItemStagePlan);
	
	/**
	 * 获得一个费用组成下面的阶段计划总金额
	 * @param partId
	 * @return
	 */
	public Double getStagePlanAmountOfContractPart(Long partId);
	/**
	 *  更新合同项目信息
	 * @param 
	 *  
	 */
	public void saveContractEvent(List<ContractItemMaininfo> iteminfolist);
	/**
	 *  更新单个合同项目信息
	 * @param 
	 *  
	 */
	public void updateContractMainItem(ContractItemMaininfo item);
	/**
	 * 更新合同项目号
	 * @param iteminfolist
	 */
	public void updateContractAndConItemId(List<MyBean> list);
	/**
	 *  根据项目号，金额，金额来添加项目组成
	 * @param id,
	 *  
	 */
	public void saveEventInfo(Long id,String itemcontrent,BigDecimal money);
	
	/**
	 *  根据主键号，删除项目组成
	 * @param id,
	 *  
	 */
	public void removeEventInfo(Long id);
	
	public int accountDepartNum(Long mainid);
	
	public int accountStageNum(Long mainid);
	
	/**
	 *  根据主合同号，查找开票收款计划
	 * @param id,
	 *  
	 */
	public List<InitContractBillpro> findPlanlist(Long mainid);

	/**
	 *  拆分计划
	 * @param splititemNum  需拆分的项目号
	 * @param splitstageNum 需拆分的阶段号
	 * @param mainid 需拆分的主体合同号
	 *  
	 */
	public void splitPlan(Long splititemNum,Long splitstageNum,Long mainid);
	
	/**
	 *  根据项目主键号查询负责部门
	 * @param itemid  项目主键号
	 * @return 负责部门名称
	 *  
	 */
	public String findDepName(Long itemid);

	/**
	 *  根据阶段主键号查询阶段名称
	 * @param stageid  阶段主键号
	 * @return 阶段名称
	 *  
	 */
    public String findStageName(Long stageid);
    
	/**
	 *  保存其他信息
	 * @param otherinfo  其他信息对象
	 * 
	 *  
	 */
    public void saveOtherInfo(ContractOtherInfo otherinfo);
    
    /**
	 *  查询合同其他信息
	 * @param mainid  根据主体合同号查询其他信息
	 * 
	 *  
	 */
    public ContractOtherInfo findOtherInfo(Long mainid);


	public void savePlanInfo(List<InitContractBillpro> planlist);

    /**
	 *  查询合同自有产品信息
	 * @param mainid  根据主体合同号查询自有产品信息
	 * 
	 *  
	 */
	public List<ContractOwnProduce> findselfProductByMainid(Long mainid);

	 /**
	 *  创建合同合同自有产品
	 * @param mainid  主合同号
	 * @param selfproduceid 自有产品号
	 *  
	 */
	public void conProduct(Long mainid, Long selfproduceid);

	 /**
	 *  删除合同自有产品
	 * @param delselfproduct 合同自有产品表的系统号
	 * 
	 *  
	 */
	public void delConSelfProduct(Long delselfproduct);

	 /**
	 *  保存合同自有产品
	 * @param ContractOwnProduce 合同自有产品对象
	 * 
	 *  
	 */
	public void saveConSelfProductInfo(List<ContractOwnProduce> contractOwnProduce);
	
	 /**
	 *  根据自有产品系统号查询产品名称
	 * @param selfproductid
	 * @return productName
	 *  
	 */
	public String findSelfProductNameById(Long selfproductid);


	 /**
	 *  根据主合同号查询关联发票
	 * @param mainid
	 * @return
	 *  
	 */
	public List<ApplyBill> findInviceByMainid(Long mainid);

	 /**
	 *  根据主合同号查询关联申购
	 * @param mainid
	 * @return
	 *  
	 */
	public List<ApplyMessage> findPurchaseByMainid(Long mainid);


	 /**
	 *  根据申购系统号删除关联
	 * @param id
	 * @return
	 *  
	 */
	public void removeConPurchase(Long delInvoice);

	 /**
	 *  根据开票申请系统号删除关联
	 * @param id
	 * @return
	 *  
	 */
	public void removeConInvoice(Long delInvoice);


	public void conPur(Long selectid, Long mainid);

	 /**
	 *  关联开票
	 * @param mainid主合同号 selectid要关联的发票系统号
	 * @return
	 *  
	 */
	public void conInvoice(Long mainid, Long selectid);
	
	public String getYXClientCode(Long mainid);

	 /**
	 *  更新合同状态为待确认
	 * @param mainid主合同号 
	 * @return
	 *  
	 */
	public void sureSubmit(Long mainid);


	public void delContract(Long mainid);
	
	/**
	 * 确认通过生成正式合同号
	 * @param mainid主合同号 
	 * @return 
	 *  
	 */
	public void changeToFormalContract(Long[] conMainId);
	/**
	 * 在主健某一区间内查询合同
	 * @param conMainId
	 */
	public List<ContractMainInfo> findContractByState(Long[] conMainId);
	/**
	 * 
	 * @param contracts
	 */
	public void changeState(List<ContractMainInfo>contracts,Long state);

	/**
	 * 
	 * @param ids主健
	 * @param states状态值列表
	 * @param state新的状态
	 */

	public void saveContrac(ContractMainInfo info);



	public void conCustom(Long id, Long conCustomer);
	
	/**
	 * 传入主合同号，将草稿合同的开票收款计划转存至实际合同开票收款计划表
	 * @param mainid
	 */
	public void copysked(Long mainid);

	/**
	 * 传入主合同号，将实际开票收款计划表清空
	 * @param mainid
	 */
	public void delrealBillpro(Long mainid);
	/**
	 * 转正式合同
	 *
	 */
	public void changeZSOneComain(Long mian);
	public void chageZSTwoComain(Long mian,String resNumber,ContractMainInfo comain);
	public void concelCirform(List view);


	public List<ContractMaininfoPart> findMainMoneyList(Long mainid);

	/**
	 * 获得设备金额和已经添加的阶段计划金额
	 * @param mainid
	 * @return
	 */
	public List<Object[]> findMainMoneyWithPlanAmountList(Long mainid);

	/**
	 * 保存合同金额组成
	 * @param mainmoneytype
	 * @param mainmoney
	 * @param mainid
	 */
	public void saveMainMoneyPart(String mainmoneytype, Double mainmoney,
			Long mainid,String ticketType);


	public void delContractMainPart(Long delmainpartid);


	public String findContractType(Long mainid);
	
	/**
	 * 根据主合同号，项目号，金额类型，项目金额来添加开票收款计划
	 * @param mainid
	 * @param itemid
	 * @param moneytype
	 * @param money
	 */
	public void GeneratePlanByEventId(Long mainid,Long itemid,String moneytype,Double money);

	/**
	 * 根据主合同号，阶段号，金额类型，阶段金额来添加开票收款计划
	 * @param mainid
	 * @param stageid
	 * @param moneytype
	 * @param money
	 */
	public void GeneratePlanByStageId(Long mainid,Long stageid,String moneytype,Double money);
	
	/**
	 * 根据主合同号，项目号，金额类型来删除开票收款
	 * @param mainid
	 * @param itemid
	 * @param moneytype
	 * @param money
	 */
	public void DelPlanByEventId(Long mainid,Long itemid,String moneytype);
	
	/**
	 * 根据主合同号，阶段号，金额类型来删除开票收款计划
	 * @param mainid
	 * @param stageid
	 * @param moneytype
	 */
	public void DelPlanByStageId(Long mainid,Long stageid,String moneytype);
	
	/**
	 * 通过项目费用id删除费用信息，并且判断项目中是否还有费用，如果没有费用把项目也删掉
	 * @param conItemInfoSid
	 */
	public void deleteEventInfo(Long conItemInfoSid);
}

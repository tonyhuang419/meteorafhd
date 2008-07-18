package com.baoz.yx.service;

import java.math.BigDecimal;
import java.util.List;

import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;

/**
 * @author Administrator
 *
 */
public interface IFormalContractModifyService {

	/**
	 *  查询项目客户 
	 * @param 
	 * @return list<YXClientCode> 
	 */
	public List<YXClientCode> findEventClient(Long id);
	
	/**
	 *  查客户 
	 * @param 
	 * @return list<YXClientCode> 
	 */
	public List<YXClientCode> findClient(Long id);

	/**
	 *  查开票客户 
	 * @param 
	 * @return list<YXClientCode> 
	 */
	public List<YXClientCode> findAllClient(Long id);
	
	
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
	 *  根据主合同号查询合同阶段
	 * @param 
	 * @return list<YXContractItemStage> 
	 */
	public List<ContractItemStage> findItemStageByMainInfoId(Long conMainInfoSid);
	
	/**
	 *  保存合同变更主信息
	 * @param 
	 * @return
	 */
	public ChangingContractMainInfo saveContractMainInfo(ChangingContractMainInfo c,int departNum,int stageNum);
	
	/**
	 *  更新合同项目信息
	 * @param 
	 *  
	 */
	public void saveContractEvent(List<ContractItemMaininfo> iteminfolist);
	
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
	
	/**
	 *  保存&更新合同阶段信息
	 * @param 
	 *  
	 */
	public void saveContractStage(List<ContractItemStage> contractItemStage);
	
	/**
	 * 保存开票收款计划
	 * @param planlist
	 */
	public void savePlanInfo(List<InitContractBillpro> planlist);
	
	/**
	 *  拆分计划
	 * @param splititemNum  需拆分的项目号
	 * @param splitstageNum 需拆分的阶段号
	 * @param mainid 需拆分的主体合同号
	 *  
	 */
	public void splitPlan(Long splititemNum,Long splitstageNum,Long mainid);
	
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
	
	 /**
	 *  保存合同自有产品
	 * @param ContractOwnProduce 合同自有产品对象
	 * 
	 *  
	 */
	public void saveConSelfProductInfo(List<ContractOwnProduce> contractOwnProduce);
	
	 /**
	 *  删除合同自有产品
	 * @param delselfproduct 合同自有产品表的系统号
	 * 
	 *  
	 */
	public void delConSelfProduct(Long delselfproduct);
	
	/**
	 *  保存其他信息
	 * @param otherinfo  其他信息对象
	 * 
	 *  
	 */
    public void saveOtherInfo(ContractOtherInfo otherinfo);
    
	/**
	 *  根据主合同号，查找开票收款计划
	 * @param id,
	 *  
	 */
	public List<RealContractBillandRecePlan> findPlanlist(Long mainid);
	
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
	 *  更新合同状态为待确认
	 * @param mainid主合同号 
	 * @return
	 *  
	 */
	public void sureSubmit(Long mainid);
	
	public void delContract(Long mainid);
	
    /**
	 *  查询合同自有产品信息
	 * @param mainid  根据主体合同号查询自有产品信息
	 * 
	 *  
	 */
	public List<ContractOwnProduce> findselfProductByMainid(Long mainid);
	
    /**
	 *  查询合同其他信息
	 * @param mainid  根据主体合同号查询其他信息
	 * 
	 *  
	 */
    public ContractOtherInfo findOtherInfo(Long mainid);
    
	public void conCustom(Long id, Long conCustomer);

	/**
	 * 根据ID号备份合同信息，并返回备份后的合同对象
	 * @param mainid
	 * @return
	 */
	public ChangingContractMainInfo loadandcopyContractMainInfo(Long mainid);

    /**
     * 根据合同ID号查询备份的合同主信息
     * @param mainid
     * @return
     */
	public ChangingContractMainInfo  loadContractMainInfo(Long mainid);
	
	
	public String getYXClientCode(Long mainid);

	public void saveContractChangeExplain(String changeExplain,Long mainid,Long personid);

	public List<ContractMaininfoPart> findMainMoneyList(Long mainid);
	
}
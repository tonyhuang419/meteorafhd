package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.contract.ChangeRealContractBillandRecePlan;
import com.baoz.yx.entity.contract.ChangingContractItemInfo;
import com.baoz.yx.entity.contract.ChangingContractItemMaininfo;
import com.baoz.yx.entity.contract.ChangingContractItemStagePlan;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;

/**
 * 合同变更的service操作，目前是从结算转决算中copy过来的
 * @author xusheng
 *
 */
public interface IAlterationContractService {

	/**
	 * 如果合同没有变更信息，需要把实际表中的信息copy到变更表中
	 * @param mainid
	 * @return
	 */
	public ChangingContractMainInfo loadandcopyContractMainInfotoclose(Long mainid);
	
	/**
	 * 保存页面上的值到合同变更信息表中
	 * @param maininfo
	 * @return
	 */
	public ChangingContractMainInfo saveContractInfo(ChangingContractMainInfo maininfo);
	
	public void saveContractMaininfoPart(
			List<ChangingContractMaininfoPart> mainInfoPartList,Long mainid);
	
	/***
	 * 修改合同变更信息，1，为待确认，2为退回
	 * @param mainId
	 * @param state
	 */
	public ChangingContractMainInfo applySubmit(Long mainId,String state);
	
	/**
	 * 保存开票收款计划
	 * @param planlist
	 */
	public void savePlanInfo(List<ChangeRealContractBillandRecePlan> planlist,Long mainid);
	
	public void saveStagePlan(List<ChangingContractItemStagePlan> itemStagePlan);
	
	/**
	 * 通过项目组成的list修改项目费用
	 * @param itemInfoList
	 */
	public void saveItemMainInfo(List<ChangingContractItemInfo> itemInfoList);
	
	
	public void addChangeRealPlan(Long mainId,ChangeRealContractBillandRecePlan changeRealPlan,Long partId);
	

	/**
	 * 合同变更确认通过操作,里面主要做了四步操作：
	 * step1、备份原始表数据，
	 * step2、把change表中的数据回写到原始表中去，
	 * step3、删除原始表中等于0的数据，（这是一个相关删除，因此比较谨慎，所以需要后面的验证），
	 * step4、验证删除后的信息是否合理
	 * @param ids
	 * @return
	 */
	public Integer confirmOkChangeMain(Long ids);
	
	/**
	 *  合同变更确认退回操作
	 * @param ids
	 */
	public void confirmNoChangeMain(Long[] ids);
	
	/**
	 * 合同变更添加项目
	 * @param contractItemMaininfo
	 * @param contractItemInfo
	 */
	public void AddEventInfo(ChangingContractItemMaininfo contractItemMaininfo,ChangingContractItemInfo contractItemInfo);
	
	
	public void addStageInfo(ChangingContractItemStagePlan citemStagePlan);
	
	
	public void addMainInfoPart(ChangingContractMaininfoPart changMainInfoPart);
	
	public Double accountnoTaxMoney(Long mainid);
	
	public Double accountTaxMoney(Long mainid);
	
	
	public int returnIsSureSubmit(Long mainid);
	
}

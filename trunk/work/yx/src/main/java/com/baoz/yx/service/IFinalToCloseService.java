package com.baoz.yx.service;

import java.util.List;
import java.util.Map;

import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ChangeRealContractBillandRecePlan;
import com.baoz.yx.entity.contract.ChangingContractItemInfo;
import com.baoz.yx.entity.contract.ChangingContractItemStagePlan;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.vo.MyBean;

/**
 * 结算转决算这一模块的service
 * @author xusheng
 *
 */
public interface IFinalToCloseService {

	/**
	 * 通过合同id查询出合同费用组成信息，项目组成信息和项目信息
	 * @param conId
	 * @return
	 */
	public List<ChangingContractMaininfoPart> getMainInfoPartByConId(Long conId);
	
	/**
	 * 通过项目组成的list修改项目费用
	 * @param itemInfoList
	 */
	public void saveItemMainInfo(List<ChangingContractItemInfo> itemInfoList);
	
	
	public List<Object[]> findMainMoneyWithPlanAmountList(Long mainid);
	
	public List<ChangingContractItemStagePlan> findItemStagePlanByMainInfoId(Long mainid);
	
	public void saveStagePlan(List<ChangingContractItemStagePlan> itemStagePlan);
	
	
	public Map<Long, ChangingContractItemInfo> getItemInfoMapByConId(Long mainid);
	
	public Map<Long, ChangingContractItemStagePlan> getItemStagePlanMapByConId(Long mainid);
	
	/**
	 * 验证是否能提交结算转决算
	 * @param mainid
	 * @return
	 */
	public int isSureSubmit(Long mainid);

	/**
	 * 通过传入的合同id和费用组成id查询出同一费用组成下面的项目名称和项目id(操作的是变更表)
	 * @param mainid
	 * @param partId
	 * @return
	 */
	public List<MyBean> getItemByConAndPart(Long mainid,Long partId);
	
	/**
	 * 通过传入的合同id和费用组成id查询出同一费用组成西面的阶段的名称和阶段id,
	 * 如果是集成类合同查询的是项目主负责部门的名称和id（操作的是变更表）
	 * @param mainid
	 * @param partId
	 * @return
	 */
	public List<MyBean> getItemStageByConAndPart(Long mainid,Long partId);
	
	public void addChangeRealPlan(Long mainId,ChangeRealContractBillandRecePlan changeRealPlan,Long partId);
	
	/**
	 * 查询出在实际表中不存在，在变更表中存在的实际开票收款记录
	 * @param mainid
	 * @return
	 */
	public List<ChangeRealContractBillandRecePlan> getChangePlanNotExistsReal(Long mainid);
	
	/**
	 *  查询出在实际表中不存在，在变更表中存在的项目和项目组成记录
	 * @param mainid
	 * @return
	 */
	public List<ChangingContractItemInfo> getchangeItemInfoNotExistsReal(Long mainid);
	
	
	public List<ChangingContractItemStagePlan> getChangeStagePlanNotExistsReal(Long mainid);
	
	public String findChangeDepName(Long itemid) ;

	public String findChangeStageName(Long stageid);
	
	public String findChangeDepNameByMainid(Long mainid);
	
	public List<ChangingContractMaininfoPart> findMainMoneyNotExistsReal(Long mainid);
	
	public List<ChangingContractMaininfoPart> getMainInfoPartNotExistsReal(Long mainid);

}

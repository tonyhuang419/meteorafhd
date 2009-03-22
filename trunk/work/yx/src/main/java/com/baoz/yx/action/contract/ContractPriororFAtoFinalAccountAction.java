package com.baoz.yx.action.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.ChangeRealContractBillandRecePlan;
import com.baoz.yx.entity.contract.ChangingContractItemInfo;
import com.baoz.yx.entity.contract.ChangingContractItemStagePlan;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.IFinalToCloseService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;


@Results( {
	@Result(name = "showInfo", value = "/WEB-INF/jsp/contract/contractPriororFAtoFinalAccount.jsp")
})
public class ContractPriororFAtoFinalAccountAction  extends DispatchAction{

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;
	
	@Autowired
	@Qualifier("finalToCloseService")
	private IFinalToCloseService finalToCloseService;

	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventInfoService;			//项目信息操作的service


	private long cmisysid = -1;      				 //合同主体信息系统号
	private String contractBillState = null;  				//合同开票状态
	private String contractReceState = null;  				//合同收款状态


	private ContractMainInfo cmi =  null;                          //合同主体信息

	private List<ContractMaininfoPart> mainMoneyList= null;      // 合同金额组成列表
	
	private List<ChangingContractMaininfoPart> changeMainMoneyList;//在变更表中存在在实际表中不存在的费用组成信息
	
	private List<ContractMaininfoPart> mainInfoPartList= null;  //合同费用列表
	
	private List<ChangingContractMaininfoPart> changeMainInfoPartList ; //在变更表中存在在实际表中不存在的费用组成信息
	
	private List<ContractItemMaininfo> iteminfolist = null;  	// 项目信息列表（维护项Iteminfo对象）
	private List<Object[]> mainMoneyWithPlanAmountList = null;  // 合同金额组成和阶段金额列表
	private List<ContractItemStagePlan> stagePlanlist = null;    	// 阶段计划信息列表
	private List<RealContractBillandRecePlan> rcbrpList = null;   //实际开票和收款计划列表

	private List<ChangeRealContractBillandRecePlan> crcbpList;//添加的开票和收款计划列表
	
	private List<ChangingContractItemInfo> changeItemInfoList;//添加的项目费用组成列表
	
	private List<ChangingContractItemStagePlan> changeStagePlanList;//添加的阶段计划组成列表
	
	private ChangingContractMainInfo changingContractMainInfo;//合同主体变更信息
	
	private Map<Long, ChangingContractMaininfoPart> mainInfoPartMap=new HashMap<Long, ChangingContractMaininfoPart>();//费用组成
	
	private Map<Long, ChangeRealContractBillandRecePlan> planMap=new HashMap<Long, ChangeRealContractBillandRecePlan>();

	private Map<Long,ChangingContractItemInfo> itemInfoMap=new HashMap<Long, ChangingContractItemInfo>();//项目费用组成map
	
	private Map<Long, ChangingContractItemStagePlan> itemStagePlanMap=new HashMap<Long, ChangingContractItemStagePlan>();
	
	private Long opInterface;//1表示从结算转决算进去。2表示从结算确认进去,3表示从合同变更确认进去
	//用来保留搜索条件的干活
	private String contractName; 
	private String contractNo;
	private String ContractType;
	
	private String changeExplain;//变更说明
		
	public String getChangeExplain() {
		return changeExplain;
	}


	public void setChangeExplain(String changeExplain) {
		this.changeExplain = changeExplain;
	}


	public String doDefault() throws Exception {	
		this.loadContractInfo();
		return "showInfo";
	}


	//载入合同信息
	public void loadContractInfo(){
		
		//first tag 载入合同主体信息
		cmi = foramlContractService.loadContractMainInfo(cmisysid);
		//载入变更后的合同主体信息
		changingContractMainInfo=foramlContractService.getChangingContractMainInfoById(cmisysid);
		//first,four tag 载入费用信息
		mainMoneyList = contractservice.findMainMoneyList(cmisysid);
		
		changeMainMoneyList = finalToCloseService.findMainMoneyNotExistsReal(cmisysid);
		mainInfoPartMap=contractservice.getMainInfoPartById(cmisysid);
		//second tag 通过合同id查找合同费用
		mainInfoPartList = eventInfoService.getMainInfoPartByConId(cmisysid);
		
		changeMainInfoPartList = finalToCloseService.getMainInfoPartNotExistsReal(cmisysid);
		//second tag 通过合同id查找项目信息
		iteminfolist = contractservice.findEventByMainInfoId(cmisysid);
		itemInfoMap = finalToCloseService.getItemInfoMapByConId(cmisysid);
		changeItemInfoList  =finalToCloseService.getchangeItemInfoNotExistsReal(cmisysid);
		//third tag 载入阶段金额信息
		mainMoneyWithPlanAmountList = contractservice.findMainMoneyWithPlanAmountList(cmisysid);
		if(changingContractMainInfo!=null){
			changeExplain =changingContractMainInfo.getChangeExplain();
		} 
		//third tag 载入阶段信息
		stagePlanlist = contractservice.findItemStagePlanByMainInfoId(cmisysid);
		
		itemStagePlanMap = finalToCloseService.getItemStagePlanMapByConId(cmisysid);
		
		changeStagePlanList = finalToCloseService.getChangeStagePlanNotExistsReal(cmisysid);
		
		//four tag 通过合同id获取实际开票和收款计划列表
		rcbrpList = foramlContractService.loadRealContractBillandRecePlan(cmisysid);
		planMap=contractservice.getPlanById(cmisysid);
		crcbpList = finalToCloseService.getChangePlanNotExistsReal(cmisysid);
		
		contractBillState = foramlContractService.getContractBillStateName(cmisysid);
		contractReceState = foramlContractService.getContractReceStateName(cmisysid);
		
	}


	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}


	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}


	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}


	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}


	public IContractService getContractservice() {
		return contractservice;
	}


	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}


	public IEventInfoService getEventInfoService() {
		return eventInfoService;
	}


	public void setEventInfoService(IEventInfoService eventInfoService) {
		this.eventInfoService = eventInfoService;
	}


	public long getCmisysid() {
		return cmisysid;
	}


	public void setCmisysid(long cmisysid) {
		this.cmisysid = cmisysid;
	}


	public String getContractBillState() {
		return contractBillState;
	}


	public void setContractBillState(String contractBillState) {
		this.contractBillState = contractBillState;
	}


	public String getContractReceState() {
		return contractReceState;
	}


	public void setContractReceState(String contractReceState) {
		this.contractReceState = contractReceState;
	}


	public ContractMainInfo getCmi() {
		return cmi;
	}


	public void setCmi(ContractMainInfo cmi) {
		this.cmi = cmi;
	}


	public List<ContractMaininfoPart> getMainMoneyList() {
		return mainMoneyList;
	}


	public void setMainMoneyList(List<ContractMaininfoPart> mainMoneyList) {
		this.mainMoneyList = mainMoneyList;
	}


	public List<ContractMaininfoPart> getMainInfoPartList() {
		return mainInfoPartList;
	}


	public void setMainInfoPartList(List<ContractMaininfoPart> mainInfoPartList) {
		this.mainInfoPartList = mainInfoPartList;
	}


	public List<ContractItemMaininfo> getIteminfolist() {
		return iteminfolist;
	}


	public void setIteminfolist(List<ContractItemMaininfo> iteminfolist) {
		this.iteminfolist = iteminfolist;
	}


	public List<Object[]> getMainMoneyWithPlanAmountList() {
		return mainMoneyWithPlanAmountList;
	}


	public void setMainMoneyWithPlanAmountList(
			List<Object[]> mainMoneyWithPlanAmountList) {
		this.mainMoneyWithPlanAmountList = mainMoneyWithPlanAmountList;
	}


	public List<ContractItemStagePlan> getStagePlanlist() {
		return stagePlanlist;
	}


	public void setStagePlanlist(List<ContractItemStagePlan> stagePlanlist) {
		this.stagePlanlist = stagePlanlist;
	}


	public List<RealContractBillandRecePlan> getRcbrpList() {
		return rcbrpList;
	}


	public void setRcbrpList(List<RealContractBillandRecePlan> rcbrpList) {
		this.rcbrpList = rcbrpList;
	}


	public String getContractName() {
		return contractName;
	}


	public void setContractName(String contractName) {
		this.contractName = contractName;
	}


	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getContractType() {
		return ContractType;
	}


	public void setContractType(String contractType) {
		ContractType = contractType;
	}


	public ChangingContractMainInfo getChangingContractMainInfo() {
		return changingContractMainInfo;
	}


	public void setChangingContractMainInfo(
			ChangingContractMainInfo changingContractMainInfo) {
		this.changingContractMainInfo = changingContractMainInfo;
	}


	public Map<Long, ChangingContractMaininfoPart> getMainInfoPartMap() {
		return mainInfoPartMap;
	}


	public void setMainInfoPartMap(
			Map<Long, ChangingContractMaininfoPart> mainInfoPartMap) {
		this.mainInfoPartMap = mainInfoPartMap;
	}


	public Map<Long, ChangeRealContractBillandRecePlan> getPlanMap() {
		return planMap;
	}


	public void setPlanMap(Map<Long, ChangeRealContractBillandRecePlan> planMap) {
		this.planMap = planMap;
	}


	public IFinalToCloseService getFinalToCloseService() {
		return finalToCloseService;
	}


	public void setFinalToCloseService(IFinalToCloseService finalToCloseService) {
		this.finalToCloseService = finalToCloseService;
	}


	public Map<Long, ChangingContractItemInfo> getItemInfoMap() {
		return itemInfoMap;
	}


	public void setItemInfoMap(Map<Long, ChangingContractItemInfo> itemInfoMap) {
		this.itemInfoMap = itemInfoMap;
	}


	public Map<Long, ChangingContractItemStagePlan> getItemStagePlanMap() {
		return itemStagePlanMap;
	}


	public void setItemStagePlanMap(
			Map<Long, ChangingContractItemStagePlan> itemStagePlanMap) {
		this.itemStagePlanMap = itemStagePlanMap;
	}


	public ICommonService getCommonService() {
		return commonService;
	}


	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}


	public List<ChangeRealContractBillandRecePlan> getCrcbpList() {
		return crcbpList;
	}


	public void setCrcbpList(List<ChangeRealContractBillandRecePlan> crcbpList) {
		this.crcbpList = crcbpList;
	}


	public Long getOpInterface() {
		return opInterface;
	}


	public void setOpInterface(Long opInterface) {
		this.opInterface = opInterface;
	}


	public List<ChangingContractItemInfo> getChangeItemInfoList() {
		return changeItemInfoList;
	}


	public void setChangeItemInfoList(
			List<ChangingContractItemInfo> changeItemInfoList) {
		this.changeItemInfoList = changeItemInfoList;
	}


	public List<ChangingContractItemStagePlan> getChangeStagePlanList() {
		return changeStagePlanList;
	}


	public void setChangeStagePlanList(
			List<ChangingContractItemStagePlan> changeStagePlanList) {
		this.changeStagePlanList = changeStagePlanList;
	}


	public List<ChangingContractMaininfoPart> getChangeMainMoneyList() {
		return changeMainMoneyList;
	}


	public List<ChangingContractMaininfoPart> getChangeMainInfoPartList() {
		return changeMainInfoPartList;
	}


	public void setChangeMainInfoPartList(
			List<ChangingContractMaininfoPart> changeMainInfoPartList) {
		this.changeMainInfoPartList = changeMainInfoPartList;
	}


	public void setChangeMainMoneyList(
			List<ChangingContractMaininfoPart> changeMainMoneyList) {
		this.changeMainMoneyList = changeMainMoneyList;
	}






}

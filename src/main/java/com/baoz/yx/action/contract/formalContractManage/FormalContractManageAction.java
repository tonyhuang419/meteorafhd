package com.baoz.yx.action.contract.formalContractManage;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.opensymphony.xwork2.ActionContext;


@Results( {
	@Result(name = "loadFormalContractDetail", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractManage.jsp"),
	@Result(name = "error", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractManageQuery.jsp")
})
public class FormalContractManageAction extends DispatchAction{
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventInfoService;			//项目信息操作的service


	private ContractMainInfo cmi =  null;                          //合同主体信息
//	private List<ContractItemMaininfo> cimiList =  null;           //合同项目主体信息List
//	private List<ContractItemInfo> ciiList =  null;                //合同项目内容List
//	private List<List<ContractItemInfo>> ciiLists = null;                                  //合同项目内容集合...........一个合同项目存在多个项目内容
//	private List<BigDecimal> itemAmountList = null;                //合同项目总金额List
//	private List<ContractItemStage> cisList = null;               //项目阶段信息List

	private ContractOtherInfo coi = null;                         //合同其它信息，只有一个，不需要列表
	private List<ApplyMessage> amList = null;                     //合同申购信息List

	private List<InvoiceInfo> conInvoiceInfoList = null;          //合同开票信息列表
	private List<List<ReveInfo>>   conReveInfoList = null;         //合同收款信息列表

	private List<AssistanceContract>  acList = null;               //合同外协合同信息列表
	private List<List<AssistancePayInfo>>  apiList = null;         //合同外协合同支付信息列表
	private List<ContractOwnProduce>  contractOwnProduceList = null;         //合同自有产品信息列表

	private String contractBillState = null;  				//合同开票状态
	private String contractReceState = null;  				//合同收款状态
	private boolean canClose   =   false;    				 //合同能否关闭

	private long cmisysid = -1;      				 //合同主体信息系统号
	private String conStat =  "0";  				 //1 申购保存成功  2申购提交成功   3外协保存成功 4外协申请成功 5关闭成功


	private List<ContractMaininfoPart> mainMoneyList= null;      // 合同金额组成列表

	private List<ContractMaininfoPart> mainInfoPartList= null;  //合同费用列表

//	private List<ContractItemMaininfo> itemMainInfoList= null;  //项目信息列表

	private List<ContractItemMaininfo> iteminfolist = null;  	// 项目信息列表（维护项Iteminfo对象）

	private List<Object[]> mainMoneyWithPlanAmountList = null;  // 合同金额组成和阶段金额列表

	private List<ContractItemStagePlan> stagePlanlist = null;    	// 阶段计划信息列表

	private List<RealContractBillandRecePlan> rcbrpList = null;   //实际开票和收款计划列表

	private List<InitContractBillpro> initRcplan;                   // 原始开票收款计划对象


	public String doDefault() throws Exception {	

		if(ActionContext.getContext().getSession().get("conStat")!=null){
			conStat = (String)ActionContext.getContext().getSession().get("conStat");
			ActionContext.getContext().getSession().remove("conStat");
		}

		//logger.info("user id is : " + UserUtils.getUser().getId());	
		if(cmisysid==-1){   //该语句调试用
			return "error";  //该语句调试用
		} 
		this.loadContractInfo();
		return "loadFormalContractDetail";
	}


	//载入合同信息
	public void loadContractInfo(){

		//first tag 载入合同主体信息
		cmi = foramlContractService.loadContractMainInfo(cmisysid);

		//first,four tag 载入费用信息
		mainMoneyList = contractservice.findMainMoneyList(cmisysid);

		//second tag 通过合同id查找合同费用
		mainInfoPartList = eventInfoService.getMainInfoPartByConId(cmisysid);

		//second tag 通过合同id查找合同费用
		iteminfolist = contractservice.findEventByMainInfoId(cmisysid);

		//third tag 载入阶段金额信息
		mainMoneyWithPlanAmountList = contractservice.findMainMoneyWithPlanAmountList(cmisysid);

		//third tag 载入阶段信息
		stagePlanlist = contractservice.findItemStagePlanByMainInfoId(cmisysid);


		//four tag 通过合同id获取init开票和收款计划列表
		if(cmi.getConState().equals(1L)){
			initRcplan = contractservice.findPlanlist(cmisysid);
		}

		else{
			//four tag 通过合同id获取实际开票和收款计划列表
			rcbrpList = foramlContractService.loadRealContractBillandRecePlan(cmisysid);

			//获取合同开票信息
			conInvoiceInfoList = foramlContractService.loadInvoiceInfo(cmisysid);


			//遍历开票信息、获得发票、收据明细表，获取收款信息
			if(conInvoiceInfoList != null && conInvoiceInfoList.size()>0){
				conReveInfoList = foramlContractService.loadReceInfoList(conInvoiceInfoList);
			}

			//获取外协合同信息
			//if( !(cmi.getExistSidehelp()== null) && cmi.getExistSidehelp()!= false ){
			acList = foramlContractService.loadContractAssistanceContract(cmisysid);
			//}

			//if exist 外协合同信息，获取外协付款信息
			if(acList!=null && acList.size()>0){
				apiList = foramlContractService.loadAssistancePayInfoList(acList);
			}


			//获取合同申购信息列表,if exist
			//if( !(cmi.getExistPurchase()== null) && cmi.getExistPurchase()!= false ){
			amList = foramlContractService.loadContractApplyInfo(cmisysid);
			//}

			//判定能否关闭合同。。。。条件：全额开票。。。全额收款。。。外协全部结束。。。项目成本结束
			if(foramlContractService.allInvoice(cmisysid) && foramlContractService.allRece(cmisysid)
					&& foramlContractService.allAssistanceOver(cmisysid)  
					&& foramlContractService.allItemCostSure(cmisysid)  ){
				//&& foramlContractService.allPEOver(cmisysid) 	 ){
				canClose = true;
			}

		}


		//获取合同自有产品信息,if exist
		//if( !(cmi.getExistOwnProd()== null) && cmi.getExistOwnProd()!= false ){
		contractOwnProduceList = foramlContractService.loadContractOwnProduce(cmisysid);
		//}

		//获取合同其它信息,if exist
		//if( !(cmi.getExistFinAccout()== null) && cmi.getExistFinAccout()!= false ){
		coi = foramlContractService.loadContractOtherInfo(cmisysid);
		//}




//		//如果存在项目信息，获取 合同项目主体信息
//		//if( !(cmi.getExistIteminfo()== null) && cmi.getExistIteminfo()!= false ){
//		logger.info(".................exist Iteminfo...............");
//		获取项系统号目主体信息
//		cmisysid = cmi.getConMainInfoSid();   

//		//载入项目主体信息
//		cimiList = foramlContractService.loadContractItemMainInfo(cmisysid);				
//		ciiLists = new ArrayList<List<ContractItemInfo>>();


		//合同状态判定
		contractBillState = foramlContractService.getContractBillStateName(cmisysid);
		contractReceState = foramlContractService.getContractReceStateName(cmisysid);




//		long cimisysid = -1;   //合同项目主体信息号
//		itemAmountList = new ArrayList<BigDecimal>();
//		获取项目内容信息
//		if(cimiList!=null && cimiList.size()>0){
//		for(ContractItemMaininfo c:cimiList){
//		//获取合同项目主体信息号
//		cimisysid = c.getConItemMinfoSid();
//		//载入合同项目主体信息
//		ciiList = foramlContractService.loadContractItemInfo(cimisysid);
//		ciiLists.add(ciiList);

//		//计算项目内容总金额（如果前台没有项目总金额，这从这里取值）
//		BigDecimal itemAmount = new BigDecimal(0);
//		for(ContractItemInfo lc:ciiList){
//		logger.info(lc.getConItemAmountWithTax());
//		itemAmount = itemAmount.add(lc.getConItemAmountWithTax());
//		}
//		itemAmountList.add(itemAmount);
//		}	
//		}


		//获取了项目后，当然是项目阶段咯，哈哈哈.......................这里需要修改（没项目、有阶段的问题）
//		cisList = foramlContractService.loadContractItemStageInfo(cmisysid);		


	}

	//关闭合同用Action
	public String closeCon(){
		cmi = foramlContractService.loadContractMainInfo(cmisysid);
		cmi.setConState(10L);
		foramlContractService.updateConMainInfo(cmi);

		this.loadContractInfo();
		conStat = "3";
		logger.info("......................");
		return "loadFormalContractDetail";	
	}

	public List<AssistanceContract> getAcList() {
		return acList;
	}

	public void setAcList(List<AssistanceContract> acList) {
		this.acList = acList;
	}

	public List<List<AssistancePayInfo>> getApiList() {
		return apiList;
	}

	public void setApiList(List<List<AssistancePayInfo>> apiList) {
		this.apiList = apiList;
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

	public ContractMainInfo getCmi() {
		return cmi;
	}

	public void setCmi(ContractMainInfo cmi) {
		this.cmi = cmi;
	}

//	public List<ContractItemMaininfo> getCimiList() {
//	return cimiList;
//	}

//	public void setCimiList(List<ContractItemMaininfo> cimiList) {
//	this.cimiList = cimiList;
//	}

//	public List<ContractItemInfo> getCiiList() {
//	return ciiList;
//	}

//	public void setCiiList(List<ContractItemInfo> ciiList) {
//	this.ciiList = ciiList;
//	}

//	public List<List<ContractItemInfo>> getCiiLists() {
//	return ciiLists;
//	}

//	public void setCiiLists(List<List<ContractItemInfo>> ciiLists) {
//	this.ciiLists = ciiLists;
//	}

//	public List<BigDecimal> getItemAmountList() {
//	return itemAmountList;
//	}

//	public void setItemAmountList(List<BigDecimal> itemAmountList) {
//	this.itemAmountList = itemAmountList;
//	}

//	public List<ContractItemStage> getCisList() {
//	return cisList;
//	}

//	public void setCisList(List<ContractItemStage> cisList) {
//	this.cisList = cisList;
//	}

	public ContractOtherInfo getCoi() {
		return coi;
	}

	public void setCoi(ContractOtherInfo coi) {
		this.coi = coi;
	}

	public List<ApplyMessage> getAmList() {
		return amList;
	}

	public void setAmList(List<ApplyMessage> amList) {
		this.amList = amList;
	}

	public List<RealContractBillandRecePlan> getRcbrpList() {
		return rcbrpList;
	}

	public void setRcbrpList(List<RealContractBillandRecePlan> rcbrpList) {
		this.rcbrpList = rcbrpList;
	}

	public List<InvoiceInfo> getConInvoiceInfoList() {
		return conInvoiceInfoList;
	}

	public void setConInvoiceInfoList(List<InvoiceInfo> conInvoiceInfoList) {
		this.conInvoiceInfoList = conInvoiceInfoList;
	}

	public List<List<ReveInfo>> getConReveInfoList() {
		return conReveInfoList;
	}

	public void setConReveInfoList(List<List<ReveInfo>> conReveInfoList) {
		this.conReveInfoList = conReveInfoList;
	}

	public long getCmisysid() {
		return cmisysid;
	}

	public void setCmisysid(long cmisysid) {
		this.cmisysid = cmisysid;
	}

	public List<ContractOwnProduce> getContractOwnProduceList() {
		return contractOwnProduceList;
	}

	public void setContractOwnProduceList(
			List<ContractOwnProduce> contractOwnProduceList) {
		this.contractOwnProduceList = contractOwnProduceList;
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



	public boolean isCanClose() {
		return canClose;
	}



	public void setCanClose(boolean canClose) {
		this.canClose = canClose;
	}

	public String getConStat() {
		return conStat;
	}


	public void setConStat(String conStat) {
		this.conStat = conStat;
	}


	public IContractService getContractservice() {
		return contractservice;
	}


	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}


	public List<ContractMaininfoPart> getMainInfoPartList() {
		return mainInfoPartList;
	}


	public void setMainInfoPartList(List<ContractMaininfoPart> mainInfoPartList) {
		this.mainInfoPartList = mainInfoPartList;
	}


//	public List<ContractItemMaininfo> getItemMainInfoList() {
//	return itemMainInfoList;
//	}


//	public void setItemMainInfoList(List<ContractItemMaininfo> itemMainInfoList) {
//	this.itemMainInfoList = itemMainInfoList;
//	}


	public IEventInfoService getEventInfoService() {
		return eventInfoService;
	}


	public void setEventInfoService(IEventInfoService eventInfoService) {
		this.eventInfoService = eventInfoService;
	}


	public List<ContractItemMaininfo> getIteminfolist() {
		return iteminfolist;
	}


	public void setIteminfolist(List<ContractItemMaininfo> iteminfolist) {
		this.iteminfolist = iteminfolist;
	}






	public List<ContractMaininfoPart> getMainMoneyList() {
		return mainMoneyList;
	}


	public void setMainMoneyList(List<ContractMaininfoPart> mainMoneyList) {
		this.mainMoneyList = mainMoneyList;
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


	public List<InitContractBillpro> getInitRcplan() {
		return initRcplan;
	}


	public void setInitRcplan(List<InitContractBillpro> initRcplan) {
		this.initRcplan = initRcplan;
	}

}

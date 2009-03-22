package com.baoz.yx.action.contract.formalContractManage;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.MaterialManager;
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
import com.baoz.yx.service.IFormalContractModifyService;
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
	@Qualifier("formalContractModifyService")
	private IFormalContractModifyService formalContractModifyService;

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
	private ContractOtherInfo coi = null;                         //合同其它信息，只有一个，不需要列表
	private List<ApplyMessage> amList = null;                     //合同申购信息List
	private List<InvoiceInfo> conInvoiceInfoList = null;          //合同开票信息列表
	private List<Object[]>  rList = null;							//收款信息
	private List<Object[]>  acList = null;               //合同外协合同信息列表
	private List<List<AssistancePayInfo>>  apiList = null;         //合同外协合同支付信息列表
	private List<ContractOwnProduce>  contractOwnProduceList = null;         //合同自有产品信息列表

	private String contractBillState = null;  				//合同开票状态
	private String contractReceState = null;  				//合同收款状态
	private String outsourceState = null;  				//合同收款状态
	private boolean canClose   =   false;    				 //合同能否关闭

	private long cmisysid = -1;      				 //合同主体信息系统号
	private String conStat =  "0";  				 //1 申购保存成功  2申购提交成功   3外协保存成功 4外协申请成功 5关闭成功
	private String whereCome = "0";                 //0草稿合同来的 1合同确认来的  2录入项目号来的  3合同工程经济来的 4统计来的


	private List<ContractMaininfoPart> mainMoneyList= null;      // 合同金额组成列表
	private List<ContractMaininfoPart> mainInfoPartList= null;  //合同费用列表
	private List<ContractItemMaininfo> iteminfolist = null;  	// 项目信息列表（维护项Iteminfo对象）
	private List<Object[]> mainMoneyWithPlanAmountList = null;  // 合同金额组成和阶段金额列表
	private List<ContractItemStagePlan> stagePlanlist = null;    	// 阶段计划信息列表
	private List<RealContractBillandRecePlan> rcbrpList = null;   //实际开票和收款计划列表
	private List<InitContractBillpro> initRcplan;                   // 原始开票收款计划对象
	private List<MaterialManager> checkedMaterialList;//显示选中的应交材料

	private Double sumBill = null;
	private Double billAmount = null;
	private Double receiptAmount = null;
	private Double receAmount = null;

	/****************************
	 * 用于合同确认时保存搜索条件
	 */
	private String groupId;
	private String expId;
	private String customerId;
	private String conType;
	private String conState;
	private Double minMoney;
	private Double maxMoney;
	private String minConSignDate;
	private String maxConSignDate;
	
	private	 Boolean isShowConChangeButton;//是否显示变更按钮



	public String doDefault() throws Exception {	
		if(ActionContext.getContext().getSession().get("conStat")!=null){
			conStat = (String)ActionContext.getContext().getSession().get("conStat");
			ActionContext.getContext().getSession().remove("conStat");
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
		
		//////////判断是否可以点合同变更
		//标准：在变更表中有信息，但是表中的合同合同状态不能为申请提交或变更表中没有信息

		//second tag 通过合同id查找合同费用
		mainInfoPartList = eventInfoService.getMainInfoPartByConId(cmisysid);

		//second tag 通过合同id查找项目信息
		iteminfolist = contractservice.findEventByMainInfoId(cmisysid);

		//third tag 载入阶段金额信息
		mainMoneyWithPlanAmountList = contractservice.findMainMoneyWithPlanAmountList(cmisysid);

		//third tag 载入阶段信息
		stagePlanlist = contractservice.findItemStagePlanByMainInfoId(cmisysid);

		//four tag 通过合同id获取init开票和收款计划列表
		if(cmi.getConState().equals(1L) || cmi.getConState().equals(3L) ){
			initRcplan = contractservice.findPlanlist(cmisysid);
		}

		else{
			//four tag 通过合同id获取实际开票和收款计划列表
			rcbrpList = foramlContractService.loadRealContractBillandRecePlan(cmisysid);

			//获取合同开票信息。。。。。。这个还用来统计开票总额。。。。out收据
			conInvoiceInfoList = foramlContractService.loadInvoiceInfo(cmisysid);

			//遍历开票信息、获得发票、收据明细表，获取收款信息...这个还用来统计收款总额。。。。out收据
//			if(conInvoiceInfoList != null && conInvoiceInfoList.size()>0){
//			conReveInfoList = foramlContractService.loadReceInfoList(conInvoiceInfoList);
			rList = foramlContractService.loadReceInfoListByCon(cmisysid);
//			}

			//获取外协合同信息
			//if( !(cmi.getExistSidehelp()== null) && cmi.getExistSidehelp()!= false ){
			acList = foramlContractService.loadContractAssistanceContract(cmisysid);
			//}

			//if exist 外协合同信息，获取外协付款信息
//			if(acList!=null && acList.size()>0){
//				apiList = foramlContractService.loadAssistancePayInfoList(acList);
//			}

			//获取合同申购信息列表,if exist
			//if( !(cmi.getExistPurchase()== null) && cmi.getExistPurchase()!= false ){
			amList = foramlContractService.loadContractApplyInfo(cmisysid);
			//}

			Long conState = foramlContractService.loadContractMainInfo(cmisysid).getConState();
			if( conState.equals(8L)){
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
		if(coi!=null){
			checkedMaterialList=contractservice.getMaterialManagerByOtherId(coi.getOtherInfoId());
		}
		//}

		//合同状态判定
		contractBillState = foramlContractService.getContractBillStateName(cmisysid);
		contractReceState = foramlContractService.getContractReceStateName(cmisysid);
		outsourceState = foramlContractService.getOutSoureStatName(cmisysid);
	}
	
	public String checkIsShowConChangeButton()throws Exception{
		Boolean showConChangeButton = formalContractModifyService.checkShowConChangeButton(cmisysid); 
		if(showConChangeButton){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
		return null;
	}

	//关闭合同用Action
	public String closeCon(){
		contractservice.contractIsCloseByContractId(cmisysid);
		Long conState = foramlContractService.loadContractMainInfo(cmisysid).getConState();
		if( conState.equals(8L)){			
			cmi = foramlContractService.loadContractMainInfo(cmisysid);
			cmi.setConState(10L);
			foramlContractService.updateConMainInfo(cmi);
			conStat = "5";
		}
		else{
			conStat = "6";
		}
		cmi = foramlContractService.loadContractMainInfo(cmisysid);
		this.loadContractInfo();
		return "loadFormalContractDetail";	
	}



	public List<Object[]> getAcList() {
		return acList;
	}


	public void setAcList(List<Object[]> acList) {
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

	public List<InvoiceInfo> getConInvoiceInfoList() {
		return conInvoiceInfoList;
	}

	public void setConInvoiceInfoList(List<InvoiceInfo> conInvoiceInfoList) {
		this.conInvoiceInfoList = conInvoiceInfoList;
	}


	public List<Object[]> getRList() {
		return rList;
	}


	public void setRList(List<Object[]> list) {
		rList = list;
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


	public String getWhereCome() {
		return whereCome;
	}


	public void setWhereCome(String whereCome) {
		this.whereCome = whereCome;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getExpId() {
		return expId;
	}


	public void setExpId(String expId) {
		this.expId = expId;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getConType() {
		return conType;
	}


	public void setConType(String conType) {
		this.conType = conType;
	}


	public String getConState() {
		return conState;
	}


	public void setConState(String conState) {
		this.conState = conState;
	}


	public Double getMinMoney() {
		return minMoney;
	}


	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}


	public Double getMaxMoney() {
		return maxMoney;
	}


	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}


	public String getMinConSignDate() {
		return minConSignDate;
	}


	public void setMinConSignDate(String minConSignDate) {
		this.minConSignDate = minConSignDate;
	}


	public String getMaxConSignDate() {
		return maxConSignDate;
	}


	public void setMaxConSignDate(String maxConSignDate) {
		this.maxConSignDate = maxConSignDate;
	}


	public List<MaterialManager> getCheckedMaterialList() {
		return checkedMaterialList;
	}


	public void setCheckedMaterialList(List<MaterialManager> checkedMaterialList) {
		this.checkedMaterialList = checkedMaterialList;
	}


	public List<RealContractBillandRecePlan> getRcbrpList() {
		return rcbrpList;
	}


	public void setRcbrpList(List<RealContractBillandRecePlan> rcbrpList) {
		this.rcbrpList = rcbrpList;
	}


	public Double getSumBill() {
		return sumBill;
	}


	public void setSumBill(Double sumBill) {
		this.sumBill = sumBill;
	}


	public Double getBillAmount() {
		return billAmount;
	}


	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}


	public Double getReceiptAmount() {
		return receiptAmount;
	}


	public void setReceiptAmount(Double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}


	public Double getReceAmount() {
		return receAmount;
	}


	public void setReceAmount(Double receAmount) {
		this.receAmount = receAmount;
	}


	public Boolean getIsShowConChangeButton() {
		return isShowConChangeButton;
	}


	public void setIsShowConChangeButton(Boolean isShowConChangeButton) {
		this.isShowConChangeButton = isShowConChangeButton;
	}


	public IFormalContractModifyService getFormalContractModifyService() {
		return formalContractModifyService;
	}


	public void setFormalContractModifyService(
			IFormalContractModifyService formalContractModifyService) {
		this.formalContractModifyService = formalContractModifyService;
	}


	public String getOutsourceState() {
		return outsourceState;
	}


	public void setOutsourceState(String outsourceState) {
		this.outsourceState = outsourceState;
	}

}

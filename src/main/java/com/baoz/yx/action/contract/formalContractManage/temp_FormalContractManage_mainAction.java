package com.baoz.yx.action.contract.formalContractManage;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/formalContractManage/temp_formalContractManage_main.jsp")
	 })
public class temp_FormalContractManage_mainAction extends DispatchAction{
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	private ContractMainInfo cmi =  null;                          //合同主体信息
	private List<ContractItemMaininfo> cimiList =  null;           //合同项目主体信息List
	private List<ContractItemInfo> ciiList =  null;                //合同项目内容List
	private List<List<ContractItemInfo>> ciiLists = null;                                  //合同项目内容集合...........一个合同项目存在多个项目内容
	private List<BigDecimal> itemAmountList = null;                //合同项目总金额List
	private List<ContractItemStage> cisList = null;               //项目阶段信息List

	private ContractOtherInfo coi = null;                         //合同其它信息，只有一个，不需要列表
	private List<ApplyMessage> amList = null;                     //合同申购信息List
	private List<RealContractBillandRecePlan> rcbrpList = null;   //开票和收款计划列表
	private List<InvoiceInfo> conInvoiceInfoList = null;          //合同开票信息列表
	private List<List<ReveInfo>>   conReveInfoList = null;         //合同收款信息列表

	private List<AssistanceContract>  acList = null;         //合同外协合同信息列表
	private List<List<AssistancePayInfo>>  apiList = null;         //合同外协合同支付信息列表
	private List<ContractOwnProduce>  contractOwnProduceList = null;         //合同自有产品信息列表

	private String contractBillState = null;  //合同开票状态
	private String contractReceState = null;  //合同收款状态
	private boolean canClose   =   false;     //合同能否关闭

	private long cmisysid = -1;       //合同主体信息系统号
	private boolean hasClose =  false;   //是否关闭动作
	
	
	private List<ContractMaininfoPart> mainMoneyList;    // 合同金额组成列表

	public List<ContractMaininfoPart> getMainMoneyList() {
		return mainMoneyList;
	}


	public void setMainMoneyList(List<ContractMaininfoPart> mainMoneyList) {
		this.mainMoneyList = mainMoneyList;
	}


	public String doDefault() throws Exception {	
		//logger.info("user id is : " + UserUtils.getUser().getId());	
		if(cmisysid==-1){   //该语句调试用
			return "error";  //该语句调试用
		} 
		this.loadContractInfo();
		return "success";
	}


	//载入合同信息
	public void loadContractInfo(){
		//载入合同主体信息
		cmi = foramlContractService.loadContractMainInfo(cmisysid);
		
		
		//载入费用信息
		mainMoneyList = contractservice.findMainMoneyList(cmisysid);

		//如果存在项目信息，获取 合同项目主体信息
		//if( !(cmi.getExistIteminfo()== null) && cmi.getExistIteminfo()!= false ){
		logger.info(".................exist Iteminfo...............");
		cmisysid = cmi.getConMainInfoSid();

		//载入项目主体信息
		cimiList = foramlContractService.loadContractItemMainInfo(cmisysid);				
		ciiLists = new ArrayList<List<ContractItemInfo>>();


		//合同状态判定
		contractBillState = foramlContractService.getContractBillStateName(cmisysid);
		contractReceState = foramlContractService.getContractReceStateName(cmisysid);

		//能否关闭合同.....缺少外协。。。项目成本确认
		if(foramlContractService.allInvoice(cmisysid) && foramlContractService.allRece(cmisysid) ){
			canClose = true;
		}


		long cimisysid = -1;   //合同项目主体信息号

		itemAmountList = new ArrayList<BigDecimal>();

		//获取项目内容信息
		if(cimiList!=null && cimiList.size()>0){
			for(ContractItemMaininfo c:cimiList){
				//获取合同项目主体信息号
				cimisysid = c.getConItemMinfoSid();
				//载入合同项目主体信息
				ciiList = foramlContractService.loadContractItemInfo(cimisysid);
				ciiLists.add(ciiList);

				//计算项目内容总金额（如果前台没有项目总金额，这从这里取值）
				BigDecimal itemAmount = new BigDecimal(0);
				for(ContractItemInfo lc:ciiList){
					logger.info(lc.getConItemAmountWithTax());
					itemAmount = itemAmount.add(lc.getConItemAmountWithTax());
				}
				itemAmountList.add(itemAmount);
			}	
		}
		//}

		//获取了项目后，当然是项目阶段咯，哈哈哈.......................这里需要修改（没项目、有阶段的问题）
		cisList = foramlContractService.loadContractItemStageInfo(cmisysid);		

		//获取开票和收款计划列表
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

		//获取合同自有产品信息,if exist
		//if( !(cmi.getExistOwnProd()== null) && cmi.getExistOwnProd()!= false ){
		contractOwnProduceList = foramlContractService.loadContractOwnProduce(cmisysid);
		//}

		//获取合同其它信息,if exist
		//if( !(cmi.getExistFinAccout()== null) && cmi.getExistFinAccout()!= false ){
		coi = foramlContractService.loadContractOtherInfo(cmisysid);
		//}
	}

	//关闭合同用Action
	public String closeCon(){
		cmi = foramlContractService.loadContractMainInfo(cmisysid);
		cmi.setConState(10L);
		foramlContractService.updateConMainInfo(cmi);

		this.loadContractInfo();
		hasClose = true;
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

	public List<ContractItemMaininfo> getCimiList() {
		return cimiList;
	}

	public void setCimiList(List<ContractItemMaininfo> cimiList) {
		this.cimiList = cimiList;
	}

	public List<ContractItemInfo> getCiiList() {
		return ciiList;
	}

	public void setCiiList(List<ContractItemInfo> ciiList) {
		this.ciiList = ciiList;
	}

	public List<List<ContractItemInfo>> getCiiLists() {
		return ciiLists;
	}

	public void setCiiLists(List<List<ContractItemInfo>> ciiLists) {
		this.ciiLists = ciiLists;
	}

	public List<BigDecimal> getItemAmountList() {
		return itemAmountList;
	}

	public void setItemAmountList(List<BigDecimal> itemAmountList) {
		this.itemAmountList = itemAmountList;
	}

	public List<ContractItemStage> getCisList() {
		return cisList;
	}

	public void setCisList(List<ContractItemStage> cisList) {
		this.cisList = cisList;
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



	public boolean isHasClose() {
		return hasClose;
	}



	public void setHasClose(boolean hasClose) {
		this.hasClose = hasClose;
	}


	public IContractService getContractservice() {
		return contractservice;
	}


	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

}
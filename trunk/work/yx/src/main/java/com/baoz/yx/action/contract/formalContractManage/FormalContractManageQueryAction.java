package com.baoz.yx.action.contract.formalContractManage;

import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ContractStateTool;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;

@Results( {
	@Result(name = "queryList", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractManageQuery.jsp")
})
public class FormalContractManageQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	private String start_date = null;       	//起始日期
	private String end_date = null;        		//结束日期
	private String conState = null;      	    //合同状态
	private int conStateSn = -2;         		//合同状态序号

	private Long saleId = null;          		 //销售员系统号
	private Long  customerId = null;     		 //客户系统号
	private String 	groupId  = null;     		 //组别代码
	private String  conType  =  null;     		//合同性质

	private String  conSn = null;       	  	//合同号
	private String  conName = null;				//合同名称
	private String  finalAccount  =  null;     //预决算信息
	private String  contractType  =  null;     //合同类型（工程类、集成类）

	private Double sumContract = 0d;   			//合同金额小计
	private Double billContract = 0d;			//开票总额小计
	private Double receiptContract = 0d;		//开收据额小计
	private Double reveContract = 0d;			//到款总额小计
	
	private Double sumContractSum = 0d;   		//合同金额累计
	private Double billContractSum = 0d;		//开票总额累计
	private Double receiptContractSum = 0d;		//开收据额累计
	private Double reveContractSum = 0d;		//到款总额累计
	private String partyAProId = null;     		//甲方工程编号
	private String partyAConId = null;    		//甲方合同号
	
	private String deliveryStartDate = null;       //起始日期
	private String deliveryEndDate = null;         //结束日期
	private Double maxAmount = null;    			//含税最大金额
	private Double minAmount = null;				//含税最小金额
	private String hasClosed;    					//已关闭
	private String itemSn;							//项目号
	
	private PageInfo info;
	private List<Object[]> listX;
	private Map<Long,List<String>> itemNoList;   //合同系统号，项目号
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"foramlContractSearch",this,new String[]{"start_date", "end_date", "conStateSn" ,
					"saleId", "customerId", "groupId" ,"conType", "conSn", "conName","finalAccount", "contractType",
					"partyAProId","partyAConId","deliveryStartDate","deliveryEndDate","maxAmount","minAmount","hasClosed","itemSn"});	

	@SuppressWarnings("unchecked")
	public String doDefault() throws Exception {
		/*
		 * 增加查询条件修改处 1 action 2 service  3 jsp  
		 */
		ParameterUtils.prepareParameters(holdTool);
		
		if(conState!=null)
			conStateSn = ContractStateTool.getContractStateNameToSn(conState);

		info = ParameterUtils.preparePageInfo(info, "formalContractManageQueryPage");
		info = foramlContractService.queryContractMainInfo( 
				start_date, end_date, conStateSn ,saleId, customerId, groupId ,conType, conSn, conName , finalAccount, contractType,partyAProId, 
				partyAConId , deliveryStartDate,deliveryEndDate,maxAmount,minAmount, hasClosed,itemSn, info );

		sumContractSum = foramlContractService.getStatSum(start_date, end_date, 
				conStateSn ,saleId, customerId, groupId ,conType, conSn, conName,finalAccount, contractType,partyAProId,partyAConId,
				deliveryStartDate,deliveryEndDate, maxAmount,minAmount,hasClosed,itemSn,1);
		billContractSum = foramlContractService.getStatSum(start_date, end_date, 
				conStateSn ,saleId, customerId, groupId ,conType, conSn,conName, finalAccount, contractType,partyAProId,partyAConId,
				deliveryStartDate,deliveryEndDate, maxAmount,minAmount,hasClosed,itemSn,2);;
		receiptContractSum = foramlContractService.getStatSum(start_date, end_date, 
				conStateSn ,saleId, customerId, groupId ,conType, conSn,conName, finalAccount, contractType,partyAProId,partyAConId,
				deliveryStartDate,deliveryEndDate,maxAmount,minAmount,hasClosed,itemSn, 3);
		reveContractSum = foramlContractService.getStatSum(start_date, end_date, 
				conStateSn ,saleId, customerId, groupId ,conType, conSn,conName, finalAccount, contractType,partyAProId,partyAConId,
				deliveryStartDate,deliveryEndDate,maxAmount,minAmount,hasClosed,itemSn, 4);
		
		
		listX = (List<Object[]>)info.getResult();
		for(Object[] o: listX){
			sumContract += ((ContractMainInfo)o[0]).getConTaxTamount();
			if(o[4]==null){
				o[4] = 0d;
			}
			billContract += (Double)o[4];
			if(o[6]==null){
				o[6] = 0d;
			}
			receiptContract += (Double)o[6];
			if(o[5]==null){
				o[5] = 0d;
			}
//			logger.info(reveContract);
//			logger.info(o[5]);
			reveContract += Double.valueOf(o[5].toString());
		}
		//处理info，取得项目号List
		itemNoList = foramlContractService.loadItemNoByPageInfo(info);
		
		return "queryList";
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

//	public List<ContractMainInfo> getList() {
//	return list;
//	}


//	public void setList(List<ContractMainInfo> list) {
//	this.list = list;
//	}


	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public PageInfo getInfo() {
		return info;
	}


	public void setInfo(PageInfo info) {
		this.info = info;
	}


	public String getConState() {
		return conState;
	}


	public void setConState(String conState) {
		this.conState = conState;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public int getConStateSn() {
		return conStateSn;
	}

	public void setConStateSn(int conStateSn) {
		this.conStateSn = conStateSn;
	}



	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}


	public String getFinalAccount() {
		return finalAccount;
	}

	public void setFinalAccount(String finalAccount) {
		this.finalAccount = finalAccount;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getConSn() {
		return conSn;
	}

	public void setConSn(String conSn) {
		this.conSn = conSn;
	}


	public Double getBillContract() {
		return billContract;
	}

	public void setBillContract(Double billContract) {
		this.billContract = billContract;
	}

	public Double getReceiptContract() {
		return receiptContract;
	}

	public void setReceiptContract(Double receiptContract) {
		this.receiptContract = receiptContract;
	}

	public Double getReveContract() {
		return reveContract;
	}

	public void setReveContract(Double reveContract) {
		this.reveContract = reveContract;
	}

	public List<Object[]> getListX() {
		return listX;
	}

	public void setListX(List<Object[]> listX) {
		this.listX = listX;
	}

	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}

	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}


	public Double getSumContract() {
		return sumContract;
	}

	public void setSumContract(Double sumContract) {
		this.sumContract = sumContract;
	}

	public Double getSumContractSum() {
		return sumContractSum;
	}

	public void setSumContractSum(Double sumContractSum) {
		this.sumContractSum = sumContractSum;
	}

	public Double getBillContractSum() {
		return billContractSum;
	}

	public void setBillContractSum(Double billContractSum) {
		this.billContractSum = billContractSum;
	}

	public Double getReceiptContractSum() {
		return receiptContractSum;
	}

	public void setReceiptContractSum(Double receiptContractSum) {
		this.receiptContractSum = receiptContractSum;
	}

	public Double getReveContractSum() {
		return reveContractSum;
	}

	public void setReveContractSum(Double reveContractSum) {
		this.reveContractSum = reveContractSum;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public String getPartyAProId() {
		return partyAProId;
	}

	public void setPartyAProId(String partyAProId) {
		this.partyAProId = partyAProId;
	}

	public String getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(String deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	public String getDeliveryEndDate() {
		return deliveryEndDate;
	}

	public void setDeliveryEndDate(String deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public String getHasClosed() {
		return hasClosed;
	}

	public void setHasClosed(String hasClosed) {
		this.hasClosed = hasClosed;
	}

	public String getItemSn() {
		return itemSn;
	}

	public void setItemSn(String itemSn) {
		this.itemSn = itemSn;
	}

	public String getPartyAConId() {
		return partyAConId;
	}

	public void setPartyAConId(String partyAConId) {
		this.partyAConId = partyAConId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

}

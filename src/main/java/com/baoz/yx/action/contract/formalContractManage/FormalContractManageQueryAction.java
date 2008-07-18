package com.baoz.yx.action.contract.formalContractManage;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ContractStateTool;

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

	private String start_date = null;       //起始日期
	private String end_date = null;         //结束日期
	private String conState = null;       //合同状态
	private int conStateSn = -1;         //合同状态序号
	
	private Long saleId = null;           //销售员系统号
	private Long  customerId = null;      //客户系统号
	private String 	groupId  = null;      //组别代码
	private String  conType  =  null;     //合同性质
	
	private String  conSn = null;       	 	 //合同号
	private String  finalAccount  =  null;     //预决算信息
	private String  contractType  =  null;     //合同类型（工程类、集成类）

	
	private PageInfo info;
	//private List<ContractMainInfo> list;

	@SuppressWarnings("unchecked")
	public String doDefault() throws Exception {
		if(conState!=null)
			conStateSn = ContractStateTool.getContractStateNameToSn(conState);

		info = foramlContractService.queryContractMainInfo( 
				start_date, end_date, conStateSn ,saleId, customerId, groupId ,conType, conSn, finalAccount, contractType, info);
		//list = (List<ContractMainInfo>)info.getResult();
		
		//下面的代为了防止空指针问题....NND真是麻烦....
		if(groupId == null){
			groupId =" ";
		}
		if(conType == null){
			conType =" ";
		}
		if(conState == null){
			conState =" ";
		}
		if(start_date == null){
			start_date =" ";
		}
		if(end_date == null){
			end_date =" ";
		}
		if(conSn == null){
			conSn =" ";
		}
		if(finalAccount == null){
			finalAccount =" ";
		}
		if(contractType == null){
			contractType =" ";
		}
	
		return "queryList";
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

//	public List<ContractMainInfo> getList() {
//		return list;
//	}
//
//
//	public void setList(List<ContractMainInfo> list) {
//		this.list = list;
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

}

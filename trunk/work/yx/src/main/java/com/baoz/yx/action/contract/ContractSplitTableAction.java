package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * 合同拆分表导出
 * 
 */
@Results( {
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/contract/contractSplitTable.jsp"),
	@Result(name = "change", value = "/WEB-INF/jsp/contract/changeContractSplitTable.jsp")
})
public class ContractSplitTableAction extends DispatchAction {


	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventInfoService;
	
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	private Long contractMainInfoSid;					   // 合同主体系统号
	private ContractMainInfo cmi;						   //合同主体信息
	private ChangingContractMainInfo gcmi;						   //合同主体信息

	private List<ContractMaininfoPart> mainMoneyList;     // 合同费用列表
	private List<ChangingContractMaininfoPart> gmainMoneyList;     // 合同费用列表
	
	
	
	private List<Object[]> itemInfolist;  	   // 项目信息
	 
	
	
	private YXClientCode client;
	private Employee employee;
	
	public ChangingContractMainInfo getGcmi() {
		return gcmi;
	}

	public void setGcmi(ChangingContractMainInfo gcmi) {
		this.gcmi = gcmi;
	}

	// 默认调用方法 显示合同新增主信息页面
	@SuppressWarnings("unchecked")
	public String doDefault() throws Exception {
		
		cmi  = contractservice.loadContractMainInfo(contractMainInfoSid);		
		client = foramlContractService.getCustomer(cmi.getConCustomer());
		employee = foramlContractService.getEmployee(cmi.getSaleMan());
		
		mainMoneyList = contractservice.findMainMoneyList(contractMainInfoSid);
		itemInfolist = foramlContractService.getItemAmountInfo(contractMainInfoSid);	
		
		return "SUCCESS";
	}
	
	public String doDefaultChange() throws Exception {
		
		gcmi  = contractservice.loadChangeContractMainInfo(contractMainInfoSid);		
		client = foramlContractService.getCustomer(gcmi.getConCustomer());
		employee = foramlContractService.getEmployee(gcmi.getSaleMan());
		
		gmainMoneyList = contractservice.findChangeMainMoneyList(contractMainInfoSid);
		itemInfolist = foramlContractService.getChangeItemAmountInfo(contractMainInfoSid);	
		
		return "change";
	}
	
	

	public IContractService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IEventInfoService getEventInfoService() {
		return eventInfoService;
	}

	public void setEventInfoService(IEventInfoService eventInfoService) {
		this.eventInfoService = eventInfoService;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public Long getContractMainInfoSid() {
		return contractMainInfoSid;
	}

	public void setContractMainInfoSid(Long contractMainInfoSid) {
		this.contractMainInfoSid = contractMainInfoSid;
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

	public List<Object[]> getItemInfolist() {
		return itemInfolist;
	}

	public void setItemInfolist(List<Object[]> itemInfolist) {
		this.itemInfolist = itemInfolist;
	}

	public YXClientCode getClient() {
		return client;
	}

	public void setClient(YXClientCode client) {
		this.client = client;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<ChangingContractMaininfoPart> getGmainMoneyList() {
		return gmainMoneyList;
	}

	public void setGmainMoneyList(List<ChangingContractMaininfoPart> gmainMoneyList) {
		this.gmainMoneyList = gmainMoneyList;
	}
	
	

	
}

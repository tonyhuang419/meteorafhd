package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.service.IBaseData;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.PadBeanFields;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/modifyContractBaseInfo.jsp"),
	@Result(name = "excessive", value = "/WEB-INF/jsp/invoiceManagement/excessive.jsp")
})
public class ModifyContractBaseInfoAction extends DispatchAction {
	private Long conSid;
	private ContractMainInfo cmi;                          //合同主体信息
	private List<ContractMaininfoPart> mainMoneyList;      // 合同金额组成列表


	private String contractBillState;  				//合同开票状态
	private String contractReceState;  				//合同收款状态
	private String groupId;    //组别

	private List<YXClientCode>	 clientlist;         // 客户列表
	private List<YXClientCode> 	allclientlist;  	// 开票客户列表
	private List<YXClientCode> eventclientlist;  //项目单位
	private List<YXTypeManage> customeventypelist;  	// 客户项目类型
	private List<YXTypeManage> dutydepartmentlist;   	// 工程责任部门
	
	private List<YXTypeManage> itemdesigntypelist;///费用组成的list
	private List<YXChargeMan> chargeManList;//负责人列表
	private List<YXLinkMan> linkmanlist;  	// 客户联系人

	private List<Employee> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	
	private Long 							contractmainsid;
	private String 							linkManName;

	// 合同性质
	private List<YXTypeManage> contractnature;

	// 合同类型
	private List<YXTypeManage> contracttype;
	
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventInfoService;


	@Autowired
	@Qualifier("baseDate")
	private IBaseData 	baseDate;

	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;



	public String doDefault() throws Exception {
		logger.info(conSid);
		groupList = UserUtils.getUserDetail().getDepartments();
		cmi = foramlContractService.loadContractMainInfo(conSid);
		mainMoneyList = contractservice.findMainMoneyList(conSid);
		contractnature = typeManageService.getYXTypeManage(1019L);
		contracttype = typeManageService.getYXTypeManage(1020L);
		customeventypelist = typeManageService.getYXTypeManage(1007L);
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);
		clientlist = contractservice.findClient(cmi.getSaleMan());
		eventclientlist = contractservice.findEventClient(cmi.getSaleMan());
		linkmanlist = contractservice.findlinkMan(cmi.getConCustomer());

		allclientlist = contractservice.findAllClient(cmi.getSaleMan());   // 寻找开票客户
		contractBillState = foramlContractService.getContractBillStateName(conSid);
		contractReceState = foramlContractService.getContractReceStateName(conSid);

		String t = foramlContractService.getOrganizationCodeByEid(cmi.getSaleMan());
		if(t.length()>4){
			groupId = t.substring(0, 4);
		}
		listExp = foramlContractService.getSaleManByGroupId(t);
		clientlist =  this.processClient(clientlist,foramlContractService.getYXClientCodeByCid(cmi.getConCustomer()));	
		allclientlist = this.processClient(allclientlist,foramlContractService.getYXClientCodeByCid(cmi.getBillCustomer()));	
		eventclientlist =  this.processClient(eventclientlist,foramlContractService.getYXClientCodeByCid(cmi.getItemCustomer()));	
		itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
		
		return SUCCESS;
	}

	public String save(){
//		Long empId = cmi.getSaleMan();
//		Long billCusId = cmi.getBillCustomer();
		if(!"".equals(linkManName)){
			cmi.setLinkManId(contractservice.getLinkManIdByName(linkManName,cmi.getConCustomer()));
		}
		ContractMainInfo tCmi = cmi;
		cmi = foramlContractService.loadContractMainInfo(contractmainsid);
		PadBeanFields.padBean(cmi, tCmi,new String[]{"serialVersionUID"});
		//因为padding 会忽略null
		if("".equals(linkManName)){
			cmi.setLinkManId(null);
		}
		baseDate.saveChange(cmi);
		baseDate.updatePartName(mainMoneyList, cmi.getConMainInfoSid());
		invoiceService.saveSaleAndCusRealtion(  cmi.getSaleMan() , cmi.getConCustomer() );
		invoiceService.saveSaleAndCusRealtion(  cmi.getSaleMan() , cmi.getBillCustomer() );
		return "excessive";
	}

	private List<YXClientCode> processClient( List<YXClientCode> clientlist ,  YXClientCode c ){
		for(YXClientCode cc : clientlist){
			if(cc.getId().equals(c.getId())){
				return clientlist;
			}
		}
		clientlist.add(c);
		return clientlist;
	}
	



	public Long getConSid() {
		return conSid;
	}

	public void setConSid(Long conSid) {
		this.conSid = conSid;
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

	public List<YXClientCode> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<YXClientCode> clientlist) {
		this.clientlist = clientlist;
	}

	public List<YXClientCode> getAllclientlist() {
		return allclientlist;
	}

	public void setAllclientlist(List<YXClientCode> allclientlist) {
		this.allclientlist = allclientlist;
	}

	public List<Employee> getListExp() {
		return listExp;
	}

	public void setListExp(List<Employee> listExp) {
		this.listExp = listExp;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getContractmainsid() {
		return contractmainsid;
	}

	public void setContractmainsid(Long contractmainsid) {
		this.contractmainsid = contractmainsid;
	}

	public IBaseData getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(IBaseData baseDate) {
		this.baseDate = baseDate;
	}

	public List<YXTypeManage> getContractnature() {
		return contractnature;
	}

	public void setContractnature(List<YXTypeManage> contractnature) {
		this.contractnature = contractnature;
	}

	public List<YXTypeManage> getContracttype() {
		return contracttype;
	}

	public void setContracttype(List<YXTypeManage> contracttype) {
		this.contracttype = contracttype;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public List<YXTypeManage> getCustomeventypelist() {
		return customeventypelist;
	}

	public void setCustomeventypelist(List<YXTypeManage> customeventypelist) {
		this.customeventypelist = customeventypelist;
	}

	public List<YXTypeManage> getDutydepartmentlist() {
		return dutydepartmentlist;
	}

	public void setDutydepartmentlist(List<YXTypeManage> dutydepartmentlist) {
		this.dutydepartmentlist = dutydepartmentlist;
	}

	public IEventInfoService getEventInfoService() {
		return eventInfoService;
	}

	public void setEventInfoService(IEventInfoService eventInfoService) {
		this.eventInfoService = eventInfoService;
	}

	public List<YXChargeMan> getChargeManList() {
		return chargeManList;
	}

	public void setChargeManList(List<YXChargeMan> chargeManList) {
		this.chargeManList = chargeManList;
	}

	public List<YXClientCode> getEventclientlist() {
		return eventclientlist;
	}

	public void setEventclientlist(List<YXClientCode> eventclientlist) {
		this.eventclientlist = eventclientlist;
	}

	public List<YXLinkMan> getLinkmanlist() {
		return linkmanlist;
	}

	public void setLinkmanlist(List<YXLinkMan> linkmanlist) {
		this.linkmanlist = linkmanlist;
	}

	public String getLinkManName() {
		return linkManName;
	}

	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}

	public List<YXTypeManage> getItemdesigntypelist() {
		return itemdesigntypelist;
	}

	public void setItemdesigntypelist(List<YXTypeManage> itemdesigntypelist) {
		this.itemdesigntypelist = itemdesigntypelist;
	}


}

package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ContractMainInfo;

import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
@Results( {
@Result(name = "success", value = "/WEB-INF/jsp/contract/contractChange.jsp"),
@Result(name = "leftFram", value = "/WEB-INF/jsp/contract/contractChange.jsp")})
public class ContractmanagerAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private List<ContractMainInfo> listSup; // 查询显示所有的合同
	private List<ContractMainInfo> listSup1;
	private List<ChangingContractMainInfo> listSup2;
	private String contractId; //合同号
	private Long contractSales;
	private PageInfo info;
	 private String[] operation2;
		private List<Object>        	yxClientCodeList;
		private List<YXTypeManage>		dutyDepartmentIdList;
		private List<Object> 			listExp; 
		private List<Department>		groupList;	
	public List<Object> getYxClientCodeList() {
			return yxClientCodeList;
		}


		public void setYxClientCodeList(List<Object> yxClientCodeList) {
			this.yxClientCodeList = yxClientCodeList;
		}


		public List<YXTypeManage> getDutyDepartmentIdList() {
			return dutyDepartmentIdList;
		}


		public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
			this.dutyDepartmentIdList = dutyDepartmentIdList;
		}


		public List<Department> getGroupList() {
			return groupList;
		}


		public void setGroupList(List<Department> groupList) {
			this.groupList = groupList;
		}


	public String[] getOperation2() {
		return operation2;
	}


	public void setOperation2(String[] operation2) {
		this.operation2 = operation2;
	}


	public void setContractId(String contractId) {
		this.contractId = contractId;
	}


	public String doDefault() throws Exception {
		queryBySale();
		return "success";
	}
	
	
	//确认通过
	public String relationTicket() throws Exception {
		operatorContract();
		queryBySale();
		return "leftFram";
	}
   
	//确认退回
	public String relationTicketBack() throws Exception {
		for(int i=0;i<operation2.length;i++)
		{   
			System.out.print(operation2[i]+"     第"+ i+" 次  ");
			String s1hql="from ChangingContractMainInfo d where  d.conMainInfoSid='"+operation2[i]+"'";
			List<ChangingContractMainInfo> chagList = service.list(s1hql);
			String s1hq2="from ContractMainInfo d where  d.conMainInfoSid='"+operation2[i]+"'";
	
			List<ContractMainInfo> conList = service.list(s1hq2);
			conList.get(0).setConState(7L);
			service.update(conList.get(0));
			service.delete(chagList.get(0));
		}
		queryBySale();
		return "leftFram";
	}
  
	//下拉框
	public String salesTicket() throws Exception
	{
		queryBySale();
		return "leftFram";
	}
	
	public String search() throws Exception
	{
		queryBySale();
		return "leftFram";
	}
	
	private void queryBySale(){
		listExp = service.list("from  Employee d where d.id in (select cc.saleMan from ChangingContractMainInfo cc group by cc.saleMan)");
		StringBuilder shql = new StringBuilder("from ChangingContractMainInfo d,YXClientCode yc,Employee emp where  d.itemCustomer = yc.id and d.saleMan = emp.id ");
		if(contractSales != null){
			shql.append(" and d.saleMan = "+this.getContractSales());
		}
		info = queryService.listQueryResult(shql.toString(), info);
	}

	public ICommonService getService() {
		return service;
	}



	public void setService(ICommonService service) {
		this.service = service;
	}



	public List<ContractMainInfo> getListSup() {
		return listSup;
	}



	public void setListSup(List<ContractMainInfo> listSup) {
		this.listSup = listSup;
	}


	public Long getContractSales() {
		return contractSales;
	}



	public void setContractSales(Long contractSales) {
		this.contractSales = contractSales;
	}



	public IQueryService getQueryService() {
		return queryService;
	}



	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}



	public PageInfo getInfo() {
		return info;
	}



	public void setInfo(PageInfo info) {
		this.info = info;
	}



	public void operatorContract()
	{
		for(int i=0;i<operation2.length;i++)
		{
			System.out.print(operation2[i]+"下拉框的值！");
		String s1hql="from ContractMainInfo d where  d.conMainInfoSid='"+operation2[i]+"'";
		System.out.println(operation2[i]);
		String s2hql="from ChangingContractMainInfo d where d.conMainInfoSid='"+operation2[i]+"'";
		
		listSup1 = service.list(s1hql);
		listSup2 = service.list(s2hql);
		System.out.print("合同号："+operation2[i]+"大小："+listSup1.size()    +"表1的记录~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("合同号："+operation2[i]+"大小："+listSup2.size()    +"表2的记录~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		listSup1.get(0).setConId(listSup2.get(0).getConId());  //正式合同号
		listSup1.get(0).setConName(listSup2.get(0).getConName()); //合同名称
		listSup1.get(0).setConType(listSup2.get(0).getConType()); //合同类型
		listSup1.get(0).setPreConSysid(listSup2.get(0).getPreConSysid());  //售前合同系统号
		listSup1.get(0).setSaleMan(listSup2.get(0).getSaleMan()); //销售员系统号
		listSup1.get(0).setConCustomer(listSup2.get(0).getConCustomer());//合同客户系统号
		listSup1.get(0).setLinkManId(listSup2.get(0).getLinkManId());//客户联系人ID
		listSup1.get(0).setCustomereventtype(listSup2.get(0).getCustomereventtype());//客户项目类型
		listSup1.get(0).setItemCustomer(listSup2.get(0).getItemCustomer());//项目客户/最终客户系统号
		listSup1.get(0).setBillCustomer(listSup2.get(0).getBillCustomer());//合同开票客户	
		listSup1.get(0).setMainItemDept(listSup2.get(0).getMainItemDept());//主项目负责部门	
		listSup1.get(0).setConDrawback(listSup2.get(0).getConDrawback());//是否退税
		listSup1.get(0).setConBid(listSup2.get(0).getConBid());//	是否中标合同
		listSup1.get(0).setConSignDate(listSup2.get(0).getConSignDate());//合同签定日期
		listSup1.get(0).setConStartDate(listSup2.get(0).getConStartDate());//合同起始日期
		listSup1.get(0).setConEndDate(listSup2.get(0).getConEndDate());//合同结束日期
		listSup1.get(0).setConTaxTamount(listSup2.get(0).getConTaxTamount());//合同含税总金额
		listSup1.get(0).setPartyAProId(listSup2.get(0).getPartyAProId());//甲方项目工程编号
		listSup1.get(0).setPartyAConId(listSup2.get(0).getPartyAConId());//甲方合同号
		listSup1.get(0).setMainItemPeople(listSup2.get(0).getMainItemPeople());//主项目负责人
		listSup1.get(0).setMainItemPhone(listSup2.get(0).getMainItemPhone()); //主项目负责人电话
		listSup1.get(0).setConBillState(listSup2.get(0).getConBillState());//合同开票状态
		listSup1.get(0).setConReceState(listSup2.get(0).getConReceState());//合同收款状态
		listSup1.get(0).setConState(listSup2.get(0).getConState());//合同状态
		listSup1.get(0).setIntoYearCon(listSup2.get(0).getIntoYearCon());//是否纳入年度运维合同
		listSup1.get(0).setExistPurchase(listSup2.get(0).getExistPurchase());//是否存在申购
		listSup1.get(0).setExistOwnProd(listSup2.get(0).getExistOwnProd());//是否存在自有产品
		listSup1.get(0).setExistIteminfo(listSup2.get(0).getExistIteminfo());//是否存在项目信息
		listSup1.get(0).setExistSidehelp(listSup2.get(0).getExistSidehelp());//是否存在外协信息
		listSup1.get(0).setExistFinAccout(listSup2.get(0).getExistFinAccout());//是否存在其它信息
		listSup1.get(0).setExistC(listSup2.get(0).getExistC());//是否存在变更
		listSup1.get(0).setConModInfo(false);  //是否属于合同变更信息
		listSup1.get(0).setOpTime(listSup2.get(0).getOpTime());
		listSup1.get(0).setOpPeople(listSup2.get(0).getOpPeople());
		service.update(listSup1.get(0));
		service.delete(listSup2.get(0));
		}
	}



	public String getContractId() {
		return contractId;
	}


	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}


	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}


	public List<Object> getListExp() {
		return listExp;
	}


	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}


	public List<ContractMainInfo> getListSup1() {
		return listSup1;
	}


	public void setListSup1(List<ContractMainInfo> listSup1) {
		this.listSup1 = listSup1;
	}


	public List<ChangingContractMainInfo> getListSup2() {
		return listSup2;
	}


	public void setListSup2(List<ChangingContractMainInfo> listSup2) {
		this.listSup2 = listSup2;
	}




	
	
}

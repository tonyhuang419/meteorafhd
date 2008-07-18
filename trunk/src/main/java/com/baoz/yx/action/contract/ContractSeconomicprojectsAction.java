package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.service.IYXTypeManageService;
@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/contractEconomicProjects.jsp"),
	@Result(name = "leftFram", value = "/WEB-INF/jsp/contract/inputReport.jsp"),
	@Result(name = "leftFramone", value = "/WEB-INF/jsp/contract/physicalHandover.jsp"),
	@Result(name = "leftFramtwo", value = "/WEB-INF/jsp/contract/finalAcceptance.jsp")})
public class ContractSeconomicprojectsAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private List<ContractMainInfo> listSup; // 查询显示所有的合同
	private List<ContractOtherInfo> listSup1;
	private PageInfo info;
	private Long contract; //合同主体信息系统号       
    private String contractId; //合同号
    private int flag=2;
    private List<Object>		listExp;
    @Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<ContractMainInfo> getListSup() {
		return listSup;
	}

	public void setListSup(List<ContractMainInfo> listSup) {
		this.listSup = listSup;
	}

	public List<ContractOtherInfo> getListSup1() {
		return listSup1;
	}

	public void setListSup1(List<ContractOtherInfo> listSup1) {
		this.listSup1 = listSup1;
	}



	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Long getContract() {
		return contract;
	}

	public void setContract(Long contract) {
		this.contract = contract;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String doDefault() throws Exception {
		logger.info("====================state");
		System.out.print("chaxun 查询");
		
		String shql = "from ContractMainInfo d,YXClientCode yc,Employee emp,ContractOtherInfo o " +
				"where o.contractMainSid = d.id and d.itemCustomer = yc.id and d.saleMan = emp.id";
		info = queryService.listQueryResult(shql, info);
		logger.info("test");
		
		return "success";
	}
	
	public String relationTicket() throws Exception {
		System.out.print(this.getContract()+"主合同信息！！！!!!");
		String shql ="from ContractMainInfo d where d.conMainInfoSid="+this.getContract();
		listSup=service.list(shql);
		System.out.print(listSup.get(0).getConId()+"从数据库里取合同号！！！！！");
		contractId=listSup.get(0).getConId(); //合同号
		contract=this.getContract();//合同主体信息系统号      
		return "leftFram";
	}
	
	public String relationTicketone() throws Exception {
		System.out.print(this.getContract()+"主合同信息one！！！!!!");
		String shql ="from ContractMainInfo d where d.conMainInfoSid="+this.getContract();
		listSup=service.list(shql);
		System.out.print(listSup.get(0).getConId()+"从数据库里取合同号！！！！！");
		contractId=listSup.get(0).getConId(); //合同号
		contract=this.getContract();//合同主体信息系统号  ]
		/*String shql1 ="from ContractOtherInfo d where d.contractMainSid="+this.getContract();
		listSup1=service.list(shql1);
		if(listSup1.get(0).getPerativeReport()!=null)
		{
			flag=1;
		}*/
		return "leftFramone";
	}
	
	public String relationTickettwo() throws Exception {
		System.out.print(this.getContract()+"主合同信息two！！！!!!");
		String shql ="from ContractMainInfo d where d.conMainInfoSid="+this.getContract();
		listSup=service.list(shql);
		System.out.print(listSup.get(0).getConId()+"从数据库里取合同号！！！！！");
		contractId=listSup.get(0).getConId(); //合同号
		contract=this.getContract();//合同主体信息系统号      
		return "leftFramtwo";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
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

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

}

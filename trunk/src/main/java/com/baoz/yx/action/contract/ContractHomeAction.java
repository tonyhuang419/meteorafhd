package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/*
 * 草搞合同相关操作
 * @author xiao ping (xiaoping@baoz.com.cn)
 */
@Results( {
		@Result(name = "main", value = "/WEB-INF/jsp/contract/contractHome.jsp"),
		@Result(name = "leftFram", value = "/WEB-INF/jsp/contract/contractLeftFram.jsp"),
		@Result(name = "leftBotJsp", value = "/WEB-INF/jsp/contract/contractSearchForm.jsp"),
		@Result(name="leftTopJsp",value="/WEB-INF/jsp/contract/disCustomer.jsp") })
public class ContractHomeAction extends DispatchAction {
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	private List<ContractMainInfo>contractNames;
	private List<YXClientCode>clients;
	private List<YXClientCode> yXClientCodes;
	private List<YXTypeManage>contractTypeList;
	@Override
	
	public String doDefault() throws Exception {
		logger.info("");
		return "main";
	}

	public String goLeftFram() throws Exception {
		return "leftFram";
	}

	@SuppressWarnings("unchecked")
	public String goLeftBotJsp() throws Exception {

		
		Employee user=UserUtils.getUser();
        contractTypeList=typeManageService.getYXTypeManage(1019L);
		yXClientCodes=(List<YXClientCode>)systemService.queryClients(user.getId());
		
		return "leftBotJsp";
	}
	@SuppressWarnings("unchecked")
	public String goLeftTopJsp()throws Exception{
		Long empId=UserUtils.getUser().getId();
		StringBuffer sp=new StringBuffer();
		
		sp.append("select c from ContractMainInfo c,YXClientCode y where c.saleMan="+empId+" and c.conCustomer=y.id");
		sp.append(" and (c.conState=0 or c.conState=2)");
		//String hqlSp="select y from YXClientCode y";
		//clients=(List<YXClientCode>)commonService.list(hqlSp);
		clients=(List<YXClientCode>)systemService.queryClients(empId);
		contractNames=(List<ContractMainInfo>)commonService.list(sp.toString());
		return "leftTopJsp";
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public List<YXClientCode> getYXClientCodes() {
		return yXClientCodes;
	}

	public void setYXClientCodes(List<YXClientCode> clientCodes) {
		yXClientCodes = clientCodes;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public List<ContractMainInfo> getContractNames() {
		return contractNames;
	}

	public void setContractNames(List<ContractMainInfo> contractNames) {
		this.contractNames = contractNames;
	}

	public List<YXClientCode> getClients() {
		return clients;
	}

	public void setClients(List<YXClientCode> clients) {
		this.clients = clients;
	}

	

	
}

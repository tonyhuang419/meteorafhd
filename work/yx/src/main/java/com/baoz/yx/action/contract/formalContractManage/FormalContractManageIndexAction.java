package com.baoz.yx.action.contract.formalContractManage;

import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.utils.UserUtils;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractManageIndex.jsp")
})
public class FormalContractManageIndexAction extends DispatchAction{
	
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	
	private List<ContractMainInfo>				conList;
	private List<Object[]> clientList  = null;  //客户列表索引...  1 客户合同数量[0]  2 客户系统号ID[1] 3 客户合同名称[2] 
	private Map<Long ,List<String[]>> customerConMap = null;  //客户合同列表(客户ID、合同名称)
	private Long saleId = null;            //销售员系统号
//	@SuppressWarnings("unused")
//	private int conStateSn = -1;           //合同状态序号...此处保持-1，在搜索时默认所有正式合同（未关闭）
	

//	public int getConStateSn() {
//		return -1;
//	}

//	public void setConStateSn(int conStateSn) {
//		this.conStateSn = conStateSn;
//	}


	public String doDefault() throws Exception {
		//获取登陆员工ID
		saleId = UserUtils.getUser().getId();
		clientList = foramlContractService.loadSaleContractCustomerIndex(saleId);
		customerConMap = foramlContractService.loadSaleContractGroupByCustomer(saleId);
		return SUCCESS;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public List<ContractMainInfo> getConList() {
		return conList;
	}

	public void setConList(List<ContractMainInfo> conList) {
		this.conList = conList;
	}

	public List<Object[]> getClientList() {
		return clientList;
	}

	public void setClientList(List<Object[]> clientList) {
		this.clientList = clientList;
	}

	public Map<Long, List<String[]>> getCustomerConMap() {
		return customerConMap;
	}

	public void setCustomerConMap(Map<Long, List<String[]>> customerConMap) {
		this.customerConMap = customerConMap;
	}

	public Long getSaleId() {
		return saleId;
	}
	
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

}

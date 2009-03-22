package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;


@Results( {
	@Result(name = "showinvoice", value = "/WEB-INF/jsp/contract/searchInvoice.jsp")
})
public class SearchInvoiceQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemservice;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService  queryService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	private ApplyBill applybill;
	
	private PageInfo info;
		
	private Long mainid;
	
	private List<Long> selectid;
	
	public String doDefault() throws Exception {
		logger.info("选择合同未签开票页面");
		logger.info(mainid);
		String str=" from ApplyBill where contractMainInfo=null and isNoContract = 1 and employeeId = "+UserUtils.getUser().getId()+"";
		info=queryService.listQueryResult(str,info);
		return "showinvoice";
	}
	
	
	public String conInvoice(){
		//将主合同号保存至发票表中
		System.out.println("关联开票");
		if(selectid!=null){
			if(selectid.size()!=0){
			 	for(int i=0;i<selectid.size();i++){
			    contractservice.conInvoice(mainid,selectid.get(i));
			 	}			 
			 }
		}
		ActionContext.getContext().put("isRelationSuccess", "true");
		return  "showinvoice";
	}

	public ISystemService getSystemservice() {
		return systemservice;
	}

	public void setSystemservice(ISystemService systemservice) {
		this.systemservice = systemservice;
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

	public Long getMainid() {
		return mainid;
	}

	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}

	public List getSelectid() {
		return selectid;
	}

	public ApplyBill getApplybill() {
		return applybill;
	}

	public void setApplybill(ApplyBill applybill) {
		this.applybill = applybill;
	}

	public void setSelectid(List<Long> selectid) {
		this.selectid = selectid;
	}

	
	
	
	
}

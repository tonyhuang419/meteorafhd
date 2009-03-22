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
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;


@Results( {
	@Result(name = "showpur", value = "/WEB-INF/jsp/contract/searchPur.jsp")
})
public class SearchPurQueryAction extends DispatchAction {
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
	
	private SelfProduction sp;
	
	private PageInfo info;
	
	private String productname;
	
	private Long mainid;
	
	private List<Long> selectid;
	
	public String doDefault() throws Exception {
		logger.info("选择合同未签申购页面");
		logger.info(mainid);
		String str=" from ApplyMessage where applyState = 0 and sellmanId =  "+UserUtils.getUser().getId()+"";
	    info=queryService.listQueryResult(str,info);
		return "showpur";
	}
	
	public String getPurList() throws Exception {
	
		logger.info("*****调用执行******"); 
		return "showpur";
	}
	
	public String conPur(){
		System.out.println("关联申购");	
		//保存合同号到申购
		if(selectid!=null){
			for(int i=0;i<selectid.size();i++){
			  contractservice.conPur(selectid.get(i),mainid);
			}
		}
		ActionContext.getContext().put("isRelationSuccess", "true");
		return  "showpur";
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

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public SelfProduction getSp() {
		return sp;
	}

	public void setSp(SelfProduction sp) {
		this.sp = sp;
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

	public void setSelectid(List selectid) {
		this.selectid = selectid;
	}
	
	
	
	
}

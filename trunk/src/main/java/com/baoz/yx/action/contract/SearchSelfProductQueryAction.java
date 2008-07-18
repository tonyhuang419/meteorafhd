package com.baoz.yx.action.contract;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.append.helper.StringAppender;
import com.opensymphony.xwork2.ActionContext;


@Results( {
	@Result(name = "showselfproduct", value = "/WEB-INF/jsp/contract/searchSelfProduct.jsp")
})
public class SearchSelfProductQueryAction extends DispatchAction {
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
	
	private Long savemainid;
	
	private List<Long> selectid;
	
	public String doDefault() throws Exception {
		logger.info("选择自有产品页面");
		logger.info(mainid);
		getSelfProductList();
		return "showselfproduct";
	}
	
	public String getSelfProductList() throws Exception {
		logger.info("执行查询");
		StringAppender hql = new StringAppender();
		hql.append(" from SelfProduction sp where sp.validateTime >= to_date('"+DateUtil.format(new Date(), "yyyy-MM-dd")+"','yyyy-MM-dd') ");
		hql.appendIfNotEmpty(" and sp.registerName like '%"+StringUtils.trim(productname)+"%' ", productname);
	    info=queryService.listQueryResult(hql.toString(),info);
		logger.info(mainid);
		logger.info("*****调用执行******"); 
		return "showselfproduct";
	}
	
	public String conProduct(){
		System.out.println("执行至此");
		if(selectid!=null){
	    	if(selectid.size()!=0){
		    	for(int i=0;i<selectid.size();i++){
		    		contractservice.conProduct(mainid,selectid.get(i));
		 	}		
		 }
		}
		ActionContext.getContext().put("isRelationSuccess", "true");
		return  "showselfproduct";
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

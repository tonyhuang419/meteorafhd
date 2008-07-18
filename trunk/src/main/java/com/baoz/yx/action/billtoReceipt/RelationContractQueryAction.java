package com.baoz.yx.action.billtoReceipt;


import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/relationContract.jsp"),
	@Result(name = "showContract", value = "/WEB-INF/jsp/billtoReceipt/searchContract.jsp"),
	@Result(name = "relation", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
})
public class RelationContractQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	
	
	
	private PageInfo					info;
	private String 						cName;
	private String 						cId;
	
	private Long 						cmId;  			//合同表的id
	private Long						ids;            //bill表的id
	
	@Override
	public String doDefault() throws Exception {
		logger.info("显示未签开票");
		info=queryService.listQueryResult(" from ApplyBill bill where bill.isNoContract = 1 order by bill.billApplyId desc", info);
		return SUCCESS;
	}
	
	public String searchContract()
	{
		logger.info("查询合同进行关联");
		StringBuffer str = new StringBuffer();
		str.append(" from ContractMainInfo cm where 1=1 ");
		if (StringUtils.isNotBlank(cName)) {
			str.append("and cm.conName like '%").append(cName+"%'");
		}
		if (StringUtils.isNotBlank(cId)) {
			str.append(" and cm.conId like '%").append(cId+"%'");
		}
		str.append("order by cm.conMainInfoSid desc");
		info = queryService.listQueryResult(str.toString(), info);
		return "showContract";
	}
	
	public String relationCon()
	{
		logger.info("确定关联合同");
		logger.info(ids);
		logger.info(cmId);
		//开票申请关联合同
		service.executeUpdate("update ApplyBill bill set bill.isNoContract =0,bill.contractMainInfo = '" + cmId + "' where bill.billApplyId = " + ids);
		//开票申请开得票关联合同
		invoiceService.relateContract(ids, cmId);		
		return "relation";
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


	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}


	public String getCName() {
		return cName;
	}

	public void setCName(String name) {
		cName = name;
	}

	public String getCId() {
		return cId;
	}

	public void setCId(String id) {
		cId = id;
	}

	public Long getCmId() {
		return cmId;
	}

	public void setCmId(Long cmId) {
		this.cmId = cmId;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
}

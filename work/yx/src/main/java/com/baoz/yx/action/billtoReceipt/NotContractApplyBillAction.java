package com.baoz.yx.action.billtoReceipt;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

@Results({
	@Result(name="success",value="/WEB-INF/jsp/billtoReceipt/notContractApplyBill.jsp")
})
public class NotContractApplyBillAction extends DispatchAction{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	private PageInfo 			info;
	
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		Long uid = new UserUtils().getUser().getId();
		info=queryService.listQueryResult(" from ApplyBill bill where bill.isNoContract = 1 and bill.employeeId = "+uid+"  order by bill.billApplyId desc ", info);
		return SUCCESS;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	
}

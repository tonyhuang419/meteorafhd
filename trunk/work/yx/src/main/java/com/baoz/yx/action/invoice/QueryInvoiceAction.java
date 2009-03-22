package com.baoz.yx.action.invoice;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.utils.UserUtils;

@Results( { @Result(name = "success", value = "/WEB-INF/jsp/invoiceManagement/queryInvoice.jsp"),
	@Result(name="showMain",value="/WEB-INF/jsp/invoiceManagement/mainInvoice.jsp"),
	@Result(name="openTest",value="/WEB-INF/jsp/jasperReport/applyBillJasper.jsp")
})

public class QueryInvoiceAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	
	private List<Object>        	yxClientCodeList;
	@Override
	public String doDefault() throws Exception {
		Long uid = UserUtils.getUser().getId();
		yxClientCodeList = commonService.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		return SUCCESS;
	}
	
	
	public String showMainInvoice()throws Exception{
		return "showMain";
	}
	
	public String openTest()throws Exception{
		return "openTest";
	}


	public ICommonService getCommonService() {
		return commonService;
	}


	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}


	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}


	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
}

package com.baoz.yx.action.contract;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXClientCode;

@Results({
	@Result(name="success" ,value="/WEB-INF/jsp/contract/showOneClientInfo.jsp")
	
})
public class ShowOneClientInfoAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	private Long clientId;//客户id
	
	private YXClientCode yxClientCode;//客户详细信息


	@Override
	public String doDefault() throws Exception {
		yxClientCode = (YXClientCode)commonService.load(YXClientCode.class, clientId);
		
		return SUCCESS;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public YXClientCode getYxClientCode() {
		return yxClientCode;
	}

	public void setYxClientCode(YXClientCode yxClientCode) {
		this.yxClientCode = yxClientCode;
	}
}

package com.baoz.yx.action.contract;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	显示主页面
 * 
 */
@Result(name = "success", value = "/WEB-INF/jsp/contract/iframe.jsp")

public class IframeQueryAction extends DispatchAction {
	@Override
	public String doDefault() throws Exception {
		this.logger.info("售前合同查询显示");
	
		return SUCCESS;
	}

}


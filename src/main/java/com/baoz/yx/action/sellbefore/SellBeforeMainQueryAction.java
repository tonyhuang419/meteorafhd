package com.baoz.yx.action.sellbefore;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;

/**
 * 显示售前合同管理mian
 */
@Results( {
		@Result(name = "showSellBeforeMain", value = "/WEB-INF/jsp/sellbefore/sellBeforeMain.jsp")})
public class SellBeforeMainQueryAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showSellBeforeMain";
	}
}


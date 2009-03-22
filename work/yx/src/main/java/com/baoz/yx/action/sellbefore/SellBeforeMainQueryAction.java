package com.baoz.yx.action.sellbefore;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;


@Results( {
		@Result(name = "showSellBeforeMain", value = "/WEB-INF/jsp/sellbefore/sellBeforeMain.jsp"),
		@Result(name = "sellbeforeLeftFrame", value = "/WEB-INF/jsp/sellbefore/sellBeforeLeftFrame.jsp")	
})
public class SellBeforeMainQueryAction extends DispatchAction {

	/**
	 * 显示售前合同管理mian
	 */
	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showSellBeforeMain";
	}
	
	/**
	 * 显示售前合同管左框架
	 */
	public String sellBeforeLeftFrame () throws Exception {
		return "sellbeforeLeftFrame";
	}
}


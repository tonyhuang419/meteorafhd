package com.baoz.yx.action.system;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;

/**
 * 显示自有产品管理
 */
@Results( {
		@Result(name = "showSellBeforeMain", value = "/WEB-INF/jsp/manage/system/selfproductManage/selfproductMain.jsp")})
public class SelfProductMainQueryAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showSellBeforeMain";
	}
}


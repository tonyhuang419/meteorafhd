package com.baoz.yx.action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;



@Result(name = "showMain", value = "/WEB-INF/jsp/main_view.jsp")

public class MainViewAction extends DispatchAction{
	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showMain";
	}

}

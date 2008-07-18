package com.baoz.yx.action.personnelManager;

import org.apache.struts2.config.Result;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	员工管理信息
 *  
 */
@Result(name = "personnelManager", value = "/WEB-INF/jsp/personnelManager/userMain.jsp")

public class ShowPersonnelMainAction extends DispatchAction {
	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示用户管理main");
		return "personnelManager";
	}
}


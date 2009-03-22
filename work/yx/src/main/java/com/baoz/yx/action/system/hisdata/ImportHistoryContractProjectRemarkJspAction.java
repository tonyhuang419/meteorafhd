package com.baoz.yx.action.system.hisdata;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Gonggao;

@Result(name = "queryList", value = "/WEB-INF/jsp/system/hisdata/importHistoryContractProjectRemark.jsp")
public class ImportHistoryContractProjectRemarkJspAction extends DispatchAction {

	public String doDefault() throws Exception {
		this.logger.info("======");
		return "queryList";
	}

}
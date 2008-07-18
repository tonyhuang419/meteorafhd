package com.baoz.yx.action.fileManager;


import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	收款明细管理显示
 *  
 */
@Result(name = "fileUp", value = "/WEB-INF/jsp/fileUpDown/fileUp.jsp")

public class FileUpQuery extends DispatchAction {

	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	private List<Object> 		yxClientCodeList;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("收款管理查询");
		yxClientCodeList=commonService.list("from YXClientCode d where d.id not in(0) and d.is_active=1");
		return "fileUp";
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


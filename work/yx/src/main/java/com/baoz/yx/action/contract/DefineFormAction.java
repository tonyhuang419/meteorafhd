package com.baoz.yx.action.contract;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IYXTypeManageService;

@Results(@Result(name = "showForm", value = "/WEB-INF/jsp/contract/defineForm.jsp"))
public class DefineFormAction extends DispatchAction {

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	@Override
	public String doDefault() throws Exception {

		return "showForm";
	}

}

package com.waterking.action.demo;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.waterking.action.BaseAction;
import com.waterking.service.ICommonService;
import com.waterking.utils.PageInfo;

@Results( {
	@Result(name = "demoPage", value = "/WEB-INF/jsp/demo/page.jsp")
})
public class PageAction extends BaseAction{
	private static final long serialVersionUID = -6929869573044077058L;


	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	private PageInfo info;

	public String demoPage(){
		ServletActionContext.getRequest ().getParameter("pageNo");
		info = commonService.listQueryResult(" from Book  " ,"", info);
//		commonService.execModifyProcedure();
		return "demoPage";
	}
	
	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}


}



package com.baoz.yx.action.system.material;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.opensymphony.xwork2.ActionContext;
/**
 * 应交材料管理
 *
 */
@Result(name = "success", value = "/WEB-INF/jsp/system/material/materialList.jsp")
public class MaterialQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService 			queryService;
	
	private PageInfo 				info;
	
	private String  				stage;
	@Override
	public String doDefault() throws Exception {
		if(ActionContext.getContext().getSession().get("stage")!=null){
			stage = (String)ActionContext.getContext().getSession().get("stage");
			ActionContext.getContext().getSession().remove("stage");
		}
		info = queryService.listQueryResult(" from MaterialManager mm order by mm.sortOrder ",info);
		return SUCCESS;
	}
	
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
}

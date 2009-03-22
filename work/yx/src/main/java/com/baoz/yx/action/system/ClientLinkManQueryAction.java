
package com.baoz.yx.action.system;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;

/**
 * 客户联系人查询
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/linkMan/clientLinkManList.jsp")
public class ClientLinkManQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo info;
	private List<YXTypeManage> linkmanNature; // 联系人性质
	
	private String 		clientName;
	 private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
	    		"SelectPerQueryParameters",this,new String[]{"clientName"});

	@Override
	public String doDefault() throws Exception {
		this.logger.info("客户联系人信息表");
		linkmanNature= typeManageService.getYXTypeManage(1003L);// 显示联系人性质
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		if (StringUtils.isNotBlank(clientName)) {
			str.append("select lm,yc.name,em from YXLinkMan lm,Employee em,YXClientCode yc where lm.is_active='1' and lm.byId= em.id and lm.clientId=yc.id ");
			String clientNames="'%"+clientName+"%'";
			str.append(" and yc.name like ").append(clientNames);
			str.append(" order by lm.id");	
			
			info = ParameterUtils.preparePageInfo(info, "clientLinkManQueryAction");
			
			info = queryService.listQueryResult(str.toString(), info);		
		}else{
			str.append("select lm,(select yc.name from YXClientCode yc where lm.clientId=yc.id ),em from YXLinkMan lm,Employee em where lm.is_active='1' and lm.byId= em.id ");
			str.append(" order by lm.id");	
			
			info = ParameterUtils.preparePageInfo(info, "clientLinkManQuery");
			
			info = queryService.listQueryResult("select count(*) from  YXLinkMan lm,Employee em where lm.is_active='1' and lm.byId= em.id ",str.toString(), info);
		}	
		return "queryList";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getLinkmanNature() {
		return linkmanNature;
	}

	public void setLinkmanNature(List<YXTypeManage> linkmanNature) {
		this.linkmanNature = linkmanNature;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
}


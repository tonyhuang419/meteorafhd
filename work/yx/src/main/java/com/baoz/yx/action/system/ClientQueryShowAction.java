package com.baoz.yx.action.system;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * 客户查询
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/client/clientQueryForm.jsp")
public class ClientQueryShowAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private PageInfo info;
	private List<YXTypeManage> clientNature; // 客户性质
	private List<YXTypeManage> businessType; // 行业类型
	private List<YXTypeManage> clientArea; // 客户地域
//	private List<YXTypeManage> businessUnit; // 是否为项目单位
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("员工信息表");
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		
//		
//		businessUnit = systemService.listA(hql4);// 显示是否为项目单位
//		info = queryService.listQueryResult(
//				" from YXClientCode d where d.id not in(0) and d.is_active!=0", info);
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



	public ISystemService getSystemService() {
		return systemService;
	}



	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}



	public List<YXTypeManage> getClientNature() {
		return clientNature;
	}



	public void setClientNature(List<YXTypeManage> clientNature) {
		this.clientNature = clientNature;
	}



	public List<YXTypeManage> getBusinessType() {
		return businessType;
	}



	public void setBusinessType(List<YXTypeManage> businessType) {
		this.businessType = businessType;
	}



	public List<YXTypeManage> getClientArea() {
		return clientArea;
	}



	public void setClientArea(List<YXTypeManage> clientArea) {
		this.clientArea = clientArea;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

}

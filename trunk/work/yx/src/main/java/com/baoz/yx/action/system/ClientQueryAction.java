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
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;

/**
 * 客户查询
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/client/clientList.jsp")
public class ClientQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo info;
	private List<YXTypeManage> clientNature; // 客户性质
	private List<YXTypeManage> businessType; // 行业类型
	private List<YXTypeManage> clientArea; // 客户地域
	private List<YXTypeManage> businessArea; // 行业市场
	private String 		name;
	private String		clientNID;
	private String		businessID;
	private String 		areaID;
	private String 		isEventUnit;
	
	private String fullName;
	
	
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"SelectPerQueryParameters",this,new String[]{"name","fullName","clientNID","businessID","areaID","isEventUnit"});
	
	
	public String doDefault() throws Exception {
		this.logger.info("客户信息表");
//		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
//		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
//		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
//		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场	
//		info = queryService.listQueryResult(
//				" from YXClientCode d where d.id not in(0) and d.is_active!=0 order by d.id desc", info);
//		return "queryList";
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
		
		
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append("select yc from YXClientCode yc where yc.is_active='1'");
	
		if (name!=null&&!"".equals(name)) {
			String names="'%"+name+"%'";
			str.append(" and yc.fullName like ").append(names);
		}
		if(StringUtils.isNotBlank(fullName)){
			str.append(" and yc.name like '%"+fullName+"%'");
		}
		if (clientNID!=null&&!"".equals(clientNID)) {
			str.append(" and yc.clientNID= '").append(clientNID).append("'");
		}
		if (businessID!=null&&!"".equals(businessID)) {
			str.append(" and yc.businessID= '").append(businessID).append("'");
		}
		if (areaID!=null&&!"".equals(areaID)) {
			str.append(" and yc.areaID= '").append(areaID).append("'");
		}
		if (isEventUnit!=null&&!"".equals(isEventUnit)) {
			str.append(" and yc.isEventUnit= '").append(isEventUnit).append("'");
		}
		str.append(" order by yc.id desc");
//		str.append(" order by  nlssort(yc.name,'NLS_SORT=SCHINESE_PINYIN_M')  ");
		
		info = ParameterUtils.preparePageInfo(info, "clientQuery");
		
		
		info = queryService.listQueryResult(str.toString(), info);
		return "queryList";
	}

//	public String doquery() throws Exception {
//		this.logger.info("根据条件查询客户表");
//		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
//		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
//		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
//		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
//		
//		
//		StringBuffer str = new StringBuffer();
//		ParameterUtils.prepareParameters(holdTool);
//		str.append("select yc from YXClientCode yc where yc.is_active='1'");
//	
//		if (name!=null&&!"".equals(name)) {
//			String names="'%"+name+"%'";
//			str.append(" and yc.name like ").append(names);
//		}
//		if (clientNID!=null&&!"".equals(clientNID)) {
//			str.append(" and yc.clientNID= ").append(clientNID);
//		}
//		if (businessID!=null&&!"".equals(businessID)) {
//			str.append(" and yc.businessID= ").append(businessID);
//		}
//		if (isEventUnit!=null&&!"".equals(isEventUnit)) {
//			str.append(" and yc.isEventUnit= ").append(isEventUnit);
//		}
//		str.append(" order by yc.id desc");
//		
//		
//		
//		
//		info = queryService.listQueryResult(str.toString(), info);
//		return "queryList";
//	}
	
	
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



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getClientNID() {
		return clientNID;
	}



	public void setClientNID(String clientNID) {
		this.clientNID = clientNID;
	}



	public String getBusinessID() {
		return businessID;
	}



	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}



	public String getAreaID() {
		return areaID;
	}



	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}
	public String getIsEventUnit() {
		return isEventUnit;
	}
	public void setIsEventUnit(String isEventUnit) {
		this.isEventUnit = isEventUnit;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(List<YXTypeManage> businessArea) {
		this.businessArea = businessArea;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}

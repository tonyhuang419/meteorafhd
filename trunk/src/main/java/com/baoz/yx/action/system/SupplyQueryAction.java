package com.baoz.yx.action.system;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.SupplierInfo;
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
@Result(name = "queryList", value = "/WEB-INF/jsp/system/supply/supplyList.jsp")
public class SupplyQueryAction extends DispatchAction {

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
	
	private List<YXTypeManage> supplyArea; // 供应商地域
	private List<YXTypeManage> supplierTypeList; // 供应商类型
	private String 		supplierName;
	private String 		supplierType;
	private String 		eareCode;
	
	  private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
	    		"SelectPerQueryParameters",this,new String[]{"supplierName","supplierType","eareCode"});
	@Override
	public String doDefault() throws Exception {
		this.logger.info("员工信息表");
		supplierTypeList = typeManageService.getYXTypeManage(1013L);// 显示所有供应商类型
    	supplyArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append("select si from SupplierInfo si where si.is_active='1'");
	
		if (supplierName!=null&&!"".equals(supplierName)) {
			String names="'%"+supplierName+"%'";
			str.append(" and si.supplierName like ").append(names);
		}
		if (supplierType!=null&&!"".equals(supplierType)) {
			str.append(" and si.supplierType= ").append(supplierType);
		}
		if (eareCode!=null&&!"".equals(eareCode)) {
			str.append(" and si.eareCode= ").append(eareCode);
		}

		str.append(" order by si.id desc");
		
		info = queryService.listQueryResult(str.toString(), info);
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



	public List<YXTypeManage> getSupplyArea() {
		return supplyArea;
	}



	public void setSupplyArea(List<YXTypeManage> supplyArea) {
		this.supplyArea = supplyArea;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}



	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}



	public List<YXTypeManage> getSupplierTypeList() {
		return supplierTypeList;
	}



	public void setSupplierTypeList(List<YXTypeManage> supplierTypeList) {
		this.supplierTypeList = supplierTypeList;
	}



	public String getSupplierName() {
		return supplierName;
	}



	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}



	public String getSupplierType() {
		return supplierType;
	}



	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}



	public String getEareCode() {
		return eareCode;
	}



	public void setEareCode(String eareCode) {
		this.eareCode = eareCode;
	}



	

}

package com.baoz.yx.action.system;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.ArrayList;
import java.util.List;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXChargemanDepartment;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;


/**
 * 部门负责人查询操作
 * 
 * @author 
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/manage/system/chargemanManage/chargemanList.jsp")
public class ChargemanQueryAction extends DispatchAction {
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private PageInfo info;
	private List<YXChargemanDepartment> departmentList;
	
	private String searchName;
	private String searchDepartment;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"ChargemanQueryParameter",this,new String[]{"searchName","searchDepartment"});

	@Override
	public String doDefault() throws Exception {
		this.logger.info("部门负责人查询");
		StringBuffer str1 = new StringBuffer();
		StringBuffer str2 = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str1.append("select c from YXChargeMan c where c.is_active=1");
		if (StringUtils.isNotBlank(searchName)){
			str1.append(" and c.name like '%").append(searchName).append("%'");
		}
		if (StringUtils.isNotBlank(searchDepartment)){
			str1.append(" and exists( select 1 from YXChargemanDepartment cd ,YXTypeManage t where cd.departmentid = t.typeSmall and t.typeBig = 1018 and t.typeName like '%").append(searchDepartment).append("%' and cd.chargemanid = c.id ) ");
		}
		str1.append(" order by c.id desc");	
		info = queryService.listQueryResult(str1.toString(), info);
		str2.append("select c from YXChargemanDepartment c ");
		/*if (StringUtils.isNotBlank(searchDepartment)){
			List<String> temp = new ArrayList<String>();
			temp = service.list("select obj.typeSmall from YXTypeManage obj where obj.typeBig=1018L and obj.typeName like '%" + searchDepartment + "%'");
			str2.append(" where c.departmentid in ('").append(temp).append("')");
		}*/
		departmentList = service.list(str2.toString());
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

	public List<YXChargemanDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<YXChargemanDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchDepartment() {
		return searchDepartment;
	}

	public void setSearchDepartment(String searchDepartment) {
		this.searchDepartment = searchDepartment;
	}
	
	
}

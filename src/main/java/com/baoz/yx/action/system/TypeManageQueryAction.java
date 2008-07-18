package com.baoz.yx.action.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.baoz.yx.utils.YxConstants;

@Result(name = "queryList", value = "/WEB-INF/jsp/manage/system/typeManage/typeManageList.jsp")
public class TypeManageQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
    private String						typeSmall;
    private String						typeBig;
    private String                      typeName;
    private String				lastUpdatedDate1;
    private String				lastUpdatedDate2;
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"TypeManageQueryParameters",this,new String[]{"typeSmall","typeBig","typeName","lastUpdatedDate1","lastUpdatedDate2"});
	@SuppressWarnings("unchecked")
	@Override
	public String doDefault() throws Exception {
		this.logger.info("类型管理信息查询");
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append("select t , e from YXTypeManage t,Employee e where t.is_active='1' and t.byId=e.id ");
		if (StringUtils.isNotBlank(typeSmall)) {
			str.append(" and t.typeSmall like '%").append(typeSmall).append("%'");
		}
		if (StringUtils.isNotBlank(typeBig)){
			str.append(" and t.typeBig = ").append(typeBig);
		}
		if (StringUtils.isNotBlank(typeName)) {
			str.append(" and t.typeName like '%").append(typeName).append("%'");
		}
		if (StringUtils.isNotBlank(lastUpdatedDate1)){
			str.append(" and t.updateBy >= to_date('"+lastUpdatedDate1+"','yyyy-MM-dd')");
		}
		if (StringUtils.isNotBlank(lastUpdatedDate2)){
			str.append(" and t.updateBy <= to_date('"+lastUpdatedDate2+"','yyyy-MM-dd')");
		}
		str.append(" order by t.typeBig , t.orderNum ");
		info = queryService.listQueryResult(str.toString(), info);
		return "queryList";
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub

	}
	public Map getTypeBigMap() {
		return YxConstants.typeBigMap;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public String getTypeSmall() {
		return typeSmall;
	}
	public void setTypeSmall(String typeSmall) {
		this.typeSmall = typeSmall;
	}
	public String getTypeBig() {
		return typeBig;
	}
	public void setTypeBig(String typeBig) {
		this.typeBig = typeBig;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getLastUpdatedDate1() {
		return lastUpdatedDate1;
	}
	public void setLastUpdatedDate1(String lastUpdatedDate1) {
		this.lastUpdatedDate1 = lastUpdatedDate1;
	}
	public String getLastUpdatedDate2() {
		return lastUpdatedDate2;
	}
	public void setLastUpdatedDate2(String lastUpdatedDate2) {
		this.lastUpdatedDate2 = lastUpdatedDate2;
	}
}

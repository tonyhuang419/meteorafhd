/**
 * 
 */
package com.baoz.yx.action;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.components.json.JSONResult;
import com.baoz.components.json.annotations.JSON;
import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author xurong Jun 10, 2008 5:35:45 PM
 */
@ParentPackage(value = "json-default")
@Results( { @Result(name = "success", type=JSONResult.class , value = "")})
public class JsonDataAction extends DispatchAction{
	private Object jsonData;
	/////////////
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	////////////
	/**
	 */
	public String doGetEmployeeOfDepartment(){
		String departmentCode = ((String[])ActionContext.getContext().getParameters().get("departmentCode"))[0];
		if(StringUtils.isNotBlank(departmentCode)){
			UserDetail userDetail = UserUtils.getUserDetail();
			if(DepartmentUtils.isTeamLeader(userDetail.getPosition().getOrganizationCode())){
				jsonData = service.list("select e from Employee e,OrganizationTree t where e.position = t.id and t.organizationCode like ? and e.is_active = '1'", departmentCode+"%");
			}else{
				jsonData = service.list("select e from Employee e where e.id = ? ", userDetail.getUser().getId());
			}
		}
		return "success";
	}
	/**
	 */
	public String doGetClientOfEmployee(){
		String employeeId = ((String[])ActionContext.getContext().getParameters().get("employeeId"))[0];
		if(StringUtils.isNotBlank(employeeId)){
			jsonData = service.list("select ec.cli from YXOEmployeeClient ec where ec.exp.id = ? and ec.cli.is_active = '1'", Long.valueOf(employeeId));
		}
		return "success";
	}
	/**
	 */
	public String doGetLinkMainOfClient(){
		String clientIdStr = ((String[])ActionContext.getContext().getParameters().get("clientId"))[0];
		if(StringUtils.isNotBlank(clientIdStr)){
			jsonData = service.list("from YXLinkMan l where l.clientId = ? and l.is_active = '1'", Long.valueOf(clientIdStr));
		}
		return "success";
	}
	
	public String doGetLinkMainOfUnits(){
		String unitsStr = ((String[])ActionContext.getContext().getParameters().get("unitsId"))[0];
		if(StringUtils.isNotBlank(unitsStr)){
			jsonData = service.uniqueResult("from YXClientCode y where y.id=?", Long.valueOf(unitsStr));
		}
		return "success";
	}
	/*
	 * 根据选择的客户选出项目和行业
	 * 
	 */
	public String doGetClientOfOther(){
		String otherStr = ((String[])ActionContext.getContext().getParameters().get("clientId"))[0];
		if(StringUtils.isNotBlank(otherStr)){
			jsonData = service.uniqueResult("select t from YXClientCode yc,YXTypeManage t where t.typeSmall = yc.businessID and t.typeBig = 1002 and yc.id = ?", Long.valueOf(otherStr));
		}
		return "success";
	}
	
	public String doGetChargeManOfDepartment(){
		String departmentCode = ((String[])ActionContext.getContext().getParameters().get("departmentCode"))[0];
		if(StringUtils.isNotBlank(departmentCode)){
			jsonData = service.list("select c from YXChargemanDepartment cd , YXChargeMan c where c.is_active = '1' and cd.chargemanid = c.id and cd.departmentid = ? ", departmentCode);
		}
		return "success";
	}
	
	
	//按票据类型，获取税率
	public String doGetTax(){
		String billType = ((String[])ActionContext.getContext().getParameters().get("billType"))[0];
		if(StringUtils.isNotBlank(billType)){
			jsonData = service.uniqueResult("select y.info from YXTypeManage y where " +
					" y.typeBig = 1004 and y.typeSmall = ?", billType);
		}
		return "success";
	}
	
	
	
	///////////////////
	@JSON(serialize=false,deserialize=false)
	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}
	//////////
	public Object getJsonData() {
		return jsonData;
	}
	public void setJsonData(Object jsonData) {
		this.jsonData = jsonData;
	}
}

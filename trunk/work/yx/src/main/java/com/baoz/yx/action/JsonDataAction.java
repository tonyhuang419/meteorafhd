/**
 * 
 */
package com.baoz.yx.action;

import java.util.HashMap;
import java.util.Map;

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
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.service.IFinalToCloseService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.ISystemService;
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

	@Autowired
	@Qualifier("finalToCloseService")
	private IFinalToCloseService finalToCloseService;

	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;

	@Autowired
	@Qualifier("beforeSellContractService")
	private IBeforeSellContractService beforeSellContractService;

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;

	////////////
	/**
	 */
	public String doGetEmployeeOfDepartment(){
		String departmentCode = ((String[])ActionContext.getContext().getParameters().get("departmentCode"))[0];
		if(StringUtils.isNotBlank(departmentCode)){
			UserDetail userDetail = UserUtils.getUserDetail();
			if(DepartmentUtils.isTeamLeader(userDetail.getPosition().getOrganizationCode())){
				jsonData = service.list("select e from Employee e,OrganizationTree t where e.position = t.id  and not exists( select 1 from Role r , RoleEmployee re where r.id = re.roleId and re.userId = e.id and r.code = '01' ) and t.organizationCode like ? and e.is_active = '1'" +
						" order by NLSSORT(e.name,'NLS_SORT = SCHINESE_PINYIN_M') ", departmentCode+"%");
			}else{
				jsonData = service.list("select e from Employee e where e.id = ? ", userDetail.getUser().getId());
			}
		}
		return "success";
	}

	public String doGetEmployeeOfDepartment2(){
		String departmentCode = ((String[])ActionContext.getContext().getParameters().get("departmentCode"))[0];
		jsonData = service.list("select e from Employee e,OrganizationTree t where e.position = t.id  and not exists( select 1 from Role r , RoleEmployee re where r.id = re.roleId and re.userId = e.id and r.code = '01' ) and t.organizationCode like ? and e.is_active = '1'" +
				" order by NLSSORT(e.name,'NLS_SORT = SCHINESE_PINYIN_M') ", departmentCode+"%");
		return "success";
	}


	public String displayPhone(){
		String clientIdStr = ((String[])ActionContext.getContext().getParameters().get("clientId"))[0];
		Object obj[] = null;
		if(StringUtils.isNotBlank(clientIdStr)){
			obj = (Object[])service.uniqueResult("select  l.phone,l.mobilephone  from YXChargeMan  l where l.id = ?  ", Long.valueOf(clientIdStr));
		}
		if(null == obj[0]){
			obj[0]="";
		}
		if(null == obj[1]){
			obj[1]="";
		}
		jsonData = obj[0].toString() + " "  + obj[1].toString();;
		return "success";
	}
	/**
	 * 查询组别所有销售员
	 * @return
	 */
	public String doGetEmployeeOfDepartmentSimple(){
		String departmentCode = ((String[])ActionContext.getContext().getParameters().get("departmentCode"))[0];
		if(StringUtils.isNotBlank(departmentCode)){
			jsonData = service.list("select e from Employee e,OrganizationTree t where e.position = t.id and not exists( select 1 from Role r , RoleEmployee re where r.id = re.roleId and re.userId = e.id and r.code = '01' ) and t.organizationCode like ? and e.is_active = '1'" +
					" order by NLSSORT(e.name,'NLS_SORT = SCHINESE_PINYIN_M') ", departmentCode+"%");
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
	/**
	 * 选择供应商获取供应商信息
	 * @return
	 */
	public String doGetSupplyInfoOfSupplyId(){
		String supId = ((String[])ActionContext.getContext().getParameters().get("supplyId"))[0];
		if(StringUtils.isNotBlank(supId)){
			jsonData = service.uniqueResult(" from SupplierInfo s where s.supplierid = ? ", Long.valueOf(supId));
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

	/*
	 * 获取客户性质
	 */
	public String doGetClientOfNature(){
		String otherStr = ((String[])ActionContext.getContext().getParameters().get("clientId"))[0];
		if(StringUtils.isNotBlank(otherStr)){
			jsonData = service.uniqueResult("select t from YXClientCode yc,YXTypeManage t where t.typeSmall = yc.clientNID and t.typeBig = 1001 and yc.id = ?", Long.valueOf(otherStr));
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


	//获取项目跟踪结果List（售前）
	public String doGetTrackList(){
		String trace = ((String[])ActionContext.getContext().getParameters().get("trace"))[0]; 
		if(StringUtils.isNotBlank(trace)){
			jsonData = beforeSellContractService.loadTrackList(trace);
		}
		return "success";
	}
	/**
	 * 检查客户的税号是否为空
	 * @return
	 */
	public String isTaxNoOfBillClientBlank(){
		String clientIdStr = ((String[])ActionContext.getContext().getParameters().get("billClientId"))[0];
		long clientId = Long.parseLong(clientIdStr);
		YXClientCode billClient = (YXClientCode) service.load(YXClientCode.class, clientId);
		Map<String, Boolean> ret = new HashMap<String, Boolean>();
		ret.put("isTaxNumberBlank", false);
		if(StringUtils.isBlank(billClient.getTaxNumber())){
			ret.put("isTaxNumberBlank", true);
		}
		jsonData = ret;
		return "success";
	}

	/**
	 * 检查客户的税号是否为空
	 * @return
	 */
	public String isTaxNoOfApplyBillNeeded(){
		String applyBillIdStr = ((String[])ActionContext.getContext().getParameters().get("applyBillId"))[0];
		long applyBillId = Long.parseLong(applyBillIdStr);
		ApplyBill applyBill = (ApplyBill) service.load(ApplyBill.class, applyBillId);
		YXClientCode billClient = (YXClientCode) service.load(YXClientCode.class, applyBill.getBillCustomer());
		Map<String, Boolean> ret = new HashMap<String, Boolean>();
		ret.put("isTaxNumberBlank", false);
		if(StringUtils.isBlank(billClient.getTaxNumber())
				&&"2".equals(applyBill.getBillType())//增票
		){
			ret.put("isTaxNumberBlank", true);
		}
		jsonData = ret;
		return "success";
	}
	/**
	 * 通过client端传入的合同系统号和合同费用组成系统号查询出项目名称和项目id
	 * @return
	 * @throws Exception
	 */
	public String doGetItemByConAndPart()throws Exception{
		Long mainid = Long.valueOf(((String[])ActionContext.getContext().getParameters().get("mainid"))[0]);
		Long partId = Long.valueOf(((String[])ActionContext.getContext().getParameters().get("partId"))[0]);
		jsonData = finalToCloseService.getItemByConAndPart(mainid, partId);
		return "success";
	}
	/**
	 * 通过client端传入的合同系统号和费用组成系统号查询出阶段名称和阶段id
	 * @return
	 * @throws Exception
	 */
	public String doGetStageByConAndPart()throws Exception{
		Long mainid = Long.valueOf(((String[])ActionContext.getContext().getParameters().get("mainid"))[0]);
		Long partId = Long.valueOf(((String[])ActionContext.getContext().getParameters().get("partId"))[0]);
		jsonData = finalToCloseService.getItemStageByConAndPart(mainid, partId);
		return "success";
	}

	/**
	 * 通过项目id查询出阶段的id和名称
	 * @return
	 * @throws Exception
	 */
	public String assistanceSectionByItemId()throws Exception{
		String[] str =(String[])(ActionContext.getContext().getParameters().get("itemSid"));
		Long itemSid = Long.valueOf(str[0]);
		jsonData = assistanceService.getStageByItemSid(itemSid);
		return "success";
	}

	//按票据类型，获取税率
	public String doGetTax(){
		String billType = ((String[])ActionContext.getContext().getParameters().get("billType"))[0];
		if(StringUtils.isNotBlank(billType)){
			jsonData = service.uniqueResult("select y.info from YXTypeManage y where " +
					" y.typeBig = 1004 and  y.is_active = '1'  and y.typeSmall = ?", billType);
		}
		return "success";
	}

	//检查合同号
	public String uniqueConNum(){
		String conNum = ((String[])ActionContext.getContext().getParameters().get("conNum"))[0];
		if(StringUtils.isNotBlank(conNum)){
			if(this.foramlContractService.uniqueConNum(conNum)){
				jsonData = "1";
			}
			else{
				jsonData = "0";
			}
		}
		return "success";
	}
	
	
	//检查供应商代码
	public String uniqueSupplyNum(){
		String supNum = ((String[])ActionContext.getContext().getParameters().get("orgNum"))[0];
		if(StringUtils.isNotBlank(supNum)){
			if(this.systemService.uniqueSupNum(supNum)){
				jsonData = "1";
			}
			else{
				jsonData = "0";
			}
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

	@JSON(serialize=false,deserialize=false)
	public IFinalToCloseService getFinalToCloseService() {
		return finalToCloseService;
	}
	public void setFinalToCloseService(IFinalToCloseService finalToCloseService) {
		this.finalToCloseService = finalToCloseService;
	}

	@JSON(serialize=false,deserialize=false)
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}


	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
}

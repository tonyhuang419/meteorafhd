package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IFinalToCloseService;
import com.baoz.yx.service.IFormalContractModifyService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( { @Result(name = "resultList", value = "/WEB-INF/jsp/contract/reservationList.jsp") })
public class SearchReservationReturnAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("finalToCloseService")
	private IFinalToCloseService finalToCloseService;
	
	@Autowired
	@Qualifier("formalContractModifyService")
	private IFormalContractModifyService contractservice;

	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;

	private List<YXTypeManage> projectDeptTypeList;// 工程部门列表

	private List<YXTypeManage> contractTypeList;

	private List<YXClientCode> yXClientCodeList;

	private PageInfo info;

	private String contractName;

	private String contractNo;

	private Long contractType;
	
	private Long mainid;
	
	private ObjectPropertySessionHoldTool propertyHoldTool = new ObjectPropertySessionHoldTool(
			"SearchReservationReturnParameters",this,new String[]{"contractName","contractNo","contractType"});
	
	

	// private String clietId;

	@Override
	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(propertyHoldTool);
		StringBuffer sp = new StringBuffer();
        Employee emp=UserUtils.getUser();
        String select="select c,e,(select cgm.changeContractState from ChangingContractMainInfo cgm where cgm.conMainInfoSid = c.conMainInfoSid)";
		sp.append(" from ContractMainInfo c,Employee e where c.saleMan=e.id and c.saleMan="+emp.getId());
		sp.append(" and c.FinalAccount='1'");
		sp.append(" and c.conState>=4");
		if (StringUtils.isNotBlank(contractName)) {
			sp.append(" and c.conName like '%" + contractName + "%'");
		}
		if (StringUtils.isNotBlank(contractNo)) {
			sp.append(" and c.conId like '%" + contractNo + "%'");
		}
		
		if (contractType!=null) {
			sp.append(" and c.ContractType=" + contractType + "");
		}
		info = ParameterUtils.preparePageInfo(info, "SearchReservationReturnCurrentPage");
		info = queryService.listQueryResult("select count(*) "+sp , select+sp.toString(), info);
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yXClientCodeList=systemService.queryAllClients();
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return "resultList";
	}
	
	public String applySubmit()throws Exception{
		logger.info("结算转决算申请提交");
		int result = finalToCloseService.isSureSubmit(mainid);
		if(result==0){
			contractservice.applySubmit(mainid,"1");
			
		}else{
			ActionContext.getContext().put("isSure", result);
		}
		logger.info("保存完毕，跳转至其他页面");        
		return doDefault();
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List<YXTypeManage> getProjectDeptTypeList() {
		return projectDeptTypeList;
	}

	public void setProjectDeptTypeList(List<YXTypeManage> projectDeptTypeList) {
		this.projectDeptTypeList = projectDeptTypeList;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXClientCode> getYXClientCodeList() {
		return yXClientCodeList;
	}

	public void setYXClientCodeList(List<YXClientCode> clientCodeList) {
		yXClientCodeList = clientCodeList;
	}

	public IFinalToCloseService getFinalToCloseService() {
		return finalToCloseService;
	}

	public void setFinalToCloseService(IFinalToCloseService finalToCloseService) {
		this.finalToCloseService = finalToCloseService;
	}

	public IFormalContractModifyService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IFormalContractModifyService contractservice) {
		this.contractservice = contractservice;
	}

	public Long getMainid() {
		return mainid;
	}

	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}

	public Long getContractType() {
		return contractType;
	}

	public void setContractType(Long contractType) {
		this.contractType = contractType;
	}

}

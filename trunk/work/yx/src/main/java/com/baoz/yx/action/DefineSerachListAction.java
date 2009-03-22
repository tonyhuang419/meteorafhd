package com.baoz.yx.action;

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
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
@Results({@Result(name="showList",value="/WEB-INF/jsp/contract/defineList.jsp")})
public class DefineSerachListAction extends DispatchAction{
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

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

	
	private Long[]mainId;
	private String msgAlert;
	// private String clietId;
	
	private ObjectPropertySessionHoldTool propertyHoldTool = new ObjectPropertySessionHoldTool(
		"defineSerachListParameters",this,new String[]{"contractName","contractNo","contractType"}
		);

	@Override
	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(propertyHoldTool);
		StringBuffer sp = new StringBuffer();
        Employee emp=UserUtils.getUser();
		sp
				.append("select c,e from ChangingContractMainInfo c,Employee e where c.saleMan=e.id and c.changeContractState=1 ");
		
		/*if(clietId!=null&&!clietId.equals("")){
			sp.append(" and c.id="+new Long(getClietId()));
		}*/
		sp.append(" and c.changeType='2'");//表示结算转决算过来的变更
		sp.append(" and c.conState>=4");
		//sp.append(" and c.ContractType='4'");
		if (!StringUtils.isBlank(contractName)) {
			sp.append(" and c.conName like '%" + contractName + "%'");
		}
		if (!StringUtils.isBlank(contractNo)) {
			sp.append(" and c.conId like '%" + contractNo + "%'");
		}
		if (contractType!=null) {
			sp.append(" and c.ContractType=" + contractType);
		}
		info = ParameterUtils.preparePageInfo(info, "defineSerachListCurrentPage");
		info = queryService.listQueryResult(sp.toString(), info);
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yXClientCodeList=systemService.queryAllClients();
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return "showList";
	}
	public String cirformOKToContractMain()throws Exception{
		contractService.cirformOkChangeMain(mainId);
		setMsgAlert("操作成功!");
		return doDefault();
	}
    public String removeChangeConMains()throws Exception{
    	contractService.deleteChangeConMains(mainId);
    	setMsgAlert("成功退回!");
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

	public Long[] getMainId() {
		return mainId;
	}

	public void setMainId(Long[] mainId) {
		this.mainId = mainId;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public String getMsgAlert() {
		return msgAlert;
	}
	public void setMsgAlert(String msgAlert) {
		this.msgAlert = msgAlert;
	}
	public Long getContractType() {
		return contractType;
	}
	public void setContractType(Long contractType) {
		this.contractType = contractType;
	}

}

package com.baoz.yx.action.contract;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name="success",value="/WEB-INF/jsp/contract/assistanceApplyCon.jsp"),
	@Result(name="saveSuccess",value="/WEB-INF/jsp/contract/assistanceApplySuccess.jsp"),
	@Result(name = "interim", value = "/WEB-INF/jsp/purchase/interim.jsp")	
})
public class AssistanceApplyQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 					service;

	private AssistanceContract				a;

	private Long 							mainId;
	private Long							eventId;

	private List<ContractMainInfo>			mainContract;
	private List<ContractItemMaininfo>		mainProject;

	private Long 							empName;
	private String 							contractId;
	private String							contractName;
	private String 							contractProjectId;
	private Long 							contractMId;

	private Long 							supplyName;

	private String sign;                  //0保存 1提交

	@Override
	public String doDefault() throws Exception {
		logger.info("外协申请");
		logger.info("合同信息"+mainId);
		logger.info("项目号id"+eventId);
		mainContract = service.list(" select c.conId,c.conName,emp.name,c.conMainInfoSid from ContractMainInfo c,Employee emp where c.saleMan = emp.id and c.conMainInfoSid = " + mainId);
		mainProject = service.list(" select item.conItemId from ContractItemMaininfo item where item.conItemMinfoSid =" + eventId);
		return SUCCESS;
	}

	public String saveAContract()
	{
		logger.info("保存外协申请信息");
		logger.info("合同号"+contractId);
		logger.info("合同名称"+contractName);
		logger.info("项目主体号"+contractProjectId);
		logger.info(supplyName+"sssssssssssssss");
		a.setAssistanceId(contractId);
		a.setAssistanceName(contractName);
		a.setMainProjectId(contractProjectId);
		a.setContractId(contractMId);
		a.setSupplyId(supplyName);

		a.setAssistanceContractType("0");
		a.setIs_active("1");
		a.setUpdateBy(new Date());
		a.setById(UserUtils.getUser().getId());

		if("1".equals(sign)){
			a.setAssistanceType("1");
			ActionContext.getContext().getSession().put("conStat","4");
		}
		else{
			a.setAssistanceType("0");
			ActionContext.getContext().getSession().put("conStat","3");
		}
		service.save(a);
		return "interim";
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public List<ContractMainInfo> getMainContract() {
		return mainContract;
	}

	public void setMainContract(List<ContractMainInfo> mainContract) {
		this.mainContract = mainContract;
	}

	public List<ContractItemMaininfo> getMainProject() {
		return mainProject;
	}

	public void setMainProject(List<ContractItemMaininfo> mainProject) {
		this.mainProject = mainProject;
	}

	public Long getEmpName() {
		return empName;
	}

	public void setEmpName(Long empName) {
		this.empName = empName;
	}



	public AssistanceContract getA() {
		return a;
	}

	public void setA(AssistanceContract a) {
		this.a = a;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractProjectId() {
		return contractProjectId;
	}

	public void setContractProjectId(String contractProjectId) {
		this.contractProjectId = contractProjectId;
	}

	public Long getContractMId() {
		return contractMId;
	}

	public void setContractMId(Long contractMId) {
		this.contractMId = contractMId;
	}

	public Long getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(Long supplyName) {
		this.supplyName = supplyName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}



}

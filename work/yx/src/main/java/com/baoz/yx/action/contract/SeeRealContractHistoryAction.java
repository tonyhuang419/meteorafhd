package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IYXTypeManageService;
@Results( { @Result(name = "success", value = "/WEB-INF/jsp/contract/findPlanHistory.jsp") })
public class SeeRealContractHistoryAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
    private String mainId;
    private String dept;
    private String htxz;
    private String htNo;
    private String receId;
    private InitContractBillpro billpro;
    private List<YXTypeManage> openBillType;//开票性质
	private List<YXTypeManage>projectDeptTypeList;//工程部门列表
	private List<BillReceChangeHistory>bilhistoryList;
	@SuppressWarnings("unchecked")
	@Override
	public String doDefault() throws Exception {
		//logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa="+mainId+"bbbbbbbbbbbbbbbbb="+dept+"ccccccccccccccc="+htxz);
		//ContractMainInfo cminfo=(ContractMainInfo) service.load(ContractMainInfo.class, new Long(mainId));
		RealContractBillandRecePlan plan=(RealContractBillandRecePlan) service.load(RealContractBillandRecePlan.class, new Long(receId));
		ContractMainInfo cminfo=(ContractMainInfo) service.load(ContractMainInfo.class, plan.getConMainInfoSid());
		setHtNo(cminfo.getConId());
		openBillType=typeManageService.getYXTypeManage(1012L);
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		billpro=(InitContractBillpro) service.load(InitContractBillpro.class, plan.getInitContractBillpro());
		bilhistoryList=service.list("from BillReceChangeHistory b where b.historyType = '0' and b.realContractBillandRecePlan=? order by b.billproChangeHisSid ", new Object[]{plan});
		htNo=cminfo.getConId();
		return SUCCESS;
	}
	public String getOpName(Long opId){
		Employee emp=(Employee)service.load(Employee.class, opId);
		return emp.getName();
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getHtxz() {
		return htxz;
	}
	public void setHtxz(String htxz) {
		this.htxz = htxz;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public String getHtNo() {
		return htNo;
	}
	public void setHtNo(String htNo) {
		this.htNo = htNo;
	}
	
	public List<YXTypeManage> getProjectDeptTypeList() {
		return projectDeptTypeList;
	}
	public void setProjectDeptTypeList(List<YXTypeManage> projectDeptTypeList) {
		this.projectDeptTypeList = projectDeptTypeList;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getOpenBillType() {
		return openBillType;
	}
	public void setOpenBillType(List<YXTypeManage> openBillType) {
		this.openBillType = openBillType;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public List<BillReceChangeHistory> getBilhistoryList() {
		return bilhistoryList;
	}
	public void setBilhistoryList(List<BillReceChangeHistory> bilhistoryList) {
		this.bilhistoryList = bilhistoryList;
	}
	public String getReceId() {
		return receId;
	}
	public void setReceId(String receId) {
		this.receId = receId;
	}
	public InitContractBillpro getBillpro() {
		return billpro;
	}
	public void setBillpro(InitContractBillpro billpro) {
		this.billpro = billpro;
	}

}

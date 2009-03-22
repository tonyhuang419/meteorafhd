package com.baoz.yx.action.assistance;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractService;
@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/splitAssistanceSction.jsp"),
	@Result(name = "showSplitSction", value = "/WEB-INF/jsp/assistance/splitSectionAmount.jsp"),
	@Result(name = "showSplit", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
	} )
public class SplitSectionAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	
	private String ids;
	private String userName;
	private AssistanceContract ac;
	private SupplierInfo supInfo;   //外协供应商信息
	private List<AssistanceContract>    accistanceList;   //外协合同信息
	private List<AssistanceSection> sectionList;           //阶段信息
	
	private Long[] sectionId;
	private AssistanceSection assSection;
	private Double SplitAmount;
	private Date sectionBillTime;
	@Override
	public String doDefault() throws Exception {
		
		accistanceList = service.list(" select ac,sin.supplierName,cm.conName,cm.conId from AssistanceContract ac,SupplierInfo sin,ContractMainInfo cm " +
				"where ac.contractId = cm.conMainInfoSid and ac.supplyId = sin.supplierid and ac = " + Long.parseLong(ids));
		// 查询外协合同
		String hql = "from AssistanceContract ac where ac.id="
				+ Long.parseLong(ids);
		ac = (AssistanceContract) service.uniqueResult(hql);
		// 查询供应商信息
		String sId = ac.getSupplyId().toString();
		if (sId != null && !"".equals(sId)) {
			String sHql = "from SupplierInfo s where s.supplierid=" + sId;
			supInfo = (SupplierInfo) service.uniqueResult(sHql);
		}
		
		userName = assistanceService.getAContractName(Long.parseLong(ids));

		sectionList = service.list(" from AssistanceSection a where a.assistanceId ="+Long.parseLong(ids));
		return SUCCESS;
	}
	public String updateSectionDate(){
		AssistanceSection section = (AssistanceSection) service.load(AssistanceSection.class, sectionId[0]);
		section.setSectionBillTime(sectionBillTime);
		service.update(section);
		return "showSplit";
	}
	/**
	 * 显示拆分页面
	 * @return
	 */
	public String showSplitSction(){
		assSection = (AssistanceSection) service.load(AssistanceSection.class, sectionId[0]);
		return "showSplitSction";
	}
	
	/**
	 * 进行拆分处理
	 * @return
	 */
	public String splitSection(){
		assistanceService.splitSectionAmount(sectionId[0], SplitAmount);
		return "showSplit";
	}
	/**
	 * 合并阶段信息
	 * @return
	 */
	public String mergeSection(){
		assistanceService.mergeRealPlan(sectionId);
		return "showSplit";
	}
	
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AssistanceContract getAc() {
		return ac;
	}

	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}

	public SupplierInfo getSupInfo() {
		return supInfo;
	}

	public void setSupInfo(SupplierInfo supInfo) {
		this.supInfo = supInfo;
	}

	public List<AssistanceContract> getAccistanceList() {
		return accistanceList;
	}

	public void setAccistanceList(List<AssistanceContract> accistanceList) {
		this.accistanceList = accistanceList;
	}

	public List<AssistanceSection> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<AssistanceSection> sectionList) {
		this.sectionList = sectionList;
	}
	public Long[] getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long[] sectionId) {
		this.sectionId = sectionId;
	}
	public AssistanceSection getAssSection() {
		return assSection;
	}
	public void setAssSection(AssistanceSection assSection) {
		this.assSection = assSection;
	}
	public Double getSplitAmount() {
		return SplitAmount;
	}
	public void setSplitAmount(Double splitAmount) {
		SplitAmount = splitAmount;
	}
	public Date getSectionBillTime() {
		return sectionBillTime;
	}
	public void setSectionBillTime(Date sectionBillTime) {
		this.sectionBillTime = sectionBillTime;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}


	
}

package com.baoz.yx.action.system.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemOpAssistanceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;

@Results({
	@Result(name = "assistancePayInfoApply",value="/WEB-INF/jsp/system/basedata/assistance/assistancePayInfoApply.jsp"),
	@Result(name = "relationPrepList",value="/WEB-INF/jsp/system/basedata/assistance/relationPrepList.jsp"),
	@Result(name = "cancelPayInfoApply",value="/WEB-INF/jsp/system/basedata/assistance/cancelPayInfoApply.jsp"),
	@Result(name = "cancelPrep",value = "/WEB-INF/jsp/system/basedata/assistance/cancelPrep.jsp"),
	@Result(name = "relationPrep",value = "/WEB-INF/jsp/system/basedata/assistance/relationPrep.jsp")
})
public class SystemOpAssistanceAction extends DispatchAction{
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	
	@Autowired
	@Qualifier("systemOpAssistanceService")
	private ISystemOpAssistanceService systemOpAssistanceService;
	
	@Autowired
	@Qualifier("assistancePayService")
	private IAssistancePayService assistancePayService;
	
	private String assistanceConNo;////外协合同号
	
	private AssistanceContract assistanceContract;/////外协合同
	
	private ContractMainInfo contract;///////主题合同信息
	
	private ContractItemMaininfo contractItem;/////主题项目信息
	
	private SupplierInfo supply;///外协供应商信息
	
	private List<AssistanceSection> sectionList;////外协合同的阶段列表
	
	private List<AssistancePayInfo> prepPayInfoList;////预付外协付款申请单列表
	
	
	private List<AssistancePayInfo> unPrepPayInfoList;///非预付外协付款申请单列表
	
	
	private List<AssistancePayInfo> payInfoList;/////同一个合同下面的所有的外协付款申请单
	
	private  String addSign;///新增外协付款申请的标记，1表示查询成功，0表示未查询
	
	private String cancelSign;///取消外协付款申请的标记，1表示查询。0表示未查询
	
	private String cancelPaySign;///取消关联的标记，1表示查询。0表示未查询
	
	private String relationPaySign;///建立关联的标记，1表示查询，0表示未查询
	
	private Long[] sectionIds;//////选中的外协id数组
	
	private Long[] payInfoIds;/////付款申请id的数组
	
	private Long payInfoId;////付款申请单的id
	
	private Date applyPayDate;///付款申请日期
	
	private Boolean isPrepPay;//是否预付
	
	
	private PageInfo info;
	
	private ProcessResult result;
	
	private Long[] prepayIds;///关联的预付款申请的id列表
	
	/**
	 * 显示新增付款申请页面
	 * @return
	 * @throws Exception
	 */
	public String showPayInfoApplyPage()throws Exception{
		return "assistancePayInfoApply";
	}
	/**
	 * 查询外协付款申请信息
	 * @return
	 * @throws Exception
	 */
	public String searchAssiConThenPayApply()throws Exception{	
		showAssistanceAndSupply(assistanceConNo);
		isPrepPay=Boolean.FALSE;
		return "assistancePayInfoApply";
	}
	
	public String relationPrepList() throws Exception{
		AssistanceContract contract = (AssistanceContract)commonService.load(AssistanceContract.class, assistanceContract.getId());
		prepPayInfoList = assistanceService.getAssistancepayInfoBySupplierId(contract);
		return "relationPrepList";
	}	
	public String saveApplyPayInfo()throws  Exception{
		
		////保存外协付款申请，此时能给的只有外协合同id，外协阶段的id数组，还有付款申请的日期
		////需要做的操作主要有：1，外协发票的录入，2外协付款申请的录入，3外协发票和付款申请的录入
		
		Map<Long,AssistancePayInfo> tempMap = new HashMap<Long,AssistancePayInfo>();
		if(prepayIds!=null&&prepayIds.length>0){
			for( int i = 0 ;i <prepayIds.length; i ++ ){
				AssistancePayInfo pay = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, prepayIds[i]);
				tempMap.put(pay.getId(), pay);
			}
		}
		List<AssistancePayInfo> opList = new ArrayList<AssistancePayInfo>();
		opList.addAll(tempMap.values());
		AssistanceContract as = (AssistanceContract) commonService.load(AssistanceContract.class, assistanceContract.getId());
		List<AssistanceSection> secList = assistancePayService.getSectionBySectionIds(sectionIds);
		AssistancePayInfo payInfo = new AssistancePayInfo();
		payInfo.setApplyDate(applyPayDate);
		payInfo.setEmployeeId(as.getEmployeeId());
		payInfo.setApplyPay(isPrepPay);
		payInfo.setById(UserUtils.getUser().getId());
		payInfo.setUpdateBy(new Date());
		systemOpAssistanceService.createAssistanceApplyPayInfo(as, secList, payInfo,opList);
		/**
		 * 显示页面信息
		 */
		
		return searchAssiConThenPayApply();
		
	}
	
	
	
	private void showAssistanceAndSupply(String assistanceId)throws Exception{
		String contractHql = "from AssistanceContract contract where lower(contract.assistanceId) = ?";
		assistanceContract = (AssistanceContract) commonService.uniqueResult(contractHql, StringUtils.lowerCase(assistanceId.trim()));
		if(assistanceContract !=null){
			supply = (SupplierInfo)commonService.load(SupplierInfo.class, assistanceContract.getSupplyId());
			contract = (ContractMainInfo) commonService.load(ContractMainInfo.class,assistanceContract.getContractId());
			contractItem = (ContractItemMaininfo)commonService.load(ContractItemMaininfo.class, assistanceContract.getConItemMainInfoSid());
			sectionList = assistanceService.getAssistanceSectionByContractId(assistanceContract.getId());
			addSign = "1";
		}
	}
	/**
	 * 显示取消外协付款申请
	 * @return
	 * @throws Exception
	 */
	public String showCancelPayInfoApply() throws Exception{
		return "cancelPayInfoApply";
	}
	
	public String searchAssiConThenCancelPayApply() throws Exception{
		
		String contractHql = "from AssistanceContract contract where lower(contract.assistanceId) = ?";
		assistanceContract = (AssistanceContract) commonService.uniqueResult(contractHql, StringUtils.lowerCase(assistanceConNo.trim()));
		if(assistanceContract != null){
			StringBuffer sb = new StringBuffer();
			sb.append("select pi, " +
					"ac.assistanceId," +
					"ac.assistanceName," +
					" si.supplierName,emp.name  " +
					"from AssistancePayInfo pi, SupplierInfo si , Employee emp,AssistanceContract ac  " +
					" where pi.is_active='1' and  ac.id = pi.assistanceId " +
					"and pi.supplyId = si.supplierid " +
					"and emp.id = pi.employeeId  and ac.id = ?");
			info = queryService.listQueryResult(sb.toString(),info,assistanceContract.getId());
			cancelSign = "1";
		}
		return "cancelPayInfoApply";
	}
	
	public String cancelPayApply() throws Exception{
		result = systemOpAssistanceService.cancelAssistanceApplyPayInfo(payInfoId);
		return searchAssiConThenCancelPayApply();
	}
	
	
	/**
	 *显示取消关联
	 * @return
	 * @throws Exception
	 */
	public String showCancelPage() throws Exception{
		return "cancelPrep";
	}
	/**
	 * 取消关联查询
	 * @return
	 * @throws Exception
	 */
	public String searchCancelPayInfo()throws Exception{
		String contractHql = "from AssistanceContract contract where lower(contract.assistanceId) = ?";
		assistanceContract = (AssistanceContract) commonService.uniqueResult(contractHql, StringUtils.lowerCase(assistanceConNo.trim()));
		if(assistanceContract != null){
			StringBuffer sb = new StringBuffer();
			sb.append("select pi, " +
					"ac.assistanceId," +
					"ac.assistanceName," +
					" si.supplierName,emp.name  " +
					"from AssistancePayInfo pi, SupplierInfo si , Employee emp,AssistanceContract ac  " +
					" where pi.is_active='1' and  ac.id = pi.assistanceId " +
					"and pi.supplyId = si.supplierid " +
					"and emp.id = pi.employeeId and (pi.applyPay is null or pi.applyPay !=1) and exists (select 1 from AssistancePayInfo prepPay where prepPay.relationPrePayId = pi.id )  and ac.id = ?");
			info = queryService.listQueryResult(sb.toString(),info,assistanceContract.getId());
			cancelPaySign = "1";
		}
		
		return "cancelPrep";
	}
	
	
	public String cancelPrep()throws Exception{
		result = systemOpAssistanceService.cancelPrep(payInfoId);
		return searchCancelPayInfo();
	}
	/**
	 * 显示建立关联
	 * @return
	 * @throws Exception
	 */
	public String showRelationPage() throws Exception{
		
		return "relationPrep";
	}
	/**
	 * 建立关联查询
	 * @return
	 * @throws Exception
	 */
	public String searchRelationPayInfo() throws Exception{
		return "relationPrep";
	}
	
	public String getAssistanceConNo() {
		return assistanceConNo;
	}

	public void setAssistanceConNo(String assistanceConNo) {
		this.assistanceConNo = assistanceConNo;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public AssistanceContract getAssistanceContract() {
		return assistanceContract;
	}
	public void setAssistanceContract(AssistanceContract assistanceContract) {
		this.assistanceContract = assistanceContract;
	}
	public SupplierInfo getSupply() {
		return supply;
	}
	public void setSupply(SupplierInfo supply) {
		this.supply = supply;
	}
	public List<AssistanceSection> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<AssistanceSection> sectionList) {
		this.sectionList = sectionList;
	}
	public List<AssistancePayInfo> getPrepPayInfoList() {
		return prepPayInfoList;
	}
	public void setPrepPayInfoList(List<AssistancePayInfo> prepPayInfoList) {
		this.prepPayInfoList = prepPayInfoList;
	}
	public List<AssistancePayInfo> getUnPrepPayInfoList() {
		return unPrepPayInfoList;
	}
	public void setUnPrepPayInfoList(List<AssistancePayInfo> unPrepPayInfoList) {
		this.unPrepPayInfoList = unPrepPayInfoList;
	}
	public List<AssistancePayInfo> getPayInfoList() {
		return payInfoList;
	}
	public void setPayInfoList(List<AssistancePayInfo> payInfoList) {
		this.payInfoList = payInfoList;
	}
	public ContractMainInfo getContract() {
		return contract;
	}
	public void setContract(ContractMainInfo contract) {
		this.contract = contract;
	}
	public ContractItemMaininfo getContractItem() {
		return contractItem;
	}
	public void setContractItem(ContractItemMaininfo contractItem) {
		this.contractItem = contractItem;
	}
	public String getAddSign() {
		return addSign;
	}
	public void setAddSign(String addSign) {
		this.addSign = addSign;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
	public Long[] getSectionIds() {
		return sectionIds;
	}
	public void setSectionIds(Long[] sectionIds) {
		this.sectionIds = sectionIds;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public Date getApplyPayDate() {
		return applyPayDate;
	}
	public void setApplyPayDate(Date applyPayDate) {
		this.applyPayDate = applyPayDate;
	}
	public Boolean getIsPrepPay() {
		return isPrepPay;
	}
	public void setIsPrepPay(Boolean isPrepPay) {
		this.isPrepPay = isPrepPay;
	}
	public ISystemOpAssistanceService getSystemOpAssistanceService() {
		return systemOpAssistanceService;
	}
	public void setSystemOpAssistanceService(
			ISystemOpAssistanceService systemOpAssistanceService) {
		this.systemOpAssistanceService = systemOpAssistanceService;
	}
	public IAssistancePayService getAssistancePayService() {
		return assistancePayService;
	}
	public void setAssistancePayService(IAssistancePayService assistancePayService) {
		this.assistancePayService = assistancePayService;
	}
	public String getCancelSign() {
		return cancelSign;
	}
	public void setCancelSign(String cancelSign) {
		this.cancelSign = cancelSign;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public Long[] getPayInfoIds() {
		return payInfoIds;
	}
	public void setPayInfoIds(Long[] payInfoIds) {
		this.payInfoIds = payInfoIds;
	}
	public ProcessResult getResult() {
		return result;
	}
	public void setResult(ProcessResult result) {
		this.result = result;
	}
	public Long getPayInfoId() {
		return payInfoId;
	}
	public void setPayInfoId(Long payInfoId) {
		this.payInfoId = payInfoId;
	}
	public String getCancelPaySign() {
		return cancelPaySign;
	}
	public void setCancelPaySign(String cancelPaySign) {
		this.cancelPaySign = cancelPaySign;
	}
	public String getRelationPaySign() {
		return relationPaySign;
	}
	public void setRelationPaySign(String relationPaySign) {
		this.relationPaySign = relationPaySign;
	}
	public Long[] getPrepayIds() {
		return prepayIds;
	}
	public void setPrepayIds(Long[] prepayIds) {
		this.prepayIds = prepayIds;
	}

	
}

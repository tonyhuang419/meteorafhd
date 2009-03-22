package com.baoz.yx.action.contract;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IRealContractBillandRecePlanService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;

@Results( {

@Result(name = "retlBillList", value = "/WEB-INF/jsp/contract/realContractBill.jsp") })
public class RealContractBillandRecePlanAction extends DispatchAction {
	@Autowired
	@Qualifier("recePlanService")
	private IRealContractBillandRecePlanService recePlanService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	private Date beforBillDate;

	private Date beforReceDate;

	private Date afterChangeDate;

	private Date afterReceDate;
	
	private String billChangeType;//开票计划变更类型
	
	private List<YXTypeManage> changeTypeList;//开票计划变更类型列表

	private String changeExp;
	private Long realConBillproSid;
	private Long[]monthlyBillproSids;
	private String mesaage;
	private List retlBillList;
	private Long mainId;// 合同Id
	private List<YXTypeManage> openBillType;//开票性质
	private List<YXTypeManage>fapBillType;//发票性质
	private List<YXTypeManage>projectDeptTypeList;//工程部门列表
	
	private List<BillReceChangeHistory> bilhistoryList;//变更履历
	
	private String sign;    //1 变更保存
	
	public List<BillReceChangeHistory> getBilhistoryList() {
		return bilhistoryList;
	}
	public void setBilhistoryList(List<BillReceChangeHistory> bilhistoryList) {
		this.bilhistoryList = bilhistoryList;
	}
	public String getBillChangeType() {
		return billChangeType;
	}
	public void setBillChangeType(String billChangeType) {
		this.billChangeType = billChangeType;
	}
	public List<YXTypeManage> getChangeTypeList() {
		return changeTypeList;
	}
	public void setChangeTypeList(List<YXTypeManage> changeTypeList) {
		this.changeTypeList = changeTypeList;
	}
	@SuppressWarnings("unchecked")

	public String openApplyMs() throws Exception {
		
//		StringBuffer sp = new StringBuffer();
//		sp.append("select r,c,i,(select im.itemResDept from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid) " +
//				"from RealContractBillandRecePlan r,ContractItemStage c,InitContractBillpro i " +
//				"where r.conMainInfoSid="+ mainId);
//		sp.append(" and r.conItemStage=c.conIStageSid " +
//				"and r.initContractBillpro=i.initConBillproSid " +
//				"and c.conIStageSid=i.conItemStage ");
//		retlBillList=recePlanService.findRealContractBillandRecePlans(sp.toString());
//		openBillType=typeManageService.getYXTypeManage(1012L);
//		fapBillType=typeManageService.getYXTypeManage(1004L);
//		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		//changeTypeList=typeManageService.getYXTypeManage(1024L);
		RealContractBillandRecePlan plan=(RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realConBillproSid);
		ContractMainInfo cminfo=(ContractMainInfo) commonService.load(ContractMainInfo.class, plan.getConMainInfoSid());
	
		openBillType=typeManageService.getYXTypeManage(1012L);
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		bilhistoryList=commonService.list("from BillReceChangeHistory b where b.historyType = '0' and b.realContractBillandRecePlan=? order by b.billproChangeHisSid ", new Object[]{plan});
		return "retlBillList";
	}
	
	public String getOpName(Long opId){
		Employee emp=(Employee)commonService.load(Employee.class, opId);
		return emp.getName();
	}
	@Override
	public String doDefault()throws Exception{
		logger.info(monthlyBillproSids);
		logger.info("id是："+realConBillproSid);
		changeTypeList=typeManageService.getYXTypeManage(1024L);
		RealContractBillandRecePlan plan=(RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realConBillproSid);
		beforBillDate = plan.getRealPredBillDate();
		beforReceDate = plan.getRealPredReceDate();
		bilhistoryList=commonService.list("from BillReceChangeHistory b where b.historyType = '0' and b.realContractBillandRecePlan=? order by b.billproChangeHisSid ", new Object[]{plan});
		return "retlBillList";
	}	
	public String saveRealHistory() throws Exception {
		RealContractBillandRecePlan rpec = (RealContractBillandRecePlan) commonService
				.load(RealContractBillandRecePlan.class, realConBillproSid);
		
		BillReceChangeHistory history = new BillReceChangeHistory();
		history.setRealContractBillandRecePlan(rpec);
		history.setBeforBillDate(rpec.getRealPredBillDate());
		history.setAfterChangeDate(afterChangeDate);
		history.setBillChangeType(billChangeType);
		history.setBeforReceDate(rpec.getRealPredReceDate());
		history.setAfterReceDate(afterReceDate);
		history.setChangeTime(new Date());
		history.setChangePeople(UserUtils.getUser().getId());
		history.setHistoryType(YxConstants.PLAN_CHANGE_HISTORY_TYPE_BILL);
		history.setChangeExp(changeExp);
		//增加计数
		Integer billChangeCount = rpec.getBillChangeCount();
		if(billChangeCount == null){
			billChangeCount = 0;
		}
		billChangeCount++;
		rpec.setRealPredBillDate(afterChangeDate);	
		rpec.setRealPredReceDate(afterReceDate);
		rpec.setBillChangeCount(billChangeCount);
		commonService.save(history);
		commonService.update(rpec);
		logger.info(getMesaage());
		sign = "1";
		return doDefault();
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public IRealContractBillandRecePlanService getRecePlanService() {
		return recePlanService;
	}

	public void setRecePlanService(
			IRealContractBillandRecePlanService recePlanService) {
		this.recePlanService = recePlanService;
	}

	public List getRetlBillList() {
		return retlBillList;
	}

	public void setRetlBillList(List retlBillList) {
		this.retlBillList = retlBillList;
	}

	public List<YXTypeManage> getOpenBillType() {
		return openBillType;
	}

	public void setOpenBillType(List<YXTypeManage> openBillType) {
		this.openBillType = openBillType;
	}

	public List<YXTypeManage> getFapBillType() {
		return fapBillType;
	}

	public void setFapBillType(List<YXTypeManage> fapBillType) {
		this.fapBillType = fapBillType;
	}

	

	public List<YXTypeManage> getProjectDeptTypeList() {
		return projectDeptTypeList;
	}

	public void setProjectDeptTypeList(List<YXTypeManage> projectDeptTypeList) {
		this.projectDeptTypeList = projectDeptTypeList;
	}
	public Long[] getMonthlyBillproSids() {
		return monthlyBillproSids;
	}
	public void setMonthlyBillproSids(Long[] monthlyBillproSids) {
		this.monthlyBillproSids = monthlyBillproSids;
	}
	public Date getAfterChangeDate() {
		return afterChangeDate;
	}
	public void setAfterChangeDate(Date afterChangeDate) {
		this.afterChangeDate = afterChangeDate;
	}
	public Date getAfterReceDate() {
		return afterReceDate;
	}
	public void setAfterReceDate(Date afterReceDate) {
		this.afterReceDate = afterReceDate;
	}
	public Date getBeforBillDate() {
		return beforBillDate;
	}
	public void setBeforBillDate(Date beforBillDate) {
		this.beforBillDate = beforBillDate;
	}
	public Date getBeforReceDate() {
		return beforReceDate;
	}
	public void setBeforReceDate(Date beforReceDate) {
		this.beforReceDate = beforReceDate;
	}
	public String getChangeExp() {
		return changeExp;
	}
	public void setChangeExp(String changeExp) {
		this.changeExp = changeExp;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public String getMesaage() {
		return mesaage;
	}
	public void setMesaage(String mesaage) {
		this.mesaage = mesaage;
	}
	public Long getRealConBillproSid() {
		return realConBillproSid;
	}
	public void setRealConBillproSid(Long realConBillproSid) {
		this.realConBillproSid = realConBillproSid;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}

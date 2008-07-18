package com.baoz.yx.action.contract;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IRealContractBillandRecePlanService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

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

	private String changeExp;
	private Long realConBillproSid;
	private Long[]monthlyBillproSids;
	private String mesaage;
	private List retlBillList;
	private Long mainId;// 合同Id
	private List<YXTypeManage> openBillType;//开票性质
	private List<YXTypeManage>fapBillType;//发票性质
	private List<YXTypeManage>projectDeptTypeList;//工程部门列表
	
	
	@SuppressWarnings("unchecked")

	public String openApplyMs() throws Exception {
		
		StringBuffer sp = new StringBuffer();
		sp
				.append("select r,c,i,ci from RealContractBillandRecePlan r,ContractItemStage c,InitContractBillpro i,ContractItemMaininfo ci where r.conMainInfoSid="
						+ mainId);
		sp
				.append(" and r.conItemStage=c.conIStageSid and r.initContractBillpro=i.initConBillproSid and c.conIStageSid=i.conItemStage and r.contractItemMaininfo=ci.conItemMinfoSid");
		retlBillList=recePlanService.findRealContractBillandRecePlans(sp.toString());
		openBillType=typeManageService.getYXTypeManage(1012L);
		fapBillType=typeManageService.getYXTypeManage(1004L);
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return "retlBillList";
	}
	@Override
	public String doDefault()throws Exception{
		
		logger.info(monthlyBillproSids);
		StringBuffer sp = new StringBuffer();
		for(Long l:monthlyBillproSids){
			logger.info("monthlyBillproSids======"+l);
		}
		sp.append("select r,c,i,ci from RealContractBillandRecePlan r,ContractItemStage c,InitContractBillpro i,ContractItemMaininfo ci where ");
		sp.append(" r.conItemStage=c.conIStageSid and r.initContractBillpro=i.initConBillproSid and c.conIStageSid=i.conItemStage and r.contractItemMaininfo=ci.conItemMinfoSid");
		sp.append(" and r.realConBillproSid IN (").append(StringUtils.join(monthlyBillproSids,",")).append(") ");
		retlBillList=recePlanService.findRealContractBillandRecePlans(sp.toString());
		openBillType=typeManageService.getYXTypeManage(1012L);
		fapBillType=typeManageService.getYXTypeManage(1004L);
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return "retlBillList";
	}
	
	public String saveRealHistory() throws Exception {
        logger.info("mainId============================"+mainId);
        logger.info("monthlyBillproSids========="+monthlyBillproSids);
             for(Long l:monthlyBillproSids){
             logger.info("monthlyBillproSids"+l);
        }
		RealContractBillandRecePlan rpec = (RealContractBillandRecePlan) commonService
				.load(RealContractBillandRecePlan.class, realConBillproSid);
		rpec.setRealPredBillDate(afterChangeDate);	
		rpec.setRealPredReceDate(afterReceDate);
		BillReceChangeHistory history = new BillReceChangeHistory();
		history.setRealContractBillandRecePlan(rpec);
		if (beforBillDate != null && !beforBillDate.equals("")) {
			history.setBeforBillDate(beforBillDate);
		} 
		if (beforReceDate != null && !beforReceDate.equals("")) {
			history.setBeforReceDate(beforReceDate);
		} 
		if (afterChangeDate != null && !afterChangeDate.equals("")) {
			history.setAfterChangeDate(afterChangeDate);
		}
		if (afterReceDate != null && !afterReceDate.equals("")) {
			history.setAfterReceDate(afterReceDate);
		}
		history.setChangeTime(new Date());
		history.setChangePeople(UserUtils.getUser().getId());
		history.setChangeExp(changeExp);
		
		commonService.save(history);
		commonService.update(rpec);
		
		setMesaage("success");
		logger.info(getMesaage());
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

}

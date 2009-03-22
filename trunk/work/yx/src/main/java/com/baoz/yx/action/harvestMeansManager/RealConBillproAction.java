package com.baoz.yx.action.harvestMeansManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name = "query", value = "/WEB-INF/jsp/harvestMeansManager/realConBillproManage.jsp"),
	@Result(name = "updateEnter", value = "/WEB-INF/jsp/harvestMeansManager/popUpdateRealConBillpro.jsp"),
	@Result(name = "updateSuc", value = "/WEB-INF/jsp/assistance/showSuc.jsp")
})
public class RealConBillproAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	private PageInfo info;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService yxTypeManageService;

	private Long realConBillproSid;// 实际合同开票收款计划表系统号

	private String conId;// 合同号

	private String itemId;// 项目号

	private String startDate;// 开始日期

	private String endDate;// 结束日期

	private RealContractBillandRecePlan billAndReceplan;

	private String realPredReceDate;

	private String changeReason;//变更理由
	
	private List<YXTypeManage> changeTypeList=new ArrayList<YXTypeManage>();
	
	private String receChangeType;
	
	private List<BillReceChangeHistory> changeHistoryList=new ArrayList<BillReceChangeHistory>();//变更履历列表

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"realConBillproParameters", this, new String[] { "conId", "itemId",
					"startDate", "endDate" });

	@Override
	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer select = new StringBuffer(
				"select maininfo.conId as conId," +
				"maininfo.conName as conName," +
				"(select itemmain.conItemId from ContractItemMaininfo itemmain where itemmain.conItemMinfoSid=realplan.contractItemMaininfo and rownum<=1) as conItemId," +
				"(select typemanage.typeName from ContractItemMaininfo itemmain,YXTypeManage typemanage where itemmain.itemResDept=typemanage.typeSmall and typemanage.typeBig=1018 and itemmain.conItemMinfoSid=realplan.contractItemMaininfo and rownum<=1) as dept,"+
				"realplan ");
		StringBuffer fromwhere=new StringBuffer();
		fromwhere.append(" from RealContractBillandRecePlan realplan,"+
				"ContractMainInfo maininfo "+
				"where maininfo.saleMan =  " + UserUtils.getUser().getId()+
				" and realplan.conMainInfoSid=maininfo.conMainInfoSid "+
				" and (realplan.realTaxReceAmount<>realplan.realArriveAmount or realplan.realArriveAmount is null)");
		if (conId != null && conId.length() > 0) {
			fromwhere.append(" and maininfo.conId='" + conId + "'");
		}
		if (itemId != null && itemId.length() > 0) {
			fromwhere.append(" and exists(select 1 from ContractItemMaininfo itemmain where itemmain.conItemMinfoSid=realplan.contractItemMaininfo and itemmain.conItemId='" + itemId + "') ");
		}
		if (startDate != null && startDate.length() > 0) {
			fromwhere.append(" and to_date('" + startDate
					+ "', 'yyyy-MM-dd') <= realplan.realPredReceDate ");
		}
		if (endDate != null && endDate.length() > 0) {
			fromwhere.append(" and to_date('" + endDate
					+ "', 'yyyy-MM-dd') >= realplan.realPredReceDate ");
		}
		String count="select count(*) "+fromwhere.toString()+"";
		info = queryService.listQueryResult(count,select.toString()+fromwhere.toString(), info);
		return "query";
	}

	public String checkOperator() throws Exception {

		return null;
	}

	public String updateEnter() throws Exception {
		realPredReceDate = null;
		receChangeType = null;
		changeReason = null;
		billAndReceplan = (RealContractBillandRecePlan) commonService
		.load(RealContractBillandRecePlan.class, realConBillproSid);
		String hql="from BillReceChangeHistory bill where bill.historyType = '1' and bill.realContractBillandRecePlan=?" +
				" order by bill.changeTime";
		changeHistoryList=commonService.list(hql,billAndReceplan);
		changeTypeList=yxTypeManageService.getYXTypeManage(1025L);
		return "updateEnter";
	}
	public String getOpName(Long opId){
		Employee emp=(Employee)commonService.load(Employee.class, opId);
		return emp.getName();
	}
	public String updateRealPredReceDate()throws Exception{

		RealContractBillandRecePlan oldPlan=new RealContractBillandRecePlan();
		oldPlan.setRealConBillproSid(realConBillproSid);
		//BeanUtils.copyProperties(oldPlan, billAndReceplan);
		oldPlan.setRealPredReceDate(NumberToTime.getStringConvertDate(realPredReceDate));
		BillReceChangeHistory history=new BillReceChangeHistory();
		history.setBeforReceDate(billAndReceplan.getRealPredReceDate());
		history.setAfterReceDate(oldPlan.getRealPredReceDate());
		history.setChangeTime(new Date());
		history.setHistoryType(YxConstants.PLAN_CHANGE_HISTORY_TYPE_RECE);
		history.setReceChangeType(receChangeType);
		history.setChangeExp(changeReason);
		history.setChangePeople(UserUtils.getUser().getId());
		contractCommonService.updateBillAndReceplan(oldPlan, history);
		ActionContext.getContext().put("updateSuccess", "true");
		return updateEnter();
	}
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public Long getRealConBillproSid() {
		return realConBillproSid;
	}

	public void setRealConBillproSid(Long realConBillproSid) {
		this.realConBillproSid = realConBillproSid;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public RealContractBillandRecePlan getBillAndReceplan() {
		return billAndReceplan;
	}

	public void setBillAndReceplan(RealContractBillandRecePlan billAndReceplan) {
		this.billAndReceplan = billAndReceplan;
	}

	public String getRealPredReceDate() {
		return realPredReceDate;
	}

	public void setRealPredReceDate(String realPredReceDate) {
		this.realPredReceDate = realPredReceDate;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	public List<BillReceChangeHistory> getChangeHistoryList() {
		return changeHistoryList;
	}

	public void setChangeHistoryList(List<BillReceChangeHistory> changeHistoryList) {
		this.changeHistoryList = changeHistoryList;
	}

	public List<YXTypeManage> getChangeTypeList() {
		return changeTypeList;
	}

	public void setChangeTypeList(List<YXTypeManage> changeTypeList) {
		this.changeTypeList = changeTypeList;
	}

	public IYXTypeManageService getYxTypeManageService() {
		return yxTypeManageService;
	}

	public void setYxTypeManageService(IYXTypeManageService yxTypeManageService) {
		this.yxTypeManageService = yxTypeManageService;
	}

	public String getReceChangeType() {
		return receChangeType;
	}

	public void setReceChangeType(String receChangeType) {
		this.receChangeType = receChangeType;
	}

}

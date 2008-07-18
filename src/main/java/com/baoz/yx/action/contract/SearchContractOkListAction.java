package com.baoz.yx.action.contract;

import java.util.ArrayList;
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
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ContractStateTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

@Results( { @Result(name = "contractList", value = "/WEB-INF/jsp/contract/contractOkList.jsp"),@Result(name="upcNo",value="/WEB-INF/jsp/contract/updateContractNo.jsp") })
public class SearchContractOkListAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;

	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService applyBillService;
	
	@Autowired
	@Qualifier("noticeService")
	private INoticeService noticeservice;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	private List<String> messages = null;
    private String groupId;
	private String expId;

	private String customerId;

	private String conType;

	private String conState;

	private Double minMoney;

	private Double maxMoney;

	private String minConSignDate;

	private String maxConSignDate;

	private List<YXTypeManage> contractTypeList;

	private Long[] conMainId;

	private String states;
    private String msgalert="";
	private Long[] conStates;// 对应确认退回
    private String resNubmer;//项目号
	private PageInfo info;

	@Override
	public String doDefault() throws Exception {
		logger.info("excute!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.info("expId===============================" + expId);
		logger.info("customerId===============================" + customerId);
		logger.info("conType===============================" + conType);
		logger.info("minMoney===============================" + minMoney);
		logger.info("maxMoney===============================" + maxMoney);
		logger.info("minConSignDate==============================="
				+ minConSignDate);
		logger.info("maxConSignDate==============================="
				+ maxConSignDate);
		Employee emp = UserUtils.getUser();
		StringBuffer sp = new StringBuffer();
		sp.append("select c,e,y from ContractMainInfo c,Employee e,YXClientCode y,OrganizationTree orgTree " +
				"where c.saleMan=e.id and e.position=orgTree.id and c.conCustomer=y.id "	);

//		"where c.saleMan=e.id and e.position=orgTree.id and c.conCustomer=y.id and c.saleMan="
//		+ emp.getId());
		
		if (conState != null && !conState.equals("")) {
			sp.append(" and c.conState=" + conState);
		} else {
			sp.append(" and (c.conState=1 or c.conState=3)");
		}
		
//		这活是商务组干的。。。不需要这个
//		 不是组长,只能查自己
//		UserDetail user = UserUtils.getUserDetail();
//		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
//			expId = user.getUser().getId()+"";
//		}
//		if (groupId != null && !"".equals(groupId)) {
//			sp.append(" and orgTree.organizationCode like '").append(groupId+"%'");
//		}
		
		if (expId != null && !expId.equals("")) {
			sp.append(" and e.id='" + expId + "'");
		}
		if (customerId != null && !customerId.equals("")) {
			sp.append(" and y.id='" + customerId + "'");
		}
		if (conType != null && !conType.equals("")) {
			sp.append(" and c.conType='" + conType + "'");
		}
		if (minMoney != null && maxMoney != null) {
			sp.append(" and c.conTaxTamount>=" + minMoney
					+ " and c.conTaxTamount<=" + maxMoney);
		}
		if (minMoney != null && maxMoney == null) {
			sp.append(" and c.conTaxTamount>=" + minMoney);

		}
		if (maxMoney != null && minMoney == null) {
			sp.append(" and c.conTaxTamount<=" + maxMoney);
		}
		if (minConSignDate != null && !minConSignDate.equals("")
				&& maxConSignDate != null && !maxConSignDate.equals("")) {

			sp.append(" and c.conSignDate>=" + "to_date('" + minConSignDate
					+ "','yyyy-mm-dd')" + " and c.conSignDate<=" + "to_date('"
					+ maxConSignDate + "','yyyy-mm-dd')");
		}
		if (minConSignDate != null && !minConSignDate.equals("")
				&& (maxConSignDate == null || maxConSignDate.equals(""))) {
			sp.append(" and c.conSignDate>=" + "to_date('" + minConSignDate
					+ "','yyyy-mm-dd')");
		}
		if (maxConSignDate != null && !maxConSignDate.equals("")
				&& (minConSignDate == null || minConSignDate.equals(""))) {
			sp.append(" and c.conSignDate<=" + "to_date('" + maxConSignDate
					+ "','yyyy-mm-dd')");
		}
		sp.append(" order by c.conState asc");
		
		logger.info("----------------------------" + sp);
		
		info = queryService.listQueryResult(sp.toString(), info);
		contractTypeList = typeManageService.getYXTypeManage(1019L);
		return "contractList";
	}

	public String changeState() throws Exception {

		for (Long id : conMainId) {
			logger.info("id=========================" + id);
		}
		String aa = request().getParameter("aa");
		logger.info("states===============" + aa);
		logger.info("iddd================="
				+ new Long(ContractStateTool.getContractStateNameToSn(states)));

		contractService.updateContractMainState(conMainId, new Long(
				ContractStateTool.getContractStateNameToSn(states)));
		return doDefault();
	}

	public String changeTrueState() throws Exception {
		
		logger.info("conMainId[0]");
		ContractMainInfo cm=(ContractMainInfo) commonService.load(ContractMainInfo.class, conMainId[0]);
		if(cm.getImportFromFile()!=null){
			if(cm.getImportFromFile()){
				cm.setConState(4L);
				commonService.update(cm);
				msgalert="转正式合同成功";
				return doDefault();
			}
		}
		contractService.changeZSOneComain(conMainId[0]);
		msgalert="转正式合同成功";
		return doDefault();
	}

	/**
	 * 转预合同
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changeOrderState() throws Exception {
		messages = new ArrayList<String>();
		List<ContractMainInfo> cmains = contractService
				.findContractByState(conMainId);
		List<ContractMainInfo> view = new ArrayList<ContractMainInfo>();
		for (ContractMainInfo c : cmains) {
			if (c.getConState() == 4) {
				messages.add("正式合同不能再转预合同");
			} else {
				view.add(c);
			}
		}
		if(view.size()>0){
			msgalert="转预合同成功";
		}
		contractService.changeState(view, new Long(3));

		return doDefault();
	}
	public String changeTrueAndJC()throws Exception{
		logger.info("changeTrueAndJC Excute!!!!!");
		logger.info("id:::::::::::::::::::888888888888888888888888888888888888="+conMainId[0]);
		ContractMainInfo cm=(ContractMainInfo) commonService.load(ContractMainInfo.class, conMainId[0]);
		if(cm.getImportFromFile()!=null){
			if(cm.getImportFromFile()){
				cm.setConState(4L);
				commonService.update(cm);
				msgalert="转正式合同成功";
				return doDefault();
			}
		}
		return "upcNo";
	}

	/**
	 * 确认退回
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirmReturn() throws Exception {
		int i=0;
		messages = new ArrayList<String>();
		List<ContractMainInfo> cmains = contractService
				.findContractByState(conMainId);
		List<ContractMainInfo> view = new ArrayList<ContractMainInfo>();
		for (ContractMainInfo c : cmains) {
			if (c.getConState() == 1) {
				String str1 = "您的草稿合同已被确认退回";
				noticeservice.addNotice(str1,c.getSaleMan());
				view.add(c);
			} else {
				i=1;
				messages.add("其中合同名称为" + c.getConName() + "已经确认不能退回");
			}
		}
		if(i==0){
			msgalert="确认退回成功!";
		}
		if(i==1&&view.size()<1){
			msgalert="确认退回失败";
		}
		if(i==1&&view.size()>1){
			msgalert="部分退回成功部分不能退回";
		}
		contractService.changeState(view, new Long(2));

		return doDefault();
	}

	/**
	 * 取消确认
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cancelConfirm() throws Exception {
		logger.info("excute conceConfirm!!!!!!!!!!");
		messages = new ArrayList<String>();
		List<ContractMainInfo> view = new ArrayList<ContractMainInfo>();
		List<ContractMainInfo> contracts = contractService
				.findContractByState(conMainId);
		logger.info("excute conceConfirm2!!!!!!!!!!" + contracts.size());
		for (ContractMainInfo c : contracts) {
			if (c.getConState() == ContractStateTool
					.getContractStateNameToSn("正式合同")) {
				List<ApplyBill> bills = applyBillService
						.findApplyBillsByContractNo(c.getConMainInfoSid());
				if (checkApplyBill(bills) == true) {
					messages.add("其中合同名称为" + c.getConName() + "已开票不能取消确认");
					// contracts.remove(c);
				} else {
					
					view.add(c);
				}
			} else {


				view.add(c);
			}

		}
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<size>>>>>>>>>>>>>>>>>>>>>>"
				+ contracts.size());
		contractService.concelCirform(view);
		logger.info("11111111111111111111111");
		return doDefault();
	}

	@SuppressWarnings("unused")
	private boolean checkApplyBill(List<ApplyBill> bills) {
		for (ApplyBill b : bills) {
			if (b.getApplyBillState() == 3) {

				return true;
			}
			logger.info("checkApplyBill'sate====" + b.getApplyBillState());
		}

		return false;
	}
   public String doUpdateConNo()throws Exception{
	   logger.info(":::::::::::::::::::::::::::::::::::::"+conMainId[0]);
	   logger.info(":::::::::::::::::::::::::"+resNubmer);
	   ContractMainInfo cmain= contractService.loadContractMainInfo(conMainId[0]);
	   contractService.chageZSTwoComain(conMainId[0], resNubmer, cmain);
//	   contractService.copysked(conMainId[0]);
	   msgalert="转正式合同成功";
	   return doDefault();
   }
	public String getExpId() {
		return expId;
	}

	public void setExpId(String expId) {
		this.expId = expId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public String getMaxConSignDate() {
		return maxConSignDate;
	}

	public void setMaxConSignDate(String maxConSignDate) {
		this.maxConSignDate = maxConSignDate;
	}

	public String getMinConSignDate() {
		return minConSignDate;
	}

	public void setMinConSignDate(String minConSignDate) {
		this.minConSignDate = minConSignDate;
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

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public Long[] getConMainId() {
		return conMainId;
	}

	public void setConMainId(Long[] conMainId) {
		this.conMainId = conMainId;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public String getConState() {
		return conState;
	}

	public void setConState(String conState) {
		this.conState = conState;
	}

	public IApplyBillService getApplyBillService() {
		return applyBillService;
	}

	public void setApplyBillService(IApplyBillService applyBillService) {
		this.applyBillService = applyBillService;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Long[] getConStates() {
		return conStates;
	}

	public void setConStates(Long[] conStates) {
		this.conStates = conStates;
	}

	public String getResNubmer() {
		return resNubmer;
	}

	public void setResNubmer(String resNubmer) {
		this.resNubmer = resNubmer;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMsgalert() {
		return msgalert;
	}

	public void setMsgalert(String msgalert) {
		this.msgalert = msgalert;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public INoticeService getNoticeservice() {
		return noticeservice;
	}

	public void setNoticeservice(INoticeService noticeservice) {
		this.noticeservice = noticeservice;
	}

}

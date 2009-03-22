package com.baoz.yx.action.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.ITestService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ContractStateTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

//项目类合同转正式合同方法
@Results( {
	@Result(name = "contractList", value = "/WEB-INF/jsp/contract/contractOkList.jsp"),
	@Result(name = "toSureContractIdPage", value = "/WEB-INF/jsp/contract/toSureContractIdPage.jsp"),
	@Result(name = "toPrepare", value = "/WEB-INF/jsp/contract/contractOkToPrepare.jsp"),
	@Result(name = "showConfirmCancel", value = "/WEB-INF/jsp/contract/writeConfirmCancel.jsp"),
	@Result(name = "jicheng",type = ServletRedirectResult.class, value = "/contract/searchContractOkJCList.action"),
	@Result(name = "upcNo", value = "/WEB-INF/jsp/contract/updateContractNo.jsp") })
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
	
	@Autowired
	@Qualifier("testService")
	private ITestService testService;

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

	private Long conMainid;
	
	private String states;

	private String msgalert = "";

	private Long[] conStates;// 对应确认退回

	private String resNubmer;// 项目号

	private PageInfo info;
	
	private String isSureContractId;  //记录用户是否已经手动确认的值
	
	private String contractId; //合同号
	
	private Long isModify;

	private String toPrepareConNum;  //转预合同号

	private String XMJC;   //判断是项目还是集成
	
	private String returnReason; //退回理由

	@Override
	public String doDefault() throws Exception {
		logger.info("excute!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.info("expId===============================" + expId);
		logger.info("customerId===============================" + customerId);
		logger.info("conType===============================" + conType);
		logger.info("conState===============================" + conState);
		logger.info("minMoney===============================" + minMoney);
		logger.info("maxMoney===============================" + maxMoney);
		logger.info("minConSignDate==============================="
				+ minConSignDate);
		logger.info("maxConSignDate==============================="
				+ maxConSignDate);
		Employee emp = UserUtils.getUser();
		StringBuffer sp = new StringBuffer();
		sp
		.append("select c,e,y from ContractMainInfo c,Employee e,YXClientCode y,OrganizationTree orgTree "
				+ "where c.saleMan=e.id and e.position=orgTree.id and c.conCustomer=y.id ");

		// "where c.saleMan=e.id and e.position=orgTree.id and
		// c.conCustomer=y.id and c.saleMan="
		// + emp.getId());
		sp.append(" and c.ContractType='1'");
		if (conState != null && !conState.equals("")) {
			sp.append(" and c.conState=" + conState);
		} else {
			sp.append(" and (c.conState=1)");
		}

		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId()+"";
		}
		if (groupId != null && !"".equals(groupId)) {
			sp.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}

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

		info = ParameterUtils.preparePageInfo(info, "contractSureList");
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

	//更新此方法，
	public String changeTrueState() throws Exception {
		logger.info("conMainId[0]");
		ContractMainInfo cm = (ContractMainInfo) commonService.load(
				ContractMainInfo.class, conMainId[0]);
		//先生成合同号，并且页面显示，然后把页面显示的号进行保存
		if("true".equals(isSureContractId)){
			//确认生成合同号无误后执行的方法（需要修改）
			if (cm.getImportFromFile()!=null&&cm.getImportFromFile()==true) {
					cm.setConState(4L);
					cm.setOpPeople(UserUtils.getUser().getId());
					cm.setOpTime(new Date());
					commonService.update(cm);
					String str1 = "您合同号为:" + cm.getConId() + "已转成正式合同 ";
					noticeservice.addNotice(str1, cm.getSaleMan());
					msgalert = "转正式合同成功";
					contractService.copysked(conMainId[0]);
					contractService.cleanRealPlan(conMainId[0]);
					testService.updateOneContractIsActiveDate(cm);
					return doDefault();
			}else{
				//重新生成合同号 判断合同号是否一样 如果不一样需要继续确认
				if(isModify==null||isModify==0){
					//没有修改改过合同号
					if(!contractService.ContractIdIsRepeat(conMainId[0],contractId)){
						//重新生成的合同号相同
						cm.setConId(contractId);
						contractService.changeZSOneComain(cm);		
						msgalert = "转正式合同成功";
						return doDefault();
					}else{
						//重新生成的合同号不同
						contractId = contractService.producedConId(cm);
						ActionContext.getContext().put("isfalse", "true");
						ActionContext.getContext().put("isInput", "false");
						return "toSureContractIdPage";
					}
				}else{
					//如果修改过合同号
					if(contractService.ContractIdIsRepeat(conMainId[0],contractId)){
						ActionContext.getContext().put("isfalse", "true");
						ActionContext.getContext().put("isInput", "true");
						return "toSureContractIdPage";
					}else{
						cm.setConId(contractId);
						contractService.changeZSOneComain(cm);		
						msgalert = "转正式合同成功";
						return doDefault();
					}
				}
			}
		}else{
			//没确认过之前的方法，生成合同号并返回页面给用户查看
			if (cm.getImportFromFile()!=null&&cm.getImportFromFile()==true) {
				//是导入合同直接取合同号
				contractId = cm.getConId();	
			}
			else if(cm.getConState() == 3&&StringUtils.isNotBlank(cm.getConId())){
				contractId = cm.getConId();	
			}
			else{
				//不是导入合同生成合同号
				contractId = contractService.producedConId(cm);
			}
			ActionContext.getContext().put("isfalse", "false");
			return "toSureContractIdPage";
		}
		
	}

	public String toPrepare(){
		//conMainid = conMainId[0];
		return "toPrepare";
	}
	/**
	 * 判断预合同输入的合同号是否重复
	 * @return
	 */
	public String isExist(){
		ContractMainInfo cmaininfo = (ContractMainInfo) commonService.uniqueResult("from ContractMainInfo cm where cm.conId = ? ", resNubmer);
		if(cmaininfo!=null){
			this.renderText("1");
		}else{ 
			this.renderText("0");
		}
		return null;
	}
	
	public void selectExist(){
		Long conState = (Long) commonService.uniqueResult(" select cm.conState from ContractMainInfo cm where cm.conMainInfoSid = ? ", conMainid);
		if(conState == 3){
			this.renderText("1");
		}else{ 
			this.renderText("0");
		}
	}
	
	/**
	 * 转预合同
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changeOrderState() throws Exception {
		messages = new ArrayList<String>();
		List<ContractMainInfo> cmains = contractService.findContractByState(conMainId);
		List<ContractMainInfo> view = new ArrayList<ContractMainInfo>();
		for (ContractMainInfo c : cmains) {
			if (c.getConState() == 4) {
				messages.add("正式合同不能再转预合同");
			} else {
				view.add(c);
			}
		}
		if (view.size() > 0) {
			msgalert = "转预合同成功";
		}
		contractService.changeState(view, new Long(3),contractId);
		if(XMJC.equals("1")){
			return doDefault();
		}
		else{
			return "jicheng";
		}
	}
	/**
	 * 取消取消预合同操作跳转页面
	 * @return
	 * @throws Exception
	 */
	public String showExit() throws Exception{
		if(XMJC.equals("1")){
			return doDefault();
		}
		else{
			return "jicheng";
		}
	}
	/**
	 * 显示确认退回时候填写退回理由
	 * @return
	 */
	public String showConfirmCancel(){
		
		return "showConfirmCancel";
	}
	
	/**
	 * 确认退回
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirmReturn() throws Exception {
		int i = 0;
		messages = new ArrayList<String>();
		List<ContractMainInfo> cmains = contractService
		.findContractByState(conMainId);
		List<ContractMainInfo> view = new ArrayList<ContractMainInfo>();
		for (ContractMainInfo c : cmains) {
			if (c.getConState() == 1) {
				String str1 = "您的草稿合同已被确认退回";
				noticeservice.addNotice(str1, c.getSaleMan());
				view.add(c);
			} else {
				i = 1;
				messages.add("其中合同名称为" + c.getConName() + "已经确认不能退回");
			}
		}
		if (i == 0) {
			msgalert = "确认退回成功!";
		}
		if (i == 1 && view.size() < 1) {
			msgalert = "确认退回失败";
		}
		if (i == 1 && view.size() > 1) {
			msgalert = "部分退回成功部分不能退回";
		}
		//contractService.changeState(view, new Long(2));
		for (ContractMainInfo cm : view) {
			cm.setConState(2L);
			cm.setOpPeople(UserUtils.getUser().getId());
			cm.setOpTime(new Date());
			cm.setReturnReason(returnReason);
			this.commonService.update(cm);
		}
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
				List<RealContractBillandRecePlan> realbillrecelist = commonService.list(
						"from RealContractBillandRecePlan where conMainInfoSid=?",
						c.getConMainInfoSid());
				if (checkApplyBill(bills) == true || checkMonthlyPro(realbillrecelist) ==true) {
					if(checkApplyBill(bills) == true){
						messages.add("合同名称为" + c.getConName() + "已开票不能取消确认");
					}
					if(checkMonthlyPro(realbillrecelist) == true){
						messages.add("合同名称为" + c.getConName() + "已生成月度开票或收款计划不能取消确认");
					}
					
					// contracts.remove(c);
				} else {
					if (c.getConState() == 3 || c.getConState() == 4) {
						view.add(c);
					}
				}
			} else {
				if (c.getConState() == 3 || c.getConState() == 4) {

					view.add(c);
				}
			}

		}
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<size>>>>>>>>>>>>>>>>>>>>>>"
				+ contracts.size());
		if(messages.size()==0){
			if (view.size() > 0) {
				contractService.concelCirform(view);
				msgalert = "取消确认成功!";
			} else {
				msgalert = "只有正式合同可以取消确认!";
			}
		}
		logger.info("11111111111111111111111");
		return doDefault();
	}

	@SuppressWarnings("unused")
	private boolean checkApplyBill(List<ApplyBill> bills) {
		for (ApplyBill b : bills) {
			if (b!=null) {
				return true;
			}
			logger.info("checkApplyBill'sate====" + b.getApplyBillState());
		}

		return false;
	}
	
	private boolean checkMonthlyPro(List<RealContractBillandRecePlan> realbillrecelist){
		for (RealContractBillandRecePlan realContractBillandRecePlan : realbillrecelist) {
			List<MonthlyBillpro> billMonthList = commonService.list(" from MonthlyBillpro where realContractBillandRecePlan.realConBillproSid = ?", realContractBillandRecePlan.getRealConBillproSid());
			List<MonthlyRecepro> receMonthList = commonService.list(" from MonthlyRecepro where realContractBillandRecePlan.realConBillproSid = ?", realContractBillandRecePlan.getRealConBillproSid());
			if(billMonthList.size()!=0 || receMonthList.size() != 0){
				return true;
			}
		}	
		
		return false;
	}

	public String doUpdateConNo() throws Exception {
		logger.info(":::::::::::::::::::::::::::::::::::::" + conMainId[0]);
		logger.info(":::::::::::::::::::::::::" + resNubmer);
		ContractMainInfo cmain = contractService
		.loadContractMainInfo(conMainId[0]);
		contractService.chageZSTwoComain(conMainId[0], resNubmer, cmain);
		// contractService.copysked(conMainId[0]);
		msgalert = "转正式合同成功";
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

	public String getIsSureContractId() {
		return isSureContractId;
	}

	public void setIsSureContractId(String isSureContractId) {
		this.isSureContractId = isSureContractId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Long getIsModify() {
		return isModify;
	}

	public void setIsModify(Long isModify) {
		this.isModify = isModify;
	}

	public String getToPrepareConNum() {
		return toPrepareConNum;
	}

	public void setToPrepareConNum(String toPrepareConNum) {
		this.toPrepareConNum = toPrepareConNum;
	}

	public Long getConMainid() {
		return conMainid;
	}

	public void setConMainid(Long conMainid) {
		this.conMainid = conMainid;
	}

	public String getXMJC() {
		return XMJC;
	}

	public void setXMJC(String xmjc) {
		XMJC = xmjc;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	

	
}

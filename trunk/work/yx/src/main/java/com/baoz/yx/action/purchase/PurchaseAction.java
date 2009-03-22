package com.baoz.yx.action.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/exployeeQuery.action"),
	@Result(name = "succ", type = ServletRedirectResult.class, value = "/purchase/purchaseVerifyQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/purchase/purchaseForm.jsp"),
	@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/purchase/purchaseForm.jsp"),
	//	@Result(name = "executeSuc", value = "/WEB-INF/jsp/purchase/purchaseManage.jsp"),
	@Result(name = "executeSuc", type = ServletRedirectResult.class, value = "/purchase/purchaseManagerSearch.action"),
	@Result(name = "addOrUpdateSuccess", value = "/WEB-INF/jsp/purchase/addOrUpdateResult.jsp" ),
	@Result(name = "updateSuc", value = "/WEB-INF/jsp/purchase/refresh.jsp"),
	@Result(name = "newPurchase", value = "/WEB-INF/jsp/purchase/newPurchase.jsp"),
	@Result(name = "confirmSuc", type = ServletRedirectResult.class, value = "/purchase/purchaseConfirmSearch.action"),
	@Result(name = "view", value = "/WEB-INF/jsp/purchase/purchaseFormDetail.jsp"),
	@Result(name = "interim", value = "/WEB-INF/jsp/purchase/interim.jsp"),
	@Result(name = "returnReason", value = "/WEB-INF/jsp/purchase/returnReason.jsp"),
	@Result(name = "interim2", value = "/WEB-INF/jsp/purchase/interim2.jsp")
})
public class PurchaseAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	
	@Autowired
	@Qualifier("purService")
	private IPurService purService;
	
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	private String customerName;   //客户名称，页面显示用
	private ApplyMessage am;

	private String amids;
	private String ids;

	private String action; // 用于知道是查看还是更新

	private String state; // 用于知道是草稿状态，还是待确认状态

	private String projectName1;// 项目名称1
	private String projectName2;// 项目名称2

	private Long cNameId1;// 客户编号1
	private Long cNameId2;// 客户编号2

//	private String cName1;// 客户名称1
	private String cName2;// 客户名称2

	private String contractName;// 合同名称
	private String contractId;// 合同号

	private String idstate1;// 复选框
	private String idstate2;// 复选框

	private String applyMan;// 申购人
	private String applyDepartment;// 申购部门

	private Long mainId;
	private Long eventId;
	private String applyType;
	private Double maxAmount;
	private String displayInfo;    //页面提示信息

	private String opState;    //0 保存成功 1 提交成功  2 删除成功 3 修改成功 4 确认通过 5 确认退回 6保存修改 7关联成功

	private List<Object>        yxClientCodeList;		
	private String					 returnReason; //退回理由
	

	/**
	 * 申购采购新增
	 */
	@SuppressWarnings("unchecked")
	public String doDefault() throws Exception {
		logger.info("申购采购新增");
		am = new ApplyMessage();
		am.setApplyDate(new Date());
		Long uid = UserUtils.getUser().getId();
		this.changeManDepartment(uid);
		yxClientCodeList = service.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		//		opState = (String)ActionContext.getContext().getSession().get("opState");
		//		ActionContext.getContext().getSession().remove("opState");
		//		this.changeManDepartment(new UserUtils().getUser().getId());
		return ENTER_SAVE;
	}

	/**
	 * 保存申购采购信息
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String savePurchase() throws Exception {
		logger.info("保存");
		if ("contract".equals(action)) {   //从合同来的保存
			am=purService.fillContractAM(am, idstate1, idstate2, state);
		} else {
			am = purService.fillAM(am, idstate1, idstate2, projectName1,projectName2, cNameId1, cNameId2, state);
		}

		service.saveOrUpdate(am);	
		systemService.addRelation(am.getCustomerId());
		amids = am.getId().toString(); //如果是保存修改，保存系统号
		this.setNull();

		if(action == null){
			if(opState!=null && opState.equals("6")){
				displayInfo= "申购已保存";
				return enterUpdate();    //保存修改
			}
			else{
				opState = purService.setAS(state); 	 //申购新建并关闭
				return "enterSave";
			}
		}
		else{
			if ("contract".equals(action) && "wait".equals(state)) {
				ActionContext.getContext().getSession().put("conStat","2");
				return "interim";
			}
			else{
				ActionContext.getContext().getSession().put("conStat","1");
				return "interim";
			}
		}

	}

	/**
	 * 进入修改申购采购信息
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		logger.info("进入申购修改"); 
		Long uid =UserUtils.getUser().getId();
		yxClientCodeList = service.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		am = (ApplyMessage) service.load(ApplyMessage.class, Long.valueOf(amids));
		this.changeData(am);
		maxAmount = this.getApplyMaxAmount();
		return ENTER_UPDATE;

	}

	/**
	 * 执行修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatePurchase() throws Exception {
		logger.info("执行申购修改" + am.getId());
		logger.info("=====" + am.getMainId());
		ApplyMessage am1 = (ApplyMessage) service.load(ApplyMessage.class, am.getId());
		am = purService.updateFillPurchase(am, am1, idstate1, idstate2,
				projectName1, projectName2, cNameId1, cNameId2, state);
		service.saveOrUpdate(am);	
		systemService.addRelation(am.getCustomerId());
		opState = purService.setAS(state);	
		return "updateSuc";

	}

	/**
	 * 删除申购信息（即将申购的is_active设置为0）
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String delPru() throws Exception {
		purService.delPurchase(amids);
		logger.info("删除申购成功！");
		ActionContext.getContext().getSession().put("opState", "2");	
		return "executeSuc";
	}

	/**
	 * 关联合同
	 * 
	 * @return
	 */
	public String link() throws Exception {
		purService.linkCountract(this.getAmids(), ids);
		logger.info("关联合同成功");
		opState = "7";
		return "addOrUpdateSuccess";
	}

	/**
	 * 确认提交（即将申购的affirmState设置为1）
	 * 
	 * @return
	 */
	public String submitPru() throws Exception {
		purService.submitPurchase(amids);
		logger.info("提交成功");
		ActionContext.getContext().getSession().put("opState", "1");	
		return "executeSuc";
	}

	public String viewPurchase() throws Exception {
		am = (ApplyMessage) service.load(ApplyMessage.class, Long.valueOf(this.getAmids()));		
		this.changeData(am);
		customerName = foramlContractService.getCustomerName(am.getCustomerId());
		return "view";
	}

	/**
	 * 在合同管理中选择新建申购申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String newPurchase() throws Exception {
		logger.info("由合同新增申购采购");
		am = new ApplyMessage();
		am.setApplyDate(new Date());
		this.changeManDepartment(new UserUtils().getUser().getId());
		if (eventId == 0) {
			this.setApplyType("集成类");
			am.setApplyType("2");
		} else {
			this.setApplyType("工程类");
			String temp = (String) service.uniqueResult("select c.conItemId from ContractItemMaininfo" +	"  c where c.conItemMinfoSid=?",this.getEventId());
			am.setEventId(temp);
			am.setApplyType("1");
		}
		am.setMainId(mainId);
		ContractMainInfo cmi = (ContractMainInfo) service.load(ContractMainInfo.class, mainId);
		this.setContractId(cmi.getConId());
		am.setCustomerId(cmi.getConCustomer());
		this.setCName2((String) service.uniqueResult("select c.name from YXClientCode c where id=?", cmi.getConCustomer()));

		maxAmount = this.getApplyMaxAmount();			
		return "newPurchase";
	}


	public Double getApplyMaxAmount(){
		BigDecimal itemAmount = (BigDecimal) service.uniqueResult(
				" select sum(ii.conItemAmountWithTax) from " +
				" ContractItemInfo ii,ContractItemMaininfo cim where ii.contractItemMaininfo = cim.conItemMinfoSid and cim.conItemId  = ?"
				, am.getEventId());
		if(itemAmount==null){
			return 0d;
		}
		else{
			return  Double.valueOf(itemAmount.toString());
		}
	}


	/**
	 * 将数据库中的数据转换到页面中
	 * 
	 * @param am
	 */
	public void changeData(ApplyMessage am) throws Exception {
		if (am.getMainId() != null) {
			contractName = purService.getContractMainInfo(am.getMainId())
			.getConName();
			contractId = purService.getContractMainInfo(am.getMainId())
			.getConId();
		}
		cNameId2 = am.getCustomerId();
		cName2 = purService.getYXClientCode(am.getCustomerId()).getName();
		cNameId1 = am.getCustomerId();
//		cName1 = purService.getYXClientCode(am.getCustomerId()).getName();
		projectName1 = am.getProjectName();
		projectName2 = am.getProjectName();
		this.changeManDepartment(am.getSellmanId());
		int i = Integer.valueOf(am.getInformState());
		switch (i) {
		case 0: {
			idstate1 = "0";
			idstate2 = "0";
			break;
		}
		case 1: {
			idstate1 = "1";
			idstate2 = "0";
			break;
		}
		case 2: {
			idstate1 = "0";
			idstate2 = "2";
			break;
		}
		case 3: {
			idstate1 = "1";
			idstate2 = "2";
			break;
		}
		}
	}

	public void setNull() throws Exception {
		this.setProjectName1(null);
		this.setProjectName2(null);
		this.setCNameId1(null);
		this.setCNameId2(null);
//		this.setCName1(null);
		this.setCName2(null);
		this.setContractId(null);
		this.setContractName(null);
		this.setIdstate1(null);
		this.setIdstate2(null);
		this.setAm(null);
		this.doDefault();
	}

	public void changeManDepartment(Long id) throws Exception {
		Employee e = (Employee) service.uniqueResult(
				"from Employee e where e.id=?", id);
		this.setApplyMan(e.getName());
		this.setApplyDepartment(userService.getDepartment(e).getOrganizationName());
	}

	@SuppressWarnings("unchecked")
	public String checkThrough() throws Exception {
		purService.checkConfirm(1, amids , null);
		ActionContext.getContext().getSession().put("opState", "4");	
		return "confirmSuc";
	}

	@SuppressWarnings("unchecked")
	public String checkBback() throws Exception {
		purService.checkConfirm(0, amids , returnReason);
		ActionContext.getContext().getSession().put("opState", "5");	
		return "interim2";
	}
	
	public String returnReason(){
		return "returnReason";
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IPurService getPurService() {
		return purService;
	}

	public void setPurService(IPurService purService) {
		this.purService = purService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ApplyMessage getAm() {
		return am;
	}

	public void setAm(ApplyMessage am) {
		this.am = am;
	}

	public String getAmids() {
		return amids;
	}

	public void setAmids(String amids) {
		this.amids = amids;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProjectName1() {
		return projectName1;
	}

	public void setProjectName1(String projectName1) {
		this.projectName1 = projectName1;
	}

	public String getProjectName2() {
		return projectName2;
	}

	public void setProjectName2(String projectName2) {
		this.projectName2 = projectName2;
	}

	public Long getCNameId1() {
		return cNameId1;
	}

	public void setCNameId1(Long nameId1) {
		cNameId1 = nameId1;
	}

	public Long getCNameId2() {
		return cNameId2;
	}

	public void setCNameId2(Long nameId2) {
		cNameId2 = nameId2;
	}

//	public String getCName1() {
//		return cName1;
//	}
//
//	public void setCName1(String name1) {
//		cName1 = name1;
//	}

	public String getCName2() {
		return cName2;
	}

	public void setCName2(String name2) {
		cName2 = name2;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getIdstate1() {
		return idstate1;
	}

	public void setIdstate1(String idstate1) {
		this.idstate1 = idstate1;
	}

	public String getIdstate2() {
		return idstate2;
	}

	public void setIdstate2(String idstate2) {
		this.idstate2 = idstate2;
	}

	public String getApplyMan() {
		return applyMan;
	}

	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}

	public String getApplyDepartment() {
		return applyDepartment;
	}

	public void setApplyDepartment(String applyDepartment) {
		this.applyDepartment = applyDepartment;
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

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getOpState() {
		return opState;
	}

	public void setOpState(String opState) {
		this.opState = opState;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	public String getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(String displayInfo) {
		this.displayInfo = displayInfo;
	}
	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
	
	
	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	
}

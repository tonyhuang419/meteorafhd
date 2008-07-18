package com.baoz.yx.action.purchase;

import java.util.Date;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 申购采购相关操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/exployeeQuery.action"),
	@Result(name = "succ", type = ServletRedirectResult.class, value = "/purchase/purchaseVerifyQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/purchase/purchaseForm.jsp"),
	@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/purchase/purchaseForm.jsp"),
	@Result(name = "executeSuc", type = ServletRedirectResult.class, value = "/purchase/purManageQuery.action"),
	@Result(name = "addOrUpdateSuccess", value = "/WEB-INF/jsp/purchase/addOrUpdateResult.jsp" ),
	@Result(name = "updateSuc", value = "/WEB-INF/jsp/purchase/refresh.jsp"),
	@Result(name = "newPurchase", value = "/WEB-INF/jsp/purchase/newPurchase.jsp"),
	@Result(name = "confirmSuc", type = ServletRedirectResult.class, value = "/purchase/purManageQuery.action?method=confirmPurchase"),
	@Result(name = "view", value = "/WEB-INF/jsp/purchase/purchaseForm.jsp"),
	@Result(name = "interim", value = "/WEB-INF/jsp/purchase/interim.jsp")
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
	@Qualifier("userService")
	private IUserService userService;


	PageInfo info;
	private ApplyMessage am;

	private String amids;
	private String ids;

	private String action; // 用于知道是查看还是更新

	private String state; // 用于知道是草稿状态，还是待确认状态

	private String projectName1;// 项目名称1
	private String projectName2;// 项目名称2

	private Long cNameId1;// 客户编号1
	private Long cNameId2;// 客户编号2

	private String cName1;// 客户名称1
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

	/**
	 * 申购采购新增
	 */
	public String doDefault() throws Exception {
		logger.info("申购采购新增");
		am = new ApplyMessage();
		am.setApplyDate(new Date());
		this.changeManDepartment(new UserUtils().getUser().getId());
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
		if ("contract".equals(action)) {
			am=purService.fillContractAM(am, idstate1, idstate2, state);
		} else {
			am = purService.fillAM(am, idstate1, idstate2, projectName1,
					projectName2, cNameId1, cNameId2, state);
		}

		service.save(am);
		logger.info("保存成功");
		this.setNull();

		if ("contract".equals(action) && "wait".equals(state)) {
			ActionContext.getContext().getSession().put("conStat","2");
			return "interim";
		}
		else{
			ActionContext.getContext().getSession().put("conStat","1");
			return "interim";
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
		am = (ApplyMessage) service.load(ApplyMessage.class, Long
				.valueOf(amids));
		this.changeData(am);
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
		ApplyMessage am1 = (ApplyMessage) service.load(ApplyMessage.class, am
				.getId());
		am = purService.updateFillPurchase(am, am1, idstate1, idstate2,
				projectName1, projectName2, cNameId1, cNameId2, state);
		service.saveOrUpdate(am);
		return "updateSuc";

	}

	/**
	 * 删除申购信息（即将申购的is_active设置为0）
	 * 
	 * @return
	 */
	public String delPru() throws Exception {
		purService.delPurchase(amids);
		logger.info("删除申购成功！");
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
		return "executeSuc";
	}

	public String viewPurchase() throws Exception {
		am = (ApplyMessage) service.load(ApplyMessage.class, Long.valueOf(this
				.getAmids()));
		this.changeData(am);
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
			am.setApplyType("1");
		} else {
			this.setApplyType("工程类");
			String temp = (String) service
			.uniqueResult(
					"select c.conItemId from ContractItemMaininfo c where c.conItemMinfoSid=?",
					this.getEventId());
			am.setEventId(temp);
			am.setApplyType("0");
		}
		am.setMainId(mainId);
		ContractMainInfo cmi = (ContractMainInfo) service.load(
				ContractMainInfo.class, mainId);
		this.setContractId(cmi.getConId());
		am.setCustomerId(cmi.getConCustomer());
		this.setCName2((String) service.uniqueResult(
				"select c.name from YXClientCode c where id=?", cmi
				.getConCustomer()));
		return "newPurchase";
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
		cName1 = purService.getYXClientCode(am.getCustomerId()).getName();
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
		this.setCName1(null);
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
		this.setApplyDepartment(userService.getDepartment(e)
				.getOrganizationName());
	}

	public String checkThrough() throws Exception {
		purService.checkConfirm(1, amids);
		return "confirmSuc";
	}

	public String checkBback() throws Exception {
		purService.checkConfirm(0, amids);
		return "confirmSuc";
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

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
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

	public String getCName1() {
		return cName1;
	}

	public void setCName1(String name1) {
		cName1 = name1;
	}

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
}

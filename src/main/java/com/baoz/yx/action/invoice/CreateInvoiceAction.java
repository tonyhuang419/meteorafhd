package com.baoz.yx.action.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name = "anotherApply",type = ServletRedirectResult.class,  value = "/invoice/createInvoice.action"),
	@Result(name = "noContractInvoice", value = "/WEB-INF/jsp/invoiceManagement/noContractInvoiceApplications.jsp"),
	@Result(name = "applicationsManagement", value = "/WEB-INF/jsp/invoiceManagement/applicationsManagement.jsp"),
	@Result(name = "applyAssociation", value = "/WEB-INF/jsp/invoiceManagement/applyAssociation.jsp"),
	@Result(name = "excessive", value = "/WEB-INF/jsp/invoiceManagement/excessive.jsp"),
	@Result(name = "guodu", value = "/WEB-INF/jsp/invoiceManagement/guodu.jsp"),
	@Result(name = "oneInvoice", value = "/WEB-INF/jsp/invoiceManagement/oneInvoice.jsp"),
	@Result(name = "applyAssociationUpdate", value = "/WEB-INF/jsp/invoiceManagement/applyAssociationUpdate.jsp"),
	@Result(name = "showInvoiceInforNo", value = "/WEB-INF/jsp/invoiceManagement/showInvoiceInforNo.jsp"),
	@Result(name="printList", value="/WEB-INF/jsp/jasperReport/applyBillJasper.jsp")
})
public class CreateInvoiceAction extends DispatchAction {
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("systemService")
	private ISystemService systemservice;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;



	/*
	 * add by lewei
	 */
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService applyBillService;

	/*
	 * add by lewei
	 */
	private Long contractmainsid = null;      //合同主体系统号


	private PageInfo info;// 分页用info

	private ApplyBill applybill;// 一个申请单实例

	private List clientlist;

	private List<YXTypeManage> invoiceNatureList;

	private List<YXTypeManage> invoiceList;

	private List<YXClientCode> yxClientCodeList;  //合同客户
	
	private List<YXClientCode> billCustomerList;	//开票客户

	private List<ApplyBill> applications;

	private List<ApplyMessage> messageList;

	private String invoiceContent;// 开票内容

	private String remarks;// 备注

	private Double lowercaseMoney;// 金额小写含税

	private Double lowercaseMoneyN;// 金额小写不含税

	private String invoiceNature;// 开票性质

	private String invoiceType;  // 开票类型

	private String clientNameY;  // 合同客户

	private String hetongkehu;  // 合同客户（无用）

	private String contactName; // 合同名称

	private String billCustomer;// 开票单位

	private String kaipiaodanwei;// 开票单位（无用）

	private String base;// 基准

	private boolean ischu;// 是否一次出库

	private Double money;

	private String hiddenid;

	private Employee user;

	private String name;

	private String position;

	private String success;

	private String applyBillId;

	private String customerName;// 客户名称

	private String billApplyNum;// 申请编号

	private String beginApplyDate;// 申请日期开始

	private String endApplyDate;// 申请日期结束

	private String monthPlanYear;// 月计划年

	private String monthPlanMonth;// 月计划月

	private String applyStatus;// 状态

	private String customerId;//客户id

	private YXClientCode yxClientCode;// 客户信息对象


	//增加库存组织
	private List<YXTypeManage>  stockOrgList;  //库存组织

	//增加库存组织
	private String stockOrg;   //库存组织

	//增加取票地点
	private String billSpot;    //取票地点


	private String modify;    //判断界面是否未修改界面
	
	private String succSave ;   //保存、提交 成功标记 0保存成功 1提交成功 2修改成功


//	private String modify;    //判断是否开票申请修改界面

//	public String getModify() {
//	return modify;
//	}

//	public void setModify(String modify) {
//	this.modify = modify;
//	}

	@Override
	public String doDefault() {
		
		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		
		user = UserUtils.getUser();
		name = user.getName();

		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService
		.uniqueResult(
				"from OrganizationTree where organizationCode = ?",
				departmentCode);
		position = department.getOrganizationName();


		logger.info("我的部门是##############################" + position);
		ActionContext.getContext().getSession().remove("messageList");
		yxClientCodeList = contractservice.findClient(UserUtils.getUser().getId());
//			commonService
//		.list("from YXClientCode d where d.id not in(0) and d.is_active=1");
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
//			messageList = (List<ApplyMessage>) ActionContext.getContext()
//			.getSession().get("messageList");
			ActionContext.getContext().getSession().remove("messageList");
		}

		billSpot = "宝山";
		money = 0d;


		
		return "noContractInvoice";
	}

	public String doDefaultT() {

		user = UserUtils.getUser();
		name = user.getName();

		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService
		.uniqueResult(
				"from OrganizationTree where organizationCode = ?",
				departmentCode);
		position = department.getOrganizationName();

		logger.info("我的部门是##############################" + position);
		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		return "noContractInvoice";
	}

	public String saveInvoice() {// 保存申请

		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService
		.uniqueResult(
				"from OrganizationTree where organizationCode = ?",
				departmentCode);
		position = department.getOrganizationName();


		user = UserUtils.getUser();
		Long workid = user.getId();
		ApplyBill ab = new ApplyBill();

		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);
		logger.info("billCustomer" + billCustomer); 			//这个是合同客户
		logger.info("clientNameY" + clientNameY);				//这个是开票客户
		logger.info("base" + base);
		logger.info("ischu" + ischu);


		ab.setCustomerId(Long.valueOf(billCustomer));       //这个是合同客户
		ab.setBillCustomer(Long.valueOf(clientNameY));		 //这个是开票客户

		//增加一次出库
		ab.setOneOut(ischu);

		ab.setRemarks(remarks);
		ab.setBase(Long.valueOf(base));
		ab.setBillContent(invoiceContent);
		ab.setBillType(invoiceType);
		ab.setBillNature(invoiceNature);
		ab.setApplyBillState(1L);
		ab.setContactName(contactName);
		ab.setApplyId(new Date());
		ab.setBase(Long.valueOf(base));
		ab.setEmployeeId(workid);
		ab.setIsNoContract(Boolean.TRUE);

		//增加一次出库
		ab.setOneOut(ischu);

		//增加库存组织
		ab.setStockOrg(stockOrg);

		//增加取票地点
		ab.setBillSpot(billSpot);
		ab.setSign(false);           //默认签收状态为未签

		//增加 原始是否未签申请
		ab.setInitIsNoContract(true);

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		invoiceService.saveApplications(ab, listSave, null, null);
		ActionContext.getContext().getSession().remove("messageList");
		
		succSave = "0";
		ActionContext.getContext().getSession().put("succSave", succSave);
		
//		money = null;
//		base = null;
//		kaipiaodanwei = null;
//		billCustomer = null;
//		contactName = null;
//		hetongkehu = null;
//		clientNameY = null;
//		invoiceType = null;
//		invoiceNature = null;
//		lowercaseMoneyN = null;
//		lowercaseMoney = null;
//		remarks = null;
//		invoiceContent = null;	
//		success = "1";

//		//增加库存组织
//		stockOrg = null;

//		//增加取票地点
//		billSpot = null;

//		user = UserUtils.getUser();
//		name = user.getName();

//		yxClientCodeList = commonService
//		.list("from YXClientCode d where d.id not in(0) and d.is_active=1");
//		invoiceList = typeManageService.getYXTypeManage(1004L);
//		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

//		//增加库存组织
//		stockOrgList = typeManageService.getYXTypeManage(1021L);

//		if (ActionContext.getContext().getSession().get("messageList") != null) {
//		messageList = (List<ApplyMessage>) ActionContext.getContext()
//		.getSession().get("messageList");
//		}
		return "anotherApply";
	}

	public String saveInvoiceAndApplications() {// 提交申请

		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService
		.uniqueResult(
				"from OrganizationTree where organizationCode = ?",
				departmentCode);
		position = department.getOrganizationName();


		user = UserUtils.getUser();
		Long workid = user.getId(); // 获取用户系统号
		ApplyBill ab = new ApplyBill();

		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);
		logger.info("----------------------------/n/n/n/nbillCustomer" + billCustomer);   				 //这个是合同客户
		logger.info("clientNameY" + clientNameY);     				 //这个是开票客户
		logger.info("base" + base);  

		ab.setCustomerId(Long.valueOf(billCustomer));      //这个是合同客户
		ab.setBillCustomer(Long.valueOf(clientNameY));		 //这个是开票客户

		ab.setRemarks(remarks);
		ab.setBase(Long.valueOf(base));
		ab.setBillContent(invoiceContent);
		ab.setBillType(invoiceType);
		ab.setBillNature(invoiceNature);
		ab.setApplyBillState(2L);
		ab.setContactName(contactName);
		ab.setApplyId(new Date());
		ab.setBase(Long.valueOf(base));
		ab.setEmployeeId(workid);
		ab.setIsNoContract(Boolean.TRUE);

		//增加库存组织
		ab.setStockOrg(stockOrg);

		//增加取票地点
		ab.setBillSpot(billSpot);

		//增肌一次出库
		ab.setOneOut(ischu);
		ab.setSign(false);

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}

//		SeeBeanFields.travBean(ab);

		invoiceService.saveApplications(ab, listSave,null,null);

		ActionContext.getContext().getSession().remove("messageList");
		
		succSave = "1";
		ActionContext.getContext().getSession().put("succSave", succSave);
		
//		money = null;
//		base = null;
//		kaipiaodanwei = null;
//		billCustomer = null;
//		contactName = null;
//		hetongkehu = null;
//		clientNameY = null;
//		invoiceType = null;
//		invoiceNature = null;
//		lowercaseMoneyN = null;
//		lowercaseMoney = null;
//		remarks = null;
//		invoiceContent = null;
//		//增加库存组织
//		stockOrg = null;

//		//增加取票地点
//		billSpot = "宝山";

//		success = "1";
//		user = UserUtils.getUser();
//		name = user.getName();

//		yxClientCodeList = commonService
//		.list("from YXClientCode d where d.id not in(0) and d.is_active=1");
//		invoiceList = typeManageService.getYXTypeManage(1004L);
//		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

//		//增加库存组织
//		stockOrgList = typeManageService.getYXTypeManage(1021L);

//		if (ActionContext.getContext().getSession().get("messageList") != null) {
//		messageList = (List<ApplyMessage>) ActionContext.getContext()
//		.getSession().get("messageList");
//		}


		return "anotherApply";
	}

	// 查找开票申请
	public String findInvoiceApplications() {

		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		
		
		user = UserUtils.getUser();
		Long uid = user.getId();

		StringBuffer hql = new StringBuffer("from ApplyBill bill " +
				" where  bill.employeeId=" + uid);
		
		if (customerId != null
				&& !"".equals(customerId)) {
			hql.append(" and bill.customerId="+ customerId);

		}
		if (this.getBillApplyNum() != null
				&& !"".equals(this.getBillApplyNum())) {
			hql.append(" and bill.billApplyNum like '%" + this.getBillApplyNum() + "%'" );
		}
		if (this.getBeginApplyDate() != null
				&& !"".equals(this.getBeginApplyDate())) {
			hql.append(" and bill.applyId>=to_date('" + this.getBeginApplyDate()
					+ "','yyyy-mm-dd')");
		}
		if (this.getEndApplyDate() != null
				&& !"".equals(this.getEndApplyDate())) {
			hql.append(" and bill.applyId<=to_date('" + this.getEndApplyDate()
					+ "','yyyy-mm-dd')");
		}
		if (this.getMonthPlanYear() != null
				&& !"0000".equals(this.getMonthPlanYear()) 
				&& !"".equals(this.getMonthPlanYear()) ) {
			hql.append(" and bill.billApplyId in (select relation.applyBill from BillandProRelaion relation " +
					" where relation.realContractBillandRecePlan in " +
					"(select plan.realConBillproSid from " +
					" RealContractBillandRecePlan plan where to_char(plan.realPredBillDate,'yyyy')='"
					+ this.getMonthPlanYear() + "'))");
		}
		if (this.getMonthPlanMonth() != null
				&& !"00".equals(this.getMonthPlanMonth())
				&& !"".equals(this.getMonthPlanMonth())) {
			hql.append(" and bill.billApplyId in (select relation.applyBill from BillandProRelaion  relation " +
					"where relation.realContractBillandRecePlan in " +
					"(select plan.realConBillproSid from " +
					" RealContractBillandRecePlan plan where to_char(plan.realPredBillDate,'mm')='"
					+ this.getMonthPlanMonth() + "'))");
		}
		if (this.getApplyStatus() != null && !"0".equals(this.getApplyStatus())&&!"".equals(this.getApplyStatus())) {
			hql.append(" and bill.applyBillState = '" + this.getApplyStatus()
					+ "'");
		}

		hql.append(" order by bill.applyId desc, bill.isNoContract desc");  //这里不是id排序，是日期排序，实体类命名有误，乎...
		logger.info("查询语句是：" + hql);
		info = queryService.listQueryResult(hql.toString(), info);
		return "applicationsManagement";
	}

	public String submitApplications() {// 进行申请状态修改
		String[] id = (String[]) ActionContext.getContext().getParameters().get("ids");

		invoiceService.updateApplyBillState(id);

		return this.findInvoiceApplications();
	}

	public String deleteApplications() {// 进行申请状态删除
		String[] id = (String[]) ActionContext.getContext().getParameters().get("ids");
		if(id!=null && id.length>0){
			invoiceService.deleteApplyBill(id);
			info = queryService.listQueryResult("from ApplyBill bill", info);
		}
		return "applicationsManagement";
	}

	public String openAssociation() {// 打开关联页面
		user = UserUtils.getUser();
		Long workid = user.getId();
		info = queryService.listQueryResult(
				"select apply,client from ApplyMessage apply,YXClientCode client" +
				" where apply.sellmanId = ? and  apply.customerId = client.id  and " +
				" apply.outState = 0 and apply.ticketApplyId is null ", 
				info, workid);

		return "applyAssociation";
	}


	public String openAssociationUpdate() {// 打开关联页面(修改用)
		user = UserUtils.getUser();
		Long workid = user.getId();
		info = queryService.listQueryResult(
				"select apply,client from ApplyMessage apply,YXClientCode client" +
				" where apply.sellmanId = ? and  apply.customerId = client.id  and " +
				" apply.outState = 0 and apply.ticketApplyId is null ", 
				info, workid);

		return "applyAssociationUpdate";
	}

	public String findAssociation() {
		user = UserUtils.getUser();
		Long workid = user.getId();
		String id = ((String[]) ActionContext.getContext().getParameters().get(
		"iid"))[0];
		if (id != null) {
			info = queryService.listQueryResult(
					"select apply,client from ApplyMessage apply, YXClientCode client "+
					" where  apply.sellmanId = ? and " +
					" apply.ticketApplyId is null and apply.customerId = client.id and  " +
					" apply.applyId like '%"
					+ id + "%'", info ,workid);
		}
		return "applyAssociation";
	}

	public String findAssociationUpdate() {
		String id = ((String[]) ActionContext.getContext().getParameters().get(
		"iid"))[0];
		if (id != null && !id.equals("")) {
			info = queryService
			.listQueryResult(
					"from ApplyMessage apply where apply.ticketApplyId is null and apply.id like '%"
					+ id + "%'", info);
		}
		return "applyAssociationUpdate";
	}

	public String applicationsAssociation() {// 进行关联保存到session中
		String[] id = (String[]) ActionContext.getContext().getParameters()
		.get("associationIds");

		List<ApplyMessage> list = invoiceService.applicationsAssociation(id);
		List<ApplyMessage> list1 = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			list1 = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		if (list != null && list1 != null) {
			for (int i = 0; i < list1.size(); i++) {
				ApplyMessage a1 = list1.get(i);
				for (int j = 0; j < list.size(); j++) {
					ApplyMessage a2 = list.get(j);
					if (a1.getId().equals(a2.getId())) {
						list.remove(j);
						break;
					}
				}
			}
		}
		list1.addAll(list);
		ActionContext.getContext().getSession().put("messageList", list1);
		return "excessive";
	}



	public String findOneInvoice() {// 修改页面弹出显示需要修改的信息
		user = UserUtils.getUser();
		name = user.getName();
		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService.uniqueResult(
				"from OrganizationTree where organizationCode = ?" , departmentCode);
		position = department.getOrganizationName();
		logger.info("我的部门是##############################" + position);
		String id = ((String[]) ActionContext.getContext().getParameters().get(
		"sid"))[0];
		if (id == null)
			id = "7";
		hiddenid = id;
		logger.info("我的id是+++++++++++++" + id);
		applybill = invoiceService.findOneInvocie(id);
		lowercaseMoney = applybill.getBillAmountTax();
		lowercaseMoneyN = applybill.getBillAmountNotax();
		clientNameY = applybill.getCustomerId().toString();
		billCustomer = applybill.getBillCustomer().toString();

		//增加一次出库
		ischu = applybill.getOneOut();

		if (clientNameY != null && !clientNameY.equals("")) {
			List<YXClientCode> llll1 = commonService
			.list("from YXClientCode d where d.id='" + clientNameY
					+ "'");
			if (llll1 != null && llll1.size() > 0)
				hetongkehu = llll1.get(0).getName();
		}
		if (billCustomer != null && !billCustomer.equals("")) {
			List<YXClientCode> llll2 = commonService
			.list("from YXClientCode d where d.id='" + billCustomer
					+ "'");
			if (llll2 != null && llll2.size() > 0)
				kaipiaodanwei = llll2.get(0).getName();
		}
		contactName = applybill.getContactName();
		remarks = applybill.getRemarks();
		base = applybill.getBase().toString();
		if (base.equals("1")) {
			money = lowercaseMoney;
		} else
			money = lowercaseMoneyN;
		invoiceContent = applybill.getBillContent();
		invoiceType = applybill.getBillType();
		invoiceNature = applybill.getBillNature();
		contactName = applybill.getContactName();
		hiddenid = applybill.getBillApplyId().toString();

		ischu = applybill.getOneOut();

		//增加库存组织
		stockOrg = applybill.getStockOrg();

		//增加取票地点
		billSpot = applybill.getBillSpot();

		List<ApplyMessage> list = invoiceService.findMessage(id);

//		if (applybill.getIsNoContract() == null) {
//		ischu = false;
//		} else {
//		ischu = applybill.getIsNoContract();
//		}

//		invoiceService.disconnectIncoice(id);

		if (list != null) {
			ActionContext.getContext().getSession().put("messageList", list);

		}

		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		return "oneInvoice";
	}

	public String refreshupdata() {// 刷新页面（修改时用）
		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}

		return "oneInvoice";
	}

	public String applicationsDelete() {// 删除一个关联

		List<ApplyMessage> list = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			list = (List<ApplyMessage>) ActionContext.getContext().getSession()
			.get("messageList");
			String id = ((String[]) ActionContext.getContext().getParameters()
					.get("deleteId"))[0];
			for (int i = 0; i < list.size(); i++) {
				ApplyMessage am = list.get(i);
				if (am.getId().equals(Long.valueOf(id))) {
					list.remove(i);
				}
			}
		}

		ActionContext.getContext().getSession().put("messageList", list);

		user = UserUtils.getUser();
		name = user.getName();
		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService
		.uniqueResult(
				"from OrganizationTree where organizationCode = ?",
				departmentCode);
		position = department.getOrganizationName();


		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1022L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		ActionContext.getContext().getSession().put("messageList", messageList);

		if("true".equals(modify)){
			logger.info("返回修改界面");
			return "oneInvoice";
		}
		return "noContractInvoice";
	}


//	public String applicationsDeleteDelete() {// 删除一个关联（修改用）
//	List<ApplyMessage> list = new ArrayList<ApplyMessage>();
//	if (ActionContext.getContext().getSession().get("messageList") != null) {
//	list = (List<ApplyMessage>) ActionContext.getContext().getSession()
//	.get("messageList");
//	String id = ((String[]) ActionContext.getContext().getParameters()
//	.get("deleteId"))[0];
//	for (int i = 0; i < list.size(); i++) {
//	ApplyMessage am = list.get(i);
//	if (am.getId().equals(Long.valueOf(id))) {
//	list.remove(i);
//	}
//	}
//	}

//	ActionContext.getContext().getSession().put("messageList", list);

//	user = UserUtils.getUser();
//	name = user.getName();
//	position = user.getPosition().toString();
//	yxClientCodeList = commonService
//	.list("from YXClientCode d where d.id not in(0) and d.is_active=1");
//	invoiceList = typeManageService.getYXTypeManage(1004L);
//	invoiceNatureList = typeManageService.getYXTypeManage(1022L);

//	//增加库存组织
//	stockOrgList = typeManageService.getYXTypeManage(1021L);

//	if (ActionContext.getContext().getSession().get("messageList") != null) {
//	messageList = (List<ApplyMessage>) ActionContext.getContext()
//	.getSession().get("messageList");
//	}

//	return "oneInvoice";
//	}

	public String applicationsAssociationDelete() {// 进行关联保存到session中(修改用)
		String[] id = (String[]) ActionContext.getContext().getParameters().get("associationIds");

		List<ApplyMessage> list = invoiceService.applicationsAssociation(id);
		List<ApplyMessage> list1 = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			list1 = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		if (list != null && list1 != null) {
			for (int i = 0; i < list1.size(); i++) {
				ApplyMessage a1 = list1.get(i);
				for (int j = 0; j < list.size(); j++) {
					ApplyMessage a2 = list.get(j);
					if (a1.getId().equals(a2.getId())) {
						list.remove(j);
						break;
					}
				}
			}
		}
		list1.addAll(list);
		ActionContext.getContext().getSession().put("messageList", list1);
		return "excessive";
	}

	public String updateOneInvoice() {
		ApplyBill ab = new ApplyBill();
		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);
		ab.setCustomerId(Long.valueOf(clientNameY));
		ab.setRemarks(remarks);
		ab.setBillContent(invoiceContent);
		ab.setBillType(invoiceType);
		ab.setBase(Long.valueOf(base));
		ab.setBillNature(invoiceNature);
		ab.setContactName(contactName);
		ab.setApplyId(new Date());

		ab.setCustomerId(Long.parseLong(billCustomer));
		ab.setBillCustomer(Long.parseLong(clientNameY));

		//增肌一次出库
		ab.setOneOut(ischu);

		//增加库存组织
		ab.setStockOrg(stockOrg);

		//增加取票地点
		ab.setBillSpot(billSpot);

		//SeeBeanFields.travBean(ab);   

		List<ApplyMessage> list = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
		invoiceService.disconnectIncoice(hiddenid);
		invoiceService.updateInvoice(ab, list, hiddenid);
		ActionContext.getContext().getSession().remove("messageList");
 
		succSave = "2";
		ActionContext.getContext().getSession().put("succSave",succSave);
		
		return "guodu";
	}

	public String printList()throws Exception{
		return "printList";
	}
	public String showInvoiceInfor() throws Exception {

		user = UserUtils.getUser();
		name = user.getName();
		String departmentCode = DepartmentUtils.getDepartmentCode(UserUtils
				.getUserDetail().getPosition().getOrganizationCode());
		OrganizationTree department = (OrganizationTree) commonService
		.uniqueResult(
				"from OrganizationTree where organizationCode = ?",
				departmentCode);
		position = department.getOrganizationName();
		String id = this.applyBillId;
		hiddenid = id;
		applybill = invoiceService.findOneInvocie(id);		
		this.setApplyBillId(applybill.getBillApplyId().toString());
		lowercaseMoney = applybill.getBillAmountTax();
		lowercaseMoneyN = applybill.getBillAmountNotax();
		clientNameY = applybill.getCustomerId().toString();           //获取合同客户ID
		billCustomer = applybill.getBillCustomer().toString();        //获取开票客户ID

		//按获取合同客户ID 获取合同客户
		if (clientNameY != null && !clientNameY.equals("")) {
			YXClientCode llll1 = (YXClientCode)commonService.uniqueResult(" from YXClientCode d " +
					" where d.is_active='1' and d.id= ? " , Long.parseLong(clientNameY));
			if(llll1 != null){
				hetongkehu = llll1.getName();		
			}
		}

		//按获取开票客户ID 获取开票客户
		if (billCustomer != null && !billCustomer.equals("")) {
			YXClientCode llll2 = (YXClientCode)commonService.uniqueResult("from YXClientCode d " +
					" where d.is_active='1' and  d.id = ? ", Long.parseLong(billCustomer));
			if (llll2 != null){	
				yxClientCode = llll2;
			}
		}


		contactName = applybill.getContactName();
		remarks = applybill.getRemarks();
		base = applybill.getBase().toString();

		if (base.equals("1")) {
			money =  lowercaseMoney;
		} else {
			money =  lowercaseMoneyN;
		}
		invoiceContent = applybill.getBillContent();
		invoiceType = applybill.getBillType();
		invoiceNature = applybill.getBillNature();
		contactName = applybill.getContactName();
		hiddenid = applybill.getBillApplyId().toString();

		//增加库存组织
		stockOrg = applybill.getStockOrg();

		//增加取票地点
		billSpot = applybill.getBillSpot();

		//增加一次出库
		ischu = applybill.getOneOut();


		List<ApplyMessage> list = invoiceService.findMessage(id);
//		if (applybill.getIsNoContract() == null) {
//		ischu = false;
//		} else {
//		ischu = applybill.getIsNoContract();
//		}

//		invoiceService.disconnectIncoice(id);
		if (list != null) {
			ActionContext.getContext().getSession().put("messageList", list);

		}

		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
//		YXTypeManage yx = typeManageService.getYXTypeManage(1004L, invoiceType);
//		invoiceType = yx.getTypeName();
//		YXTypeManage yxTypeManage = typeManageService.getYXTypeManage(1022L, invoiceNature);
//		invoiceNature = yxTypeManage.getTypeName();

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		return "showInvoiceInforNo";
	}




	/*
	 * add by lewei 打开关联页面, 根据合同主体系统号，获取未出库申购清单
	 */
	public String openAssociationByConsid() {
		String aid = "";      //搜索功能这个是
		try{
			aid = ((String[]) ActionContext.getContext().getParameters().get("iid"))[0];
		}catch(Exception e){
			logger.info("这个不是分页查询");
		}
		info = applyBillService.loadNotOutPurchase(contractmainsid, aid, info);
		return "applyAssociation";
	}


	/*
	 * add by lewei 打开关联页面, 根据合同主体系统号，获取未出库申购清单(修改用)
	 */
	public String openAssociationByConsidUpdate() {
		String aid = "";
		try{
			aid = ((String[]) ActionContext.getContext().getParameters().get("iid"))[0];
		}catch(Exception e){
			logger.info("这个不是分页查询");
		}
		info = applyBillService.loadNotOutPurchase(contractmainsid, aid, info);
		return "applyAssociationUpdate";
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String submitInvoice() {
		return null;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IContractService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

	public List getClientlist() {
		return clientlist;
	}

	public void setClientlist(List clientlist) {
		this.clientlist = clientlist;
	}

	public ISystemService getSystemservice() {
		return systemservice;
	}

	public void setSystemservice(ISystemService systemservice) {
		this.systemservice = systemservice;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public List<YXTypeManage> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<YXTypeManage> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXClientCode> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<YXClientCode> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public List<YXTypeManage> getInvoiceNatureList() {
		return invoiceNatureList;
	}

	public void setInvoiceNatureList(List<YXTypeManage> invoiceNatureList) {
		this.invoiceNatureList = invoiceNatureList;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	
	public Double getLowercaseMoney() {
		return lowercaseMoney;
	}

	public void setLowercaseMoney(Double lowercaseMoney) {
		this.lowercaseMoney = lowercaseMoney;
	}

	public Double getLowercaseMoneyN() {
		return lowercaseMoneyN;
	}

	public void setLowercaseMoneyN(Double lowercaseMoneyN) {
		this.lowercaseMoneyN = lowercaseMoneyN;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInvoiceNature() {
		return invoiceNature;
	}

	public void setInvoiceNature(String invoiceNature) {
		this.invoiceNature = invoiceNature;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getClientNameY() {
		return clientNameY;
	}

	public void setClientNameY(String clientNameY) {
		this.clientNameY = clientNameY;
	}

	public List<ApplyBill> getApplications() {
		return applications;
	}

	public void setApplications(List<ApplyBill> applications) {
		this.applications = applications;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List<ApplyMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ApplyMessage> messageList) {
		this.messageList = messageList;
	}

	public ApplyBill getApplybill() {
		return applybill;
	}

	public void setApplybill(ApplyBill applybill) {
		this.applybill = applybill;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getHiddenid() {
		return hiddenid;
	}

	public void setHiddenid(String hiddenid) {
		this.hiddenid = hiddenid;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getBillCustomer() {
		return billCustomer;
	}

	public void setBillCustomer(String billCustomer) {
		this.billCustomer = billCustomer;
	}

	public String getHetongkehu() {
		return hetongkehu;
	}

	public void setHetongkehu(String hetongkehu) {
		this.hetongkehu = hetongkehu;
	}

	public String getKaipiaodanwei() {
		return kaipiaodanwei;
	}

	public void setKaipiaodanwei(String kaipiaodanwei) {
		this.kaipiaodanwei = kaipiaodanwei;
	}


	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public boolean isIschu() {
		return ischu;
	}

	public void setIschu(boolean ischu) {
		this.ischu = ischu;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBillApplyNum() {
		return billApplyNum;
	}

	public void setBillApplyNum(String billApplyNum) {
		this.billApplyNum = billApplyNum;
	}

	public String getBeginApplyDate() {
		return beginApplyDate;
	}

	public void setBeginApplyDate(String beginApplyDate) {
		this.beginApplyDate = beginApplyDate;
	}

	public String getEndApplyDate() {
		return endApplyDate;
	}

	public void setEndApplyDate(String endApplyDate) {
		this.endApplyDate = endApplyDate;
	}

	public String getMonthPlanYear() {
		return monthPlanYear;
	}

	public void setMonthPlanYear(String monthPlanYear) {
		this.monthPlanYear = monthPlanYear;
	}

	public String getMonthPlanMonth() {
		return monthPlanMonth;
	}

	public void setMonthPlanMonth(String monthPlanMonth) {
		this.monthPlanMonth = monthPlanMonth;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<YXTypeManage> getStockOrgList() {
		return stockOrgList;
	}

	public void setStockOrgList(List<YXTypeManage> stockOrgList) {
		this.stockOrgList = stockOrgList;
	}

	public String getStockOrg() {
		return stockOrg;
	}

	public void setStockOrg(String stockOrg) {
		this.stockOrg = stockOrg;
	}

	public IApplyBillService getApplyBillService() {
		return applyBillService;
	}

	public void setApplyBillService(IApplyBillService applyBillService) {
		this.applyBillService = applyBillService;
	}

	public Long getContractmainsid() {
		return contractmainsid;
	}

	public void setContractmainsid(Long contractmainsid) {
		this.contractmainsid = contractmainsid;
	}

	public String getBillSpot() {
		return billSpot;
	}

	public void setBillSpot(String billSpot) {
		this.billSpot = billSpot;
	}

	public YXClientCode getYxClientCode() {
		return yxClientCode;
	}

	public void setYxClientCode(YXClientCode yxClientCode) {
		this.yxClientCode = yxClientCode;
	}

	public String getApplyBillId() {
		return applyBillId;
	}

	public void setApplyBillId(String applyBillId) {
		this.applyBillId = applyBillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public List<YXClientCode> getBillCustomerList() {
		return billCustomerList;
	}

	public void setBillCustomerList(List<YXClientCode> billCustomerList) {
		this.billCustomerList = billCustomerList;
	}

	public String getSuccSave() {
		return succSave;
	}

	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}

}

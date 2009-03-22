package com.baoz.yx.action.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.ProcessResult;
import com.baoz.yx.vo.UserDetail;
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
	@Result(name = "saveAndOpen",type = ServletRedirectResult.class,  value = "/invoice/createInvoice.action?method=findOneInvoice"),
	@Result(name = "modifyNoCon",type = ServletRedirectResult.class,  value = "/invoice/createInvoice.action?method=findOneInvoice"),
	@Result(name="printList", value="/WEB-INF/jsp/jasperReport/applyBillJasper.jsp"),
	@Result(name="showPrintBillQuery", value="/WEB-INF/jsp/billtoReceipt/printBillQuery.jsp"),
	@Result(name = "showUpdateRemark", value = "/WEB-INF/jsp/billtoReceipt/updateNoConRemark.jsp"),
	@Result(name="printBill", value="/WEB-INF/jsp/billtoReceipt/printBill.jsp")
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

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService applyBillService;

	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 			billService;

	private String conNum;  //合同号

	private String itemNum;  //项目号

	private String 						groupId;      //组别
	private Long 						expId;		//销售员

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

	private String hetongkehu;  // 合同客户

	private String contactName; // 合同名称

	private String firstReceiveMan;  //甲方接收人
	private String billCustomer;			// 开票单位

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

	private Boolean hasInvoice;    //开票申请是否存在开票

	//搜索条件。。start
	private String customerName;// 客户名称

	private String billApplyNum;// 申请编号

	private String beginApplyDate;// 申请日期开始

	private String endApplyDate;// 申请日期结束

	private String monthPlanYear;// 月计划年

	private String monthPlanMonth;// 月计划月

	private String applyStatus;// 状态

	private String conNumX;   //合同号

	private String hasSigned; //已签收
	
	private String itemNumX;   //项目号
	//搜索条件。。end

	private ProcessResult processResult;//返回信息	
	private String customerId;//客户id

	private YXClientCode yxClientCode;// 客户信息对象

	private List<YXTypeManage>  stockOrgList;  //库存组织

	private String stockOrg;   //库存组织

	private String billSpot;    //取票地点


	private String modify;    //判断界面是否未修改界面

	private String succSave ;   //保存、提交 成功标记 0保存成功 1提交成功 2修改成功  3保存提交成功 4 删除成功

	private String saveUpdate;  //判断是否保存并提交 。。。。。2 保存并提交

	private String opType;//判断details页面显示的时候是否显示确认按钮

	private String state;   //1 新建修改   2 新建保存  3 新建提交

	private String applySid;  //开票申请系统号

	private List<Department>			groupList;	  //组别

	private List<Object> 			listExp; 			// 查询显示所有的销售员

	private Map<Long,Date> proDateMap;    //开票申请系统号，计划时间

	private Map<Long,List<String>> itemNoList;   //开票申请系统号，项目号

	private Long[] ids;//选中的申请单

	private Long	applyBillState;  //状态
	private String	returnReason;    //退回理由
	private Date returnDate;
	private String isCon; //是不是从修改备注过来 
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"createInvocieParameters",this,new String[]{"groupId","expId","billApplyNum","conNum","itemNum",
					"beginApplyDate","endApplyDate","applyStatus" }); 


	@Override
	public String doDefault() {
		if(ActionContext.getContext().getSession().get("sid")!=null){
			ActionContext.getContext().getSession().remove("sid");
		}
		if(ActionContext.getContext().getSession().get("sign")!=null){
			ActionContext.getContext().getSession().remove("sign");
		}
		
		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}

		user = UserUtils.getUser();
		name = user.getName();

		position = foramlContractService.getDept(UserUtils.getUser().getId());

		logger.info("我的部门是##############################" + position);
		ActionContext.getContext().getSession().remove("messageList");
		yxClientCodeList = contractservice.findClient(UserUtils.getUser().getId());
//		commonService
//		.list("from YXClientCode d where d.id not in(0) and d.is_active=1");
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1012L);

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


		position = foramlContractService.getDept(UserUtils.getUser().getId());

		logger.info("我的部门是##############################" + position);
		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1012L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		return "noContractInvoice";
	}

	public String saveInvoice() {// 保存申请

		position = foramlContractService.getDept(UserUtils.getUser().getId());
		user = UserUtils.getUser();
		Long workid = user.getId();
		
		ApplyBill ab = new ApplyBill();

		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);

		ab.setCustomerId(Long.valueOf(clientNameY));       //这个是合同客户
		ab.setBillCustomer(Long.valueOf(billCustomer));		 //这个是开票客户

		ab.setRemarks(remarks);
		ab.setBase(Long.valueOf(base));
		ab.setBillContent(invoiceContent);
		ab.setBillType(invoiceType);
		ab.setBillNature(invoiceNature);
		ab.setApplyBillState(1L);
		ab.setContactName(contactName);
		ab.setEmployeeId(workid);
		ab.setIsNoContract(Boolean.TRUE);
		ab.setOneOut(ischu);
		ab.setStockOrg(stockOrg);
		ab.setBillSpot(billSpot);
		ab.setSign(false);           //默认签收状态为未签
		ab.setFirstReceiveMan(firstReceiveMan);

		//增加 原始是否未签申请
		ab.setInitIsNoContract(1L);

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}

		invoiceService.saveApplications(ab, listSave, null, null);
		invoiceService.saveSaleAndCusRealtion( workid, Long.valueOf(billCustomer));

		ActionContext.getContext().getSession().remove("messageList");

		succSave = "0";
		ActionContext.getContext().getSession().put("succSave", succSave);

		return "anotherApply";
	}

	public String saveInvoiceAndApplications()  {// 提交申请

		position = foramlContractService.getDept(UserUtils.getUser().getId());
		user = UserUtils.getUser();
		Long workid = user.getId(); // 获取用户系统号
		ApplyBill ab = new ApplyBill();

		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);
		logger.info("----------------------------/n/n/n/nbillCustomer" + billCustomer);   				 //这个是合同客户
		logger.info("clientNameY" + clientNameY);     				 //这个是开票客户
		logger.info("base" + base);  

		ab.setCustomerId(Long.valueOf(clientNameY));      //这个是合同客户
		ab.setBillCustomer(Long.valueOf(billCustomer));		 //这个是开票客户

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

		ab.setStockOrg(stockOrg);
		ab.setBillSpot(billSpot);
		ab.setOneOut(ischu);
		ab.setSign(false);
		ab.setInitIsNoContract(1L);
		ab.setFirstReceiveMan(firstReceiveMan);

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		} 

//		SeeBeanFields.travBean(ab);

		invoiceService.saveApplications(ab, listSave,null,null);
		invoiceService.saveSaleAndCusRealtion( workid, Long.valueOf(billCustomer));

		ActionContext.getContext().getSession().remove("messageList");

		succSave = "1";
		ActionContext.getContext().getSession().put("succSave", succSave);

		return "anotherApply";
	}


	public String saveInvoiceAndOpen() {  // 保存申请并修改

		position = foramlContractService.getDept(UserUtils.getUser().getId());

		user = UserUtils.getUser();
		Long workid = user.getId();
		ApplyBill ab = new ApplyBill();

		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);

		ab.setCustomerId(Long.valueOf(clientNameY));       //这个是合同客户
		ab.setBillCustomer(Long.valueOf(billCustomer));		 //这个是开票客户

		ab.setRemarks(remarks);
		ab.setBase(Long.valueOf(base));
		ab.setBillContent(invoiceContent);
		ab.setBillType(invoiceType);
		ab.setBillNature(invoiceNature);
		ab.setApplyBillState(1L);
		ab.setContactName(contactName);
//		ab.setApplyId(new Date());
		ab.setBase(Long.valueOf(base));
		ab.setEmployeeId(workid);
		ab.setIsNoContract(Boolean.TRUE);

		ab.setOneOut(ischu);
		ab.setStockOrg(stockOrg);
		ab.setBillSpot(billSpot);
		ab.setSign(false);           //默认签收状态为未签
		ab.setInitIsNoContract(1L);
		ab.setFirstReceiveMan(firstReceiveMan);

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}

		invoiceService.saveApplications(ab, listSave, null, null);
		invoiceService.saveSaleAndCusRealtion( workid, Long.valueOf(billCustomer));
		ActionContext.getContext().getSession().remove("messageList");

		//修改开票申请时，将系统号放入session
		ActionContext.getContext().getSession().put("sid",ab.getBillApplyId());

//		succSave = "0";
//		ActionContext.getContext().getSession().put("succSave", succSave);

		return "saveAndOpen";
	}


	// 查找开票申请
	@SuppressWarnings("unchecked")
	public String findInvoiceApplications() {

		this.getFindCondition();

		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		user = UserUtils.getUser();
		Long uid = user.getId();

		StringBuffer hql = new StringBuffer("select bill, " +
				" ( select cmi.conId from ContractMainInfo  cmi where cmi.conMainInfoSid = bill.contractMainInfo)," +
				" ( select min(rcbrp.realPredBillDate)  from  BillandProRelaion r, " +
				" RealContractBillandRecePlan rcbrp where " +
				" rcbrp.realConBillproSid =   r.realContractBillandRecePlan and r.applyBill = bill.billApplyId )" +
				" from ApplyBill bill " +
				" where  bill.employeeId=" + uid);

		if (conNumX != null && !"".equals(conNumX)) {
			conNumX = conNumX.trim().toUpperCase();
			hql.append(" and exists (select 1  from ContractMainInfo cmi where bill.contractMainInfo = cmi.conMainInfoSid " +
					"  and upper(cmi.conId) like '%" + conNumX +"%')" );
		}
		if(itemNumX!=null && !"".equals(itemNumX)){
			itemNumX = itemNumX.trim().toUpperCase();
			hql.append(" and exists (select 1  from ContractItemMaininfo cim ,RealContractBillandRecePlan rc ,BillandProRelaion rr  " +
					" where  rc.realConBillproSid = rr.realContractBillandRecePlan " +
					" and cim.conItemMinfoSid = rc.contractItemMaininfo  " +
					" and bill.billApplyId  =  rr.applyBill "+
					" and upper(cim.conItemId) like '%" + itemNumX +"%')" );
		}
		

		if (customerId != null && !"".equals(customerId)) {
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

		if(hasSigned!=null && hasSigned.equals("true")){
			hql.append(" and bill.applyBillState = 6" );
		}
		else{
			if (this.getApplyStatus() != null && !"0".equals(this.getApplyStatus())&&!"".equals(this.getApplyStatus())) {
				hql.append(" and bill.applyBillState = " + this.getApplyStatus() );
			}
			else if(this.getApplyStatus() == null || this.getApplyStatus().equals("0" )){
				hql.append(" and bill.applyBillState != 6" );
			}
		}
		//先按时间倒序，在按已签、未签排序，最后是系统号倒序
		hql.append(" order by decode(bill.applyBillState, 4,0, 1,1, 2,2, 3,3) , bill.billApplyId desc");    //bill.applyId desc, bill.isNoContract desc,

		info = ParameterUtils.preparePageInfo(info, "invoiceM");
		info = queryService.listQueryResult(SqlUtils.getCountSql(hql.toString()), hql.toString() , info);
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			//proDateMap = invoiceService.getProDateFromApplyBill(applyBillList);
			itemNoList = invoiceService.getItemNumFromApplyBilll(applyBillList);
		}
		return "applicationsManagement";
	}

	public String showPrintBillQuery(){
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		return "showPrintBillQuery";
	}

	/**
	 * 打印申请单
	 * @return
	 */
	public String printBill(){
		user = UserUtils.getUser();
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer hql = new StringBuffer("select bill, " +
				"( select cmi.conId from ContractMainInfo  cmi where cmi.conMainInfoSid = bill.contractMainInfo),ee.name " +
		" from ApplyBill bill ,Employee ee where   ee.id = bill.employeeId   and   bill.applyBillState in (3,5,6,7) ");
		// 不是组长,只能查自己3
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId != null && !"".equals(groupId)) {
			hql.append(" and exists (select 1 from Employee e ,OrganizationTree t where t.id = e.position " +
					" and  t.organizationCode like '"+groupId+"%' and e.id = bill.employeeId ) ");
		}
		if (expId != null && !"".equals(expId)) {
			hql.append(" and bill.employeeId = ").append(expId);
		}
		if (this.getBillApplyNum() != null
				&& !"".equals(this.getBillApplyNum())) {
			hql.append(" and bill.billApplyNum like '%" + this.getBillApplyNum() + "%'" );
		}
		if (StringUtils.isNotBlank(conNum) && StringUtils.isNotBlank(conNum)) {
			hql.append(" and exists (select 1 from ContractMainInfo cm where lower(cm.conId) like '%"+StringUtils.lowerCase(StringUtils.trim(conNum))+"%' and cm.conMainInfoSid = bill.contractMainInfo ) ");
		}
		if (StringUtils.isNotBlank(itemNum) && StringUtils.isNotBlank(itemNum)) {
			hql.append(" and exists (select 1 from ContractItemMaininfo im,RealContractBillandRecePlan real,BillandProRelaion br " +
					"where lower(im.conItemId) " +
					"like '%"+StringUtils.lowerCase(StringUtils.trim(itemNum))+"%' " +
					"and real.contractItemMaininfo = im.conItemMinfoSid " +
					"and br.applyBill = bill.billApplyId " +
			"and br.realContractBillandRecePlan = real.realConBillproSid ) ");
		}
		if (this.getBeginApplyDate() != null && !"".equals(this.getBeginApplyDate())) {
			hql.append(" and bill.applyId>=to_date('" + this.getBeginApplyDate()
					+ "','yyyy-mm-dd')");
		}
		if (this.getEndApplyDate() != null && !"".equals(this.getEndApplyDate())) {
			hql.append(" and bill.applyId<=to_date('" + this.getEndApplyDate()
					+ "','yyyy-mm-dd')");
		}
		//默认
		if(StringUtils.isBlank(applyStatus)){
			hql.append(" and bill.applyBillState = 3 ");
		}else if(!"-1".equals(applyStatus)){
			//不是全部
			hql.append(" and bill.applyBillState = "+applyStatus+" ");
		}
		//先按时间倒序，在按已签、未签排序，最后是系统号倒序
		hql.append(" order by  bill.billApplyId desc,decode(bill.applyBillState, 3,0, 5,1, 7,2, 6,3)");    //bill.applyId desc, bill.isNoContract desc,
		info = ParameterUtils.preparePageInfo(info, "invoiceM");
		info = queryService.listQueryResult(SqlUtils.getCountSql(hql.toString()), hql.toString() , info);
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			itemNoList = invoiceService.getItemNumFromApplyBilll(applyBillList);
		}
		return "printBill";
	}

	public String submitApplications() {// 进行申请状态修改
		String[] id = (String[]) ActionContext.getContext().getParameters().get("ids");

		invoiceService.updateApplyBillState(id);
		succSave = "3";
		return this.findInvoiceApplications();
	}

	public String deleteApplications() {// 进行申请状态删除
		String[] id = (String[]) ActionContext.getContext().getParameters().get("ids");
		if(id!=null && id.length>0){
			invoiceService.deleteApplyBill(id);
		}
		succSave = "4";
		return this.findInvoiceApplications();
	}


	/*
	 * select count(*) from yx_apply_message apply, yx_client c 
	 *	where       
	 *  apply.sellman_id = 1 and  apply.OUT_STATE = 0 and apply.TICKET_APPLY_ID is null  and apply.CUSTOMER_ID = c.id  and apply.miain_id is  null
	 */
	public String openAssociation() {// 打开关联页面    （ 没有出库   没开票申请    没合同号 ）
		user = UserUtils.getUser();
		Long workid = user.getId();
		info = queryService.listQueryResult(
				"select apply,client from ApplyMessage apply,YXClientCode client" +
				" where apply.sellmanId = ? and  apply.customerId = client.id  and " +
				" apply.is_active = 1  and  apply.affirmState = '2' and   " +
				" apply.outState = 0 and apply.ticketApplyId is null and apply.mainId is null", 
				info, workid);

		return "applyAssociation";
	}


	public String openAssociationUpdate() {// 打开关联页面(修改用)  （ 没有出库   没开票申请    没合同号 ）
		user = UserUtils.getUser();
		Long workid = user.getId();
		info = queryService.listQueryResult(
				"select apply,client from ApplyMessage apply,YXClientCode client" +
				" where apply.sellmanId = ? and  apply.customerId = client.id  and " +
				" apply.is_active = 1  and  apply.affirmState = '2' and  " +
				" apply.outState = 0 and apply.ticketApplyId is null and apply.mainId is null", 
				info, workid);

		return "applyAssociationUpdate";
	}

	public String findAssociation() {
		user = UserUtils.getUser();
		Long workid = user.getId();
		String id = ((String[]) ActionContext.getContext().getParameters().get(
		"iid"))[0];
		id = id.trim();
		if (id != null && !id.equals("")) {
			info = queryService.listQueryResult(
					"select apply,client from ApplyMessage apply, YXClientCode client "+
					" where  apply.sellmanId = ? and " +
					" apply.ticketApplyId is null and apply.customerId = client.id and  " +
					" apply.is_active = 1  and  apply.affirmState = '2' and  " +
					" apply.applyId like '%"
					+ id + "%'", info ,workid);
		}
		else{
			info = queryService.listQueryResult(
					"select apply,client from ApplyMessage apply,YXClientCode client" +
					" where apply.sellmanId = ? and  apply.customerId = client.id  and " +
					" apply.is_active = 1  and  apply.affirmState = '2' and  " +
					" apply.outState = 0 and apply.ticketApplyId is null ", 
					info, workid);
		}
		return "applyAssociation";
	}

	/*
	 * info = queryService.listQueryResult(
				"select apply,client from ApplyMessage apply,YXClientCode client" +
				" where apply.sellmanId = ? and  apply.customerId = client.id  and " +
				" apply.is_active != 0  and  apply.affirmState = '2' and  " +
				" apply.outState = 0 and apply.ticketApplyId is null and apply.mainId is null", 
				info, workid);
	 * 
	 */

	public String findAssociationUpdate() {
		user = UserUtils.getUser();
		Long workid = user.getId();
		String id = ((String[]) ActionContext.getContext().getParameters().get(
		"iid"))[0];
		id = id.trim();
		if (id != null && !id.equals("")) {
			info = queryService.listQueryResult(
					" select apply,client from ApplyMessage apply, YXClientCode client "+
					" where  apply.sellmanId = ? and apply.customerId = client.id and   " +
					" apply.is_active = 1  and  apply.affirmState = '2' and  " +
					" apply.outState = 0 and apply.ticketApplyId is null and apply.mainId is null and " + 
					" apply.applyId like '%"
					+ id + "%'", info ,workid);
		}
		else{
			info = queryService.listQueryResult(
					"select apply,client from ApplyMessage apply,YXClientCode client" +
					" where  apply.sellmanId = ? and apply.customerId = client.id and   " +
					" apply.is_active = 1  and  apply.affirmState = '2' and  " +
					" apply.outState = 0 and apply.ticketApplyId is null and apply.mainId is null ",
					info, workid);
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

		position = foramlContractService.getDept(UserUtils.getUser().getId());

		String id = null;

		//保存修改入口，从session取开票申请sid
		if( ActionContext.getContext().getSession().get("sid") != null &&
				ActionContext.getContext().getSession().get("sign") == null ){
			Long applysid;
			applysid = (Long)ActionContext.getContext().getSession().get("sid");
			id = applysid.toString();
			state = "1";
		}


		//修改开票申请。。。未签开票
		else if(  ActionContext.getContext().getSession().get("sid") != null &&  
				ActionContext.getContext().getSession().get("sign").equals("modifyNoCon") ){

			id = ActionContext.getContext().getSession().get("sid").toString();	
			this.getFindCondition();

		}
		else{
			id= ((String[]) ActionContext.getContext().getParameters().get("sid"))[0];
		}

		hiddenid = id;
		logger.info("我的id是+++++++++++++" + id);
		applybill = invoiceService.findOneInvocie(id);
		lowercaseMoney = applybill.getBillAmountTax();
		lowercaseMoneyN = applybill.getBillAmountNotax();
		clientNameY  = applybill.getCustomerId().toString();
		billCustomer= applybill.getBillCustomer().toString(); 

		
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
		applyBillState = applybill.getApplyBillState();
		returnReason = applybill.getReturnReason();
		ischu = applybill.getOneOut();
		firstReceiveMan = applybill.getFirstReceiveMan();

		//增加库存组织
		stockOrg = applybill.getStockOrg();

		//增加取票地点
		billSpot = applybill.getBillSpot();

		List<ApplyMessage> list = invoiceService.findMessage(id);


//		invoiceService.disconnectIncoice(id);

		if (list != null) {
			ActionContext.getContext().getSession().put("messageList", list);

		}

		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1012L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		yxClientCode = (YXClientCode)commonService.uniqueResult(" from YXClientCode d " +
				" where d.id= ? " , Long.parseLong(billCustomer));

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
		invoiceNatureList = typeManageService.getYXTypeManage(1012L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
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
		position = foramlContractService.getDept(UserUtils.getUser().getId());


		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());
		invoiceList = typeManageService.getYXTypeManage(1004L);
		invoiceNatureList = typeManageService.getYXTypeManage(1012L);

		//增加库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
		}
		ActionContext.getContext().getSession().put("messageList", messageList);

		if("true".equals(modify)){
			logger.info("返回修改界面");
			return "oneInvoice";
		}
		return "noContractInvoice";
	}


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

	@SuppressWarnings("unchecked")
	public String updateOneInvoice() {
		ApplyBill ab = new ApplyBill();
		ab.setBillAmountTax(lowercaseMoney);
		ab.setBillAmountNotax(lowercaseMoneyN);
		ab.setRemarks(remarks);
		ab.setBillContent(invoiceContent);
		ab.setBillType(invoiceType);
		ab.setBase(Long.valueOf(base));
		ab.setBillNature(invoiceNature);
		ab.setContactName(contactName);
		ab.setFirstReceiveMan(firstReceiveMan);
		ab.setCustomerId(Long.parseLong(clientNameY));
		ab.setBillCustomer(Long.parseLong(billCustomer));

		if("2".equals(saveUpdate)){
			ab.setApplyId(new Date());
			ab.setApplyBillState(2L);
		}
		else{
			ab.setApplyBillState(1L);
		}

		ab.setOneOut(ischu);
		ab.setStockOrg(stockOrg);
		ab.setBillSpot(billSpot);
		//SeeBeanFields.travBean(ab);   

		List<ApplyMessage> list = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
		invoiceService.disconnectIncoice(hiddenid);
		invoiceService.updateInvoice(ab, list, hiddenid);
		ActionContext.getContext().getSession().remove("messageList");

		if(state != null){
			//如果是合同新建来的，继续修改新建....保存
			if("1".equals(state)){
				//ActionContext.getContext().getSession().put("sid",ab.getBillApplyId());
				return "saveAndOpen";
			}
			//保存关闭
			else if("2".equals(state)){
				ActionContext.getContext().getSession().remove("sid");
				ActionContext.getContext().getSession().put("succSave","0");	
				ActionContext.getContext().getSession().remove("sign");
				return "anotherApply";
			}
			//提交
			else if("3".equals(state)){
				ActionContext.getContext().getSession().remove("sid");
				ActionContext.getContext().getSession().put("succSave","3");	
				ActionContext.getContext().getSession().remove("sign");
				return "anotherApply";
			}
		}
		else{
			if("2".equals(saveUpdate)){
				succSave = "3";			
			}
			else{
				succSave = "2";
			}
			ActionContext.getContext().getSession().put("succSave",succSave);		
		}
		return "guodu";
	}

	public String printList()throws Exception{
		return "printList";
	}
	
	public String showInvoiceInfor() throws Exception {

		String id = this.applyBillId;
		hiddenid = id;
		applybill = invoiceService.findOneInvocie(id);

		name = (String)commonService.uniqueResult("select e.name from Employee e where e.id = ?", applybill.getEmployeeId());
		position = foramlContractService.getDept(applybill.getEmployeeId());

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
		applyBillState = applybill.getApplyBillState();
		returnReason = applybill.getReturnReason();  //退回理由
		returnDate = applybill.getReturnDate();
		//增加库存组织
		stockOrg = applybill.getStockOrg();

		//增加取票地点
		billSpot = applybill.getBillSpot();

		//增加一次出库
		ischu = applybill.getOneOut();
		
		
		List<ApplyMessage> list = invoiceService.findMessage(id);
		if (list != null) {
			ActionContext.getContext().getSession().put("messageList", list);
		}

		yxClientCodeList = clientlist = contractservice.findClient(UserUtils.getUser().getId());
		billCustomerList = contractservice.findAllClient(UserUtils.getUser().getId());

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
		}

		hasInvoice = billService.isBill(Long.valueOf(applyBillId));
		//显示未签的
		
		if(isCon != null){
			return "showUpdateRemark";
		}
		else{
			return "showInvoiceInforNo";
		}
	}


	/*
	 * add by lewei 打开关联页面, 根据合同主体系统号，获取未出库申购清单
	 */
	public String openAssociationByConsid() {
		String aid = "";      //搜索功能这个是

		user = UserUtils.getUser();
		Long uid = user.getId();

		try{
			aid = ((String[]) ActionContext.getContext().getParameters().get("iid"))[0];
		}catch(Exception e){
			logger.info("这个不是分页查询");
		}
		info = applyBillService.loadNotOutPurchase(uid , contractmainsid, aid, info);
		return "applyAssociationUpdate";
	}


	/*
	 * add by lewei 打开关联页面, 根据合同主体系统号，获取未出库申购清单(修改用)
	 */
	public String openAssociationByConsidUpdate() {
		String aid = "";

		user = UserUtils.getUser();
		Long uid = user.getId();

		try{
			aid = ((String[]) ActionContext.getContext().getParameters().get("iid"))[0];
		}catch(Exception e){
			logger.info("这个不是分页查询");
		}
		info = applyBillService.loadNotOutPurchase(uid , contractmainsid, aid, info); 
		return "applyAssociationUpdate";
	}


	@SuppressWarnings("unchecked")
	public String updateApply(){

		this.saveFindCondition();

		String applysid;
		//获取开票申请系统号
		applysid = ((String[]) ActionContext.getContext().getParameters().get("sid"))[0];
		applybill = invoiceService.findOneInvocie(applysid.toString());

		//如果是未签申请
		if( applybill.getInitIsNoContract().equals(1L) ){
			ActionContext.getContext().getSession().put("sid", applysid);
			ActionContext.getContext().getSession().put("sign", "modifyNoCon");
			return "modifyNoCon";
		}

		//如果是已签申请
		else{		
			applySid =  applysid;
			return "guodu";
		}
	}

	@SuppressWarnings("unchecked")
	public void saveFindCondition(){
		//保存搜索条件，用于返回
		ActionContext.getContext().getSession().put("customerId", customerId);
		ActionContext.getContext().getSession().put("billApplyNum", billApplyNum);
		ActionContext.getContext().getSession().put("beginApplyDate", beginApplyDate);
		ActionContext.getContext().getSession().put("endApplyDate", endApplyDate);
		ActionContext.getContext().getSession().put("monthPlanYear", monthPlanYear);
		ActionContext.getContext().getSession().put("monthPlanMonth", monthPlanMonth);
		ActionContext.getContext().getSession().put("applyStatus", applyStatus);
		ActionContext.getContext().getSession().put("hasSigned", hasSigned);
		ActionContext.getContext().getSession().put("conNumX", conNumX);	
		ActionContext.getContext().getSession().put("itemNumX", itemNumX);	
	}

	//获取搜索条件，用于返回
	public void getFindCondition(){
		if(ActionContext.getContext().getSession().get("customerId")!=null){
			customerId = (String)ActionContext.getContext().getSession().get("customerId");
			ActionContext.getContext().getSession().remove("customerId");
		}

		if(ActionContext.getContext().getSession().get("billApplyNum")!=null){
			billApplyNum = (String)ActionContext.getContext().getSession().get("billApplyNum");
			ActionContext.getContext().getSession().remove("billApplyNum");
		}

		if(ActionContext.getContext().getSession().get("beginApplyDate")!=null){
			beginApplyDate = (String)ActionContext.getContext().getSession().get("beginApplyDate");
			ActionContext.getContext().getSession().remove("beginApplyDate");
		}

		if(ActionContext.getContext().getSession().get("endApplyDate")!=null){
			endApplyDate = (String)ActionContext.getContext().getSession().get("endApplyDate");
			ActionContext.getContext().getSession().remove("endApplyDate");
		}

		if(ActionContext.getContext().getSession().get("monthPlanYear")!=null){
			monthPlanYear = (String)ActionContext.getContext().getSession().get("monthPlanYear");
			ActionContext.getContext().getSession().remove("monthPlanYear");
		}

		if(ActionContext.getContext().getSession().get("monthPlanMonth")!=null){
			monthPlanMonth = (String)ActionContext.getContext().getSession().get("monthPlanMonth");
			ActionContext.getContext().getSession().remove("monthPlanMonth");
		}

		if(ActionContext.getContext().getSession().get("applyStatus")!=null){
			applyStatus = (String)ActionContext.getContext().getSession().get("applyStatus" );
			ActionContext.getContext().getSession().remove("applyStatus");
		}	
		if(ActionContext.getContext().getSession().get("hasSigned")!=null){
			hasSigned = (String)ActionContext.getContext().getSession().get("hasSigned" );
			ActionContext.getContext().getSession().remove("hasSigned");
		}
		if(ActionContext.getContext().getSession().get("conNumX")!=null){
			conNumX = (String)ActionContext.getContext().getSession().get("conNumX" );
			ActionContext.getContext().getSession().remove("conNumX");
		}	
		if(ActionContext.getContext().getSession().get("itemNumX")!=null){
			itemNumX = (String)ActionContext.getContext().getSession().get("itemNumX" );
			ActionContext.getContext().getSession().remove("itemNumX");
		}
	}
	
	public String processApplyBill(){
		// 不能将已签收设成已处理，处理完后才签收的
		commonService.executeUpdate("update ApplyBill ab set ab.applyBillState = 7 where ab.applyBillState <> 6 and ab.billApplyId in ("+StringUtils.join(ids,",")+") ");
		
		StringBuffer sql = new StringBuffer();
		sql.append("update RealContractBillandRecePlan plan set plan.applyBillState = 7");
		sql.append(" where exists ");
		sql.append("(select 1 from BillandProRelaion br where br.realContractBillandRecePlan = plan.realConBillproSid and br.applyBill in ( "+StringUtils.join(ids,",")+" ) )");
		commonService.executeUpdate(sql.toString());
		ActionContext.getContext().put("processSuccess", true);
		return printBill();
	}
	//取消处理
	public String returnProcessed(){
		processResult = billService.returnProcessed(ids[0]);
		return printBill();
	}
	//取消确认
	public String returnConfirm(){
		processResult = invoiceService.checkApplyBillCancelConfirm(ids[0]);
		if(!processResult.isSuccess()){
			return printBill();
		}
		processResult = billService.returnConfirm(ids[0]);
		return printBill();
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

	public String getSaveUpdate() {
		return saveUpdate;
	}

	public void setSaveUpdate(String saveUpdate) {
		this.saveUpdate = saveUpdate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public String getApplySid() {
		return applySid;
	}

	public void setApplySid(String applySid) {
		this.applySid = applySid;
	}

	public Map<Long, Date> getProDateMap() {
		return proDateMap;
	}

	public void setProDateMap(Map<Long, Date> proDateMap) {
		this.proDateMap = proDateMap;
	}




	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}

	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}

	public IApplyBillService getBillService() {
		return billService;
	}

	public void setBillService(IApplyBillService billService) {
		this.billService = billService;
	}

	public Boolean getHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(Boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public String getConNum() {
		return conNum;
	}

	public void setConNum(String conNum) {
		this.conNum = conNum;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getConNumX() {
		return conNumX;
	}

	public void setConNumX(String conNumX) {
		this.conNumX = conNumX;
	}

	public String getHasSigned() {
		return hasSigned;
	}

	public void setHasSigned(String hasSigned) {
		this.hasSigned = hasSigned;
	}

	public Long getApplyBillState() {
		return applyBillState;
	}

	public void setApplyBillState(Long applyBillState) {
		this.applyBillState = applyBillState;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getIsCon() {
		return isCon;
	}

	public void setIsCon(String isCon) {
		this.isCon = isCon;
	}

	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}

	public String getFirstReceiveMan() {
		return firstReceiveMan;
	}

	public void setFirstReceiveMan(String firstReceiveMan) {
		this.firstReceiveMan = firstReceiveMan;
	}

	public String getItemNumX() {
		return itemNumX;
	}

	public void setItemNumX(String itemNumX) {
		this.itemNumX = itemNumX;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}



}

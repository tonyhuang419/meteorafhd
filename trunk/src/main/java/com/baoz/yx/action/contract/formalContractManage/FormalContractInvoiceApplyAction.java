package com.baoz.yx.action.contract.formalContractManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name = "formalContractInvoice", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractInvoiceApply.jsp"),
	@Result(name = "excessive", value = "/WEB-INF/jsp/invoiceManagement/excessive.jsp"),
	@Result(name = "saveSuccess", value = "/WEB-INF/jsp/contract/formalContractManage/successApply.jsp"),
	@Result(name = "failSuccess", value = "/WEB-INF/jsp/contract/formalContractManage/failApply.jsp"),
	@Result(name = "commitSuccess", value = "/WEB-INF/jsp/contract/formalContractManage/commitApply.jsp"),
	@Result(name = "showInvoice", value = "/WEB-INF/jsp/invoiceManagement/formalContractInvoiceApplyShow.jsp"),
	@Result(name = "guodu", value = "/WEB-INF/jsp/invoiceManagement/guodu.jsp"),
	@Result(name = "modifyApplyBill", value = "/WEB-INF/jsp/invoiceManagement/formalContractInvoiceApplyModify.jsp") })
	public class FormalContractInvoiceApplyAction extends DispatchAction {

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	private PageInfo info;
	private Employee user;

	private ContractMainInfo cmi; // 合同主体信息
	private YXClientCode billClient = null;         // 开票客户
	private YXClientCode conClient = null;  		// 合同客户
	private RealContractBillandRecePlan rcPlan; 	// 实际合同开票收款计划

	private String rclist = null; 					// 提交上来的开票收款计划 action传值 ..

	private String itemName = null;   				// 项目名称、号


	private String invoiceContent; 		// 开票内容
	private String remarks; 			// 备注
	private boolean oneOut;				 // 是否一次出库
	private String billSpot; 			// 取票地点
	private Double txMoney;				 // 金额小写含税
	private Double notxMoney;	 		// 金额小写不含税


	private List<YXTypeManage> stockOrgList; // 库存组织List
	private String stockOrg; 				// 库存组织


	private Long contractmainsid = null; 			// 合同主体系统号 action传值 ..
	private Long billcustomersid = null; 			// 开票客户系统号 action传值
	private String contractid = null; 				// 合同号 action传值

	private long uid = -1; 				// 申请员工系统号
//	private List<RealContractBillandRecePlan> rcplanList = null; // 实际开票和收款计划列表
	private Date now = null; 				// 当前时间
	private String applyName = null; 			// 申请人
	private String applyDept = null; 		// 申请部门

	private String conName = null;			 // 合同名称

	private Double applyAmount; 			// 申请金额


	private List<ApplyMessage> messageList; // 申购关联列表


	Map<Long, Double> rcPlanList = new HashMap<Long, Double>(); // 实际开票和收款计划列表（项目号，金额）
	private String saveSign; // 保存，提交申请 1保存，2提交待确认
	private String succSave ;   //保存、提交 成功标记 0保存成功 1提交成功 2修改成功
	
	
	private ApplyBill applybill; // 开票申请单实例
	private ApplyBill applybillx; // 开票申请单实例

	private String applyBillSid; // 开票申请系统号，读取、更新申请时使用


	private String modify; // 修改状态，决定是否跳入修改界面



	private String hiddenId;//隐藏的id在显示已签开票申请详细信息的时候使用到了！



	//创建开票申请用
	//给我一个实际开票申请计划系统号，
	//你给我 合同主体信息，合同客户信息，合同开票客户信息,实际开票收款计划实例
	public void loadRequireInfo(Long rcPlanSid){

		//获取登陆人信息
		uid = UserUtils.getUser().getId();
		applyName = foramlContractService.getName(uid);
		applyDept = foramlContractService.getDept(uid);
		now = new Date();
		logger.info(applyName);
		logger.info(applyDept);


		cmi = invoiceService.getCmiFromRCPlan(rcPlanSid);             //合同主体信息
		itemName = invoiceService.getCimiName(rcPlanSid);      		 //获取项目名称/号
		billClient = invoiceService.getBillClientInfoFromCmi(cmi);    // 开票客户
		conClient = invoiceService.getClientInfoFromCmi(cmi); 		//合同客户
		rcPlan = invoiceService.getRCPlanFromRCPlan(rcPlanSid); 		// 实际合同开票收款计划
		
		applyAmount  = Double.valueOf(rcPlan.getRealBillAmount().toString());
	
		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织

	}


	//显示、修改明细用
	//you give me a "ApplyBill" instance,i reply you 
	// 载入关联的申购信息、载入开票客户信息、载入实际开票和收款计划列表
	public void showApplyBill(){

		applybill = invoiceService.findOneInvocie(applyBillSid);
		applyBillSid = (String) ActionContext.getContext().getSession().put("applyBillSid",applyBillSid);
		hiddenId = applybill.getBillApplyId().toString();

		// 从开票申请获取申请人系统号，然后获取其姓名和部门
		long applyManSid = applybill.getEmployeeId();
		this.getApplePeopleInfoFromUid(applyManSid);

		//如果不是修改的话
		if(! "true".equals(modify)){		
			// 载入关联的申购信息
			messageList = invoiceService.findMessage(applybill.getBillApplyId().toString());	
			ActionContext.getContext().getSession().put("messageList",messageList);
		}
		// 载入开票客户信息
		billClient = foramlContractService.getCustomer(applybill.getBillCustomer());

		// 载入实际开票和收款计划列表
		rcPlanList = invoiceService.getRCplanFromApply(applybill.getBillApplyId());
		for(Object m: rcPlanList.keySet()){
			itemName = m.toString();
			applyAmount  = rcPlanList.get(m);
		}

	}
	
	

	//默认方法.....创建开票申请
	public String doDefault() {
		logger.info("doDefault");
		logger.info(rclist);
		if(rclist!=null){
			ActionContext.getContext().getSession().put("rclist", rclist);
			rclist = rclist.substring(0, 1);
			this.loadRequireInfo(Long.parseLong(rclist));
		}

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			ActionContext.getContext().getSession().remove("messageList");
		}

		this.billSpot = "宝山";

		//this.putSession();
		return "formalContractInvoice";
	}

	//刷新用方法
	public String doDefaultT() {
		Double applyAmountx = applyAmount;

		rclist = (String) ActionContext.getContext().getSession().get("rclist");
		rclist = rclist.substring(0, 1);
		this.loadRequireInfo(Long.parseLong(rclist));

		
		applyAmount = applyAmountx;		//防止刷掉数据
		
		logger.info("doDefaultT");
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}


		return "formalContractInvoice";
	}


	public String refreshUpdate() {// 刷新页面（修改时用）

		Double applyAmountx = 0d;
		applybillx = applybill;
		applyAmountx = applyAmount;

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		ActionContext.getContext().getSession().put("messageList", messageList);

		applyBillSid = (String) ActionContext.getContext().getSession().get("applyBillSid");

		this.showApplyBill();

		applybill.setBillContent(applybillx.getBillContent());
		applybill.setRemarks(applybillx.getRemarks());
		applybill.setOneOut(applybillx.getOneOut());
		applybill.setStockOrg(applybillx.getStockOrg());
		applybill.setBillSpot(applybillx.getBillSpot());
		applyAmount = applyAmountx;
		
		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织
		logger.info("返回修改界面");
		return "modifyApplyBill";

	}



	public String updateApplyBill() {
		this.showApplyBill();
		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织

		applybill = invoiceService.findOneInvocie(applyBillSid);

		succSave = "2";
		ActionContext.getContext().getSession().put("succSave", succSave);
		
		return "modifyApplyBill";
	}



	


	public String showInvoice() {
		//载入开票申请信息
		this.showApplyBill();
		return "showInvoice";

	}




	// 获取当前登陆人信息
	private void getApplePeopleInfo() {
		uid = UserUtils.getUser().getId();
		applyName = foramlContractService.getName(uid);
		applyDept = foramlContractService.getDept(uid);
		logger.info(applyName);
		logger.info(applyDept);
	}

	// 通过id获取登陆人信息
	private void getApplePeopleInfoFromUid(Long id) {
		applyName = foramlContractService.getName(id);
		applyDept = foramlContractService.getDept(id);
		logger.info(applyName);
		logger.info(applyDept);
	}

	/*
	 * 保存申请
	 */
	public String saveInvoice() {
		user = UserUtils.getUser();
		uid = user.getId();
		applybill = new ApplyBill();
		
		Double applyAmountx = applyAmount;
		

		rclist = (String) ActionContext.getContext().getSession().get("rclist");
		rclist = rclist.substring(0, 1);
		this.loadRequireInfo(Long.parseLong(rclist));


//		//获取登陆人信息
//		uid = UserUtils.getUser().getId();
//		applyName = foramlContractService.getName(uid);
//		applyDept = foramlContractService.getDept(uid);
//		now = new Date();


		applyAmount = applyAmountx;
		
//		cmi = invoiceService.getCmiFromRCPlan(rcPlanSid);             //合同主体信息
//		itemName = invoiceService.getCimiName(rcPlanSid);      		 //获取项目名称/号
//		billClient = invoiceService.getBillClientInfoFromCmi(cmi);    // 开票客户
//		conClient = invoiceService.getClientInfoFromCmi(cmi); 		//合同客户
//		rcPlan = invoiceService.getRCPlanFromRCPlan(rcPlanSid); 		// 实际合同开票收款计划

//		stockOrgList = typeManageService.getYXTypeManage(1021L);

		applybill.setBillApplyNum("");
		applybill.setContactName(cmi.getConName());
		applybill.setContractMainInfo(cmi.getConMainInfoSid());
		applybill.setEmployeeId(uid);
		applybill.setCustomerId(cmi.getConCustomer());
		applybill.setBillCustomer(cmi.getBillCustomer());
		applybill.setApplyId(now); 
		applybill.setBillAmountTax(txMoney);
		applybill.setBillAmountNotax(notxMoney);
		applybill.setBase(rcPlan.getBase());
		applybill.setBillContent(invoiceContent);
		applybill.setBillNature(rcPlan.getBillNature());
		applybill.setBillType(rcPlan.getBillType());
		applybill.setRemarks(remarks);
		applybill.setOneOut(oneOut);
		applybill.setApplyBillState(Long.parseLong(saveSign)); //  1保存   // 2待确认 3确认通过  // 4确认退回
		applybill.setIsNoContract(false);  // 已签开票申请....false
		applybill.setSign(false);
		applybill.setStockOrg(stockOrg);
		applybill.setBillSpot(billSpot);
		applybill.setInitIsNoContract(false);   //已签未签初始状态。。。。已签。。。false

//		SeeBeanFields.travBean(applybill);


		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		invoiceService.saveApplications(applybill, listSave, rclist,applyAmount);


		// 保存完成,清理session
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			ActionContext.getContext().getSession().remove("messageList");
		}
		if (ActionContext.getContext().getSession().get("rclist") != null) {
			ActionContext.getContext().getSession().remove("rclist");
		}

		if (saveSign.equals("1")) {
			return "saveSuccess";
		} else if (saveSign.equals("2")) {
			return "commitSuccess";
		}
		return "failSuccess";
	}
	
	

	public String updateSave(){

		applybillx = applybill;
	
		applyBillSid = (String) ActionContext.getContext().getSession().get("applyBillSid");
		applybill = invoiceService.findOneInvocie(applyBillSid);
		
		
		rcPlanList = invoiceService.getRCplanFromApply(applybill.getBillApplyId());
		for(Object m: rcPlanList.keySet()){
			itemName = m.toString();
		}
//		因为开票计划N对开票申请1.。。由此找出关联数据，修改之.....e...又没了....一个从多对多变成1对1的股市
//		BillandProRelaion r = null;
//		r = invoiceService.getBPR(Long.parseLong(applyBillSid));
//		r.setRelateAmount(applyAmount);
//		r.setRelateAmount(txMoney);
		
		applybill.setBillContent(applybillx.getBillContent());
		applybill.setRemarks(applybillx.getRemarks());
		applybill.setOneOut(applybillx.getOneOut());
		applybill.setStockOrg(applybillx.getStockOrg());
		applybill.setBillSpot(applybillx.getBillSpot());
//		applybill.setBillAmountTax(txMoney);
//		applybill.setBillAmountNotax(notxMoney);
		
		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		
//		invoiceService.disconnectIncoice(applybill.getBillApplyId().toString());
//		invoiceService.updateInvoiceHasContract(applybill, listSave, r);
		
		
		// 保存完成,清理session
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			ActionContext.getContext().getSession().remove("messageList");
		}
		
		// 保存完成,清理session
		if (ActionContext.getContext().getSession().get("applyBillSid") != null) {
			ActionContext.getContext().getSession().remove("applyBillSid");
		}
		
		return "guodu";
	}
	
	

	/*
	 * 这个方法copy自CreateInvoiceAction   进行申购关联保存到session中
	 */
	public String applicationsAssociation() {
		logger.info("applicationsAssociation");
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

	/*
	 * 这个方法copy自CreateInvoiceAction 删除一个关联
	 */
	public String applicationsDelete() {
		Double applyAmountx = applyAmount;
		
		List<ApplyMessage> list = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			list = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
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
		
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		ActionContext.getContext().getSession().put("messageList", messageList);



		if("true".equals(modify)){
			applyBillSid = (String) ActionContext.getContext().getSession().get("applyBillSid");  //<<<<<<<<<<<<<<<<<<<<<<<
			this.showApplyBill();
			
			applyAmount = applyAmountx;  	//防止刷掉数据
			
			stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织
			logger.info("返回修改界面");
			return "modifyApplyBill";
		}

		else{
			rclist = (String)ActionContext.getContext().getSession().get("rclist");
			logger.info(rclist);
			rclist = rclist.substring(0, 1);
			this.loadRequireInfo(Long.parseLong(rclist));
			
			applyAmount = applyAmountx;		//防止刷掉数据
			
			ActionContext.getContext().getSession().get("rclist");
			return "formalContractInvoice";
		}
	}



	// 开票申请时，载入开票申请信息
	public void loadBillApplyInfo() {
		this.getApplePeopleInfo(); // 载入申请人ID，姓名，部门
		this.loadRequireInfo(Long.parseLong(rclist));

		// 载入库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);
		now = new Date();

	}


	public String submitInvoice() {
		return null;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public ApplyBill getApplybill() {
		return applybill;
	}

	public void setApplybill(ApplyBill applybill) {
		this.applybill = applybill;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRclist() {
		return rclist;
	}

	public void setRclist(String rclist) {
		this.rclist = rclist;
	}

//	public List<RealContractBillandRecePlan> getRcplanList() {
//	return rcplanList;
//	}

//	public void setRcplanList(List<RealContractBillandRecePlan> rcplanList) {
//	this.rcplanList = rcplanList;
//	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}

	public Long getContractmainsid() {
		return contractmainsid;
	}

	public void setContractmainsid(Long contractmainsid) {
		this.contractmainsid = contractmainsid;
	}

	public Long getBillcustomersid() {
		return billcustomersid;
	}

	public void setBillcustomersid(Long billcustomersid) {
		this.billcustomersid = billcustomersid;
	}

	public YXClientCode getBillClient() {
		return billClient;
	}

	public void setBillClient(YXClientCode billClient) {
		this.billClient = billClient;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public boolean isOneOut() {
		return oneOut;
	}

	public void setOneOut(boolean oneOut) {
		this.oneOut = oneOut;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public List<ApplyMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ApplyMessage> messageList) {
		this.messageList = messageList;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Double getTxMoney() {
		return txMoney;
	}

	public void setTxMoney(Double txMoney) {
		this.txMoney = txMoney;
	}

	public Double getNotxMoney() {
		return notxMoney;
	}

	public void setNotxMoney(Double notxMoney) {
		this.notxMoney = notxMoney;
	}

	public String getSaveSign() {
		return saveSign;
	}

	public void setSaveSign(String saveSign) {
		this.saveSign = saveSign;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
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

	public String getBillSpot() {
		return billSpot;
	}

	public void setBillSpot(String billSpot) {
		this.billSpot = billSpot;
	}

	public String getApplyBillSid() {
		return applyBillSid;
	}

	public void setApplyBillSid(String applyBillSid) {
		this.applyBillSid = applyBillSid;
	}

	public Map<Long, Double> getRcPlanList() {
		return rcPlanList;
	}

	public void setRcPlanList(Map<Long, Double> rcPlanList) {
		this.rcPlanList = rcPlanList;
	}


	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public RealContractBillandRecePlan getRcPlan() {
		return rcPlan;
	}

	public void setRcPlan(RealContractBillandRecePlan rcPlan) {
		this.rcPlan = rcPlan;
	}

	public ContractMainInfo getCmi() {
		return cmi;
	}

	public void setCmi(ContractMainInfo cmi) {
		this.cmi = cmi;
	}

	public YXClientCode getConClient() {
		return conClient;
	}

	public void setConClient(YXClientCode conClient) {
		this.conClient = conClient;
	}

	public String getHiddenId() {
		return hiddenId;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}




	public ApplyBill getApplybillx() {
		return applybillx;
	}

	public void setApplybillx(ApplyBill applybillx) {
		this.applybillx = applybillx;
	}


	public String getSuccSave() {
		return succSave;
	}


	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}
}

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
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractCommonService;
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
	@Result(name = "showUpdateRemark", value = "/WEB-INF/jsp/billtoReceipt/updateBillRemarks.jsp"),
	@Result(name = "guodu", value = "/WEB-INF/jsp/invoiceManagement/guodu.jsp"),
	@Result(name = "relation", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp"),
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
	@Qualifier("contractCommonService")
	private IContractCommonService 			contractCommonService;
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 			billService;

	private PageInfo info;
	private Employee user;

	private ContractMainInfo cmi;                   // 合同主体信息
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

	private String isUpdate;  //用来判断是否是从修改备注中弹出
	
	private List<YXTypeManage> stockOrgList; // 库存组织List
	private String stockOrg; 				// 库存组织


	private Long contractmainsid = null; 			// 合同主体系统号 action传值 ..
	private Long billcustomersid = null; 			// 开票客户系统号 action传值
	private String contractid = null; 				// 合同号 action传值

	private long uid = -1; 				        // 申请员工系统号
//	private List<RealContractBillandRecePlan> rcplanList = null; // 实际开票和收款计划列表
	private Date now = null; 				     // 当前时间
	private String applyName = null; 			// 申请人
	private String applyDept = null; 		// 申请部门

	private String conName = null;			 // 合同名称

	private Double applyAmount; 			// 申请金额


	private List<ApplyMessage> messageList; // 申购关联列表


	Map<Long, Double> rcPlanList = new HashMap<Long, Double>(); // 实际开票和收款计划列表（系统号，金额）
	private String saveSign; // 保存，提交申请 1保存，2提交待确认
	private String succSave ;   //保存、提交 成功标记 0保存成功 1提交成功 2修改成功


	private ApplyBill applybill; // 开票申请单实例
	private ApplyBill applybillx; // 开票申请单实例

	private String applyBillSid; // 开票申请系统号，读取、更新申请时使用

	private String modify; // 修改状态，决定是否跳入修改界面
	private String hiddenId;//隐藏的id在显示已签开票申请详细信息的时候使用到了！
	private String saveUpdate;  //判断是否保存并提交 。。。。。2 保存并提交	
	private String opType;//判断按钮是否显示

	////////////////////
	private String mainid;        //合同主体系统号  action传
	private String eventId;       //项目号  action传
	private String moneyType;     //开票性质
	private Long moneyTypeSn;     //开票性质序号
	private String invoiceType;   // 开票类型
	private String comeFrom;               //1 正式合同管理模块——开收据  2已签合同开票模块——开收据  3预决算开票申请
	Map<Long, Double> rrcPlanList = new HashMap<Long, Double>(); // 实际开票和收款计划列表（计划系统号，金额）
	private String succValidate;        //1计划在一个合同  2计划不再一个合同
	private Double maxAmount;        //通过计划累加出来的最高金额，正式合同——开收据不能超过该值
	private Long applyWay;
	private Boolean hasInvoice;    //开票申请是否存在开票
	
	//搜索条件。。start
	private String customerId;				// 客户名称

	private String billApplyNum;			// 申请编号

	private String beginApplyDate;			// 申请日期开始

	private String endApplyDate;			// 申请日期结束

	private String monthPlanYear;			// 月计划年

	private String monthPlanMonth;			// 月计划月

	private String applyStatus;				// 状态
	//搜索条件。。end
	
	private Long 	aBillId; 				//bill的id   修改备注的使用

	private String firstReceiveMan;			 //甲方接收人
	
	private ApplyBill showBill;  //显示申请单
	
	private List<RealContractBillandRecePlan> receiptList;
	
	private List<InvoiceInfo> showInvoiceList;   //显示发票信息
	
	private List<YXTypeManage> invoiceList;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}


	//创建开票申请用
	//给我一个实际开票申请计划系统号，
	//你给我 合同主体信息，合同客户信息，合同开票客户信息,实际开票收款计划实例
	public void loadRequireInfo(Long rcPlanSid){

		//获取登陆人信息
		uid = UserUtils.getUser().getId();
		applyName = foramlContractService.getName(uid);
		applyDept = foramlContractService.getDept(uid);
		now = new Date();
//		logger.info(applyName);
//		logger.info(applyDept);


		cmi = invoiceService.getCmiFromRCPlan(rcPlanSid);             //合同主体信息
		itemName = invoiceService.getCimiName(rcPlanSid);      		 //获取项目名称/号
		billClient = invoiceService.getBillClientInfoFromCmi(cmi);    // 开票客户
		conClient = invoiceService.getClientInfoFromCmi(cmi); 		//合同客户

		rcPlan = invoiceService.getRCPlanFromRCPlan(rcPlanSid); 		// 实际合同开票收款计划
		applyAmount  = Double.valueOf(rcPlan.getRealBillAmount().toString());
		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织

	}
	
	public String showUpdateRemark(){
		showApplyBill();
		return "showUpdateRemark";
	}

	//显示、修改明细用
	//you give me a "ApplyBill" instance,i reply you 
	// 载入关联的申购信息、载入开票客户信息、载入实际开票和收款计划列表
	@SuppressWarnings("unchecked")
	public void showApplyBill(){ 
		applybill = invoiceService.findOneInvocie(applyBillSid);
		applyBillSid = (String) ActionContext.getContext().getSession().put("applyBillSid",applyBillSid);
		hiddenId = applybill.getBillApplyId().toString();

		// 从开票申请获取申请人系统号，然后获取其姓名和部门
		long applyManSid = applybill.getEmployeeId();
		this.getApplePeopleInfoFromUid(applyManSid);
		
		if( modify == null){  //修改进入
			messageList = invoiceService.findMessage(applybill.getBillApplyId().toString());	
		}
		else if( modify!=null  && ! "true".equals(modify)){		//添加删除申购，刷新页面
			// 载入关联的申购信息
			messageList = invoiceService.findMessage(applybill.getBillApplyId().toString());	
		}
		ActionContext.getContext().getSession().put("messageList",messageList);
		// 载入开票客户信息
		billClient = foramlContractService.getCustomer(applybill.getBillCustomer());
		invoiceList = typeManageService.getYXTypeManage(1004L);
//		logger.info(applybill.getApplyWay());
		
//		开票申请入口  预决算开票
//		if(applybill.getApplyWay().equals(0L)){
//			itemName = foramlContractService.getItemNo(applybill.getItemSid());	
//			invoiceList = typeManageService.getYXTypeManage(1004L);
//			if(applybill.getBase().equals(1L)){
//				applyAmount = applybill.getBillAmountTax();
//			}
//			else{
//				applyAmount = applybill.getBillAmountNotax();
//			}
//		}
//		开票申请入口  合同已签开票申请 
		if(applybill.getApplyWay().equals(1L)){
			rcPlanList = invoiceService.getRCplanFromApply(applybill.getBillApplyId());
			if(applybill.getBase().equals(1L)){
				applyAmount = applybill.getBillAmountTax();
			}
			else{
				applyAmount = applybill.getBillAmountNotax();
			}
		}
//		开票申请入口  收据申请..预决算
		else if(applybill.getApplyWay().equals(0L)  ||  applybill.getApplyWay().equals(2L)){
			rcPlanList = invoiceService.getRCplanFromApply(applybill.getBillApplyId());
			if(applybill.getBase().equals(1L)){
				applyAmount = applybill.getBillAmountTax();
			}
			else{
				applyAmount = applybill.getBillAmountNotax();
			}

			maxAmount = new Double(0d); 
			for( Object m: rcPlanList.keySet() ){				
				maxAmount += foramlContractService.getRemainAmountByRealPro(Long.parseLong(m.toString()));
			}		
		}
		else{
			// 载入实际开票和收款计划列表
			rcPlanList = invoiceService.getRCplanFromApply(applybill.getBillApplyId());
//			for(Object m: rcPlanList.keySet()){
//			itemName = m.toString();
//			applyAmount  = rcPlanList.get(m);
//			}
		}
	}



	//创建开票申请用
	//给我一个项目系统号系统号，
	//你给我 合同主体信息，合同客户信息，合同开票客户信息,实际开票收款计划实例
	public void loadRequireInfoFromItem(Long cmiSid , Long itemSid){

		//获取登陆人信息
		uid = UserUtils.getUser().getId();
		applyName = foramlContractService.getName(uid);
		applyDept = foramlContractService.getDept(uid);
		now = new Date();


		cmi = foramlContractService.loadContractMainInfo(cmiSid);
		itemName = (String)foramlContractService.getItemNo(Long.parseLong(eventId));

		billClient = invoiceService.getBillClientInfoFromCmi(cmi);    // 开票客户
		conClient = invoiceService.getClientInfoFromCmi(cmi); 		//合同客户

		moneyTypeSn = Long.parseLong(moneyType);

		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织
		invoiceList = typeManageService.getYXTypeManage(1004L);
	}



	//默认方法.....创建开票申请...预决算开票
	public String doDefault() {
		//logger.info("doDefault");
		//logger.info(rclist);
		//if(rclist!=null){
		//ActionContext.getContext().getSession().put("rclist", rclist);
		//rclist = rclist.substring(0, 1);
		this.loadRequireInfoFromItem( Long.parseLong(mainid) , Long.parseLong(eventId) );
		//}

		ActionContext.getContext().getSession().put("mainid",mainid);
		ActionContext.getContext().getSession().put("eventId",eventId);
		ActionContext.getContext().getSession().put("moneyType",moneyType);


		if (ActionContext.getContext().getSession().get("messageList") != null) {
			ActionContext.getContext().getSession().remove("messageList");
		}

		this.billSpot = "宝山";
		this.applyAmount = 0d;

		//this.putSession();
		return "formalContractInvoice";
	}



	//创建开票申请用
	//给我一个计划数组
	//你给我 合同主体信息，合同客户信息，合同开票客户信息,实际开票收款计划实例
	public void loadRequireInfoFromPlan(String[] rcbrps ){

		ActionContext.getContext().getSession().put("rcbrps",rcbrps);

		//获取登陆人信息
		uid = UserUtils.getUser().getId();
		applyName = foramlContractService.getName(uid);
		applyDept = foramlContractService.getDept(uid);
		now = new Date();

		RealContractBillandRecePlan r = foramlContractService.getRCPlan(rcbrps[0]);
		invoiceContent = r.getBillContent();
		
		cmi = foramlContractService.loadContractMainInfo(r.getConMainInfoSid());

		rrcPlanList = foramlContractService.getRcpalnList(rcbrps);
		ActionContext.getContext().getSession().put("rrcPlanList",rrcPlanList);

//		for(Object m: rrcPlanList.keySet()){
//			maxAmount += rrcPlanList.get(m);					
//		}
		
		maxAmount = new Double(0d); 
		for(int i=0;i<rcbrps.length;i++ ){			
			maxAmount += foramlContractService.getRemainAmountByRealPro(Long.parseLong(rcbrps[i]));
		}
		
		billClient = invoiceService.getBillClientInfoFromCmi(cmi);    // 开票客户
		conClient = invoiceService.getClientInfoFromCmi(cmi); 		//合同客户

//		moneyTypeSn = Long.parseLong(moneyType);

		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织
		invoiceList = typeManageService.getYXTypeManage(1004L);

		billClient = invoiceService.getBillClientInfoFromCmi(cmi);    // 开票客户
		conClient = invoiceService.getClientInfoFromCmi(cmi); 		//合同客户

//		applyAmount  = Double.valueOf(rcPlan.getRealBillAmount().toString());
		stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织

		if("1".equals(comeFrom)  ||  "2".equals(comeFrom) ){
			invoiceType = "4";
		}
		else{
			invoiceType = r.getBillType();
			moneyType = r.getBillNature();
		}
	}

	//正式合同管理。。。开票申请...action传计划系统号
	public String doApplyBillFromRcbrps() {
		String[] rcbrps;
		//入口是。。已签合同。。。收据申请
		if("2".equals(comeFrom)){
			rcbrps = (String[]) ActionContext.getContext().getParameters().get("monthlyBillproSids");
			//计划不属于同一个合同
			if ( ! foramlContractService.isOneContract(rcbrps) ){
				succValidate = "2";
				//刷新父页面时需要的参数
				//ActionContext.getContext().getSession().put("succSave", "1");
				return "failSuccess";
			}
		}
		else{
			rcbrps = (String[]) ActionContext.getContext().getParameters().get("rcbrps");
		}
		this.loadRequireInfoFromPlan(rcbrps);

		String temp = (String)service.uniqueResult(" select y.name from ContractMainInfo cmi,YXLinkMan y where " +
				" cmi.linkManId = y.id and cmi.conMainInfoSid  = ?", cmi.getConMainInfoSid());
		if(temp==null){
			temp="";
		}
		this.remarks = cmi.getPartyAProId()+"/"+temp;
		this.billSpot = "宝山";
		this.applyAmount = 0d;

		//this.putSession();
		return "formalContractInvoice";
	}

	//刷新用方法
	public String doDefaultT() {
		//Double applyAmountx = applyAmount;

		String s[] = (String[])ActionContext.getContext().getSession().get("rcbrps");
		if(s!=null || s.length > 0){
//			s = this.getRCPlanList(rr);
			this.loadRequireInfoFromPlan(s);
		}
		else{
			mainid = (String)ActionContext.getContext().getSession().get("mainid");
			eventId = (String)ActionContext.getContext().getSession().get("eventId");
			moneyType = (String)ActionContext.getContext().getSession().get("moneyType");

			this.loadRequireInfoFromItem( Long.parseLong(mainid) , Long.parseLong(eventId) );

			//applyAmount = applyAmountx;		//防止刷掉数据
		}

		if (ActionContext.getContext().getSession().get("messageList") != null) {
			messageList = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}
		return "formalContractInvoice";
	}


	public String refreshUpdate() {// 刷新页面（修改时用）

		Double applyAmountx = applyAmount;
		applybillx = applybill;
//		applyAmountx = applyAmount;

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
		return "modifyApplyBill";
	}

	public String showInvoice() {
		//载入开票申请信息
		this.showApplyBill();
		hasInvoice = billService.isBill(Long.valueOf(applyBillSid));
		//申请单信息
		receiptList = contractCommonService.showReceInfo(Long.valueOf(applyBillSid));
		
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




	//一个工具方法,将rrcPlanList返回String[].....开票计划
	public String[] getRCPlanList(Map<Long, Double> rrcPlanList){
		int len = rrcPlanList.size();
		String s[] = new String[len];
		List r = new ArrayList();

		for(Object m: rrcPlanList.keySet()){
			r.add(m.toString());
		}
		for(int i=0;i<r.size();i++){
			s[i] = r.get(i).toString();
		}		
		return s;
	}


	/*
	 * 保存申请
	 */
	public String saveInvoice() {
		user = UserUtils.getUser();
		uid = user.getId();
		applybill = new ApplyBill();
		String invoiceContentStr = invoiceContent;    //保存开票内容，之后会被冲掉

		//如果是正式合同。。。收据申请,预决算 的 存在rcbrps信息
		String s[] = (String[])ActionContext.getContext().getSession().get("rcbrps");	

		if(s!=null ){
			if(s.length > 0){
//				Map<Long, Double> rcPlanList = (Map<Long, Double>) ActionContext.getContext().getParameters().get("rrcPlanList");

				Map<Long, Double> rrcPlanList = (Map<Long, Double>) ActionContext.getContext().getSession().get("rrcPlanList");

//				s = this.getRCPlanList(rr);
				this.loadRequireInfoFromPlan(s);
				applybill.setInitIsNoContract(0L);
				applybill.setBillType(invoiceType);
				applybill.setApplyWay(applyWay);
			}
		}

		else{	
//			mainid = (String)ActionContext.getContext().getSession().get("mainid");
//			eventId = (String)ActionContext.getContext().getSession().get("eventId");
//			moneyType = (String)ActionContext.getContext().getSession().get("moneyType");
//			this.loadRequireInfoFromItem( Long.parseLong(mainid) , Long.parseLong(eventId) );
//
//			applybill.setInitIsNoContract(0L);
//			applybill.setItemSid(Long.parseLong(eventId));
//			applybill.setBillType(invoiceType);
//			applybill.setApplyWay(0L);
		}


		applybill.setBillApplyNum("");
		applybill.setContactName(cmi.getConName());
		applybill.setContractMainInfo(cmi.getConMainInfoSid());
		applybill.setEmployeeId(uid);
		applybill.setCustomerId(cmi.getConCustomer());
		applybill.setBillCustomer(cmi.getBillCustomer());
		
		applybill.setBillAmountTax(txMoney);
		applybill.setBillAmountNotax(notxMoney);

		if (saveSign.equals("2")) {
			applybill.setApplyId(now); 
		}
		
		logger.info(eventId);
//		logger.info(Long.parseLong(eventId));
		if("2".equals(cmi.getStandard())){
			applybill.setBase(0L);
		}
		else{
			applybill.setBase(Long.parseLong(cmi.getStandard()));	
		}

		applybill.setBillContent(invoiceContentStr);
		applybill.setBillNature(moneyType);	
		applybill.setRemarks(remarks);
		applybill.setOneOut(oneOut);
		applybill.setApplyBillState(Long.parseLong(saveSign)); //  1保存   // 2待确认 3确认通过  // 4确认退回
		applybill.setIsNoContract(false);  // 已签开票申请....false
		applybill.setSign(false);
		applybill.setStockOrg(stockOrg);
		applybill.setBillSpot(billSpot);
		applybill.setFirstReceiveMan(firstReceiveMan);
//		applybill.setInitIsNoContract(0L);   //已签未签初始状态。。。。已签。。。0
//		增加 原始是否未签申请
//		applybill.setInitIsNoContract(0L);
		

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext().getSession().get("messageList");
		}

		//开收据保存、、、开票申请，申购采购（无），。。。计划。。金额关联
		if(s!=null ){
			if(s.length > 0){
				invoiceService.saveApplicationsX(applybill, null, rrcPlanList);
			}
		}
		else{
			invoiceService.saveApplications(applybill, listSave, null, txMoney);
		}

		// 保存完成,清理session
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			ActionContext.getContext().getSession().remove("messageList");
		}
		if (ActionContext.getContext().getSession().get("mainid") != null) {
			ActionContext.getContext().getSession().remove("mainid");
		}
		if (ActionContext.getContext().getSession().get("eventId") != null) {
			ActionContext.getContext().getSession().remove("eventId");
		}
		if (ActionContext.getContext().getSession().get("moneyType") != null) {
			ActionContext.getContext().getSession().remove("moneyType");
		}
		if (ActionContext.getContext().getSession().get("rcbrps") != null) {
			ActionContext.getContext().getSession().remove("rcbrps");
		}
		if (ActionContext.getContext().getSession().get("rrcPlanList") != null) {
			ActionContext.getContext().getSession().remove("rrcPlanList");
		}		

		if (saveSign.equals("1")) {
			return "saveSuccess";
		} else if (saveSign.equals("2")) {
			return "commitSuccess";
		}
		return "failSuccess";
	}
	/**
	 * 修改备注
	 * @return
	 */
	public String updateRemark(){
		ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, aBillId);
		bill.setRemarks(remarks);
		service.update(bill);
		succSave = "6";
		ActionContext.getContext().getSession().put("succSave", succSave);
		return "relation";
	}

	public String updateSave(){
		
		applybillx = applybill; 

		applyBillSid = (String) ActionContext.getContext().getSession().get("applyBillSid");

//		按系统号获取实例，用于修改		
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
//		applybill.setRemarks(applybillx.getRemarks());
		applybill.setFirstReceiveMan(applybillx.getFirstReceiveMan());
		
		//如果是预决算申请。。。申请金额存在修改....收据修改。。。。注：合并开票金额不存在修改
		if(applybill.getApplyWay().equals(0L) || applybill.getApplyWay().equals(2L)){
			applybill.setBillAmountTax(txMoney);
			applybill.setBillAmountNotax(notxMoney);
		}

		if("2".equals(saveUpdate)){
			applybill.setApplyId(new Date());
			applybill.setApplyBillState(2L);
		}

		List<ApplyMessage> listSave = new ArrayList<ApplyMessage>();
		if (ActionContext.getContext().getSession().get("messageList") != null) {
			listSave = (List<ApplyMessage>) ActionContext.getContext()
			.getSession().get("messageList");
		}

		invoiceService.disconnectIncoice(applybill.getBillApplyId().toString());
		invoiceService.updateInvoiceHasContract(applybill, listSave, rcPlanList);

		if("2".equals(saveUpdate)){
			succSave = "3";			
		}
		else{
			succSave = "2";
		}
		ActionContext.getContext().getSession().put("succSave", succSave);

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
//		Double applyAmountx = applyAmount;

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

//			applyAmount = applyAmountx;  	//防止刷掉数据

			stockOrgList = typeManageService.getYXTypeManage(1021L);   //载入库存组织
			logger.info("返回修改界面");
			return "modifyApplyBill";
		}

		else{
			String s[] = (String[])ActionContext.getContext().getSession().get("rcbrps");
			if(s!=null || s.length > 0){
//				s = this.getRCPlanList(rr);
				this.loadRequireInfoFromPlan(s);
				return "formalContractInvoice";
			}
			else{
				mainid = (String)ActionContext.getContext().getSession().get("mainid");
				eventId = (String)ActionContext.getContext().getSession().get("eventId");
				moneyType = (String)ActionContext.getContext().getSession().get("moneyType");

				this.loadRequireInfoFromItem( Long.parseLong(mainid) , Long.parseLong(eventId) );
				return "formalContractInvoice";
			}
		}
	}



//	开票申请时，载入开票申请信息
	public void loadBillApplyInfo() {
		this.getApplePeopleInfo(); // 载入申请人ID，姓名，部门
		this.loadRequireInfo(Long.parseLong(rclist));

		// 载入库存组织
		stockOrgList = typeManageService.getYXTypeManage(1021L);
		now = new Date();

	}

	
	//获取搜索条件，用于返回
	public void getFindCondition(){			
		customerId = (String)ActionContext.getContext().getSession().get("customerId");
		ActionContext.getContext().getSession().remove("sid");
		
		billApplyNum = (String)ActionContext.getContext().getSession().get("billApplyNum");
		ActionContext.getContext().getSession().remove("billApplyNum");
		
		beginApplyDate = (String)ActionContext.getContext().getSession().get("beginApplyDate");
		ActionContext.getContext().getSession().remove("beginApplyDate");
		
		endApplyDate = (String)ActionContext.getContext().getSession().get("endApplyDate");
		ActionContext.getContext().getSession().remove("endApplyDate");
		
		monthPlanYear = (String)ActionContext.getContext().getSession().get("monthPlanYear");
		ActionContext.getContext().getSession().remove("monthPlanYear");
		
		monthPlanMonth = (String)ActionContext.getContext().getSession().get("monthPlanMonth");
		ActionContext.getContext().getSession().remove("monthPlanMonth");
		
		applyStatus = (String)ActionContext.getContext().getSession().get("applyStatus" );
		ActionContext.getContext().getSession().remove("applyStatus");
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


	public String getSaveUpdate() {
		return saveUpdate;
	}


	public void setSaveUpdate(String saveUpdate) {
		this.saveUpdate = saveUpdate;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Long getMoneyTypeSn() {
		return moneyTypeSn;
	}

	public void setMoneyTypeSn(Long moneyTypeSn) {
		this.moneyTypeSn = moneyTypeSn;
	}

	public List<YXTypeManage> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<YXTypeManage> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Map<Long, Double> getRrcPlanList() {
		return rrcPlanList;
	}

	public void setRrcPlanList(Map<Long, Double> rrcPlanList) {
		this.rrcPlanList = rrcPlanList;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getSuccValidate() {
		return succValidate;
	}

	public void setSuccValidate(String succValidate) {
		this.succValidate = succValidate;
	}



	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public Long getApplyWay() {
		return applyWay;
	}

	public void setApplyWay(Long applyWay) {
		this.applyWay = applyWay;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
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

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Long getABillId() {
		return aBillId;
	}

	public void setABillId(Long billId) {
		aBillId = billId;
	}

	public String getFirstReceiveMan() {
		return firstReceiveMan;
	}

	public void setFirstReceiveMan(String firstReceiveMan) {
		this.firstReceiveMan = firstReceiveMan;
	}

	public ApplyBill getShowBill() {
		return showBill;
	}

	public void setShowBill(ApplyBill showBill) {
		this.showBill = showBill;
	}

	public List<InvoiceInfo> getShowInvoiceList() {
		return showInvoiceList;
	}

	public void setShowInvoiceList(List<InvoiceInfo> showInvoiceList) {
		this.showInvoiceList = showInvoiceList;
	}

	public List<RealContractBillandRecePlan> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<RealContractBillandRecePlan> receiptList) {
		this.receiptList = receiptList;
	}

}

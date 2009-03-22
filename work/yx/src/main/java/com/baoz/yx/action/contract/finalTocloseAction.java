package com.baoz.yx.action.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ChangeRealContractBillandRecePlan;
import com.baoz.yx.entity.contract.ChangingContractItemInfo;
import com.baoz.yx.entity.contract.ChangingContractItemStagePlan;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractChangeExplain;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.IFinalToCloseService;
import com.baoz.yx.service.IFormalContractModifyService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 结算转决算操作
 * 
 * 
 * 
 */
@Results( {
		@Result(name = "showmain", value = "/WEB-INF/jsp/contract/finalToclose/newContractMainInfo.jsp"),
		@Result(name = "showEvent", value = "/WEB-INF/jsp/contract/finalToclose/newContractEventInfo.jsp"),
		@Result(name = "showStage", value = "/WEB-INF/jsp/contract/finalToclose/newContractStage.jsp"),
		@Result(name = "showPlan", value = "/WEB-INF/jsp/contract/finalToclose/newContractPlanInfo.jsp"),
		@Result(name = "showInvoice", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractConInvoice.jsp"),
		@Result(name = "success",type =  ServletRedirectResult.class,value = "/contract/searchReservationReturn.action"),
		@Result(name = "showSelfProduct", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractSelfProduct.jsp"),
		@Result(name = "showOtherInfo", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractOtherInfo.jsp"),
		@Result(name = "haschange", value = "/WEB-INF/jsp/contract/finalToclose/haschangeinfo.jsp"),
		@Result(name = "customersContract", type =  ServletRedirectResult.class, value = "/contract/contractCustoms.action?clietId=${clietId}"),
		@Result(name = "applySubmitSuccess", type = ServletRedirectResult.class, value = "/contract/searchReservationReturn.action")
})
public class finalTocloseAction extends DispatchAction {
	
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemservice;

	@Autowired
	@Qualifier("formalContractModifyService")
	private IFormalContractModifyService contractservice;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice1;
	
	@Autowired
	@Qualifier("finalToCloseService")
	private IFinalToCloseService finalToCloseService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService  commonService;

	// 客户列表
	private List<YXClientCode> clientlist;
	
	private String clientCustomer;//客户
	
	private String billCustomer;//开票客户
	
	private String itemCustomer;//项目单位
	
	private String linkMan;//客户联系人
	
	private String customereventtype;//客户项目类型
		


	// 项目客户列表
	private List<YXClientCode> eventclientlist;

	// 开票客户列表
	private List<YXClientCode> allclientlist;

	// 客户类型列表
	private List<YXTypeManage> clienttype;

	// 合同性质
	private List<YXTypeManage> contractnature;

	// 合同类型
	private List<YXTypeManage> contracttype;

	// 发票类型
	private List<YXTypeManage> tickettype;

	// 项目阶段
	private List<YXTypeManage> projectPhaseList;


	// 合同金额组成列表
	private List<ChangingContractMaininfoPart> mainMoneyList;

	// 合同金额组成和阶段金额列表
	private List<Object[]> mainMoneyWithPlanAmountList;

	// 阶段信息列表
	// private List<ContractItemStage> stagelist;


	// 合同开票收款计划对象
	private List<ChangeRealContractBillandRecePlan> planlist;

	// 合同关联的申购采购
	private List<ApplyMessage> purchaselist;

	// 合同关联的开票申请
	private List<ApplyBill> invoicelist;

	// 合同关联的自有产品信息
	private List<ContractOwnProduce> ownproductlist;


	// 合同项目对象
	private ContractItemMaininfo iteminfo;

	// 合同开票收款阶段对象
	private ContractItemStage itemstage;

	// 合同其他信息对象
	private ContractOtherInfo otherinfo;

	// 拆分的项目号
	private Long splititemNum;

	// 拆分的阶段号
	private Long splitstageNum;

	// 记录页面跳转的值
	private int tag;

	// 负责部门数
	private int departNum;

	// 阶段履历数
	private int stageNum;

	// 客户项目类型
	private List<YXTypeManage> customeventypelist;

	// 工程责任部门
	private List<YXTypeManage> dutydepartmentlist;

	// 项目组成代码
	private List<YXTypeManage> itemdesigntypelist;

	// 货币代码
	private List<YXTypeManage> copecklist;

	// 项目阶段计划
	private ContractItemStagePlan stagePlan;

	// 项目页面中做添加操作的号
	private String eventaddid;

	// 项目页面中做删除操作的号
	private String eventdelid;

	// 记录主体合同号
	private Long mainid;

	// 项目中记录金额
	private List<String> money;

	// 项目中记录金额类型
	private List<String> moneytype;

	// 删除关联自有产品号
	private Long delselfproduct;

	// 删除关联发票号
	private Long delInvoice;

	// 删除关联申购号收
	private Long delPur;

	// 判断是否是修改页面条件
	private Long isModify = 0L;

	// 客户联系人
	private List<YXLinkMan> linkmanlist;

	private Long clietId;

	// 合同金额类型
	private String mainmoneytype;

	// 合同金额
	private Double mainmoney;

	// 要删除的合同金额组成系统号
	private String delmainpartid;

	// 保存项目组成的索引号
	private int saveIndex;

	private Long contractype;

	private String ticketType;

	private List<String> itemResDept;// 部门的id list

	private List<Long> partInfoList;// 合同费用id list

	private List<String> itemResDeptP;// 负责人list

	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventInfoService;// 项目信息操作的service

	private List<ChangingContractMaininfoPart> mainInfoPartList;// 合同费用列表

	private List<ContractItemMaininfo> itemMainInfoList;// 项目信息列表
	
	private List<ChangingContractItemInfo> itemInfoList;

	private String departMentName;// 工程部门

	private String departMentPerson;// 项目负责人

	private Double itemMoney;// 项目含税费用

	private Long itemInfoId;// 项目费用id

	private List<Double> feeMoney;// 余额列表

	private Double mainInfoPartFeeMoney;//某一个合同下面的可操作余额
	
	private List chargeManList;//负责人列表
	
	private Long chargeManCode;//负责人id
	
	private List<List> eventallmoney;  //项目总金额
	
	private List<List> stageallmoney;  //阶段总金额
	
	private Double noTaxMoney;   //不含税金额
	
	
	private List<ContractMaininfoPart> eqinfo;
	
	private Double aidemoney=Double.valueOf(0);//金额B（根据基准来显示的金额）
	
	private Double maininfoMoney;//金额A（用户手填金额）

	
	private String changeExplain;
	
	private List<ChangingContractItemStagePlan> stagePlanlist;
	
	// 备份后的主体合同信息对象
	private ChangingContractMainInfo maininfo;
	
	private ChangeRealContractBillandRecePlan changeRealPlan;//变更后的开票收款计划
	
	private Long partId;//费用组成id
	


	// 默认调用方法 显示合同新增主信息页面
	public String doDefault() {
		// 调用寻找客户方法返回LIST客户
		clientlist = contractservice.findClient(UserUtils.getUser().getId());
		
		// 寻找客户项目类型
		eventclientlist = contractservice.findEventClient(UserUtils.getUser().getId());
		// 寻找开票客户
		allclientlist = contractservice.findAllClient(UserUtils.getUser().getId());
		// 寻找客户项目类型
		customeventypelist = typeManageService.getYXTypeManage(1007L);
		// 寻找工程责任部门
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);

		contractnature = typeManageService.getYXTypeManage(1019L);
		
		contracttype = typeManageService.getYXTypeManage(1020L);

		copecklist = typeManageService.getYXTypeManage(1015L);
		
		noTaxMoney = contractservice1.accountnoTaxMoney(mainid);

		tickettype = typeManageService.getYXTypeManage(1004L);

		itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
		
		contracttype=typeManageService.getYXTypeManage(1020L);
		
	    logger.info(mainid);
	    
		
		if(mainMoneyList==null){
			mainMoneyList=new ArrayList();
		}
		itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
	    
	    ChangingContractMainInfo ca=contractservice.loadContractMainInfo(mainid);
	    
	    if(ca!=null){
	    	maininfo=ca;
	    }else{
	    	maininfo = contractservice.loadandcopyContractMainInfotoclose(mainid);
		}
		clientCustomer=contractservice.getYXClientCode(maininfo.getConCustomer());
		itemCustomer=contractservice.getYXClientCode(maininfo.getItemCustomer());
		billCustomer=contractservice.getYXClientCode(maininfo.getBillCustomer());
		YXTypeManage manage=typeManageService.getYXTypeManage(1007L,maininfo.getCustomereventtype());
		if(manage!=null){
		customereventtype= manage.getTypeName();
		}
		YXLinkMan man=contractservice.getLinkManNameById(maininfo.getLinkManId());
		if(man!=null){
			linkMan=man.getName();
		}
		ContractChangeExplain change=contractservice.getChangeExplainByMainId(mainid);
		if(change!=null){
			changeExplain=change.getChangeexplain();
		}
		//需要查找联系人信息并添加至加拉列表框

		if(maininfo.getStandard().equals("1")){
			maininfoMoney = maininfo.getConTaxTamount();
			aidemoney = maininfo.getConNoTaxTamount();
		}
		if(maininfo.getStandard().equals("2")){
			maininfoMoney = maininfo.getConNoTaxTamount();
			aidemoney = maininfo.getConTaxTamount();
		}
		
		linkmanlist=contractservice.findlinkMan(maininfo.getConCustomer());
		
		mainMoneyList = contractservice.findMainMoneyList(mainid);
		
		departNum = contractservice.findEventByMainInfoId(mainid).size();
		stageNum = contractservice.findItemStageByMainInfoId(mainid).size();

		clietId=maininfo.getConCustomer();
		
		return "showmain";
	}
	


	// 保存合同主体信息并根据tag跳转页面
	public String saveMainInfo() throws Exception {
		logger.info("保存合同信息");
		// 测试隐去保存合同主体信息并且返回保存后的对象
		// 保存时需设置合同状态信息
		
		maininfo.setConMainInfoSid(mainid);
		ChangingContractMainInfo dbMainInfo = (ChangingContractMainInfo) commonService.load(ChangingContractMainInfo.class, mainid);
		Double taxConMoney = 0.00 ;
		Double noTaxConMoney = 0.00 ;
		if(dbMainInfo.getStandard().equals("1")){
			taxConMoney = maininfoMoney;
		}else{
			noTaxConMoney = maininfoMoney;
		}
		maininfo.setConNoTaxTamount(noTaxConMoney);
		maininfo.setConTaxTamount(taxConMoney);
		maininfo = contractservice.saveContractInfo(maininfo);
		logger.info("保存完毕，跳转至其他页面");
		mainid = maininfo.getConMainInfoSid();		
		logger.info(changeExplain);
        if(mainMoneyList!=null){
        	contractservice.saveContractMaininfoPart(mainMoneyList,mainid);		
        }    
 		return toPage(mainid);

	}
	
	public String applySubmit()throws Exception{
		logger.info("保存合同信息");
		// 测试隐去保存合同主体信息并且返回保存后的对象
		// 保存时需设置合同状态信息
		int result = finalToCloseService.isSureSubmit(mainid);
		if(result==0){
			contractservice.applySubmit(mainid,"1");
			return "applySubmitSuccess";
		}else{
			ActionContext.getContext().put("isSure", result);
		}
		logger.info("保存完毕，跳转至其他页面");        
		return toPage(mainid);
	}
	public String applySubmitByMainInfo()throws Exception{
		logger.info("合同主体信息页面提交");
		// 测试隐去保存合同主体信息并且返回保存后的对象
		// 保存时需设置合同状态信息
		maininfo.setConMainInfoSid(mainid);
		
		logger.info("保存完毕，跳转至其他页面");
		ChangingContractMainInfo dbMainInfo = (ChangingContractMainInfo) commonService.load(ChangingContractMainInfo.class, mainid);
		Double taxConMoney = 0.00 ;
		Double noTaxConMoney = 0.00 ;
		if(dbMainInfo.getStandard().equals("1")){
			taxConMoney = maininfoMoney;
		}else{
			noTaxConMoney = maininfoMoney;
		}
		maininfo.setConNoTaxTamount(noTaxConMoney);
		maininfo.setConTaxTamount(taxConMoney);
		maininfo = contractservice.saveContractInfo(maininfo);
		if(mainMoneyList!=null){
	        	contractservice.saveContractMaininfoPart(mainMoneyList,mainid);		
	     }    
		tag = 1;
		return applySubmit();
	}
	public String applySubmitByPlan()throws Exception{
		logger.info("开票收款计划页面提交");
		
		if (planlist != null) {
			System.out.println(planlist);
			contractservice.savePlanInfo(planlist,mainid);
		}
		tag =4;
		return  applySubmit();
	}
	public String applySubmitByStagePlan()throws Exception{
		logger.info("合同阶段页面提交");
	
		finalToCloseService.saveStagePlan(stagePlanlist);
		tag = 2;
		return applySubmit();
	}
	public String applySubmitByItemInfo()throws Exception{
		logger.info("合同项目信息页面提交");
		finalToCloseService.saveItemMainInfo(itemInfoList);
		tag = 3;
		return applySubmit();
	}

	public String saveEventInfo() throws Exception {
		// 点击添加或删除时需进行的操作
		logger.info("保存项目对象");
		finalToCloseService.saveItemMainInfo(itemInfoList);
		return toPage(mainid);
	}

	// 保存项目阶段信息对象
	public String saveStageInfo() throws Exception {
		// 隐藏保存语句
	/*	contractservice.saveContractStage(stagelist);*/
		/*
		 * System.out.println(stagelist.get(0).getContractMainSid());
		 * System.out.println(stagelist.get(0).toString());
		 * System.out.println("****************");
		 * System.out.println(stagelist.get(1).toString());
		 */
		
		finalToCloseService.saveStagePlan(stagePlanlist);
		return toPage(mainid);
	}

	// 保存开票收款计划
	public String savePlanInfo() throws Exception {
		if (planlist != null) {
			System.out.println(planlist);
			contractservice.savePlanInfo(planlist,mainid);
		}
		return toPage(mainid);
	}
	public String addChangeRealPlan()throws Exception{
		finalToCloseService.addChangeRealPlan(mainid, changeRealPlan,partId);		
		return toPage(mainid);
	}

	// 解除关联发票
	public String removeConInvoice() throws Exception {
		/*if(delInvoice!=null){
		  contractservice.removeConInvoice(delInvoice);	
		}*/
		return toPage(mainid);
	}

	// 解除关联申购
	public String removeConPurchase() throws Exception {
		/*if(delPur!=null){
		 contractservice.removeConPurchase(delPur);	
		}*/
		 return toPage(mainid);
	}

	// 保存自由产品信息
	public String selfProduct() throws Exception {
		return toPage(mainid);
	}

	// 合同其他信息
	public String saveOtherInfo() throws Exception {
		return toPage(mainid);
	}

	private String toPage(Long mainid) {
		if (tag == 1) {
		     maininfo=contractservice.loadContractMainInfo(mainid);
			// 调用寻找客户方法返回LIST客户
			clientlist = contractservice.findClient(UserUtils.getUser().getId());
			// 寻找客户项目类型
			eventclientlist = contractservice.findEventClient(UserUtils.getUser().getId());
			// 寻找开票客户
			allclientlist = contractservice.findAllClient(UserUtils.getUser().getId());
			// 寻找客户项目类型
			customeventypelist = typeManageService.getYXTypeManage(1007L);
			// 寻找工程责任部门
			dutydepartmentlist = typeManageService.getYXTypeManage(1018L);

			contractnature = typeManageService.getYXTypeManage(1019L);
			
			contracttype = typeManageService.getYXTypeManage(1020L);

			copecklist = typeManageService.getYXTypeManage(1015L);
			
			noTaxMoney = contractservice1.accountnoTaxMoney(mainid);
			

			tickettype = typeManageService.getYXTypeManage(1004L);

			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			
			contracttype=typeManageService.getYXTypeManage(1020L);
			
		    logger.info(mainid);
		    
			clientCustomer=contractservice.getYXClientCode(maininfo.getConCustomer());
			itemCustomer=contractservice.getYXClientCode(maininfo.getItemCustomer());
			billCustomer=contractservice.getYXClientCode(maininfo.getBillCustomer());
			YXTypeManage manage=typeManageService.getYXTypeManage(1007L,maininfo.getCustomereventtype());
			if(manage!=null){
			customereventtype= manage.getTypeName();
			}
			YXLinkMan man=contractservice.getLinkManNameById(maininfo.getLinkManId());
			if(man!=null){
				linkMan=man.getName();
			}
			ContractChangeExplain change=contractservice.getChangeExplainByMainId(mainid);
			if(change!=null){
				changeExplain=change.getChangeexplain();
			}	
			if(mainMoneyList==null){
				mainMoneyList=new ArrayList();
			}
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			//需要查找联系人信息并添加至加拉列表框

			if(maininfo.getStandard().equals("1")){
				maininfoMoney = maininfo.getConTaxTamount();
				aidemoney = maininfo.getConNoTaxTamount();
			}
			if(maininfo.getStandard().equals("2")){
				maininfoMoney = maininfo.getConNoTaxTamount();
				aidemoney = maininfo.getConTaxTamount();
			}
			
			linkmanlist=contractservice.findlinkMan(maininfo.getConCustomer());
			
			mainMoneyList = contractservice.findMainMoneyList(mainid);
			
			departNum = contractservice.findEventByMainInfoId(mainid).size();
			stageNum = contractservice.findItemStageByMainInfoId(mainid).size();
			ContractChangeExplain cce = contractservice.getChangeExplainByMainId(mainid,UserUtils.getUser().getId());
			if(cce!=null){
				changeExplain = cce.getChangeexplain();
			}
			clietId=maininfo.getConCustomer();
			return "showmain";
		}
		if(tag == 2){
			maininfo = (ChangingContractMainInfo) commonService.load(ChangingContractMainInfo.class,mainid);  
			stagePlanlist = finalToCloseService.findItemStagePlanByMainInfoId(mainid);
			mainMoneyWithPlanAmountList = finalToCloseService.findMainMoneyWithPlanAmountList(mainid);
			return "showStage";
		}
		if(tag == 3){
			maininfo = (ChangingContractMainInfo) commonService.load(ChangingContractMainInfo.class,mainid);
			contractype = Long.valueOf(maininfo.getContractType());
			mainInfoPartList=finalToCloseService.getMainInfoPartByConId(mainid);
			return "showEvent";
		}
		if (tag == 4) {
			maininfo = (ChangingContractMainInfo) commonService.load(ChangingContractMainInfo.class,mainid);
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳转计划信息页面");
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			tickettype = typeManageService.getYXTypeManage(1004L);
			planlist = contractservice.findchangePlanlist(mainid);
			//合同费用组成列表
			mainMoneyList=contractservice.findMainMoneyList(mainid);
			//同一费用组成下面的阶段列表
			
			//同一费用组成下面的项目列表
			changeRealPlan = null;
			return "showPlan";
		}
		return "success";
	}
	public String getStageByConAndPart()throws Exception{
		
		return null;
	}
	public String getItemByConAndPart()throws Exception{
		
		return null;
	}
	
	
	public String toPage()
	{
		return toPage(mainid);
	}
	public double getTaxAmount(BigDecimal money,String type){
		if(money!=null){
			ContractMainInfo m=(ContractMainInfo)commonService.load(ContractMainInfo.class, mainid);	
			if(m.getStandard().equals("2")){
				return TaxChange.TaxToNoTaxBigDecimal(money, type).doubleValue();
			}else{
				return money.doubleValue();
			}
			}else{ 
				
				return 0.0;
			}
	}

	// 刷新合同自有产品页面
	public String refreshSelfProductPage() throws Exception {
		logger.info("刷新合同自有产品页面");
		ownproductlist = (List<ContractOwnProduce>) contractservice.findselfProductByMainid(mainid);
		return "showSelfProduct";
	}
	
	// 刷新合同申购页面
	public String refreshPurPage() throws Exception {
		logger.info("刷新合同申购页面");
		purchaselist =  contractservice.findPurchaseByMainid(mainid);
		return "showPur";
	}
	
	//刷新合同开票页面
	public String refreshInvoicePage() throws Exception {
		logger.info("刷新合同开票页面");
		invoicelist = contractservice.findInviceByMainid(mainid);
		return "showInvoice";
	}



	public ISystemService getSystemservice() {
		return systemservice;
	}



	public void setSystemservice(ISystemService systemservice) {
		this.systemservice = systemservice;
	}



	public IFormalContractModifyService getContractservice() {
		return contractservice;
	}



	public void setContractservice(IFormalContractModifyService contractservice) {
		this.contractservice = contractservice;
	}



	public IContractService getContractservice1() {
		return contractservice1;
	}



	public void setContractservice1(IContractService contractservice1) {
		this.contractservice1 = contractservice1;
	}



	public IFinalToCloseService getFinalToCloseService() {
		return finalToCloseService;
	}



	public void setFinalToCloseService(IFinalToCloseService finalToCloseService) {
		this.finalToCloseService = finalToCloseService;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}



	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}



	public ICommonService getCommonService() {
		return commonService;
	}



	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}



	public List<YXClientCode> getClientlist() {
		return clientlist;
	}



	public void setClientlist(List<YXClientCode> clientlist) {
		this.clientlist = clientlist;
	}



	public String getClientCustomer() {
		return clientCustomer;
	}



	public void setClientCustomer(String clientCustomer) {
		this.clientCustomer = clientCustomer;
	}



	public String getBillCustomer() {
		return billCustomer;
	}



	public void setBillCustomer(String billCustomer) {
		this.billCustomer = billCustomer;
	}



	public String getItemCustomer() {
		return itemCustomer;
	}



	public void setItemCustomer(String itemCustomer) {
		this.itemCustomer = itemCustomer;
	}



	public String getLinkMan() {
		return linkMan;
	}



	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}



	public String getCustomereventtype() {
		return customereventtype;
	}



	public void setCustomereventtype(String customereventtype) {
		this.customereventtype = customereventtype;
	}



	public List<YXClientCode> getEventclientlist() {
		return eventclientlist;
	}



	public void setEventclientlist(List<YXClientCode> eventclientlist) {
		this.eventclientlist = eventclientlist;
	}



	public List<YXClientCode> getAllclientlist() {
		return allclientlist;
	}



	public void setAllclientlist(List<YXClientCode> allclientlist) {
		this.allclientlist = allclientlist;
	}



	public List<YXTypeManage> getClienttype() {
		return clienttype;
	}



	public void setClienttype(List<YXTypeManage> clienttype) {
		this.clienttype = clienttype;
	}



	public List<YXTypeManage> getContractnature() {
		return contractnature;
	}



	public void setContractnature(List<YXTypeManage> contractnature) {
		this.contractnature = contractnature;
	}



	public List<YXTypeManage> getContracttype() {
		return contracttype;
	}



	public void setContracttype(List<YXTypeManage> contracttype) {
		this.contracttype = contracttype;
	}



	public List<YXTypeManage> getTickettype() {
		return tickettype;
	}



	public void setTickettype(List<YXTypeManage> tickettype) {
		this.tickettype = tickettype;
	}



	public List<YXTypeManage> getProjectPhaseList() {
		return projectPhaseList;
	}



	public void setProjectPhaseList(List<YXTypeManage> projectPhaseList) {
		this.projectPhaseList = projectPhaseList;
	}







	public List<ChangingContractMaininfoPart> getMainMoneyList() {
		return mainMoneyList;
	}



	public void setMainMoneyList(List<ChangingContractMaininfoPart> mainMoneyList) {
		this.mainMoneyList = mainMoneyList;
	}



	public List<Object[]> getMainMoneyWithPlanAmountList() {
		return mainMoneyWithPlanAmountList;
	}



	public void setMainMoneyWithPlanAmountList(
			List<Object[]> mainMoneyWithPlanAmountList) {
		this.mainMoneyWithPlanAmountList = mainMoneyWithPlanAmountList;
	}



	public List<ChangeRealContractBillandRecePlan> getPlanlist() {
		return planlist;
	}



	public void setPlanlist(List<ChangeRealContractBillandRecePlan> planlist) {
		this.planlist = planlist;
	}



	public List<ApplyMessage> getPurchaselist() {
		return purchaselist;
	}



	public void setPurchaselist(List<ApplyMessage> purchaselist) {
		this.purchaselist = purchaselist;
	}



	public List<ApplyBill> getInvoicelist() {
		return invoicelist;
	}



	public void setInvoicelist(List<ApplyBill> invoicelist) {
		this.invoicelist = invoicelist;
	}



	public List<ContractOwnProduce> getOwnproductlist() {
		return ownproductlist;
	}



	public void setOwnproductlist(List<ContractOwnProduce> ownproductlist) {
		this.ownproductlist = ownproductlist;
	}



	public ContractItemMaininfo getIteminfo() {
		return iteminfo;
	}



	public void setIteminfo(ContractItemMaininfo iteminfo) {
		this.iteminfo = iteminfo;
	}



	public ContractItemStage getItemstage() {
		return itemstage;
	}



	public void setItemstage(ContractItemStage itemstage) {
		this.itemstage = itemstage;
	}



	public ContractOtherInfo getOtherinfo() {
		return otherinfo;
	}



	public void setOtherinfo(ContractOtherInfo otherinfo) {
		this.otherinfo = otherinfo;
	}



	public Long getSplititemNum() {
		return splititemNum;
	}



	public void setSplititemNum(Long splititemNum) {
		this.splititemNum = splititemNum;
	}



	public Long getSplitstageNum() {
		return splitstageNum;
	}



	public void setSplitstageNum(Long splitstageNum) {
		this.splitstageNum = splitstageNum;
	}



	public int getTag() {
		return tag;
	}



	public void setTag(int tag) {
		this.tag = tag;
	}



	public int getDepartNum() {
		return departNum;
	}



	public void setDepartNum(int departNum) {
		this.departNum = departNum;
	}



	public int getStageNum() {
		return stageNum;
	}



	public void setStageNum(int stageNum) {
		this.stageNum = stageNum;
	}



	public List<YXTypeManage> getCustomeventypelist() {
		return customeventypelist;
	}



	public void setCustomeventypelist(List<YXTypeManage> customeventypelist) {
		this.customeventypelist = customeventypelist;
	}



	public List<YXTypeManage> getDutydepartmentlist() {
		return dutydepartmentlist;
	}



	public void setDutydepartmentlist(List<YXTypeManage> dutydepartmentlist) {
		this.dutydepartmentlist = dutydepartmentlist;
	}



	public List<YXTypeManage> getItemdesigntypelist() {
		return itemdesigntypelist;
	}



	public void setItemdesigntypelist(List<YXTypeManage> itemdesigntypelist) {
		this.itemdesigntypelist = itemdesigntypelist;
	}



	public List<YXTypeManage> getCopecklist() {
		return copecklist;
	}



	public void setCopecklist(List<YXTypeManage> copecklist) {
		this.copecklist = copecklist;
	}



	public ContractItemStagePlan getStagePlan() {
		return stagePlan;
	}



	public void setStagePlan(ContractItemStagePlan stagePlan) {
		this.stagePlan = stagePlan;
	}



	public String getEventaddid() {
		return eventaddid;
	}



	public void setEventaddid(String eventaddid) {
		this.eventaddid = eventaddid;
	}



	public String getEventdelid() {
		return eventdelid;
	}



	public void setEventdelid(String eventdelid) {
		this.eventdelid = eventdelid;
	}



	public Long getMainid() {
		return mainid;
	}



	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}



	public List<String> getMoney() {
		return money;
	}



	public void setMoney(List<String> money) {
		this.money = money;
	}



	public List<String> getMoneytype() {
		return moneytype;
	}



	public void setMoneytype(List<String> moneytype) {
		this.moneytype = moneytype;
	}



	public Long getDelselfproduct() {
		return delselfproduct;
	}



	public void setDelselfproduct(Long delselfproduct) {
		this.delselfproduct = delselfproduct;
	}



	public Long getDelInvoice() {
		return delInvoice;
	}



	public void setDelInvoice(Long delInvoice) {
		this.delInvoice = delInvoice;
	}



	public Long getDelPur() {
		return delPur;
	}



	public void setDelPur(Long delPur) {
		this.delPur = delPur;
	}



	public Long getIsModify() {
		return isModify;
	}



	public void setIsModify(Long isModify) {
		this.isModify = isModify;
	}



	public List<YXLinkMan> getLinkmanlist() {
		return linkmanlist;
	}



	public void setLinkmanlist(List<YXLinkMan> linkmanlist) {
		this.linkmanlist = linkmanlist;
	}



	public Long getClietId() {
		return clietId;
	}



	public void setClietId(Long clietId) {
		this.clietId = clietId;
	}



	public String getMainmoneytype() {
		return mainmoneytype;
	}



	public void setMainmoneytype(String mainmoneytype) {
		this.mainmoneytype = mainmoneytype;
	}



	public Double getMainmoney() {
		return mainmoney;
	}



	public void setMainmoney(Double mainmoney) {
		this.mainmoney = mainmoney;
	}



	public String getDelmainpartid() {
		return delmainpartid;
	}



	public void setDelmainpartid(String delmainpartid) {
		this.delmainpartid = delmainpartid;
	}



	public int getSaveIndex() {
		return saveIndex;
	}



	public void setSaveIndex(int saveIndex) {
		this.saveIndex = saveIndex;
	}



	public Long getContractype() {
		return contractype;
	}



	public void setContractype(Long contractype) {
		this.contractype = contractype;
	}



	public String getTicketType() {
		return ticketType;
	}



	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}



	public List<String> getItemResDept() {
		return itemResDept;
	}



	public void setItemResDept(List<String> itemResDept) {
		this.itemResDept = itemResDept;
	}



	public List<Long> getPartInfoList() {
		return partInfoList;
	}



	public void setPartInfoList(List<Long> partInfoList) {
		this.partInfoList = partInfoList;
	}



	public List<String> getItemResDeptP() {
		return itemResDeptP;
	}



	public void setItemResDeptP(List<String> itemResDeptP) {
		this.itemResDeptP = itemResDeptP;
	}



	public IEventInfoService getEventInfoService() {
		return eventInfoService;
	}



	public void setEventInfoService(IEventInfoService eventInfoService) {
		this.eventInfoService = eventInfoService;
	}



	public List<ChangingContractMaininfoPart> getMainInfoPartList() {
		return mainInfoPartList;
	}



	public void setMainInfoPartList(
			List<ChangingContractMaininfoPart> mainInfoPartList) {
		this.mainInfoPartList = mainInfoPartList;
	}



	public List<ContractItemMaininfo> getItemMainInfoList() {
		return itemMainInfoList;
	}



	public void setItemMainInfoList(List<ContractItemMaininfo> itemMainInfoList) {
		this.itemMainInfoList = itemMainInfoList;
	}



	public List<ChangingContractItemInfo> getItemInfoList() {
		return itemInfoList;
	}



	public void setItemInfoList(List<ChangingContractItemInfo> itemInfoList) {
		this.itemInfoList = itemInfoList;
	}



	public String getDepartMentName() {
		return departMentName;
	}



	public void setDepartMentName(String departMentName) {
		this.departMentName = departMentName;
	}



	public String getDepartMentPerson() {
		return departMentPerson;
	}



	public void setDepartMentPerson(String departMentPerson) {
		this.departMentPerson = departMentPerson;
	}



	public Double getItemMoney() {
		return itemMoney;
	}



	public void setItemMoney(Double itemMoney) {
		this.itemMoney = itemMoney;
	}



	public Long getItemInfoId() {
		return itemInfoId;
	}



	public void setItemInfoId(Long itemInfoId) {
		this.itemInfoId = itemInfoId;
	}



	public List<Double> getFeeMoney() {
		return feeMoney;
	}



	public void setFeeMoney(List<Double> feeMoney) {
		this.feeMoney = feeMoney;
	}



	public Double getMainInfoPartFeeMoney() {
		return mainInfoPartFeeMoney;
	}



	public void setMainInfoPartFeeMoney(Double mainInfoPartFeeMoney) {
		this.mainInfoPartFeeMoney = mainInfoPartFeeMoney;
	}



	public List getChargeManList() {
		return chargeManList;
	}



	public void setChargeManList(List chargeManList) {
		this.chargeManList = chargeManList;
	}



	public Long getChargeManCode() {
		return chargeManCode;
	}



	public void setChargeManCode(Long chargeManCode) {
		this.chargeManCode = chargeManCode;
	}



	public List<List> getEventallmoney() {
		return eventallmoney;
	}



	public void setEventallmoney(List<List> eventallmoney) {
		this.eventallmoney = eventallmoney;
	}



	public List<List> getStageallmoney() {
		return stageallmoney;
	}



	public void setStageallmoney(List<List> stageallmoney) {
		this.stageallmoney = stageallmoney;
	}



	public Double getNoTaxMoney() {
		return noTaxMoney;
	}



	public void setNoTaxMoney(Double noTaxMoney) {
		this.noTaxMoney = noTaxMoney;
	}



	public List<ContractMaininfoPart> getEqinfo() {
		return eqinfo;
	}



	public void setEqinfo(List<ContractMaininfoPart> eqinfo) {
		this.eqinfo = eqinfo;
	}



	public Double getAidemoney() {
		return aidemoney;
	}



	public void setAidemoney(Double aidemoney) {
		this.aidemoney = aidemoney;
	}



	public Double getMaininfoMoney() {
		return maininfoMoney;
	}



	public void setMaininfoMoney(Double maininfoMoney) {
		this.maininfoMoney = maininfoMoney;
	}



	public String getChangeExplain() {
		return changeExplain;
	}



	public void setChangeExplain(String changeExplain) {
		this.changeExplain = changeExplain;
	}



	public ChangingContractMainInfo getMaininfo() {
		return maininfo;
	}



	public void setMaininfo(ChangingContractMainInfo maininfo) {
		this.maininfo = maininfo;
	}



	public List<ChangingContractItemStagePlan> getStagePlanlist() {
		return stagePlanlist;
	}



	public void setStagePlanlist(List<ChangingContractItemStagePlan> stagePlanlist) {
		this.stagePlanlist = stagePlanlist;
	}



	public ChangeRealContractBillandRecePlan getChangeRealPlan() {
		return changeRealPlan;
	}



	public void setChangeRealPlan(ChangeRealContractBillandRecePlan changeRealPlan) {
		this.changeRealPlan = changeRealPlan;
	}



	public Long getPartId() {
		return partId;
	}



	public void setPartId(Long partId) {
		this.partId = partId;
	}

	

}

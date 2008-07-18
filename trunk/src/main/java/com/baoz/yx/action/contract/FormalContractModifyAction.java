package com.baoz.yx.action.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IFormalContractModifyService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 合同新增页面/合同新增操作
 * 
 * 
 * 
 */
@Results( {
		@Result(name = "showmain", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractMainInfo.jsp"),
		@Result(name = "showevent", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractEventInfo.jsp"),
		@Result(name = "showStage", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractStage.jsp"),
		@Result(name = "showPlan", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractPlanInfo.jsp"),
		@Result(name = "showInvoice", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractConInvoice.jsp"),
		@Result(name = "showPur", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractConPurchase.jsp"),
		@Result(name = "showSelfProduct", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractSelfProduct.jsp"),
		@Result(name = "showOtherInfo", value = "/WEB-INF/jsp/contract/modifyformalContract/newContractOtherInfo.jsp"),
		@Result(name = "haschange", value = "/WEB-INF/jsp/contract/modifyformalContract/haschangeinfo.jsp"),
		@Result(name = "customersContract", type =  ServletRedirectResult.class, value = "/contract/contractCustoms.action?clietId=${clietId}")
})
public class FormalContractModifyAction extends DispatchAction {
	
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemservice;

	@Autowired
	@Qualifier("formalContractModifyService")
	private IFormalContractModifyService contractservice;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	

	// 客户列表
	private List<YXClientCode> clientlist;

	// 项目客户列表
	private List<YXClientCode> eventclientlist;

	// 开票客户列表
	private List<YXClientCode> allclientlist;

	// 客户类型列表
	private List<YXTypeManage> clienttype;

	// 合同性质
	private List<YXTypeManage> contractnature;
	
	//合同类型
	private List<YXTypeManage> contracttype;

	// 发票类型
	private List<YXTypeManage> tickettype;

	// 项目信息列表（维护项Iteminfo对象）
	private List<ContractItemMaininfo> iteminfolist;

	// 阶段信息列表
	private List<ContractItemStage> stagelist;

	// 合同开票收款计划对象
	private List<RealContractBillandRecePlan> planlist;
	
	//合同关联的申购采购
    private List<ApplyMessage> purchaselist;
    
    //合同关联的开票申请
    private List<ApplyBill> invoicelist;

	// 合同关联的自有产品信息
	private List<ContractOwnProduce> ownproductlist;

	// 备份后的主体合同信息对象
	private ChangingContractMainInfo maininfo;

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

	//删除关联自有产品号
	private Long delselfproduct;
	
	//删除关联发票号
	private Long delInvoice;

	//删除关联申购号收
	private Long delPur;
		
	//合同金额组成列表
	private List<ContractMaininfoPart> mainMoneyList;
	
	//合同金额类型
	private String mainmoneytype;
	
	//合同金额
	private Double mainmoney;
	
	//客户联系人
	private List<YXLinkMan> linkmanlist;
	
	private Long clietId;
	
	private String changeExplain;
	


	// 默认调用方法 显示合同新增主信息页面
	public String doDefault() throws Exception {
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
		
		contracttype=typeManageService.getYXTypeManage(1020L);
		
	    logger.info(mainid);
	    
	    mainMoneyList=contractservice.findMainMoneyList(mainid);
		if(mainMoneyList==null){
			mainMoneyList=new ArrayList();
		}
		itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
	    
	    ChangingContractMainInfo ca=contractservice.loadContractMainInfo(mainid);
	    
	    if(ca!=null){
	    	return "haschange";
	    }
	    

		
		maininfo = contractservice.loadandcopyContractMainInfo(mainid);
		//需要查找联系人信息并添加至加拉列表框
		
		linkmanlist=contractservice.findlinkMan(maininfo.getConCustomer());
		 
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
		maininfo = contractservice.saveContractMainInfo(maininfo, departNum,
				stageNum);
		logger.info("保存完毕，跳转至其他页面");
		mainid = maininfo.getConMainInfoSid();
		
		logger.info(changeExplain);
        contractservice.saveContractChangeExplain(changeExplain,mainid,UserUtils.getUser().getId());

		mainMoneyList = contractservice.findMainMoneyList(mainid);
		
		
		
		if(mainMoneyList==null){
			mainMoneyList=new ArrayList();
		}
		
		mainmoney=null;
		mainmoneytype=null;
		return toPage(mainid);

	}

	public String saveEventInfo() throws Exception {
		// 点击添加或删除时需进行的操作
		logger.info("保存项目对象");

		/*for (int i = 0; i < iteminfolist.size(); i++) {
			iteminfolist.get(i).setContractItemInfolist(null);
		}
		contractservice.saveContractEvent(iteminfolist);
		logger.info("****end****");

		if (!eventaddid.equals("")) {
			// 增加金额，eventaddid为金额信息表中外间的ID号
			for (int i = 0; i < money.size(); i++) {
				if (!money.get(i).equals("")) {
					contractservice.saveEventInfo(Long.valueOf(eventaddid),
							moneytype.get(i), BigDecimal.valueOf(Long
									.valueOf(money.get(i))));
					logger.info("插入金额履历成功");
				}
			}

		}

		if (!eventdelid.equals("")) {
			// 删除金额，eventdelid为删除金额的主键ID号
			System.out.println(eventdelid);
			contractservice.removeEventInfo(Long.valueOf(eventdelid));

		}

		// 获取页面中的值，按金额为准，又金额的进行保存
		for (int i = 0; i < money.size(); i++) {
			if (!money.get(i).equals("")) {
				System.out.println(moneytype.get(i));
				System.out.println(money.get(i));
			}
		}
*/
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
		return toPage(mainid);
	}

	// 保存开票收款计划
	public String savePlanInfo() throws Exception {
		

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

	public String toPage(Long mainid) {
		if (tag == 1) {
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
			
			contracttype=typeManageService.getYXTypeManage(1020L);
			
			maininfo = contractservice.loadContractMainInfo(mainid);
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			//需要查找联系人信息并添加至加拉列表框
			if(maininfo.getConCustomer()!=null){
				linkmanlist=contractservice.findlinkMan(maininfo.getConCustomer());
			}
			if(linkmanlist==null){
				linkmanlist=new ArrayList();
			}
			 
			mainMoneyList=contractservice.findMainMoneyList(mainid);
			if(mainMoneyList==null){
				mainMoneyList=new ArrayList();
			}
			
			departNum = contractservice.findEventByMainInfoId(mainid).size();
			stageNum = contractservice.findItemStageByMainInfoId(mainid).size();
			return "showmain";
		}
		if (tag == 2) {
			logger.info("跳转项目信息页面");
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			dutydepartmentlist = typeManageService.getYXTypeManage(1018L);
			iteminfolist = contractservice.findEventByMainInfoId(mainid);
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			/*
			 * Long a=new Long(41);
			 * iteminfolist=contractservice.findEventByMainInfoId(a);
			 * System.out.println("项目&项目组成对象");
			 * System.out.println("*********完成**********");
			 */
			// System.out.println(iteminfolist.get(0).getContractItemInfolist().get(0).getConItemAmountWithTax());

			return "showevent";
		}
		if (tag == 3) {
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳转阶段信息页面");
			stagelist = contractservice.findItemStageByMainInfoId(mainid);
			return "showStage";
		}
		if (tag == 4) {
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳转计划信息页面");
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			tickettype = typeManageService.getYXTypeManage(1004L);
			planlist = contractservice.findPlanlist(mainid);
			return "showPlan";
		}
		if (tag == 5) {
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳转关联发票页面");
			invoicelist=contractservice.findInviceByMainid(mainid);
			return "showInvoice";
		}
		if (tag == 6) {
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳转关联申购页面");
			purchaselist=contractservice.findPurchaseByMainid(mainid);
			return "showPur";
		}
		if (tag == 7) {
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳自有产品页面");
			ownproductlist = contractservice.findselfProductByMainid(mainid);
			return "showSelfProduct";
		}
		if (tag == 8) {
			if(clietId==null){
				clietId=maininfo.getConCustomer();
			}
			logger.info("跳转其他信息页面");
			otherinfo = contractservice.findOtherInfo(mainid);
			return "showOtherInfo";
		}

		if(clietId==null){
		//建立关联
			contractservice.conCustom(UserUtils.getUser().getId(),maininfo.getConCustomer());
		}else{
			contractservice.conCustom(UserUtils.getUser().getId(), clietId);
		}
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

		linkmanlist=new ArrayList();
		
		contractnature = typeManageService.getYXTypeManage(1019L);
		
		contracttype=typeManageService.getYXTypeManage(1020L);
		setMaininfo(null);	
		System.out.println("主键制空");
		setMainid(null);
		System.out.println(mainid);
		ActionContext.getContext().put("isRelationSuccess", "true");
		return "showmain";
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
	
	
	// 以下为个对象的SET和GET方法
	public List<YXClientCode> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<YXClientCode> clientlist) {
		this.clientlist = clientlist;
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

	public ISystemService getSystemservice() {
		return systemservice;
	}

	public void setSystemservice(ISystemService systemservice) {
		this.systemservice = systemservice;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public List<YXTypeManage> getClienttype() {
		return clienttype;
	}

	public void setClienttype(List<YXTypeManage> clienttype) {
		this.clienttype = clienttype;
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

	public List<ContractItemMaininfo> getIteminfolist() {
		return iteminfolist;
	}

	public void setIteminfolist(List<ContractItemMaininfo> iteminfolist) {
		this.iteminfolist = iteminfolist;
	}

	public List<ContractItemStage> getStagelist() {
		return stagelist;
	}

	public void setStagelist(List<ContractItemStage> stagelist) {
		this.stagelist = stagelist;
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

	public List getMoney() {
		return money;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
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

	public void setMoney(List<String> money) {
		this.money = money;
	}

	public List<String> getMoneytype() {
		return moneytype;
	}

	public void setMoneytype(List<String> moneytype) {
		this.moneytype = moneytype;
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

	public List<YXTypeManage> getTickettype() {
		return tickettype;
	}

	public void setTickettype(List<YXTypeManage> tickettype) {
		this.tickettype = tickettype;
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

	public ContractOtherInfo getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(ContractOtherInfo otherinfo) {
		this.otherinfo = otherinfo;
	}

	public List<ContractOwnProduce> getOwnproductlist() {
		return ownproductlist;
	}

	public void setOwnproductlist(List<ContractOwnProduce> ownproductlist) {
		this.ownproductlist = ownproductlist;
	}

	public Long getDelselfproduct() {
		return delselfproduct;
	}

	public void setDelselfproduct(Long delselfproduct) {
		this.delselfproduct = delselfproduct;
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


	public List<YXLinkMan> getLinkmanlist() {
		return linkmanlist;
	}

	public void setLinkmanlist(List<YXLinkMan> linkmanlist) {
		this.linkmanlist = linkmanlist;
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

	public Long getClietId() {
		return clietId;
	}

	public void setClietId(Long clietId) {
		this.clietId = clietId;
	}



	public IFormalContractModifyService getContractservice() {
		return contractservice;
	}



	public void setContractservice(IFormalContractModifyService contractservice) {
		this.contractservice = contractservice;
	}



	public ChangingContractMainInfo getMaininfo() {
		return maininfo;
	}



	public void setMaininfo(ChangingContractMainInfo maininfo) {
		this.maininfo = maininfo;
	}



	public List<RealContractBillandRecePlan> getPlanlist() {
		return planlist;
	}



	public void setPlanlist(List<RealContractBillandRecePlan> planlist) {
		this.planlist = planlist;
	}
	
	public String getChangeExplain() {
		return changeExplain;
	}

	public void setChangeExplain(String changeExplain) {
		this.changeExplain = changeExplain;
	}



	public List<ContractMaininfoPart> getMainMoneyList() {
		return mainMoneyList;
	}



	public void setMainMoneyList(List<ContractMaininfoPart> mainMoneyList) {
		this.mainMoneyList = mainMoneyList;
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


}

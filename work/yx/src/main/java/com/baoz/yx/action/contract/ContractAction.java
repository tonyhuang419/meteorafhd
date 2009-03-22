package com.baoz.yx.action.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.MaterialManager;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 合同新增页面/合同新增操作
 * 
 * 
 * 
 */
@Results( {
	@Result(name = "showmain", value = "/WEB-INF/jsp/contract/newContractMainInfo.jsp"),
	@Result(name = "showevent", value = "/WEB-INF/jsp/contract/newContractEventInfo.jsp"),
	@Result(name = "showStage", value = "/WEB-INF/jsp/contract/newContractStage.jsp"),
	@Result(name = "showPlan", value = "/WEB-INF/jsp/contract/newContractPlanInfo.jsp"),
	@Result(name = "showInvoice", value = "/WEB-INF/jsp/contract/newContractConInvoice.jsp"),
	@Result(name = "showPur", value = "/WEB-INF/jsp/contract/newContractConPurchase.jsp"),
	@Result(name = "showSelfProduct", value = "/WEB-INF/jsp/contract/newContractSelfProduct.jsp"),
	@Result(name = "showOtherInfo", value = "/WEB-INF/jsp/contract/newContractOtherInfo.jsp"),
	@Result(name = "editStagePlan", value = "/WEB-INF/jsp/contract/modifyContractStagePlan.jsp"),
	@Result(name = "editStagePlanResult", value = "/WEB-INF/jsp/contract/modifyContractStagePlanResult.jsp"),
	@Result(name = "customersContract", type = ServletRedirectResult.class, value = "/contract/searchContractList.action"),
	@Result(name = "customersContractlist", type = ServletRedirectResult.class, value = "/contract/contractHome.action"),
	@Result(name = "popUpdateEventInfo", value = "/WEB-INF/jsp/contract/popUpdateEventInfo.jsp"),
	@Result(name = "updateEventInfoSuc", value = "/WEB-INF/jsp/contract/updateEventInfoSucces.jsp"),
	@Result(name = "toDetailContract", value = "/WEB-INF/jsp/contract/toDetailContract.jsp"),
	@Result(name = "popCheckedMaterialList", value = "/WEB-INF/jsp/contract/popCheckedMaterialList.jsp")
})
public class ContractAction extends DispatchAction {

	@Autowired
	@Qualifier("systemService")
	private ISystemService systemservice;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	private Boolean importFromFile;
	
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

	// 合同类型
	private List<YXTypeManage> contracttype;

	// 发票类型
	private List<YXTypeManage> tickettype;

	// 项目阶段
	private List<YXTypeManage> projectPhaseList;

	// 项目信息列表（维护项Iteminfo对象）
	private List<ContractItemMaininfo> iteminfolist;

	// 合同金额组成列表
	private List<ContractMaininfoPart> mainMoneyList;

	// 合同金额组成和阶段金额列表
	private List<Object[]> mainMoneyWithPlanAmountList;

	// 阶段信息列表
	// private List<ContractItemStage> stagelist;

	// 阶段计划信息列表
	private List<ContractItemStagePlan> stagePlanlist;

	// 合同开票收款计划对象
	private List<InitContractBillpro> planlist;

	// 合同关联的申购采购
	private List<ApplyMessage> purchaselist;

	// 合同关联的开票申请
	private List<ApplyBill> invoicelist;

	// 合同关联的自有产品信息
	private List<ContractOwnProduce> ownproductlist;

	// 主体合同信息对象
	private ContractMainInfo maininfo;

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

	private List<ContractMaininfoPart> mainInfoPartList;// 合同费用列表

	private List<ContractItemMaininfo> itemMainInfoList;// 项目信息列表

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
	
	private Double totalPartMoney;//费用组成总金额
	
	private List<MaterialManager> materialManagerList;//应交材料列表
	
	private String[] customerMaterial;//选择的已经材料
	
	private List<MaterialManager> checkedMaterialList;//显示选中的应交材料
	
	private Long otherInfoId;//合同其他信息id
	
	private Double allReveMoney;

	private Double remainReceAmount;//剩余合同收款金额
	
	private Long isFromNewPage;//是否从新建开始
	
	private String linkManName;
	

	public Long getOtherInfoId() {
		return otherInfoId;
	}

	public void setOtherInfoId(Long otherInfoId) {
		this.otherInfoId = otherInfoId;
	}

	public List<MaterialManager> getCheckedMaterialList() {
		return checkedMaterialList;
	}

	public void setCheckedMaterialList(List<MaterialManager> checkedMaterialList) {
		this.checkedMaterialList = checkedMaterialList;
	}

	// 默认调用方法 显示合同新增主信息页面
	public String doDefault() throws Exception {
		// 调用寻找客户方法返回LIST客户
		clientlist = contractservice.findClient(UserUtils.getUser().getId());
		// 寻找客户单位
		eventclientlist = contractservice.findEventClient(UserUtils.getUser()
				.getId());
		// 寻找开票客户
		allclientlist = contractservice.findAllClient(UserUtils.getUser()
				.getId());
		// 寻找客户项目类型
		customeventypelist = typeManageService.getYXTypeManage(1007L);
		// 寻找工程责任部门
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);
		// 寻找合同性质
		contractnature = typeManageService.getYXTypeManage(1019L);

		contracttype = typeManageService.getYXTypeManage(1020L);

		copecklist = typeManageService.getYXTypeManage(1015L);

		tickettype = typeManageService.getYXTypeManage(1004L);

		itemdesigntypelist = typeManageService.getYXTypeManage(1012L);

		linkmanlist = new ArrayList();

		mainMoneyList = new ArrayList();
		
		setIsFromNewPage(1L);

		return "showmain";
	}

	public String Modify() throws Exception {

		maininfo = contractservice.loadContractMainInfo(mainid);
		if(maininfo.getLinkManId()!=null){
			YXLinkMan linkman = (YXLinkMan)commonService.load(YXLinkMan.class,maininfo.getLinkManId());
			linkManName = linkman.getName();
		}
		linkManName="";
		setIsFromNewPage(0L);

		if(maininfo.getConState().equals(1L) || maininfo.getConState().equals(3L)){
			return "toDetailContract";
		}

		else{
			setImportFromFile(maininfo.getImportFromFile());
			// 调用寻找客户方法返回LIST客户
			clientlist = contractservice.findClient(UserUtils.getUser().getId());
			// 寻找客户项目类型
			eventclientlist = contractservice.findEventClient(UserUtils.getUser()
					.getId());
			// 寻找开票客户
			allclientlist = contractservice.findAllClient(UserUtils.getUser()
					.getId());
			// 寻找客户项目类型
			customeventypelist = typeManageService.getYXTypeManage(1007L);
			// 寻找工程责任部门
			dutydepartmentlist = typeManageService.getYXTypeManage(1018L);

			contractnature = typeManageService.getYXTypeManage(1019L);

			contracttype = typeManageService.getYXTypeManage(1020L);

			copecklist = typeManageService.getYXTypeManage(1015L);
			
			noTaxMoney = contractservice.accountnoTaxMoney(mainid);
			
			if(maininfo.getStandard().equals("1")){
				maininfoMoney = maininfo.getConTaxTamount();
				aidemoney = contractservice.accountnoTaxMoney(mainid);
			}
			if(maininfo.getStandard().equals("2")){
				maininfoMoney = maininfo.getConNoTaxTamount();
				aidemoney = maininfo.getConNoTaxTamount();
			}

			mainMoneyList = contractservice.findMainMoneyList(mainid);

			tickettype = typeManageService.getYXTypeManage(1004L);

			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);


			// 需要查找联系人信息并添加至加拉列表框
			linkmanlist = contractservice.findlinkMan(maininfo.getConCustomer());

			departNum = contractservice.findEventByMainInfoId(mainid).size();
			stageNum = contractservice.findItemStageByMainInfoId(mainid).size();

			clietId = maininfo.getConCustomer();
			return "showmain";
		}
	}

	// 保存合同主体信息并根据tag跳转页面
	public String saveMainInfo() throws Exception {
		logger.info("保存合同信息");
		// 测试隐去保存合同主体信息并且返回保存后的对象
		// 保存时需设置合同状态信息
		if(!"".equals(linkManName)){
			maininfo.setLinkManId(contractservice.getLinkManIdByName(linkManName,maininfo.getConCustomer()));
		}
		this.tempSaveMainInfo();
		return toPage(mainid,isFromNewPage);

	}
	/***
	 * 保存合同信息的方法，由于这个方法要在很多地方调用，因此写成了一个公共的！没有页面跳转
	 * @throws Exception
	 */
	private void  tempSaveMainInfo()throws Exception{
		maininfo.setConMainInfoSid(mainid);
	
		
		maininfo = contractservice.saveContractMainInfo(maininfo);
		mainid =maininfo.getConMainInfoSid();
		
		if (!mainmoneytype.equals("") && mainmoney != null) {
			contractservice.saveMainMoneyPart(mainmoneytype, mainmoney, mainid,
					ticketType);
		}

		if (!delmainpartid.equals("")) {
			contractservice.delContractMainPart(Long.valueOf(delmainpartid));
		}
		
		if(mainid!=null){
			if(maininfo.getStandard().equals("1")){
				maininfo.setConTaxTamount(maininfoMoney);
				maininfo.setConNoTaxTamount(contractservice.accountnoTaxMoney(mainid));  
			}
			if(maininfo.getStandard().equals("2")){
	            maininfo.setConNoTaxTamount(maininfoMoney);
				maininfo.setConTaxTamount(contractservice.accountTaxMoney(mainid));
			}
		}else{
			if(maininfo.getStandard().equals("1")){
				maininfo.setConTaxTamount(maininfoMoney);
			}
			if(maininfo.getStandard().equals("2")){
	            maininfo.setConNoTaxTamount(maininfoMoney);
			}
		}
		
		commonService.update(maininfo);
		
		logger.info("保存完毕，跳转至其他页面");
		mainid = maininfo.getConMainInfoSid();
		mainMoneyList = contractservice.findMainMoneyList(mainid);

		mainmoney = null;
		mainmoneytype = null;
		ticketType = null;
	}


	/**
	 * 添加项目信息和费用信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addEventInfo() throws Exception {
		
		if (money.get(saveIndex) != null && partInfoList.get(saveIndex) != null
				&& itemResDept.get(saveIndex) != null
				&& itemResDeptP.get(saveIndex) != null) {
			// 保存项目信息，合同id
			ContractItemMaininfo itemMainInfo = new ContractItemMaininfo();
			itemMainInfo.setContractMainInfo(mainid);
			// 项目负责部门
			itemMainInfo.setItemResDept(itemResDept.get(saveIndex));
			// 部门负责人
			itemMainInfo.setItemResDeptP(itemResDeptP.get(saveIndex));
			itemMainInfo.setConItemCostSure(0L);
			// 保存项目费用信息，项目id，合同费用id
			ContractItemInfo itemInfo = new ContractItemInfo();
			itemInfo.setMainInfoPartId(partInfoList.get(saveIndex));
			itemInfo.setConItemAmountWithTax(BigDecimalUtils.toBigDecial
					(StringUtils.replace(money.get(saveIndex), ",", "")));
			
//			itemInfo.setConItemAmountWithTax(BigDecimalUtils.toBigDecial(Double
//					.valueOf(StringUtils.replace(money.get(saveIndex), ",", ""))));
			logger.info("您输入的值分别是：");
			logger.info("输入的金额：" + money.get(saveIndex));
			logger.info("合同费用id是：" + partInfoList.get(saveIndex));
			logger.info("负责部门：" + itemResDept.get(saveIndex));
			logger.info("部门负责人：" + itemResDeptP.get(saveIndex));
			eventInfoService.saveEventInfo(itemMainInfo, itemInfo,"add");
		}
		return toPage(mainid,isFromNewPage);
	}

	/**
	 * 删除费用信息和项目信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteEventInfo() throws Exception {
		// 通过项目费用id删除费用信息，并且判断项目中是否还存在费用，如果不存在把项目也删掉
		if (eventdelid != null && !"".equals(eventdelid)) {
			this.contractservice.deleteEventInfo(Long.valueOf(eventdelid));
		}
		return toPage(mainid,isFromNewPage);
	}

	/**
	 * 修改信息的页面跳转
	 * 
	 * @return
	 * @throws Exception
	 */
	public String tempUpdateEventInfo() throws Exception {
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);
		tag = 2;
		// 通过费用id查询费用信息和项目信息然后显示出来
		ContractItemInfo itemInfo = this.eventInfoService
		.selectContractItemInfoById(itemInfoId);
		departMentName = typeManageService.getYXTypeManage(1018L,
				itemInfo.getItemMainInfo().getItemResDept()).getTypeName();
		departMentPerson = itemInfo.getItemMainInfo().getItemResDeptP();
		ContractMaininfoPart part=(ContractMaininfoPart)commonService.load(ContractMaininfoPart.class, itemInfo.getMainInfoPartId());
		totalPartMoney=part.getMoney();
		itemMoney = Double.valueOf(itemInfo.getConItemAmountWithTax()
				.toString());
		
		
		//通过部门code查找部门对应的负责人信息
		chargeManList=this.eventInfoService.selectDepartmentListByDepartCode(itemInfo.getItemMainInfo().getItemResDept());
		for(int k=0;k<chargeManList.size();k++)
		{
			YXChargeMan chargeMan=(YXChargeMan)chargeManList.get(k);
			if(chargeMan.getName().equals(departMentPerson))
			{
				chargeManCode=chargeMan.getId();
				break;
			}
		}
		//通过mainId查询某一个费用的可用余额
		Double tempValue=eventInfoService.getOneFeeMoney(itemInfoId);
		//修改的时候的可用余额是用合同费用-项目费用的和，然后在加上当前项目的费用
		mainInfoPartFeeMoney=tempValue+itemInfo.getConItemAmountWithTax().doubleValue();
		if(mainInfoPartFeeMoney<0)
		{
			mainInfoPartFeeMoney=-mainInfoPartFeeMoney;
		}

		return "popUpdateEventInfo";
	}

	public String updateEventInfo() throws Exception {
		logger.info("mainid的值是：" + mainid);
		ContractItemInfo itemInfo = (ContractItemInfo) commonService.load(ContractItemInfo.class, itemInfoId);
		ContractItemMaininfo mainInfo=(ContractItemMaininfo) commonService.load(ContractItemMaininfo.class, itemInfo.getContractItemMaininfo());
		mainInfo.setItemResDeptP(departMentPerson);
		itemInfo.setConItemInfoSid(itemInfoId);
		itemInfo.setItemMainInfo(mainInfo);
		itemInfo.setConItemAmountWithTax(BigDecimalUtils.toBigDecial(itemMoney));
		//删除项目
		if(!Boolean.TRUE.equals(mainInfo.getImportFromFile())){
			contractservice.deleteEventInfo(itemInfoId);
			//重新创建项目
			itemInfo.setConItemInfoSid(null);
			mainInfo.setConItemMinfoSid(null);
		}
		eventInfoService.saveEventInfo(mainInfo, itemInfo,"update");
		return "updateEventInfoSuc";
	}

	// 保存项目阶段信息对象
	public String saveStageInfo() throws Exception {
		contractservice.saveContractStagePlan(stagePlan);
		return toPage(mainid,isFromNewPage);
	}

	public String enterEditStagePlan() {
		stagePlan = (ContractItemStagePlan) commonService.load(
				ContractItemStagePlan.class, stagePlan.getId());
		ActionContext.getContext().put(
				"remainPlanAmount",
				stagePlan.getContractMaininfoPart().getMoney()
				- contractservice.getStagePlanAmountOfContractPart(stagePlan.getContractMaininfoPart().getId(), false));
		
		tickettype = contractservice.getTicketTypeByPartId(stagePlan.getContractMaininfoPart().getTicketType());
		remainReceAmount = contractservice.getRemainContractReceAmount(stagePlan.getContractMainSid());
		
		return "editStagePlan";
	}

	// 更新项目阶段信息对象
	public String updateStageInfo() throws Exception {
		contractservice.updateContractStagePlan(stagePlan);
		return "editStagePlanResult";
	}

	// 删除项目阶段信息对象
	public String deleteStageInfo() throws Exception {
		contractservice.deleteContractStagePlan(stagePlan);
		return toPage(mainid,isFromNewPage);
	}

	// 保存开票收款计划
	public String savePlanInfo() throws Exception {
		if (planlist != null) {
			System.out.println(planlist);
			contractservice.savePlanInfo(planlist);
		}
		return toPage(mainid,isFromNewPage);
	}

	// 解除关联发票
	public String removeConInvoice() throws Exception {
		if (delInvoice != null) {
			contractservice.removeConInvoice(delInvoice);
		}
		return toPage(mainid,isFromNewPage);
	}

	// 解除关联申购
	public String removeConPurchase() throws Exception {
		if (delPur != null) {
			contractservice.removeConPurchase(delPur);
		}
		return toPage(mainid,isFromNewPage);
	}

	// 保存自由产品信息
	public String selfProduct() throws Exception {
		if (ownproductlist != null) {
			for (int i = 0; i < ownproductlist.size(); i++) {
				ownproductlist.get(i).setConModInfo(false);
				System.out.println(ownproductlist.get(i).getConOwnProdSid());

			}
			contractservice.saveConSelfProductInfo(ownproductlist);

			if (delselfproduct != null) {
				contractservice.delConSelfProduct(delselfproduct);
			}
		}

		return toPage(mainid,isFromNewPage);
	}

	// 合同其他信息
	public String saveOtherInfo() throws Exception {
		logger.info("saveOther!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		otherinfo.setContractMainSid(mainid);
		contractservice.saveOtherInfo(otherinfo);
		return toPage(mainid,isFromNewPage);
	}

	public String tempSaveOtherInfo()throws Exception{
		if(this.getOtherInfoId()!=null){
			ContractOtherInfo otherInfo=(ContractOtherInfo)commonService.load(ContractOtherInfo.class, otherInfoId);
			customerMaterial=StringUtils.split(otherInfo.getFinallyLize(),",");
		}else{
			customerMaterial=contractservice.getContractMaterialSetByMainId(mainid);
			
		}
		materialManagerList=contractservice.getAllMaterialManager();
		return "popCheckedMaterialList";
	}
	// 不保存，单纯跳转
	public String toPage() {
		return toPage(mainid,isFromNewPage);
	}

	public String toPage(Long mainid,Long isFromNewPage) {
		if (tag == 1) {
			// 调用寻找客户方法返回LIST客户
			clientlist = contractservice
			.findClient(UserUtils.getUser().getId());
			// 寻找客户项目类型
			eventclientlist = contractservice.findEventClient(UserUtils
					.getUser().getId());
			// 寻找开票客户
			allclientlist = contractservice.findAllClient(UserUtils.getUser()
					.getId());
			// 寻找客户项目类型
			customeventypelist = typeManageService.getYXTypeManage(1007L);
			// 寻找工程责任部门
			dutydepartmentlist = typeManageService.getYXTypeManage(1018L);

			contractnature = typeManageService.getYXTypeManage(1019L);

			contracttype = typeManageService.getYXTypeManage(1020L);

			tickettype = typeManageService.getYXTypeManage(1004L);

			copecklist = typeManageService.getYXTypeManage(1015L);

			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			
			noTaxMoney = contractservice.accountnoTaxMoney(mainid);
			maininfo = contractservice.loadContractMainInfo(mainid);
			
			if(maininfo.getStandard().equals("1")){
				maininfoMoney = maininfo.getConTaxTamount();
				aidemoney = contractservice.accountnoTaxMoney(mainid);
			}
			if(maininfo.getStandard().equals("2")){
				maininfoMoney = maininfo.getConNoTaxTamount();
				aidemoney = maininfo.getConNoTaxTamount();
			}

			if (clietId == null) {
				clietId = maininfo.getConCustomer();
			}
			// 需要查找联系人信息并添加至加拉列表框
			if (maininfo.getConCustomer() != null) {
				linkmanlist = contractservice.findlinkMan(maininfo
						.getConCustomer());
			}
			if (linkmanlist != null) {
				for(YXLinkMan man:linkmanlist){
					if(maininfo.getLinkManId()!=null){
						if(man.getId()==maininfo.getConCustomer()){
							linkManName = man.getName();
						}
					}
				}
			}else{
				linkmanlist = new ArrayList();
			}
			mainMoneyList = contractservice.findMainMoneyList(mainid);
			departNum = contractservice.findEventByMainInfoId(mainid).size();
			stageNum = contractservice.findItemStageByMainInfoId(mainid).size();
			return "showmain";
		}
		if (tag == 2) {
			logger.info("跳转项目信息页面");
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			dutydepartmentlist = typeManageService.getYXTypeManage(1018L);
			iteminfolist = contractservice.findEventByMainInfoId(mainid);
			if (clietId == null) {
				clietId = maininfo.getConCustomer();
			}

			// 查询合同类型（工程类还是集成类）
			contractype = Long
			.valueOf(contractservice.findContractType(mainid));

			// 通过合同id查找合同费用
			this.mainInfoPartList = this.eventInfoService
			.getMainInfoPartByConId(mainid);
			// 通过合同id查找同意合同费用下的项目费用的总和
			List list = this.eventInfoService.getFeeMoneyByPartId(mainid);
			feeMoney = new ArrayList<Double>();

			for (int k = 0; k < mainInfoPartList.size(); k++) {

				ContractMaininfoPart mainInfor = mainInfoPartList.get(k);
				Double sum = mainInfor.getMoney();
				Double opSum = 0.0;
				if(list!=null&&list.size()>0)
				{
					for(int j=0;j<list.size();j++){
						Object[] opFee=(Object[])list.get(j);
						BigDecimal fee=(BigDecimal)opFee[0];
						Long mid=(Long)opFee[1];
						if(mid.equals(mainInfor.getId()))
						{
							if(fee!=null){
								opSum=fee.doubleValue();	
							}
							logger.info("费用是："+fee.doubleValue());
							break;
						}
					}
				}
				sum -= opSum;
				feeMoney.add(sum);

			}
			itemResDept=new ArrayList<String>();
			// this.itemMainInfoList=this.eventInfoService.getItemMainInfoByConId(mainid);

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
			if (clietId == null) {
				clietId = maininfo.getConCustomer();
			}
			logger.info("跳转阶段信息页面");
            maininfo = (ContractMainInfo) commonService.load(ContractMainInfo.class,mainid);    
			stagePlanlist = contractservice
			.findItemStagePlanByMainInfoId(mainid);
			mainMoneyWithPlanAmountList = contractservice
			.findMainMoneyWithPlanAmountList(mainid);
			projectPhaseList = typeManageService.getYXTypeManage(1023L);
			tickettype = typeManageService.getYXTypeManage(1004L);
			remainReceAmount = contractservice.getRemainContractReceAmount(mainid);

			return "showStage";
		}
		if (tag == 4) {
			if (clietId == null) {
				clietId = maininfo.getConCustomer();
			}
			logger.info("跳转计划信息页面");
			tickettype = typeManageService.getYXTypeManage(1004L);
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			mainMoneyList = contractservice.findMainMoneyList(mainid);
			planlist = contractservice.findPlanlist(mainid);
			maininfo = contractservice.loadContractMainInfo(mainid);
			eventallmoney = contractservice.accountEventMoney(mainid);
			stageallmoney = contractservice.accountStageMoney(mainid);
			allReveMoney = contractservice.accountReveMoney(mainid);
			return "showPlan";
		}
		if (tag == 5) {
			if (clietId == null) {
				clietId = maininfo.getConCustomer();
			}
			logger.info("跳自有产品页面");
			eqinfo = contractservice.findEquipmentMoneyByMainid(mainid);
			ownproductlist = contractservice.findselfProductByMainid(mainid);
			return "showSelfProduct";

		}
		if (tag == 6) {
			if (clietId == null) {
				clietId = maininfo.getConCustomer();
			}
			logger.info("跳转其他信息页面");
			otherinfo = contractservice.findOtherInfo(mainid);
			if(otherinfo!=null){
				checkedMaterialList=contractservice.getMaterialManagerByOtherId(otherinfo.getOtherInfoId());
			}else{
				checkedMaterialList=contractservice.getMaterialManagerByMainId(mainid);
				otherinfo =new ContractOtherInfo();
				otherinfo.setContractMainSid(mainid);
			}
			/**初始化的时候otherinfo的finallyLize是空的。然后看看有没有关联的，如果没有关联的**/
			if(otherinfo.getFinallyLize()==null||otherinfo.getFinallyLize().trim().length()==0){
				String tempLize="";
				for (MaterialManager material : checkedMaterialList) {
					tempLize+=material.getMaterialCode()+",";
				}
				otherinfo.setFinallyLize(tempLize);
			}
			return "showOtherInfo";
		}

		if (clietId == null) {
			// 建立关联
			contractservice.conCustom(UserUtils.getUser().getId(), maininfo
					.getConCustomer());
		} else {
			contractservice.conCustom(UserUtils.getUser().getId(), clietId);
		}
		// 调用寻找客户方法返回LIST客户
		clientlist = contractservice.findClient(UserUtils.getUser().getId());
		// 寻找客户项目类型
		eventclientlist = contractservice.findEventClient(UserUtils.getUser()
				.getId());
		// 寻找开票客户
		allclientlist = contractservice.findAllClient(UserUtils.getUser()
				.getId());
		// 寻找客户项目类型
		customeventypelist = typeManageService.getYXTypeManage(1007L);
		// 寻找工程责任部门
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);

		linkmanlist = new ArrayList();

		mainMoneyList = new ArrayList();

		tickettype = typeManageService.getYXTypeManage(1004L);

		copecklist = typeManageService.getYXTypeManage(1015L);

		contractnature = typeManageService.getYXTypeManage(1019L);

		contracttype = typeManageService.getYXTypeManage(1020L);

		itemdesigntypelist = typeManageService.getYXTypeManage(1012L);

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
		selfProduct();
		ownproductlist = (List<ContractOwnProduce>) contractservice
		.findselfProductByMainid(mainid);
		return "showSelfProduct";
	}
	
	public String returnToPage(){
		return	toPage(mainid,isFromNewPage);
	}



	// 确认提交
	public String sureSubmit() throws Exception {
		if(tag==1){
			//保存合同主信息
			this.tempSaveMainInfo();
		}
		else if(tag==2){
			//项目信息不用保存
		}
		else if(tag==3){
		   //开票收款阶段不用保存
		}
		else if(tag==4){
			//开票收款计划需要保存
			if (planlist != null) {
				contractservice.savePlanInfo(planlist);
			}
		}
		else if(tag==5){
			//自有产品需要保存
			if (ownproductlist != null) {
				for (int i = 0; i < ownproductlist.size(); i++) {
					ownproductlist.get(i).setConModInfo(false);
					System.out.println(ownproductlist.get(i).getConOwnProdSid());
				}
				contractservice.saveConSelfProductInfo(ownproductlist);

				if (delselfproduct != null) {
					contractservice.delConSelfProduct(delselfproduct);
				}
			}
		}
		else if(tag==6){
			//资料要求需要保存
			logger.info("saveOther!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			otherinfo.setContractMainSid(mainid);
			contractservice.saveOtherInfo(otherinfo);
		}
		

		if(contractservice.isSureSubmit(mainid)==0){
		  contractservice.sureSubmit(mainid);
			// 确认提交后跳转回列表页面
		  if(isFromNewPage==1){
		    return "customersContractlist";
		  }else{
			return "customersContract";
		  }
		}else{
			ActionContext.getContext().put("isSure", contractservice.isSureSubmit(mainid));
			return toPage(mainid,isFromNewPage);
		}

	}

	public String goback() throws Exception {
		// 确认提交后跳转回列表页面

		return "customersContract";
	}


	// 删除草稿合同
	public String delContract() throws Exception {
		contractservice.delContract(mainid);
		// 删除后跳转回列表页面
		return "customersContract";
	}

	/**
	 * 在页面显示的时候判断是否被选中
	 * @param code
	 * @return
	 */
	public boolean isMaterialChecked(String code) {
		if (customerMaterial != null) {
			for (int i = 0; i < customerMaterial.length; i++) {
				String checkedCode = customerMaterial[i];
				if (StringUtils.equals(code, checkedCode)) {
					return true;
				}
			}
		}
		return false;
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

	public IContractService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

	public ContractMainInfo getMaininfo() {
		return maininfo;
	}

	public void setMaininfo(ContractMainInfo maininfo) {
		this.maininfo = maininfo;
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

	public List<InitContractBillpro> getPlanlist() {
		return planlist;
	}

	public void setPlanlist(List<InitContractBillpro> planlist) {
		this.planlist = planlist;
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

	public List<ContractItemStagePlan> getStagePlanlist() {
		return stagePlanlist;
	}

	public void setStagePlanlist(List<ContractItemStagePlan> stagePlanlist) {
		this.stagePlanlist = stagePlanlist;
	}

	public List<YXTypeManage> getProjectPhaseList() {
		return projectPhaseList;
	}

	public void setProjectPhaseList(List<YXTypeManage> projectPhaseList) {
		this.projectPhaseList = projectPhaseList;
	}

	public ContractItemStagePlan getStagePlan() {
		return stagePlan;
	}

	public void setStagePlan(ContractItemStagePlan stagePlan) {
		this.stagePlan = stagePlan;
	}

	public List<YXTypeManage> getCopecklist() {
		return copecklist;
	}

	public void setCopecklist(List<YXTypeManage> copecklist) {
		this.copecklist = copecklist;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IEventInfoService getEventInfoService() {
		return eventInfoService;
	}

	public void setEventInfoService(IEventInfoService eventInfoService) {
		this.eventInfoService = eventInfoService;
	}

	public List<ContractMaininfoPart> getMainInfoPartList() {
		return mainInfoPartList;
	}

	public void setMainInfoPartList(List<ContractMaininfoPart> mainInfoPartList) {
		this.mainInfoPartList = mainInfoPartList;
	}

	public List<Object[]> getMainMoneyWithPlanAmountList() {
		return mainMoneyWithPlanAmountList;
	}

	public void setMainMoneyWithPlanAmountList(
			List<Object[]> mainMoneyWithPlanAmountList) {
		this.mainMoneyWithPlanAmountList = mainMoneyWithPlanAmountList;
	}

	public List<ContractItemMaininfo> getItemMainInfoList() {
		return itemMainInfoList;
	}

	public void setItemMainInfoList(List<ContractItemMaininfo> itemMainInfoList) {
		this.itemMainInfoList = itemMainInfoList;
	}
	public List<Double> getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(List<Double> feeMoney) {
		this.feeMoney = feeMoney;
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

	public Double getTotalPartMoney() {
		return totalPartMoney;
	}

	public void setTotalPartMoney(Double totalPartMoney) {
		this.totalPartMoney = totalPartMoney;
	}
	public List getChargeManList() {
		return chargeManList;
	}

	public void setChargeManList(List chargeManList) {
		this.chargeManList = chargeManList;
	}

	public Double getMainInfoPartFeeMoney() {
		return mainInfoPartFeeMoney;
	}

	public void setMainInfoPartFeeMoney(Double mainInfoPartFeeMoney) {
		this.mainInfoPartFeeMoney = mainInfoPartFeeMoney;
	}

	public Long getItemInfoId() {
		return itemInfoId;
	}

	public void setItemInfoId(Long itemInfoId) {
		this.itemInfoId = itemInfoId;
	}

	public Double getItemMoney() {
		return itemMoney;
	}

	public void setItemMoney(Double itemMoney) {
		this.itemMoney = itemMoney;
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

	public List<MaterialManager> getMaterialManagerList() {
		return materialManagerList;
	}

	public void setMaterialManagerList(List<MaterialManager> materialManagerList) {
		this.materialManagerList = materialManagerList;
	}

	public String[] getCustomerMaterial() {
		return customerMaterial;
	}

	public void setCustomerMaterial(String[] customerMaterial) {
		this.customerMaterial = customerMaterial;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}


	public Double getAllReveMoney() {
		return allReveMoney;
	}

	public void setAllReveMoney(Double allReveMoney) {
		this.allReveMoney = allReveMoney;
	}


	public Double getRemainReceAmount() {
		return remainReceAmount;
	}

	public void setRemainReceAmount(Double remainReceAmount) {
		this.remainReceAmount = remainReceAmount;
	}

	public Long getIsFromNewPage() {
		return isFromNewPage;
	}

	public void setIsFromNewPage(Long isFromNewPage) {
		this.isFromNewPage = isFromNewPage;
	}

	public String getLinkManName() {
		return linkManName;
	}

	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}
	

}

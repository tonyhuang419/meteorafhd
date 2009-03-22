package com.baoz.yx.action.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.MaterialManager;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
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
	@Result(name = "showmain", value = "/WEB-INF/jsp/contract/newContractMainInfo.jsp"),
	@Result(name = "showevent", value = "/WEB-INF/jsp/contract/newContractEventInfo.jsp"),
	@Result(name = "showStage", value = "/WEB-INF/jsp/contract/newContractStage.jsp"),
	@Result(name = "showPlan", value = "/WEB-INF/jsp/contract/modifyFormalContractPlanInfo.jsp"),
	@Result(name = "showInvoice", value = "/WEB-INF/jsp/contract/newContractConInvoice.jsp")
})
public class FormalContractPlanModifyAction extends DispatchAction {

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
	private List<RealContractBillandRecePlan> planlist;

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
	
	private Long a=0L;
	
	private List<MaterialManager> materialManagerList;//应交材料列表
	
	private String[] customerMaterial;//选择的已经材料
	
	private List<MaterialManager> checkedMaterialList;//显示选中的应交材料
	
	private Long otherInfoId;//合同其他信息id
	
	private Double allReveMoney;//收款金额

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

	//变更开票收款计划 直接跳转页面
	public String doDefault() throws Exception {
		   setTag(4);
	       return toPage(mainid);
	}


	// 保存开票收款计划
	public String savePlanInfo() throws Exception {
		if (planlist != null) {
			contractservice.saveFormalPlanInfo(planlist);
		}
		ActionContext.getContext().put("isRelationSuccess", "true");
		setTag(4);
		return toPage(mainid);
	}


	public String toPage(Long mainid) {
		if (tag == 4) {
			logger.info("跳转计划信息页面");
			maininfo=contractservice.loadContractMainInfo(mainid);
			tickettype = typeManageService.getYXTypeManage(1004L);
			itemdesigntypelist = typeManageService.getYXTypeManage(1012L);
			mainMoneyList = contractservice.findMainMoneyList(mainid);
			planlist = contractservice.findFormalPlanlist(mainid);
			eventallmoney = contractservice.accountEventMoney(mainid);
			stageallmoney = contractservice.accountStageMoney(mainid);
			allReveMoney = contractservice.accountReveMoney(mainid);
			return "showPlan";
		}
		return "showmain";
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



	public List<RealContractBillandRecePlan> getPlanlist() {
		return planlist;
	}

	public void setPlanlist(List<RealContractBillandRecePlan> planlist) {
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

	public Double getAllReveMoney() {
		return allReveMoney;
	}

	public void setAllReveMoney(Double allReveMoney) {
		this.allReveMoney = allReveMoney;
	}
	
	

}

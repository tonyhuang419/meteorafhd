package com.baoz.yx.action.assistance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.TicketCollection;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.Param;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;
@Results( {
		@Result(name = "enterSave", value = "/WEB-INF/jsp/assistance/newAssistance.jsp"),
		@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceList.jsp"),
		@Result(name = "succ", type = ServletRedirectResult.class, value = "/assistance/assistanceQuery.action"),
		@Result(name = "affirm", type = ServletRedirectResult.class, value = "/assistance/affirmAssistanceContract.action?method=query"),
		@Result(name = "success", type = ServletRedirectResult.class, value = "/assistance/assistanceMQuery.action"),
		@Result(name = "list", type = ServletRedirectResult.class, value = "/assistance/assistanceMLeftQuery.action?method=query"),
		@Result(name = "edit", value = "/WEB-INF/jsp/assistance/assistanceEdit.jsp"),
		@Result(name = "enterApply", value = "/WEB-INF/jsp/assistance/assistanceApply.jsp"),
		@Result(name = "return", value = "/WEB-INF/jsp/assistance/assistanceReturn.jsp"),
		@Result(name = "showApplyHistory", value = "/WEB-INF/jsp/assistance/assistanceApplyHistory.jsp"),
		@Result(name = "saveBack", value = "/WEB-INF/jsp/assistance/saveReturn.jsp"),
		@Result(name = "passBack", value = "/WEB-INF/jsp/assistance/passAssistanceReturn.jsp"),
		@Result(name = "passAssistanceBack", value = "/WEB-INF/jsp/assistance/passReturn.jsp"),
		@Result(name = "hasError",value = "/WEB-INF/jsp/assistance/passError.jsp"),
		@Result(name = "saveApplyBack", value = "/WEB-INF/jsp/assistance/saveApplyReturn.jsp"),
		@Result(name = "passApplyBack", value = "/WEB-INF/jsp/assistance/passApplyReturn.jsp"),
		@Result(name = "editBack", value = "/WEB-INF/jsp/assistance/editReturn.jsp"),
		@Result(name = "newTicket", value = "/WEB-INF/jsp/assistance/newAssistanceTicket.jsp"),
		@Result(name = "editSee", value = "/WEB-INF/jsp/assistance/updateSection.jsp"),
		@Result(name = "showZhong", value = "/WEB-INF/jsp/assistance/showSuc.jsp"),
		@Result(name = "enterTicket", value = "/WEB-INF/jsp/assistance/assistanceTicket.jsp"),
		@Result(name = "inputAssistanceNo" , value = "/WEB-INF/jsp/assistance/inputAssistanceNo.jsp"),
		@Result(name = "passAssistance" , value = "/WEB-INF/jsp/assistance/passAssistance.jsp"),
		@Result(name = "showBack" , value = "/WEB-INF/jsp/assistance/confirmCancel.jsp"),
		@Result(name = "showApply" , value = "/WEB-INF/jsp/assistance/assistanceApply.jsp"),
		@Result(name = "prepApplyPayList",value = "/WEB-INF/jsp/assistance/prepApplyPayList.jsp"),
		@Result(name = "passAssistanceError",value = "/WEB-INF/jsp/assistance/passAssistanceError.jsp")
//		@Result(name = "passAssistanceError", type = ServletRedirectResult.class, value = "/assistance/apply.action?method=enterUpdate&ids=${payInfoId}")
})
public class AssistanceAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typemanageservice;
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService codeGenerateService;
	
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("assistancePayService")
	private IAssistancePayService assistancePayService;
	private AssistanceContract a;
	private String stateId;
	private String mix;
	private String max;
	private String start;
	private String end;
	private PageInfo info;
	private String ids;   ////外协合同id
	private AssistanceContract ac;
	private ServletRequest request;
	private ContractItemMaininfo c;
	private ContractMainInfo cm;
	private SupplierInfo s;
	private String id;////外协合同id
	private AssistanceTicket at;
	private List<TicketCollection> tc;
	private Assistance assistance;
	private AssistancePayInfo pi;
	private Long ticketId;
	private List<Assistance> tList;
	private List<AssistancePayInfo> pList;
	private Double sum;
	private Long assistanceContractId;
	private String userName;
	private String sDate;
	private String eDate;
	private String supplierName;
	private String MContractName;
	//新增时的合同名称
	private String contractName;
	private String groupName;
	private int intSum;
	private Date date;

	private AssistanceSection as;                   
	
	private List<AssistanceSection> sectionList;     //阶段list
	
	private Long 				sectionId;          //阶段id
	
	private String mainProjectId;
	private double amount;

	private double lastSum;
	
	private Double editablePrepAmount;/////可编辑的预付款的余额
	
	private List<AssistanceSection> asectionList;//外协合同阶段阶段表
	
	private Long[] assistanceSectionId;//阶段表中的id,目前在付款申请的时候用到了
	
	
	private Long payInfoId;//付款申请id 
	
	private String assignmentId ;//任务号
	
	private String receiveNum;//接收号
	
	private String asCreate;   //判断是否是从新增页面弹到修改页面上去的
	
	private Long asContractId; //外协合同id,在验证合同金额的时候用到了这个变量，其他的没有
	
	private Double unallocateAmount;//阶段未分配金额
	
	private String assistanceNo;////外协合同号
	
	private String isSuccess;

	private String returnReason;//退回理由
	
	
	private List<AssistancePayInfo> prepApplyPayList;///////预付款申请单列表
	
	private Long[] prepayIds;////预付款的id
	
	private Double thisRelationPrepAmount;//////本次关联的预付款金额
	
	private Double thisApplyPayAmount;////本次申请总额
	
	private Double splitSectionAmount;////需要拆分的阶段金额
	
	private String isPrePay;////是否预付款
	
	
	private Double prepPaySum;///////关联的预付款金额
	
	private Map<Long, AssistanceTicket> assistanceTicketMap=new HashMap<Long, AssistanceTicket>();////记录已经关联过的发票信息
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Override
	public String doDefault() throws Exception {
		logger.info("外协合同新增");
		userName = UserUtils.getUser().getName();
		//清楚以前的新增值
		getSectionListInSession().clear();
		return ENTER_SAVE;
	}

	/**
	 * 验证添加的金额与添加的合同金额相比不能超过
	 * 
	 * @return
	 */
	public String checkAmount() {
		logger.info("验证金额");
		logger.info("项目号是:" + mainProjectId);
		logger.info("书写的金额是:" + amount);
		BigDecimal itemAmount = (BigDecimal) service
				.uniqueResult(
						" select sum(ii.conItemAmountWithTax) from ContractItemInfo ii,ContractItemMaininfo cim where ii.contractItemMaininfo = cim.conItemMinfoSid and cim.conItemId  = ?",
						mainProjectId);
		Double tAmount = (Double) service.uniqueResult(
						" select sum(ac.contractMoney) from AssistanceContract ac where ac.mainProjectId = ?",
						mainProjectId);
		logger.info("已关联"+tAmount);
		if(tAmount == null){
			tAmount = 0D;
		}
		Double totalAmount = tAmount + amount;
		DecimalFormat df = new DecimalFormat("##.00");
		lastSum = Double.parseDouble(df.format(itemAmount.doubleValue()
				- tAmount));
		if (totalAmount > itemAmount.doubleValue())
			this.renderText("{isOverAmount:true,amount:" + lastSum + "}");
		else
			this.renderText("{isOverAmount:false,amount:" + lastSum + "}");
		return null;
	}
	/**
	 * 验证添加的金额与添加的合同金额相比不能超过
	 * 
	 * @return
	 */
	public String checkAmountNotContains() {
		logger.info("验证金额");
		logger.info("项目号是:" + mainProjectId);
		logger.info("书写的金额是:" + amount);
		BigDecimal itemAmount = (BigDecimal) service
				.uniqueResult(
						" select sum(ii.conItemAmountWithTax) from ContractItemInfo ii,ContractItemMaininfo cim where ii.contractItemMaininfo = cim.conItemMinfoSid and cim.conItemId  = ?",
						mainProjectId);
		Double tAmount = (Double) service.uniqueResult(
						" select sum(ac.contractMoney) from AssistanceContract ac where ac.mainProjectId = ? and  ac.id != ?",
						mainProjectId,asContractId);
		logger.info("已关联"+tAmount);
		if(tAmount == null){
			tAmount = 0D;
		}
		Double totalAmount = tAmount + amount;
		DecimalFormat df = new DecimalFormat("##.00");
		lastSum = Double.parseDouble(df.format(itemAmount.doubleValue()
				- tAmount));
		if (totalAmount > itemAmount.doubleValue())
			this.renderText("{isOverAmount:true,amount:" + lastSum + "}");
		else
			this.renderText("{isOverAmount:false,amount:" + lastSum + "}");
		return null;
	}

	//新增保存阶段   存如session
	public String saveSection(){
		as.setId(Param.getId());
		sectionList = getSectionListInSession();
		as.setAssistanceItemSId(ac.getConItemMainInfoSid());
		sectionList.add(as);
		userName = UserUtils.getUser().getName();
		Double sum = 0.00;
		if(sectionList!=null){
			for(AssistanceSection sec : sectionList){
				sum += sec.getSectionAmount();
			}
		}
		unallocateAmount = ac.getContractMoney() - sum;
		return ENTER_SAVE;
	}
	//删除session中阶段信息
	public String deleteSection(){
		sectionList=getSectionListInSession();
		for(int k=0;k<sectionList.size();k++){
			AssistanceSection  a=sectionList.get(k);
			if(a.getId().longValue()==sectionId.longValue()){
				sectionList.remove(a);
			}
		}
		Double sum = 0.00;
		if(sectionList!=null){
			for(AssistanceSection sec : sectionList){
				sum += sec.getSectionAmount();
			}
		}
		unallocateAmount = ac.getContractMoney() - sum;
		userName = UserUtils.getUser().getName();
		return ENTER_SAVE;
	}
	private List<AssistanceSection> getSectionListInSession(){
		List<AssistanceSection> assistanceSectionList = (List<AssistanceSection>) ActionContext.getContext().getSession().get("assistanceSectionList");
		if(assistanceSectionList == null){
			assistanceSectionList = new ArrayList<AssistanceSection>();
			ActionContext.getContext().getSession().put("assistanceSectionList",assistanceSectionList);
		}
		return assistanceSectionList;
	}
	/**
	 * 往sessionlist里面赋值，赋值的时候先走一边数据库，如果数据库里面有值的话都弄出来放在list里面
	 * @param contractId
	 */
	private void  setSectionListInSession(Long contractId){
		List<AssistanceSection> assistanceSectionList = (List<AssistanceSection>) ActionContext.getContext().getSession().get("assistanceSectionList");
		if(assistanceSectionList == null){
			assistanceSectionList = new ArrayList<AssistanceSection>();
		}
		if(contractId != null){
			List<AssistanceSection> assistanceList = service.list("from AssistanceSection where assistanceId = ?", contractId);
			assistanceSectionList.addAll(assistanceList);
		}
		ActionContext.getContext().getSession().put("assistanceSectionList",assistanceSectionList);
		
	}
	
	/**
	 * 保存
	 * @return
	 * @throws Exception
	 */
	public String saveAssistance() throws Exception {
		this.logger.info("保存外协合同");
		if (asCreate != null ) {
			updateAssistance(ac.getId());
		}
		else{
			saveAssistanceMethod();
			//判断下是否是从新增页面上过去的修改页面  1代表是从新增页面上过去
		}
		asCreate = "1";
		//新增下一个外协合同
		ids = ac.getId()+"";
		return enterUpdate();
	}
	/**
	 * 保存并关闭
	 * @return
	 * @throws Exception
	 */
	public String saveAssistanceAnClose()throws Exception{
//		this.logger.info("保存外协合同");
//		if (asCreate != null ) {
//			updateAssistance(ac.getId());
//		}
//		else{
//			saveAssistanceMethod();
//		}
		return "saveBack";
	}
	/**
	 * 保存字段方法  公共的
	 */
	private void saveAssistanceMethod() {
		ac.setEmployeeId(UserUtils.getUser().getId());
		ac.setAssistanceContractType("0");
		ac.setIs_active("1");
		ac.setAssistanceType("0");
		ac.setUpdateBy(new Date());
		ac.setById(UserUtils.getUser().getId());
		ac.setTicketMoney(0.00);
		ac.setHasPayAmount(0.00);
		ac.setReceiptAmount(0.00);
		ContractMainInfo contract = (ContractMainInfo)service.load(ContractMainInfo.class, ac.getContractId());
		ac.setEmployeeId(contract.getSaleMan());
		service.saveOrUpdate(ac);
		//保存阶段
		for (AssistanceSection section : getSectionListInSession()) {
			section.setId(null);
			section.setAssistanceId(ac.getId());
			section.setAssistanceConSId(ac.getContractId());
			section.setAssistanceItemSId(ac.getConItemMainInfoSid());
			service.save(section);
		}
	}
	/**
	 * 新增页面确认提交
	 * @return
	 * @throws Exception
	 */
	public String saveConfirmSubmit() throws Exception {
		this.logger.info("确认提交");
		/**asCreate空的话就是新增页面,首先要新增然后提交确认
		 * 如果不是空的话就是保存以后的提交确认,就需要调用下修改方法updateAssistance,修改下确认标示字段
		 */
		if (asCreate != null ) {
			updateAssistance(ac.getId());
			String[] acId=new String[]{ac.getId().toString()};
			if(sameJudgeMethod(acId)){
				asCreate = "1";
				ids = ac.getId()+"";
				return enterUpdate();
			}
			AssistanceContract contract =(AssistanceContract)service.load(AssistanceContract.class, ac.getId());
			contract.setAssistanceContractType("1");
			contract.setIs_active("1");
			service.update(contract);
		}
		else{
			saveAssistanceMethod();
			//判断项目是否可以添加保存 ERP号码是否正确
			String[] acId=new String[]{ac.getId().toString()};
			if(sameJudgeMethod(acId)){
				asCreate = "1";
				ids = ac.getId()+"";
				return enterUpdate();
			}
			AssistanceContract asCon = (AssistanceContract) service.load(AssistanceContract.class, ac.getId());
			asCon.setAssistanceContractType("1");
			service.update(asCon);
		}
		return "passBack";
	}
	public String querentijiao() throws Exception{
		updateAssistance(ac.getId());
		String[] acId=new String[]{ac.getId().toString()};
		if(sameJudgeMethod(acId)){
			ids = ac.getId()+"";
			return enterUpdate();
		}
		AssistanceContract contract = (AssistanceContract)service.load(AssistanceContract.class, ac.getId());
		contract.setAssistanceContractType("1");
		contract.setIs_active("1");
		service.update(contract);
		return "passAssistanceBack";
		
	}
	
	private Boolean sameJudgeMethod(String[] contracts) {
		Object[] obj = assistanceService.isSureSubmit(contracts);
		Boolean flag = (Boolean)obj[0];
		if(flag){
			AssistanceContract acontract = (AssistanceContract)service.load(AssistanceContract.class, (Long)(obj[1]));
			ActionContext.getContext().put("notSure", flag);
			ActionContext.getContext().put("assistanceContractName", acontract.getAssistanceName());
			ActionContext.getContext().put("returnCode", (String)(obj[2]));
		}
		return flag;
	}
	
	private Boolean sameJudgeApplySumbit(Long pid){
		Object[] obj = assistanceService.isSureSubmitApply(pid);
		Boolean flag = (Boolean)obj[0];
		if(flag){
			AssistancePayInfo payInfo = (AssistancePayInfo)service.load(AssistancePayInfo.class, (Long)(obj[1]));
			ActionContext.getContext().put("notSure", flag);
			ActionContext.getContext().put("payInfoCode", payInfo.getApplyInfoCode());
			ActionContext.getContext().put("returnCode", (String)(obj[2]));
		}
		return flag;
		
	}

	public String verifyState() throws Exception {
		this.logger.info("提交确认");
		String ids[] = stateId.split(",");
		Boolean flag = sameJudgeMethod(ids);
		if(flag){
			return "hasError";
		}
		
		for (String id : ids) {
			String hql = "from AssistanceContract ac where ac.id = " + id;
			ac = (AssistanceContract) service.uniqueResult(hql);
			ac.setAssistanceContractType("1");
			service.update(ac);
		}
		return "passAssistance";
	}

	public String enterConfirmPass() throws Exception {
		this.logger.info("确认通过");
		String hql = "from AssistanceContract ac where ac.id = " + asContractId;
		ac = (AssistanceContract) service.uniqueResult(hql);//获取外协合同信息
		Date date = new Date();
		String cHql = "from ContractMainInfo c where c.conMainInfoSid = "
				+ ac.getContractId();
		ContractMainInfo cmi = (ContractMainInfo) service
				.uniqueResult(cHql);//获取主合同信息
		String assisNo = codeGenerateService.generateAssistanceCode(ac
				.getMainProjectId(), date, cmi.getConCustomer());//生成外协合同号
		ac.setAssistanceId(assisNo);//给合同号赋值	
		return "inputAssistanceNo";
	}
	
	public String confirmPass() throws Exception {
		this.logger.info("确认通过");
		String hql = "from AssistanceContract ac where ac.id = " + asContractId;
		AssistanceContract ab = (AssistanceContract) service.uniqueResult(hql);
		ab.setAssistanceContractType("2");
		Boolean flag = assistanceService.checkAssistanceNoExists(ac.getAssistanceId(), asContractId);
		if(flag){
			ActionContext.getContext().put("sign","1");
			return enterConfirmPass();
		}
		ab.setAssistanceId(ac.getAssistanceId());
		service.update(ab);
		ContractMainInfo contract = (ContractMainInfo)service.load(ContractMainInfo.class, ab.getContractId());
		contract.setExistSidehelp(Boolean.TRUE);
		service.update(contract);
		isSuccess = "1";
		return "affirm";
	}
	public String confirmReturn()throws Exception{
		return "affirm";
	}
	
	//显示退回理由
	public String showBack(){
		return "showBack";
	}
	
	public String back() throws Exception {
		this.logger.info("确认退回");
		String ids[] = stateId.split(",");
		for (String id : ids) {
			String hql = "from AssistanceContract ac where ac.id = " + id;
			ac = (AssistanceContract) service.uniqueResult(hql);
			ac.setAssistanceContractType("3");
			ac.setReturnReason(returnReason);
			service.update(ac);
		}
		isSuccess = "5";
		return "affirm";
	}
	/**
	 * 进入修改页面
	 * @return
	 */
	public String enterUpdate(){
		this.logger.info("进入修改");
		getSectionListInSession().clear();
		String hql = "from AssistanceContract ac where ac.is_active='1' and ac.id="
				+ ids;
		ac = (AssistanceContract) service.uniqueResult(hql);
		String sHql = "from SupplierInfo si where si.supplierid="
				+ ac.getSupplyId();
		SupplierInfo si = (SupplierInfo) service.uniqueResult(sHql);
		supplierName = si.getSupplierName();
		if (ac.getContractId() != null && !"".equals(ac.getContractId())) {
			String cHql = "from ContractMainInfo c where c.conMainInfoSid="
					+ ac.getContractId();
			ContractMainInfo c = (ContractMainInfo) service.uniqueResult(cHql);
			if (c != null) {
				MContractName = c.getConName();
			}
		}
		userName = assistanceService.getAContractName(Long.parseLong(ids));
		String seHql=" from AssistanceSection a where a.assistanceId = "+ac.getId();
		Double sum = 0.00;
		sectionList = service.list(seHql);
		if(sectionList!=null){
			for(AssistanceSection sec : sectionList){
				sum += sec.getSectionAmount();
			}
		}
		unallocateAmount = ac.getContractMoney() - sum;
		return "edit";
	}
	public String checkAssistanceNoExists()throws Exception{
		Boolean flag =assistanceService.checkAssistanceNoExists(assistanceNo, asContractId);
		if(flag){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
		return null;
	}
	
	public String editAssistance(){
		this.logger.info("保存合同");
		updateAssistance(ac.getId());
		return "editBack";
	}
	/**
	 * 保存到数据库
	 * 
	 */
	public String saveSee(){
		////////先保存合同主题信息，再保存阶段信息
		updateAssistance(ac.getId());
		as.setAssistanceId(ac.getId());
		as.setAssistanceConSId(ac.getContractId());
		as.setAssistanceItemSId(ac.getConItemMainInfoSid());
		service.save(as);
		return enterUpdate();
	}
	/**
	 * 修改数据库中的阶段信息
	 * @return
	 */
	public String updateSee(){
		AssistanceSection as1 = (AssistanceSection) service.load(AssistanceSection.class,sectionId);
		as1.setSectionName(as.getSectionName());
		as1.setSectionAmount(as.getSectionAmount());
		as1.setSectionBillTime(as.getSectionBillTime());
		service.update(as1);
		return "showZhong";
	}
	/**
	 * 显示修改阶段信息
	 * @return
	 */
	public String editSee(){
		as = (AssistanceSection) service.load(AssistanceSection.class, sectionId);
		return "editSee";
	}
	private void updateAssistance(Long assistanceId) {
		String hql = "from AssistanceContract ac where ac.id = " + assistanceId;
		AssistanceContract acUpdate = (AssistanceContract) service
				.uniqueResult(hql);
		acUpdate.setSupplyId(ac.getSupplyId());
		acUpdate.setAssistanceName(ac.getAssistanceName());
		acUpdate.setContractId(ac.getContractId());
		acUpdate.setMainProjectId(ac.getMainProjectId());
		acUpdate.setMainProjectName(ac.getMainProjectName());
		acUpdate.setContractMoney(ac.getContractMoney());
		acUpdate.setContractDate(ac.getContractDate());
		acUpdate.setEndDate(ac.getEndDate());
		acUpdate.setContractContent(ac.getContractContent());
		acUpdate.setAssistanceName(ac.getAssistanceName());
		acUpdate.setConItemMainInfoSid(ac.getConItemMainInfoSid());
		ContractMainInfo contract = (ContractMainInfo)service.load(ContractMainInfo.class, ac.getContractId());
		acUpdate.setEmployeeId(contract.getSaleMan());
		service.update(acUpdate);
	}

	public String passAssistanceContract() throws Exception {
		if (ac.getId() != null && !"".equals(ac.getId())) {
			String hql = "from AssistanceContract ac where ac.id = "
					+ ac.getId();
			ac = (AssistanceContract) service.uniqueResult(hql);
			Date d = new Date();
			ac.setUpdateBy(d);
			ac.setAssistanceType("1");
			service.update(ac);
		} else
			service.save(ac);
		return "success";
	}
	//删除阶段
	public String delSection() {
		this.logger.info("删除合同");
		
		updateAssistance(ac.getId());
		AssistanceSection section = (AssistanceSection)service.load(AssistanceSection.class, sectionId);
		service.delete(section);
		return enterUpdate();
	}

	public String del() throws Exception {
		this.logger.info("删除合同");
		AssistanceContract contract = (AssistanceContract)service.load(AssistanceContract.class, Long.valueOf(ids));
		service.delete(contract);
		return "list";
	}
	public String enterPrepApplyPay()throws Exception{
		getSessionPrepApplyPayList().clear();
		AssistanceContract contract = (AssistanceContract)service.load(AssistanceContract.class, Long.parseLong(ids));
		prepApplyPayList = assistanceService.getAssistancepayInfoBySupplierId(contract);
		return "prepApplyPayList";
	}
	
	
	private void setSessionPrepApplyPayList(List<AssistancePayInfo> list){
		List<AssistancePayInfo> opList = (List<AssistancePayInfo>)ActionContext.getContext().getSession().get("prepApplyPayLists");
		if(opList==null){
			opList = new ArrayList<AssistancePayInfo>();
		}
		if(list!=null){
			opList.addAll(list);
		}
		ActionContext.getContext().getSession().put("prepApplyPayLists", opList);
	}
	private List<AssistancePayInfo> getSessionPrepApplyPayList(){
		List<AssistancePayInfo> opList = (List<AssistancePayInfo>)ActionContext.getContext().getSession().get("prepApplyPayLists");
		if(opList==null){
			opList = new ArrayList<AssistancePayInfo>();
		}
		ActionContext.getContext().getSession().put("prepApplyPayLists",opList);
		return opList;
	}
	
	public String enterNextApply()throws Exception{
		Map<Long,AssistancePayInfo> tempMap = new HashMap<Long,AssistancePayInfo>();
		if(prepayIds!=null&&prepayIds.length>0){
			for( int i = 0 ;i <prepayIds.length; i ++ ){
				AssistancePayInfo pay = (AssistancePayInfo)service.load(AssistancePayInfo.class, prepayIds[i]);
				tempMap.put(pay.getId(), pay);
			}
		}
		List<AssistancePayInfo> opList = new ArrayList<AssistancePayInfo>();
		opList.addAll(tempMap.values());
		setSessionPrepApplyPayList(opList);
		showApplyPayInfoMessage();
		getAssistanceList().clear();
		return "enterApply";
	}
	public String enterApply() throws Exception {
		showApplyPayInfoMessage();
		getAssistanceList().clear();
		getSessionPrepApplyPayList().clear();
		return "enterApply";
		
	}
	
	public String splitSection()throws Exception{
		assistanceService.splitSectionAmount(sectionId, splitSectionAmount);
		showApplyPayInfoMessage();
		tList = getAssistanceList();
		return "enterApply";
	}
	
	private void showApplyPayInfoMessage(){
		date = new Date();
		userName = UserUtils.getUser().getName();
		
		Employee e = UserUtils.getUser();
		groupName = userService.getDepartment(e).getOrganizationName();
		if(assistanceContractId == null){
			assistanceContractId = Long.parseLong(ids);
		}
		sum = 0.0;
		// 查询外协合同
		String hql = "from AssistanceContract ac where ac.id="
				+ assistanceContractId;
		ac = (AssistanceContract) service.uniqueResult(hql);
		// 查询主题合同
		String cId = ac.getContractId().toString();
		// String cId = c.getConItemId();
		if (cId != null && !"".equals(cId)) {
			String cHql = "from ContractMainInfo cm where cm.conMainInfoSid='"
					+ cId + "'";
			cm = (ContractMainInfo) service.uniqueResult(cHql);
		}
		// 查询供应商信息
		String sId = ac.getSupplyId().toString();
		String sHql = "from SupplierInfo s where s.supplierid=" + sId;
		s = (SupplierInfo) service.uniqueResult(sHql);
	
		// 查询支付履历
		String piHql = "from AssistancePayInfo pi where pi.assistanceId = "
				+ ac.getId()+" order by applyDate ";
		pList = service.list(piHql);
		List<Assistance> assistanceList = getAssistanceList();
		if (ac.getTicketMoney() == null) {
			ac.setTicketMoney(0.0);
		}
		if (ac.getMainProjectId() != null) {
			String pHql = "from ContractItemMaininfo c where c.conItemId = '"
					+ ac.getMainProjectId() + "'";
			c = (ContractItemMaininfo) service.uniqueResult(pHql);
		}
		//////计算将要关联的预付款的总额
	    prepPaySum	=getSumPrepAmount(getSessionPrepApplyPayList());
		asectionList = assistanceService.getAssistanceSectionByContractId(ac.getId());
	}
	
	public String enterTicket() throws Exception {
		this.logger.info("进入关联发票");
		// 去掉已选择的发票
		// 现在已选择的发票显示的时候
		List<Assistance> assList = getAssistanceList();
		for(Assistance assistance : assList){
			AssistanceTicket ticket = assistance.getTicket();
			assistanceTicketMap.put(ticket.getId(),ticket);
		}
		
		String hql = "select at,s.supplierName from AssistanceTicket at,SupplierInfo s,AssistanceContract ac " +
				" where at.is_active='1' " +
				" and at.customerId = s.supplierid and ac.id = at.contractId and ac.id="
				+ id;
		info = queryService.listQueryResult(hql, info);
		// 查询外协合同
		ac = (AssistanceContract) service.load(AssistanceContract.class,Long.valueOf(id));
		List<AssistancePayInfo> prepList = getSessionPrepApplyPayList();
		thisRelationPrepAmount = getSumPrepAmount(prepList);
		return "enterTicket";
	}
	private Double getSumEditablePrepAmount(List<TicketCollection> ticketcList){
		///已经关联发票的预付款的总金额
		Double hasRelationAmount=0.00;
		if( ticketcList != null &&ticketcList.size() >0 ){
			for(TicketCollection cel : ticketcList){
				if( cel.getPrepAmount() != null ){
					hasRelationAmount += cel.getPrepAmount();
				}
			}
		}
		return hasRelationAmount;
	}

	private Double getSumPrepAmount(List<AssistancePayInfo> prepList){
		Double sum = 0.00;
		////关联预付款的总金额
		if( prepList != null && prepList.size() > 0 ){
			for (AssistancePayInfo pay : prepList) {
				if( pay.getPayNum()!=null ){
					sum += pay.getPayNum();
				}
			}
		}
		return sum;
	}
	
	public String doTicket() throws Exception {
		this.logger.info("关联发票");
		sum = 0.0;
		pi = new AssistancePayInfo();
		List<Assistance> assistanceList = getAssistanceList();
		//////该方法在此处还不能删除，主要是要用到父页面的显示
		for (TicketCollection o : tc) {
			if (o.getId() != null) {
				assistance = new Assistance();
				Long tId = o.getTicketId();
				String hql = "from AssistanceTicket at where at.id = " + tId;
				at = (AssistanceTicket) service.uniqueResult(hql);
				assistance.setTicket(at);
				assistanceList.add(assistance);
				id = at.getContractId().toString();
			}
		}
		Long pId = pi.getId();
		String hql = "from Assistance a where a.assistancePayId = " + pId;
		List<Assistance> aList = service.list(hql);
		return "return";
	}

	private List<Assistance> getAssistanceList() {
		List<Assistance> assistanceList = (List<Assistance>) ActionContext
				.getContext().getSession().get("assistance_apply_list");
		if (assistanceList == null) {
			assistanceList = new ArrayList<Assistance>();
		}
		ActionContext.getContext().getSession().put("assistance_apply_list",
				assistanceList);
		return assistanceList;
	}
	public String relationTicket() {
		this.logger.info("关联发票后查询");
		showApplyPayInfoMessage();
		tList = getAssistanceList();
		return "enterApply";
	}

	public String saveAssistanceApply() throws Exception {
		this.logger.info("保存付款申请");
		saveAssistanceApplyInfo("0");
		return "saveApplyBack";
	}
	
	public String passAssistanceApply() throws Exception {
		this.logger.info("确认提交付款申请");
		AssistancePayInfo payInfo = saveAssistanceApplyInfo("0");
		if(sameJudgeApplySumbit(payInfo.getId())){
			//错误的话返回到本页面
			payInfoId = payInfo.getId();
			return "passAssistanceError";
		}
		confirmAssistanceApply(payInfo);
		return "passApplyBack";
	}
	public void confirmAssistanceApply(AssistancePayInfo payInfo){
		payInfo.setPayState("1");
		service.update(payInfo);
	}
	

	private AssistancePayInfo saveAssistanceApplyInfo(String payState) {
		pi.setPayState(payState);
		pi.setAssistanceId(assistanceContractId);
		if(pi.getApplyPay()==null){
			pi.setApplyPay(Boolean.FALSE);
		}
		Double sum = assistancePayService.getRelationSectionAmountBySectionId(assistanceSectionId);
		pi.setPayNum(sum);
		//pi.setApplyDept(userService.getDepartment(UserUtils.getUser()).getOrganizationCode());
		pi.setApplyInfoCode(codeGenerateService.assApplyInfoCode(pi.getAssistanceId()));
		List<AssistanceSection> assistanceSectionList = assistancePayService.getSectionBySectionIds(assistanceSectionId);
		List<Assistance> assistanceList = getAssistanceList();
		List<AssistanceTicket> ticketList = assistancePayService.changeTicketToAssistance(assistanceList);
		List<AssistancePayInfo> payInfoList = this.getSessionPrepApplyPayList();
		AssistancePayInfo payInfo = assistancePayService.saveAssistancePayInfo(pi, payInfoList, ticketList, assistanceSectionList);
		///////保存完了清除session里面的值
		cleanSessionValue();
		return payInfo;
	}
	private void cleanSessionValue(){
		if(ActionContext.getContext().getSession().containsKey("assistance_apply_list")){
			ActionContext.getContext().getSession().remove("ActionContext.getContext().getSession()");
		}
		if(ActionContext.getContext().getSession().containsKey("prepApplyPayLists")){
			ActionContext.getContext().getSession().remove("prepApplyPayLists");
		}
	}

	public String unchainRelation() {
		this.logger.info("解除关联");
		String hql = "from AssistanceTicket at where at.id = " + ticketId;
		at = (AssistanceTicket) service.uniqueResult(hql);
		String aHql = "from Assistance a where a.ticket = " + ticketId;
		assistance = (Assistance) service.uniqueResult(aHql);
		List<Assistance> assistanceList = getAssistanceList();
		ListIterator<Assistance> listIte = assistanceList.listIterator();
		while (listIte.hasNext()) {
			Assistance assistance = (Assistance) listIte.next();
			if (assistance.getTicket().getId().equals(ticketId)) {
				listIte.remove();
			}
		}
		ids = at.getContractId().toString();
		return relationTicket();
	}

	public String showApplyHistory() {
		this.logger.info("查询支付履历");
		String hql = "from AssistancePayInfo pi where pi.is_active='1' and pi.assistanceId = "
				+ assistanceContractId+" order by applyDate ";
		pList = service.list(hql);
		intSum = pList.size();
		return "showApplyHistory";
	}

	public String newTicket() {
		this.logger.info("新增发票");

		return "newTicket";
	}
	
	public String checkAssignmentIdExists()throws Exception{
		Boolean flag=false;
		if(payInfoId!=null){
			flag = assistanceService.checkAssignmentIdExists(assignmentId,payInfoId);
		}else{
			flag = assistanceService.checkAssignmentIdExists(assignmentId);
		}
		if(flag){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
		return null;
	}
	
	public String checkreceivNumExists()throws Exception{
		Boolean flag =false;
		if(payInfoId!=null){
			flag = assistanceService.checkreceivNumExists(receiveNum,payInfoId);
		}else{
			flag = assistanceService.checkreceivNumExists(receiveNum);
		}
		if(flag){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
		return null;
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

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TicketCollection> getTc() {
		return tc;
	}

	public void setTc(List<TicketCollection> tc) {
		this.tc = tc;
	}


	public List<Assistance> getTList() {
		return tList;
	}

	public void setTList(List<Assistance> list) {
		tList = list;
	}

	public List<AssistancePayInfo> getPList() {
		return pList;
	}

	public void setPList(List<AssistancePayInfo> list) {
		pList = list;
	}

	public Assistance getAssistance() {
		return assistance;
	}

	public void setAssistance(Assistance assistance) {
		this.assistance = assistance;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public AssistancePayInfo getPi() {
		return pi;
	}

	public void setPi(AssistancePayInfo pi) {
		this.pi = pi;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public String getMix() {
		return mix;
	}

	public void setMix(String mix) {
		this.mix = mix;
	}

	public SupplierInfo getS() {
		return s;
	}

	public void setS(SupplierInfo s) {
		this.s = s;
	}

	public AssistanceTicket getAt() {
		return at;
	}

	public void setAt(AssistanceTicket at) {
		this.at = at;
	}

	public String getMax() {
		return max;
	}

	public ContractItemMaininfo getC() {
		return c;
	}

	public void setC(ContractItemMaininfo c) {
		this.c = c;
	}

	public ContractMainInfo getCm() {
		return cm;
	}

	public void setCm(ContractMainInfo cm) {
		this.cm = cm;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	public AssistanceContract getAc() {
		return ac;
	}

	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}


	public AssistanceContract getA() {
		return a;
	}

	public void setA(AssistanceContract a) {
		this.a = a;
	}

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}


	public Long getAssistanceContractId() {
		return assistanceContractId;
	}

	public void setAssistanceContractId(Long assistanceContractId) {
		this.assistanceContractId = assistanceContractId;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSDate() {
		return sDate;
	}

	public void setSDate(String date) {
		sDate = date;
	}

	public String getEDate() {
		return eDate;
	}

	public void setEDate(String date) {
		eDate = date;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMContractName() {
		return MContractName;
	}

	public void setMContractName(String contractName) {
		MContractName = contractName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getIntSum() {
		return intSum;
	}

	public void setIntSum(int intSum) {
		this.intSum = intSum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public IYXTypeManageService getTypemanageservice() {
		return typemanageservice;
	}

	public void setTypemanageservice(IYXTypeManageService typemanageservice) {
		this.typemanageservice = typemanageservice;
	}

	public String getMainProjectId() {
		return mainProjectId;
	}

	public void setMainProjectId(String mainProjectId) {
		this.mainProjectId = mainProjectId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public AssistanceSection getAs() {
		return as;
	}

	public void setAs(AssistanceSection as) {
		this.as = as;
	}

	public List<AssistanceSection> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<AssistanceSection> sectionList) {
		this.sectionList = sectionList;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public List<AssistanceSection> getAsectionList() {
		return asectionList;
	}

	public void setAsectionList(List<AssistanceSection> asectionList) {
		this.asectionList = asectionList;
	}

	public Long getPayInfoId() {
		return payInfoId;
	}

	public void setPayInfoId(Long payInfoId) {
		this.payInfoId = payInfoId;
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}

	public double getLastSum() {
		return lastSum;
	}

	public void setLastSum(double lastSum) {
		this.lastSum = lastSum;
	}


	public Long[] getAssistanceSectionId() {
		return assistanceSectionId;
	}

	public void setAssistanceSectionId(Long[] assistanceSectionId) {
		this.assistanceSectionId = assistanceSectionId;
	}

	public String getAsCreate() {
		return asCreate;
	}

	public void setAsCreate(String asCreate) {
		this.asCreate = asCreate;
	}
	public Long getAsContractId() {
		return asContractId;
	}

	public void setAsContractId(Long asContractId) {
		this.asContractId = asContractId;
	}

	public Double getUnallocateAmount() {
		return unallocateAmount;
	}

	public void setUnallocateAmount(Double unallocateAmount) {
		this.unallocateAmount = unallocateAmount;
	}

	public String getAssistanceNo() {
		return assistanceNo;
	}

	public void setAssistanceNo(String assistanceNo) {
		this.assistanceNo = assistanceNo;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public List<AssistancePayInfo> getPrepApplyPayList() {
		return prepApplyPayList;
	}

	public void setPrepApplyPayList(List<AssistancePayInfo> prepApplyPayList) {
		this.prepApplyPayList = prepApplyPayList;
	}

	public Long[] getPrepayIds() {
		return prepayIds;
	}

	public void setPrepayIds(Long[] prepayIds) {
		this.prepayIds = prepayIds;
	}

	public Double getEditablePrepAmount() {
		return editablePrepAmount;
	}

	public void setEditablePrepAmount(Double editablePrepAmount) {
		this.editablePrepAmount = editablePrepAmount;
	}

	public Double getThisRelationPrepAmount() {
		return thisRelationPrepAmount;
	}

	public void setThisRelationPrepAmount(Double thisRelationPrepAmount) {
		this.thisRelationPrepAmount = thisRelationPrepAmount;
	}

	public Double getThisApplyPayAmount() {
		return thisApplyPayAmount;
	}

	public void setThisApplyPayAmount(Double thisApplyPayAmount) {
		this.thisApplyPayAmount = thisApplyPayAmount;
	}

	public Double getSplitSectionAmount() {
		return splitSectionAmount;
	}

	public void setSplitSectionAmount(Double splitSectionAmount) {
		this.splitSectionAmount = splitSectionAmount;
	}

	public String getIsPrePay() {
		return isPrePay;
	}

	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}

	public Double getPrepPaySum() {
		return prepPaySum;
	}

	public void setPrepPaySum(Double prepPaySum) {
		this.prepPaySum = prepPaySum;
	}

	public IAssistancePayService getAssistancePayService() {
		return assistancePayService;
	}

	public void setAssistancePayService(IAssistancePayService assistancePayService) {
		this.assistancePayService = assistancePayService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Map<Long, AssistanceTicket> getAssistanceTicketMap() {
		return assistanceTicketMap;
	}

	public void setAssistanceTicketMap(
			Map<Long, AssistanceTicket> assistanceTicketMap) {
		this.assistanceTicketMap = assistanceTicketMap;
	}

}

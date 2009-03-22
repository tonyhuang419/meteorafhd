package com.baoz.yx.action.assistance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.impl.AssistancePayService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;
@Results({
	@Result(name = "enterSave", value = "/WEB-INF/jsp/assistance/newAssistanceTicket.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/assistance/editAssistanceTicket.jsp"),
	@Result(name = "enterRelationContract", value = "/WEB-INF/jsp/assistance/relationContract.jsp"),
	@Result(name = "success", type = ServletRedirectResult.class, value = "/assistance/ticketLeft.action?method=query"),
	@Result(name = "relationBack", value = "/WEB-INF/jsp/assistance/relationReturn.jsp"),
	@Result(name = "editBack", value = "/WEB-INF/jsp/assistance/editTicketReturn.jsp"),
	@Result(name = "back", value = "/WEB-INF/jsp/assistance/ticketReturn.jsp"),
	@Result(name = "backContractManager", value = "WEB-INF/jsp/assistance/ticketReturn.jsp")
	})

public class AssistanceTicketAction extends DispatchAction {
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	
	@Autowired
	@Qualifier("assistancePayService")
	private IAssistancePayService assistancePayService;
	
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	
	private AssistanceTicket at;
	private String ids;
	private List<AssistanceTicket> list;
	private Date ticketDate;
	private PageInfo info;
	private AssistanceContract ac ;
	private String ticketId;
	private String supplierName;
	private String supplierid;
	private String assistanceContractNo;
	private String assistanceContractName;
		
	private List<YXTypeManage> invoiceList ;
	private String sign;
	
	private String ticketNo;//发票号
	
	private Long assistanceConId;/////外协合同id
	
	private String ticketType;/////外协发票类型
	
	private Double ticketMoney;///////发票金额
	
	
	private Long fromContractManager;   // 从外协管理处开出的发票跳转参数
	
	private String checkHasBill;  //判断是否还能添加发票信息  如果不等于null  新增页面就只能显示收据类型
	
	@Override
	public String doDefault() throws Exception {
		logger.info("开票新增");
		//如果不等于null  新增页面就只能显示收据类型
		if(StringUtils.isNotBlank(checkHasBill)){
			List<YXTypeManage> invoiceType=typeManageService.getYXTypeManage(1004L);
			invoiceList = new ArrayList<YXTypeManage>();
			for (YXTypeManage typeManage : invoiceType) {
				if(typeManage.getTypeSmall().equals("4") ){
					invoiceList.add(typeManage);
				}
			}
		}else{
			invoiceList = typeManageService.getYXTypeManage(1004L);
		}
		if(assistanceConId!=null){
			ac =(AssistanceContract) service.load(AssistanceContract.class,assistanceConId);
			supplierid=ac.getSupplyId().toString();
			SupplierInfo supperinfo=(SupplierInfo) service.load(SupplierInfo.class,ac.getSupplyId());
			supplierName=supperinfo.getSupplierName();
		}
		return ENTER_SAVE;
	}
	public String saveAssistanceTicket() throws Exception {
		this.logger.info("新增发票");
		at.setPerson(UserUtils.getUser().getId());
		at.setCustomerId(Long.parseLong(supplierid));
		at.setTicket_Time(ticketDate);
		at.setIs_active("1");
		at.setIs_related("0");
		at.setDoneMoney(at.getMoney());
		at.setDoneTime(new Date());
		at.setIsFromSys(Boolean.FALSE);
		if (assistanceConId != null) {
			at.setContractId(assistanceConId);
			AssistanceContract contract = (AssistanceContract)service.load(AssistanceContract.class, assistanceConId);
			if(at.getType().equals("4"))
			{
				Double receiptAmount = contract.getReceiptAmount() == null ? 0.00 :contract.getReceiptAmount();
				contract.setReceiptAmount(receiptAmount+at.getMoney());
			}else{
				Double ticketMoney = contract.getTicketMoney() == null ? 0.00 :contract.getTicketMoney();
				contract.setTicketMoney(ticketMoney+at.getMoney());
			}
			at.setCustomerId(contract.getSupplyId());
			service.update(contract);
		}
		service.save(at);
		if (fromContractManager!=null&&fromContractManager == 1) {
			sign = "1";
			return "back";
		}
		else
		{
			return "back";
		}
	}
	
	public String checkPayNoNotExists()throws Exception{
		
		String hql = "select count(*) from AssistanceTicket at where at.num = '"+ticketNo+"' ";
		if(StringUtils.isNotBlank(ticketId)){
			hql += "and at.id != "+ticketId;
		}
		
		Number count = (Number) service.uniqueResult(hql);
		if(count!=null&&count.longValue()!=0){//如果count不为0表示存在相同的，如果为0表示不存在项目的
			this.renderText("false");
		}else{
			this.renderText("true");
		}
		
		return null;
	}
	
	public String checkTicketMoney()throws Exception{
		
		AssistanceContract contract = (AssistanceContract) service.load(AssistanceContract.class,assistanceConId);
		Double baseCompare = 0.00;
		if(StringUtils.equals(ticketType,"4")){
			baseCompare = contract.getReceiptAmount() == null ? 0.00 : contract.getReceiptAmount();
		}else{
			baseCompare = contract.getTicketMoney() == null ? 0.00 : contract.getTicketMoney();
		}
		if(StringUtils.isNotBlank(ticketId)){
			AssistanceTicket ticket = (AssistanceTicket)service.load(AssistanceTicket.class, Long.valueOf(ticketId));
			baseCompare -= ticket.getMoney();
		}
		
		if(baseCompare+ ticketMoney >contract.getContractMoney()){////如果原来的钱加上本次开票的金额大于合同金额。返回true，else 返回false
			this.renderText("{isOverAmount:true,amount:"+(contract.getContractMoney() - baseCompare)+"}");////true表示超过
		}else{
			this.renderText("{isOverAmount:false,amount:"+(contract.getContractMoney() - baseCompare)+"}");
		}
		
		return null;
	}
	
	public String editAssistanceTicket() throws Exception {
		this.logger.info("修改发票");
		String hql = "from AssistanceTicket at where at.id = "+ticketId;
		AssistanceTicket dbTicket = (AssistanceTicket)service.uniqueResult(hql);
		dbTicket.setNum(at.getNum());
		dbTicket.setType(at.getType());
		dbTicket.setTicket_Time(ticketDate);
		dbTicket.setRemark(at.getRemark());
		dbTicket.setMoney(at.getMoney());
		dbTicket.setDoneMoney(at.getMoney());
		if(supplierid!=null && !"".equals(supplierid)){
			dbTicket.setCustomerId(Long.parseLong(supplierid));
		}
		service.update(dbTicket);
		return "editBack";
	}
	public String enterUpdate() throws Exception {
		this.logger.info("edit ticket....");
		////查询发票类型列表
		invoiceList = typeManageService.getYXTypeManage(1004L);
		 ///load出发票主题信息
		at = (AssistanceTicket) service.load(AssistanceTicket.class,Long.valueOf(ticketId));
		String acHql = "select ac from AssistanceContract ac,AssistanceTicket ticket where ticket.contractId = ac.id and ticket.id = ?";
		ac = (AssistanceContract)service.uniqueResult(acHql, Long.valueOf(ticketId));
		assistanceConId = ac.getId();
		SupplierInfo supply = assistanceService.getSupplierByAssistanceContractId(ac.getId());
		supplierid = supply.getSupplierid().toString();
		supplierName = supply.getSupplierName();
		return "enterUpdate";
	}
	public String del() throws Exception{
		this.logger.info("删除发票");
		String[] ticketArr = ids.split(",");
		Long[] ticketIds = new Long[ticketArr.length];
		for (int i = 0; i < ticketArr.length; i++) {
			ticketIds[i] = Long.valueOf(ticketArr[i]);
		}
		assistancePayService.deleteAssistanceTicket(ticketIds);
		return "success";
	}
	public String enterRelationContract() throws Exception{
		this.logger.info("选择外协合同");
		StringBuffer sb = new StringBuffer();
		/**
		 * 前提限制条件是：外协合同状态为确认通过。外协合同的付款申请状态为非全额收款
		 */
		sb.append("select ac, si.supplierName from AssistanceContract ac, SupplierInfo si where si.supplierid = ac.supplyId and ac.is_active = '1' and ac.assistanceContractType = '2' and ac.assistanceType != '2' ");
		UserDetail user = UserUtils.getUserDetail();
		String expId = null;
		String groupId = null;
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId() + "";
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		
		if(expId!=null&&!"".equals(expId))
		{
			sb.append(" and ac.employeeId ="+expId+" ");
		}
		if(groupId!=null&&!"".equals(groupId)){
			sb.append(" and ac.employeeId in (select e.id from Employee e where e.position in (select o.id from OrganizationTree o where o.organizationCode like '"+groupId+"%')))");
		}
		if(StringUtils.isNotBlank(assistanceContractName)){
			sb.append(" and ac.assistanceName like '%"+assistanceContractName+"%'");
		}
		if(StringUtils.isNotBlank(assistanceContractNo)){
			
			sb.append(" and lower(ac.assistanceId) = '"+StringUtils.lowerCase(assistanceContractNo)+"' ");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		return "enterRelationContract";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) { 
		this.service = service;
	}
	public AssistanceTicket getAt() {
		return at;
	}
	public void setAt(AssistanceTicket at) {
		this.at = at;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List getList() {
		return list;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
	public void setList(List<AssistanceTicket> list) {
		this.list = list;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	public AssistanceContract getAc() {
		return ac;
	}
	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public Date getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<YXTypeManage> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public Long getAssistanceConId() {
		return assistanceConId;
	}
	public void setAssistanceConId(Long assistanceConId) {
		this.assistanceConId = assistanceConId;
	}
	public Long getFromContractManager() {
		return fromContractManager;
	}
	public void setFromContractManager(Long fromContractManager) {
		this.fromContractManager = fromContractManager;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public IAssistancePayService getAssistancePayService() {
		return assistancePayService;
	}
	public void setAssistancePayService(IAssistancePayService assistancePayService) {
		this.assistancePayService = assistancePayService;
	}
	public String getAssistanceContractNo() {
		return assistanceContractNo;
	}
	public void setAssistanceContractNo(String assistanceContractNo) {
		this.assistanceContractNo = assistanceContractNo;
	}
	public String getAssistanceContractName() {
		return assistanceContractName;
	}
	public void setAssistanceContractName(String assistanceContractName) {
		this.assistanceContractName = assistanceContractName;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public Double getTicketMoney() {
		return ticketMoney;
	}
	public void setTicketMoney(Double ticketMoney) {
		this.ticketMoney = ticketMoney;
	}
	public String getCheckHasBill() {
		return checkHasBill;
	}
	public void setCheckHasBill(String checkHasBill) {
		this.checkHasBill = checkHasBill;
	}

}

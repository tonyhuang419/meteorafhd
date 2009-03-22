package com.baoz.yx.action.assistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SectionAndPayInfoRelation;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.TicketCollection;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
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

@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/applyLeft.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/assistance/editApply.jsp"),
	@Result(name = "enterTicket", value = "/WEB-INF/jsp/assistance/applyTicket.jsp"),
	@Result(name = "return", value = "/WEB-INF/jsp/assistance/applyReturn.jsp"),
	@Result(name = "edit", value = "/WEB-INF/jsp/assistance/editApplyReturn.jsp"),
	@Result(name = "saveApplyBack", value = "/WEB-INF/jsp/assistance/saveAssistanceApplyReturn.jsp"),
	@Result(name = "passApplyBack", value = "/WEB-INF/jsp/assistance/passAssistanceApplyReturn.jsp"),
	@Result(name = "query", type = ServletRedirectResult.class, value = "/assistance/apply.action?method=query"),
	@Result(name = "unchain", value = "/WEB-INF/jsp/assistance/unchain.jsp"),
	@Result(name = "detail", value = "/WEB-INF/jsp/assistance/applyDetail.jsp"),
	@Result(name = "showUpdate", value = "/WEB-INF/jsp/assistance/addBeforehandPay.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/applyList.jsp")})
public class ApplyAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
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
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typemanageservice;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	private AssistancePayInfo pi ;
	private AssistancePayInfo sup ;
	private PageInfo info ;
	private String payState;///申请单状态
	private String startDate;//申请日期
	private String endDate;///申请日期
	private Long supplierid;///外协供应商id
	private String ids;
	private Long applyInfoId;
	private Double sum;
	private AssistanceContract ac;
	private AssistanceTicket at;
	private SupplierInfo s;
	private ContractMainInfo cmi;
	private ContractItemMaininfo c;
	private List<TicketCollection> tc;
	private List<AssistanceTicket> tList;
	private List<AssistancePayInfo> pList;
	private Double balance;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	private String id;
	private String pid;
	private String saveId;
	private String expId;//销售员id
	private String groupId;//销售员的部门
	private String userName;
	private String groupName;
	private String supplierName; //供应商名称
	private String updateSel; //是否是从修改页面来
	private List<AssistanceSection> sectionList;
	
	
	private List<AssistanceSection> asectionList;//外协合同阶段阶段表
	
	private Long[] assistanceSectionId;//外协阶段id的数组
	
	private Double prepPaySum;///////关联的预付款金额
	
	
	private List<Assistance> assistanceList;//////修改外协付款申请的时候从数据库中查询出来的该付款申请下面的付款申请和发票的关联表信息
	
	
	private Double thisRelationPrepAmount;/////本次关联的预付款总额
	
	private String isPrePay;////是否预付款
	
	private Long 				sectionId;          //阶段id
	
	private Double splitSectionAmount;////需要拆分的阶段金额
	
	private Long ticketId;////发票id
	
	private String assistanceConNo;  //外协合同好
	
	private ProcessResult result;///操作返回值
	
	private Map<Long, AssistanceTicket> assistanceTicketMap=new HashMap<Long, AssistanceTicket>();////记录已经关联过的发票信息
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"applyParameters",this,
			new String[]{"payState","supplierid","startDate","endDate","expId","groupId","assistanceConNo"});
	
	

	@Override
	public String doDefault() throws Exception {
		this.logger.info("进入付款申请管理");
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "success";
	}
	public String query() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sb = new StringBuffer();
		sb.append("select pi, " +
				"(select ac.assistanceId from AssistanceContract ac where ac.id = pi.assistanceId )," +
				"(select ac.assistanceName from AssistanceContract ac where ac.id = pi.assistanceId )," +
				" si.supplierName,emp.name  " +
				"from AssistancePayInfo pi, SupplierInfo si , Employee emp " +
				" where pi.is_active='1' " +
				"and pi.supplyId = si.supplierid " +
				"and emp.id = pi.employeeId ");
		if(payState!=null && !"".equals(payState)){
			sb.append(" and pi.payState = '"+payState+"'");
		}
		if(supplierid != null){
			sb.append(" and si.supplierid = ").append( supplierid).append(" ");
		}
		if(startDate!=null && !"".equals(startDate)){
			sb.append(" and to_date('"+startDate+"', 'yyyy-MM-dd') <= pi.applyDate  ");
		}
		if(endDate!=null && !"".equals(endDate)){
			sb.append(" and trunc(pi.applyDate,'dd') <= to_date('"+endDate+"', 'yyyy-MM-dd') ");
		}
		UserDetail user = UserUtils.getUserDetail();
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId() + "";
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		
		if(expId!=null&&!"".equals(expId))
		{
			sb.append(" and emp.id = " + expId);
		}
		if(groupId!=null&&!"".equals(groupId)){
			sb.append(" and emp.position in (select tr.id from OrganizationTree tr where tr.organizationCode like '"
					+ groupId + "%') ");
		}		
		//合同号查询
		if(StringUtils.isNotBlank(assistanceConNo)){
			sb.append(" and exists (select 1 from AssistanceContract ac where ac.id = pi.assistanceId and ac.assistanceId = '"+assistanceConNo+"')");
		}
		sb.append("order by decode(pi.payState,'0','0','1','1','2','3','3',pi.payState),pi.id DESC");
		info = ParameterUtils.preparePageInfo(info, "applyPageInfo");
		info = queryService.listQueryResult(SqlUtils.getCountSql(sb.toString()),sb.toString(), info);
		return "queryList";
	}
	public String enterUpdate(){
		this.logger.info("进入修改付款申请");
		showApplyPayInfoMessage();
		return "enterUpdate";
	}
	private void showApplyPayInfoMessage(){
		if(applyInfoId==null){
			applyInfoId = Long.parseLong(ids);
		}
		pi = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, applyInfoId);
		// 查询外协合同
		ac = assistanceService.getContactByPayInfoId(pi.getId());
		Employee emp = (Employee)commonService.load(Employee.class, pi.getEmployeeId());
		userName = emp.getName();
		groupName =userService.getDepartment(emp).getOrganizationName();
		// 查询主题合同
		cmi = (ContractMainInfo) commonService.load(ContractMainInfo.class, ac.getContractId());
		// 查询供应商信息
		s = assistanceService.getSupplierByAssistanceContractId(ac.getId());
		// 查询支付履历
		pList = assistanceService.getPayInfoRecordNotContained(pi.getId());
		/**
		 * 发票和付款申请的关联表
		 */
		assistanceList = getSessionAssistanceList();
		assistanceList.clear();
		List<Assistance> existAssistance = commonService.list("from Assistance where assistancePayId = "+ applyInfoId);
		assistanceList.addAll(existAssistance);
		if (ac.getTicketMoney() == null) {
			ac.setTicketMoney(0.0);
		}
		if (ac.getMainProjectId() != null) {
			String pHql = "from ContractItemMaininfo c where c.conItemId = '"
					+ ac.getMainProjectId() + "'";
			c = (ContractItemMaininfo) commonService.uniqueResult(pHql);
		}
	
		asectionList = assistanceService.getAssistanceSectionByContractId(ac.getId());
		if(assistanceSectionId == null||assistanceSectionId.length == 0 ){
			assistanceSectionId = assistanceService.getcheckedSectionByPayInfoId(pi.getId());
		}
		//ActionContext.getContext().getSession().remove("prepApplyPayLists");
		////查询处已经关联过的预付款信息
		setSessionPrepApplyPayList(assistanceService.getRelationPayInfoByPayInfo(pi.getId()));
		
		//////计算将要关联的预付款的总额
	    prepPaySum	=getSumPrepAmount(getSessionPrepApplyPayList());
		
	}
	private void cleanSessionValue(){
		if(ActionContext.getContext().getSession().containsKey("assistance_apply_list")){
			ActionContext.getContext().getSession().remove("ActionContext.getContext().getSession()");
		}
		if(ActionContext.getContext().getSession().containsKey("prepApplyPayLists")){
			ActionContext.getContext().getSession().remove("prepApplyPayLists");
		}
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
	
	private List<AssistancePayInfo> getSessionPrepApplyPayList(){
		List<AssistancePayInfo> opList = (List<AssistancePayInfo>)ActionContext.getContext().getSession().get("prepApplyPayLists");
		if(opList==null){
			opList = new ArrayList<AssistancePayInfo>();
		}
		ActionContext.getContext().getSession().put("prepApplyPayLists",opList);
		return opList;
	}
	private void setSessionPrepApplyPayList(List<AssistancePayInfo> payInfoList){
		List<AssistancePayInfo> opList = (List<AssistancePayInfo>)ActionContext.getContext().getSession().get("prepApplyPayLists");
		if(opList==null){
			opList = new ArrayList<AssistancePayInfo>();
		}
		opList.clear();
		opList.addAll(payInfoList);
		ActionContext.getContext().getSession().put("prepApplyPayLists",opList);
	}
	
	public String pass()throws Exception{
		this.logger.info("确认提交");
		updateAssistanceApply("1");
		return query();
	}
	public String delete() throws Exception{
		this.logger.info("删除申请");
		String[] idsArray=StringUtils.split(ids,",");
		Long[] payInfoIds = new Long[idsArray.length];
		for (int i = 0; i < idsArray.length; i++) {
			payInfoIds[i] = Long.valueOf(idsArray[i]);
		}
		result = assistancePayService.deleteAssistancePayInfo(payInfoIds[0]);
		return query();
	}
	
	private void deleteRelation(Long payInfoId){
		String sectionHql = "select relation from SectionAndPayInfoRelation relation,AssistancePayInfo py where relation.payInfoId = py.id and py.id = ?";
		List<SectionAndPayInfoRelation> sectionAllList = commonService.list(sectionHql, payInfoId);
		for (SectionAndPayInfoRelation relation : sectionAllList) {
			commonService.delete(relation);
		}
	}
	public String enterTicket() throws Exception{
		this.logger.info("进入关联发票");
		//去掉已选择的发票
		List<Assistance> assList =  getSessionAssistanceList();
		for (Assistance assistance : assList) {
			AssistanceTicket ticket = assistance.getTicket();
			assistanceTicketMap.put(ticket.getId(),ticket);
		}
		String hql = "select at,s.supplierName from AssistanceTicket at,SupplierInfo s,AssistanceContract ac " +
		" where at.is_active='1' " +
		" and at.customerId = s.supplierid and ac.id = at.contractId and ac.id="
		+ ids ;
		info = queryService.listQueryResult(hql, info);
		// 查询外协合同
		ac = (AssistanceContract) commonService.load(AssistanceContract.class, Long.valueOf(ids));
		List<AssistancePayInfo> prepList = getSessionPrepApplyPayList();
		thisRelationPrepAmount = getSumPrepAmount(prepList);
		return "enterTicket";
	}
	public String doTicket() throws Exception{
		this.logger.info("关联发票");
		pi = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, Long.valueOf(pid));
		applyInfoId = pi.getId();
		List<Assistance> assistanceList = getSessionAssistanceList();
		
		List<AssistancePayInfo> prepPayInfoList = getSessionPrepApplyPayList();
		//////该方法在此处还不能删除，主要是要用到父页面的显示
		for (TicketCollection o : tc) {
			if (o.getId() != null) {
				Assistance assistance = new Assistance();
				Long tId = o.getTicketId();
				String hql = "from AssistanceTicket at where at.id = " + tId;
				at = (AssistanceTicket) commonService.uniqueResult(hql);
				assistance.setTicket(at);
				assistance.setAssistancePayId(pi.getId());
				assistancePayService.doTicketByUpdateApply(assistance, pi, prepPayInfoList);
				assistanceList.add(assistance);
				id = at.getContractId().toString();
			}
		}
		///建立票和付款申请之间的关联
		
//		for(TicketCollection o : tc){
//			if(o.getId()!=null){
//				if(o.getMoney()==null){
//					o.setMoney(0.0);
//				}
//				Assistance assistance = new Assistance();
//				assistance.setConMoney(o.getMoney());
//				Long tId = o.getTicketId();
//				String hql = "from AssistanceTicket at where at.id = "+tId;
//				at = (AssistanceTicket)commonService.uniqueResult(hql);
//				assistance.setTicket(at);
//				assistance.setAssistancePayId(pi.getId());
//				assistanceList.add(assistance);
//				this.updateTicketFeeAndSaveAssistance(assistance,pi);
//				id = at.getContractId().toString();
//			}
//		}
		return "return";
	}
	
	private List<Assistance> getSessionAssistanceList(){
		List<Assistance> assistanceList = (List<Assistance>)ActionContext.getContext().getSession().get("assistance_apply_list");
		if(assistanceList==null){
			assistanceList = new ArrayList<Assistance>();
		}
		ActionContext.getContext().getSession().put("assistance_apply_list", assistanceList);
		return assistanceList;
	}
	private void setSessionAssistanceList(List<Assistance> list){
		List<Assistance> assistanceList = (List<Assistance>)ActionContext.getContext().getSession().get("assistance_apply_list");
		if(assistanceList==null){
			assistanceList = new ArrayList<Assistance>();
		}
		assistanceList.addAll(list);
		ActionContext.getContext().getSession().put("assistance_apply_list", assistanceList);
	}
	
	public String relationTicket(){
		showApplyPayInfoMessage();
		return "enterUpdate";
	}
	public String saveAssistanceApply()throws Exception{
		this.logger.info("保存付款申请");
		pi.setId(applyInfoId);
		AssistancePayInfo ap = this.updateOneAssistanceApply(pi);//保存
		AssistancePayInfo opAp = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, ap.getId());
		if(opAp.getPayState()!= null){
			opAp.setPayState("0");
			commonService.update(opAp);
		}
		return "saveApplyBack";
	}
	/**
	 * 确认提交
	 * @return
	 * @throws Exception
	 */
	public String passAssistanceApply()throws Exception{
		this.logger.info("提交确认付款申请");
		Boolean flag = updateAssistanceApply("1");
		
		if(flag){
			return enterUpdate();
		}
		return "passApplyBack";
	}
	/***
	 * 申请提交
	 * @param payState
	 * @return
	 * @throws Exception
	 */
	private Boolean updateAssistanceApply(String payState)throws Exception {
		////如果applyInfoId没有赋值的话，说明是整体提交。因此要用循环遍历。
		if(applyInfoId==null && ids!=null){
			String[] opId=StringUtils.split(ids, ",");
			if(opId!=null&&opId.length>0){
				for(int k=0;k<opId.length;k++){
					Boolean flag = applySubmit(Long.valueOf(opId[k]));
					if(flag){
						return flag;
					}
				}
			}
		}else{////如果applyInfo有值说明是单独提交不要循环
			pi.setId(applyInfoId);
			AssistancePayInfo ap = this.updateOneAssistanceApply(pi);
			return applySubmit(applyInfoId);
	   }
		return false;
	}
	
	private Boolean applySubmit(Long payInfoId){
		AssistancePayInfo api = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, payInfoId);
		Object[] obj = assistanceService.isSureSubmitApply(payInfoId);
		Boolean flag = (Boolean)obj[0];
		if(flag){///表示有错误
			AssistancePayInfo payInfo = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, (Long)(obj[1]));
			ActionContext.getContext().put("notSure", flag);
			ActionContext.getContext().put("payInfoCode", payInfo.getApplyInfoCode());
			ActionContext.getContext().put("returnCode", (String)(obj[2]));
			return flag;
		}
		api.setPayState("1");
		commonService.update(api);
		return false;
	}
	
	
	private AssistancePayInfo updateOneAssistanceApply(Long appId) throws Exception {
		String pHql = "from AssistancePayInfo pi where pi.id = "+appId;
		AssistancePayInfo dbPi = (AssistancePayInfo)commonService.uniqueResult(pHql);
		String aHql = "from AssistanceContract ac where ac.id = " + dbPi.getAssistanceId();
		ac = (AssistanceContract)commonService.uniqueResult(aHql);
		/////////////////////
		if(pi!=null){
			dbPi.setReceivNum(pi.getReceivNum());
			dbPi.setRemark(pi.getRemark());
			dbPi.setAssignmentId(pi.getAssignmentId());
			dbPi.setInfo(pi.getInfo());
			dbPi.setAssistanceId(ac.getId());
		}
		dbPi.setIs_active("1");
		commonService.update(dbPi);
		
		////先删除原来的关联。然后再重新保存现在的关联
		deleteRelation(dbPi.getId());
		/////保存关联表
		for (int i = 0; i < assistanceSectionId.length; i++) {
			SectionAndPayInfoRelation relation = new SectionAndPayInfoRelation();
			relation.setAssistanceContractId(dbPi.getAssistanceId());///外协系统号
			relation.setPayInfoId(dbPi.getId());
			relation.setSectionId(assistanceSectionId[i]);
			commonService.save(relation);
		}
		return dbPi;
	}
	
	private AssistancePayInfo updateOneAssistanceApply(AssistancePayInfo payInfo)throws Exception{
		/**
		 * 需要修改的东西有：
		 * 1，任务号，2接受号，3，是否预付，4，付款事项说明5，备注，6，本次申请金额
		 */
		////计算申请金额	
		Double sum = assistancePayService.getRelationSectionAmountBySectionId(assistanceSectionId);
		payInfo.setPayNum(sum);
		AssistancePayInfo assistancePayInfo = assistancePayService.updateAssistancePayInfo(payInfo);
		deleteRelation(assistancePayInfo.getId());
		/////保存关联表
		for (int i = 0; i < assistanceSectionId.length; i++) {
			SectionAndPayInfoRelation relation = new SectionAndPayInfoRelation();
			relation.setAssistanceContractId(assistancePayInfo.getAssistanceId());///外协系统号
			relation.setPayInfoId(assistancePayInfo.getId());
			relation.setSectionId(assistanceSectionId[i]);
			commonService.save(relation);
		}
		return assistancePayInfo;
	}
	
	public String splitSection()throws Exception{
		assistanceService.splitSectionAmount(sectionId, splitSectionAmount);
		showApplyPayInfoMessage();
		return "enterUpdate";
	}
	
	
	public String unchainRelation(){
		this.logger.info("解除关联");
		///////////////////////////////////////获得外协合同号
		String hql = "from AssistanceTicket at where at.id = "+ticketId;
		at = (AssistanceTicket)commonService.uniqueResult(hql);
		////////////////////////////////////////
		List<Assistance> assistanceList = getSessionAssistanceList();
		ListIterator<Assistance> listIte = assistanceList.listIterator();
		while (listIte.hasNext()) {
			Assistance assistance = (Assistance) listIte.next();
			if(assistance.getTicket().getId()== ticketId.longValue()){
				listIte.remove();
				assistancePayService.unchainRelation(assistance);
				//this.updateTicketFeeAndDeleteAssistance(assistance);
			}
		}
		showApplyPayInfoMessage();
		return "enterUpdate";
	}
	
	public String detail(){
		this.logger.info("显示明细");
		pi = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, applyInfoId);
		Employee e = (Employee)commonService.load(Employee.class, pi.getEmployeeId());
		userName = e.getName();
		groupName = userService.getDepartment(e).getOrganizationName();
		Long assistanceContractId = pi.getAssistanceId();
		//查询外协合同
		String hql = "from AssistanceContract ac where ac.id="+assistanceContractId;
		ac = (AssistanceContract)commonService.uniqueResult(hql);	
		//查询供应商信息
		String sId = ac.getSupplyId().toString();
		if(sId!=null && !"".equals(sId)){
			String sHql = "from SupplierInfo s where s.supplierid="+sId;
			s = (SupplierInfo)commonService.uniqueResult(sHql);
		}
		//查询主题合同
		if(ac.getContractId()!=null){
			String cId = ac.getContractId().toString();
			if(cId!=null && !"".equals(cId)){
				String cHql = "from ContractMainInfo cm where cm.conMainInfoSid='"+cId+"'";
				cmi = (ContractMainInfo)commonService.uniqueResult(cHql);
			}
		}
		if(ac.getMainProjectId()!=null){
			String cHql = "from ContractItemMaininfo c where c.conItemId = '"+ac.getMainProjectId()+"'";
			c = (ContractItemMaininfo)commonService.uniqueResult(cHql);
		}
		// 查询支付履历
		pList = pList = assistanceService.getPayInfoRecordNotContained(pi.getId());
		//查询已关联发票信息
		String atHql = "select a from Assistance a, AssistancePayInfo pi, AssistanceTicket at where a.assistancePayId=pi.id and a.ticket=at.id and pi.id = "+applyInfoId;
		info = queryService.listQueryResult(atHql, info);
		/// 查询阶段信息
		asectionList = assistanceService.getAssistanceSEctionByPayInfoId(pi.getId());
		
		return "detail";
	}
	
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public AssistancePayInfo getPi() {
		return pi;
	}
	public void setPi(AssistancePayInfo pi) {
		this.pi = pi;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public AssistanceContract getAc() {
		return ac;
	}
	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}
	public AssistanceTicket getAt() {
		return at;
	}
	public void setAt(AssistanceTicket at) {
		this.at = at;
	}
	public SupplierInfo getS() {
		return s;
	}
	public void setS(SupplierInfo s) {
		this.s = s;
	}
	public ContractMainInfo getCmi() {
		return cmi;
	}
	public void setCmi(ContractMainInfo cmi) {
		this.cmi = cmi;
	}
	public ContractItemMaininfo getC() {
		return c;
	}
	public void setC(ContractItemMaininfo c) {
		this.c = c;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
	public List<TicketCollection> getTc() {
		return tc;
	}
	public void setTc(List<TicketCollection> tc) {
		this.tc = tc;
	}
	public List<AssistanceTicket> getTList() {
		return tList;
	}
	public void setTList(List<AssistanceTicket> list) {
		tList = list;
	}
	public List<AssistancePayInfo> getPList() {
		return pList;
	}
	public void setPList(List<AssistancePayInfo> list) {
		pList = list;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public List<Object> getListExp() {
		return listExp;
	}
	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSaveId() {
		return saveId;
	}
	public void setSaveId(String saveId) {
		this.saveId = saveId;
	}
	public Long getApplyInfoId() {
		return applyInfoId;
	}
	public void setApplyInfoId(Long applyInfoId) {
		this.applyInfoId = applyInfoId;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public IYXTypeManageService getTypemanageservice() {
		return typemanageservice;
	}
	public void setTypemanageservice(IYXTypeManageService typemanageservice) {
		this.typemanageservice = typemanageservice;
	}
	public List<AssistanceSection> getAsectionList() {
		return asectionList;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public List<AssistanceSection> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<AssistanceSection> sectionList) {
		this.sectionList = sectionList;
	}
	public Long[] getAssistanceSectionId() {
		return assistanceSectionId;
	}
	public void setAssistanceSectionId(Long[] assistanceSectionId) {
		this.assistanceSectionId = assistanceSectionId;
	}
	public void setAsectionList(List<AssistanceSection> asectionList) {
		this.asectionList = asectionList;
	}
	public AssistancePayInfo getSup() {
		return sup;
	}
	public void setSup(AssistancePayInfo sup) {
		this.sup = sup;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getUpdateSel() {
		return updateSel;
	}
	public void setUpdateSel(String updateSel) {
		this.updateSel = updateSel;
	}
	public Double getPrepPaySum() {
		return prepPaySum;
	}
	public void setPrepPaySum(Double prepPaySum) {
		this.prepPaySum = prepPaySum;
	}
	public void setAssistanceList(List<Assistance> assistanceList) {
		this.assistanceList = assistanceList;
	}
	public List<Assistance> getAssistanceList() {
		return assistanceList;
	}
	public Double getThisRelationPrepAmount() {
		return thisRelationPrepAmount;
	}
	public void setThisRelationPrepAmount(Double thisRelationPrepAmount) {
		this.thisRelationPrepAmount = thisRelationPrepAmount;
	}
	public String getIsPrePay() {
		return isPrePay;
	}
	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}
	public IAssistancePayService getAssistancePayService() {
		return assistancePayService;
	}
	public void setAssistancePayService(IAssistancePayService assistancePayService) {
		this.assistancePayService = assistancePayService;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Double getSplitSectionAmount() {
		return splitSectionAmount;
	}
	public void setSplitSectionAmount(Double splitSectionAmount) {
		this.splitSectionAmount = splitSectionAmount;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}
	public String getExpId() {
		return expId;
	}
	public void setExpId(String expId) {
		this.expId = expId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public String getAssistanceConNo() {
		return assistanceConNo;
	}
	public void setAssistanceConNo(String assistanceConNo) {
		this.assistanceConNo = assistanceConNo;
	}
	public ProcessResult getResult() {
		return result;
	}
	public void setResult(ProcessResult result) {
		this.result = result;
	}
	
}

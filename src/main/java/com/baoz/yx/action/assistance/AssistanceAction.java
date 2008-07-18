package com.baoz.yx.action.assistance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.TicketCollection;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;
@Results({
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
	@Result(name = "passBack", value = "/WEB-INF/jsp/assistance/passReturn.jsp"),
	@Result(name = "saveApplyBack", value = "/WEB-INF/jsp/assistance/saveApplyReturn.jsp"),
	@Result(name = "passApplyBack", value = "/WEB-INF/jsp/assistance/passApplyReturn.jsp"),
	@Result(name = "editBack", value = "/WEB-INF/jsp/assistance/editReturn.jsp"),
	@Result(name = "enterTicket", value = "/WEB-INF/jsp/assistance/assistanceTicket.jsp")})
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
	private List<Object> listlm;
	private List<Object> listc;
	private AssistanceContract a;
	private String stateId;
	private String mix;
	private String max;
	private String start;
	private String end;
	private PageInfo info;
	private String ids;
	private List list;
	private AssistanceContract ac;
	private ServletRequest request;
	private ContractItemMaininfo  c;
	private ContractMainInfo cm ;
	private SupplierInfo s;
	private String id;
	private AssistanceTicket at ;
	private List<TicketCollection> tc;
	private Assistance assistance;
	private AssistancePayInfo pi;
	private Long ticketId;
	private List<Assistance> tList;
	private List<AssistancePayInfo> pList;
	private Double sum;
	private Double balance;
	private Long assistanceContractId;
	private List<ContractItemMaininfo> projectList;
	private String userName;
	private String sDate;
	private String eDate;
	private String supplierName;
	private String MContractName;
	@Override
	public String doDefault() throws Exception {
		logger.info("外协合同新增");
		// 得到申购人的id，查询出他所在部门(先固定写两个)
		String hql = "from ApplyMessage am where am.affirmState = '4'";
		String hqlc = "from YXClientCode cc where cc.id not in(0) and cc.is_active!=0";
		String pHql = "from ContractItemMaininfo";
		listlm = systemService.listA(hql);
		listc = systemService.listB(hqlc);// 显示所有客户
		projectList = service.list(pHql);
		userName = UserUtils.getUser().getName();		
		return ENTER_SAVE;
	}	
	
	public String query()throws Exception {
		logger.info("查询");
		String ahql = "from AssistanceContract ac where "+start+"< ac.contractDate <"+end+"and "+mix+"< ac.contractMoney <"+max;
		info = queryService.listQueryResult(ahql, info);
		List queryList = (List)info.getResult();
		list = assistanceService.queryAssistanceContract(queryList);
		return "queryList";
	}
	
	public String saveAssistance() throws Exception {
		this.logger.info("保存外协合同");
		String at = "0";
		ac.setAssistanceContractType(at);
		ac.setIs_active("1");
		ac.setContractDate(DateUtil.parse(sDate, "yy-MM-dd"));
		ac.setEndDate(DateUtil.parse(eDate, "yy-MM-dd"));
		service.save(ac);
		return "saveBack";
	}
	
	public String saveAssistance2() throws Exception {
		this.logger.info("确认提交");
		String at = "1";
		ac.setAssistanceContractType(at);
		ac.setIs_active("1");
		ac.setContractDate(DateUtil.parse(sDate, "yy-MM-dd"));
		ac.setEndDate(DateUtil.parse(eDate, "yy-MM-dd"));
		service.save(ac);
		return "passBack";
	}
	
	public String verifyState() throws Exception{
		this.logger.info("提交确认");
		String id[] = stateId.split(",");
		int a = assistanceService.updateState(id);
		if (a > 0)
			return "passBack";
		else {
			logger.info("更新状态操作不成功！");
			return "passBack";
		}
	}
	
	public String verifyState2() throws Exception{
		this.logger.info("确认通过");
		String id[] = stateId.split(",");
		int a = assistanceService.updateState2(id);
		if (a > 0)
			return "affirm";
		else {
			logger.info("更新状态操作不成功！");
			return "success";
		}
	}
	
	public String back() throws Exception{
		this.logger.info("确认退回");
		String id[] = stateId.split(",");
		int a = assistanceService.back(id);
		if (a > 0)
			return "list";
		else {
			logger.info("更新状态操作不成功！");
			return "success";
		}
	}
	
	public String enterUpdate() throws Exception{
		this.logger.info("进入修改");
		//String hql = "select ac, si.supplierName from AssistanceContract ac, SupplierInfo si where ac.is_active='1' and ac.supplyId=si.supplierid and ac.assistanceContractType='0' and ac.id="+ids;
		String hql = "from AssistanceContract ac where ac.is_active='1' and ac.id="+ids;
		ac = (AssistanceContract)service.uniqueResult(hql);
		String sHql = "from SupplierInfo si where si.supplierid="+ac.getSupplyId();
		SupplierInfo si = (SupplierInfo)service.uniqueResult(sHql);
		supplierName = si.getSupplierName();
		if(ac.getContractId()!=null && !"".equals(ac.getContractId())){
			String cHql = "from ContractMainInfo c where c.conMainInfoSid="+ac.getContractId();
			ContractMainInfo c = (ContractMainInfo)service.uniqueResult(cHql);
			if(c!=null){
				MContractName = c.getConName();
			}
		}
		return "edit";
	}
	
	public String editAssistance() throws Exception{
		this.logger.info("保存合同");
		String hql = "from AssistanceContract ac where ac.id = "+ac.getId();
		AssistanceContract acUpdate = (AssistanceContract)service.uniqueResult(hql);
		acUpdate.setAssistanceName(ac.getAssistanceName());
		acUpdate.setContractId(ac.getContractId());
		acUpdate.setMainProjectId(ac.getMainProjectId());
		acUpdate.setMainProjectName(ac.getMainProjectName());
		acUpdate.setContractMoney(ac.getContractMoney());
		acUpdate.setContractDate(ac.getContractDate());
		acUpdate.setEndDate(ac.getEndDate());
		acUpdate.setContractContent(ac.getContractContent());
		acUpdate.setAssistanceName(ac.getAssistanceName());
		service.update(acUpdate);
		return "editBack";
	}
	
	public String passAssistanceContract() throws Exception{
		if(ac.getId()!=null && !"".equals(ac.getId())){
			String hql = "from AssistanceContract ac where ac.id = "+ac.getId();
			ac = (AssistanceContract)service.uniqueResult(hql);
			Date d = new Date();
			ac.setUpdateBy(d);
			ac.setAssistanceType("1");
			service.update(ac);
		}else service.save(ac);
		return "success";
	}
	
	public String del() throws Exception{
		this.logger.info("删除合同");
		String hql=" update AssistanceContract obj set obj.is_active=0 ";
		int a = systemService.deleteChose(ids, hql);
		if (a > 0) {
			return "list";
		} else {
			logger.info("删除操作不成功！");
			return "list";
		}
	}
	
	public String enterApply() throws Exception{
		this.logger.info("进入付款申请");
		//查询外协合同
		String hql = "from AssistanceContract ac where ac.id="+ids;
		ac = (AssistanceContract)service.uniqueResult(hql);	
		String pId = ac.getMainProjectId();
		//查询主题项目
		if(pId!=null && !"".equals(pId)){
			String pHql = "from ContractItemMaininfo c where c.conItemMinfoSid="+pId;
			c = (ContractItemMaininfo)service.uniqueResult(pHql);
			//查询主题合同
			String cId = c.getConItemId();
			if(cId!=null && !"".equals(cId)){
				String cHql = "from ContractMainInfo cm where cm.conId='"+cId+"'";
				cm = (ContractMainInfo)service.uniqueResult(cHql);
				//查询供应商信息
				String sId = ac.getSupplyId().toString();
				if(sId!=null && !"".equals(sId)){
				String sHql = "from SupplierInfo s where s.supplierid="+sId;
				s = (SupplierInfo)service.uniqueResult(sHql);
				//查询发票收款金额
				String tHql = "from AssistanceTicket at where at.contractId = "+ac.getId();
				//tList = null;
				getAssistanceList().clear();
				//查询支付履历
				String piHql = "from AssistancePayInfo pi where pi.assistanceId = "+ac.getId();
				pList = service.list(piHql);
				}
			}
		}
		return "enterApply";
	}
	
	public String enterTicket() throws Exception{
		this.logger.info("进入关联发票");
		String hql = "from AssistanceTicket at where at.is_active='1' and exists(select ac.id from AssistanceContract ac where ac.id = at.contractId and ac.id="+id+")";
		info = queryService.listQueryResult(hql, info);
		List queryList = (List)info.getResult();
		list = assistanceService.getSupply(queryList);
		return "enterTicket";
	}
	
	public String doTicket() throws Exception{
		this.logger.info("关联发票");
		sum = 0.0; 
		balance = 0.0;
		pi = new AssistancePayInfo();
		List<Assistance> assistanceList = getAssistanceList();
		for(TicketCollection o : tc){
			if(o.getId()!=null){
				assistance = new Assistance();
				assistance.setConMoney(o.getMoney());
				Long tId = o.getTicketId();
				String hql = "from AssistanceTicket at where at.id = "+tId;
				at = (AssistanceTicket)service.uniqueResult(hql);
				assistance.setTicket(at);
				assistanceList.add(assistance);
				id = at.getContractId().toString();
			}
		}
		Long pId = pi.getId();
		String hql = "from Assistance a where a.assistancePayId = "+pId;
		List<Assistance> aList = service.list(hql);
		for(Assistance a : aList){
			sum += a.getConMoney();
		}
		return "return";
	}
	private List<Assistance> getAssistanceList(){
		List<Assistance> assistanceList = (List<Assistance>)ActionContext.getContext().getSession().get("assistance_apply_list");
		if(assistanceList==null){
			assistanceList = new ArrayList<Assistance>();
		}
		ActionContext.getContext().getSession().put("assistance_apply_list", assistanceList);
		return assistanceList;
	}
	
	public String relationTicket() throws Exception{
		this.logger.info("关联发票后查询");
		sum = 0.0;
		//查询外协合同
		String hql = "from AssistanceContract ac where ac.id="+Long.parseLong(ids);
		ac = (AssistanceContract)service.uniqueResult(hql);		
		String pId = ac.getMainProjectId();
		//查询主题项目
		if(pId!=null && !"".equals(pId)){
			String pHql = "from ContractItemMaininfo c where c.conItemMinfoSid='"+Long.parseLong(pId)+"'";
			c = (ContractItemMaininfo)service.uniqueResult(pHql);
		}
		//查询主题合同
		String cId = ac.getContractId().toString();
		//String cId = c.getConItemId();
		if(cId!=null && !"".equals(cId)){
			String cHql = "from ContractMainInfo cm where cm.conMainInfoSid='"+cId+"'";
			cm = (ContractMainInfo)service.uniqueResult(cHql);
		}
		//查询供应商信息
		String sId = ac.getSupplyId().toString();
		String sHql = "from SupplierInfo s where s.supplierid="+sId;
		s = (SupplierInfo)service.uniqueResult(sHql);
		//查询发票收款金额
		String tHql = "from AssistanceTicket at where at.contractId = "+ac.getId();
		tList = getAssistanceList();
		//查询支付履历
		String piHql = "from AssistancePayInfo pi where pi.assistanceId = "+ac.getId();
		pList = service.list(piHql);
		List<Assistance> assistanceList = getAssistanceList();
		for(Assistance a : assistanceList){
			sum += a.getConMoney();
		}
		if(ac.getTicketMoney()==null){
			ac.setTicketMoney(0.0);
		}
		ac.setTicketMoney(ac.getTicketMoney() + sum);
		if(ac.getContractMoney()==null){
			ac.setContractMoney(0.0);
		}
		balance = ac.getContractMoney() - ac.getTicketMoney();
		return "enterApply";
	}
	
	public String saveAssistanceApply()throws Exception{
		this.logger.info("保存付款申请");
		sum = 0.0;
		pi = new AssistancePayInfo();
		service.save(pi);
		List<Assistance> assistanceList = (List<Assistance>) ActionContext.getContext().getSession().get("assistance_apply_list");
		//String tHql = "from AssistanceTicket at where at.contractId = "+assistanceContractId;
		//List<AssistanceTicket> tList = service.list(tHql);
		String aHql = "from AssistanceContract ac where ac.id = " + assistanceContractId;
		ac = (AssistanceContract)service.uniqueResult(aHql);
			for(Assistance a : assistanceList){
				a.setAssistancePayId(pi.getId());
				service.save(a);
				String hql = "from AssistanceTicket at where at.id = "+a.getTicket().getId();
				at = (AssistanceTicket)service.uniqueResult(hql);
				at.setAssistancPayInfoId(pi.getId());
				if(at.getDoneMoney()==null){
					at.setDoneMoney(0.0);
				}
				at.setDoneMoney(at.getDoneMoney()+a.getConMoney());
				sum += a.getConMoney();
			}
			if(tc!=null){
				if(ac.getTicketMoney()==null) ac.setTicketMoney(0.0);
				Double nowMoney = sum + ac.getTicketMoney();
				ac.setTicketMoney(nowMoney);
					service.update(ac);
					service.update(at);
			}
		Date date = new Date();
		pi.setApplyDate(date);
		pi.setPayNum(sum);
		pi.setAssistanceId(ac.getId());
		pi.setPayState("0");
		pi.setIs_active("1");
		service.update(pi);
		return "saveApplyBack";
	}
	
	public String passAssistanceApply()throws Exception{
		this.logger.info("确认通过付款申请");
		sum = 0.0;
		pi = new AssistancePayInfo();
		service.save(pi);
		List<Assistance> assistanceList = (List<Assistance>) ActionContext.getContext().getSession().get("assistance_apply_list");
		//String tHql = "from AssistanceTicket at where at.contractId = "+assistanceContractId;
		//List<AssistanceTicket> tList = service.list(tHql);
		String aHql = "from AssistanceContract ac where ac.id = " + assistanceContractId;
		ac = (AssistanceContract)service.uniqueResult(aHql);
		for(Assistance a : assistanceList){
			a.setAssistancePayId(pi.getId());
			service.save(a);
			String hql = "from AssistanceTicket at where at.id = "+a.getTicket().getId();
			at = (AssistanceTicket)service.uniqueResult(hql);
			at.setAssistancPayInfoId(pi.getId());
			if(at.getDoneMoney()==null){
				at.setDoneMoney(0.0);
			}
			at.setDoneMoney(at.getDoneMoney()+a.getConMoney());
			sum += a.getConMoney();
		}
		if(ac.getTicketMoney()==null){
			ac.setTicketMoney(0.0);
		}
		Double nowMoney = sum + ac.getTicketMoney();
		ac.setTicketMoney(nowMoney);
		service.update(ac);
		service.update(at);
		Date date = new Date();
		pi.setApplyDate(date);
		pi.setPayNum(sum);
		pi.setAssistanceId(ac.getId());
		pi.setPayState("1");
		pi.setIs_active("1");
		service.update(pi);
		return "passApplyBack";
	}
	
	public String unchainRelation(){
		this.logger.info("解除关联");
		String hql = "from AssistanceTicket at where at.id = "+ticketId;
		at = (AssistanceTicket)service.uniqueResult(hql);
		String aHql = "from Assistance a where a.ticket = "+ticketId;
		assistance = (Assistance)service.uniqueResult(aHql);
		List<Assistance> assistanceList = (List<Assistance>)ActionContext.getContext().getSession().get("assistance_apply_list");
		for(Assistance a : assistanceList){
			if(a.getTicket().getId()==ticketId){
				assistanceList.remove(a);
			}
		}
		return "enterApply";
	}
	
	public String showApplyHistory(){
		this.logger.info("查询支付履历");
		String hql = "from AssistancePayInfo pi where pi.assistanceId = "+assistanceContractId;
		pList = service.list(hql);
		sum = new Double(pList.size());
		return "showApplyHistory";
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

	public List<Object> getListlm() {
		return listlm;
	}
	public void setListlm(List<Object> listlm) {
		this.listlm = listlm;
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

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public AssistanceContract getAc() {
		return ac;
	}

	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}

	public List<Object> getListc() {
		return listc;
	}
	public void setListc(List<Object> listc) {
		this.listc = listc;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Long getAssistanceContractId() {
		return assistanceContractId;
	}

	public void setAssistanceContractId(Long assistanceContractId) {
		this.assistanceContractId = assistanceContractId;
	}

	public List<ContractItemMaininfo> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ContractItemMaininfo> projectList) {
		this.projectList = projectList;
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
}

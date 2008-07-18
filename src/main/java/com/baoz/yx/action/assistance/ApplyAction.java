package com.baoz.yx.action.assistance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.TicketCollection;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/applyLeft.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/assistance/editApply.jsp"),
	@Result(name = "enterTicket", value = "/WEB-INF/jsp/assistance/applyTicket.jsp"),
	@Result(name = "return", value = "/WEB-INF/jsp/assistance/applyReturn.jsp"),
	@Result(name = "edit", value = "/WEB-INF/jsp/assistance/editApplyReturn.jsp"),
	@Result(name = "saveApplyBack", value = "/WEB-INF/jsp/assistance/saveAssistanceApplyReturn.jsp"),
	@Result(name = "query", type = ServletRedirectResult.class, value = "/assistance/apply.action?method=query"),
	@Result(name = "unchain", type = ServletRedirectResult.class, value = "/assistance/apply.action?method=enterUpdate"),
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
	private AssistancePayInfo pi ;
	private PageInfo info ;
	private String supply;
	private String type;
	private String sDate;
	private String eDate;
	private String ids;
	private Object[] ob;
	private Double sum;
	private AssistanceContract ac;
	private AssistanceTicket at;
	private SupplierInfo s;
	private ContractMainInfo cmi;
	private ContractItemMaininfo c;
	private List list;
	private List<TicketCollection> tc;
	private List<Assistance> tList;
	private List<AssistancePayInfo> pList;
	private Double balance;
	private ContractMainInfo cm ;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	private String id;
	private String pid;
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("进入付款申请管理");
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "success";
	}
	public String query() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select pi, ac.id, ac.assistanceName, si.supplierName from AssistancePayInfo pi, AssistanceContract ac, SupplierInfo si where pi.is_active='1' and pi.assistanceId=ac.id and ac.supplyId=si.supplierid");
		if(supply!=null && !"".equals(supply)){
			sb.append(" and si.supplierName like '%"+supply+"%'");
		}
		if(type!=null && !"".equals(type)){
			sb.append(" and pi.payState = '"+type+"'");
		}
		if(sDate!=null && !"".equals(sDate)){
			sb.append(" and to_date('"+sDate+"', 'yyyy-MM-dd') <= pi.applyDate  ");
		}
		if(eDate!=null && !"".equals(eDate)){
			sb.append(" and pi.applyDate <= to_date('"+eDate+"', 'yyyy-MM-dd') ");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		return "queryList";
	}
	public String enterUpdate(){
		this.logger.info("进入修改付款申请");
		/*String hql = "select pi, ac, cmi, cim, si from AssistancePayInfo pi ,AssistanceContract ac, ContractMainInfo cmi, ContractItemMaininfo cim, SupplierInfo si where pi.is_active='1' and ac.id=pi.assistanceId and ac.contractId=cmi.conMainInfoSid and cim.conItemId=ac.mainProjectId and si.supplierid=ac.supplyId and pi.id = "+Long.parseLong(ids);
		ob = (Object[])commonService.uniqueResult(hql);
		String tHql = "select at from Assistance a, AssistancePayInfo pi, AssistanceTicket at where pi.id=a.assistancePayId and at.id=a.ticket";
		info = queryService.listQueryResult(tHql, info);
		String Hql = "from Assistance a where a.assistancePayId = "+ids;
		List<Assistance> aList = (List<Assistance>)commonService.list(Hql);
		String pHql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(pHql);*/
		String hql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		String acHql = "from AssistanceContract ac where ac.id = " + pi.getAssistanceId();
		ac = (AssistanceContract)commonService.uniqueResult(acHql);
		String sHql = "from SupplierInfo s where s.supplierid = "+ac.getSupplyId();
		s = (SupplierInfo)commonService.uniqueResult(sHql);
		//查询主题合同
		String cHql = "from ContractMainInfo cm where cm.conMainInfoSid="+ac.getContractId();
		cmi = (ContractMainInfo)commonService.uniqueResult(cHql);
		//查询主题项目
		String pHql = "from ContractItemMaininfo c where c.conItemMinfoSid="+ac.getMainProjectId();
		c = (ContractItemMaininfo)commonService.uniqueResult(pHql);
		String atHql = "select at from Assistance a, AssistancePayInfo pi, AssistanceTicket at where a.assistancePayId=pi.id and a.ticket=at.id and pi.id = "+ids;
		info = queryService.listQueryResult(atHql, info);
		getAssistanceList().clear();
		return "enterUpdate";
	}
	public String pass(){
		this.logger.info("确认提交");
		String hql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		pi.setPayState("1");
		commonService.update(pi);
		return "query";
	}
	public String delete(){
		this.logger.info("删除申请");
		String hql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		pi.setIs_active("0");
		commonService.update(pi);
		return "query";
	}
	public String enterTicket() throws Exception{
		this.logger.info("进入关联发票");
		String hql = "from AssistanceTicket at where at.is_active='1' and exists(select ac.id from AssistanceContract ac where ac.id = at.contractId and ac.id="+ids+")";
		info = queryService.listQueryResult(hql, info);
		List queryList = (List)info.getResult();
		list = assistanceService.getSupply(queryList);
		return "enterTicket";
	}
	public String doTicket() throws Exception{
		this.logger.info("关联发票");
		sum = 0.0; 
		String pHql = "from AssistancePayInfo pi where pi.id = "+pid;
		pi = (AssistancePayInfo)commonService.uniqueResult(pHql);
		List<Assistance> assistanceList = getAssistanceList();
		for(TicketCollection o : tc){
			if(o.getId()!=null){
				Assistance assistance = new Assistance();
				assistance.setConMoney(o.getMoney());
				Long tId = o.getTicketId();
				String hql = "from AssistanceTicket at where at.id = "+tId;
				at = (AssistanceTicket)commonService.uniqueResult(hql);
				assistance.setTicket(at);
				assistance.setAssistancePayId(pi.getId());
				assistanceList.add(assistance);
				id = at.getContractId().toString();
			}
		}
		Long pId = pi.getId();
		String hql = "from Assistance a where a.assistancePayId = "+pId;
		List<Assistance> aList = getAssistanceList();
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
		String aHql = "from AssistanceContract ac where ac.id = "+ids;
		ac = (AssistanceContract)commonService.uniqueResult(aHql);
		String hql = "from AssistancePayInfo pi where pi.assistanceId = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		String sHql = "from SupplierInfo s where s.supplierid = "+ac.getSupplyId();
		s = (SupplierInfo)commonService.uniqueResult(sHql);
		//查询主题合同
		String cHql = "from ContractMainInfo cm where cm.conMainInfoSid="+ac.getContractId();
		cmi = (ContractMainInfo)commonService.uniqueResult(cHql);
		//查询主题项目
		if(ac.getMainProjectId()!=null && !"".equals(ac.getMainProjectId())){
			String pHql = "from ContractItemMaininfo c where c.conItemMinfoSid="+ac.getMainProjectId();
			c = (ContractItemMaininfo)commonService.uniqueResult(pHql);
		}
		String atHql = "select at from Assistance a, AssistancePayInfo pi, AssistanceTicket at where a.assistancePayId=pi.id and a.ticket=at.id and pi.id = "+pi.getId();
		//info = queryService.listQueryResult(atHql, info);
		List<Assistance> assistanceList = getAssistanceList();
		List<AssistanceTicket> ticketList = new ArrayList<AssistanceTicket>();
			ticketList = commonService.list(atHql);
		for(Assistance a : assistanceList){
			sum += a.getConMoney();
			a.getTicket().setDoneMoney(a.getConMoney());
			ticketList.add(a.getTicket());
		}
		info = new PageInfo();
		info.setResult(ticketList);
		ac.setTicketMoney(ac.getTicketMoney() + sum);	
		/*balance = ac.getContractMoney() - ac.getTicketMoney(); */
		return "enterUpdate";
	}
	public String saveAssistanceApply()throws Exception{
		this.logger.info("保存付款申请");
		sum = 0.0;
		String pHql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(pHql);
		List<Assistance> assistanceList = (List<Assistance>) ActionContext.getContext().getSession().get("assistance_apply_list");
		//String tHql = "from AssistanceTicket at where at.contractId = "+assistanceContractId;
		//List<AssistanceTicket> tList = commonService.list(tHql);
		String aHql = "from AssistanceContract ac where ac.id = " + pi.getAssistanceId();
		ac = (AssistanceContract)commonService.uniqueResult(aHql);
		for(Assistance a : assistanceList){
			a.setAssistancePayId(pi.getId());
			commonService.save(a);
			String hql = "from AssistanceTicket at where at.id = "+a.getTicket().getId();
			at = (AssistanceTicket)commonService.uniqueResult(hql);
			at.setAssistancPayInfoId(pi.getId());
			if(at.getDoneMoney()==null){
				at.setDoneMoney(0.0);
			}
			at.setDoneMoney(at.getDoneMoney()+a.getConMoney());
			sum += a.getConMoney();
		}
		if(at!=null){
			if(ac.getTicketMoney()==null){
				ac.setTicketMoney(0.0);
			}
			Double nowMoney = sum + ac.getTicketMoney();
			ac.setTicketMoney(nowMoney);
			commonService.update(ac);
			commonService.update(at);
		}
		Date date = new Date();
		pi.setApplyDate(date);
		pi.setPayNum(sum);
		pi.setAssistanceId(ac.getId());
		pi.setPayState("0");
		pi.setIs_active("1");
		commonService.update(pi);
		return "saveApplyBack";
	}
	public String passAssistanceApply()throws Exception{
		this.logger.info("提交确认付款申请");
		sum = 0.0;
		String pHql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(pHql);
		List<Assistance> assistanceList = (List<Assistance>) ActionContext.getContext().getSession().get("assistance_apply_list");
		//String tHql = "from AssistanceTicket at where at.contractId = "+assistanceContractId;
		//List<AssistanceTicket> tList = commonService.list(tHql);
		String aHql = "from AssistanceContract ac where ac.id = " + pi.getAssistanceId();
		ac = (AssistanceContract)commonService.uniqueResult(aHql);
		for(Assistance a : assistanceList){
			a.setAssistancePayId(pi.getId());
			commonService.save(a);
			String hql = "from AssistanceTicket at where at.id = "+a.getTicket().getId();
			at = (AssistanceTicket)commonService.uniqueResult(hql);
			at.setAssistancPayInfoId(pi.getId());
			if(at.getDoneMoney()==null){
				at.setDoneMoney(0.0);
			}
			at.setDoneMoney(at.getDoneMoney()+a.getConMoney());
			sum += a.getConMoney();
		}
		if(at!=null){
			if(ac.getTicketMoney()==null){
				ac.setTicketMoney(0.0);
			}
			Double nowMoney = sum + ac.getTicketMoney();
			ac.setTicketMoney(nowMoney);
			commonService.update(ac);
			commonService.update(at);
		}
		Date date = new Date();
		pi.setApplyDate(date);
		pi.setPayNum(sum);
		pi.setAssistanceId(ac.getId());
		pi.setPayState("1");
		pi.setIs_active("1");
		commonService.update(pi);
		return "saveApplyBack";
	}
	
	public String unchainRelation(){
		this.logger.info("解除关联");
		String hql = "from AssistanceTicket at where at.id = "+ids;
		at = (AssistanceTicket)commonService.uniqueResult(hql);
		String aHql = "from Assistance a where a.ticket = "+ids;
		Assistance assistance = (Assistance)commonService.uniqueResult(aHql);
		List<Assistance> assistanceList = (List<Assistance>)ActionContext.getContext().getSession().get("assistance_apply_list");
		for(Assistance a : assistanceList){
			if(a.getTicket().getId()==Long.parseLong(ids)){
				assistanceList.remove(a);
			}
		}
		return "enterUpdate";
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
	public String getSupply() {
		return supply;
	}
	public void setSupply(String supply) {
		this.supply = supply;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Object[] getOb() {
		return ob;
	}
	public void setOb(Object[] ob) {
		this.ob = ob;
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
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
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
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public ContractMainInfo getCm() {
		return cm;
	}
	public void setCm(ContractMainInfo cm) {
		this.cm = cm;
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
}

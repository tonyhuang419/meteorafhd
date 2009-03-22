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
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/passApplyLeft.jsp"),
	@Result(name = "detail", value = "/WEB-INF/jsp/assistance/applyDetail.jsp"),
	@Result(name = "showBack", value = "/WEB-INF/jsp/assistance/confirmCancelReason.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/passApplyList.jsp")})
public class PassApplyAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typemanageservice;
	private AssistancePayInfo pi ;
	private PageInfo info ;
	private String supply;
	
	private Long supplierid;//外协供应商id
	private String startDate;
	private String endDate;
	private Date date;
	private String ids;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	private String groupId;
	private String expId;
	private String userName;
	private String groupName;
	private Long applyInfoId;
	private SupplierInfo s;
	private ContractMainInfo cmi;
	private Double sum;
	private ContractItemMaininfo c;
	private AssistanceContract ac;
	
	private String payInfoState;//付款申请状态
	private String returnReason;//退回理由
	
	private List<AssistanceSection> asectionList;//外协阶段
	
	private String assistanceNo;//外协合同号
	
	private ObjectPropertySessionHoldTool  holdTool = new ObjectPropertySessionHoldTool("passApplyParameters",this,
			new String[]{"payInfoState","supplierid","startDate","endDate","expId","groupId","assistanceNo"});
	@Override
	public String doDefault() throws Exception {
		this.logger.info("进入付款申请管理");
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "success";
	}
	public String query() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append("select pi, " +
				"ac.assistanceId , " +
				"ac.assistanceName ," +
				" si.supplierName,emp " +
				"from AssistancePayInfo pi, SupplierInfo si , Employee emp," +
				" AssistanceContract ac " +
				" where pi.is_active='1' " +
				"and pi.supplyId = si.supplierid " +
				"and emp.id = pi.employeeId " +
				"and ac.id = pi.assistanceId ");
		if(payInfoState != null){
			sb.append(" and pi.payState = '"+payInfoState+"'");
		}else{
			sb.append(" and pi.payState ='1' ");
		}
		if(supplierid!=null){
			sb.append(" and si.supplierid = "+supplierid+" ");
		}
		if(startDate!=null && !"".equals(startDate)){
			sb.append(" and to_date('"+startDate+"', 'yyyy-MM-dd') <= pi.applyDate  ");
		}
		if(endDate!=null && !"".equals(endDate)){
			sb.append(" and trunc(pi.applyDate) <= to_date('"+endDate+"', 'yyyy-MM-dd') ");
		}
		if(StringUtils.isNotBlank(assistanceNo)){
			sb.append(" and lower(ac.assistanceId) = '"+StringUtils.lowerCase(assistanceNo)+"' ");
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
		sb.append(" order by pi.payState DESC,pi.id");
		info = ParameterUtils.preparePageInfo(info, "passApplyPageInfo");
		info = queryService.listQueryResult(SqlUtils.getCountSql(sb.toString()),sb.toString(), info);
		return "queryList";
	}
	public String pass() throws Exception {
		this.logger.info("确认通过");
		String[] opId=StringUtils.split(ids,",");
		assistanceService.confirmPayInfoPass(opId);
		return query();
	}
	/**
	 * 确认处理的操作
	 * @return
	 * @throws Exception
	 */
	public String confirmOperator()throws Exception{
		String[] opId=StringUtils.split(ids,",");
		assistanceService.confirmPayInfoOperator(opId);
		return query();
	}
	/**
	 * 确认退回
	 * @return
	 */
	public String showBack(){
		
		return "showBack";
	}
	
	public String back() throws Exception {
		this.logger.info("确认退回");
		if(ids!=null){
			String[] opId=StringUtils.split(ids,",");
			if(opId!=null&&opId.length>0){
				for(int k=0;k<opId.length;k++){
					AssistancePayInfo payInfo = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, Long.valueOf(opId[k]));
					payInfo.setPayState("3");
					payInfo.setReturnReason(returnReason);
					commonService.update(payInfo);
				}
			}
		}
		return query();
	}
	
	public String cancelConfirm()throws Exception{
		if(ids!=null){
			String[] opId=StringUtils.split(ids,",");
			if(opId!=null&&opId.length>0){
				for(int k=0;k<opId.length;k++){
					AssistancePayInfo payInfo = (AssistancePayInfo)commonService.load(AssistancePayInfo.class, Long.valueOf(opId[k]));
					payInfo.setPayState("1");
					commonService.update(payInfo);
				}
			}
		}	
		return query();
	}
	
	public String detail(){
		userName = UserUtils.getUser().getName();
		String userHql = "from Employee e where e.id = "+UserUtils.getUser().getId();;
		Employee e = (Employee)commonService.uniqueResult(userHql);
		String oHql = "from OrganizationTree o where o.id = "+e.getPosition();
		OrganizationTree o = (OrganizationTree)commonService.uniqueResult(oHql);
		groupName = o.getOrganizationName();
		applyInfoId = Long.parseLong(ids);
		String hql = "from AssistancePayInfo pi where pi.id = "+applyInfoId;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		String acHql = "from AssistanceContract ac where ac.id = " + pi.getAssistanceId();
		ac = (AssistanceContract)commonService.uniqueResult(acHql);
		String sHql = "from SupplierInfo s where s.supplierid = "+ac.getSupplyId();
		s = (SupplierInfo)commonService.uniqueResult(sHql);
		//查询主题合同
		String cHql = "from ContractMainInfo cm where cm.conMainInfoSid="+ac.getContractId();
		cmi = (ContractMainInfo)commonService.uniqueResult(cHql);
		//查询主题项目
		//String pHql = "from ContractItemMaininfo c where c.conItemMinfoSid="+ac.getMainProjectId();
		//c = (ContractItemMaininfo)commonService.uniqueResult(pHql);
		String atHql = "select at from Assistance a, AssistancePayInfo pi, AssistanceTicket at where a.assistancePayId=pi.id and a.ticket=at.id and pi.id = "+applyInfoId;
		info = queryService.listQueryResult(atHql, info);
		getAssistanceList().clear();
		List<Assistance> existAssistance = commonService.list("from Assistance where assistancePayId = "+ applyInfoId);
		sum = 0.0;
		for(Assistance assis : existAssistance){
			sum += assis.getConMoney();
		}
		getAssistanceList().addAll(existAssistance);
		if(ac.getMainProjectId()!=null){
			String pHql = "from ContractItemMaininfo c where c.conItemId = '"+ac.getMainProjectId()+"'";
			c = (ContractItemMaininfo)commonService.uniqueResult(pHql);
		}
		asectionList = assistanceService.getAssistanceSEctionByPayInfoId(pi.getId());
		return "detail";
	}
	
	private List<Assistance> getAssistanceList(){
		List<Assistance> assistanceList = (List<Assistance>)ActionContext.getContext().getSession().get("assistance_apply_list");
		if(assistanceList==null){
			assistanceList = new ArrayList<Assistance>();
		}
		ActionContext.getContext().getSession().put("assistance_apply_list", assistanceList);
		return assistanceList;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public Long getApplyInfoId() {
		return applyInfoId;
	}
	public void setApplyInfoId(Long applyInfoId) {
		this.applyInfoId = applyInfoId;
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
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public ContractItemMaininfo getC() {
		return c;
	}
	public void setC(ContractItemMaininfo c) {
		this.c = c;
	}
	public AssistanceContract getAc() {
		return ac;
	}
	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}
	public IYXTypeManageService getTypemanageservice() {
		return typemanageservice;
	}
	public void setTypemanageservice(IYXTypeManageService typemanageservice) {
		this.typemanageservice = typemanageservice;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public Long getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}
	public List<AssistanceSection> getAsectionList() {
		return asectionList;
	}
	public void setAsectionList(List<AssistanceSection> asectionList) {
		this.asectionList = asectionList;
	}
	public String getPayInfoState() {
		return payInfoState;
	}
	public void setPayInfoState(String payInfoState) {
		this.payInfoState = payInfoState;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getExpId() {
		return expId;
	}
	public void setExpId(String expId) {
		this.expId = expId;
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
	public String getAssistanceNo() {
		return assistanceNo;
	}
	public void setAssistanceNo(String assistanceNo) {
		this.assistanceNo = assistanceNo;
	}


}

package com.baoz.yx.action.harvestMeansManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

/**
 * 收款管理查询
 * 
 * @author ye peng (yepeng@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/harvestMeansManagerList.jsp")
public class HarvestMeansSearchAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	private PageInfo info;
	private List list;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private List<YXTypeManage> receTypetrans;
	private ReveInfo ri;	
	private List<YXTypeManage> clientNames;
	private Long sid; // 查询出销售员id
//	private Long id; // 查询客户
	private String invoiceNo; // 发票号
	private String projectNo; // 项目号
	private String ticketType; // 开票类型
	private String sTime; // 开始时间
	private String lTime; // 结束时间
	private Long receState; // 收款状态
	private Long billType; // 开票类型
	private String groupId; //组名
	private String clientId; //选择时客户id

	List<List<ReveInfo>> reveList = new ArrayList<List<ReveInfo>>();

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"SelectPerQueryParameters",this,new String[]{"sid","clientId","invoiceNo","projectNo","billType","receState","STime","LTime"});	


	@Override
	@SuppressWarnings("unchecked")

	public String doDefault() throws Exception {
		this.logger.info("根据查询条件查询出收款明细");

		receTypetrans= typeManageService.getYXTypeManage(1017L);// 到款方式
		StringBuffer from = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		String select = "select ab,ii,yc,(select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = ab.contractMainInfo ) ,"+
		"( select max(ci.conItemId) from RealContractBillandRecePlan bp ,ContractItemMaininfo ci ,BillandProRelaion br where bp.contractItemMaininfo = ci.conItemMinfoSid and br.realContractBillandRecePlan = bp.realConBillproSid and br.applyBill = ab.billApplyId ) ";
		from.append(" from ApplyBill ab,InvoiceInfo ii,YXClientCode yc,OrganizationTree org,Employee emp where org.id=emp.position and emp.id=ab.employeeId and ab.billApplyId = ii.applyInvoiceId " +
		"and ab.customerId = yc.id and  ii.is_active =1 and  yc.is_active =1 and (ii.changeState ='0' or ii.changeState is null)");
		// 不是组长,只能查自己

		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			sid = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId!=null&&!"".equals(groupId)) {
			from.append(" and org.organizationCode like '").append(groupId+"%'");
		}	

		if (sid != null && !"".equals(sid)) {
			from.append(" and ab.employeeId= ").append(sid);//销售员
		}
		if (clientId != null && !"".equals(clientId)) {
			from.append(" and ab.customerId= ").append(clientId);//客户
		}
		if (invoiceNo != null && !"".equals(invoiceNo)) {

			from.append(" and lower(ii.invoiceNo) like '%").append(StringUtils.lowerCase(StringUtils.trim(invoiceNo))+"%'");// 发票号
		}
		if (projectNo != null && !"".equals(projectNo)) {

			from.append(" and exists ( select 1 from RealContractBillandRecePlan bp ,ContractItemMaininfo ci ,BillandProRelaion br " +
					" where bp.contractItemMaininfo = ci.conItemMinfoSid " +
					" and br.realContractBillandRecePlan = bp.realConBillproSid " +
					" and br.applyBill = ab.billApplyId and lower(ci.conItemId) like '%"+StringUtils.lowerCase(StringUtils.trim(projectNo))+"%' ) ");// 项目号
		}
		if (billType != null && !"".equals(billType)) {
			from.append(" and ab.billType= ").append(billType);// 开票类型
		}
		if (receState != null && !"".equals(receState)) {
			if (receState == 2) {
				from.append(" and ( ii.receState is null or ii.receState= ").append(receState).append(") ");// 收款状态
			}else{
				from.append(" and ii.receState= ").append(receState);// 收款状态
			}
		}
		if (sTime != null && !"".equals(sTime)) {
			from.append(" and ii.invoiceDate>=to_date('" + sTime
					+ "','yyyy-mm-dd')"); // 开始时间
		}
		if (lTime != null && !"".equals(lTime)) {
			from.append(" and ii.invoiceDate <=to_date('" + lTime
					+ "','yyyy-mm-dd')");// 结束时间
		}

		from.append(" order by ii.id desc");

		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "harvestMeanSearchCurPage");

		info = queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);

		List<Object[]> invoiceList = (List<Object[]>) info.getResult();
		for (Object[] objects : invoiceList) {
			reveList.add(commonService.list("from ReveInfo ri where ri.billSid=? and ri.is_active =1 order by ri.id desc",((InvoiceInfo)objects[1]).getId()));
		}

		logger.info("查询完毕");

		return "queryList";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getReceTypetrans() {
		return receTypetrans;
	}

	public void setReceTypetrans(List<YXTypeManage> receTypetrans) {
		this.receTypetrans = receTypetrans;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

//	public Long getId() {
//	return id;
//	}

//	public void setId(Long id) {
//	this.id = id;
//	}





	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}



	public String getSTime() {
		return sTime;
	}

	public void setSTime(String time) {
		sTime = time;
	}

	public String getLTime() {
		return lTime;
	}

	public void setLTime(String time) {
		lTime = time;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public Long getReceState() {
		return receState;
	}

	public void setReceState(Long receState) {
		this.receState = receState;
	}

	public ReveInfo getRi() {
		return ri;
	}

	public void setRi(ReveInfo ri) {
		this.ri = ri;
	}

	public List<List<ReveInfo>> getReveList() {
		return reveList;
	}

	public void setReveList(List<List<ReveInfo>> reveList) {
		this.reveList = reveList;
	}

	public Long getBillType() {
		return billType;
	}

	public void setBillType(Long billType) {
		this.billType = billType;
	}

	public List<YXTypeManage> getClientNames() {
		return clientNames;
	}

	public void setClientNames(List<YXTypeManage> clientNames) {
		this.clientNames = clientNames;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}

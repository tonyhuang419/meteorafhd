package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.service.impl.CommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
@Results({	
	@Result(name = "succ", value = "/WEB-INF/jsp/assistance/assistanceMList.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceMQueryLeft.jsp"),
	@Result(name = "showDetailha", value = "/WEB-INF/jsp/assistance/showAssistanceDetail.jsp")
	})
public class AssistanceMLeftQueryAction extends DispatchAction {
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	private String supplier;//外协供应商名称
	private PageInfo info;
	private String conState;   //外协合同状态
	private String payState;   //付款状态
	private Double minConMoney;		 //最小金额
	private Double maxConMoney;		 //最大金额
	private String startDate;
	private String endDate;
	private String contractName;//外协合同名称
	private String assistanceContractNo;// 外协合同号
	private String itemNo;//项目号
	private String contractNo;//合同号
	private String departmentName;//部门
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;	
	private String userName;
	private String groupId;
	
	private Boolean hasClosed;//是否已经关闭
	private String detailComeFrom;   //con:正式合同管理  stat:统计
	
	private List<AssistancePayInfo> pList ;////外协付款申请
	
	// 工程责任部门
	private List<YXTypeManage> dutydepartmentlist;
	
	private String expId;
	
	private Long     assistanceId;
	
	private Long supplierid;//外协供应商id
	
	private List<AssistanceSection> sectionList;
	
	private AssistanceContract assistanceContract;////外协合同id
	
	private SupplierInfo supply;///外协供应商
	
	private ContractMainInfo contractMainInfo;////正式合同
	
	private ContractItemMaininfo itemMainInfo;////正式项目
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"assistanceMLeftQueryParameters",
			this,
			new String[]{"supplierid","conState","payState",
					"minConMoney","maxConMoney",
					"startDate","endDate",
					"contractName","assistanceContractNo",
					"itemNo","contractNo","departmentName",
					"expId","groupId","hasClosed"});

	private Double sumConMoney;////查询出外协合同列表中的合同总金额
	
	private Double sumHasPayAmount;///查询出外协合同列表中的已支付总金额
	
	private Double sumFeeAmount;////查询出外协合同列表中的合同总的余额
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		listExp = service.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		expId=UserUtils.getUser().getId().toString();
		groupId=UserUtils.getUser().getPosition().toString();
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);
		return "queryList";
	}

	
	
	public String query() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sb = new StringBuffer();
		String select = "select ac, si.supplierName," +
				"(select sum(a.payNum) " +
				"from AssistancePayInfo a where a.assistanceId = ac.id ), emp.name ";
		sb.append(" from AssistanceContract ac, SupplierInfo si,Employee emp where ac.is_active='1' and ac.supplyId=si.supplierid and ac.employeeId = emp.id  ");
		if(supplierid!=null){
			sb.append(" and  si.supplierid = '"+supplierid+"' " );
		}
		if(hasClosed!=null && hasClosed){
			sb.append(" and ac.assistanceContractType = '4' ");///4表示已经关闭
		}else{
			if(conState!=null &&!"".equals(conState)){
				sb.append(" and ac.assistanceContractType = '").append(conState).append("' ");
			}
			if(payState!=null && !"".equals(payState)){
				sb.append(" and ac.assistanceType=").append(payState);
			}
		}
		
		if(minConMoney!=null){
			sb.append(" and ").append(minConMoney).append("<ac.contractMoney ");
		}
		if(maxConMoney!=null){
			sb.append(" and ac.contractMoney < ").append(maxConMoney);
		}
		if(startDate!=null && !"".equals(startDate)){
			sb.append(" and to_date('").append(startDate).append("', 'yyyy-MM-dd')").append(" <= ac.contractDate ");
		}
		if(endDate!=null && !"".equals(endDate)){
			sb.append(" and trunc(ac.contractDate,'dd') <= to_date('").append(endDate).append("', 'yyyy-MM-dd') ");
		}
		if(contractName!=null && !"".equals(contractName)){
			sb.append(" and ac.assistanceName like '%").append(contractName).append("%' ");
		}
		if(StringUtils.isNotBlank(assistanceContractNo)){
			sb.append(" and lower(ac.assistanceId) = '"+ StringUtils.lowerCase(assistanceContractNo) +"' ");
		}
		if(StringUtils.isNotBlank(itemNo)){
			sb.append(" and lower(ac.mainProjectId) = '"+StringUtils.lowerCase(itemNo)+"' ");
		}
		if(StringUtils.isNotBlank(contractNo)){
			sb.append(" and exists (select 1 from ContractMainInfo contract where contract.conMainInfoSid = ac.contractId and lower(contract.conId) = '"+StringUtils.lowerCase(contractNo)+"') ");
		}
		if(StringUtils.isNotBlank(departmentName)){
			sb.append(" and exists (select 1 from ContractItemMaininfo item where item.conItemMinfoSid = ac.conItemMainInfoSid and item.itemResDept = '"+departmentName+"')");
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
			sb.append(" and emp.id ="+expId+")");
		}
		if(groupId!=null&&!"".equals(groupId)){
			sb.append(" and emp.id in (select e.id from Employee e where e.position in (select o.id from OrganizationTree o where o.organizationCode like '"+groupId+"%'))");
		}
		sb.append("order by ac.assistanceContractType,ac.id desc");
		info = ParameterUtils.preparePageInfo(info, "assistanceMLeftQueryPageInfo");
		info = queryService.listQueryResult("select count(*) "+ sb,select+sb, info);
		String totalHql = "select sum(ac.contractMoney),sum(ac.hasPayAmount),sum(ac.contractMoney-nvl(ac.hasPayAmount,0)) ";
		Object[]objects = (Object[])service.uniqueResult(totalHql+sb);
		if(objects != null){
			sumConMoney = (Double)objects[0];
			sumHasPayAmount = (Double)objects[1];
			sumFeeAmount = (Double)objects[2];
		}else{
			sumConMoney = new Double(0.00);
			sumHasPayAmount = new Double(0.00);
			sumFeeAmount = new Double(0.00);
		}
		return "succ";
	}
	
	/**
	 * 用来显示外协合同明细页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showDetail()
	{
		assistanceContract  = (AssistanceContract)service.load(AssistanceContract.class, assistanceId);
		supply = assistanceService.getSupplierByAssistanceContractId(assistanceContract.getId());
		contractMainInfo = (ContractMainInfo) service.load(ContractMainInfo.class, assistanceContract.getContractId());
		itemMainInfo = (ContractItemMaininfo) service.load(ContractItemMaininfo.class, assistanceContract.getConItemMainInfoSid());
		userName = assistanceService.getAContractName(assistanceId);
		
		String hql = "from AssistancePayInfo pi where pi.is_active='1' and pi.assistanceId = "
			+ assistanceId+" order by applyDate ";
		pList = service.list(hql);
		sectionList = service.list(" from AssistanceSection a where a.assistanceId ="+assistanceId);
		return "showDetailha";
	}
	
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
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

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
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

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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
	public Long getAssistanceId() {
		return assistanceId;
	}
	public void setAssistanceId(Long assistanceId) {
		this.assistanceId = assistanceId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public String getAssistanceContractNo() {
		return assistanceContractNo;
	}
	public void setAssistanceContractNo(String assistanceContractNo) {
		this.assistanceContractNo = assistanceContractNo;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Long getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}
	public List<YXTypeManage> getDutydepartmentlist() {
		return dutydepartmentlist;
	}
	public void setDutydepartmentlist(List<YXTypeManage> dutydepartmentlist) {
		this.dutydepartmentlist = dutydepartmentlist;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public List<AssistancePayInfo> getPList() {
		return pList;
	}
	public void setPList(List<AssistancePayInfo> list) {
		pList = list;
	}	
	public List<AssistanceSection> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<AssistanceSection> sectionList) {
		this.sectionList = sectionList;
	}



	public Double getMinConMoney() {
		return minConMoney;
	}



	public void setMinConMoney(Double minConMoney) {
		this.minConMoney = minConMoney;
	}



	public Double getMaxConMoney() {
		return maxConMoney;
	}



	public void setMaxConMoney(Double maxConMoney) {
		this.maxConMoney = maxConMoney;
	}



	public String getConState() {
		return conState;
	}



	public void setConState(String conState) {
		this.conState = conState;
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



	public AssistanceContract getAssistanceContract() {
		return assistanceContract;
	}



	public void setAssistanceContract(AssistanceContract assistanceContract) {
		this.assistanceContract = assistanceContract;
	}



	public SupplierInfo getSupply() {
		return supply;
	}



	public void setSupply(SupplierInfo supply) {
		this.supply = supply;
	}



	public ContractMainInfo getContractMainInfo() {
		return contractMainInfo;
	}



	public void setContractMainInfo(ContractMainInfo contractMainInfo) {
		this.contractMainInfo = contractMainInfo;
	}



	public ContractItemMaininfo getItemMainInfo() {
		return itemMainInfo;
	}



	public void setItemMainInfo(ContractItemMaininfo itemMainInfo) {
		this.itemMainInfo = itemMainInfo;
	}

	public String getDetailComeFrom() {
		return detailComeFrom;
	}


	public void setDetailComeFrom(String detailComeFrom) {
		this.detailComeFrom = detailComeFrom;
	}


	public Double getSumConMoney() {
		return sumConMoney;
	}


	public void setSumConMoney(Double sumConMoney) {
		this.sumConMoney = sumConMoney;
	}


	public Double getSumHasPayAmount() {
		return sumHasPayAmount;
	}

	public void setSumHasPayAmount(Double sumHasPayAmount) {
		this.sumHasPayAmount = sumHasPayAmount;
	}


	public Double getSumFeeAmount() {
		return sumFeeAmount;
	}


	public void setSumFeeAmount(Double sumFeeAmount) {
		this.sumFeeAmount = sumFeeAmount;
	}



	public Boolean getHasClosed() {
		return hasClosed;
	}



	public void setHasClosed(Boolean hasClosed) {
		this.hasClosed = hasClosed;
	}
}

package com.baoz.yx.action.contract;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.MyBean;
import com.baoz.yx.vo.UserDetail;

/**
 * 项目号录入相关操作
 * 
 * @author xiaoping
 * 
 */
@Results( { @Result(name = "success", value = "/WEB-INF/jsp/contract/contractItemRight.jsp"),@Result(name="goupdate",value="/WEB-INF/jsp/contract/updateConItemId.jsp"),@Result(name = "noProjectTipList", value = "/WEB-INF/jsp/contract/noProjectTipList.jsp"),
	@Result(name = "noProjectTipFrame", value = "/WEB-INF/jsp/contract/noProjectTipFrame.jsp"),
	@Result(name = "noProjectTipCondition", value = "/WEB-INF/jsp/contract/noProjectTipCondition.jsp")
})
public class ContractItemManagerAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private String[]itemsNo;
	private Long[]itemsId;
	private Long resNo;
	private String resNubmer;
	private List<YXTypeManage>projectDeptTypeList;//工程部门列表
	private List<Department>			groupList;
	private List<MyBean> beans;
	private List<YXTypeManage>contractTypeList;
	private PageInfo info;
	private String expId;
    private String groupId;
	private String customerId;

	private String conType;

	private String projectDept;

	private String projectNoState;

	private Long noProjectCodeConCount;

	private String itemNo;
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getExpId() {
		return expId;
	}
	public void setExpId(String expId) {
		this.expId = expId;
	}
	public String getProjectDept() {
		return projectDept;
	}
	public void setProjectDept(String projectDept) {
		this.projectDept = projectDept;
	}
	public String getProjectNoState() {
		return projectNoState;
	}
	public void setProjectNoState(String projectNoState) {
		this.projectNoState = projectNoState;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	@Override
	public String doDefault() throws Exception {
		StringBuffer sp = new StringBuffer();
		sp.append("select c,e,y,cm from ContractMainInfo c,Employee e,YXClientCode y,ContractItemMaininfo cm,OrganizationTree orgTree where c.saleMan=e.id and c.conCustomer=y.id and c.conMainInfoSid=cm.contractMainInfo and e.position=orgTree.id and c.conId is not null");
		UserDetail user = UserUtils.getUserDetail();
		sp.append(" and c.ContractType=1");
		if(itemNo == null){
			sp.append(" and cm.conItemId is null ");
		}
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId()+"";
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId != null && !"".equals(groupId)) {
			sp.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}
		if (expId != null && !expId.equals("")) {
			sp.append(" and e.id='" + expId + "'");
		}
		if (customerId != null && !customerId.equals("")) {
			sp.append(" and y.id='" + customerId + "'");
		}
		if (conType != null && !conType.equals("")) {
			sp.append(" and c.conType='" + conType + "'");
		}
		if (projectDept != null && !projectDept.equals("")) {
			sp.append(" and cm.itemResDept='" + projectDept + "'");
		}
		if (projectNoState != null && !projectNoState.equals("")) {
			if (projectNoState.equals("0"))
				sp.append(" and cm.conItemId IS NULL");
			else
				sp.append(" and cm.conItemId IS NOT NULL");
		}
		sp.append(" and c.conState>=4 and c.conState<=9");
		info = queryService.listQueryResult(sp.toString(), info);
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return SUCCESS;
	}
	public String enterNoProjectTipFrame(){
		return "noProjectTipFrame";
	}
	public String enterNoProjectTipCondition(){
		groupList = UserUtils.getUserDetail().getDepartments();
		return "noProjectTipCondition";
	}
	/**
	 * 查询无项目号的项目，首页提示用
	 * @return
	 */
	public String queryNoProjectCode(){
		StringBuffer sp = new StringBuffer();
		sp.append("select c,e,y,cm," +
				" (select sum(realBillAmount) from RealContractBillandRecePlan real where real.contractItemMaininfo = cm.conItemMinfoSid) "+
				" from ContractMainInfo c,Employee e,YXClientCode y,ContractItemMaininfo cm,OrganizationTree orgTree where c.saleMan=e.id and c.conCustomer=y.id and c.conMainInfoSid=cm.contractMainInfo and e.position=orgTree.id and c.conId is not null");
		UserDetail user = UserUtils.getUserDetail();
		sp.append(" and c.ContractType=1");
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId()+"";
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId != null && !"".equals(groupId)) {
			sp.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}
		if (expId != null && !expId.equals("")) {
			sp.append(" and e.id='" + expId + "'");
		}
		sp.append(" and c.conState>=4 and c.conState<=9 ");
		sp.append(" and cm.conItemId IS NULL");
		info = queryService.listQueryResult(SqlUtils.getCountSql(sp.toString()),sp.toString(), info);
		//计算合同总数
		StringBuilder hql= new StringBuilder();
		hql.append("select count(distinct i.contractMainInfo)" +
				" from ContractMainInfo m , ContractItemMaininfo i " +
				" where i.contractMainInfo = m.conMainInfoSid ");
		if (expId != null && !expId.equals("") ) {
			hql.append(" and m.saleMan = "+expId+" ");
		}
		if (groupId != null && !"".equals(groupId)) {
			hql.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = m.saleMan ) ");
		}
		hql.append(" and m.conState>=4 and m.conState<=9 and i.conItemId is null ");
		noProjectCodeConCount =(Long)commonService.uniqueResult(hql.toString());
		//
		return "noProjectTipList";
	}
    public String saveItemNo()throws Exception{
    	/*logger.info("strLenght===================="+itemsNo.length);
    	logger.info("itemsIdLength================"+itemsId.length);
    	for(int i=0;i<itemsNo.length;i++){
    		 
    	     logger.info("str"+i+"======================="+itemsNo[i]);
    	     logger.info("str"+itemsId[i]);
    	}
    	contractService.updateContractAndConItemId(itemsId,itemsNo);*/
    	beans=new ArrayList<MyBean>();
    	for(int i=0;i<itemsId.length;i++){
    		MyBean bean=new MyBean();
    		bean.setKey(itemsId[i]);
    		bean.setValue(itemsNo[i]);
    		beans.add(bean);
    	}
    	contractService.updateContractAndConItemId(beans);
    	return doDefault();
    }
    public String updateConItemId()throws Exception{
    	ContractItemMaininfo citem=contractService.getContractItemMaininfo(resNo);
    	setResNubmer(citem.getConItemId());
    	return "goupdate";
    }
    public String doUpdateConItemId()throws Exception{
    	logger.info("doUpdateConItemId   resNo====================="+resNo);
    	logger.info("doUpdateConItemId   resNubmer====================="+resNubmer);
    	ContractItemMaininfo citem=contractService.getContractItemMaininfo(resNo);
    	citem.setConItemId(resNubmer);
    	contractService.updateContractMainItem(citem);
    	return doDefault();
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
	
	
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	public Long[] getItemsId() {
		return itemsId;
	}
	public void setItemsId(Long[] itemsId) {
		this.itemsId = itemsId;
	}
	public String[] getItemsNo() {
		return itemsNo;
	}
	public void setItemsNo(String[] itemsNo) {
		this.itemsNo = itemsNo;
	}
	public List getBeans() {
		return beans;
	}
	public void setBeans(List<MyBean> beans) {
		this.beans = beans;
	}
	public Long getResNo() {
		return resNo;
	}
	public void setResNo(Long resNo) {
		this.resNo = resNo;
	}
	public String getResNubmer() {
		return resNubmer;
	}
	public void setResNubmer(String resNubmer) {
		this.resNubmer = resNubmer;
	}
	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}
	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getProjectDeptTypeList() {
		return projectDeptTypeList;
	}
	public void setProjectDeptTypeList(List<YXTypeManage> projectDeptTypeList) {
		this.projectDeptTypeList = projectDeptTypeList;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public Long getNoProjectCodeConCount() {
		return noProjectCodeConCount;
	}
	public void setNoProjectCodeConCount(Long noProjectCodeConCount) {
		this.noProjectCodeConCount = noProjectCodeConCount;
	}
	
	
	
	

}

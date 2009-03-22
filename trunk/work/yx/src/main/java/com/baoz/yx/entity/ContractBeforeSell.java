package com.baoz.yx.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
@Entity
@Table(name = "yx_contract_before_sell")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractBeforeSell extends PriEntity implements Serializable {
	private static final long	serialVersionUID	= 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "ID", valueColumnName = "tableid", pkColumnValue = "19", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="SELL_BEFORE_ID",length = 20)
	private Long				ID;								//售前合同系统号
	@Column(name="SELL_BEFORE_NUM",length = 20)
	private String				sellBeforeNum;					//售前合同系统编号
	@Column(name = "EMPLOYEE_ID",length = 20)
	private Long				employeeId;						//员工代码..销售员
	@Column(name = "CUSTOMER_ID",length = 20)
	private Long				customerId;						//客户代码
	@Column(name = "CUSTOMER_PROJECT_TYPE_ID",length = 20)
	private String				customerProjectTypeId;			//客户项目类型代码
	
	@Deprecated
	@Column(name = "BUSINESS_TYPE_ID",length = 20)
	private String				businessTypeId;					//行业类型代码
	
	@Column(name = "PROJECT_NAME",length = 100)
	private String				projectName;					//项目名称
	@Column(name = "PROJECT_STATE_ID",length = 20)
	private String				projectStateId;					//甲方项目进度
	
	@Deprecated
	@Column(name = "PROJECT_TYPE_ID",length = 20)
	private String				projectTypeId;					//项目类型代码
	@Column(name = "PROJECT_STATE_FOLLOW_ID",length = 20)
	private String				projectStateFollowId;			//项目跟踪状态
	@Column(name = "PROJECT_SUM",length = 20)
	private Double				projectSum;						//项目预计金额  
	@Column(name = "BID_SUM",length = 20)
	private Double				bidSum;							//投标（报价）金额
	@Column(name = "OWN_SUM",length = 20)
	private Double				ownSum;							//中标（合同）金额
	@Column(name = "DUTY_DEPARTMENT_ID",length = 20)
	private String				dutyDepartmentId;				//工程责任部门代码
	@Column(name = "LINK_MAN_ID",length = 20)
	private Long				linkManId;						//联系人代码
	@Column(name = "PROJECT_MAN_ID",length = 20)
	private String				projectManId;					//项目负责人代码
	@Column(name = "MAIN_PROJECT_CONTENT",length = 100)
	private String				mainProjectContent;				//项目主要内容
	@Column(name = "DESC_PROJECT_FOLLOW",length = 100)
	private String				descProjectFollow;				//项目跟踪情况说明
	@Temporal(TemporalType.DATE)
	@Column(name = "PROJECT_DATE",length = 20)
	private Date				projectDate;					//项目起始跟踪日期
	@Temporal(TemporalType.DATE)
	@Column(name = "BID_DATE",length = 20)
	private Date				bidDate;						//投标（报价）日期
	@Temporal(TemporalType.DATE)
	@Column(name = "ESTIMATE_SIGN_DATE",length = 20)
	private Date				estimateSignDate;				//预计合同签订日期
	@Column(name = "PROJECT_STATE_SELECT",length = 3)
	private String				projectStateSelect;				//项目跟踪标记   1on 2off
	@Column(name = "COMPETE_INFO",length = 50)
	private String				competeInfo;					//参与竞争厂家情况概述
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ESTIMATE_PROJECT_DATE",length = 20)
	private Date				estimateProjectDate;			//项目（工程）计划投运日期
	
	@Deprecated
	@Column(name = "OWN_PROBABILITY",length = 10)
	private String				ownProbability;					//中标概率
	
	@Column(name = "OWN_FACTORY",length = 20)
	private String				ownFactory;						//中标厂商
	@Column(name = "INTERVAL_OF_VARY",length = 20)
	private String				intervalOfVary;					//跟踪变更距离天数
	@Column(name = "TIME_OF_VARY",length = 10)
	private Long				timeOfVary;						//变更次数
	@Column(name = "REMARK",length = 200)
	private String				remark;							//备注
	@Column(name = "PROJECT_ECONOMY_ID",length = 20)
	private Long				projectEconomyId;				//工程经济代码
	
	
	
	@Column(name = "PROJECT_NAMEX",length = 50)
	private String				projectNameX;	 				 //工程名称
	
	@Column(name = "PROJECT_SUMMARY",length = 200)
	private String              projectSummary;   				//工程概况
	
	@Column(name = "CUSTOMER_DEPARTMENT",length = 100)
	private String             customerDepartment; 				 //客户联系人部门
	
	@Column(name = "CUSTOMER_LINKMAN_PHONE",length = 100)
	private String             customerLinkManPhone;  			//客户联系人电话
	
	@Column(name = "ITEM_TRACERESULT_REASON",length = 200)	
	private String itemTraceResultReason;						//项目跟踪结果原因
	
	@Column(name = "ITEM_TRACERESULT",length = 100)	
	private String itemTraceResult;								//项目跟踪结果
	
	@Column(name = "ITEM_RESPEOPLE_PHONE",length = 200)
	private String itemRespeoplePhone;							//项目负责人电话
	
	@Column(name="party_a_pro_id",length = 50)
	private String partyAProId;                 //甲方项目工程编号
	
	@Column(name = "IMPORTANT_ITEM")
	private Boolean importantItem;	             //	是否重点项目  

	@Column(name = "CON_STATE",length = 2)
	private String conState;							//0,初始状态  1,已转正

	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public String getSellBeforeNum() {
		return sellBeforeNum;
	}

	public void setSellBeforeNum(String sellBeforeNum) {
		this.sellBeforeNum = sellBeforeNum;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerProjectTypeId() {
		return customerProjectTypeId;
	}

	public void setCustomerProjectTypeId(String customerProjectTypeId) {
		this.customerProjectTypeId = customerProjectTypeId;
	}

	@Deprecated
	public String getBusinessTypeId() {
		return businessTypeId;
	}

	@Deprecated
	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStateId() {
		return projectStateId;
	}

	public void setProjectStateId(String projectStateId) {
		this.projectStateId = projectStateId;
	}

	@Deprecated
	public String getProjectTypeId() {
		return projectTypeId;
	}

	@Deprecated
	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public String getProjectStateFollowId() {
		return projectStateFollowId;
	}

	public void setProjectStateFollowId(String projectStateFollowId) {
		this.projectStateFollowId = projectStateFollowId;
	}

	public Double getProjectSum() {
		return projectSum;
	}

	public void setProjectSum(Double projectSum) {
		this.projectSum = projectSum;
	}

	public Double getBidSum() {
		return bidSum;
	}

	public void setBidSum(Double bidSum) {
		this.bidSum = bidSum;
	}

	public Double getOwnSum() {
		return ownSum;
	}

	public void setOwnSum(Double ownSum) {
		this.ownSum = ownSum;
	}

	public String getDutyDepartmentId() {
		return dutyDepartmentId;
	}

	public void setDutyDepartmentId(String dutyDepartmentId) {
		this.dutyDepartmentId = dutyDepartmentId;
	}

	public Long getLinkManId() {
		return linkManId;
	}

	public void setLinkManId(Long linkManId) {
		this.linkManId = linkManId;
	}

	public String getProjectManId() {
		return projectManId;
	}

	public void setProjectManId(String projectManId) {
		this.projectManId = projectManId;
	}

	public String getMainProjectContent() {
		return mainProjectContent;
	}

	public void setMainProjectContent(String mainProjectContent) {
		this.mainProjectContent = mainProjectContent;
	}

	public String getDescProjectFollow() {
		return descProjectFollow;
	}

	public void setDescProjectFollow(String descProjectFollow) {
		this.descProjectFollow = descProjectFollow;
	}

	public Date getProjectDate() {
		return projectDate;
	}

	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public Date getEstimateSignDate() {
		return estimateSignDate;
	}

	public void setEstimateSignDate(Date estimateSignDate) {
		this.estimateSignDate = estimateSignDate;
	}

	public String getProjectStateSelect() {
		return projectStateSelect;
	}

	public void setProjectStateSelect(String projectStateSelect) {
		this.projectStateSelect = projectStateSelect;
	}

	public String getCompeteInfo() {
		return competeInfo;
	}

	public void setCompeteInfo(String competeInfo) {
		this.competeInfo = competeInfo;
	}

	public Date getEstimateProjectDate() {
		return estimateProjectDate;
	}

	public void setEstimateProjectDate(Date estimateProjectDate) {
		this.estimateProjectDate = estimateProjectDate;
	}

	@Deprecated
	public String getOwnProbability() {
		return ownProbability;
	}

	@Deprecated
	public void setOwnProbability(String ownProbability) {
		this.ownProbability = ownProbability;
	}

	public String getOwnFactory() {
		return ownFactory;
	}

	public void setOwnFactory(String ownFactory) {
		this.ownFactory = ownFactory;
	}

	public String getIntervalOfVary() {
		return intervalOfVary;
	}

	public void setIntervalOfVary(String intervalOfVary) {
		this.intervalOfVary = intervalOfVary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getProjectEconomyId() {
		return projectEconomyId;
	}

	public void setProjectEconomyId(Long projectEconomyId) {
		this.projectEconomyId = projectEconomyId;
	}

	public String getProjectNameX() {
		return projectNameX;
	}

	public void setProjectNameX(String projectNameX) {
		this.projectNameX = projectNameX;
	}

	public String getProjectSummary() {
		return projectSummary;
	}

	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
	}

	public String getCustomerDepartment() {
		return customerDepartment;
	}

	public void setCustomerDepartment(String customerDepartment) {
		this.customerDepartment = customerDepartment;
	}

	public String getCustomerLinkManPhone() {
		return customerLinkManPhone;
	}

	public void setCustomerLinkManPhone(String customerLinkManPhone) {
		this.customerLinkManPhone = customerLinkManPhone;
	}

	public String getItemTraceResultReason() {
		return itemTraceResultReason;
	}

	public void setItemTraceResultReason(String itemTraceResultReason) {
		this.itemTraceResultReason = itemTraceResultReason;
	}

	public String getItemTraceResult() {
		return itemTraceResult;
	}

	public void setItemTraceResult(String itemTraceResult) {
		this.itemTraceResult = itemTraceResult;
	}

	public String getItemRespeoplePhone() {
		return itemRespeoplePhone;
	}

	public void setItemRespeoplePhone(String itemRespeoplePhone) {
		this.itemRespeoplePhone = itemRespeoplePhone;
	}

	public String getConState() {
		return conState;
	}

	public void setConState(String conState) {
		this.conState = conState;
	}

	public Long getTimeOfVary() {
		return timeOfVary;
	}

	public void setTimeOfVary(Long timeOfVary) {
		this.timeOfVary = timeOfVary;
	}

	public Boolean getImportantItem() {
		return importantItem;
	}

	public void setImportantItem(Boolean importantItem) {
		this.importantItem = importantItem;
	}

	public String getPartyAProId() {
		return partyAProId;
	}

	public void setPartyAProId(String partyAProId) {
		this.partyAProId = partyAProId;
	}
	

	
}

package com.baoz.yx.entity.programEconomy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Where;

import com.baoz.yx.entity.PriEntity;

/**
 * Alvin (yangyuan@baoz.com.cn)
 * <p>
 * 工程经济信息表
 * </p>
 */

@Entity
@Table(name = "yxo_program_economy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXOProgramEconomy extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "32", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; 
	@Column(name = "PROJECT_NUMBER", length = 20)
	private String projectNumber;                           // 工程编号
	@Column(name = "PROJECT_NAME", length = 50)
	private String projectName;                           //项目名称  
	@Column(name = "EXPLOYEE_ID", length = 20)
	private Long   exployeeId;                           // 跟踪营销员代码
	@Column(name = "BID_VERIFY", length = 5)
	private String bidVerify;                             // 招标文件确认标志 0-无；1-有  ??demo上是录入标志
	@Column(name = "ENTER_TIME", length = 20)
	private Date   enterTime;                             // 招标文件录入时间
	
	@Column(name = "STATE", length = 5)          
	private String state;                  // 工程经济状态 0-结束；1-售前合同；2-正式合同3-确认通过4-确认退回5-草稿6-待确认
	
	@Column(name = "SHELL_CONTRACT_ID", length = 10)
	private Long  shellContractId;                        // 售前合同系统号
	
	@Column(name = "INVESTMENT", length = 20)
	private Double investment;                              //AO 投资匡算 对应售前合同信息表  //改了
	
	
	@Column(name = "ENTER_FLAG", length = 5)
	private String  enterFlag;                            // 设备总表录入标志 对应售前合同信息表
	@Column(name = "EQUIP_ENTER_TIME", length = 20)
	private Date equipEnterTime;                           // 设备总表录入时间
	@Column(name = "PROJECT_NUMBERJ", length = 20)
	private String  projectNumberJ;                        // 项目编号 （甲方）是不是字符型的
	@Column(name = "CLIENT_FACTORY", length = 50)
	private String   clientFactory;                         // 客户项目单位
	@Column(name = "CLIENT_ORDERP", length = 20)
	private String  clientOrderP;                          // 客户项目负责人（电话）
	@Column(name = "PROJECT_LMP", length = 20)
	private String projectLMP;                             // 项目联系人（电话）
	@Column(name = "CLIENT_MANAGE_DEP", length = 50)
	private String clientManageDep;                        // 客户管理部门
	@Column(name = "MANAGE_DEP_LMP", length = 20)
	private String manageDepLMP;                           // 管理部门联系人（电话）
	
	@Column(name = "APPLY_TIME", length = 20)
	private Date applyTime;                               // 申请时间 addnew
	
	@Column(name = "CLIENT_ID", length = 20)
	private Long   clientId;                               //  客户id    addnew
	@Column(name="EQUIP_TOTAL_MONEY",length=20)
	
	
	//private String equipTotalMoney;                        //设备总表金额   added on 5.28 just now.
	
	private Double equipTotalMoney;                                                  //改了
	@OneToMany(targetEntity=YXOSectionInfo.class,mappedBy="economy")
	@Where(clause=" is_active = '1'")
	@JoinColumn(name="fk_program_economy_id")
	private List<YXOSectionInfo> sectionInfo;	
	
	
	public Double getEquipTotalMoney() {
		return equipTotalMoney;
	}
	public void setEquipTotalMoney(Double equipTotalMoney) {
		this.equipTotalMoney = equipTotalMoney;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getExployeeId() {
		return exployeeId;
	}
	public void setExployeeId(Long exployeeId) {
		this.exployeeId = exployeeId;
	}
	public String getBidVerify() {
		return bidVerify;
	}
	public void setBidVerify(String bidVerify) {
		this.bidVerify = bidVerify;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getShellContractId() {
		return shellContractId;
	}
	public void setShellContractId(Long shellContractId) {
		this.shellContractId = shellContractId;
	}
	
	public String getEnterFlag() {
		return enterFlag;
	}
	public void setEnterFlag(String enterFlag) {
		this.enterFlag = enterFlag;
	}
	public Date getEquipEnterTime() {
		return equipEnterTime;
	}
	public void setEquipEnterTime(Date equipEnterTime) {
		this.equipEnterTime = equipEnterTime;
	}
	public String getProjectNumberJ() {
		return projectNumberJ;
	}
	public void setProjectNumberJ(String projectNumberJ) {
		this.projectNumberJ = projectNumberJ;
	}
	
	public String getClientOrderP() {
		return clientOrderP;
	}
	public void setClientOrderP(String clientOrderP) {
		this.clientOrderP = clientOrderP;
	}
	public String getProjectLMP() {
		return projectLMP;
	}
	public void setProjectLMP(String projectLMP) {
		this.projectLMP = projectLMP;
	}
	public String getClientManageDep() {
		return clientManageDep;
	}
	public void setClientManageDep(String clientManageDep) {
		this.clientManageDep = clientManageDep;
	}
	public String getManageDepLMP() {
		return manageDepLMP;
	}
	public void setManageDepLMP(String manageDepLMP) {
		this.manageDepLMP = manageDepLMP;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getClientFactory() {
		return clientFactory;
	}
	public void setClientFactory(String clientFactory) {
		this.clientFactory = clientFactory;
	}
	public List<YXOSectionInfo> getSectionInfo() {
		return sectionInfo;
	}
	public void setSectionInfo(List<YXOSectionInfo> sectionInfo) {
		this.sectionInfo = sectionInfo;
	}
	public Double getInvestment() {
		return investment;
	}
	public void setInvestment(Double investment) {
		this.investment = investment;
	}
	
	


}

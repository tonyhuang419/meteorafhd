package com.baoz.yx.entity.contract;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

//合同项目主体信息表
@Entity
@Table(name = "yx_con_item_minfo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractItemMaininfo implements java.io.Serializable {
	private static final long serialVersionUID = -7719752273712241156L;


	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "73", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="con_item_minfo_sid",length = 20, nullable = false)
	private Long conItemMinfoSid;   //合同项目主体信息系统号
		

	@Column(name = "fk_con_main_info_sid",nullable = false)
	private Long contractMainInfo;  //合同主体信息系统号
	
	
	@Column(name="con_item_id",length = 20 )
	private String conItemId; 		 //合同项目编号//外键关联外协合同
	
	@Column(name="is_war",length = 20 )
	private Boolean War;			 //是否WAR项目
	
	@Column(name="item_res_dept",length = 100 )
	private String itemResDept; 		 //项目负责部门
	
	@Column(name="item_res_dept_p",length = 100 )
	private String itemResDeptP;		 //项目负责人
	
	@Column(name="con_item_cost_sure")
	private Long conItemCostSure; 		//合同项目成本确认状态   //0未录入 1已录入 2待确认 3确认通过 4确认退回
	
	@Column(name="is_con_mod_info")
	private Boolean ConModInfo;  		//是否属于合同变更信息
	
	@Column(name="account_money")
	private BigDecimal accountmoney;		//总金额
	
	@Column(name="remain_bill")
	private Double remainBill;		//剩余发票....项目成本确认用....导入时如果不存在，-1标识
	
	@Column(name="remain_assistance")
	private Double remainAssistance;		//剩余外协....项目成本确认用....导入时如果不存在，-1标识
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	
	@OneToMany (targetEntity=ContractItemInfo.class , mappedBy="contractItemMaininfo")
	@JoinColumn(name="fk_con_item_minfo_sid")
	@OrderBy(value = "conItemInfoSid")
	private List<ContractItemInfo> contractItemInfolist;	
	
	public Long getConItemMinfoSid() {
		return conItemMinfoSid;
	}

	public void setConItemMinfoSid(Long conItemMinfoSid) {
		this.conItemMinfoSid = conItemMinfoSid;
	}

	public Long getContractMainInfo() {
		return contractMainInfo;
	}



	public void setContractMainInfo(Long contractMainInfo) {
		this.contractMainInfo = contractMainInfo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String getConItemId() {
		return conItemId;
	}

	public void setConItemId(String conItemId) {
		this.conItemId = conItemId;
	}

	public Boolean getWar() {
		return War;
	}

	public void setWar(Boolean war) {
		War = war;
	}

	public String getItemResDept() {
		return itemResDept;
	}

	public void setItemResDept(String itemResDept) {
		this.itemResDept = itemResDept;
	}

	public String getItemResDeptP() {
		return itemResDeptP;
	}

	public void setItemResDeptP(String itemResDeptP) {
		this.itemResDeptP = itemResDeptP;
	}



	public Boolean getConModInfo() {
		return ConModInfo;
	}

	public void setConModInfo(Boolean conModInfo) {
		ConModInfo = conModInfo;
	}

	public List<ContractItemInfo> getContractItemInfolist() {
		return contractItemInfolist;
	}

	public void setContractItemInfolist(List<ContractItemInfo> contractItemInfolist) {
		this.contractItemInfolist = contractItemInfolist;
	}



	public Long getConItemCostSure() {
		return conItemCostSure;
	}

	public void setConItemCostSure(Long conItemCostSure) {
		this.conItemCostSure = conItemCostSure;
	}


	public Double getRemainBill() {
		return remainBill;
	}

	public void setRemainBill(Double remainBill) {
		this.remainBill = remainBill;
	}

	public Double getRemainAssistance() {
		return remainAssistance;
	}

	public void setRemainAssistance(Double remainAssistance) {
		this.remainAssistance = remainAssistance;
	}

	public BigDecimal getAccountmoney() {
		return accountmoney;
	}

	public void setAccountmoney(BigDecimal accountmoney) {
		this.accountmoney = accountmoney;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}

}
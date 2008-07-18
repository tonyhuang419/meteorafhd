package com.baoz.yx.entity.contract;

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

//合同主体信息表


//开发备注：变更合同时,合同状态有“变更保存”、“变更待确认”、“变更退回”、“变更通过/正式合同”
//“变更保存”，“正式合同”可同时被查询出。“变更保存”可以做提交动作。提交后，即只能查询出，提交信息



//查询变更信息时可以通过“保存时间”来判断是否为变更信息。


@Entity
@Table(name = "yx_changing_con_main_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ChangingContractMainInfo  implements Serializable{
	private static final long serialVersionUID = 7774567155267501785L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "70", allocationSize = 1)
	@Column(name="con_main_info_sid",length = 20)
	private Long conMainInfoSid;            //合同主体信息系统号
	
	@Column(name="con_id",length = 50 )
	private String conId;                      //正式合同号

	@Column(name="con_name",length = 100)
	private String conName;                     //合同名称

	@Column(name="con_type",length=20)
	private  String  conType;                     //合同性质

	@Column(name="pre_con_sysid",length=20)
	private Long preConSysid;                //售前合同系统号
	
	@Column(name="sale_man",length=20)
	private Long saleMan;                //销售员系统号

	@Column(name="con_customer",length=20)
	private Long conCustomer;                //合同客户系统号

	@Column(name="custom_linkman_id")
	private Long linkManId;               //客户联系人ID
	
	@Column(name="custom_event_type")
	private String customereventtype;      //客户项目类型
	
    @Column(name="item_customer",length=20)
	private Long itemCustomer;                //项目客户/最终客户系统号
    
    @Column(name="bill_customer",length=20)
	private Long billCustomer;                //合同开票客户	
	
    @Column(name="main_item_department",length=20)
	private String mainItemDept;               //主项目负责部门	
	
	@Column(name = "is_drawback_con")
	private Boolean conDrawback;             //是否退税

	@Column(name = "is_bid_con")
	private Boolean conBid;	             //	是否中标合同

	@Temporal(TemporalType.DATE)
	@Column(name = "con_sign_date") 
	private Date conSignDate; 	          //合同签定日期

	@Temporal(TemporalType.DATE)
	@Column(name = "con_start_date") 
	private Date conStartDate; 	         //合同起始日期

	@Temporal(TemporalType.DATE)
	@Column(name = "con_end_time") 
	private Date conEndDate;                 //合同结束日期

	@Column(name="con_tax_tamount",length = 20,scale = 2)
	private Double conTaxTamount;                //合同含税总金额

	@Column(name="party_a_pro_id",length = 50)
	private String partyAProId;                 //甲方项目工程编号

	@Column(name="party_a_con_id",length = 50)
	private String partyAConId;                 //甲方合同号

	@Column(name="fin_pro_stuff",length = 1000)
	private String finProStuff;                //完工应交材料

	@Column(name="main_item_people",length = 50)
	private String mainItemPeople;               //主项目负责人
	
	@Column(name="main_item_phone",length = 50)
	private String mainItemPhone;               //主项目负责人电话

	@Column(name="con_bill_state",length = 1)
	private Long conBillState;                //合同开票状态

	@Column(name="con_rece_state",length = 1)
	private Long conReceState;                 //合同收款状态

	@Column(name="con_state",length = 2)
	private Long conState;                    //合同状态

	@Column(name = "is_into_year_con")
	private Boolean IntoYearCon;                  //是否纳入年度运维合同

	@Column(name = "is_exist_purchase")
	private Boolean  ExistPurchase;             //是否存在申购

	@Column(name = "is_exist_own_prod")
	private Boolean ExistOwnProd ;            //是否存在自有产品

	@Column(name = "is_exist_iteminfo")
	private Boolean ExistIteminfo;               //是否存在项目信息

	@Column(name = "is_exist_sidehelp")
	private Boolean ExistSidehelp;               //是否存在外协信息

	@Column(name = "is_exist_fin_accout")
	private Boolean ExistFinAccout ;             //是否存在其它信息

	@Column(name = "is_exist_c")
	private Boolean ExistC;                      //是否存在变更

	@Column(name = "is_con_mod_info")
	private Boolean ConModInfo;             //是否属于合同变更信息...0不属于变更信息  1属于变更信息
	
	@Column(name = "has_item_id")
	private Boolean HasItemId;             //项目号是否录完 1为录完 0未录完（部分录入项目号的情况下也属于未录完）
	
	@Column(name = "CONTRACT_TYPE")
	private String ContractType;            //合同类型
	
	@Column(name = "FINAL_ACCOUNT")
	private String FinalAccount;        //预决算与否 0非预决算 1预决算


	@Column(name="op_time",length = 20)
	private Date opTime;   //操作时间
	
	@Column(name="op_people",length=100)
	private Long opPeople;  //变更操作人
	

	public Long getConMainInfoSid() {
		return conMainInfoSid;
	}


	public void setConMainInfoSid(Long conMainInfoSid) {
		this.conMainInfoSid = conMainInfoSid;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public Long getPreConSysid() {
		return preConSysid;
	}

	public void setPreConSysid(Long preConSysid) {
		this.preConSysid = preConSysid;
	}

	public Long getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}

	public Long getConCustomer() {
		return conCustomer;
	}

	public void setConCustomer(Long conCustomer) {
		this.conCustomer = conCustomer;
	}

	public Long getItemCustomer() {
		return itemCustomer;
	}

	public void setItemCustomer(Long itemCustomer) {
		this.itemCustomer = itemCustomer;
	}

	public Long getBillCustomer() {
		return billCustomer;
	}

	public void setBillCustomer(Long billCustomer) {
		this.billCustomer = billCustomer;
	}

	public String getMainItemDept() {
		return mainItemDept;
	}

	public void setMainItemDept(String mainItemDept) {
		this.mainItemDept = mainItemDept;
	}

	public Boolean isConDrawback() {
		return conDrawback;
	}

	public void setConDrawback(Boolean conDrawback) {
		this.conDrawback = conDrawback;
	}

	public Boolean isConBid() {
		return conBid;
	}

	public void setConBid(Boolean conBid) {
		this.conBid = conBid;
	}

	public Date getConSignDate() {
		return conSignDate;
	}

	public void setConSignDate(Date conSignDate) {
		this.conSignDate = conSignDate;
	}

	public Date getConStartDate() {
		return conStartDate;
	}

	public void setConStartDate(Date conStartDate) {
		this.conStartDate = conStartDate;
	}

	public Date getConEndDate() {
		return conEndDate;
	}

	public void setConEndDate(Date conEndDate) {
		this.conEndDate = conEndDate;
	}

	public String getPartyAProId() {
		return partyAProId;
	}

	public void setPartyAProId(String partyAProId) {
		this.partyAProId = partyAProId;
	}

	public String getPartyAConId() {
		return partyAConId;
	}

	public void setPartyAConId(String partyAConId) {
		this.partyAConId = partyAConId;
	}

	public String getFinProStuff() {
		return finProStuff;
	}

	public void setFinProStuff(String finProStuff) {
		this.finProStuff = finProStuff;
	}

	public String getMainItemPeople() {
		return mainItemPeople;
	}

	public void setMainItemPeople(String mainItemPeople) {
		this.mainItemPeople = mainItemPeople;
	}

	public String getMainItemPhone() {
		return mainItemPhone;
	}

	public void setMainItemPhone(String mainItemPhone) {
		this.mainItemPhone = mainItemPhone;
	}

	public Long getConBillState() {
		return conBillState;
	}

	public void setConBillState(Long conBillState) {
		this.conBillState = conBillState;
	}

	public Long getConReceState() {
		return conReceState;
	}

	public void setConReceState(Long conReceState) {
		this.conReceState = conReceState;
	}

	public Long getConState() {
		return conState;
	}

	public void setConState(Long conState) {
		this.conState = conState;
	}

	public String getCustomereventtype() {
		return customereventtype;
	}


	public void setCustomereventtype(String customereventtype) {
		this.customereventtype = customereventtype;
	}


	public Boolean getIntoYearCon() {
		return IntoYearCon;
	}


	public void setIntoYearCon(Boolean intoYearCon) {
		IntoYearCon = intoYearCon;
	}


	public Boolean getExistPurchase() {
		return ExistPurchase;
	}


	public void setExistPurchase(Boolean existPurchase) {
		ExistPurchase = existPurchase;
	}


	public Boolean getExistOwnProd() {
		return ExistOwnProd;
	}


	public void setExistOwnProd(Boolean existOwnProd) {
		ExistOwnProd = existOwnProd;
	}


	public Boolean getExistIteminfo() {
		return ExistIteminfo;
	}


	public void setExistIteminfo(Boolean existIteminfo) {
		ExistIteminfo = existIteminfo;
	}


	public Boolean getExistSidehelp() {
		return ExistSidehelp;
	}


	public void setExistSidehelp(Boolean existSidehelp) {
		ExistSidehelp = existSidehelp;
	}


	public Boolean getExistFinAccout() {
		return ExistFinAccout;
	}


	public void setExistFinAccout(Boolean existFinAccout) {
		ExistFinAccout = existFinAccout;
	}


	public Boolean getExistC() {
		return ExistC;
	}


	public void setExistC(Boolean existC) {
		ExistC = existC;
	}


	public Boolean getConModInfo() {
		return ConModInfo;
	}


	public void setConModInfo(Boolean conModInfo) {
		ConModInfo = conModInfo;
	}



	public Date getOpTime() {
		return opTime;
	}


	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}


	public Long getOpPeople() {
		return opPeople;
	}


	public void setOpPeople(Long opPeople) {
		this.opPeople = opPeople;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Boolean getConDrawback() {
		return conDrawback;
	}


	public Boolean getConBid() {
		return conBid;
	}


	public Long getLinkManId() {
		return linkManId;
	}


	public void setLinkManId(Long linkManId) {
		this.linkManId = linkManId;
	}


	public Double getConTaxTamount() {
		return conTaxTamount;
	}


	public void setConTaxTamount(Double conTaxTamount) {
		this.conTaxTamount = conTaxTamount;
	}


	public Boolean getHasItemId() {
		return HasItemId;
	}


	public void setHasItemId(Boolean hasItemId) {
		HasItemId = hasItemId;
	}


	public String getContractType() {
		return ContractType;
	}


	public void setContractType(String contractType) {
		ContractType = contractType;
	}


	public String getFinalAccount() {
		return FinalAccount;
	}


	public void setFinalAccount(String finalAccount) {
		FinalAccount = finalAccount;
	}


	
}

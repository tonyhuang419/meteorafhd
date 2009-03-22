package com.baoz.yx.entity.contract;


import java.math.BigDecimal;
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

//实际合同开票收款计划表

//开票性质，开票内容 未作关联
@Entity
@Table(name = "yx_real_con_bc_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class RealContractBillandRecePlan implements java.io.Serializable {

	private static final long serialVersionUID = -5602017646049754979L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "79", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="real_con_billpro_sid")
	private Long realConBillproSid;  //实际合同开票计划系统号
	
	@Column(name = "fk_init_con_billpro_sid")
	private Long initContractBillpro;  //原始合同开票收款计划系统号
	
	@Column(name = "bill_sure_sign")
	private Boolean billSureSign;               //开票确定收入标志
	
	@Column(name="bill_content",length = 100)
	private String billContent;              //开票内容
	
	@Column(name = "base")
	private Long base;      //基准,是否含税  0-不含税；1-含税；  bu yong le 
	
	@Column(name = "fk_bill_nature")
	private String billNature;   //开票性质(连接类型管理表)
	
	@Column(name = "fk_bill_type")
	private String billType;    //开票类型(连接类型管理表)

	@Column(name = "real_bill_amount",length = 20,scale = 2)
	private BigDecimal realBillAmount;          //实际预计开票金额，基准金额
	
	@Column(name = "real_rece_amount",length = 20,scale = 2)
	private BigDecimal realReceAmount;  //实际预计收款金额，基准金额
	
	///////////含税金额
	@Column(name = "real_tax_bill_amount",length = 20,scale = 2)
	private BigDecimal realTaxBillAmount;  //预计开票金额（含税）
	
	@Column(name = "real_tax_rece_amount",length = 20,scale = 2)
	private BigDecimal realTaxReceAmount;  //预计收款金额（含税）
	///////////
	
	//////////计划内外标志
	@Column(name = "bill_plan_in_month_plan")
	@Deprecated
	private Boolean billPlanInMonthPlan; // 开票计划是否在月度开票计划里，1是在，0或null不在
	
	@Column(name = "rece_plan_in_month_plan")
	@Deprecated
	private Boolean recePlanInMonthPlan; // 收款计划是否在月度收款计划里，1是在，0或null不在
	//////////
	@Column(name = "real_arrive_amount",length = 20,scale = 2)
	private BigDecimal realArriveAmount;  //实际到款金额，在收款管理里核消的金额
	
	@Column(name = "is_unite_bill")
	private Boolean uniteBill;            //是否统一开票
	
	@Temporal(TemporalType.DATE)
	@Column(name = "real_pred_bill_date")
	private Date realPredBillDate;             //实际预计开票日期
	
	@Temporal(TemporalType.DATE)
	@Column(name = "real_pred_rece_date")
	private Date realPredReceDate;  //实际预计收款日期
	
	//该变量暂时保留
//	@Column(name="bill_state",length = 1)
//	private Long billState;                //开票计划状态
	
	@Column(name="is_exist_change")
	private Boolean existC;                 //是否存在变更
	
	@Column(name = "is_con_mod_info")
	private Boolean conModInfo;             //是否属于合同变更信息

	@Column(name = "fk_con_main_sid")
	private Long conMainInfoSid;			//合同主体信息系统号
	
	@Column(name = "fk_con_item_minfo_sid")
	private Long contractItemMaininfo;  			//合同项目主体信息系统号
	
	@Column(name = "fk_con_i_stage_sid")
	private Long conItemStage; 				//合同项目阶段系统号
	
	/////////////////帐龄
	@Column(name = "logic_day_account_age")
	private Integer logicDayAccountAge; //逻辑帐龄，天数
	
	@Column(name = "logic_month_account_age")
	private Integer logicMonthAccountAge;//逻辑帐龄，月数
	
	@Column(name = "real_day_account_age")
	private Integer realDayAccountAge;//实际帐龄，天数
	
	@Column(name = "real_month_account_age")
	private Integer realMonthAccountAge;//实际帐龄，月数
	////////////////
	
	////////////////
	@Column(name = "bill_change_count")
	private Integer billChangeCount; // 开票计划变更次数
	
	@Column(name = "rece_change_count")
	private Integer receChangeCount; // 收款计划变更次数
	////////////////
	@Column(name="RELATION_RECE_AMOUNT",length=20)
	private Double relationReceAmount;//关联收据总金额

	@Column(name="BILL_INVOICE_AMOUNT",length=20)
	private Double billInvoiceAmount;//已开票总金额
	
	@Column(name="SHOULD_RECE_AMOUNT",length=20)
	private Double shouldReceAmount;//应收总额
	
	@Column(name="CURRENT_ARRIVE_AMOUNT",length=20)
	private Double currentArriveAmount;//本次到款
	
	@Temporal(TemporalType.DATE)
	@Column(name = "real_now_bill_date")
	private Date realNowBillDate;             //最后一次开票时间
	
	@Temporal(TemporalType.DATE)
	@Column(name = "real_now_rece_date")
	private Date realNowReceDate;             //最后一次收款时间
	
	@Column(name="apply_bill_state",length=20)
	private Long applyBillState;      //同申请单状态    1保存 2待确认   3确认通过 4确认退回 5 已开票 7已处理 6 已签收（处理完的才签收的）
	
	public Double getBillInvoiceAmount() {
		return billInvoiceAmount;
	}

	public void setBillInvoiceAmount(Double billInvoiceAmount) {
		this.billInvoiceAmount = billInvoiceAmount;
	}

	public Double getRelationReceAmount() {
		return relationReceAmount;
	}

	public void setRelationReceAmount(Double relationReceAmount) {
		this.relationReceAmount = relationReceAmount;
	}

	public Long getRealConBillproSid() {
		return realConBillproSid;
	}

	public void setRealConBillproSid(Long realConBillproSid) {
		this.realConBillproSid = realConBillproSid;
	}

	public Long getInitContractBillpro() {
		return initContractBillpro;
	}

	public void setInitContractBillpro(Long initContractBillpro) {
		this.initContractBillpro = initContractBillpro;
	}

	public Boolean getBillSureSign() {
		return billSureSign;
	}

	public void setBillSureSign(Boolean billSureSign) {
		this.billSureSign = billSureSign;
	}

	public String getBillContent() {
		return billContent;
	}

	public void setBillContent(String billContent) {
		this.billContent = billContent;
	}

	public Long getBase() {
		return base;
	}

	public void setBase(Long base) {
		this.base = base;
	}

	public String getBillNature() {
		return billNature;
	}

	public void setBillNature(String billNature) {
		this.billNature = billNature;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public BigDecimal getRealBillAmount() {
		return realBillAmount;
	}

	public void setRealBillAmount(BigDecimal realBillAmount) {
		this.realBillAmount = realBillAmount;
	}

	public BigDecimal getRealReceAmount() {
		return realReceAmount;
	}

	public void setRealReceAmount(BigDecimal realReceAmount) {
		this.realReceAmount = realReceAmount;
	}

	public BigDecimal getRealTaxBillAmount() {
		return realTaxBillAmount;
	}

	public void setRealTaxBillAmount(BigDecimal realTaxBillAmount) {
		this.realTaxBillAmount = realTaxBillAmount;
	}

	public BigDecimal getRealTaxReceAmount() {
		return realTaxReceAmount;
	}

	public void setRealTaxReceAmount(BigDecimal realTaxReceAmount) {
		this.realTaxReceAmount = realTaxReceAmount;
	}

	public Integer getLogicDayAccountAge() {
		return logicDayAccountAge;
	}

	public void setLogicDayAccountAge(Integer logicDayAccountAge) {
		this.logicDayAccountAge = logicDayAccountAge;
	}

	public Integer getLogicMonthAccountAge() {
		return logicMonthAccountAge;
	}

	public void setLogicMonthAccountAge(Integer logicMonthAccountAge) {
		this.logicMonthAccountAge = logicMonthAccountAge;
	}

	public Integer getRealDayAccountAge() {
		return realDayAccountAge;
	}

	public void setRealDayAccountAge(Integer realDayAccountAge) {
		this.realDayAccountAge = realDayAccountAge;
	}

	public Integer getRealMonthAccountAge() {
		return realMonthAccountAge;
	}

	public void setRealMonthAccountAge(Integer realMonthAccountAge) {
		this.realMonthAccountAge = realMonthAccountAge;
	}

	public BigDecimal getRealArriveAmount() {
		return realArriveAmount;
	}

	public void setRealArriveAmount(BigDecimal realArriveAmount) {
		this.realArriveAmount = realArriveAmount;
	}

	public Boolean getUniteBill() {
		return uniteBill;
	}

	public void setUniteBill(Boolean uniteBill) {
		this.uniteBill = uniteBill;
	}

	public Date getRealPredBillDate() {
		return realPredBillDate;
	}

	public void setRealPredBillDate(Date realPredBillDate) {
		this.realPredBillDate = realPredBillDate;
	}

	public Date getRealPredReceDate() {
		return realPredReceDate;
	}

	public void setRealPredReceDate(Date realPredReceDate) {
		this.realPredReceDate = realPredReceDate;
	}

	public Boolean getExistC() {
		return existC;
	}

	public void setExistC(Boolean existC) {
		this.existC = existC;
	}

	public Boolean getConModInfo() {
		return conModInfo;
	}

	public void setConModInfo(Boolean conModInfo) {
		this.conModInfo = conModInfo;
	}

	public Long getConMainInfoSid() {
		return conMainInfoSid;
	}

	public void setConMainInfoSid(Long conMainInfoSid) {
		this.conMainInfoSid = conMainInfoSid;
	}

	public Long getContractItemMaininfo() {
		return contractItemMaininfo;
	}

	public void setContractItemMaininfo(Long contractItemMaininfo) {
		this.contractItemMaininfo = contractItemMaininfo;
	}

	public Long getConItemStage() {
		return conItemStage;
	}

	public void setConItemStage(Long conItemStage) {
		this.conItemStage = conItemStage;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Double getShouldReceAmount() {
		return shouldReceAmount;
	}

	public void setShouldReceAmount(Double shouldReceAmount) {
		this.shouldReceAmount = shouldReceAmount;
	}

	public Double getCurrentArriveAmount() {
		return currentArriveAmount;
	}

	public void setCurrentArriveAmount(Double currentArriveAmount) {
		this.currentArriveAmount = currentArriveAmount;
	}
	@Deprecated
	public Boolean getBillPlanInMonthPlan() {
		return billPlanInMonthPlan;
	}
	@Deprecated
	public void setBillPlanInMonthPlan(Boolean billPlanInMonthPlan) {
		this.billPlanInMonthPlan = billPlanInMonthPlan;
	}
	@Deprecated
	public Boolean getRecePlanInMonthPlan() {
		return recePlanInMonthPlan;
	}
	@Deprecated
	public void setRecePlanInMonthPlan(Boolean recePlanInMonthPlan) {
		this.recePlanInMonthPlan = recePlanInMonthPlan;
	}

	public Integer getBillChangeCount() {
		return billChangeCount;
	}

	public void setBillChangeCount(Integer billChangeCount) {
		this.billChangeCount = billChangeCount;
	}

	public Integer getReceChangeCount() {
		return receChangeCount;
	}

	public void setReceChangeCount(Integer receChangeCount) {
		this.receChangeCount = receChangeCount;
	}

	public Date getRealNowBillDate() {
		return realNowBillDate;
	}

	public void setRealNowBillDate(Date realNowBillDate) {
		this.realNowBillDate = realNowBillDate;
	}

	public Date getRealNowReceDate() {
		return realNowReceDate;
	}

	public void setRealNowReceDate(Date realNowReceDate) {
		this.realNowReceDate = realNowReceDate;
	}

	public Long getApplyBillState() {
		return applyBillState;
	}

	public void setApplyBillState(Long applyBillState) {
		this.applyBillState = applyBillState;
	}


	
}
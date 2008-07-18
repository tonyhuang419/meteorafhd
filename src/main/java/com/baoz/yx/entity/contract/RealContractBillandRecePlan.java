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
	private Long base;      //基准,是否含税  0-不含税；1-含税；
	
	@Column(name = "fk_bill_nature")
	private String billNature;   //开票性质(连接类型管理表)
	
	@Column(name = "fk_bill_type")
	private String billType;    //开票类型(连接类型管理表)

	@Column(name = "real_bill_amount",length = 20,scale = 2)
	private BigDecimal realBillAmount;          //实际预计开票金额
	
	@Column(name = "real_rece_amount",length = 20,scale = 2)
	private BigDecimal realReceAmount;  //实际预计收款金额
	
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

	
}
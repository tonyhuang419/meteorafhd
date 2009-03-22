package com.baoz.yx.entity.importfile;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "YX_IMPORT_TEMPFILE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ImportTempFile implements Serializable {
	
	private static final long serialVersionUID = 3558001003021L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "31", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; // 标示列
	@Column(name = "CON_ID", length = 30)
	private String conId; // 合同号

	@Column(name = "CON_NAME", length = 100)
	private String conName;// 合同名称

	@Column(name = "REAL_BILL_MONEY", length = 20)
	private BigDecimal realBillMoney;// 实际开票金额

	@Column(name = "REAL_REVE_MONEY", length = 20)
	private BigDecimal realReveMoney;// 实际收款金额

	@Column(name = "REAL_ARRIVE_MONEY", length = 20)
	private BigDecimal realArriveMoney;// 实际到款金额

	@Column(name = "SIGN", length = 2)
	private String sign;// 标示是一次导入的数据
	
	@Column(name="CON_MONEY",length=20)
	private BigDecimal conMoney;//合同金额
	
	@Column(name="CON_SALE_MAN",length=30)
	private String conSaleMan;//销售员姓名
	
	@Column(name="CON_ITEM_ID",length=50)
	private String conItemId;//项目编号

	public String getConItemId() {
		return conItemId;
	}

	public void setConItemId(String conItemId) {
		this.conItemId = conItemId;
	}

	public BigDecimal getConMoney() {
		return conMoney;
	}

	public void setConMoney(BigDecimal conMoney) {
		this.conMoney = conMoney;
	}

	public String getConSaleMan() {
		return conSaleMan;
	}

	public void setConSaleMan(String conSaleMan) {
		this.conSaleMan = conSaleMan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getRealBillMoney() {
		return realBillMoney;
	}

	public void setRealBillMoney(BigDecimal realBillMoney) {
		this.realBillMoney = realBillMoney;
	}

	public BigDecimal getRealReveMoney() {
		return realReveMoney;
	}

	public void setRealReveMoney(BigDecimal realReveMoney) {
		this.realReveMoney = realReveMoney;
	}

	public BigDecimal getRealArriveMoney() {
		return realArriveMoney;
	}

	public void setRealArriveMoney(BigDecimal realArriveMoney) {
		this.realArriveMoney = realArriveMoney;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	

}

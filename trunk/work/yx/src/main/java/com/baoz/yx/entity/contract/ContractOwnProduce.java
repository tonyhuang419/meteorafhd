package com.baoz.yx.entity.contract;


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

//合同自有产品信息表

//为了调试方便，多数字段未定义“不能为空”
//自有产品序号未作关联
@Entity
@Table(name = "yx_con_own_prod")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractOwnProduce  implements java.io.Serializable {
	private static final long serialVersionUID = 1078033703299210104L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "71", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="con_own_prod_sid",length = 20)
	private Long conOwnProdSid;  //合同自有产品系统号 
	
	@Column(name = "fk_con_main_info_sid")
	private Long conMinfo;  //合同主体信息系统号
	
	@Column(name = "fk_self_product_sid")
	private Long ownProduceId;    //自有产品信息系统号
	
	@Column(name = "con_own_prod_amount",length=3)
	private Long conOwnProdAmount;  //合同自由产品数量
	
	@Column(name = "con_own_prod_price",length=20)
	private Double conOwnProdPrice;   //合同自由产品价格
	
	@Column(name = "is_con_mod_info") 
	private Boolean ConModInfo;   //是否属于合同变更信息

	public Long getConOwnProdSid() {
		return conOwnProdSid;
	}

	public void setConOwnProdSid(Long conOwnProdSid) {
		this.conOwnProdSid = conOwnProdSid;
	}



	public Long getConOwnProdAmount() {
		return conOwnProdAmount;
	}

	public void setConOwnProdAmount(Long conOwnProdAmount) {
		this.conOwnProdAmount = conOwnProdAmount;
	}

	public Boolean getConModInfo() {
		return ConModInfo;
	}

	public void setConModInfo(Boolean conModInfo) {
		ConModInfo = conModInfo;
	}

	public Long getConMinfo() {
		return conMinfo;
	}

	public void setConMinfo(Long conMinfo) {
		this.conMinfo = conMinfo;
	}

	public boolean isConModInfo() {
		return ConModInfo;
	}

	public void setConModInfo(boolean conModInfo) {
		ConModInfo = conModInfo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getOwnProduceId() {
		return ownProduceId;
	}

	public void setOwnProduceId(Long ownProduceId) {
		this.ownProduceId = ownProduceId;
	}

	public Double getConOwnProdPrice() {
		return conOwnProdPrice;
	}

	public void setConOwnProdPrice(Double conOwnProdPrice) {
		this.conOwnProdPrice = conOwnProdPrice;
	}

	
}
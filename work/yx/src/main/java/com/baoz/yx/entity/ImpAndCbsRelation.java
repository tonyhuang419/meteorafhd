package com.baoz.yx.entity;

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

//重点工程项目系统号，售前关联表

@Entity
@Table(name = "YX_IMPANDCBS_RELAION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class ImpAndCbsRelation implements java.io.Serializable{
	private static final long serialVersionUID = -2507862306246743822L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "impandcbsRelaion", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id")
	private Long id;  

	@Column(name = "fk_cbs_id",  length = 20)
	private Long cbsId;       //售前系统号
	
	@Column(name = "fk_imp_id",  length = 20)
	private Long impID;			 //重点工程项目系统号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCbsId() {
		return cbsId;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public Long getImpID() {
		return impID;
	}

	public void setImpID(Long impID) {
		this.impID = impID;
	}


}

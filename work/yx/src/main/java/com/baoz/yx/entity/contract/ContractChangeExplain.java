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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;


@Entity
@Table(name = "YX_CONTRACT_CHANGE_EXPLAIN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractChangeExplain implements Serializable {

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "81", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name = "id", length = 20, nullable = false)
	private Long id;     //变更说明系统号
	
	@Column(name = "CONTRACT_ID")
	private Long contractid;
	
	@Column(name = "CHANGE_EXPLAIN")
	private String changeexplain;
	
	@Column(name = "CHANGE_DATE")
	private Date changedate;
	
	@Column(name = "CHANGE_PERSON_ID")
	private Long changepersonid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractid() {
		return contractid;
	}

	public void setContractid(Long contractid) {
		this.contractid = contractid;
	}

	public String getChangeexplain() {
		return changeexplain;
	}

	public void setChangeexplain(String changeexplain) {
		this.changeexplain = changeexplain;
	}

	public Date getChangedate() {
		return changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public Long getChangepersonid() {
		return changepersonid;
	}

	public void setChangepersonid(Long changepersonid) {
		this.changepersonid = changepersonid;
	}
		
	
}

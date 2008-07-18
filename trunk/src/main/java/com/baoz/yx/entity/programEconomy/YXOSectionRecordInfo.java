
package com.baoz.yx.entity.programEconomy;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.baoz.yx.entity.PriEntity;

/**
 * Alvin (yangyuan@baoz.com.cn)
 * <p>
 * 阶段履历表
 * </p>
 */

@Entity
@Table(name = "yxo_section_record_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXOSectionRecordInfo extends PriEntity  implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "48", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; 
	@Column(name = "SECTION_RECORD_ID", length = 20)
	private Long srId;                              //阶段履历代码
	
	@Column(name = "TYPE_CODE", length = 50)
	private String  typeCodee;                             // 类型代码（工程经济组成）   这个是不是费用组成？？
	
	@Column(name = "MONEY", length = 20)
	private Double moneyy;                                 //金额
	
	@ManyToOne
	@JoinColumn(name = "fk_section_record_info")
	private YXOSectionRecord record;          //阶段信息many->one工程经济信息 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getTypeCodee() {
		return typeCodee;
	}
	public void setTypeCodee(String typeCodee) {
		this.typeCodee = typeCodee;
	}
	
	public Double getMoneyy() {
		return moneyy;
	}
	public void setMoneyy(Double moneyy) {
		this.moneyy = moneyy;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Long getSrId() {
		return srId;
	}
	public void setSrId(Long srId) {
		this.srId = srId;
	}
	public YXOSectionRecord getRecord() {
		return record;
	}
	public void setRecord(YXOSectionRecord record) {
		this.record = record;
	}
	
}




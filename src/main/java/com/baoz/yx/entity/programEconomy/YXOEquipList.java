
package com.baoz.yx.entity.programEconomy;

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

import com.baoz.yx.entity.PriEntity;

/**
 * Alvin (yangyuan@baoz.com.cn)
 * <p>
 * 设备清单表
 * </p>
 */

@Entity
@Table(name = "yxo_equipment_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXOEquipList extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "35", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; 
	@Column(name = "PROJECT_ECONOMY_ID", length = 20)
	private Long projectEconomyId;                           //工程经济代码
	@Column(name = "ENTER_TIME", length = 20)
	private Date   enterTime;                             // 录入日期 系统默认录入当日时间，允许修改
	@Column(name = "EXPLOYEE_ID", length = 20)
	private Long   exployeeId;                           //录入人代码 对应员工信息代码表
	
	@Column(name="LIST_NAME",length=100)
	private String listName;                            //清单名称
	@Column(name="S_EQUIP_NUMBER",length=20)            
	private String sequipNumber;                        //起始设备编号
	@Column(name="E_EQUIP_NUMBER",length=20)
	private String eequipNumber;                        //结束设备编号
	@Column(name="TOTAL" ,length=20)                    //合计次数
	private Long total;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectEconomyId() {
		return projectEconomyId;
	}
	public void setProjectEconomyId(Long projectEconomyId) {
		this.projectEconomyId = projectEconomyId;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public Long getExployeeId() {
		return exployeeId;
	}
	public void setExployeeId(Long exployeeId) {
		this.exployeeId = exployeeId;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getSequipNumber() {
		return sequipNumber;
	}
	public void setSequipNumber(String sequipNumber) {
		this.sequipNumber = sequipNumber;
	}
	public String getEequipNumber() {
		return eequipNumber;
	}
	public void setEequipNumber(String eequipNumber) {
		this.eequipNumber = eequipNumber;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}



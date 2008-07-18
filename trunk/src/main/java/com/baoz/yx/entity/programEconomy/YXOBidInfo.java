
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
 * 招标文件信息表
 * </p>
 */

@Entity
@Table(name = "yxo_bid_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXOBidInfo extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "36", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; 
	@Column(name = "PROJECT_ECONOMY_ID", length = 20)
	private Long projectEconomyId;                           //工程经济代码
	@Column(name = "ENTER_TIME", length = 20)
	private Date   enterTime;                             // 录入日期 
	@Column(name = "EXPLOYEE_ID", length = 20)
	private Long   exployeeId;                           //录入人代码 对应员工信息代码表
	
	@Column(name="BID_NAME",length=100)
	private String bidName;                            //招标文件名称
	
	@Column(name="DESCRIPTION",length=4000)
	private String 	desc;                        //描述
	
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
	
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}




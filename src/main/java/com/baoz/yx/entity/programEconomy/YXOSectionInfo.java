
package com.baoz.yx.entity.programEconomy;

import java.io.Serializable;
import java.util.Date;

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
 * 阶段信息表
 * </p>
 */

@Entity
@Table(name = "yxo_section_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXOSectionInfo extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "33", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; 
	@Column(name = "SECTION_NAME", length = 50)
	private String sectionName;                           // 阶段名称
//	@Column(name = "PROJECT_ECONOMY_ID", length = 20)
//	private Long projectEconomyId;                           //工程经济代码
	@Column(name = "BPMS_FLAG", length = 5)
	private String  bpmsFlag;                           // BPMS录入标志 0-未录入；1-已录入
	@Column(name = "BPMS_ENTER_TIME", length = 20)
	private Date bpmsEnterTime;                             // BPMS录入时间
	@Column(name = "DESIGN_SPEED", length = 20)
	private Date   designSpeed;                             // 设计委托进度
	@Column(name = "INVESTMENT_COUNT", length = 20)
	private Double investmentCount;                        //AO 投资估概算
	
	@ManyToOne
	@JoinColumn(name = "fk_program_economy_id")
	private YXOProgramEconomy			economy;          //阶段信息many->one工程经济信息   
	
	
	public YXOProgramEconomy getEconomy() {
		return economy;
	}
	public void setEconomy(YXOProgramEconomy economy) {
		this.economy = economy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public String getBpmsFlag() {
		return bpmsFlag;
	}
	public void setBpmsFlag(String bpmsFlag) {
		this.bpmsFlag = bpmsFlag;
	}
	public Date getBpmsEnterTime() {
		return bpmsEnterTime;
	}
	public void setBpmsEnterTime(Date bpmsEnterTime) {
		this.bpmsEnterTime = bpmsEnterTime;
	}
	public Date getDesignSpeed() {
		return designSpeed;
	}
	public void setDesignSpeed(Date designSpeed) {
		this.designSpeed = designSpeed;
	}
	
	public Double getInvestmentCount() {
		return investmentCount;
	}
	public void setInvestmentCount(Double investmentCount) {
		this.investmentCount = investmentCount;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}



package com.baoz.yx.entity.programEconomy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Where;

import com.baoz.yx.entity.PriEntity;

/**
 *  (yangyuan@baoz.com.cn)
 * <p>
 * 阶段履历表
 * </p>
 */

@Entity
@Table(name = "yxo_section_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXOSectionRecord extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "34", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; 
	@Column(name = "SECTION_ID", length = 20)
	private Long sectionId;                              //阶段代码
	@Column(name = "DRAFT_COUNT", length = 20)
	private Long  draftCount;                           // 稿数
	@Column(name = "TYPE_CODE", length = 50)
	private String  typeCode;                             // 类型代码（工程经济组成）   这个是不是费用组成？？
	@Column(name = "ENTER_TIME", length = 20)
	private Date   enterTime;                             // 录入时间 系统自动获取建立时间
	
	@OneToMany(targetEntity=YXOSectionRecordInfo.class,mappedBy="record")
	@Where(clause=" is_active = '1'")
	@JoinColumn(name="fk_section_record_info")
	private List<YXOSectionRecordInfo> recordInfo;	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getDraftCount() {
		return draftCount;
	}
	public void setDraftCount(Long draftCount) {
		this.draftCount = draftCount;
	}
	
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public List<YXOSectionRecordInfo> getRecordInfo() {
		return recordInfo;
	}
	public void setRecordInfo(List<YXOSectionRecordInfo> recordInfo) {
		this.recordInfo = recordInfo;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	
	
}



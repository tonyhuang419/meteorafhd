package com.baoz.yx.entity;

import com.baoz.core.entity.PrimaryEntity;
import java.io.Serializable;
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

/**
 * <p>
 * hr数据字典表
 * </p>
 * 
 * @author Chirs Zhou (chirs@zhoujin.com)
 */
@Entity
@Table(name = "yx_sys_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class SysItem extends PrimaryEntity implements Serializable {
	private static final long	serialVersionUID	= 8485230926287099780L;
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "1", allocationSize = 1, initialValue = 10000000)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;
	@Column(length = 20)
	private Long				fid;
	@Column(length = 50)
	private String				annexa;
	@Column(length = 50)
	private String				annexb;
	@Column(length = 50)
	private String				annexc;
	@Column(length = 100)
	private String				annexd;
	@Column(length = 100)
	private String				annexe;
	@Column(length = 20)
	private String				itemid;
	@Column(length = 20)
	private String				objtid;
	@Column(length = 20)
	private String				refeid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getAnnexa() {
		return annexa;
	}

	public void setAnnexa(String annexa) {
		this.annexa = annexa;
	}

	public String getAnnexb() {
		return annexb;
	}

	public void setAnnexb(String annexb) {
		this.annexb = annexb;
	}

	public String getAnnexc() {
		return annexc;
	}

	public void setAnnexc(String annexc) {
		this.annexc = annexc;
	}

	public String getAnnexd() {
		return annexd;
	}

	public void setAnnexd(String annexd) {
		this.annexd = annexd;
	}

	public String getAnnexe() {
		return annexe;
	}

	public void setAnnexe(String annexe) {
		this.annexe = annexe;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getObjtid() {
		return objtid;
	}

	public void setObjtid(String objtid) {
		this.objtid = objtid;
	}

	public String getRefeid() {
		return refeid;
	}

	public void setRefeid(String refeid) {
		this.refeid = refeid;
	}
}

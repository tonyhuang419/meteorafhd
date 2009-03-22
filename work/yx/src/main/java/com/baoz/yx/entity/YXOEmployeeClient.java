package com.baoz.yx.entity;


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

/**
 * <p>
 * 员工客户表
 * </p>
 * 
 * @author yangyuan
 */
@Entity
@Table(name = "yx_exployee_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class YXOEmployeeClient extends PriEntity implements Serializable {
	private static final long	serialVersionUID	= -4050908306322296801L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "9", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;

	@ManyToOne
	@JoinColumn(name = "fk_exployee_id" ,nullable = false)
	private Employee				exp;

	@ManyToOne
	@JoinColumn(name = "fk_client_id",nullable=false)
	private YXClientCode 			cli;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Employee getExp() {
		return exp;
	}

	public void setExp(Employee exp) {
		this.exp = exp;
	}

	public YXClientCode getCli() {
		return cli;
	}

	public void setCli(YXClientCode cli) {
		this.cli = cli;
	}

	

	
}


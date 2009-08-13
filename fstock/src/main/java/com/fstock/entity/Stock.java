package com.fstock.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;


@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Stock implements Serializable {

	private static final long serialVersionUID = 2887751273500967619L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 20)
	private Long id;
	
	@Column(name = "code", length = 100)
	private String code;
	
	@Column(name = "name", length = 100)
	private String name;
	
	/**
	 * 44434
	 */
	@Column(name = "average_level", length = 100)
	private String averageLevel;

	/**
	 * yyyyMMdd/yyyyMMdd
	 */
	@Column(name = "average_level_date", length = 4000)
	private String averageLevelDate;
	
	public Stock(String code,String name){
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAverageLevel() {
		return averageLevel;
	}

	public void setAverageLevel(String averageLevel) {
		this.averageLevel = averageLevel;
	}

	public String getAverageLevelDate() {
		return averageLevelDate;
	}

	public void setAverageLevelDate(String averageLevelDate) {
		this.averageLevelDate = averageLevelDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}

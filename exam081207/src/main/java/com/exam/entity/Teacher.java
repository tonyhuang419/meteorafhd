package com.exam.entity;

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



@Entity
@Table(name = "exam_teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Teacher extends PriEntity implements Serializable {
	private static final long serialVersionUID = -2454636805102140717L;
	
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "teacherId", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private long id;
	
	@Column(name = "teacher_name", length = 200)
	private String teacherName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}




}

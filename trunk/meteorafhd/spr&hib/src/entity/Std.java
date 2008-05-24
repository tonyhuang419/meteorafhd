package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "Std")
public class Std implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "sid")
	private Integer sid;
	
	@Column(name = "sname",length = 20) 
	private String sname;
	
	@Column(name = "addr") 
	private String addr;

	public Std(){ }

	public Std(java.lang.Integer sid){
		this.setSid(sid);
	}

	public Integer getSid(){
		return sid;
	}

	public void setSid(Integer sid){
		this.sid = sid;
	}

	public String getSname(){
		return this.sname;
	}

	public void setSname(String sname){
		this.sname = sname;
	}

	public String getAddr(){
		return this.addr;
	}

	public void setAddr(String addr){
		this.addr = addr;
	}

	
	public boolean equals(Object rhs){
		if (rhs == null)
			return false;
		if (! (rhs instanceof Std))
			return false;
		Std that = (Std) rhs;
		if (this.getSid() == null || that.getSid() == null)
			return false;
		return (this.getSid().equals(that.getSid()));
	}
}

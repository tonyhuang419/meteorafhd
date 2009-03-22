package com.baoz.yx.entity.contract;

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


//项目成本确认表
@Entity
@Table(name = "yx_item_costing_sure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ItemCostingSure implements java.io.Serializable {

	private static final long serialVersionUID = 5141321104424358625L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "87", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="con_item_minfo_sid",length = 20, nullable = false)
	private Long id;   //项目成本确认系统号
		
	@Column(name = "fk_con_item_minfo_sid")
	private Long contractItemMaininfo;  //合同项目主体信息系统号

	@Column(name="feedback_nfo",length = 200 )
	private String feedbackInfo; 		 //反馈信息

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getFeedbackInfo() {
		return feedbackInfo;
	}

	public void setFeedbackInfo(String feedbackInfo) {
		this.feedbackInfo = feedbackInfo;
	}

	public Long getContractItemMaininfo() {
		return contractItemMaininfo;
	}

	public void setContractItemMaininfo(Long contractItemMaininfo) {
		this.contractItemMaininfo = contractItemMaininfo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}




}

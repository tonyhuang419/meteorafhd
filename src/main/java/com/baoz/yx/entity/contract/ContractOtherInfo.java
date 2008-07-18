package com.baoz.yx.entity.contract;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * <p>
 * 合同其他信息表
 * </p>
 */
@Entity
@Table(name = "yx_con_other_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractOtherInfo implements Serializable {
	private static final long serialVersionUID	= 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "72", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="con_other_info_sid",length = 20)
	private Long  otherInfoId;  //合同其它信息系统号
	

	//请注意：一个合同主体信息同一时间只能有1个“合同其它信息”
	@Column(name = "fk_con_main_info_sid")
	private Long contractMainSid;  //合同主体信息系统号

	
	@Column(name = "is_perativereport")
	private Boolean needPerativeReport;//是否需要开工报告

	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "OP_REPORT_Date")
	private Date perativeReport;  	//开工报告日期
	
	
	
	
	@Column(name = "is_finallyreport")
	private Boolean needFinallyReport;//是否需要竣工验收
	

	
	@Temporal(TemporalType.DATE)
	@Column(name = "FIN_REPORT_Date")
	private Date finallyReport;  //竣工验收书日期
	
	
	@Column(name = "is_recivedthing")
	private Boolean  needRecivedThing;//是否需要实物交接
	

	
	@Temporal(TemporalType.DATE)
	@Column(name = "REC_THING_Date")
	private Date recivedThing;  //实物交接日期
	
	

	
	

	
	@Column(name = "other_remarks",length=1000)
	private String otherRemarks;  //备注

	@Column(name = "is_con_mod_info") 
	private Boolean conModInfo;   //是否属于合同变更信息
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	@Column(name="FINALLY_LIZE")
	private String finallyLize;//完工应交材料
	
	@Temporal(TemporalType.DATE)
	@Column(name="WANT_OPEN_REPORT_DATE")
	private Date wantOpenReportDate;//需要开工报告时间
	@Temporal(TemporalType.DATE)
	@Column(name="WANT_FINALLY_REPT_DATE")
	private Date wantFinallyReptDate;//需要竣工验收时间
	@Temporal(TemporalType.DATE)
	@Column(name="WANT_RECIVE_THING")
	private Date wantReciveThing;//需要实物交接时间
	public Long getOtherInfoId() {
		return otherInfoId;
	}


	public void setOtherInfoId(Long otherInfoId) {
		this.otherInfoId = otherInfoId;
	}




	public Long getContractMainSid() {
		return contractMainSid;
	}


	public void setContractMainSid(Long contractMainSid) {
		this.contractMainSid = contractMainSid;
	}



	public Date getPerativeReport() {
		return perativeReport;
	}


	public void setPerativeReport(Date perativeReport) {
		this.perativeReport = perativeReport;
	}


	public Date getFinallyReport() {
		return finallyReport;
	}


	public void setFinallyReport(Date finallyReport) {
		this.finallyReport = finallyReport;
	}


	public Date getRecivedThing() {
		return recivedThing;
	}


	public void setRecivedThing(Date recivedThing) {
		this.recivedThing = recivedThing;
	}


	public String getOtherRemarks() {
		return otherRemarks;
	}


	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}





	public Boolean getConModInfo() {
		return conModInfo;
	}


	public void setConModInfo(Boolean conModInfo) {
		this.conModInfo = conModInfo;
	}


	public Boolean getNeedPerativeReport() {
		return needPerativeReport;
	}


	public void setNeedPerativeReport(Boolean needPerativeReport) {
		this.needPerativeReport = needPerativeReport;
	}


	public Boolean getNeedFinallyReport() {
		return needFinallyReport;
	}


	public void setNeedFinallyReport(Boolean needFinallyReport) {
		this.needFinallyReport = needFinallyReport;
	}


	public Boolean getNeedRecivedThing() {
		return needRecivedThing;
	}


	public void setNeedRecivedThing(Boolean needRecivedThing) {
		this.needRecivedThing = needRecivedThing;
	}


	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}



	public String getFinallyLize() {
		return finallyLize;
	}


	public void setFinallyLize(String finallyLize) {
		this.finallyLize = finallyLize;
	}


	public Date getWantOpenReportDate() {
		return wantOpenReportDate;
	}


	public void setWantOpenReportDate(Date wantOpenReportDate) {
		this.wantOpenReportDate = wantOpenReportDate;
	}


	public Date getWantFinallyReptDate() {
		return wantFinallyReptDate;
	}


	public void setWantFinallyReptDate(Date wantFinallyReptDate) {
		this.wantFinallyReptDate = wantFinallyReptDate;
	}


	public Date getWantReciveThing() {
		return wantReciveThing;
	}


	public void setWantReciveThing(Date wantReciveThing) {
		this.wantReciveThing = wantReciveThing;
	}
	
}

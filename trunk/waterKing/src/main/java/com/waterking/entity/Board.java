package com.waterking.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Board")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Board implements Serializable {

	private static final long serialVersionUID = 4208204656035193726L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id" ,length = 20)
	private Long id;
	
	@Column(name = "topic", length = 255)
	private String topic;
	
	@Column(name = "topicUrl", length = 255)
	private String topicUrl;
	
	@Column(name = "starter", length = 255)
	private String starter;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "issueDate" )
	private Date issueDate;
	
	@Column(name = "replyNum", length = 20)
	private Long replyNum;
	
	@Column(name = "readNum", length = 20)
	private Long readNum;
	
	@Column(name = "readLevel", length = 20)
	private Long readLevel;
	
	@Column(name = "isVote", length = 1)
	private Boolean isVote;

	@Column(name = "lastScanFloor", length = 20)
	private Long lastScanFloor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastScanTime" )
	private Date lastScanTime;
	
	@Column(name = "lastUpateUser", length = 255)
	private String lastUpateUser;
	
	@Column(name = "skip", length = 1)
	private Boolean skip;
	
	/**
	 * topic's endPage page,hasn't serialize it
	 */
	@Transient
	private Long endPage;
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getStarter() {
		return starter;
	}
	public void setStarter(String starter) {
		this.starter = starter;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Long getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Long replyNum) {
		this.replyNum = replyNum;
	}
	public String getTopicUrl() {
		return topicUrl;
	}
	public void setTopicUrl(String topicUrl) {
		this.topicUrl = topicUrl;
	}
	public Date getLastScanTime() {
		return lastScanTime;
	}
	public void setLastScanTime(Date lastScanTime) {
		this.lastScanTime = lastScanTime;
	}
	public Long getReadNum() {
		return readNum;
	}
	public void setReadNum(Long readNum) {
		this.readNum = readNum;
	}
	public Long getEndPage() {
		return endPage;
	}
	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}
	public Long getReadLevel() {
		return readLevel;
	}
	public void setReadLevel(Long readLevel) {
		this.readLevel = readLevel;
	}
	public Boolean getIsVote() {
		return isVote;
	}
	public void setIsVote(Boolean isVote) {
		this.isVote = isVote;
	}
	public Long getLastScanFloor() {
		return lastScanFloor;
	}
	public void setLastScanFloor(Long lastScanFloor) {
		this.lastScanFloor = lastScanFloor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLastUpateUser() {
		return lastUpateUser;
	}
	public void setLastUpateUser(String lastUpateUser) {
		this.lastUpateUser = lastUpateUser;
	}
	public Boolean getSkip() {
		return skip;
	}
	public void setSkip(Boolean skip) {
		this.skip = skip;
	}
	
}

package com.waterking.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;




@Entity
@Table(name = "Board_Detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
@Indexed
public class BoardDetail implements Serializable {

	private static final long serialVersionUID = -1317011598005210392L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 20)
	@DocumentId
	private Long id;

	@Column(name = "floor", length = 255)
	private String floor;

	@Column(name = "topic", length = 255)
	private String topic;  //reference Board's topic , foreigner key

	@Column(name = "postId", length = 255)
	private String postId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "postTime" )
	private Date postTime;

	@Lob
	@Column(name = "postMessage")
	@Field( store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = ChineseAnalyzer.class)) 
	private String postMessage;

	@Column(name = "faceNum", length = 20)
	private Long faceNum;

	@Column(name = "faceDetail", length = 4000)
	@Field( store = Store.YES, index = Index.TOKENIZED)
	private String faceDetail;

	@Column(name = "pictureNum", length = 20)
	private Long pictureNum;

	@Column(name = "pictureDetail", length = 4000)
	@Field( store = Store.YES, index = Index.TOKENIZED)
	private String pictureDetail;

	/**
	 * redundancy field (postMessage length)
	 */
	@Column(name = "postMessageLength", length = 20)
	private Long postMessageLength; 

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastScanTime" )
	private Date lastScanTime;

	@Column(name = "lastUpateUser", length = 255)
	private String lastUpateUser;

	@Column(name = "floorNum", length = 20)
	private Long floorNum;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}

	public Long getPostMessageLength() {
		return postMessageLength;
	}

	public void setPostMessageLength(Long postMessageLength) {
		this.postMessageLength = postMessageLength;
	}

	public Long getFaceNum() {
		return faceNum;
	}

	public void setFaceNum(Long faceNum) {
		this.faceNum = faceNum;
	}

	public String getFaceDetail() {
		return faceDetail;
	}

	public void setFaceDetail(String faceDetail) {
		this.faceDetail = faceDetail;
	}

	public Long getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(Long pictureNum) {
		this.pictureNum = pictureNum;
	}

	public String getPictureDetail() {
		return pictureDetail;
	}

	public void setPictureDetail(String pictureDetail) {
		this.pictureDetail = pictureDetail;
	}

	public Date getLastScanTime() {
		return lastScanTime;
	}

	public void setLastScanTime(Date lastScanTime) {
		this.lastScanTime = lastScanTime;
	}

	public String getLastUpateUser() {
		return lastUpateUser;
	}

	public void setLastUpateUser(String lastUpateUser) {
		this.lastUpateUser = lastUpateUser;
	}

	public Long getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Long floorNum) {
		this.floorNum = floorNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



}

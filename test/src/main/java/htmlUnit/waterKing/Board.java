package htmlUnit.waterKing;

import java.util.Date;

public class Board {
	
	private String topic;
	
	private String  topicUrl;
	
	private String starter;
	
	private Date issueDate;
	
	private Long replyNum;
	
	private Long readNum;
	
	private Date lastScanTime;
	
	private Long lastScanFloor;
	
	private Long raedLevel;
	
	/**
	 * topic's start page,don't serialize it
	 */
	private Long startPage;
	
	
	/**
	 * topic's endPage page,don't serialize it
	 */
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
	public Long getLastScanFloor() {
		return lastScanFloor;
	}
	public void setLastScanFloor(Long lastScanFloor) {
		this.lastScanFloor = lastScanFloor;
	}
	public Long getStartPage() {
		return startPage;
	}
	public void setStartPage(Long startPage) {
		this.startPage = startPage;
	}
	public Long getEndPage() {
		return endPage;
	}
	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}
	public Long getRaedLevel() {
		return raedLevel;
	}
	public void setRaedLevel(Long raedLevel) {
		this.raedLevel = raedLevel;
	}
	
}

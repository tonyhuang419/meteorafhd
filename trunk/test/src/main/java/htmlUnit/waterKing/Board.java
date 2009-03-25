package htmlUnit.waterKing;

import java.util.Date;

public class Board {
	
	private Long id;
	
	private String topic;
	
	private String topicUrl;
	
	private String starter;
	
	private Date issueDate;
	
	private Long replyNum;
	
	private Long readNum;
	
	private Long readLevel;
	
	/**
	 * topic's endPage page,hasn't serialize it
	 */
	private Long endPage;
	
	private Boolean isVote;

	private Long lastScanFloor;
	
	private Date lastScanTime;
	
	private String lastUpateUser;
	
	private Boolean skip;
	
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

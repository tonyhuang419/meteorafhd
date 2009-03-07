package htmlUnit.waterKing;

import java.util.Date;

public class Board {
	
	private String topic;
	
	private String  topicUrl;
	
	private String starter;
	
	private Date issueDate;
	
	private Long replyNum;
	
	private Date lastScanTime;
	
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
	
}

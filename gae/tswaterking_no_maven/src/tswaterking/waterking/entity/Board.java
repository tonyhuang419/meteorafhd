package tswaterking.waterking.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Board {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String topic;

	@Persistent
	private String topicUrl;

	@Persistent
	private String starter;

	@Persistent
	private Date issueDate;

	@Persistent
	private Long replyNum;

	@Persistent
	private Long readNum;

	@Persistent
	private Long readLevel;

	@Persistent	
	private Boolean isVote;

	@Persistent
	private Long lastScanFloor;

	@Persistent
	private Date lastScanTime;

	@Persistent
	private String lastUpateUser;


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
}

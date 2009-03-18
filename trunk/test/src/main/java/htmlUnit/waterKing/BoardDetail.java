package htmlUnit.waterKing;

import java.util.Date;

public class BoardDetail {
	
	private String floor;
	
	/**
	 * reference Board's topic
	 */
	private String topic; 
	
	private String postId;
	
	private Date postTime;
	
	private String postMessage;
	
	private Long faceNum;
	
	/**
	 * redundancy field (postMessage length)
	 */
	private Long postMessageLength;  

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
	
	
	
}

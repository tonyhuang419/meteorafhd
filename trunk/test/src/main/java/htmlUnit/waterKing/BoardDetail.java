package htmlUnit.waterKing;

import java.util.Date;

public class BoardDetail {
	
	private String floor;
	
	/**
	 * reference Board's topic , foreigner key
	 */
	private String topic; 
	
	private String postId;
	
	private Date postTime;
	
	private String postMessage;
	
	private Long faceNum;
	
	private String faceDetail;
	
	private Long pictureNum;
	
	private String pictureDetail;
	
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
	
	
	
}

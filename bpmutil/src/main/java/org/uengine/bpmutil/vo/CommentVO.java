package org.uengine.bpmutil.vo;

import java.io.Serializable;

public class CommentVO implements Serializable {
	
	/**
	 * 
	 */
	
	String comment, commentType, userId, userName, jikCode, createdDate, commentType_ko, commentType_en, activityName;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJikCode() {
		return jikCode;
	}

	public void setJikCode(String jikCode) {
		this.jikCode = jikCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCommentType_ko() {
		return commentType_ko;
	}

	public void setCommentType_ko(String commentType_ko) {
		this.commentType_ko = commentType_ko;
	}

	public String getCommentType_en() {
		return commentType_en;
	}

	public void setCommentType_en(String commentType_en) {
		this.commentType_en = commentType_en;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
}

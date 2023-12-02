package com.ssafy.video.model.dto;

public class Comment {
	private int commentId;
	private String userId;
	private String username;
	private String content;
	private String regDate;
	
	public Comment() {}
	
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userId=" + userId + ", username=" + username + ", content="
				+ content + ", regDate=" + regDate + "]";
	}
	
	
	
	
}

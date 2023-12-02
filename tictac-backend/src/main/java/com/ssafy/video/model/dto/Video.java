package com.ssafy.video.model.dto;

public class Video {
	
	private int videoId;
	private String userId;
	private String content;
	private String videoSrc;
	private String orgVideoSrc;
	private String regDate;
	private int heartCnt;
	private int bookmarkCnt;
	private int viewCnt;
	private String thumbnailImgSrc;
	private String orgThumbnailImgSrc;
	private String categoryId;
	
	public Video() {}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVideoSrc() {
		return videoSrc;
	}

	public void setVideoSrc(String videoSrc) {
		this.videoSrc = videoSrc;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getHeartCnt() {
		return heartCnt;
	}

	public void setHeartCnt(int heartCnt) {
		this.heartCnt = heartCnt;
	}

	public int getBookmarkCnt() {
		return bookmarkCnt;
	}

	public void setBookmarkCnt(int bookmarkCnt) {
		this.bookmarkCnt = bookmarkCnt;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getThumbnailImgSrc() {
		return thumbnailImgSrc;
	}

	public void setThumbnailImgSrc(String thumbnailImgSrc) {
		this.thumbnailImgSrc = thumbnailImgSrc;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", userId=" + userId + ", content=" + content + ", videoSrc=" + videoSrc
				+ ", regDate=" + regDate + ", heartCnt=" + heartCnt + ", bookmarkCnt=" + bookmarkCnt + ", viewCnt="
				+ viewCnt + ", thumbnailImgSrc=" + thumbnailImgSrc + ", categoryId=" + categoryId + "]";
	}

	public String getOrgVideoSrc() {
		return orgVideoSrc;
	}

	public void setOrgVideoSrc(String orgVideoSrc) {
		this.orgVideoSrc = orgVideoSrc;
	}

	public String getOrgThumbnailImgSrc() {
		return orgThumbnailImgSrc;
	}

	public void setOrgThumbnailImgSrc(String orgThumbnailImgSrc) {
		this.orgThumbnailImgSrc = orgThumbnailImgSrc;
	}
	
	
	


}

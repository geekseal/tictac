package com.ssafy.video.model.dao;

import java.util.List;

import com.ssafy.video.model.dto.Video;

public interface VideoDao {

	List<Video> selectAllVideo();

	List<Video> selectBySearchWord(String searchWord);

	List<Video> selectByCategoryId(String categoryId);

	List<Video> selectUploadList(String userId);

	List<Video> selectMyBookmarkList(String userId);

	
	Video selectOneVideo(int videoId);

	int insertVideo(Video video);

	int updateVideo(Video video);

	int deleteVideo(int videoId);
	
	int updateViewCnt(int videoId);

}

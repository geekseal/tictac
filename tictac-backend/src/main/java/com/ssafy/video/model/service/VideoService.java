package com.ssafy.video.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.video.model.dto.Video;

public interface VideoService {
	List<Video> getWholeList();

	List<Video> getListBySearchWord(String searchWord);

	List<Video> getListByCategoryId(String categoryId);

	List<Video> getUploadList(String userId);

	List<Video> getMyBookmarkList(String userId);

	
	Video getVideoDetail(int videoId);

	int uploadVideo(Video video, MultipartFile file, MultipartFile thumbnail);

	int modifyVideo(Video video);

	int removeVideo(int videoId);
	
}

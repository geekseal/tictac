package com.ssafy.video.model.dao;

import java.util.List;

import com.ssafy.video.model.dto.Comment;

public interface CommentDao {

	List<Comment> selectAllComment(int videoId);

	int insertComment(int videoId, Comment comment);

	int updateComment(int videoId, Comment comment);

	int deleteComment(int videoId, int commentId);

	
}

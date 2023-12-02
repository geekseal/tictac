package com.ssafy.video.model.service;

import java.util.List;

import com.ssafy.video.model.dto.Comment;

public interface CommentService {

	List<Comment> getCommentList(int videoId);

	int writeComment(int videoId, Comment comment);

	int modifyComment(int videoId, Comment comment);

	int removeComment(int videoId, int commentId);


}

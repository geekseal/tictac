package com.ssafy.video.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.video.model.dao.CommentDao;
import com.ssafy.video.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	private CommentDao commentDao;
	
	@Autowired
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	
	@Override
	public List<Comment> getCommentList(int videoId) {
		return commentDao.selectAllComment(videoId);
	}

	@Override
	public int writeComment(int videoId, Comment comment) {
		return commentDao.insertComment(videoId, comment);
	}

	@Override
	public int modifyComment(int videoId, Comment comment) {
		return commentDao.updateComment(videoId, comment);
	}

	@Override
	public int removeComment(int videoId, int commentId) {
		return commentDao.deleteComment(videoId, commentId);
	}

}

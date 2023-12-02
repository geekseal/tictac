package com.ssafy.video.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.video.model.dto.Comment;
import com.ssafy.video.model.service.CommentService;


@RestController
@CrossOrigin
public class CommentController {

	@Autowired
	CommentService commentService;

	// 댓글 목록 조회
	@GetMapping("/video/{videoId}/comment")
	private ResponseEntity<?> detail(@PathVariable int videoId) {
		List<Comment> list = commentService.getCommentList(videoId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
	}

	// 댓글 등록
	@PostMapping("/video/{videoId}/comment")
	private ResponseEntity<Comment> upload(@PathVariable int videoId, @RequestBody Comment comment) {
		commentService.writeComment(videoId, comment);
		return new ResponseEntity<Comment>(comment, HttpStatus.OK);
	}

	// 댓글 수정
	@PutMapping("/video/{videoId}/comment")
	private ResponseEntity<Void> update(@PathVariable int videoId, @RequestBody Comment comment) {
		commentService.modifyComment(videoId, comment);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// 댓글 삭제
	@DeleteMapping("/video/{videoId}/comment")
	private ResponseEntity<Void> delete(@PathVariable int videoId, Integer commentId) {
		System.out.println(commentId);
		commentService.removeComment(videoId, commentId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}

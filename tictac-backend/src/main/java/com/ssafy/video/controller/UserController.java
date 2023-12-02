package com.ssafy.video.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.video.model.dto.User;
import com.ssafy.video.model.service.UserService;
import com.ssafy.video.util.JwtUtil;

@RestController
@CrossOrigin
public class UserController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	private ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		Map<String, Object> result = new HashMap<String, Object>();

		HttpStatus status = null;
		
		User tempUser = userService.login(user);

		try {
			// if 로그인 성공
			if (tempUser != null) {
				// 
				result.put("access-token", jwtUtil.createToken(tempUser.getUserId(), tempUser.getUsername(), tempUser.getEmail()));
				result.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
				// 로그인 실패
			} else {
				result.put("message", FAIL);
				status = HttpStatus.NO_CONTENT;
			}
			// 예외 발생
		} catch (UnsupportedEncodingException e) {
			result.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(result, status);
	}

//	@PostMapping("/login")
//	private ResponseEntity<?> login(User user, HttpSession session) {
//		User tempUser = userService.login(user);
//		if (tempUser == null) {
//			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
//		}
//		session.setAttribute("loginUser", user.getUsername());
//		return new ResponseEntity<String>(tempUser.getUsername(), HttpStatus.UNAUTHORIZED);
//	}
//	
	@GetMapping("/logout")
	private ResponseEntity<Void> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/@{userId}")
	private ResponseEntity<String> logout(@PathVariable String userId) {
		String username = userService.getUsername(userId);
		return new ResponseEntity<String>(username, HttpStatus.OK);
	}

}

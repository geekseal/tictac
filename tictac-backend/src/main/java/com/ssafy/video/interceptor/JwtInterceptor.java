package com.ssafy.video.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.video.util.JwtUtil;


@Component
public class JwtInterceptor implements HandlerInterceptor {
	private static final String HEADER_AUTH = "access-token";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 이거 안쓰면 CORS 에러
		if (request.getMethod().contentEquals("OPTIONS"))
			return true;
		
		// HTTP 요청 헤더에 "access-token" 이름으로 할당된 값 리턴
		String token = request.getHeader(HEADER_AUTH);
		
		if (token != null) {
			jwtUtil.valid(token); // 토큰 유효성 검사
			return true;
		}
		
		throw new Exception("유효하지 않은 접근");
//		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}

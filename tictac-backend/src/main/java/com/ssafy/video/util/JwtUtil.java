package com.ssafy.video.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	
	private static final String SALT = "a6kl2j5";
	
	// 토큰 생성 메서드
	public String createToken(String value1, String value2, String value3) throws UnsupportedEncodingException {
//		System.out.println( Arrays.toString((value2.getBytes("UTF-8"))));
		return Jwts.builder()
				.setHeaderParam("alg", "HS256")
				.setHeaderParam("typ", "JWT")
				.claim("userId", value1)
				.claim("username", value2)
//				.claim("username", value2.getBytes("UTF-8"))
				.claim("email", value3)
				.signWith(SignatureAlgorithm.HS256, SALT.getBytes("utf-8"))
				.compact();
	}
	
	// 토큰 유효성 검사 메서드
	public void valid(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
		Jwts.parser().setSigningKey(SALT.getBytes("utf-8")).parseClaimsJws(token);
	}
	
}

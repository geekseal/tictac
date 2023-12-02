package com.ssafy.video.model.dao;

import com.ssafy.video.model.dto.User;

public interface UserDao {

	User selectOne(String userId);

	String selectUsername(String userId);
	
}

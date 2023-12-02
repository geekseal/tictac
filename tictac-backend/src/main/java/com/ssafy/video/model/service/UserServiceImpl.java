package com.ssafy.video.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.video.model.dao.UserDao;
import com.ssafy.video.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public User login(User user) {
		User tempUser = userDao.selectOne(user.getUserId());
		if (tempUser != null && tempUser.getPassword().equals(user.getPassword())) {
			return tempUser;
		}
		return null;
	}

	@Override
	public String getUsername(String userId) {
		String username = userDao.selectUsername(userId);
		return username;
	}


}

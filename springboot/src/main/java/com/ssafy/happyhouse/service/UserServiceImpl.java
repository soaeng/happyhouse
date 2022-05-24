package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.dao.UserDao;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	// 회원 등록
	@Override
	@Transactional
	public UserResultDto userRegister(UserDto user) {
		UserResultDto userResultDto = new UserResultDto();

		if (userDao.userRegister(user) == 1 ) {
			userResultDto.setResult(SUCCESS);
		}else {
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}

	// 회원 조회
	@Override
	public UserDto userDetail(String userId) {
		UserDto user = userDao.userDetail(userId);
		return user;
	}

	// 회원 수정
	@Override
	@Transactional
	public UserResultDto userUpdate(UserDto user) {
		UserResultDto userResultDto = new UserResultDto();
		if( userDao.userUpdate(user) == 1 ) {
			userResultDto.setResult(SUCCESS);
		}else {
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}

	// 회원 삭제
	@Override
	@Transactional
	public UserResultDto userDelete(String userId) {
		UserResultDto userResultDto = new UserResultDto();
		// System.out.println("delete " + userId);
		int ret = userDao.userDelete(userId);
		if( ret == 1 ) {
			userResultDto.setResult(SUCCESS);
		}else {
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}
	
}

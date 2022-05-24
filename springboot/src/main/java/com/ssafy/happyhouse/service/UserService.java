package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserResultDto;

public interface UserService {
	
	// 회원 등록
	public UserResultDto userRegister(UserDto user);
	
	// 회원 조회
	public UserDto userDetail(String userId);
	
	// 회원 수정
	public UserResultDto userUpdate(UserDto user);
	
	// 회원 삭제
	public UserResultDto userDelete(String userId);
	
}

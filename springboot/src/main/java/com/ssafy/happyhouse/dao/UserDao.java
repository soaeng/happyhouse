package com.ssafy.happyhouse.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.UserDto;

@Mapper
public interface UserDao {
    // 회원 등록
    public int userRegister(UserDto userDto);

    // 회원 조회
    public UserDto userDetail(String userId);

    // 회원 수정
    public int userUpdate(UserDto user);

    // 회원 삭제
    public int userDelete(String userId);

}

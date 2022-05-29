package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.LoginDao;
import com.ssafy.happyhouse.dto.UserDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDao loginDao;

    @Override
    public UserDto login(UserDto dto) {

        // log.info("===== 로그인 수행 service - 확인완료 =====");
        UserDto userDto = loginDao.login(dto.getUserEmail());
        if (userDto != null && userDto.getUserPassword().equals(dto.getUserPassword())) {
            // log.info(userDto);
            return userDto;
        } else {
            return null;
        }
    }
}
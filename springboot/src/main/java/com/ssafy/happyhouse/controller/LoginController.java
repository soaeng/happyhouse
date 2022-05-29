package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.service.LoginService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(
        // localhost:5500 과 127.0.0.1 구분
        origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS}
)
public class LoginController {

    @Autowired
    LoginService service;

    // json request 처리 - @RequestBody (front가 바뀌면서 같이 바꿈)
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDto dto, HttpSession session) {

        UserDto userDto = service.login(dto);
        Map<String, String> map = new HashMap<>();

        if (userDto != null) {
            session.setAttribute("userDto", userDto);

            map.put("result", "success");
            map.put("userName", userDto.getUserName());
            map.put("userProfileImageUrl", userDto.getUserProfileImageUrl());

            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
        map.put("result", "fail");

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
    }
}



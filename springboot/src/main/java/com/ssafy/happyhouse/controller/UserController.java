package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserResultDto;
import com.ssafy.happyhouse.service.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(
        // localhost:5500 과 127.0.0.1 구분
        origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS}
)
@Log4j2
public class UserController {

    @Autowired
    UserService service;

    private static final int SUCCESS = 1;

    // 회원 등록
    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto, HttpSession session) {
        log.info("===== 회원 등록 =====");
        UserResultDto userResultDto = service.userRegister(userDto);

        Map<String, String> map = new HashMap<String, String>();

        if (userResultDto.getResult() == SUCCESS) {
            map.put("result", "success");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        } else {
            map.put("result", "fail");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> userDetail(@PathVariable String userId, HttpSession session) {
        log.info("===== 회원 조회 =====");
        UserDto userDto = service.userDetail(userId);
        log.info("조회한 회원 정보: " + userDto);
        if (userDto != null) {
            session.setAttribute("userDto", userDto);
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
    }

    // 회원 수정
    @PutMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> userUpdate(@PathVariable String userId, @RequestBody UserDto userDto, HttpSession session) {
        log.info("===== 회원 수정 =====");

        UserResultDto userResultDto = service.userUpdate(userDto);

        Map<String, String> map = new HashMap<String, String>();

        if (userResultDto.getResult() == SUCCESS) {
            session.setAttribute("userDto", service.userDetail(userId));
            map.put("result", "success");

            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        } else {
            map.put("result", "fail");

            return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 회원 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> userDelete(@PathVariable String userId, HttpSession session) {
        log.info("===== 회원 삭제 =====");

        UserResultDto userResultDto = service.userDelete(userId);

        Map<String, String> map = new HashMap<String, String>();

        if (userResultDto.getResult() == SUCCESS) {
            session.invalidate();
            map.put("result", "success");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        } else {
            map.put("result", "fail");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

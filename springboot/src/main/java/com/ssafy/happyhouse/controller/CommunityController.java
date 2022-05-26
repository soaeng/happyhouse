package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dto.CommunityDto;
import com.ssafy.happyhouse.dto.CommunityParamDto;
import com.ssafy.happyhouse.dto.CommunityResultDto;
import com.ssafy.happyhouse.dto.ReplyDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.service.CommunityService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(
	    // localhost:5500 과 127.0.0.1 구분
	    origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
	    allowCredentials = "true", 
	    allowedHeaders = "*", 
	    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
@Log4j2
public class CommunityController {

	@Autowired
	CommunityService service;
		
	private static final int SUCCESS = 1;
	
	// 게시글 목록
	@GetMapping("/community")
	private ResponseEntity<CommunityResultDto> communityList(CommunityParamDto communityParamDto){
		// log.info("===== 게시글 목록 controller - 확인 끝 =====");
		CommunityResultDto communityResultDto;

		if( communityParamDto.getKeyword().isEmpty() ) {
			communityResultDto = service.communityList(communityParamDto);
		}else {
			communityResultDto = service.communityListKeyword(communityParamDto);
		}
		
		if( communityResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 게시글 상세 조회
	@GetMapping("/community/{boardId}")
	private ResponseEntity<CommunityResultDto> communityDetail(@PathVariable int boardId, HttpSession session){
		// log.info("===== 게시글 상세 조회 controller - 확인 끝 =====");
		CommunityParamDto communityParamDto = new CommunityParamDto();
		communityParamDto.setBoardId(boardId);
		communityParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		
		CommunityResultDto communityResultDto = service.communityDetail(communityParamDto);

		if( communityResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	// 게시글 등록
	@PostMapping("/community")
	private ResponseEntity<CommunityResultDto> communityInsert(CommunityDto communityDto,  MultipartHttpServletRequest request) {
		
		// log.info("===== 게시글 등록 controller - 확인 =====");
		
		// LoginFilter 먼저 적용 필요!!
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		// log.info(userDto);
		
		communityDto.setUserSeq(userDto.getUserSeq());
				
		CommunityResultDto communityResultDto = service.communityInsert(communityDto, request);
		
		if( communityResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	// 게시글 수정
	@PostMapping("/community/{boardId}") 
	private ResponseEntity<CommunityResultDto> communityUpdate( CommunityDto communityDto, MultipartHttpServletRequest request){
		log.info("===== 게시글 수정 controller - 확인 =====");
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		communityDto.setUserSeq(userDto.getUserSeq());
		
		CommunityResultDto communityResultDto = service.communityUpdate(communityDto, request);
		
		if( communityResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 게시글 삭제
	@DeleteMapping("/community/{boardId}") 
	private ResponseEntity<CommunityResultDto> communityDelete(@PathVariable int boardId){
		log.info("===== 게시글 삭제 controller - 확인 =====");
		CommunityResultDto communityResultDto = service.communityDelete(boardId);
		
		if( communityResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	
	/* 댓글 처리 */
	// 특정 댓글  조회
//	@GetMapping("/reply/{replyId}")
//	private ResponseEntity<ReplyDto> replyDetail(@PathVariable int replyId, HttpSession session){
//		log.info("===== 댓글 상세 조회 controller");
//		CommunityParamDto communityParamDto = new CommunityParamDto();
//		communityParamDto.setReplyId(replyId);
//		System.out.println(((UserDto) session.getAttribute("userDto")).getUserSeq());
////		communityParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
//		
//		ReplyDto replyDto = service.replyDetail(communityParamDto);
//		if( replyDto != null) {
//			return new ResponseEntity<ReplyDto>(replyDto, HttpStatus.OK);
//		}else {
//			return new ResponseEntity<ReplyDto>(replyDto, HttpStatus.INTERNAL_SERVER_ERROR);
//		}		 
//	}
	
//	// 게시글 등록
//	@PostMapping("/community")
//	private ResponseEntity<CommunityResultDto> communityInsert(CommunityDto communityDto,  MultipartHttpServletRequest request) {
//		
//		// log.info("===== 게시글 등록 controller - 확인 =====");
//		
//		// LoginFilter 먼저 적용 필요!!
//		HttpSession session = request.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("userDto");
//		
//		// log.info(userDto);
//		
//		communityDto.setUserSeq(userDto.getUserSeq());
//				
//		CommunityResultDto communityResultDto = service.communityInsert(communityDto, request);
//		
//		if( communityResultDto.getResult() == SUCCESS ) {
//			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
//		}else {
//			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
//		}		 
//	}
//	
//	// 게시글 수정
//	@PostMapping("/community/{boardId}") 
//	private ResponseEntity<CommunityResultDto> communityUpdate( CommunityDto communityDto, MultipartHttpServletRequest request){
//		log.info("===== 게시글 수정 controller - 확인 =====");
//		HttpSession session = request.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("userDto");
//		
//		communityDto.setUserSeq(userDto.getUserSeq());
//		
//		CommunityResultDto communityResultDto = service.communityUpdate(communityDto, request);
//		
//		if( communityResultDto.getResult() == SUCCESS ) {
//			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
//		}else {
//			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	// 게시글 삭제
//	@DeleteMapping("/community/{boardId}") 
//	private ResponseEntity<CommunityResultDto> communityDelete(@PathVariable int boardId){
//		log.info("===== 게시글 삭제 controller - 확인 =====");
//		CommunityResultDto communityResultDto = service.communityDelete(boardId);
//		
//		if( communityResultDto.getResult() == SUCCESS ) {
//			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.OK);
//		}else {
//			return new ResponseEntity<CommunityResultDto>(communityResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
//		}		 
//	}
//	
//	
//	
}

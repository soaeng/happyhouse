package com.ssafy.happyhouse.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dto.CommunityDto;
import com.ssafy.happyhouse.dto.CommunityParamDto;
import com.ssafy.happyhouse.dto.CommunityResultDto;

public interface CommunityService {
	
	// 게시글 등록
	public CommunityResultDto communityInsert(CommunityDto dto, MultipartHttpServletRequest request);
	
	// 게시글 조회
	public CommunityResultDto communityDetail(CommunityParamDto communityParamDto);
	
	// 게시글 삭제
	public CommunityResultDto communityDelete(int boardId);
	
	// 게시글 수정
	public CommunityResultDto communityUpdate(CommunityDto dto, MultipartHttpServletRequest request);

	// 게시글 목록
	public CommunityResultDto communityList(CommunityParamDto communityParamDto);
	
	// 게시글 검색 결과
	public CommunityResultDto communityListKeyword(CommunityParamDto communityParamDto);

}
